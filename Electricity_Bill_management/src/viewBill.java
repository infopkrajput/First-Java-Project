import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.sql.Connection;

public class viewBill extends JFrame implements ActionListener {

    Choice billTypeChoice;
    DefaultTableModel model;
    JTable table;

    viewBill() {
        String title = "View Bill";
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) (screenSize.width * 0.90);
        int height = (int) (screenSize.height * 0.90);

        int marginLeft = (int) (width * 0.05);
        int marginTop = 100;
        int rowHeight = 30;

        int labelWidth = (int) (width * 0.1);
        int fieldWidth = (int) (width * 0.35);

        int fieldX = marginLeft + labelWidth + 20;

        createLabel("Type of Bill", marginLeft, marginTop, labelWidth, rowHeight);
        billTypeChoice = createChoice(fieldX, marginTop, fieldWidth / 4, rowHeight);
        billTypeChoice.add("Pending");
        billTypeChoice.add("Paid");

        billTypeChoice.addItemListener(_ -> {
            String selected = billTypeChoice.getSelectedItem();
            if (selected.equals("Pending")) {
                loadBills("NO");
            } else if (selected.equals("Paid")) {
                loadBills("YES");
            }
        });

        model = new DefaultTableModel();
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane);

        scrollPane.setBounds(marginLeft, marginTop + rowHeight + 10, width - 2 * marginLeft, height - (marginTop + rowHeight + 50));
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 20));
        table.setFont(new Font("Arial", Font.PLAIN, 15));

        JLabel heading = new JLabel(title);
        heading.setFont(new Font("Arial", Font.BOLD, 30));
        FontMetrics fm = heading.getFontMetrics(heading.getFont());
        int textWidth = fm.stringWidth(heading.getText());
        int x = (width - textWidth) / 2;
        heading.setBounds(x, 30, textWidth, 30);
        add(heading);

        loadBills("NO");

        JRootPane rootPane = getRootPane();
        KeyStroke escKey = KeyStroke.getKeyStroke("ESCAPE");
        rootPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(escKey, "exitApp");

        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                showExitDialog(viewBill.this);
            }
        });

        rootPane.getActionMap().put("exitApp", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showExitDialog(viewBill.this);
            }
        });

        setSize(width, height);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(null);
        setVisible(true);
    }

    public void loadBills(String paymentStatus) {
        model.setRowCount(0);
        model.setColumnCount(0);

        String query = "SELECT " +
                "t.account_id, " +
                "t.bill_number AS Bill_no, " +
                "c.name, " +
                "c.city, " +
                "c.mobile_number, " +
                "t.date_of_transaction AS DateOfTransaction, " +
                "t.unit AS Units, " +
                "t.amount AS Amount, " +
                "t.payment_status AS Paid " +
                "FROM transactions t " +
                "JOIN customer c ON t.account_id = c.account_id " +
                "WHERE t.payment_status = ?";

        String[] columnNames = {
                "Account ID", "Bill No", "Name", "City", "Mobile",
                "Date", "Units", "Amount", "Paid"
        };

        for (String col : columnNames) {
            model.addColumn(col);
        }

        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, paymentStatus);

            try (ResultSet rs = pstmt.executeQuery()) {
                ResultSetMetaData rsmd = rs.getMetaData();
                int columnCount = rsmd.getColumnCount();

                while (rs.next()) {
                    Object[] row = new Object[columnCount];
                    for (int i = 1; i <= columnCount; i++) {
                        row[i - 1] = rs.getObject(i);
                    }
                    model.addRow(row);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error loading bill records!", "Database Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
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

    private static void showExitDialog(JFrame frame) {
        int option = JOptionPane.showConfirmDialog(frame, "Are you sure you want to exit?",
                "Confirm Exit", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

        if (option == JOptionPane.YES_OPTION) {
            frame.dispose();
        }
    }

    public static void main(String[] args) {
        new viewBill();
    }
}