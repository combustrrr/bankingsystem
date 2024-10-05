package main.java.com.banking.system.ui.panels;

import main.java.com.banking.system.ui.panels.buttons.RoundedButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

import static main.java.com.banking.system.BankingSystem.withdraw;

/**
 * A panel for withdrawing money from an account.
 */
public class WithdrawPanel {
    /**
     * Creates a new panel for withdrawing money from an account.
     *
     * @return a new panel for withdrawing money from an account
     */
    public static JPanel withdrawPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Withdraw"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel accNumberLabel = new JLabel("Account Number:");
        JTextField accNumberField = new JTextField(20);
        JLabel amountLabel = new JLabel("Amount:");
        JTextField amountField = new JTextField(20);
        RoundedButton withdrawButton = new RoundedButton("Withdraw");

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(accNumberLabel, gbc);
        gbc.gridx = 1;
        panel.add(accNumberField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(amountLabel, gbc);
        gbc.gridx = 1;
        panel.add(amountField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        panel.add(withdrawButton, gbc);

        withdrawButton.addActionListener(e -> {
            String accountNumber = accNumberField.getText();
            String amountText = amountField.getText();

            if (accountNumber.isEmpty() || amountText.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter both account number and amount", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                double amount = Double.parseDouble(amountText);
                if (amount <= 0) {
                    JOptionPane.showMessageDialog(null, "Please enter a positive amount", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                withdraw(accountNumber, amount);
                accNumberField.setText("");
                amountField.setText("");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Please enter a valid amount", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        return panel;
    }
}