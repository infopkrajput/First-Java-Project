import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.awt.event.*;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JDateChooser;

import java.text.SimpleDateFormat;

public class customerWiseReport extends JFrame implements ActionListener {

    private final JDateChooser dateChooserFrom;
    private final JDateChooser dateChooserTo;
    private final Choice customerChoice;
    private final Choice typeChoice;
    private final DefaultTableModel tableModel;
    private final JButton fetchButton;

    customerWiseReport() {
        String title = "Customer wise Report";
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) (screenSize.width * 0.90);
        int height = (int) (screenSize.height * 0.90);

        int marginLeft = (int) (width * 0.05);
        int marginTop = 100;
        int rowHeight = 30;

        int labelWidth = (int) (width * 0.1);
        int fieldWidth = (int) (width * 0.35);
        int fieldX = marginLeft + labelWidth + 20;

        setLayout(null);

        createLabel("Date from:", marginLeft, marginTop, labelWidth, rowHeight);
        dateChooserFrom = new JDateChooser();
        dateChooserFrom.setBounds(fieldX, marginTop, fieldWidth / 2, rowHeight);
        dateChooserFrom.setFont(new Font("Arial", Font.PLAIN, 20));
        add(dateChooserFrom);

        createLabel("Date To:", marginLeft + fieldWidth, marginTop, labelWidth, rowHeight);
        dateChooserTo = new JDateChooser();
        dateChooserTo.setBounds(fieldX + marginLeft + fieldWidth, marginTop, fieldWidth / 2, rowHeight);
        dateChooserTo.setFont(new Font("Arial", Font.PLAIN, 20));
        add(dateChooserTo);

        createLabel("Choose customer: ", marginLeft, marginTop + rowHeight + 20, labelWidth, rowHeight);
        customerChoice = createChoice(fieldX, marginTop + rowHeight + 20, fieldWidth, rowHeight);

        String customerListQuery = "SELECT * FROM customer";

        try (Connection conn = Database.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs0 = stmt.executeQuery(customerListQuery)) {

            while (rs0.next()) {
                String name = rs0.getString("name");
                String city = rs0.getString("city");
                String displayText = name + ", " + city;
                customerChoice.add(displayText);
            }

        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }


        createLabel("Type: ", marginLeft + fieldX + fieldWidth, marginTop + rowHeight + 20, labelWidth - 80, rowHeight);
        typeChoice = createChoice(marginLeft + fieldX + fieldWidth + labelWidth - 80, marginTop + rowHeight + 20, fieldWidth / 3, rowHeight);
        typeChoice.add("All");
        typeChoice.add("Paid");
        typeChoice.add("Pending");

        fetchButton = createButton("Fetch Data", marginLeft, marginTop + 100, 200, 40);
        fetchButton.addActionListener(this);


        tableModel = new DefaultTableModel(new String[]{"ID", "Name", "City", "Invoice No.", "Date", "Amount", "Status"}, 0);
        JTable table = new JTable(tableModel);
        table.setFont(new Font("Arial", Font.PLAIN, 18));
        table.setRowHeight(25);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(marginLeft, marginTop + 160, width - 2 * marginLeft, height - (marginTop + 220));
        add(scrollPane);

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
                showExitDialog(customerWiseReport.this);
            }
        });

        rootPane.getActionMap().put("exitApp", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showExitDialog(customerWiseReport.this);
            }
        });

        setSize(width, height);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == fetchButton) {
            tableModel.setRowCount(0);
            if (dateChooserFrom.getDate() == null || dateChooserTo.getDate() == null) {
                JOptionPane.showMessageDialog(null, "Please select both From and To dates.");
                return;
            }
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String fromDate = sdf.format(dateChooserFrom.getDate());
            String toDate = sdf.format(dateChooserTo.getDate());
            String filter = typeChoice.getSelectedItem();

            String selectedItem = customerChoice.getSelectedItem().trim();
            String customerName = selectedItem.split(",")[0].trim();

            StringBuilder queryBuilder = getStringBuilder(filter);

            try (Connection conn = Database.getConnection();
                 PreparedStatement ps = conn.prepareStatement(queryBuilder.toString())) {

                ps.setString(1, customerName);
                ps.setString(2, fromDate);
                ps.setString(3, toDate);

                try (ResultSet rs0 = ps.executeQuery()) {
                    ResultSetMetaData rsmd = rs0.getMetaData();
                    int columnCount = rsmd.getColumnCount();

                    while (rs0.next()) {
                        Object[] row = new Object[columnCount];
                        for (int i = 1; i <= columnCount; i++) {
                            row[i - 1] = rs0.getObject(i);
                        }
                        tableModel.addRow(row);
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    private static StringBuilder getStringBuilder(String filter) {
        StringBuilder queryBuilder = new StringBuilder(
                """
                        SELECT\s
                            customer.account_id AS AccountID,
                            customer.name,
                            customer.city,
                            transactions.bill_number,
                            transactions.date_of_transaction,
                            transactions.amount,
                            transactions.payment_status
                        FROM\s
                            customer
                        JOIN\s
                            transactions
                        ON\s
                            customer.account_id = transactions.account_id
                        WHERE\s
                            customer.name = ? AND
                            transactions.date_of_transaction BETWEEN ? AND ?"""
        );

        if (filter.equals("Paid")) {
            queryBuilder.append(" AND transactions.payment_status = 'YES'");
        } else if (filter.equals("Pending")) {
            queryBuilder.append(" AND transactions.payment_status = 'NO'");
        }
        return queryBuilder;
    }

    public void createLabel(String labelName, int x, int y, int width, int height) {
        JLabel label = new JLabel(labelName);
        label.setBounds(x, y, width, height);
        label.setFont(new Font("Arial", Font.PLAIN, 20));
        add(label);
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

    private static void showExitDialog(JFrame frame) {
        int option = JOptionPane.showConfirmDialog(frame, "Are you sure you want to exit?",
                "Confirm Exit", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

        if (option == JOptionPane.YES_OPTION) {
            frame.dispose();
        }
    }


    public static void main(String[] args) {
        new customerWiseReport();
    }
}
