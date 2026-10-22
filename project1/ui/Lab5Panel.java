package project1.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import lab5.expression.ExpressionCalculator;
import project1.core.ExpressionService;

/** GUI-демонстрація класу ExpressionCalculator із ЛР5. */
public class Lab5Panel extends JPanel {
    private final JTextField xField = AppStyle.createTextField("0.5");
    private final JTextField yField = AppStyle.createTextField("1.5");
    private final JTextArea output = AppStyle.createOutputArea();

    public Lab5Panel() {
        setLayout(new BorderLayout(0, 18));
        setBackground(AppStyle.BACKGROUND);
        setBorder(javax.swing.BorderFactory.createEmptyBorder(28, 32, 28, 32));

        add(AppStyle.createHeader(
                "ЛР5. Класи та методи",
                "Демонстрація двох конструкторів, інкапсуляції через get/set, "
                        + "нестатичного та статичного способів обчислення."),
                BorderLayout.NORTH);
        add(createContent(), BorderLayout.CENTER);
        demonstrateCalculator();
    }

    private JPanel createContent() {
        JPanel content = new JPanel(new BorderLayout(0, 14));
        content.setOpaque(false);

        JPanel form = AppStyle.createCard();
        form.setLayout(new FlowLayout(FlowLayout.LEFT, 12, 4));
        form.add(AppStyle.createFieldLabel("x:"));
        form.add(xField);
        form.add(AppStyle.createFieldLabel("y:"));
        form.add(yField);

        JButton button = AppStyle.createPrimaryButton("Створити об'єкти");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                demonstrateCalculator();
            }
        });
        form.add(button);

        content.add(form, BorderLayout.NORTH);
        content.add(AppStyle.createOutputScroll(output), BorderLayout.CENTER);
        return content;
    }

    private void demonstrateCalculator() {
        try {
            double x = ExpressionService.parseNumber(xField.getText());
            double y = ExpressionService.parseNumber(yField.getText());
            StringBuilder result = new StringBuilder();

            ExpressionCalculator defaultCalculator = new ExpressionCalculator();
            result.append("1. Конструктор без параметрів\n");
            appendCalculator(result, defaultCalculator);
            result.append(String.format(Locale.US,
                    "Нестатичний calculate(): %.10f%n%n",
                    defaultCalculator.calculate()));

            defaultCalculator.setX(x);
            defaultCalculator.setY(y);
            result.append("2. Той самий об'єкт після setX() і setY()\n");
            appendCalculator(result, defaultCalculator);
            result.append(String.format(Locale.US,
                    "Нестатичний calculate(): %.10f%n%n",
                    defaultCalculator.calculate()));

            ExpressionCalculator parameterizedCalculator =
                    new ExpressionCalculator(x, y);
            result.append("3. Конструктор із параметрами\n");
            appendCalculator(result, parameterizedCalculator);
            result.append(String.format(Locale.US,
                    "Нестатичний calculate(): %.10f%n",
                    parameterizedCalculator.calculate()));
            result.append(String.format(Locale.US,
                    "Статичний ExpressionCalculator.calculate(x, y): %.10f%n",
                    ExpressionCalculator.calculate(x, y)));

            output.setForeground(AppStyle.TEXT);
            output.setText(result.toString());
            output.setCaretPosition(0);
        } catch (NumberFormatException exception) {
            showError("x та y потрібно ввести як числа.");
        } catch (IllegalArgumentException exception) {
            showError(exception.getMessage());
        }
    }

    private void appendCalculator(StringBuilder result, ExpressionCalculator calculator) {
        result.append(String.format(Locale.US,
                "getX() = %.6f, getY() = %.6f%n",
                calculator.getX(),
                calculator.getY()));
    }

    private void showError(String message) {
        output.setForeground(AppStyle.ERROR);
        output.setText("Помилка: " + message);
    }
}
