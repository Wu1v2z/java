package lab7;

import lab7.cooking.AbstractCookedDish;
import lab7.cooking.BakedDish;
import lab7.cooking.Cookable;
import lab7.cooking.Soup;

/**
 * Лабораторна робота №7, варіант 11 — «Їстівна страва».
 *
 * <p>Програма доповнює систему класів ЛР6 інтерфейсом,
 * абстрактним класом і двома конкретними реалізаціями.</p>
 */
public class Lab7Variant11 {
    public static void main(String[] args) {
        printTitle("ЛР7. Абстрактні класи та інтерфейси");

        Soup borscht = createBorscht();
        BakedDish casserole = createCasserole();

        AbstractCookedDish[] menu = {borscht, casserole};
        demonstrateDishes(menu);
    }

    private static Soup createBorscht() {
        Soup borscht = new Soup(
                "Український борщ",
                60,
                70.0,
                3,
                3.0);

        borscht.addIngredient("Буряк", 200.0, 43.0);
        borscht.addIngredient("Картопля", 250.0, 77.0);
        borscht.addIngredient("Яловичина", 300.0, 250.0);
        return borscht;
    }

    private static BakedDish createCasserole() {
        BakedDish casserole = new BakedDish(
                "Картопляна запіканка",
                50,
                65.0,
                2,
                180);

        casserole.addIngredient("Картопля", 500.0, 77.0);
        casserole.addIngredient("Сир", 150.0, 350.0);
        casserole.addIngredient("Яйце", 100.0, 155.0);
        return casserole;
    }

    private static void demonstrateDishes(AbstractCookedDish[] menu) {
        for (AbstractCookedDish dish : menu) {
            printSection(dish.getName());

            // Поліморфний виклик перевизначеного методу showInfo().
            dish.showInfo();

            // Метод абстрактного класу звертається до реалізацій
            // абстрактного й інтерфейсного методів конкретного об'єкта.
            dish.showCookingPlan();

            // Об'єкт конкретного класу використовується через інтерфейс.
            Cookable cookable = dish;
            cookable.cook();
        }
    }

    private static void printTitle(String title) {
        System.out.println("================================================");
        System.out.println(title);
        System.out.println("================================================");
    }

    private static void printSection(String title) {
        System.out.println();
        System.out.println("--- " + title + " ---");
    }
}
