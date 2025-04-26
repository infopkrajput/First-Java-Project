import javax.swing.*;

public class generateBill extends JFrame {
    generateBill(){
        JFrame frame = new JFrame("Generate Bill");
        frame.setSize(600, 600);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
    public static void main(String[] args) {
        new generateBill();
    }
}
