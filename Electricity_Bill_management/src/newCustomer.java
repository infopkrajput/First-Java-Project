import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class newCustomer extends JFrame implements ActionListener {
    JTextField meterNumber;
    JButton submit, cancel;

    newCustomer() {

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) (screenSize.width * 0.75);
        int height = (int) (screenSize.height * 0.75);
        JFrame frame = new JFrame("New Customer");
        frame.setSize(width, height);
        frame.setLocationRelativeTo(null);
        setResizable(false);
        setLayout(null);
        frame.setVisible(true);
        frame.getContentPane().setBackground(new Color(83, 199, 224));

        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                showExitDialog(frame);
            }
        });

        JRootPane rootPane = frame.getRootPane();
        KeyStroke escKey = KeyStroke.getKeyStroke("ESCAPE");
        rootPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(escKey, "exitApp");

        rootPane.getActionMap().put("exitApp", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showExitDialog(frame);
            }
        });

        JLabel heading = new JLabel("New Customer");
        heading.setFont(new Font("Arial", Font.BOLD, 30));
        heading.setBounds(200, 10, 300, 30);
        add(heading);

        createLabel("Customer name: ", 20, 60, 180, 25);
        meterNumber = createTextField(200, 60, 180, 25);


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

    public static void main(String[] args) {
        new newCustomer();
    }
}
