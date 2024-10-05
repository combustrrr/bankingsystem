package main.java.com.banking.system.ui.panels;

import main.java.com.banking.system.ui.panels.buttons.RoundedButton;

import javax.swing.*;
import java.awt.*;

import static main.java.com.banking.system.BankingSystem.viewTransactions;

public class ViewTransactionsPanel {
    public static JPanel viewTransactionsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("View Transactions"));

        JTextArea transactionArea = new JTextArea();
        transactionArea.setEditable(false);
        transactionArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        transactionArea.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        JScrollPane scrollPane = new JScrollPane(transactionArea);

        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel accNumberLabel = new JLabel("Account Number:");
        JTextField accNumberField = new JTextField(20);
        JButton viewButton = new RoundedButton("View Transactions");

        gbc.gridx = 0;
        gbc.gridy = 0;
        inputPanel.add(accNumberLabel, gbc);
        gbc.gridx = 1;
        inputPanel.add(accNumberField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        inputPanel.add(viewButton, gbc);

        panel.add(inputPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);

        viewButton.addActionListener(_ -> {
            String accountNumber = accNumberField.getText();

            if (accountNumber.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter account number", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            viewTransactions(accountNumber, transactionArea);
        });

        return panel;
    }
}
