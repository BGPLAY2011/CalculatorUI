import javax.swing.*;
import java.awt.*;
class CalculatorUI {
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setLayout(null);
        frame.setSize(500, 500);
        frame.setLayout(new BorderLayout());
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 4));
        String[] buttons = {
                "1", "2", "3", "+",
                "4", "5", "6", "-",
                "7", "8", "9", "*",
                "0", "C", "=", "/"};
        for (String text : buttons) {
            JButton button = new JButton(text);
            panel.add(button);
        }
        frame.add(panel);
        frame.setVisible(true);
    }
}