package project1.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import lab7.cooking.AbstractCookedDish;
import lab7.cooking.BakedDish;
import lab7.cooking.Cookable;
import lab7.cooking.Soup;
import project1.core.ConsoleCapture;

/** GUI-демонстрація абстрактного класу та інтерфейсу з ЛР7. */
public class Lab7Panel extends JPanel {
    private final JTextArea output = AppStyle.createOutputArea();

    public Lab7Panel() {
        setLayout(new BorderLayout(0, 18));
        setBackground(AppStyle.BACKGROUND);
        setBorder(javax.swing.BorderFactory.createEmptyBorder(28, 32, 28, 32));

        add(AppStyle.createHeader(
                "ЛР7. Абстрактні класи та інтерфейси",
                "AbstractCookedDish успадковує HotDish і реалізує Cookable. "
                        + "Soup та BakedDish надають різні реалізації абстрактних методів."),
                BorderLayout.NORTH);
        add(createContent(), BorderLayout.CENTER);
        runDemonstration();
    }

    private JPanel createContent() {
        JPanel content = new JPanel(new BorderLayout(0, 14));
        content.setOpaque(false);

        JPanel actions = AppStyle.createCard();
        actions.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 4));
        JButton button = AppStyle.createPrimaryButton("Продемонструвати реалізації");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                runDemonstration();
            }
        });
        actions.add(button);

        content.add(actions, BorderLayout.NORTH);
        content.add(AppStyle.createOutputScroll(output), BorderLayout.CENTER);
        return content;
    }

    private void runDemonstration() {
        final Soup borscht = new Soup(
                "Український борщ", 60, 70.0, 3, 3.0);
        borscht.addIngredient("Буряк", 200.0, 43.0);
        borscht.addIngredient("Картопля", 250.0, 77.0);

        final BakedDish casserole = new BakedDish(
                "Картопляна запіканка", 50, 65.0, 2, 180);
        casserole.addIngredient("Картопля", 500.0, 77.0);
        casserole.addIngredient("Сир", 150.0, 350.0);

        String result = ConsoleCapture.capture(new Runnable() {
            @Override
            public void run() {
                AbstractCookedDish[] menu = {borscht, casserole};

                for (AbstractCookedDish dish : menu) {
                    System.out.println("--- " + dish.getName() + " ---");
                    System.out.println("Посилання абстрактного типу: AbstractCookedDish");
                    System.out.println("Реальний клас: " + dish.getClass().getSimpleName());
                    dish.showCookingPlan();

                    Cookable cookable = dish;
                    System.out.println("Виклик cook() через інтерфейс Cookable:");
                    cookable.cook();
                    System.out.println();
                }
            }
        });

        output.setForeground(AppStyle.TEXT);
        output.setText(result);
        output.setCaretPosition(0);
    }
}
