import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class main_window extends JFrame {
    JMenuItem newCustomer, generateBill, viewBill, pendingBill, editCustomer, pendingBillReport, billWiseReport, closeWindows;

    main_window() {

        ImageIcon backgroundImage = new ImageIcon(ClassLoader.getSystemResource("images/bg.jpg"));
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Image backgroundImageScale = backgroundImage.getImage().getScaledInstance(screenSize.width, screenSize.height, Image.SCALE_SMOOTH);
        ImageIcon backgroundImageScaled = new ImageIcon(backgroundImageScale);
        add(new JLabel(backgroundImageScaled));

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu menu = new JMenu("Main Menu");
        menu.setFont(new Font("monospaced", Font.BOLD, 20));
        menuBar.add(menu);

        generateBill = menu.add(createMenuItem("Generate Bill", "images/generateBill.png", e -> new generateBill()));
        viewBill = menu.add(createMenuItem("View Bill", "images/viewBill.png", e -> new viewBill()));
        pendingBill = menu.add(createMenuItem("Pending Bills", "images/pendingBill.png", e -> new pendingBill()));

        JMenu manage = new JMenu("Manage");
        manage.setFont(new Font("monospaced", Font.BOLD, 20));
        menuBar.add(manage);

        newCustomer = manage.add(createMenuItem("New Customer", "images/newCustomer.png", e -> new newCustomer()));
        editCustomer = manage.add(createMenuItem("Edit Customer", "images/editCustomer.png", e -> new editCustomer()));

        JMenu report = new JMenu("Report");
        report.setFont(new Font("monospaced", Font.BOLD, 20));
        menuBar.add(report);

        pendingBillReport = report.add(createMenuItem("Pending Bill Report", "images/editCustomer.png", e -> new pendingBillReport()));
        billWiseReport = report.add(createMenuItem("Bill Wise Report ", "images/editCustomer.png", e -> new billWiseReport()));

        JMenu utility = new JMenu("Utility");
        utility.setFont(new Font("monospaced", Font.BOLD, 20));
        menuBar.add(utility);

        JMenu close = new JMenu("Close");
        close.setFont(new Font("monospaced", Font.BOLD, 20));
        menuBar.add(close);

        closeWindows = close.add(createMenuItem("Exit", "images/editCustomer.png", e -> new billWiseReport()));

        setTitle("Electricity Bill Management | Ver: 1.0.1 | ");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setResizable(false);
        setVisible(true);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                showExitDialog(main_window.this);
            }
        });

        JRootPane rootPane = getRootPane();
        KeyStroke escKey = KeyStroke.getKeyStroke("ESCAPE");
        rootPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(escKey, "exitApp");

        rootPane.getActionMap().put("exitApp", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showExitDialog(main_window.this);
            }
        });


    }

    private static void showExitDialog(JFrame frame) {
        int option = JOptionPane.showConfirmDialog(frame, "Are you sure you want to exit?",
                "Confirm Exit", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

        if (option == JOptionPane.YES_OPTION) {
            frame.dispose();
        }
    }

    private JMenuItem createMenuItem(String text, String iconPath, ActionListener actionListener) {
        JMenuItem item = new JMenuItem(text);
        item.setFont(new Font("monospaced", Font.BOLD, 18));
        ImageIcon icon = new ImageIcon(ClassLoader.getSystemResource(iconPath));
        Image scaledImage = icon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        item.setIcon(new ImageIcon(scaledImage));
        item.addActionListener(actionListener);
        return item;
    }

    public static void main(String[] args) {
        new main_window();
    }
}
