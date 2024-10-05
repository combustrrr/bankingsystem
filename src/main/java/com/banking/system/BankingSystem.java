package main.java.com.banking.system;

import main.java.com.banking.system.ui.panels.AccountPanel;
import main.java.com.banking.system.ui.panels.DepositPanel;
import main.java.com.banking.system.ui.panels.ViewTransactionsPanel;
import main.java.com.banking.system.ui.panels.WithdrawPanel;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

import static main.java.com.banking.system.DatabaseConnection.connect;

public class BankingSystem {
    public static void createAccount(String customerName, String accountNumber) {
        String query = "INSERT INTO accounts (customer_name, account_number) VALUES (?, ?)";
        Logger logger = Logger.getLogger(BankingSystem.class.getName());
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, customerName);
            pstmt.setString(2, accountNumber);
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Account created successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            logger.severe("Error creating account: " + e.getMessage());
            JOptionPane.showMessageDialog(null, "Error creating account: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void deposit(String accountNumber, double amount) {
        String updateAccountQuery = "UPDATE accounts SET balance = balance + ? WHERE account_number = ?";
        String insertTransactionQuery = "INSERT INTO transactions (account_id, type, amount) " +
                "VALUES ((SELECT id FROM accounts WHERE account_number = ?), 'deposit', ?)";

        Logger logger = Logger.getLogger(BankingSystem.class.getName()); // Get the logger for the current class

        try (Connection conn = connect();
             PreparedStatement updateAccountStmt = conn.prepareStatement(updateAccountQuery);
             PreparedStatement insertTransactionStmt = conn.prepareStatement(insertTransactionQuery)) {

            conn.setAutoCommit(false);

            // Update account balance
            updateAccountStmt.setDouble(1, amount);
            updateAccountStmt.setString(2, accountNumber);
            int affectedRows = updateAccountStmt.executeUpdate();

            if (affectedRows > 0) {
                // Insert transaction record
                insertTransactionStmt.setString(1, accountNumber);
                insertTransactionStmt.setDouble(2, amount);
                insertTransactionStmt.executeUpdate();

                conn.commit();

                JOptionPane.showMessageDialog(null, "Deposit successful.", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                conn.rollback();
                JOptionPane.showMessageDialog(null, "No rows affected. Account might not exist.", "Warning", JOptionPane.WARNING_MESSAGE);
            }
        } catch (SQLException e) {
            logger.severe("Error during deposit: " + e.getMessage()); // Log the error using the logger
            JOptionPane.showMessageDialog(null, "Error during deposit: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void withdraw(String accountNumber, double amount) {
        String updateAccountQuery = "UPDATE accounts SET balance = balance - ? WHERE account_number = ?";
        String insertTransactionQuery = "INSERT INTO transactions (account_id, type, amount) " +
                "VALUES ((SELECT id FROM accounts WHERE account_number = ?), 'withdrawal', ?)";

        Logger logger = Logger.getLogger(BankingSystem.class.getName()); // Get the logger for the current class

        try (Connection conn = connect();
             PreparedStatement updateAccountStmt = conn.prepareStatement(updateAccountQuery);
             PreparedStatement insertTransactionStmt = conn.prepareStatement(insertTransactionQuery)) {

            conn.setAutoCommit(false);

            // Update account balance
            updateAccountStmt.setDouble(1, amount);
            updateAccountStmt.setString(2, accountNumber);
            int affectedRows = updateAccountStmt.executeUpdate();

            if (affectedRows > 0) {
                // Insert transaction record
                insertTransactionStmt.setString(1, accountNumber);
                insertTransactionStmt.setDouble(2, amount);
                insertTransactionStmt.executeUpdate();

                conn.commit();

                JOptionPane.showMessageDialog(null, "Withdrawal successful.", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                conn.rollback();
                JOptionPane.showMessageDialog(null, "No rows affected. Account might not exist.", "Warning", JOptionPane.WARNING_MESSAGE);
            }
        } catch (SQLException e) {
            logger.severe("Error during withdrawal: " + e.getMessage()); // Log the error using the logger
            JOptionPane.showMessageDialog(null, "Error during withdrawal: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void viewTransactions(String accountNumber, JTextArea transactionArea) {
        String query = "SELECT t.date, t.type, t.amount " +
                "FROM transactions t " +
                "JOIN accounts a ON t.account_id = a.id " +
                "WHERE a.account_number = ?";
        Logger logger = Logger.getLogger(BankingSystem.class.getName()); // Get the logger for the current class

        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, accountNumber);
            ResultSet rs = pstmt.executeQuery();
            transactionArea.setText("");

            System.out.println("Executing query: " + query);
            System.out.println("With parameter: " + accountNumber);

            boolean hasResults = false;
            while (rs.next()) {
                hasResults = true;
                String transaction = String.format("Date: %s, Type: %s, Amount: %.2f\n",
                        rs.getTimestamp("date"), rs.getString("type"), rs.getDouble("amount"));
                transactionArea.append(transaction);
            }

            if (!hasResults) {
                transactionArea.append("No transactions found for this account.");
            }
        } catch (SQLException e) {
            logger.severe("Error retrieving transactions: " + e.getMessage()); // Log the error using the logger
            JOptionPane.showMessageDialog(null, "Error retrieving transactions: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        try {
            JFrame frame = new JFrame("Banking System");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 600);
            frame.setLayout(new BorderLayout());

            JPanel mainPanel = new JPanel(new GridLayout(2, 2, 10, 10));
            mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            mainPanel.add(AccountPanel.createAccountPanel());
            mainPanel.add(DepositPanel.depositPanel());
            mainPanel.add(WithdrawPanel.withdrawPanel());
            mainPanel.add(ViewTransactionsPanel.viewTransactionsPanel());

            frame.add(mainPanel, BorderLayout.CENTER);

            frame.setVisible(true);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "An error occurred: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }
}
