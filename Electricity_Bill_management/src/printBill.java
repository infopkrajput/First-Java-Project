import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.awt.print.Printable;
import java.awt.print.PrinterJob;
import java.sql.ResultSet;
import java.sql.SQLException;

public class printBill extends JFrame implements ActionListener {
    JTextField billNumber;
    JButton printBillButton;
    PrintJob printJob;
    JPanel invoicePanel;

    printBill() {
        String title = "Print Bill";
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) (screenSize.width * 0.90);
        int height = (int) (screenSize.height * 0.90);

        int marginLeft = (int) (width * 0.05);
        int marginTop = 100;
        int rowHeight = 30;
        int spacingY = 20;
        int labelWidth = (int) (width * 0.1);
        int fieldWidth = (int) (width * 0.35);

        int fieldX = marginLeft + labelWidth + 20;

        invoicePanel = new JPanel();
        invoicePanel.setLayout(new BoxLayout(invoicePanel, BoxLayout.Y_AXIS));
//        invoicePanel.setBorder(BorderFactory.createTitledBorder("Invoice Preview"));
        invoicePanel.setBounds((int) (width * 0.5), marginTop, (int) (width * 0.4), (int) (height * 0.75));
        invoicePanel.setBackground(new Color(255, 255, 255));
        add(invoicePanel);


        createLabel("Bill Number: ", marginLeft, marginTop, labelWidth, rowHeight);
        billNumber = createTextField(fieldX, marginTop, fieldWidth / 2, rowHeight);
        addSuggestionText(billNumber, "Enter Bill Number");

        createButton("Find Bill", marginLeft, marginTop + rowHeight + spacingY, fieldX, rowHeight);

        JLabel heading = new JLabel(title);
        heading.setFont(new Font("Arial", Font.BOLD, 30));
        FontMetrics fm = heading.getFontMetrics(heading.getFont());
        int textWidth = fm.stringWidth(heading.getText());
        int x = (width - textWidth) / 2;
        heading.setBounds(x, 30, textWidth, 30);
        add(heading);

        JButton printInvoiceBtn = new JButton("Print Invoice");
        printInvoiceBtn.setBounds(marginLeft, marginTop + rowHeight + spacingY * 2 + 10 +20 , fieldX, rowHeight);
        printInvoiceBtn.setFont(new Font("Arial", Font.BOLD, 20));
        printInvoiceBtn.addActionListener(e -> printInvoicePanel());
        add(printInvoiceBtn);


        JRootPane rootPane = getRootPane();
        KeyStroke escKey = KeyStroke.getKeyStroke("ESCAPE");
        rootPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(escKey, "exitApp");

        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                showExitDialog(printBill.this);
            }
        });

        rootPane.getActionMap().put("exitApp", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showExitDialog(printBill.this);
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
        if (e.getSource() == printBillButton) {
            String billNo = billNumber.getText().trim();
            if (billNo.isEmpty() || billNo.equals("Enter Bill Number")) {
                JOptionPane.showMessageDialog(this, "Please enter a valid Bill Number.");
                return;
            }

            displayInvoice(billNo); // Custom method
        }
    }

//    public void displayInvoice(String billNo) {
//
//        invoicePanel.removeAll();
//        invoicePanel.revalidate();
//        invoicePanel.repaint();
//
//        try {
//            // Replace with your DB connection code
//            String query = "SELECT * FROM transaction WHERE bill_number = '" + billNo + "'";
//
//            ResultSet rs = Database.getStatement().executeQuery(query);
//            if (rs.next()) {
//                JLabel billLabel = new JLabel("Bill Number: " + rs.getString("bill_number"));
//                JLabel nameLabel = new JLabel("Account Id: " + rs.getString("account_id"));
//                JLabel dateLabel = new JLabel("Date: " + rs.getString("date_of_payment"));
//                JLabel amountLabel = new JLabel("Total Amount: ₹" + rs.getString("amount"));
//
//                Font font = new Font("Arial", Font.PLAIN, 18);
//                for (JLabel label : new JLabel[]{billLabel, nameLabel, dateLabel, amountLabel}) {
//                    label.setFont(font);
//                    label.setAlignmentX(Component.LEFT_ALIGNMENT);
//                    invoicePanel.add(Box.createVerticalStrut(10)); // Space between labels
//                    invoicePanel.add(label);
//                }

    /// /                exportPanelToPDF(invoicePanel, "Invoice_" + billNo + ".pdf");
//            } else {
//                JLabel notFound = new JLabel("Bill not found.");
//                notFound.setFont(new Font("Arial", Font.BOLD, 18));
//                notFound.setForeground(Color.RED);
//                invoicePanel.add(notFound);
//            }
//
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//            JOptionPane.showMessageDialog(this, "Error fetching bill data.");
//        }
//    }
    public void displayInvoice(String billNo) {
        invoicePanel.removeAll();
        invoicePanel.revalidate();
        invoicePanel.repaint();

        try {
            String query = "SELECT * FROM transactions t JOIN customer c ON t.account_id = c.account_id WHERE bill_number = '" + billNo + "'";
            ResultSet rs = Database.getStatement().executeQuery(query);

            if (rs.next()) {
                JTextArea billArea = new JTextArea();
                billArea.setFont(new Font("Monospaced", Font.PLAIN, 16));
                billArea.setEditable(false);

                String formattedBill = String.format(
                                "------------------------------------------------------------\n" +
                                "|                      ELECTRICITY BILL                    |\n" +
                                "------------------------------------------------------------\n" +
                                "| Bill No: %-18s   Date: %-20s |\n" +
                                "------------------------------------------------------------\n" +
                                "| Account ID       : %-37s |\n" +
                                "| Name             : %-37s |\n" +
                                "| Address          : %-37s |\n" +
                                "| State            : %-37s |\n" +
                                "| Mobile           : %-37s |\n" +
                                "| Connection Type  : %-37s |\n" +
                                "| Meter Number     : %-37s |\n" +
                                "| ID Proof         : %-37s |\n" +
                                "| Issue Date       : %-37s |\n" +
                                "------------------------------------------------------------\n" +
                                "| Units Consumed   : %-37s |\n" +
                                "| Rate/Unit        : ₹%-36s |\n" +
                                "| Total Amount     : ₹%-36s |\n" +
                                "| Status           : %-37s |\n" +
                                "------------------------------------------------------------\n" +
                                "| Thank you for using our services. Pay before due date.   |\n" +
                                "------------------------------------------------------------",
                        rs.getString("bill_number"),
                        rs.getString("date_of_payment"),
                        rs.getString("account_id"),
                        rs.getString("name"),
                        rs.getString("address") + ", " + rs.getString("city") + " - " + rs.getString("pin_code"),
                        rs.getString("state"),
                        rs.getString("mobile_number"),
                        rs.getString("connection_type"),
                        rs.getString("meter_number"),
                        rs.getString("id_proof_type") + " - " + rs.getString("id_proof_number"),
                        rs.getString("date_of_issue"),
                        rs.getString("unit"),
                        rs.getString("rate_per_unit"),
                        rs.getString("amount"),
                        rs.getString("payment_status")
                );

                billArea.setText(formattedBill);
                invoicePanel.add(new JScrollPane(billArea));

            } else {
                JLabel notFound = new JLabel("Bill not found.");
                notFound.setFont(new Font("Arial", Font.BOLD, 18));
                notFound.setForeground(Color.RED);
                invoicePanel.add(notFound);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error fetching bill data.");
        }
    }

    private void printInvoicePanel() {
        PrinterJob job = PrinterJob.getPrinterJob();
        job.setJobName("Print Bill");

        job.setPrintable((graphics, pageFormat, pageIndex) -> {
            if (pageIndex > 0) return Printable.NO_SUCH_PAGE;

            Graphics2D g2 = (Graphics2D) graphics;
            g2.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
            invoicePanel.printAll(g2);

            return Printable.PAGE_EXISTS;
        });

        boolean doPrint = job.printDialog();
        if (doPrint) {
            try {
                job.print();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Print failed: " + e.getMessage());
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

    public void createButton(String name, int x, int y, int width, int height) {
        printBillButton = new JButton(name);
        printBillButton.setBounds(x, y, width, height);
        printBillButton.setFont(new Font("Arial", Font.PLAIN, 20));
        printBillButton.addActionListener(this); // Add ActionListener
        add(printBillButton);
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
        new printBill();
    }
}