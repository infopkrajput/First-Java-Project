import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Connection;

public class editCustomer extends JFrame implements ActionListener {

    Choice state, idProofType;
    JTextField name, address, city, pinCode, idProofNumber, meterNumberField, accountId, mobileNumber, dateToday;
    JButton submit, check, back;

    editCustomer() {
        String title = "Modify Customer";
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) (screenSize.width * 0.75);
        int height = (int) (screenSize.height * 0.75);

        int marginLeft = (int) (width * 0.05);
        int marginTop = 100;
        int rowHeight = 30;
        int spacingY = 20;

        int labelWidth = (int) (width * 0.2);
        int fieldWidth = (int) (width * 0.35);

        int fieldX = marginLeft + labelWidth + 20;

        createLabel("Account ID:", marginLeft, marginTop, labelWidth, rowHeight);
        accountId = createTextField(fieldX, marginTop, fieldWidth - (fieldWidth / 2), rowHeight);
        accountId.setText("Enter Customer account Id here");
        accountId.setForeground(new Color(153, 153, 153));
        addSuggestionText(accountId, "Enter Customer account id");

        check = createButton("Check", fieldX + fieldWidth - (fieldWidth / 2) + 50, marginTop, fieldWidth / 3, rowHeight);
        check.addActionListener(this);

        createLabel("Customer name:", marginLeft, marginTop + (rowHeight + spacingY), labelWidth, rowHeight);
        name = createTextField(fieldX, marginTop + (rowHeight + spacingY), fieldWidth, rowHeight);

        createLabel("Address:", marginLeft, marginTop + (rowHeight + spacingY) * 2, labelWidth, rowHeight);
        address = createTextField(fieldX, marginTop + (rowHeight + spacingY) * 2, fieldWidth, rowHeight);

        createLabel("City:", marginLeft, marginTop + (rowHeight + spacingY) * 3, labelWidth, rowHeight);
        city = createTextField(fieldX, marginTop + (rowHeight + spacingY) * 3, fieldWidth, rowHeight);

        createLabel("State:", marginLeft, marginTop + (rowHeight + spacingY) * 4, labelWidth, rowHeight);
        state = createChoice(fieldX, marginTop + (rowHeight + spacingY) * 4, fieldWidth, rowHeight);
        addStates(state);

        createLabel("Pin Code:", marginLeft, marginTop + (rowHeight + spacingY) * 5, labelWidth, rowHeight);
        pinCode = createTextField(fieldX, marginTop + (rowHeight + spacingY) * 5, fieldWidth, rowHeight);

        createLabel("Mobile Number:", marginLeft, marginTop + (rowHeight + spacingY) * 6, labelWidth, rowHeight);
        mobileNumber = createTextField(fieldX, marginTop + (rowHeight + spacingY) * 6, fieldWidth, rowHeight);

        createLabel("Id Proof Type:", marginLeft, marginTop + (rowHeight + spacingY) * 7, labelWidth, rowHeight);
        idProofType = createChoice(fieldX, marginTop + (rowHeight + spacingY) * 7, fieldWidth, rowHeight);
        addIdProofType(idProofType);

        createLabel("Id Proof No.:", marginLeft, marginTop + (rowHeight + spacingY) * 8, labelWidth, rowHeight);
        idProofNumber = createTextField(fieldX, marginTop + (rowHeight + spacingY) * 8, fieldWidth, rowHeight);

        createLabel("Meter Number:", marginLeft, marginTop + (rowHeight + spacingY) * 9, labelWidth, rowHeight);
        meterNumberField = createTextField(fieldX, marginTop + (rowHeight + spacingY) * 9, fieldWidth / 2, rowHeight);


        createLabel("Issue Date: ", fieldX + (fieldWidth / 2) + 5, marginTop + (rowHeight + spacingY) * 9, (fieldWidth / 2) - (fieldWidth / 4) - 5, rowHeight);
        dateToday = createTextField(fieldX + (fieldWidth / 2) + (fieldWidth / 4), marginTop + (rowHeight + spacingY) * 9, (fieldWidth / 2) - (fieldWidth / 4), rowHeight);
        dateToday.setEditable(false);


        submit = createButton("Submit", marginLeft + 100, marginTop + (rowHeight + spacingY) * 10, fieldWidth / 3, rowHeight);
        submit.addActionListener(this);
        back = createButton("Back", marginLeft + 100 + (fieldWidth / 3 + fieldWidth / 3 + 60), marginTop + (rowHeight + spacingY) * 10, fieldWidth / 3, rowHeight);
        back.addActionListener(this);

        name.setEditable(false);
        address.setEditable(false);
        city.setEditable(false);
        pinCode.setEditable(false);
        state.setEnabled(false);
        mobileNumber.setEditable(false);
        idProofType.setEnabled(false);
        meterNumberField.setEditable(false);
        idProofNumber.setEditable(false);
        dateToday.setEditable(false);

        JLabel heading = new JLabel(title);
        heading.setFont(new Font("Arial", Font.BOLD, 30));
        FontMetrics fm = heading.getFontMetrics(heading.getFont());
        int textWidth = fm.stringWidth(heading.getText());
        int x = (width - textWidth) / 2;
        heading.setBounds(x, 30, textWidth, 30);
        add(heading);

        int imageSize = Math.min(width - (fieldX + fieldWidth + 100), height / 2);
        int imageX = fieldX + fieldWidth + ((width - (fieldX + fieldWidth)) - imageSize) / 2;
        int imageY = (height - imageSize) / 2;

        ImageIcon image = new ImageIcon(ClassLoader.getSystemResource("images/editCustomerPage.png"));
        Image imageScale = image.getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH);
        ImageIcon imageFinal = new ImageIcon(imageScale);
        JLabel imageLabel = new JLabel(imageFinal);
        imageLabel.setBounds(imageX, imageY, imageSize, imageSize);
        add(imageLabel);

        JRootPane rootPane = getRootPane();
        KeyStroke escKey = KeyStroke.getKeyStroke("ESCAPE");
        rootPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(escKey, "exitApp");

        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                showExitDialog(editCustomer.this);
            }
        });

        rootPane.getActionMap().put("exitApp", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showExitDialog(editCustomer.this);
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
        if (e.getSource() == back) {
            showExitDialog(editCustomer.this);
        } else if (e.getSource() == check) {
            Border redBorder = BorderFactory.createLineBorder(Color.RED, 2);
            Border greenBorder = BorderFactory.createLineBorder(Color.green, 2);
            String accountIdString = accountId.getText();

            String query = "SELECT * FROM customer WHERE account_id = ?";

            try (Connection conn = Database.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(query)) {

                pstmt.setString(1, accountIdString);

                try (ResultSet rs0 = pstmt.executeQuery()) {
                    if (rs0.next()) {
                        name.setEditable(true);
                        address.setEditable(true);
                        city.setEditable(true);
                        pinCode.setEditable(true);
                        state.setEnabled(true);
                        mobileNumber.setEditable(true);
                        idProofType.setEnabled(true);
                        meterNumberField.setEditable(true);
                        idProofNumber.setEditable(true);

                        accountId.setBorder(greenBorder);

                        name.setText(rs0.getString("name"));
                        address.setText(rs0.getString("address"));
                        city.setText(rs0.getString("city"));
                        pinCode.setText(rs0.getString("pin_code"));
                        mobileNumber.setText(rs0.getString("mobile_number"));
                        idProofNumber.setText(rs0.getString("id_proof_number"));
                        meterNumberField.setText(rs0.getString("meter_number"));
                        dateToday.setText(rs0.getString("date_of_issue"));

                        state.select(rs0.getString("state"));
                        idProofType.select(rs0.getString("id_proof_type"));
                    } else {
                        addSuggestionText(accountId, "Account ID not found");
                        accountId.setBorder(redBorder);
                        name.setText("");
                        address.setText("");
                        city.setText("");
                        pinCode.setText("");
                        mobileNumber.setText("");
                        idProofNumber.setText("");
                        meterNumberField.setText("");
                        state.select(0);
                        idProofType.select(0);

                        name.setEditable(false);
                        address.setEditable(false);
                        city.setEditable(false);
                        pinCode.setEditable(false);
                        state.setEnabled(false);
                        mobileNumber.setEditable(false);
                        idProofType.setEnabled(false);
                        meterNumberField.setEditable(false);
                        idProofNumber.setEditable(false);
                        JOptionPane.showMessageDialog(this, "No data found for the entered Account ID.", "Not Found", JOptionPane.INFORMATION_MESSAGE);
                    }
                }

            } catch (Exception ECheck) {
                ECheck.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error fetching data! Database connection.", "Error", JOptionPane.ERROR_MESSAGE);
            }


        } else if (e.getSource() == submit) {
            Border redBorder = BorderFactory.createLineBorder(Color.RED, 2);
            Border normalBorder = BorderFactory.createLineBorder(Color.GRAY, 1);
            String checkName = name.getText();
            if (checkName.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Enter correct account id", "Not Found", JOptionPane.INFORMATION_MESSAGE);
            } else {
                boolean isCorrect = true;
                String accountIdString = accountId.getText();
                String nameString = name.getText();
                String addressString = address.getText();
                String cityString = city.getText();
                String mobileNumberString = mobileNumber.getText();
                String pinCodeString = pinCode.getText();
                String idProofTypeString = idProofType.getSelectedItem();
                String idProofNumberString = idProofNumber.getText();
                String stateString = state.getSelectedItem();
                String meterNumberString = meterNumberField.getText();

                if (nameString.isEmpty()) {
                    addSuggestionText(name, "Customer name should not be empty");
                    name.setBorder(redBorder);
                    isCorrect = false;
                } else if (nameString.length() <= 3) {
                    addSuggestionText(name, "Enter correct name");
                    name.setBorder(redBorder);
                    isCorrect = false;
                } else {
                    name.setBorder(normalBorder);
                }

                if (addressString.isEmpty()) {
                    addSuggestionText(address, "Address should not be empty");
                    address.setBorder(redBorder);
                    isCorrect = false;
                } else {
                    address.setBorder(normalBorder);
                }

                if (cityString.isEmpty()) {
                    addSuggestionText(city, "City Should not be empty");
                    city.setBorder(redBorder);
                    isCorrect = false;
                } else {
                    city.setBorder(normalBorder);
                }

                if (pinCodeString.isEmpty()) {
                    addSuggestionText(pinCode, "Pin Code should not be empty");
                    pinCode.setBorder(redBorder);
                    isCorrect = false;
                } else if (pinCodeString.length() != 6 || !isOnlyDigits(pinCodeString)) {
                    addSuggestionText(pinCode, "Pin Code must be in 6 digits");
                    pinCode.setBorder(redBorder);
                    isCorrect = false;
                } else {
                    pinCode.setBorder(normalBorder);
                }

                if (idProofNumberString.isEmpty()) {
                    idProofNumber.setBorder(redBorder);
                    isCorrect = false;
                } else {
                    idProofNumber.setBorder(normalBorder);
                }
                if (mobileNumberString.isEmpty()) {
                    addSuggestionText(mobileNumber, "Mobile number should not empty");
                    mobileNumber.setBorder(redBorder);
                    isCorrect = false;
                } else if (mobileNumberString.length() == 10 && isOnlyDigits(mobileNumberString)) {
                    mobileNumber.setBorder(normalBorder);
                } else {
                    mobileNumber.setBorder(redBorder);
                    isCorrect = false;
                }
                if (meterNumberString.isEmpty()) {
                    addSuggestionText(meterNumberField, "Meter number number should not empty");
                    meterNumberField.setBorder(redBorder);
                    isCorrect = false;
                } else {
                    idProofNumber.setBorder(normalBorder);
                }

                if (!isCorrect) {
                    JOptionPane.showMessageDialog(null, "Please fill all fields correctly.");
                    return;
                }

                String modifyDetail = "UPDATE customer SET name = ?, address = ?, city = ?, pin_code = ?, mobile_number = ?, state = ?, id_proof_type = ?, id_proof_number = ?, meter_number = ? WHERE account_id = ?";

                try (Connection conn = Database.getConnection();
                     PreparedStatement pstmt = conn.prepareStatement(modifyDetail)) {

                    pstmt.setString(1, nameString);
                    pstmt.setString(2, addressString);
                    pstmt.setString(3, cityString);
                    pstmt.setString(4, pinCodeString);
                    pstmt.setString(5, mobileNumberString);
                    pstmt.setString(6, stateString);
                    pstmt.setString(7, idProofTypeString);
                    pstmt.setString(8, idProofNumberString);
                    pstmt.setString(9, meterNumberString);
                    pstmt.setString(10, accountIdString);

                    int rowsUpdated = pstmt.executeUpdate();

                    if (rowsUpdated > 0) {
                        System.out.println("Customer details updated successfully.");
                    } else {
                        System.out.println("No customer found with account ID: " + accountIdString);
                    }

                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }


                name.setText("");
                address.setText("");
                city.setText("");
                pinCode.setText("");
                mobileNumber.setText("");
                idProofNumber.setText("");
                meterNumberField.setText("");
                state.select(0);
                idProofType.select(0);

                name.setEditable(false);
                address.setEditable(false);
                city.setEditable(false);
                pinCode.setEditable(false);
                state.setEnabled(false);
                mobileNumber.setEditable(false);
                idProofType.setEnabled(false);
                meterNumberField.setEditable(false);
                idProofNumber.setEditable(false);

                JOptionPane.showMessageDialog(null, "Account Modified Successfully ");
            }
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
        int option = JOptionPane.showConfirmDialog(frame, "Are you sure you want to exit?", "Confirm Exit", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

        if (option == JOptionPane.YES_OPTION) {
            frame.dispose();
        }
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
        String[] states = {"", "Andhra Pradesh", "Arunachal Pradesh", "Assam", "Bihar", "Chhattisgarh", "Goa", "Gujarat", "Haryana", "Himachal Pradesh", "Jharkhand", "Karnataka", "Kerala", "Madhya Pradesh", "Maharashtra", "Manipur", "Meghalaya", "Mizoram", "Nagaland", "Odisha", "Punjab", "Rajasthan", "Sikkim", "Tamil Nadu", "Telangana", "Tripura", "Uttar Pradesh", "Uttarakhand", "West Bengal"};
        for (String s : states) {
            stateChoice.add(s);
        }
    }

    public void addIdProofType(Choice idProofTypeChoice) {
        String[] idProofTypes = {"", "Aadhaar Card", "PAN Card", "Voter ID Card", "Passport", "Driving License", "Government-issued Employee ID Card", "Ration Card (with photo)", "NREGA Job Card", "Senior Citizen ID Card", "Health Insurance Smart Card (issued by government)", "Arms License (with photograph)", "Pensioner Photo Card", "Bank Passbook (with photo, issued by PSU banks)", "Post Office ID Card", "Student ID Card (with photo)", "CGHS / ECHS Card", "Birth Certificate (for minors with parent's ID)"};
        for (String s : idProofTypes) {
            idProofTypeChoice.add(s);
        }
    }

    public static void addSuggestionText(JTextField field, String text) {
        payBill.addSuggestionText(field, text);
    }

    public static boolean isOnlyDigits(String str) {
        try {
            Long.parseLong(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static void main(String[] args) {
        new editCustomer();
    }
}
