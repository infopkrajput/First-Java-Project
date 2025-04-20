import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Login extends JFrame {
    @SuppressWarnings("unused")
    private JFrame mainFrame;

    public Login(JFrame mainFrame) {
        this.mainFrame = mainFrame;

        setTitle("Login");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(3, 2, 10, 10));

        JLabel userLabel = new JLabel("Username:");
        JTextField userField = new JTextField();

        JLabel passLabel = new JLabel("Password:");
        JPasswordField passField = new JPasswordField();

        JButton loginBtn = new JButton("Login");

        add(userLabel);
        add(userField);
        add(passLabel);
        add(passField);
        add(new JLabel()); // empty cell
        add(loginBtn);

        // Handle login logic
        loginBtn.addActionListener(e -> {
            String username = userField.getText();
            String password = new String(passField.getPassword());

            // Dummy check: replace with actual logic
            if (username.equals("admin") && password.equals("1234")) {
                JOptionPane.showMessageDialog(this, "Login Successful ✅");
                mainFrame.setEnabled(true); // Enable the main window
                dispose(); // Close the login window
            } else {
                JOptionPane.showMessageDialog(this, "Invalid Credentials ❌", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Override close operation
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                int choice = JOptionPane.showConfirmDialog(Login.this,
                        "Are you sure you want to exit?",
                        "Exit Confirmation",
                        JOptionPane.YES_NO_OPTION);
                if (choice == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });

        setVisible(true);
    }
}
