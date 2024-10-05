package main.java.com.banking.system.ui.panels;

import main.java.com.banking.system.ui.panels.buttons.RoundedButton;

import javax.swing.*;
import java.awt.*;

import static main.java.com.banking.system.BankingSystem.createAccount;

/**
 * A panel for creating a new account.
 */
public class AccountPanel {
    /**
     * Creates a new panel for creating a new account.
     *
     * @return a new panel for creating a new account
     */
    public static JPanel createAccountPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Create Account"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel nameLabel = new JLabel("Customer Name:");
        JTextField nameField = new JTextField(20);
        JLabel accNumberLabel = new JLabel("Account Number:");
        JTextField accNumberField = new JTextField(20);
        RoundedButton createButton = new RoundedButton("Create Account");

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(nameLabel, gbc);
        gbc.gridx = 1;
        panel.add(nameField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(accNumberLabel, gbc);
        gbc.gridx = 1;
        panel.add(accNumberField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        panel.add(createButton, gbc);

        createButton.addActionListener(e -> {
            String customerName = nameField.getText();
            String accountNumber = accNumberField.getText();

            if (customerName.isEmpty() || accountNumber.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter both customer name and account number", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            createAccount(customerName, accountNumber);
            nameField.setText("");
            accNumberField.setText("");
        });

        return panel;
    }
}