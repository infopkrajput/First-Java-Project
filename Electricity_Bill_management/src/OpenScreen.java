import javax.swing.*;
import java.awt.*;

public class OpenScreen extends JFrame {
    private final JProgressBar progressBar;

    OpenScreen() {
        // Set the background image
        ImageIcon image = new ImageIcon(ClassLoader.getSystemResource("images/pkcomputer.jpg"));
        Image img = image.getImage().getScaledInstance(600, 600, Image.SCALE_DEFAULT);
        ImageIcon imgIcon = new ImageIcon(img);
        JLabel label = new JLabel(imgIcon);
        label.setLayout(new BorderLayout());

        // Initialize and configure the progress bar
        progressBar = new JProgressBar();
        progressBar.setMinimum(0);
        progressBar.setMaximum(100);
        progressBar.setStringPainted(true);
        progressBar.setForeground(Color.GRAY);
        progressBar.setBackground(Color.WHITE);

        label.add(progressBar, BorderLayout.SOUTH);
        add(label);

        setTitle("Welcome to PK Computer");
        setSize(600, 600);
        setLocationRelativeTo(null);
        setVisible(true);

        startLoading();
    }

    private void startLoading() {
        SwingWorker<Void, Integer> worker = new SwingWorker<Void, Integer>() {
            @Override
            protected Void doInBackground() throws Exception {
                for (int i = 0; i <= 100; i++) {
                    Thread.sleep(30);
                    publish(i);
                }
                return null;
            }

            @Override
            protected void process(java.util.List<Integer> chunks) {
                for (int value : chunks) {
                    progressBar.setValue(value);
                }
            }

            @Override
            protected void done() {
                setVisible(false);

                // ✅ Open Main Window first (disabled)
                MainWindow mainWindow = new MainWindow();

                // ✅ Open the Login window and pass reference of MainWindow’s frame
                new Login(mainWindow.getFrame());
            }
        };
        worker.execute();
    }

    public static void main(String[] args) {
        new OpenScreen();
    }
}
