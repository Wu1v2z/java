package project1.ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/** Стартова сторінка програмного пакета. */
public class HomePanel extends JPanel {
    private static final String[][] LABS = {
        {"ЛР1", "Обчислення математичного виразу"},
        {"ЛР2", "Перевірка області допустимих значень"},
        {"ЛР3", "Обробка некоректного введення та винятків"},
        {"ЛР4", "Цикли for і while, таблиця значень"},
        {"ЛР5", "Класи, конструктори, get/set і static"},
        {"ЛР6", "Агрегація, успадкування та поліморфізм"},
        {"ЛР7", "Абстрактні класи та інтерфейси"}
    };

    public HomePanel() {
        setLayout(new BorderLayout(0, 20));
        setBackground(AppStyle.BACKGROUND);
        setBorder(javax.swing.BorderFactory.createEmptyBorder(28, 32, 28, 32));

        add(AppStyle.createHeader(
                "Самостійна робота: CP1. Проєкт 1",
                "Єдиний графічний застосунок для демонстрації лабораторних робіт №1–7, "
                        + "виконаних для варіанта 11."), BorderLayout.NORTH);

        JPanel content = new JPanel();
        content.setOpaque(false);
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));

        JPanel intro = AppStyle.createCard();
        intro.setLayout(new BorderLayout());
        JTextArea introText = new JTextArea(
                "Оберіть лабораторну роботу в меню ліворуч. Кожна сторінка містить "
                        + "вхідні дані, кнопку виконання та результат. Консольні алгоритми "
                        + "перенесено в GUI без зміни основної логіки.");
        introText.setEditable(false);
        introText.setFocusable(false);
        introText.setLineWrap(true);
        introText.setWrapStyleWord(true);
        introText.setOpaque(false);
        introText.setForeground(AppStyle.TEXT);
        intro.add(introText, BorderLayout.CENTER);
        intro.setAlignmentX(Component.LEFT_ALIGNMENT);
        content.add(intro);
        content.add(Box.createVerticalStrut(18));

        JPanel grid = new JPanel(new GridLayout(0, 2, 12, 12));
        grid.setOpaque(false);
        grid.setAlignmentX(Component.LEFT_ALIGNMENT);

        for (String[] lab : LABS) {
            JPanel card = AppStyle.createCard();
            card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));

            JLabel number = new JLabel(lab[0]);
            number.setForeground(AppStyle.PRIMARY);
            number.setFont(number.getFont().deriveFont(java.awt.Font.BOLD, 18f));

            JLabel description = new JLabel("<html>" + lab[1] + "</html>");
            description.setForeground(AppStyle.MUTED);

            card.add(number);
            card.add(Box.createVerticalStrut(8));
            card.add(description);
            grid.add(card);
        }

        content.add(grid);
        content.add(Box.createVerticalStrut(16));

        JLabel hint = new JLabel(
                "Порада: десяткові числа можна вводити як через крапку, так і через кому.");
        hint.setForeground(AppStyle.MUTED);
        hint.setAlignmentX(Component.LEFT_ALIGNMENT);
        content.add(hint);

        JScrollPane scrollPane = new JScrollPane(content);
        scrollPane.setBorder(null);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(scrollPane, BorderLayout.CENTER);
    }
}
