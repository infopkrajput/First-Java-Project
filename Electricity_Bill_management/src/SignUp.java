import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;

public class SignUp extends JFrame implements ActionListener {
    JTextField usernameInput, passwordInput, nameInput, idInput;
    Choice createAccountAs;
    JButton create, back;
    JLabel usernameError, passwordError, employeeIdError, nameError, accountIdError;
    String idText = "Employee ID";

    SignUp() {
        // Set the Title of the Window
        setTitle("Sign Up");

        // Set the background image
        getContentPane().setBackground(new Color(83, 199, 224));

        // add a create account image
        ImageIcon createAccountIcon = new ImageIcon(ClassLoader.getSystemResource("images/create.png"));
        Image createAccountIconModified = createAccountIcon.getImage().getScaledInstance(300, 300, Image.SCALE_DEFAULT);
        ImageIcon createAccountIconFinal = new ImageIcon(createAccountIconModified);
        JLabel createAccountIconFinalLabel = new JLabel(createAccountIconFinal);
        createAccountIconFinalLabel.setBounds(350, 80, 300, 300);
        add(createAccountIconFinalLabel);

        // Add a Create Account label
        JLabel createAccount = new JLabel("Create Account");
        createAccount.setBounds(220, 30, 400, 25);
        createAccount.setFont(new Font("Arial", Font.BOLD, 30));
        add(createAccount);

        // Add a Create account as Choice label
        JLabel createAccountAsLabel = new JLabel("Create Account As: ");
        createAccountAsLabel.setBounds(20, 80, 150, 25);
        createAccountAsLabel.setFont(new Font("Arial", Font.BOLD, 15));
        add(createAccountAsLabel);

        // Add a Create account as Choice dropdown
        createAccountAs = new Choice();
        createAccountAs.add("Admin");
        createAccountAs.add("Customer");
        createAccountAs.setBounds(170, 80, 100, 25);
        createAccountAs.setFont(new Font("Arial", Font.BOLD, 15));
        add(createAccountAs);

        // Add a Employee ID label
        JLabel employeeID = new JLabel(idText);
        employeeID.setBounds(20, 130, 150, 25);
        employeeID.setFont(new Font("Arial", Font.BOLD, 15));
        employeeID.setVisible(true);
        add(employeeID);

        // Add employeeIDInput text Field
        idInput = new JTextField();
        idInput.setBounds(170, 130, 150, 25);
        idInput.setFont(new Font("Arial", Font.PLAIN, 15));
        idInput.setVisible(true);
        add(idInput);

        employeeIdError = new JLabel("Required");
        employeeIdError.setForeground(Color.RED);
        employeeIdError.setBounds(170, 152, 150, 25);
        employeeIdError.setFont(new Font("Arial", Font.BOLD, 12));
        employeeIdError.setVisible(false);
        add(employeeIdError);

        // Add a AccountID label
//        JLabel accountID = new JLabel("Account ID: ");
//        accountID.setBounds(20, 130, 150, 25);
//        accountID.setFont(new Font("Arial", Font.BOLD, 15));
//        accountID.setVisible(false);
//        add(accountID);

        // Add idInput text Field
//        idInput = new JTextField();
//        idInput.setBounds(170, 130, 150, 25);
//        idInput.setFont(new Font("Arial", Font.PLAIN, 15));
//        idInput.setVisible(true);
//        add(idInput);

        accountIdError = new JLabel("Required");
        accountIdError.setForeground(Color.RED);
        accountIdError.setBounds(170, 152, 150, 25);
        accountIdError.setFont(new Font("Arial", Font.BOLD, 12));
        accountIdError.setVisible(false);
        add(accountIdError);


        createAccountAs.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                String user = createAccountAs.getSelectedItem();
                if (user.equals("Admin")) {
                    employeeID.setText("Employee ID");
                } else {
                    employeeID.setText("Account ID");
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
        JLabel name = new JLabel("Name: ");
        name.setBounds(20, 230, 150, 25);
        name.setFont(new Font("Arial", Font.BOLD, 15));
        add(name);

        // Add nameInput text Field
        nameInput = new JTextField();
        nameInput.setBounds(170, 230, 150, 25);
        nameInput.setFont(new Font("Arial", Font.PLAIN, 15));
        add(nameInput);

        nameError = new JLabel("Required");
        nameError.setForeground(Color.RED);
        nameError.setBounds(170, 252, 150, 25);
        nameError.setFont(new Font("Arial", Font.BOLD, 12));
        nameError.setVisible(false);
        add(nameError);

        // Add a password label
        JLabel password = new JLabel("Password: ");
        password.setBounds(20, 280, 150, 25);
        password.setFont(new Font("Arial", Font.BOLD, 15));
        add(password);

        // Add nameInput text Field
        passwordInput = new JPasswordField();
        passwordInput.setBounds(170, 280, 150, 25);
        passwordInput.setFont(new Font("Arial", Font.PLAIN, 15));
        add(passwordInput);

        passwordError = new JLabel("Required");
        passwordError.setForeground(Color.RED);
        passwordError.setBounds(170, 302, 150, 25);
        passwordError.setFont(new Font("Arial", Font.BOLD, 12));
        passwordError.setVisible(false);
        add(passwordError);


        // Add a create button
        create = new JButton("Create");
        create.setBounds(50, 330, 100, 30);
        create.setFont(new Font("Arial", Font.BOLD, 20));
        create.setBackground(new Color(51, 107, 229));
        create.setForeground(new Color(255, 255, 255));
        create.addActionListener(this);
        add(create);

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
        if (e.getSource() == create) {
            String id = null;
            if (createAccountAs.getSelectedItem().equals("Admin")) {
                id = idInput.getText();
            } else if (createAccountAs.getSelectedItem().equals("Customer")) {
                id = idInput.getText();
            }
            String createAsString = createAccountAs.getSelectedItem();
            String userNameString = usernameInput.getText();
            String nameString = nameInput.getText();
            String passwordString = passwordInput.getText();

            // Hide all error labels
            usernameError.setVisible(false);
            nameError.setVisible(false);
            passwordError.setVisible(false);
            employeeIdError.setVisible(false);
            accountIdError.setVisible(false);

            boolean isValid = true;

            if (userNameString.isEmpty()) {
                usernameInput.setBorder(redBorder);
                usernameError.setVisible(true);
                isValid = false;
            } else {
                usernameInput.setBorder(normalBorder);
            }

            if (nameString.isEmpty()) {
                nameInput.setBorder(redBorder);
                nameError.setVisible(true);
                isValid = false;
            } else {
                nameInput.setBorder(normalBorder);
            }

            if (passwordString.isEmpty()) {
                passwordInput.setBorder(redBorder);
                passwordError.setVisible(true);
                isValid = false;
            } else {
                passwordInput.setBorder(normalBorder);
            }

            if (createAsString.equals("Admin")) {
                String empId = idInput.getText();
                if (empId.isEmpty()) {
                    idInput.setBorder(redBorder);
                    employeeIdError.setText("Required");
                    employeeIdError.setFont(new Font("Arial", Font.BOLD, 12));
                    employeeIdError.setVisible(true);
                    isValid = false;
                } else if (!empId.matches("\\d{8}")) {
                    idInput.setBorder(redBorder);
                    employeeIdError.setText("Must be 8 digits");
                    employeeIdError.setFont(new Font("Arial", Font.BOLD, 12));
                    employeeIdError.setVisible(true);
                    isValid = false;
                } else {
                    idInput.setBorder(normalBorder);
                    employeeIdError.setVisible(false);
                }
            } else {
                String accId = idInput.getText();
                if (accId.isEmpty()) {
                    idInput.setBorder(redBorder);
                    accountIdError.setText("Required");
                    accountIdError.setFont(new Font("Arial", Font.BOLD, 12));
                    accountIdError.setVisible(true);
                    isValid = false;
                } else if (!accId.matches("\\d{8}")) {
                    idInput.setBorder(redBorder);
                    accountIdError.setText("Must be 8 digits");
                    accountIdError.setFont(new Font("Arial", Font.BOLD, 12));
                    accountIdError.setVisible(true);
                    isValid = false;
                } else {
                    idInput.setBorder(normalBorder);
                    accountIdError.setVisible(false);
                }
            }

            if (!isValid) {
                JOptionPane.showMessageDialog(null, "Please fill all fields correctly.");
                return;
            }

            try {
                Database c = new Database();
//                String query = "insert into users value('" + id + "','" + userNameString + "','" + nameString + "','" + passwordString + "','" + createAsString + "')";
                String query = "INSERT INTO users (id, username, name, password, usertype) VALUES ('"
                        + id + "', '"
                        + userNameString + "', '"
                        + nameString + "', '"
                        + passwordString + "', '"
                        + createAsString + "');";

                c.getStatement().executeUpdate(query);
                JOptionPane.showMessageDialog(null, "Account Created Successful");
                setVisible(false);
                new Login();
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        } else if (e.getSource() == back) {
            setVisible(false);
            new Login();
        }
    }

    public boolean isPasswordStrong(String password) {
        return password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,}$");
    }

    public static void main(String[] args) {
        new SignUp();
    }
}
