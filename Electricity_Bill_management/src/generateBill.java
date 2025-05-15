import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class generateBill extends JFrame implements ActionListener {

    JTextField name, address, city, pinCode, accountId, mobileNumber, totalConsumedUnits, connectionType, totalBilled, billNumber;
    JButton check, createBill, close;
    Choice state;

    generateBill() {
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

        int imageSize = Math.min(width - (fieldX + fieldWidth + 100), height / 2);
        int imageX = fieldX + fieldWidth + ((width - (fieldX + fieldWidth)) - imageSize) / 2;
        int imageY = (height - imageSize) / 2;

        ImageIcon image = new ImageIcon(ClassLoader.getSystemResource("images/generateBill.png"));
        Image imageScale = image.getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH);
        ImageIcon imageFinal = new ImageIcon(imageScale);
        JLabel imageLabel = new JLabel(imageFinal);
        imageLabel.setBounds(imageX, imageY, imageSize, imageSize);
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
            Border redBorder = BorderFactory.createLineBorder(Color.RED, 2);
            Border greenBorder = BorderFactory.createLineBorder(Color.green, 2);
            Border normalBorder = BorderFactory.createLineBorder(Color.GRAY, 1);

            billNumber.setText("");
            totalBilled.setText("");
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
                String query = "select * from customer where account_id = '" + accountIdString + "' ";
                ResultSet rs0 = Database.getStatement().executeQuery(query);
                if (rs0.next()) {
                    accountId.setBorder(greenBorder);
                    connectionType.setText(rs0.getString("connection_type"));
                    name.setText(rs0.getString("name"));
                    address.setText(rs0.getString("address"));
                    city.setText(rs0.getString("city"));
                    pinCode.setText(rs0.getString("pin_code"));
                    mobileNumber.setText(rs0.getString("mobile_number"));
                    state.select(rs0.getString("state"));

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


            } catch (Exception ECheck) {
                ECheck.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error fetching data! Database connection.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        if (e.getSource() == createBill) {
            Border redBorder = BorderFactory.createLineBorder(Color.RED, 2);
            Border greenBorder = BorderFactory.createLineBorder(Color.green, 2);
            Border normalBorder = BorderFactory.createLineBorder(Color.GRAY, 1);

            String accountIdString = accountId.getText();
            String totalConsumedUnitsString = totalConsumedUnits.getText();
            String connectionTypeString = connectionType.getText();
            String billNumberString = "";

            double rate_per_unit = 0, total_amount = 0;
            double totalConsumedUnitsDouble = 0;
            boolean isCorrect = true;


            if (totalConsumedUnitsString.equals("Enter Total units")) {
                totalConsumedUnits.setBorder(redBorder);
                accountId.setBorder(redBorder);
                isCorrect = false;
                JOptionPane.showMessageDialog(null, "Please Enter the account id and click on check.\nThen enter total unit.");
                return;
            } else {
                totalConsumedUnits.setBorder(normalBorder);
            }

            try {
                totalConsumedUnitsDouble = Double.parseDouble(totalConsumedUnitsString);
            } catch (NumberFormatException ed) {
                ed.printStackTrace();
            }

            if (accountIdString.equals("Enter Account Id")) {
                accountId.setBorder(redBorder);
                isCorrect = false;
                JOptionPane.showMessageDialog(null, "Please Enter the account id and click on check.");
                return;
            } else {
                accountId.setBorder(normalBorder);
            }


            if (!isCorrect) {
                JOptionPane.showMessageDialog(null, "Please fill all fields correctly.");
                return;
            }

            if (connectionTypeString.equals("Individual")) {
                rate_per_unit = 5.5;
                total_amount = totalConsumedUnitsDouble * rate_per_unit;
            } else if (connectionTypeString.equals("Corporate")) {
                rate_per_unit = 8.5;
                total_amount = totalConsumedUnitsDouble * rate_per_unit;
            }

            try {
                double carryForwardAmount = 0;
                String checkBill = "SELECT amount FROM transaction WHERE account_id = '" + accountIdString + "' AND payment_status = 'NO'";
                ResultSet rs = Database.getStatement().executeQuery(checkBill);
                if (rs != null && rs.next()) {
                    carryForwardAmount = Double.parseDouble(rs.getString("amount"));
                }

                String updatePreviousBillQuery = "UPDATE transaction SET payment_status = 'CF' WHERE account_id = '" + accountIdString + "' AND payment_status = 'NO'";
                Database.getStatement().executeUpdate(updatePreviousBillQuery);

                total_amount += carryForwardAmount;

                String billQuery = "SELECT COALESCE(MAX(RIGHT(bill_number, 8)),'00000000') AS bill_number_max FROM transaction";
                ResultSet rs0 = Database.getStatement().executeQuery(billQuery);
                int newBillNumber = 1;
                if (rs0.next()) {
                    String maxBillNumber = rs0.getString("bill_number_max");
                    if (maxBillNumber != null) {
                        newBillNumber = Integer.parseInt(maxBillNumber) + 1;
                    }
                }

                String billNumberStringGenerated = String.format("%08d", newBillNumber);
                String seriesOfBill = "T";
                billNumberString = seriesOfBill + billNumberStringGenerated;

                String query = "INSERT INTO transaction (account_id, date_of_transaction, unit, rate_per_unit, amount, bill_number, payment_status) " +
                        "VALUES('" + accountIdString + "',NOW(),'" + totalConsumedUnitsDouble + "','" + rate_per_unit + "','" + total_amount + "','" + billNumberString + "','NO')";
                Database.getStatement().executeUpdate(query);

                totalConsumedUnits.setText("");
                accountId.setText("");
                connectionType.setText("");
                name.setText("");
                address.setText("");
                city.setText("");
                pinCode.setText("");
                mobileNumber.setText("");
                state.select(0);

                totalBilled.setText(String.valueOf(total_amount));
                billNumber.setText(billNumberString);

                JOptionPane.showMessageDialog(null, "Bill Generated Successfully! of " + total_amount);

            } catch (SQLException ex) {
                ex.printStackTrace();
            }

        }
        if (e.getSource() == close) {
            showExitDialog(generateBill.this);
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

    public static boolean isOnlyDigits(String str) {
        try {
            Long.parseLong(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
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
