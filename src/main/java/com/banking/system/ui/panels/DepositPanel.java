package main.java.com.banking.system.ui.panels;

import main.java.com.banking.system.ui.panels.buttons.RoundedButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

import static main.java.com.banking.system.BankingSystem.deposit;

/**
 * A panel for depositing money into an account.
 */
public class DepositPanel {
    /**
     * Creates a new panel for depositing money into an account.
     *
     * @return a new panel for depositing money into an account
     */
    public static JPanel depositPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Deposit"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel accNumberLabel = new JLabel("Account Number:");
        JTextField accNumberField = new JTextField(20);
        JLabel amountLabel = new JLabel("Amount:");
        JTextField amountField = new JTextField(20);
        RoundedButton depositButton = new RoundedButton("Deposit");

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
        panel.add(depositButton, gbc);

        depositButton.addActionListener(e -> {
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
                deposit(accountNumber, amount);
                accNumberField.setText("");
                amountField.setText("");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Please enter a valid amount", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        return panel;
    }
}