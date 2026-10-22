package project1.ui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/** Головне вікно з бічною навігацією між лабораторними роботами. */
public class MainFrame extends JFrame {
    private final CardLayout cardLayout = new CardLayout();
    private final JPanel pages = new JPanel(cardLayout);
    private final Map<String, JButton> navigationButtons = new LinkedHashMap<String, JButton>();
    private JButton selectedButton;

    public MainFrame() {
        super("CP1. Проєкт 1 — Лабораторні роботи Java");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(1040, 680));
        setSize(1220, 780);
        setLocationRelativeTo(null);

        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(AppStyle.BACKGROUND);
        setContentPane(root);

        pages.setBackground(AppStyle.BACKGROUND);
        registerPages();

        root.add(createSidebar(), BorderLayout.WEST);
        root.add(pages, BorderLayout.CENTER);

        showPage("home");
    }

    private void registerPages() {
        pages.add(new HomePanel(), "home");
        pages.add(new ExpressionLabPanel(1), "lab1");
        pages.add(new ExpressionLabPanel(2), "lab2");
        pages.add(new ExpressionLabPanel(3), "lab3");
        pages.add(new Lab4Panel(), "lab4");
        pages.add(new Lab5Panel(), "lab5");
        pages.add(new Lab6Panel(), "lab6");
        pages.add(new Lab7Panel(), "lab7");
        pages.add(new AuthorPanel(), "author");
    }

    private Component createSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setBackground(AppStyle.SIDEBAR);
        sidebar.setPreferredSize(new Dimension(250, 0));
        sidebar.setBorder(BorderFactory.createEmptyBorder(24, 16, 20, 16));
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));

        JLabel projectLabel = new JLabel("CP1. ПРОЄКТ 1");
        projectLabel.setForeground(Color.WHITE);
        projectLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        projectLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel subtitle = new JLabel("Java • варіант 11");
        subtitle.setForeground(new Color(166, 180, 202));
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        subtitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        sidebar.add(projectLabel);
        sidebar.add(Box.createVerticalStrut(4));
        sidebar.add(subtitle);
        sidebar.add(Box.createVerticalStrut(26));

        addNavigationButton(sidebar, "home", "Головна");
        addNavigationButton(sidebar, "lab1", "ЛР1  Обчислення виразу");
        addNavigationButton(sidebar, "lab2", "ЛР2  Перевірка ОДЗ");
        addNavigationButton(sidebar, "lab3", "ЛР3  Винятки");
        addNavigationButton(sidebar, "lab4", "ЛР4  Цикли");
        addNavigationButton(sidebar, "lab5", "ЛР5  Класи й методи");
        addNavigationButton(sidebar, "lab6", "ЛР6  Ієрархія класів");
        addNavigationButton(sidebar, "lab7", "ЛР7  Абстракції");

        sidebar.add(Box.createVerticalGlue());
        addNavigationButton(sidebar, "author", "Про автора");

        return sidebar;
    }

    private void addNavigationButton(JPanel sidebar, final String pageId, String text) {
        final JButton button = new JButton(text);
        button.setAlignmentX(Component.LEFT_ALIGNMENT);
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setForeground(new Color(224, 231, 242));
        button.setBackground(AppStyle.SIDEBAR);
        button.setBorder(BorderFactory.createEmptyBorder(10, 12, 10, 12));
        button.setFocusPainted(false);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                showPage(pageId);
            }
        });

        navigationButtons.put(pageId, button);
        sidebar.add(button);
        sidebar.add(Box.createVerticalStrut(4));
    }

    private void showPage(String pageId) {
        cardLayout.show(pages, pageId);

        if (selectedButton != null) {
            selectedButton.setBackground(AppStyle.SIDEBAR);
            selectedButton.setForeground(new Color(224, 231, 242));
        }

        selectedButton = navigationButtons.get(pageId);
        if (selectedButton != null) {
            selectedButton.setBackground(AppStyle.PRIMARY_DARK);
            selectedButton.setForeground(Color.WHITE);
        }
    }
}
