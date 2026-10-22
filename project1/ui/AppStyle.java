package project1.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.Border;

/** Спільні кольори та компоненти інтерфейсу. */
public final class AppStyle {
    public static final Color BACKGROUND = new Color(244, 247, 251);
    public static final Color SURFACE = Color.WHITE;
    public static final Color SIDEBAR = new Color(24, 34, 52);
    public static final Color SIDEBAR_HOVER = new Color(43, 58, 82);
    public static final Color PRIMARY = new Color(50, 103, 214);
    public static final Color PRIMARY_DARK = new Color(34, 77, 168);
    public static final Color TEXT = new Color(31, 41, 55);
    public static final Color MUTED = new Color(102, 112, 133);
    public static final Color BORDER = new Color(218, 224, 234);
    public static final Color SUCCESS = new Color(26, 127, 82);
    public static final Color ERROR = new Color(190, 47, 47);

    private static final Font BASE_FONT = new Font("Segoe UI", Font.PLAIN, 14);

    private AppStyle() {
    }

    public static void configureLookAndFeel() {
        UIManager.put("defaultFont", BASE_FONT);
        UIManager.put("Label.font", BASE_FONT);
        UIManager.put("Button.font", BASE_FONT.deriveFont(Font.BOLD));
        UIManager.put("TextField.font", BASE_FONT);
        UIManager.put("TextArea.font", new Font("Consolas", Font.PLAIN, 13));
        UIManager.put("ComboBox.font", BASE_FONT);
    }

    public static JPanel createPage() {
        JPanel panel = new JPanel();
        panel.setBackground(BACKGROUND);
        panel.setBorder(BorderFactory.createEmptyBorder(28, 32, 28, 32));
        return panel;
    }

    public static JPanel createHeader(String title, String description) {
        JPanel header = new JPanel();
        header.setOpaque(false);
        header.setLayout(new javax.swing.BoxLayout(header, javax.swing.BoxLayout.Y_AXIS));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(BASE_FONT.deriveFont(Font.BOLD, 26f));
        titleLabel.setForeground(TEXT);
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JTextArea descriptionArea = new JTextArea(description);
        descriptionArea.setEditable(false);
        descriptionArea.setFocusable(false);
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        descriptionArea.setOpaque(false);
        descriptionArea.setForeground(MUTED);
        descriptionArea.setFont(BASE_FONT.deriveFont(14f));
        descriptionArea.setBorder(BorderFactory.createEmptyBorder(8, 0, 0, 0));
        descriptionArea.setAlignmentX(Component.LEFT_ALIGNMENT);
        descriptionArea.setMaximumSize(new Dimension(Integer.MAX_VALUE, 58));

        header.add(titleLabel);
        header.add(descriptionArea);
        return header;
    }

    public static JPanel createCard() {
        JPanel card = new JPanel();
        card.setBackground(SURFACE);
        Border line = BorderFactory.createLineBorder(BORDER, 1, true);
        Border padding = BorderFactory.createEmptyBorder(18, 20, 18, 20);
        card.setBorder(BorderFactory.createCompoundBorder(line, padding));
        return card;
    }

    public static JTextField createTextField(String value) {
        JTextField field = new JTextField(value);
        field.setPreferredSize(new Dimension(170, 38));
        field.setMargin(new Insets(6, 10, 6, 10));
        return field;
    }

    public static JButton createPrimaryButton(String text) {
        JButton button = new JButton(text);
        button.setForeground(Color.WHITE);
        button.setBackground(PRIMARY);
        button.setFocusPainted(false);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setBorder(BorderFactory.createEmptyBorder(10, 18, 10, 18));
        return button;
    }

    public static JTextArea createOutputArea() {
        JTextArea area = new JTextArea();
        area.setEditable(false);
        area.setLineWrap(false);
        area.setTabSize(4);
        area.setBackground(new Color(250, 251, 253));
        area.setForeground(TEXT);
        area.setBorder(BorderFactory.createEmptyBorder(12, 14, 12, 14));
        return area;
    }

    public static JScrollPane createOutputScroll(JTextArea area) {
        JScrollPane scrollPane = new JScrollPane(area);
        scrollPane.setBorder(BorderFactory.createLineBorder(BORDER));
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        return scrollPane;
    }

    public static JLabel createFieldLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(TEXT);
        label.setFont(BASE_FONT.deriveFont(Font.BOLD));
        return label;
    }
}
