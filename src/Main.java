import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
class CalculatorUI extends JFrame implements ActionListener {
    private JTextField display;
    private StringBuilder currentInput;
    private int firstNumber;
    private String operator;
    private final String FILE_NAME = "last_result.txt";
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
        loadLastResult();
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
    private void loadLastResult() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String json = "{\"result\": \"" + display.getText() + "\"}";
            if (json != null && json.contains(":")) {
                int start = json.indexOf("\"", json.indexOf(":")) + 1;
                int end = json.indexOf("\"", start);
                String value = json.substring(start, end);
                display.setText(value);
                currentInput.append(value);
            }
        } catch (IOException ex) {
        }
    }
    {
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
                    writer.write(display.getText());
                } catch (IOException ex) {
                    System.out.println("Ошибка при сохранении результата");
                }
            }
        });
    }
    public static void main(String[] args) {
        new CalculatorUI();
    }
}
