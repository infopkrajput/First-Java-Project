import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Date;

public class newCustomer extends JFrame implements ActionListener {
    Choice state, idProofType, connectionType;
    JTextField name, address, city, pinCode, idProofNumber, meterNumberField, accountIdField, mobileNumber;
    JButton create, clear, back;

    newCustomer() {
        String title = "Add New Customer";
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

        createLabel("Customer name:", marginLeft, marginTop, labelWidth, rowHeight);
        name = createTextField(fieldX, marginTop, fieldWidth, rowHeight);
        name.setText("Enter Customer name here");
        name.setForeground(new Color(153, 153, 153));
        addSuggestionText(name, "Enter Customer name here");

        createLabel("Address:", marginLeft, marginTop + (rowHeight + spacingY), labelWidth, rowHeight);
        address = createTextField(fieldX, marginTop + (rowHeight + spacingY), fieldWidth, rowHeight);
        addSuggestionText(address, "Enter Address here");

        createLabel("City:", marginLeft, marginTop + (rowHeight + spacingY) * 2, labelWidth, rowHeight);
        city = createTextField(fieldX, marginTop + (rowHeight + spacingY) * 2, fieldWidth, rowHeight);
        addSuggestionText(city, "Enter City here");

        createLabel("State:", marginLeft, marginTop + (rowHeight + spacingY) * 3, labelWidth, rowHeight);
        state = createChoice(fieldX, marginTop + (rowHeight + spacingY) * 3, fieldWidth, rowHeight);
        addStates(state);

        createLabel("Pin Code:", marginLeft, marginTop + (rowHeight + spacingY) * 4, labelWidth, rowHeight);
        pinCode = createTextField(fieldX, marginTop + (rowHeight + spacingY) * 4, fieldWidth, rowHeight);
        addSuggestionText(pinCode, "Enter Pin Code here");

        createLabel("Mobile Number:", marginLeft, marginTop + (rowHeight + spacingY) * 5, labelWidth, rowHeight);
        mobileNumber = createTextField(fieldX, marginTop + (rowHeight + spacingY) * 5, fieldWidth / 2, rowHeight);
        addSuggestionText(mobileNumber, "Enter Mobile Number here");

        createLabel("Type:", fieldX + (fieldWidth / 2) + 5, marginTop + (rowHeight + spacingY) * 5, (fieldWidth / 2) - (fieldWidth / 3), rowHeight);
        connectionType = createChoice(fieldX + (fieldWidth / 2) + (fieldWidth / 4), marginTop + (rowHeight + spacingY) * 5, (fieldWidth / 2) - (fieldWidth / 4), rowHeight);
        addConnectionType(connectionType);

        createLabel("Id Proof Type:", marginLeft, marginTop + (rowHeight + spacingY) * 6, labelWidth, rowHeight);
        idProofType = createChoice(fieldX, marginTop + (rowHeight + spacingY) * 6, fieldWidth, rowHeight);
        addIdProofType(idProofType);

        createLabel("Id Proof No.:", marginLeft, marginTop + (rowHeight + spacingY) * 7, labelWidth, rowHeight);
        idProofNumber = createTextField(fieldX, marginTop + (rowHeight + spacingY) * 7, fieldWidth, rowHeight);
        addSuggestionText(idProofNumber, "Enter Id Proof Number here");


        create = createButton("Create", marginLeft + 50, marginTop + (rowHeight + spacingY) * 8, fieldWidth / 3, rowHeight);
        create.addActionListener(this);
        clear = createButton("Clear", marginLeft + 50 + (fieldWidth / 3 + 30), marginTop + (rowHeight + spacingY) * 8, fieldWidth / 3, rowHeight);
        clear.addActionListener(this);
        back = createButton("Back", marginLeft + 50 + (fieldWidth / 3 + fieldWidth / 3 + 60), marginTop + (rowHeight + spacingY) * 8, fieldWidth / 3, rowHeight);
        back.addActionListener(this);

        createLabel("Meter Number:", marginLeft, marginTop + (rowHeight + spacingY) * 9, labelWidth, rowHeight);
        meterNumberField = createTextField(fieldX, marginTop + (rowHeight + spacingY) * 9, fieldWidth, rowHeight);
        meterNumberField.setEditable(false);

        createLabel("Account ID:", marginLeft, marginTop + (rowHeight + spacingY) * 10, labelWidth, rowHeight);
        accountIdField = createTextField(fieldX, marginTop + (rowHeight + spacingY) * 10, fieldWidth, rowHeight);
        accountIdField.setEditable(false);

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

        ImageIcon image = new ImageIcon(ClassLoader.getSystemResource("images/newCustomer.png"));
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
                showExitDialog(newCustomer.this);
            }
        });

        rootPane.getActionMap().put("exitApp", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showExitDialog(newCustomer.this);
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
            showExitDialog(newCustomer.this);
        } else if (e.getSource() == create) {
            Border redBorder = BorderFactory.createLineBorder(Color.RED, 2);
            Border normalBorder = BorderFactory.createLineBorder(Color.GRAY, 1);
            boolean isCorrect = true;
            String nameString = name.getText();
            String addressString = address.getText();
            String cityString = city.getText();
            String mobileNumberString = mobileNumber.getText();
            String pinCodeString = pinCode.getText();
            String idProofTypeString = idProofType.getSelectedItem();
            String idProofNumberString = idProofNumber.getText();
            String stateString = state.getSelectedItem();
            String connectionTypeString = connectionType.getSelectedItem();

            java.sql.Date tdate = new java.sql.Date(System.currentTimeMillis());

            if (connectionTypeString.equals("")) {
                isCorrect = false;
            }

            if (stateString.equals("")) {
                isCorrect = false;
            }

            if (idProofTypeString.equals("")) {
                isCorrect = false;
            }

            if (nameString.equals("Enter Customer name here")) {
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

            if (addressString.equals("Enter Address here")) {
                addSuggestionText(address, "Address should not be empty");
                address.setBorder(redBorder);
                isCorrect = false;
            } else {
                address.setBorder(normalBorder);
            }

            if (cityString.equals("Enter City here")) {
                addSuggestionText(city, "City Should not be empty");
                city.setBorder(redBorder);
                isCorrect = false;
            } else {
                city.setBorder(normalBorder);
            }

            if (pinCodeString.equals("Enter Pin Code here")) {
                addSuggestionText(pinCode, "Pin Code should not be empty");
                pinCode.setBorder(redBorder);
                isCorrect = false;
            } else if (pinCodeString.length() > 6 || pinCodeString.length() < 6 || !isOnlyDigits(pinCodeString)) {
                addSuggestionText(pinCode, "Pin Code must be in 6 digits");
                pinCode.setBorder(redBorder);
                isCorrect = false;
            } else {
                pinCode.setBorder(normalBorder);
            }

            if (idProofNumberString.equals("Enter Id Proof Number here")) {
                idProofNumber.setBorder(redBorder);
                isCorrect = false;
            } else {
                idProofNumber.setBorder(normalBorder);
            }
            if (mobileNumberString.equals("Enter Mobile Number here")) {
                addSuggestionText(mobileNumber, "Mobile number should not empty");
                mobileNumber.setBorder(redBorder);
                isCorrect = false;
            } else if (mobileNumberString.length() == 10 && isOnlyDigits(mobileNumberString)) {
                mobileNumber.setBorder(normalBorder);
            } else {
                mobileNumber.setBorder(redBorder);
                isCorrect = false;
            }

            if (!isCorrect) {
                JOptionPane.showMessageDialog(null, "Please fill all fields correctly.");
                return;
            }

            try {
//                String previousMeterNumberVerify = "SELECT COALESCE(MAX(RIGHT(meter_number, 6)),'000000') AS max_last_six_digits FROM customer";
                String previousMeterNumberVerify = "SELECT COALESCE(MAX(CAST(SUBSTR(meter_number, -6) AS INTEGER)), 0) AS max_last_six_digits FROM customer";

                ResultSet rs0 = Database.getStatement().executeQuery(previousMeterNumberVerify);
                int newMeterNumber = 1;
                if (rs0.next()) {
                    String maxLastSixDigits = rs0.getString("max_last_six_digits");
                    if (maxLastSixDigits != null) {
                        newMeterNumber = Integer.parseInt(maxLastSixDigits) + 1;
                    }
                }

                String meterNumberString = String.format("%06d", newMeterNumber);
                String yearMonth = java.time.LocalDate.now().format(java.time.format.DateTimeFormatter.ofPattern("yyyyMM"));
                String meterNumberIncludingYearAndMonth = yearMonth + meterNumberString;

//                String accountIdVerify = "SELECT COALESCE(MAX(account_id), '00000000') AS max_account_id FROM customer";
                String accountIdVerify = "SELECT COALESCE(MAX(CAST(account_id AS INTEGER)), 0) AS max_account_id FROM customer";

                ResultSet rs = Database.getStatement().executeQuery(accountIdVerify);
                int newAccountId = 1; // default first account id

                if (rs.next()) {
                    String maxAccountId = rs.getString("max_account_id");
                    if (maxAccountId != null) {
                        // Convert to integer and add 1
                        newAccountId = Integer.parseInt(maxAccountId) + 1;
                    }
                }
                // Format newAccountId to 8 digits with leading zeros
                String accountIdString = String.format("%08d", newAccountId);

//                String insertCustomerQuery = "INSERT INTO customer (account_id, name, address, city, pin_code, mobile_number,connection_type, state, id_proof_type, id_proof_number, meter_number,date_of_issue)\n" + "VALUES ('" + accountIdString + "', '" + nameString + "', '" + addressString + "', '" + cityString + "', '" + pinCodeString + "', '" + mobileNumberString + "','" + connectionTypeString + "', '" + stateString + "', '" + idProofTypeString + "', '" + idProofNumberString + "', '" + meterNumberIncludingYearAndMonth + "','" + tdate.toString() + "')";
//                Database.getStatement().executeUpdate(insertCustomerQuery);
                String insertCustomerQuery = "INSERT INTO customer (account_id, name, address, city, pin_code, mobile_number, connection_type, state, id_proof_type, id_proof_number, meter_number, date_of_issue) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement pstmt = Database.getConnection().prepareStatement(insertCustomerQuery);
                pstmt.setString(1, accountIdString);
                pstmt.setString(2, nameString);
                pstmt.setString(3, addressString);
                pstmt.setString(4, cityString);
                pstmt.setString(5, pinCodeString);
                pstmt.setString(6, mobileNumberString);
                pstmt.setString(7, connectionTypeString);
                pstmt.setString(8, stateString);
                pstmt.setString(9, idProofTypeString);
                pstmt.setString(10, idProofNumberString);
                pstmt.setString(11, meterNumberIncludingYearAndMonth);
                pstmt.setString(12, tdate.toString());
                pstmt.executeUpdate();

                JOptionPane.showMessageDialog(null, "Account Created Successfully ");

                meterNumberField.setText(meterNumberIncludingYearAndMonth);
                accountIdField.setText(accountIdString);
                name.setText("");
                address.setText("");
                city.setText("");
                pinCode.setText("");
                idProofNumber.setText("");
                mobileNumber.setText("");
                state.select(0);
                idProofType.select(0);

            } catch (Exception ex) {
                ex.printStackTrace();
            }


        } else if (e.getSource() == clear) {
            name.setText("");
            address.setText("");
            city.setText("");
            pinCode.setText("");
            idProofNumber.setText("");
            meterNumberField.setText("");
            accountIdField.setText("");
            mobileNumber.setText("");
            state.select(0);
            idProofType.select(0);
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

    public void addConnectionType(Choice connect0) {
        String[] types = {
                "", "Individual", "Corporate"
        };
        for (String s : types) {
            connect0.add(s);
        }
    }

    public void addIdProofType(Choice idProofTypeChoice) {
        String[] idProofTypes = {
                "",
                "Aadhaar Card",
                "PAN Card",
                "Voter ID Card",
                "Passport",
                "Driving License",
                "Government-issued Employee ID Card",
                "Ration Card (with photo)",
                "NREGA Job Card",
                "Senior Citizen ID Card",
                "Health Insurance Smart Card (issued by government)",
                "Arms License (with photograph)",
                "Pensioner Photo Card",
                "Bank Passbook (with photo, issued by PSU banks)",
                "Post Office ID Card",
                "Student ID Card (with photo)",
                "CGHS / ECHS Card",
                "Birth Certificate (for minors with parent's ID)"
        };
        for (String s : idProofTypes) {
            idProofTypeChoice.add(s);
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

    public static void main(String[] args) {
        new newCustomer();
    }
}
