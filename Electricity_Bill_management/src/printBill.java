import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.print.Printable;
import java.awt.print.PrinterJob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;

public class printBill extends JFrame implements ActionListener {
    JTextField billNumber;
    JButton printBillButton;
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
        printInvoiceBtn.setBounds(marginLeft, marginTop + rowHeight + spacingY * 2 + 10 + 20, fieldX, rowHeight);
        printInvoiceBtn.setFont(new Font("Arial", Font.BOLD, 20));
        printInvoiceBtn.addActionListener(_ -> printInvoicePanel());
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

    public void displayInvoice(String billNo) {
        invoicePanel.removeAll();
        invoicePanel.revalidate();
        invoicePanel.repaint();

        String query = "SELECT * FROM transactions t JOIN customer c ON t.account_id = c.account_id WHERE bill_number = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, billNo);

            try (ResultSet rs = pstmt.executeQuery()) {
                invoicePanel.removeAll();  // Clear previous content in the invoice panel

                if (rs.next()) {
                    JTextArea billArea = new JTextArea();
                    billArea.setFont(new Font("Monospaced", Font.PLAIN, 16));
                    billArea.setEditable(false);

                    String formattedBill = String.format(
                            """
                                    ------------------------------------------------------------
                                    |                      ELECTRICITY BILL                    |
                                    ------------------------------------------------------------
                                    | Bill No: %-18s   Date: %-20s |
                                    ------------------------------------------------------------
                                    | Account ID       : %-37s |
                                    | Name             : %-37s |
                                    | Address          : %-37s |
                                    | State            : %-37s |
                                    | Mobile           : %-37s |
                                    | Connection Type  : %-37s |
                                    | Meter Number     : %-37s |
                                    | ID Proof         : %-37s |
                                    | Issue Date       : %-37s |
                                    ------------------------------------------------------------
                                    | Units Consumed   : %-37s |
                                    | Rate/Unit        : ₹%-36s |
                                    | Total Amount     : ₹%-36s |
                                    | Status           : %-37s |
                                    ------------------------------------------------------------
                                    | Thank you for using our services. Pay before due date.   |
                                    ------------------------------------------------------------""",
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

                invoicePanel.revalidate();
                invoicePanel.repaint();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error fetching bill data.", "Database Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            throw new RuntimeException(e);
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
        payBill.addSuggestionText(field, text);
    }

    public void createButton(String name, int x, int y, int width, int height) {
        printBillButton = new JButton(name);
        printBillButton.setBounds(x, y, width, height);
        printBillButton.setFont(new Font("Arial", Font.PLAIN, 20));
        printBillButton.addActionListener(this); // Add ActionListener
        add(printBillButton);
    }

    public static void main(String[] args) {
        new printBill();
    }
}