import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Login extends JFrame implements ActionListener {
    JTextField usernameInput;
    JPasswordField passwordInput;
    Choice loginChoice;
    JButton loginButton, forget, signUpButton;

    Login() {
        // Set the Title of the Window
        setTitle("Login");

        // Set the background image
        getContentPane().setBackground(Color.white);

        // Add a username label
        JLabel username = new JLabel("Username: ");
        username.setBounds(220, 30, 80, 25);
        add(username);

        // Add a username input field
        usernameInput = new JTextField();
        usernameInput.setBounds(300, 30, 150, 25);
        add(usernameInput);

        // Add a password label
        JLabel password = new JLabel("Password: ");
        password.setBounds(220, 60, 80, 25);
        add(password);

        // Add a password input field
        passwordInput = new JPasswordField();
        passwordInput.setBounds(300, 60, 150, 25);
        add(passwordInput);

        // Add a login as choice label
        JLabel login = new JLabel("Login As:");
        login.setBounds(220, 90, 80, 25);
        add(login);

        // Add a login as choice dropdown
        loginChoice = new Choice();
        loginChoice.add("Admin");
        loginChoice.add("Customer");
        loginChoice.setBounds(300, 90, 100, 25);
        add(loginChoice);

        // Add a login button
        loginButton = new JButton("Login");
        loginButton.setBounds(300, 130, 100, 25);
        loginButton.addActionListener(this);
        add(loginButton);

        // Add a cancel button
        forget = new JButton("Forget");
        forget.setBounds(360, 170, 80, 25);
        forget.addActionListener(this);
        add(forget);

        // Add a sign-up button
        signUpButton = new JButton("Sign Up");
        signUpButton.setBounds(260, 170, 80, 25);
        signUpButton.addActionListener(this);
        add(signUpButton);

        // Add a profile icon
        ImageIcon profileIcon = new ImageIcon(ClassLoader.getSystemResource("images/profileIcon.png"));
        Image profileIconModify = profileIcon.getImage().getScaledInstance(200, 200, Image.SCALE_DEFAULT);
        ImageIcon profileIconFinal = new ImageIcon(profileIconModify);
        JLabel profileIconFinalLabel = new JLabel(profileIconFinal);
        profileIconFinalLabel.setBounds(10, 25, 200, 200);
        add(profileIconFinalLabel);

        setSize(500, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setResizable(false);
        setLayout(null);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == signUpButton) {
            setVisible(false);
            new SignUp().setVisible(true);
        } else if (e.getSource() == loginButton) {
            String username = usernameInput.getText();
            String password = new String(passwordInput.getPassword());
            String loginAs = loginChoice.getSelectedItem();
            Session.userTypeLoggedInAS = loginAs;
            if (username.equals("root")) {
                loginAs = "Root";
                Session.userTypeLoggedInAS = "root";
            }

            String query = "SELECT * FROM users WHERE username = ? AND password = ? AND usertype = ?";

            try (Connection conn = Database.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(query)) {

                pstmt.setString(1, username);
                pstmt.setString(2, password);
                pstmt.setString(3, loginAs);

                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        Session.userNameLoggedIn = username;
                        setVisible(false);
                        System.out.println("Login Granted to " + Session.userNameLoggedIn + " as " + Session.userTypeLoggedInAS);
                        new main_window();
                    } else {
                        JOptionPane.showMessageDialog(null, "Invalid Username or Password");
                    }
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Database error: " + ex.getMessage());
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        } else if (e.getSource() == forget) {
            setVisible(false);
            new Forget().setVisible(true);
        }
    }

    public static void main(String[] args) {
        new Login();
    }
}
