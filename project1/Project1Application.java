package project1;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import project1.ui.AppStyle;
import project1.ui.MainFrame;

/** Точка входу до самостійної роботи «CP1. Проєкт 1». */
public final class Project1Application {
    private Project1Application() {
    }

    public static void main(String[] args) {
        installLookAndFeel();

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainFrame().setVisible(true);
            }
        });
    }

    private static void installLookAndFeel() {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ignored) {
            // Якщо Nimbus недоступний, Swing використає системне оформлення.
        }

        AppStyle.configureLookAndFeel();
    }
}
