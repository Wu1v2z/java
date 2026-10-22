package project1.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import project1.core.ExpressionService;

/** GUI для табулювання виразу циклами for і while. */
public class Lab4Panel extends JPanel {
    private final JTextField yField = AppStyle.createTextField("1.0");
    private final JComboBox<String> loopSelector = new JComboBox<String>(new String[] {
        "Обидва цикли", "Тільки for", "Тільки while"
    });
    private final JTextArea output = AppStyle.createOutputArea();

    public Lab4Panel() {
        setLayout(new BorderLayout(0, 18));
        setBackground(AppStyle.BACKGROUND);
        setBorder(javax.swing.BorderFactory.createEmptyBorder(28, 32, 28, 32));

        add(AppStyle.createHeader(
                "ЛР4. Цикли",
                "Табулювання виразу для варіанта 11 на проміжку x ∈ [-37.50; 18.20] "
                        + "із кроком 1.05. Можна порівняти цикли for і while."),
                BorderLayout.NORTH);
        add(createContent(), BorderLayout.CENTER);
        calculateTable();
    }

    private JPanel createContent() {
        JPanel content = new JPanel(new BorderLayout(0, 14));
        content.setOpaque(false);

        JPanel form = AppStyle.createCard();
        form.setLayout(new FlowLayout(FlowLayout.LEFT, 12, 4));
        form.add(AppStyle.createFieldLabel("Значення y:"));
        form.add(yField);
        form.add(AppStyle.createFieldLabel("Режим:"));
        loopSelector.setPreferredSize(new java.awt.Dimension(160, 38));
        form.add(loopSelector);

        JButton calculateButton = AppStyle.createPrimaryButton("Побудувати таблицю");
        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                calculateTable();
            }
        });
        form.add(calculateButton);

        content.add(form, BorderLayout.NORTH);
        content.add(AppStyle.createOutputScroll(output), BorderLayout.CENTER);
        return content;
    }

    private void calculateTable() {
        try {
            double y = ExpressionService.parseNumber(yField.getText());
            if (!Double.isFinite(y)) {
                throw new IllegalArgumentException("y має бути скінченним числом.");
            }

            String selected = (String) loopSelector.getSelectedItem();
            StringBuilder result = new StringBuilder();
            result.append(String.format(Locale.US,
                    "N = %d, x ∈ [%.2f; %.2f], Δx = %.2f, y = %.4f%n",
                    ExpressionService.VARIANT,
                    ExpressionService.START_X,
                    ExpressionService.END_X,
                    ExpressionService.STEP,
                    y));

            if (!"Тільки while".equals(selected)) {
                appendForTable(result, y);
            }
            if (!"Тільки for".equals(selected)) {
                appendWhileTable(result, y);
            }

            output.setForeground(AppStyle.TEXT);
            output.setText(result.toString());
            output.setCaretPosition(0);
        } catch (NumberFormatException exception) {
            showError("Введіть числове значення y.");
        } catch (IllegalArgumentException exception) {
            showError(exception.getMessage());
        }
    }

    private void appendForTable(StringBuilder result, double y) {
        appendTableHeader(result, "Цикл for");
        int count = ExpressionService.getNumberOfValues();

        for (int i = 0; i < count; i++) {
            double x = ExpressionService.START_X + i * ExpressionService.STEP;
            appendTableRow(result, i + 1, x, y);
        }
    }

    private void appendWhileTable(StringBuilder result, double y) {
        appendTableHeader(result, "Цикл while");
        int count = ExpressionService.getNumberOfValues();
        int i = 0;

        while (i < count) {
            double x = ExpressionService.START_X + i * ExpressionService.STEP;
            appendTableRow(result, i + 1, x, y);
            i++;
        }
    }

    private void appendTableHeader(StringBuilder result, String title) {
        result.append("\n").append(title).append("\n");
        result.append(String.format("%-4s %-13s %-16s%n", "№", "x", "I"));
        result.append("--------------------------------------\n");
    }

    private void appendTableRow(StringBuilder result, int number, double x, double y) {
        if (!ExpressionService.isInDomain(x, y)) {
            result.append(String.format(Locale.US,
                    "%-4d %-13.6f %s%n", number, x, "порушено ОДЗ"));
            return;
        }

        result.append(String.format(Locale.US,
                "%-4d %-13.6f %-16.10f%n",
                number,
                x,
                ExpressionService.calculateUnchecked(x, y)));
    }

    private void showError(String message) {
        output.setForeground(AppStyle.ERROR);
        output.setText("Помилка: " + message);
    }
}
