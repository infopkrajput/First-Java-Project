import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class generateBill extends JFrame implements ActionListener {

    JTextField name, address, city, pinCode, accountId, mobileNumber, totalConsumedUnits, connectionType, totalBilled, billNumber;
    JButton check, createBill, close;
    Choice state;

    public generateBill() {
        String title = "Generate Bill";
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) (screenSize.width * 0.95);
        int height = (int) (screenSize.height * 0.95);

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

        check = createButton("Check", fieldX + fieldWidth - (fieldWidth / 2) + 50, marginTop, fieldWidth / 4, rowHeight);
        check.addActionListener(this);

        createLabel("Units Consumed: ", marginLeft, marginTop + (rowHeight + spacingY), labelWidth, rowHeight);
        totalConsumedUnits = createTextField(fieldX, marginTop + (rowHeight + spacingY), fieldWidth / 2, rowHeight);
        addSuggestionText(totalConsumedUnits, "Enter Total units");

        createLabel("Connection type: ", fieldX + (fieldWidth / 2) + 5, marginTop + (rowHeight + spacingY), (fieldWidth / 2) - (fieldWidth / 4) - 5, rowHeight);
        connectionType = createTextField(fieldX + (fieldWidth / 2) + (fieldWidth / 4), marginTop + (rowHeight + spacingY), (fieldWidth / 2) - (fieldWidth / 4), rowHeight);
        connectionType.setEditable(false);
        connectionType.setFocusable(false);

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

        createBill = createButton("Create", marginLeft, marginTop + (rowHeight + spacingY) * 8, fieldWidth / 4, rowHeight);
        createBill.addActionListener(this);

        close = createButton("Close", marginLeft + fieldWidth / 4 + 10, marginTop + (rowHeight + spacingY) * 8, fieldWidth / 4, rowHeight);
        close.addActionListener(this);

        createLabel("Total Bill Amount:", fieldX, marginTop + (rowHeight + spacingY) * 8, fieldWidth / 4, rowHeight);
        totalBilled = createTextField(fieldX + fieldWidth / 4 + 10, marginTop + (rowHeight + spacingY) * 8, fieldWidth / 4, rowHeight);
        totalBilled.setEditable(false);
        totalBilled.setFocusable(false);

        createLabel("Bill Number:", fieldX + fieldWidth / 2 + 20, marginTop + (rowHeight + spacingY) * 8, fieldWidth / 4 - 30, rowHeight);
        billNumber = createTextField(fieldX + fieldWidth / 2 + fieldWidth / 4 - 30, marginTop + (rowHeight + spacingY) * 8, fieldWidth / 4 + 30, rowHeight);
        billNumber.setEditable(false);
        billNumber.setFocusable(false);

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
                showExitDialog(generateBill.this);
            }
        });

        rootPane.getActionMap().put("exitApp", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showExitDialog(generateBill.this);
            }
        });

        ImageIcon image = new ImageIcon(ClassLoader.getSystemResource("images/generateBill.png"));
        Image imageScale = image.getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH);
        ImageIcon imageFinal = new ImageIcon(imageScale);
        JLabel imageLabel = new JLabel(imageFinal);
        imageLabel.setBounds(fieldX + fieldWidth + 100, (height - 300) / 2, 300, 300);
        add(imageLabel);

        setSize(width, height);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(null);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == check) {
            checkAccountId();
        } else if (e.getSource() == createBill) {
            createBill();
        } else if (e.getSource() == close) {
            showExitDialog(this);
        }
    }

    private void checkAccountId() {
        Border redBorder = BorderFactory.createLineBorder(Color.RED, 2);
        Border greenBorder = BorderFactory.createLineBorder(Color.GREEN, 2);
        Border normalBorder = BorderFactory.createLineBorder(Color.GRAY, 1);

        billNumber.setText("");
        totalBilled.setText("");
        String accountIdString = accountId.getText();

        if (accountIdString.isEmpty() || accountIdString.equals("Enter Account Id")) {
            accountId.setBorder(redBorder);
            JOptionPane.showMessageDialog(null, "Please fill the account id correctly.");
            return;
        } else {
            accountId.setBorder(normalBorder);
        }

        String query = "SELECT * FROM customer WHERE account_id = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, accountIdString);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    accountId.setBorder(greenBorder);
                    connectionType.setText(rs.getString("connection_type"));
                    name.setText(rs.getString("name"));
                    address.setText(rs.getString("address"));
                    city.setText(rs.getString("city"));
                    pinCode.setText(rs.getString("pin_code"));
                    mobileNumber.setText(rs.getString("mobile_number"));
                    state.select(rs.getString("state"));
                } else {
                    addSuggestionText(accountId, "Account ID not found");
                    connectionType.setText("");
                    name.setText("");
                    address.setText("");
                    city.setText("");
                    pinCode.setText("");
                    mobileNumber.setText("");
                    state.select(0);

                    accountId.setBorder(redBorder);
                    JOptionPane.showMessageDialog(this, "No data found for the entered Account ID.", "Not Found", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error fetching data! Database connection.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void createBill() {
        Border redBorder = BorderFactory.createLineBorder(Color.RED, 2);
        Border normalBorder = BorderFactory.createLineBorder(Color.GRAY, 1);

        String accountIdString = accountId.getText();
        String totalConsumedUnitsString = totalConsumedUnits.getText();
        String connectionTypeString = connectionType.getText();

        if (totalConsumedUnitsString.isEmpty() || totalConsumedUnitsString.equals("Enter Total units")) {
            totalConsumedUnits.setBorder(redBorder);
            JOptionPane.showMessageDialog(null, "Please enter the account id and click on check.\nThen enter total unit.");
            return;
        } else {
            totalConsumedUnits.setBorder(normalBorder);
        }

        double totalConsumedUnitsDouble;
        try {
            totalConsumedUnitsDouble = Double.parseDouble(totalConsumedUnitsString);
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Total units must be a number.");
            return;
        }

        if (accountIdString.isEmpty() || accountIdString.equals("Enter Account Id")) {
            accountId.setBorder(redBorder);
            JOptionPane.showMessageDialog(null, "Please enter the account id and click on check.");
            return;
        } else {
            accountId.setBorder(normalBorder);
        }

        double rate_per_unit, total_amount;

        if ("Individual".equalsIgnoreCase(connectionTypeString)) {
            rate_per_unit = 5.5;
        } else if ("Corporate".equalsIgnoreCase(connectionTypeString)) {
            rate_per_unit = 8.5;
        } else {
            JOptionPane.showMessageDialog(null, "Unknown connection type.");
            return;
        }

        total_amount = totalConsumedUnitsDouble * rate_per_unit;

        try (Connection conn = Database.getConnection()) {
            // Carry forward previous unpaid amount
            double carryForwardAmount = 0;
            String checkBill = "SELECT amount FROM transactions WHERE account_id = ? AND payment_status = 'NO'";
            try (PreparedStatement checkStmt = conn.prepareStatement(checkBill)) {
                checkStmt.setString(1, accountIdString);
                try (ResultSet rs = checkStmt.executeQuery()) {
                    if (rs.next()) {
                        carryForwardAmount = rs.getDouble("amount");
                    }
                }
            }

            // Update old bill status to 'CF'
            String updatePreviousBillQuery = "UPDATE transactions SET payment_status = 'CF' WHERE account_id = ? AND payment_status = 'NO'";
            try (PreparedStatement updateStmt = conn.prepareStatement(updatePreviousBillQuery)) {
                updateStmt.setString(1, accountIdString);
                updateStmt.executeUpdate();
            }

            // Add carry forward amount
            total_amount += carryForwardAmount;

            // Generate new bill number
            String billQuery = "SELECT COALESCE(MAX(CAST(SUBSTR(bill_number, -8) AS INTEGER)), 0) AS bill_number_max FROM transactions";
            int newBillNumber = 1;
            try (PreparedStatement billStmt = conn.prepareStatement(billQuery);
                 ResultSet rs0 = billStmt.executeQuery()) {
                if (rs0.next()) {
                    newBillNumber = rs0.getInt("bill_number_max") + 1;
                }
            }

            String billNumberString = "T" + String.format("%08d", newBillNumber);

            // Insert new bill record
            String insertQuery = "INSERT INTO transactions (account_id, date_of_transaction, unit, rate_per_unit, amount, bill_number, payment_status) " +
                    "VALUES (?, DATE('now'), ?, ?, ?, ?, 'NO')";
            try (PreparedStatement insertStmt = conn.prepareStatement(insertQuery)) {
                insertStmt.setString(1, accountIdString);
                insertStmt.setDouble(2, totalConsumedUnitsDouble);
                insertStmt.setDouble(3, rate_per_unit);
                insertStmt.setDouble(4, total_amount);
                insertStmt.setString(5, billNumberString);
                insertStmt.executeUpdate();
            }

            // Reset fields and display results
            totalConsumedUnits.setText("");
            accountId.setText("");
            connectionType.setText("");
            name.setText("");
            address.setText("");
            city.setText("");
            pinCode.setText("");
            mobileNumber.setText("");
            state.select(0);

            totalBilled.setText(String.format("%.2f", total_amount));
            billNumber.setText(billNumberString);

            JOptionPane.showMessageDialog(null, "Bill Generated Successfully! Of ₹" + String.format("%.2f", total_amount));

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error generating bill!", "Database Error", JOptionPane.ERROR_MESSAGE);
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
        payBill.addSuggestionText(field, text);
    }

    public Choice createChoice(int x, int y, int width, int height) {
        Choice choice = new Choice();
        choice.setBounds(x, y, width, height);
        choice.setFont(new Font("Arial", Font.PLAIN, 20));
        add(choice);
        return choice;
    }

    public JButton createButton(String name, int x, int y, int width, int height) {
        JButton button = new JButton(name);
        button.setBounds(x, y, width, height);
        button.setFont(new Font("Arial", Font.PLAIN, 20));
        add(button);
        return button;
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
        new generateBill();
    }
}
