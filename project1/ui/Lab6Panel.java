package project1.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import lab6.dishes.Dish;
import lab6.dishes.HotDish;
import lab6.dishes.Ingredient;
import project1.core.ConsoleCapture;

/** GUI-демонстрація системи класів «Їстівна страва» з ЛР6. */
public class Lab6Panel extends JPanel {
    private final JTextArea output = AppStyle.createOutputArea();

    public Lab6Panel() {
        setLayout(new BorderLayout(0, 18));
        setBackground(AppStyle.BACKGROUND);
        setBorder(javax.swing.BorderFactory.createEmptyBorder(28, 32, 28, 32));

        add(AppStyle.createHeader(
                "ЛР6. Ієрархія класів «Їстівна страва»",
                "Ingredient агрегується у Dish, а HotDish успадковує Dish. "
                        + "Демонструються конструктори, перевантаження, перевизначення та поліморфізм."),
                BorderLayout.NORTH);
        add(createContent(), BorderLayout.CENTER);
        runDemonstration();
    }

    private JPanel createContent() {
        JPanel content = new JPanel(new BorderLayout(0, 14));
        content.setOpaque(false);

        JPanel actions = AppStyle.createCard();
        actions.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 4));
        JButton button = AppStyle.createPrimaryButton("Створити страви");
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
        final Ingredient defaultIngredient = new Ingredient();
        final Ingredient tomato = new Ingredient("Помідор", 180.0, 18.0);

        final Dish salad = new Dish("Овочевий салат", 15);
        salad.addIngredient(tomato);
        salad.addIngredient("Огірок", 150.0, 15.0);
        salad.addIngredient("Оливкова олія", 15.0, 884.0);

        final HotDish borscht = new HotDish(
                "Український борщ", 60, 70.0, "Варіння");
        borscht.addIngredient("Буряк", 200.0, 43.0);
        borscht.addIngredient("Картопля", 250.0, 77.0);
        borscht.addIngredient("Яловичина", 300.0, 250.0);

        String result = ConsoleCapture.capture(new Runnable() {
            @Override
            public void run() {
                System.out.println("1. Ingredient: два конструктори");
                defaultIngredient.showInfo();
                tomato.showInfo();

                System.out.println("\n2. Dish: агрегація та перевантажений addIngredient()");
                salad.showInfo();

                System.out.println("\n3. HotDish: успадкування та перевизначення showInfo()");
                Dish polymorphicDish = borscht;
                System.out.println("Тип посилання: Dish");
                System.out.println("Реальний клас: "
                        + polymorphicDish.getClass().getSimpleName());
                polymorphicDish.showInfo();
            }
        });

        output.setForeground(AppStyle.TEXT);
        output.setText(result);
        output.setCaretPosition(0);
    }
}
