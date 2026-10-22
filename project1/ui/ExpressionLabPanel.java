package project1.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import project1.core.ExpressionService;

/** Спільна GUI-форма для етапів розвитку програми в ЛР1–3. */
public class ExpressionLabPanel extends JPanel {
    private final int labNumber;
    private final JTextField xField = AppStyle.createTextField("0.5");
    private final JTextField yField = AppStyle.createTextField("1.0");
    private final JTextArea output = AppStyle.createOutputArea();

    public ExpressionLabPanel(int labNumber) {
        this.labNumber = labNumber;
        setLayout(new BorderLayout(0, 18));
        setBackground(AppStyle.BACKGROUND);
        setBorder(javax.swing.BorderFactory.createEmptyBorder(28, 32, 28, 32));

        add(AppStyle.createHeader(getTitle(), getDescription()), BorderLayout.NORTH);
        add(createContent(), BorderLayout.CENTER);
        runCalculation();
    }

    private JPanel createContent() {
        JPanel content = new JPanel(new BorderLayout(0, 14));
        content.setOpaque(false);

        JPanel form = AppStyle.createCard();
        form.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(4, 6, 4, 6);
        constraints.anchor = GridBagConstraints.WEST;

        constraints.gridx = 0;
        constraints.gridy = 0;
        form.add(AppStyle.createFieldLabel("x, радіани:"), constraints);
        constraints.gridx = 1;
        form.add(xField, constraints);

        constraints.gridx = 2;
        form.add(AppStyle.createFieldLabel("y:"), constraints);
        constraints.gridx = 3;
        yField.setEditable(false);
        yField.setToolTipText("За умовою ЛР1–3 значення y ініціалізовано як 1.0");
        form.add(yField, constraints);

        JPanel actions = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        actions.setOpaque(false);
        JButton calculateButton = AppStyle.createPrimaryButton("Обчислити");
        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                runCalculation();
            }
        });
        actions.add(calculateButton);

        constraints.gridx = 4;
        constraints.weightx = 1.0;
        form.add(actions, constraints);

        content.add(form, BorderLayout.NORTH);
        content.add(AppStyle.createOutputScroll(output), BorderLayout.CENTER);
        return content;
    }

    private void runCalculation() {
        try {
            double x = ExpressionService.parseNumber(xField.getText());
            double y = 1.0;
            StringBuilder result = new StringBuilder();

            result.append("Формула:\n");
            result.append("I = 2.33 · ln(√(1 + cos²(y))) / (eʸ + sin²(x))\n\n");
            result.append(String.format(Locale.US, "x = %.6f рад%n", x));
            result.append(String.format(Locale.US, "y = %.6f (задано програмою)%n%n", y));

            if (labNumber == 1) {
                double value = ExpressionService.calculateUnchecked(x, y);
                result.append("ЛР1: безпосереднє обчислення виразу.\n");
                result.append(String.format(Locale.US, "I = %.10f%n", value));
            } else {
                appendDomainCheck(result, x, y);
                double value = ExpressionService.calculate(x, y);

                if (labNumber == 2) {
                    result.append("\nЛР2: усі умови ОДЗ виконано.\n");
                } else {
                    result.append("\nЛР3: введення прочитано в блоці try, винятків не виникло.\n");
                }
                result.append(String.format(Locale.US, "I = %.10f%n", value));
            }

            output.setForeground(AppStyle.TEXT);
            output.setText(result.toString());
            output.setCaretPosition(0);
        } catch (NumberFormatException exception) {
            showInputError("Неможливо перетворити введене значення на число.");
        } catch (IllegalArgumentException exception) {
            showInputError(exception.getMessage());
        }
    }

    private void appendDomainCheck(StringBuilder result, double x, double y) {
        double sqrtArgument = ExpressionService.getSqrtArgument(y);
        double logArgument = Math.sqrt(sqrtArgument);
        double denominator = ExpressionService.getDenominator(x, y);

        result.append("Перевірка області допустимих значень:\n");
        result.append(String.format(Locale.US,
                "• 1 + cos²(y) = %.10f ≥ 0%n", sqrtArgument));
        result.append(String.format(Locale.US,
                "• √(1 + cos²(y)) = %.10f > 0%n", logArgument));
        result.append(String.format(Locale.US,
                "• eʸ + sin²(x) = %.10f ≠ 0%n", denominator));
    }

    private void showInputError(String message) {
        output.setForeground(AppStyle.ERROR);
        output.setText(
                "Помилка введення: " + message + "\n\n"
                        + (labNumber == 3
                                ? "ЛР3 демонструє, що помилку перехоплено й програма продовжує працювати."
                                : "Введіть скінченне дійсне число."));
    }

    private String getTitle() {
        if (labNumber == 1) {
            return "ЛР1. Обчислення виразу";
        }
        if (labNumber == 2) {
            return "ЛР2. Область допустимих значень";
        }
        return "ЛР3. Обробка виняткових ситуацій";
    }

    private String getDescription() {
        if (labNumber == 1) {
            return "Користувач вводить x, значення y ініціалізується як 1.0, після чого програма обчислює вираз варіанта 11.";
        }
        if (labNumber == 2) {
            return "Перед обчисленням програма перевіряє підкореневий вираз, аргумент логарифма, знаменник і скінченність чисел.";
        }
        return "Некоректне введення перехоплюється як виняткова ситуація та показується користувачу без аварійного завершення програми.";
    }
}
