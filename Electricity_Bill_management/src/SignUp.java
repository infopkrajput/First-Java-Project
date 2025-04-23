import javax.swing.*;
import java.awt.*;

public class SignUp extends JFrame {
    JTextField usernameInput,passwordInput,employeeIdInput,nameInput,accountIdInput;
    SignUp(){
        // Set the Title of the Window
        setTitle("Sign Up");

          // Set the background image
//        getContentPane().setBackground(Color.getHSBColor(0, 0, 255));

        // add a create account image
        ImageIcon createAccountIcon = new ImageIcon(ClassLoader.getSystemResource("images/create.png"));
        Image createAccountIconModified = createAccountIcon.getImage().getScaledInstance(300,300,Image.SCALE_DEFAULT);
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
        Choice createAccountAs = new Choice();
        createAccountAs.add("Admin");
        createAccountAs.add("Customer");
        createAccountAs.setBounds(170, 80, 100, 25);
        createAccountAs.setFont(new Font("Arial", Font.BOLD, 15));
        add(createAccountAs);

        // Add a Employee ID label
        JLabel employeeID = new JLabel("Employee ID: ");
        employeeID.setBounds(20, 130,150,25);
        employeeID.setFont(new Font("Arial", Font.BOLD, 15));
        employeeID.setVisible(true);
        add(employeeID);

        // Add employeeIDInput text Field
        employeeIdInput = new JTextField();
        employeeIdInput.setBounds(170, 130, 150, 25);
        employeeIdInput.setFont(new Font("Arial", Font.PLAIN,15));
        employeeIdInput.setVisible(true);
        add(employeeIdInput);

        // Add a AccountID label
        JLabel accountID = new JLabel("Account ID: ");
        accountID.setBounds(20, 130,150,25);
        accountID.setFont(new Font("Arial", Font.BOLD, 15));
        accountID.setVisible(false);
        add(accountID);

        // Add accountIdInput text Field
        accountIdInput = new JTextField();
        accountIdInput.setBounds(170, 130, 150, 25);
        accountIdInput.setFont(new Font("Arial", Font.PLAIN,15));
        accountIdInput.setVisible(false);
        add(accountIdInput);

        // Add a Username label
        JLabel username = new JLabel("Username: ");
        username.setBounds(20, 180,150,25);
        username.setFont(new Font("Arial", Font.BOLD, 15));
        add(username);

        // Add UserNameInput text Field
        usernameInput = new JTextField();
        usernameInput.setBounds(170, 180, 150, 25);
        usernameInput.setFont(new Font("Arial", Font.PLAIN,15));
        add(usernameInput);

        // Add a name label
        JLabel name = new JLabel("Name: ");
        name.setBounds(20, 230,150,25);
        name.setFont(new Font("Arial", Font.BOLD, 15));
        add(name);

        // Add nameInput text Field
        nameInput = new JTextField();
        nameInput.setBounds(170, 230, 150, 25);
        nameInput.setFont(new Font("Arial", Font.PLAIN,15));
        add(nameInput);

        // Add a password label
        JLabel password = new JLabel("Name: ");
        password.setBounds(20, 280,150,25);
        password.setFont(new Font("Arial", Font.BOLD, 15));
        add(password);

        // Add nameInput text Field
        passwordInput = new JTextField();
        passwordInput.setBounds(170, 280, 150, 25);
        passwordInput.setFont(new Font("Arial", Font.PLAIN,15));
        add(passwordInput);

        // Add a create button
        JButton create = new JButton("Create");
        create.setBounds(50,330,100,30);
        create.setFont(new Font("Arial", Font.BOLD, 20));
        add(create);

        // Add a Back button
        JButton back = new JButton("Back");
        back.setBounds(190,330,100,30);
        back.setFont(new Font("Arial", Font.BOLD, 20));
        add(back);



        setSize(700, 450);
        setLocationRelativeTo(null);
        setLayout(null);
        setVisible(true);

    }
    public static void main(String[] args) {
        new SignUp();
    }
}
