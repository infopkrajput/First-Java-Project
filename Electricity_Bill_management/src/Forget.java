import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Forget extends JFrame implements ActionListener {
    JTextField usernameInput, passwordInput1, passwordInput2, employeeIdInput, accountIdInput;
    Choice accounttype;
    JButton done, back;
    JLabel usernameError, passwordError1, passwordError2, employeeIdError, accountIdError;

    Forget() {
        // Set the Title of the Window
        setTitle("Forget Password");

        // Set the background image
        getContentPane().setBackground(new Color(83, 199, 224));

        // add a create account image
        ImageIcon forgetPassword = new ImageIcon(ClassLoader.getSystemResource("images/forget.png"));
        Image forgetPasswordModified = forgetPassword.getImage().getScaledInstance(300, 300, Image.SCALE_DEFAULT);
        ImageIcon forgetPasswordFinal = new ImageIcon(forgetPasswordModified);
        JLabel forgetPasswordFinalLabel = new JLabel(forgetPasswordFinal);
        forgetPasswordFinalLabel.setBounds(350, 80, 300, 300);
        add(forgetPasswordFinalLabel);


        // Add a Create Account label
        JLabel forget = new JLabel("Forget Password");
        forget.setBounds(220, 30, 400, 25);
        forget.setFont(new Font("Arial", Font.BOLD, 30));
        add(forget);

        // Add a Create account as Choice label
        JLabel accountTypeLabel = new JLabel("Account type As: ");
        accountTypeLabel.setBounds(20, 80, 150, 25);
        accountTypeLabel.setFont(new Font("Arial", Font.BOLD, 15));
        add(accountTypeLabel);

        // Add a Create account as Choice dropdown
        accounttype = new Choice();
        accounttype.add("Admin");
        accounttype.add("Customer");
        accounttype.setBounds(170, 80, 100, 25);
        accounttype.setFont(new Font("Arial", Font.BOLD, 15));
        add(accounttype);


        // Add a Employee ID label
        JLabel employeeID = new JLabel("Employee ID: ");
        employeeID.setBounds(20, 130, 150, 25);
        employeeID.setFont(new Font("Arial", Font.BOLD, 15));
        employeeID.setVisible(true);
        add(employeeID);

        // Add employeeIDInput text Field
        employeeIdInput = new JTextField();
        employeeIdInput.setBounds(170, 130, 150, 25);
        employeeIdInput.setFont(new Font("Arial", Font.PLAIN, 15));
        employeeIdInput.setVisible(true);
        add(employeeIdInput);

        employeeIdError = new JLabel("Required");
        employeeIdError.setForeground(Color.RED);
        employeeIdError.setBounds(170, 152, 150, 25);
        employeeIdError.setFont(new Font("Arial", Font.BOLD, 12));
        employeeIdError.setVisible(false);
        add(employeeIdError);

        // Add a AccountID label
        JLabel accountID = new JLabel("Account ID: ");
        accountID.setBounds(20, 130, 150, 25);
        accountID.setFont(new Font("Arial", Font.BOLD, 15));
        accountID.setVisible(false);
        add(accountID);

        // Add accountIdInput text Field
        accountIdInput = new JTextField();
        accountIdInput.setBounds(170, 130, 150, 25);
        accountIdInput.setFont(new Font("Arial", Font.PLAIN, 15));
        accountIdInput.setVisible(false);
        add(accountIdInput);

        accountIdError = new JLabel("Required");
        accountIdError.setForeground(Color.RED);
        accountIdError.setBounds(170, 152, 150, 25);
        accountIdError.setFont(new Font("Arial", Font.BOLD, 12));
        accountIdError.setVisible(false);
        add(accountIdError);

        accounttype.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                String user = accounttype.getSelectedItem();
                if (user.equals("Admin")) {
                    accountID.setVisible(false);
                    accountIdInput.setVisible(false);
                    employeeID.setVisible(true);
                    employeeIdInput.setVisible(true);
                } else {
                    accountID.setVisible(true);
                    accountIdInput.setVisible(true);
                    employeeID.setVisible(false);
                    employeeIdInput.setVisible(false);
                }
            }
        });
        // Add a Username label
        JLabel username = new JLabel("Username: ");
        username.setBounds(20, 180, 150, 25);
        username.setFont(new Font("Arial", Font.BOLD, 15));
        add(username);

        // Add UserNameInput text Field
        usernameInput = new JTextField();
        usernameInput.setBounds(170, 180, 150, 25);
        usernameInput.setFont(new Font("Arial", Font.PLAIN, 15));
        add(usernameInput);

        usernameError = new JLabel("Required");
        usernameError.setForeground(Color.RED);
        usernameError.setBounds(170, 202, 150, 25);
        usernameError.setFont(new Font("Arial", Font.BOLD, 12));
        usernameError.setVisible(false);
        add(usernameError);

        // Add a name label
        JLabel passwordLabel1 = new JLabel("Password: ");
        passwordLabel1.setBounds(20, 230, 150, 25);
        passwordLabel1.setFont(new Font("Arial", Font.BOLD, 15));
        add(passwordLabel1);

        // Add nameInput text Field
        passwordInput1 = new JPasswordField();
        passwordInput1.setBounds(170, 230, 150, 25);
        passwordInput1.setFont(new Font("Arial", Font.PLAIN, 15));
        add(passwordInput1);

        passwordError1 = new JLabel("Required");
        passwordError1.setForeground(Color.RED);
        passwordError1.setBounds(170, 252, 150, 25);
        passwordError1.setFont(new Font("Arial", Font.BOLD, 12));
        passwordError1.setVisible(false);
        add(passwordError1);

        // Add a password label
        JLabel passwordLabel2 = new JLabel("Confirm Password: ");
        passwordLabel2.setBounds(20, 280, 150, 25);
        passwordLabel2.setFont(new Font("Arial", Font.BOLD, 15));
        add(passwordLabel2);

        // Add nameInput text Field
        passwordInput2 = new JPasswordField();
        passwordInput2.setBounds(170, 280, 150, 25);
        passwordInput2.setFont(new Font("Arial", Font.PLAIN, 15));
        add(passwordInput2);

        passwordError2 = new JLabel("Required");
        passwordError2.setForeground(Color.RED);
        passwordError2.setBounds(170, 302, 150, 25);
        passwordError2.setFont(new Font("Arial", Font.BOLD, 12));
        passwordError2.setVisible(false);
        add(passwordError2);

        // Add a create button
        done = new JButton("Done");
        done.setBounds(50, 330, 100, 30);
        done.setFont(new Font("Arial", Font.BOLD, 20));
        done.setBackground(new Color(51, 107, 229));
        done.setForeground(new Color(255, 255, 255));
        done.addActionListener(this);
        add(done);

        // Add a Back button
        back = new JButton("Back");
        back.setBounds(190, 330, 100, 30);
        back.setFont(new Font("Arial", Font.BOLD, 20));
        back.setBackground(new Color(51, 107, 229));
        back.setForeground(new Color(255, 255, 255));
        back.addActionListener(this);
        add(back);

        setSize(700, 450);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(null);
        setVisible(true);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // Simulate the "Back" button functionality
                setVisible(false);
                new Login();
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Border redBorder = BorderFactory.createLineBorder(Color.RED, 2);
        Border normalBorder = BorderFactory.createLineBorder(Color.GRAY, 1);
        if (e.getSource() == done) {
            String id = null;
            if (accounttype.getSelectedItem().equals("Admin")) {
                id = employeeIdInput.getText();
            } else if (accounttype.getSelectedItem().equals("Customer")) {
                id = accountIdInput.getText();
            }

            String accountTypeString = accounttype.getSelectedItem();
            String userNameString = usernameInput.getText();
            String password1String = passwordInput1.getText();
            String password2String = passwordInput2.getText();

            usernameError.setVisible(false);
            employeeIdError.setVisible(false);
            accountIdError.setVisible(false);
            passwordError1.setVisible(false);
            passwordError2.setVisible(false);

            boolean isValid = true;

            if (userNameString.isEmpty()) {
                usernameInput.setBorder(redBorder);
                usernameError.setVisible(true);
                isValid = false;
            } else {
                usernameInput.setBorder(normalBorder);
            }


            if (accountTypeString.equals("Admin")) {
                String empId = employeeIdInput.getText();
                if (empId.isEmpty()) {
                    employeeIdInput.setBorder(redBorder);
                    employeeIdError.setText("Required");
                    employeeIdError.setFont(new Font("Arial", Font.BOLD, 12));
                    employeeIdError.setVisible(true);
                    isValid = false;
                } else if (!empId.matches("\\d{8}")) {
                    employeeIdInput.setBorder(redBorder);
                    employeeIdError.setText("Must be 8 digits");
                    employeeIdError.setFont(new Font("Arial", Font.BOLD, 12));
                    employeeIdError.setVisible(true);
                    isValid = false;
                } else {
                    employeeIdInput.setBorder(normalBorder);
                    employeeIdError.setVisible(false);
                }
            } else {
                String accId = accountIdInput.getText();
                if (accId.isEmpty()) {
                    accountIdInput.setBorder(redBorder);
                    accountIdError.setText("Required");
                    accountIdError.setFont(new Font("Arial", Font.BOLD, 12));
                    accountIdError.setVisible(true);
                    isValid = false;
                } else if (!accId.matches("\\d{8}")) {
                    accountIdInput.setBorder(redBorder);
                    accountIdError.setText("Must be 8 digits");
                    accountIdError.setFont(new Font("Arial", Font.BOLD, 12));
                    accountIdError.setVisible(true);
                    isValid = false;
                } else {
                    accountIdInput.setBorder(normalBorder);
                    accountIdError.setVisible(false);
                }
            }

            if (password1String.isEmpty()) {
                passwordInput1.setBorder(redBorder);
                passwordError1.setVisible(true);
                isValid = false;
            } else {
                passwordInput1.setBorder(normalBorder);
            }

            if (password2String.isEmpty()) {
                passwordInput2.setBorder(redBorder);
                passwordError2.setVisible(true);
                isValid = false;
            } else {
                passwordInput2.setBorder(normalBorder);
            }

            if (!password1String.equals(password2String)) {
                passwordError2.setVisible(true);
                passwordError2.setText("Passwords do not match");
                isValid = false;
            }

            if (!isValid) {
                JOptionPane.showMessageDialog(null, "Please fill all fields correctly.");
                return;
            }

            try {
                String query = "UPDATE users SET password = '" + password1String + "' " +
               "WHERE id = " + id + " AND username = '" + userNameString + "' AND usertype = '" + accountTypeString + "'";
                int rowsAffected = Database.getStatement().executeUpdate(query);
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(null, "Password Reset Successful");
                    setVisible(false);
                    new Login();
                }else{
                    JOptionPane.showMessageDialog(null, "No user found with the given ID.\n Please try again.");
                }

            } catch (Exception ex) {
                ex.printStackTrace();
            }


        } else if (e.getSource() == back) {
            setVisible(false);
            new Login();
        }
    }

    public static void main(String[] args) {
        new Forget();
    }
}
