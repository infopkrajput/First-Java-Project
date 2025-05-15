import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import java.sql.SQLException;

public class payBill extends JFrame implements ActionListener {

    JTextField name, address, city, pinCode, accountId, mobileNumber, totalBilled, billNumbers;
    JButton open, paid, close;
    Choice state;

    payBill() {
        String title = "Pay Bill";
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) (screenSize.width * 0.90);
        int height = (int) (screenSize.height * 0.90);

        int marginLeft = (int) (width * 0.05);
        int marginTop = 100;
        int rowHeight = 30;
        int spacingY = 20;

        int labelWidth = (int) (width * 0.2);
        int fieldWidth = (int) (width * 0.35);

        int fieldX = marginLeft + labelWidth + 20;

        createLabel("Account Id: ", marginLeft, marginTop, labelWidth, rowHeight);
        accountId = createTextField(fieldX, marginTop, fieldWidth / 2, rowHeight);
        addSuggestionText(accountId, "Enter Account Id");

        open = createButton("Open", fieldX + fieldWidth - (fieldWidth / 2) + 50, marginTop, fieldWidth / 4, rowHeight);
        open.addActionListener(this);

        createLabel("Total Bill Amount :", marginLeft, marginTop + (rowHeight + spacingY), labelWidth, rowHeight);
        totalBilled = createTextField(fieldX, marginTop + (rowHeight + spacingY), fieldWidth / 4, rowHeight);
        totalBilled.setEditable(false);
        totalBilled.setFocusable(false);

        createLabel("Bill No:", fieldX + fieldWidth / 4 + 20, marginTop + (rowHeight + spacingY), fieldWidth / 2, rowHeight);
        billNumbers = createTextField(fieldX + fieldWidth / 2 - 40, marginTop + (rowHeight + spacingY), fieldWidth / 2 + 40, rowHeight);
        billNumbers.setEditable(false);
        billNumbers.setFocusable(false);

        createLabel("Customer name:", marginLeft, marginTop + (rowHeight + spacingY) * 2, labelWidth, rowHeight);
        name = createTextField(fieldX, marginTop + (rowHeight + spacingY) * 2, fieldWidth, rowHeight);
        name.setEditable(false);
        name.setFocusable(false);

        createLabel("Address:", marginLeft, marginTop + (rowHeight + spacingY) * 3, labelWidth, rowHeight);
        address = createTextField(fieldX, marginTop + (rowHeight + spacingY) * 3, fieldWidth, rowHeight);
        address.setEditable(false);
        address.setFocusable(false);

        createLabel("City:", marginLeft, marginTop + (rowHeight + spacingY) * 4, labelWidth, rowHeight);
        city = createTextField(fieldX, marginTop + (rowHeight + spacingY) * 4, fieldWidth, rowHeight);
        city.setEditable(false);
        city.setFocusable(false);

        createLabel("State:", marginLeft, marginTop + (rowHeight + spacingY) * 5, labelWidth, rowHeight);
        state = createChoice(fieldX, marginTop + (rowHeight + spacingY) * 5, fieldWidth, rowHeight);
        addStates(state);
        state.setEnabled(false);
        state.setFocusable(false);

        createLabel("Pin Code:", marginLeft, marginTop + (rowHeight + spacingY) * 6, labelWidth, rowHeight);
        pinCode = createTextField(fieldX, marginTop + (rowHeight + spacingY) * 6, fieldWidth, rowHeight);
        pinCode.setEditable(false);
        pinCode.setFocusable(false);

        createLabel("Mobile Number:", marginLeft, marginTop + (rowHeight + spacingY) * 7, labelWidth, rowHeight);
        mobileNumber = createTextField(fieldX, marginTop + (rowHeight + spacingY) * 7, fieldWidth, rowHeight);
        mobileNumber.setEditable(false);
        mobileNumber.setFocusable(false);

        paid = createButton("Pay", marginLeft, marginTop + (rowHeight + spacingY) * 8, fieldWidth / 4, rowHeight);
        paid.addActionListener(this);

        close = createButton("Close", marginLeft + fieldWidth / 4 + 10, marginTop + (rowHeight + spacingY) * 8, fieldWidth / 4, rowHeight);
        close.addActionListener(this);

        int imageSize = Math.min(width - (fieldX + fieldWidth + 100), height / 2);
        int imageX = fieldX + fieldWidth + ((width - (fieldX + fieldWidth)) - imageSize) / 2;
        int imageY = (height - imageSize) / 2;

        ImageIcon image = new ImageIcon(ClassLoader.getSystemResource("images/payBill.png"));
        Image imageScale = image.getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH);
        ImageIcon imageFinal = new ImageIcon(imageScale);
        JLabel imageLabel = new JLabel(imageFinal);
        imageLabel.setBounds(imageX, imageY, imageSize, imageSize);
        add(imageLabel);

        JLabel heading = new JLabel(title);
        heading.setFont(new Font("Arial", Font.BOLD, 30));
        FontMetrics fm = heading.getFontMetrics(heading.getFont());
        int textWidth = fm.stringWidth(heading.getText());
        int x = (width - textWidth) / 2;
        heading.setBounds(x, 30, textWidth, 30);
        add(heading);

        JRootPane rootPane = getRootPane();
        KeyStroke escKey = KeyStroke.getKeyStroke("ESCAPE");
        rootPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(escKey, "exitApp");

        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                showExitDialog(payBill.this);
            }
        });

        rootPane.getActionMap().put("exitApp", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showExitDialog(payBill.this);
            }
        });

        setSize(width, height);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(null);
        setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == open) {
            Border redBorder = BorderFactory.createLineBorder(Color.RED, 2);
            Border greenBorder = BorderFactory.createLineBorder(Color.green, 2);
            Border normalBorder = BorderFactory.createLineBorder(Color.GRAY, 1);

            boolean isCorrect = true;

            String accountIdString = accountId.getText();

            if (accountIdString.equals("Enter Account Id")) {
                accountId.setBorder(redBorder);
                isCorrect = false;
            } else {
                accountId.setBorder(normalBorder);
            }

            if (!isCorrect) {
                JOptionPane.showMessageDialog(null, "Please fill teh account id correctly.");
                return;
            }

            try {

                String query = "SELECT * from customer where account_id = '" + accountIdString + "' ";
                ResultSet rs0 = Database.getStatement().executeQuery(query);
                if (rs0.next()) {
                    accountId.setBorder(greenBorder);
                    name.setText(rs0.getString("name"));
                    address.setText(rs0.getString("address"));
                    city.setText(rs0.getString("city"));
                    pinCode.setText(rs0.getString("pin_code"));
                    mobileNumber.setText(rs0.getString("mobile_number"));
                    state.select(rs0.getString("state"));

                    double amount = 0;
                    String amountInString = "";
                    String billCheck = "";
                    String billQuery = "SELECT * from transaction where account_id = '" + accountIdString + "' and payment_status = 'NO' ";
                    ResultSet rs1 = Database.getStatement().executeQuery(billQuery);
                    if (rs1.next()) {
                        billNumbers.setText(rs1.getString("bill_number"));
                        totalBilled.setText(rs1.getString("amount"));
                    } else {
                        JOptionPane.showMessageDialog(this, "No Pending bill found for the entered Account ID.", "Not Found", JOptionPane.INFORMATION_MESSAGE);
                        name.setText("");
                        address.setText("");
                        city.setText("");
                        pinCode.setText("");
                        mobileNumber.setText("");
                        state.select(0);
                        totalBilled.setText("");
                        billNumbers.setText("");
                    }

                } else {
                    addSuggestionText(accountId, "Account ID not found");
                    name.setText("");
                    address.setText("");
                    city.setText("");
                    pinCode.setText("");
                    mobileNumber.setText("");
                    state.select(0);
                    accountId.setBorder(redBorder);
                    totalBilled.setText("");
                    billNumbers.setText("");
                    JOptionPane.showMessageDialog(this, "No data found for the entered Account ID.", "Not Found", JOptionPane.INFORMATION_MESSAGE);
                }

            } catch (Exception ECheck) {
                ECheck.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error fetching data! Database connection.", "Error", JOptionPane.ERROR_MESSAGE);
            }

        }

        if (e.getSource() == paid) {
            boolean isCorrect = true;
            if (totalBilled.getText().equals("")) {
                isCorrect = false;
                JOptionPane.showMessageDialog(this, "Please account id and click open.");
                return;
            }
            String query = "UPDATE transaction SET payment_status = 'YES', date_of_payment = NOW() WHERE account_id = '" + accountId.getText() + "' AND payment_status = 'NO'";
            try {
                Database.getStatement().executeUpdate(query);
                JOptionPane.showMessageDialog(this, "Bill Paid Successfully.", "Not Found", JOptionPane.INFORMATION_MESSAGE);
                name.setText("");
                address.setText("");
                city.setText("");
                pinCode.setText("");
                mobileNumber.setText("");
                state.select(0);
                totalBilled.setText("");
                billNumbers.setText("");
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }

        }

        if (e.getSource() == close) {
            showExitDialog(payBill.this);
        }

    }

    public void createLabel(String labelName, int x, int y, int width, int height) {
        JLabel label = new JLabel(labelName);
        label.setBounds(x, y, width, height);
        label.setFont(new Font("Arial", Font.PLAIN, 20));
        add(label);
    }

    public JTextField createTextField(int x, int y, int width, int height) {
        JTextField field = new JTextField();
        field.setBounds(x, y, width, height);
        field.setFont(new Font("Arial", Font.PLAIN, 20));
        add(field);
        return field;
    }

    private static void showExitDialog(JFrame frame) {
        int option = JOptionPane.showConfirmDialog(frame, "Are you sure you want to exit?",
                "Confirm Exit", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

        if (option == JOptionPane.YES_OPTION) {
            frame.dispose();
        }
    }

    public static void addSuggestionText(JTextField field, String text) {
        field.setText(text);
        field.setForeground(new Color(153, 153, 153));
        field.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (field.getText().equals(text)) {
                    field.setText("");
                    field.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (field.getText().equals("")) {
                    field.setText(text);
                    field.setForeground(new Color(153, 153, 153));
                }
            }
        });
    }

    public JButton createButton(String name, int x, int y, int width, int height) {
        JButton button = new JButton(name);
        button.setBounds(x, y, width, height);
        button.setFont(new Font("Arial", Font.PLAIN, 20));
        add(button);
        return button;
    }

    public Choice createChoice(int x, int y, int width, int height) {
        Choice choice = new Choice();
        choice.setBounds(x, y, width, height);
        choice.setFont(new Font("Arial", Font.PLAIN, 20));
        add(choice);
        return choice;
    }

    public void addStates(Choice stateChoice) {
        String[] states = {
                "", "Andhra Pradesh", "Arunachal Pradesh", "Assam", "Bihar", "Chhattisgarh",
                "Goa", "Gujarat", "Haryana", "Himachal Pradesh", "Jharkhand",
                "Karnataka", "Kerala", "Madhya Pradesh", "Maharashtra", "Manipur",
                "Meghalaya", "Mizoram", "Nagaland", "Odisha", "Punjab",
                "Rajasthan", "Sikkim", "Tamil Nadu", "Telangana", "Tripura",
                "Uttar Pradesh", "Uttarakhand", "West Bengal"
        };
        for (String s : states) {
            stateChoice.add(s);
        }
    }

    public static void main(String[] args) {
        new payBill();
    }
}
