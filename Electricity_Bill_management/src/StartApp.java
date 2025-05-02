import javax.swing.*;
import java.awt.*;

public class StartApp extends JFrame {

    StartApp() {
        // Set the background image
        ImageIcon image = new ImageIcon(ClassLoader.getSystemResource("images/pkcomputer.jpg"));
        Image img = image.getImage().getScaledInstance(600, 600, Image.SCALE_DEFAULT);
        ImageIcon imgIcon = new ImageIcon(img);
        JLabel label = new JLabel(imgIcon);
        add(label);

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setTitle("Welcome to PK Computer | Electricity Bill Management");
        setSize(600, 600);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
        try {
            Thread.sleep(3000);
            setVisible(false);
            Database.connect();
            new Login();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        new StartApp();
    }
}