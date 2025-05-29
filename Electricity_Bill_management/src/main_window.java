import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class main_window extends JFrame {
    JMenuItem newCustomer, generateBill, viewBill, payBill, editCustomer, payBillReport, customerWise, viewCustomers, printBill, settings;

    main_window() {
        ImageIcon backgroundImage = new ImageIcon(ClassLoader.getSystemResource("images/bg.jpg"));
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Image backgroundImageScale = backgroundImage.getImage().getScaledInstance(screenSize.width, screenSize.height, Image.SCALE_SMOOTH);
        ImageIcon backgroundImageScaled = new ImageIcon(backgroundImageScale);
        add(new JLabel(backgroundImageScaled));

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenuItem exit = new JMenuItem("Exit");
        exit.setFont(new Font("monospaced", Font.BOLD, 18));
        ImageIcon icon = new ImageIcon(ClassLoader.getSystemResource("images/exit.png"));
        Image scaledImage = icon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        exit.setIcon(new ImageIcon(scaledImage));

        exit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int option = JOptionPane.showConfirmDialog(exit, "Are you sure you want to exit?",
                        "Confirm Exit", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (option == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });


        if (Session.userTypeLoggedInAS.equals("Customer")) {

            JMenu menu = new JMenu("Main Menu");
            menu.setFont(new Font("monospaced", Font.BOLD, 20));
            menuBar.add(menu);
            payBill = menu.add(createMenuItem("Pay Bill", "images/payBill.png", e -> new payBill()));
            printBill = menu.add(createMenuItem("Print Bill", "images/printBill.png", e -> new printBill()));

            JMenu report = new JMenu("Report");
            report.setFont(new Font("monospaced", Font.BOLD, 20));
            menuBar.add(report);

        } else if (Session.userTypeLoggedInAS.equals("Admin")) {

            JMenu menu = new JMenu("Main Menu");
            menu.setFont(new Font("monospaced", Font.BOLD, 20));
            menuBar.add(menu);

            generateBill = menu.add(createMenuItem("Generate Bill", "images/generateBill.png", e -> new generateBill()));
            viewBill = menu.add(createMenuItem("View Bill", "images/viewBill.png", e -> new viewBill()));
            payBill = menu.add(createMenuItem("Pay Bill", "images/payBill.png", e -> new payBill()));
            printBill = menu.add(createMenuItem("Print Bill", "images/printBill.png", e -> new printBill()));

            JMenu manage = new JMenu("Manage");
            manage.setFont(new Font("monospaced", Font.BOLD, 20));
            menuBar.add(manage);

            newCustomer = manage.add(createMenuItem("New Customer", "images/newCustomer.png", e -> new newCustomer()));
            editCustomer = manage.add(createMenuItem("Edit Customer", "images/editCustomer.png", e -> new editCustomer()));
            viewCustomers = manage.add(createMenuItem("View Customers", "images/viewCustomer.png", e -> new viewCustomer()));

            JMenu report = new JMenu("Report");
            report.setFont(new Font("monospaced", Font.BOLD, 20));
            menuBar.add(report);

            payBillReport = report.add(createMenuItem("Pending Bill Report", "images/editCustomer.png", e -> new payBillReport()));
            customerWise = report.add(createMenuItem("Customer Record", "images/editCustomer.png", e -> new CustomerRecord()));

        } else if (Session.userTypeLoggedInAS.equals("root")) {
            JMenu menu = new JMenu("Main Menu");
            menu.setFont(new Font("monospaced", Font.BOLD, 20));
            menuBar.add(menu);

            generateBill = menu.add(createMenuItem("Generate Bill", "images/generateBill.png", e -> new generateBill()));
            viewBill = menu.add(createMenuItem("View Bill", "images/viewBill.png", e -> new viewBill()));
            payBill = menu.add(createMenuItem("Pay Bill", "images/payBill.png", e -> new payBill()));
            printBill = menu.add(createMenuItem("Print Bill", "images/printBill.png", e -> new printBill()));

            JMenu manage = new JMenu("Manage");
            manage.setFont(new Font("monospaced", Font.BOLD, 20));
            menuBar.add(manage);

            newCustomer = manage.add(createMenuItem("New Customer", "images/newCustomer.png", e -> new newCustomer()));
            editCustomer = manage.add(createMenuItem("Edit Customer", "images/editCustomer.png", e -> new editCustomer()));
            viewCustomers = manage.add(createMenuItem("View Customers", "images/viewCustomer.png", e -> new viewCustomer()));

            JMenu report = new JMenu("Report");
            report.setFont(new Font("monospaced", Font.BOLD, 20));
            menuBar.add(report);

            payBillReport = report.add(createMenuItem("Pending Bill Report", "images/editCustomer.png", e -> new payBillReport()));
            customerWise = report.add(createMenuItem("Customer Record", "images/editCustomer.png", e -> new CustomerRecord()));

            JMenu utility = new JMenu("Utility");
            utility.setFont(new Font("monospaced", Font.BOLD, 20));
            settings = utility.add(createMenuItem("Settings", "images/editCustomer.png", e -> new Settings()));
            menuBar.add(utility);

        }
        JMenu close = new JMenu("Close");
        close.setFont(new Font("monospaced", Font.BOLD, 20));
        menuBar.add(close);
        close.add(exit);


        setTitle("Electricity Bill Management | Ver: 1.0.1 | Login As: " + Session.userTypeLoggedInAS + " | User Name: " + Session.userNameLoggedIn);
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
            System.exit(0);
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
