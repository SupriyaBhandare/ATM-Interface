import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class ATMSystemGUI extends JFrame {
    private String userId = "123456";
    private String userPin = "1234";
    private double balance = 1000.0;
    private List<String> transactionHistory = new ArrayList<>();

    private JTextField userIdField;
    private JPasswordField pinField;
    private JTextArea outputTextArea;
    private JTextField amountField;
    private JTextField transferUserIdField;

    public ATMSystemGUI() {
        setTitle("ATM System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(500, 500));

        createLoginPanel();
        createATMPanel();

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void createLoginPanel() {
        JPanel loginPanel = new JPanel(new GridLayout(4, 2));
        loginPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel userIdLabel = new JLabel("User ID:");
        userIdLabel.setFont(new Font("Arial", Font.BOLD, 16));
        userIdField = new JTextField();
        userIdField.setFont(new Font("Arial", Font.PLAIN, 16));
        JLabel pinLabel = new JLabel("PIN:");
        pinLabel.setFont(new Font("Arial", Font.BOLD, 16));
        pinField = new JPasswordField();
        pinField.setFont(new Font("Arial", Font.PLAIN, 16));
        JButton loginButton = new JButton("Login");
        loginButton.setFont(new Font("Arial", Font.BOLD, 16));

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performLogin();
            }
        });

        loginPanel.add(userIdLabel);
        loginPanel.add(userIdField);
        loginPanel.add(pinLabel);
        loginPanel.add(pinField);
        loginPanel.add(new JLabel()); // Empty space
        loginPanel.add(loginButton);

        add(loginPanel, BorderLayout.CENTER);
    }

    private void createATMPanel() {
        JPanel atmPanel = new JPanel(new BorderLayout());
        atmPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        outputTextArea = new JTextArea();
        outputTextArea.setEditable(false);
        outputTextArea.setFont(new Font("Arial", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(outputTextArea);

        JPanel transactionPanel = new JPanel(new GridLayout(4, 2));
        JLabel amountLabel = new JLabel("Amount:");
        amountLabel.setFont(new Font("Arial", Font.BOLD, 16));
        amountField = new JTextField();
        amountField.setFont(new Font("Arial", Font.PLAIN, 16));
        JLabel transferUserIdLabel = new JLabel("Transfer User ID:");
        transferUserIdLabel.setFont(new Font("Arial", Font.BOLD, 16));
        transferUserIdField = new JTextField();
        transferUserIdField.setFont(new Font("Arial", Font.PLAIN, 16));
        JButton historyButton = new JButton("Transactions History");
        historyButton.setFont(new Font("Arial", Font.BOLD, 16));
        JButton withdrawButton = new JButton("Withdraw");
        withdrawButton.setFont(new Font("Arial", Font.BOLD, 16));
        JButton depositButton = new JButton("Deposit");
        depositButton.setFont(new Font("Arial", Font.BOLD, 16));
        JButton transferButton = new JButton("Transfer");
        transferButton.setFont(new Font("Arial", Font.BOLD, 16));
        JButton quitButton = new JButton("Quit");
        quitButton.setFont(new Font("Arial", Font.BOLD, 16));

        historyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showTransactionHistory();
            }
        });

        withdrawButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performWithdraw();
            }
        });

        depositButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performDeposit();
            }
        });

        transferButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performTransfer();
            }
        });

        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        transactionPanel.add(amountLabel);
        transactionPanel.add(amountField);
        transactionPanel.add(transferUserIdLabel);
        transactionPanel.add(transferUserIdField);
        transactionPanel.add(historyButton);
        transactionPanel.add(withdrawButton);
        transactionPanel.add(depositButton);
        transactionPanel.add(transferButton);

        atmPanel.add(scrollPane, BorderLayout.CENTER);
        atmPanel.add(transactionPanel, BorderLayout.SOUTH);

        add(atmPanel, BorderLayout.CENTER);
    }

    private void performLogin() {
        String enteredUserId = userIdField.getText();
        String enteredPin = new String(pinField.getPassword());

        if (enteredUserId.equals(userId) && enteredPin.equals(userPin)) {
            userIdField.setEnabled(false);
            pinField.setEnabled(false);
            outputTextArea.setText("Login successful!\n");
        } else {
            outputTextArea.setText("Login failed. Please try again.\n");
        }
    }

    private void showTransactionHistory() {
        outputTextArea.append("Transaction History:\n");
        for (String transaction : transactionHistory) {
            outputTextArea.append(transaction + "\n");
        }
    }

    private void performWithdraw() {
        try {
            double amount = Double.parseDouble(amountField.getText());
            if (amount > 0 && amount <= balance) {
                balance -= amount;
                updateTransactionHistory("Withdraw: -$" + amount);
                outputTextArea.setText("Withdraw successful. New balance: $" + balance + "\n");
            } else {
                outputTextArea.setText("Invalid withdrawal amount.\n");
            }
        } catch (NumberFormatException e) {
            outputTextArea.setText("Invalid input. Please enter a valid amount.\n");
        }
    }

    private void performDeposit() {
        try {
            double amount = Double.parseDouble(amountField.getText());
            if (amount > 0) {
                balance += amount;
                updateTransactionHistory("Deposit: +$" + amount);
                outputTextArea.setText("Deposit successful. New balance: $" + balance + "\n");
            } else {
                outputTextArea.setText("Invalid deposit amount.\n");
            }
        } catch (NumberFormatException e) {
            outputTextArea.setText("Invalid input. Please enter a valid amount.\n");
        }
    }

    private void performTransfer() {
        try {
            double amount = Double.parseDouble(amountField.getText());
            String transferUserId = transferUserIdField.getText();

            if (amount > 0 && amount <= balance && !transferUserId.isEmpty()) {
                balance -= amount;
                updateTransactionHistory("Transfer to " + transferUserId + ": -$" + amount);
                outputTextArea.setText("Transfer successful. New balance: $" + balance + "\n");
            } else {
                outputTextArea.setText("Invalid transfer details.\n");
            }
        } catch (NumberFormatException e) {
            outputTextArea.setText("Invalid input. Please enter a valid amount.\n");
        }
    }

    private void updateTransactionHistory(String transaction) {
        transactionHistory.add(transaction);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ATMSystemGUI());
    }
}
