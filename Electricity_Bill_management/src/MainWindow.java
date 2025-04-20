import javax.swing.*;
import java.awt.*;

public class MainWindow {
    JFrame frame;

    public MainWindow() {
        frame = new JFrame("PK Software | Mob. 9058350820 | Ver. 1.0.0 |");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setResizable(false);
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Disable close button
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setEnabled(false); // Initially disable the main window
    }

    public JFrame getFrame() {
        return frame;
    }

    public static void main(String[] args) {
        MainWindow mainWindow = new MainWindow();

        // Show the login screen after 2 seconds
        Timer timer = new Timer(2000, e -> {
            new Login(mainWindow.getFrame()); // Pass reference of the main window
        });
        timer.setRepeats(false);
        timer.start();
    }
}
