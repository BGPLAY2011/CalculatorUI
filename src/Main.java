import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class CalculatorUI extends JFrame implements ActionListener {
    private JTextField display;
    private StringBuilder currentInput;
    private int firstNumber;
    private String operator;

    public CalculatorUI() {
        setTitle("Калькулятор");
        setSize(300, 400);
        setLayout(new BorderLayout());

        currentInput = new StringBuilder();
        firstNumber = 0;
        operator = "";

        display = new JTextField();
        add(display, BorderLayout.NORTH);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 4));

        String[] buttons = {
                "7", "8", "9", "+",
                "4", "5", "6", "-",
                "1", "2", "3", "*",
                "0", "=", "C", ":"
        };

        for (String text : buttons) {
            JButton button = new JButton(text);
            button.addActionListener(this);
            panel.add(button);
        }
        add(panel, BorderLayout.CENTER);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if (command.matches("[0-9]")) {
            currentInput.append(command);
            display.setText(currentInput.toString());
        } else if (command.matches("[+\\-*/]")) {
            firstNumber = Integer.parseInt(currentInput.toString());
            operator = command;
            currentInput.setLength(0);
        } else if (command.equals("=")) {
            double secondNumber = Double.parseDouble(currentInput.toString());
            double result = 0;
            switch (operator) {
                case "+": result = firstNumber + secondNumber; break;
                case "-": result = firstNumber - secondNumber; break;
                case "*": result = firstNumber * secondNumber; break;
                case "/": result = secondNumber != 0 ? firstNumber / secondNumber : 0;
            }
            display.setText(String.valueOf(result));
            currentInput.setLength(0);
            currentInput.append(result);
        } else if (command.equals("C")) {
            currentInput.setLength(0);
            display.setText("");
        }
    }
    public static void main(String[] args) {
        new CalculatorUI();
    }
}
