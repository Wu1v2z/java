package project1.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/** Сторінка з даними автора та фотографією. */
public class AuthorPanel extends JPanel {
    private static final String PROFILE_FILE = "project1/author.properties";

    public AuthorPanel() {
        setLayout(new BorderLayout(0, 18));
        setBackground(AppStyle.BACKGROUND);
        setBorder(BorderFactory.createEmptyBorder(28, 32, 28, 32));

        add(AppStyle.createHeader(
                "Про автора",
                "Інформація завантажується з project1/author.properties, "
                        + "а фотографія — зі шляху, вказаного у властивості photo."),
                BorderLayout.NORTH);
        add(createProfileCard(loadProperties()), BorderLayout.CENTER);
    }

    private JPanel createProfileCard(Properties properties) {
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setOpaque(false);

        JPanel card = AppStyle.createCard();
        card.setLayout(new BorderLayout(28, 0));
        card.add(createPhoto(properties.getProperty("photo", "project1/assets/author.jpg")),
                BorderLayout.WEST);

        JPanel details = new JPanel();
        details.setOpaque(false);
        details.setLayout(new BoxLayout(details, BoxLayout.Y_AXIS));

        JLabel name = new JLabel(properties.getProperty("name", "Дані ще не вказані"));
        name.setForeground(AppStyle.TEXT);
        name.setFont(new Font("Segoe UI", Font.BOLD, 26));

        details.add(name);
        details.add(Box.createVerticalStrut(16));
        details.add(createDetail("Група", properties.getProperty("group", "Не вказано")));
        details.add(Box.createVerticalStrut(10));
        details.add(createDetail("Заклад освіти",
                properties.getProperty("university", "Не вказано")));
        details.add(Box.createVerticalStrut(10));
        details.add(createDetail("Дисципліна",
                properties.getProperty("discipline", "Програмування Java")));
        details.add(Box.createVerticalStrut(10));
        details.add(createDetail("Варіант",
                properties.getProperty("variant", "11")));
        details.add(Box.createVerticalStrut(28));

        JLabel hint = new JLabel(
                "<html><b>Щоб заповнити сторінку:</b> внесіть дані в "
                        + "project1/author.properties і додайте власне фото.</html>");
        hint.setForeground(AppStyle.MUTED);
        details.add(hint);
        details.add(Box.createVerticalGlue());

        card.add(details, BorderLayout.CENTER);
        wrapper.add(card, BorderLayout.NORTH);
        return wrapper;
    }

    private JLabel createPhoto(String photoPath) {
        JLabel photoLabel = new JLabel();
        photoLabel.setPreferredSize(new Dimension(230, 290));
        photoLabel.setMinimumSize(new Dimension(230, 290));
        photoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        photoLabel.setVerticalAlignment(SwingConstants.CENTER);
        photoLabel.setOpaque(true);
        photoLabel.setBackground(new Color(235, 239, 246));
        photoLabel.setBorder(BorderFactory.createLineBorder(AppStyle.BORDER));

        File photoFile = new File(photoPath);
        ImageIcon icon = photoFile.isFile() ? new ImageIcon(photoFile.getPath()) : null;

        if (icon != null && icon.getIconWidth() > 0 && icon.getIconHeight() > 0) {
            double scale = Math.min(
                    220.0 / icon.getIconWidth(),
                    280.0 / icon.getIconHeight());
            int width = Math.max(1, (int) (icon.getIconWidth() * scale));
            int height = Math.max(1, (int) (icon.getIconHeight() * scale));
            Image scaled = icon.getImage().getScaledInstance(
                    width, height, Image.SCALE_SMOOTH);
            photoLabel.setIcon(new ImageIcon(scaled));
        } else {
            photoLabel.setText("<html><div style='text-align:center'>ФОТО<br>ще не додано</div></html>");
            photoLabel.setForeground(AppStyle.MUTED);
            photoLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        }

        return photoLabel;
    }

    private JLabel createDetail(String label, String value) {
        JLabel detail = new JLabel("<html><b>" + label + ":</b> " + value + "</html>");
        detail.setForeground(AppStyle.TEXT);
        detail.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        return detail;
    }

    private Properties loadProperties() {
        Properties properties = new Properties();
        File file = new File(PROFILE_FILE);

        if (!file.isFile()) {
            return properties;
        }

        try (InputStreamReader reader = new InputStreamReader(
                new FileInputStream(file), StandardCharsets.UTF_8)) {
            properties.load(reader);
        } catch (Exception ignored) {
            // У разі проблеми відображаються безпечні стандартні значення.
        }
        return properties;
    }
}
