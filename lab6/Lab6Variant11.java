package lab6;

import lab6.dishes.Dish;
import lab6.dishes.HotDish;
import lab6.dishes.Ingredient;

/**
 * Лабораторна робота №6, варіант 11 — «Їстівна страва».
 *
 * <p>Програма демонструє конструктори, агрегацію, успадкування,
 * перевантаження методів, перевизначення та поліморфізм.</p>
 */
public class Lab6Variant11 {
    public static void main(String[] args) {
        printTitle("Варіант 11: Їстівна страва");

        demonstrateIngredientClass();
        demonstrateDishClass();
        demonstrateInheritanceAndPolymorphism();
    }

    /** Демонстрація двох конструкторів і методів класу Ingredient. */
    private static void demonstrateIngredientClass() {
        printSection("1. Клас Ingredient і два його конструктори");

        Ingredient defaultIngredient = new Ingredient();
        Ingredient chicken = new Ingredient("Куряче філе", 200.0, 165.0);

        defaultIngredient.showInfo();
        chicken.showInfo();
    }

    /** Демонстрація агрегації та перевантаження методів. */
    private static void demonstrateDishClass() {
        printSection("2. Клас Dish, агрегація та перевантаження методів");

        // Конструктор без параметрів.
        Dish defaultDish = new Dish();
        defaultDish.addIngredient(new Ingredient());
        defaultDish.showInfo(false);

        // Конструктор з параметрами.
        Dish salad = new Dish("Овочевий салат", 15);

        // Перша версія addIngredient приймає готовий об'єкт Ingredient.
        salad.addIngredient(new Ingredient("Помідор", 180.0, 18.0));

        // Друга, перевантажена версія приймає окремі значення.
        salad.addIngredient("Огірок", 150.0, 15.0);
        salad.addIngredient("Оливкова олія", 15.0, 884.0);

        salad.showInfo();
    }

    /** Демонстрація успадкування, перевизначення і поліморфізму. */
    private static void demonstrateInheritanceAndPolymorphism() {
        printSection("3. Клас HotDish, успадкування та поліморфізм");

        HotDish defaultHotDish = new HotDish();
        defaultHotDish.showInfo();

        HotDish borscht = new HotDish(
                "Український борщ",
                60,
                70.0,
                "Варіння");

        borscht.addIngredient("Буряк", 200.0, 43.0);
        borscht.addIngredient("Картопля", 250.0, 77.0);
        borscht.addIngredient("Яловичина", 300.0, 250.0);

        // Тип посилання — Dish, а реальний тип об'єкта — HotDish.
        // Через поліморфізм викликається перевизначений метод HotDish.showInfo().
        Dish polymorphicDish = borscht;
        polymorphicDish.showInfo();
    }

    private static void printTitle(String title) {
        System.out.println("============================================");
        System.out.println(title);
        System.out.println("============================================");
    }

    private static void printSection(String title) {
        System.out.println();
        System.out.println("--- " + title + " ---");
    }
}
