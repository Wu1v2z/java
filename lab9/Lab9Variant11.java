package lab9;

import common.ConsoleEncoding;
import java.util.Locale;
import java.util.Scanner;
import lab6.dishes.HotDish;
import lab8.menu.HotDishCatalog;

/**
 * Лабораторна робота №9, варіант 11 — «Їстівна страва».
 *
 * <p>Програма розширює ЛР8 сортуванням за текстовим полем,
 * пошуком за регулярним виразом і визначенням мінімального
 * та максимального значень числового поля.</p>
 */
public class Lab9Variant11 {
    private static Scanner scanner;

    public static void main(String[] args) {
        ConsoleEncoding.useUtf8();
        scanner = new Scanner(System.in, "UTF-8");
        scanner.useLocale(Locale.US);

        HotDishCatalog catalog = new HotDishCatalog(createInitialDishes());

        printTitle();
        System.out.println("Початковий масив страв:");
        catalog.printAll();

        runMenu(catalog);
        scanner.close();
    }

    private static HotDish[] createInitialDishes() {
        return new HotDish[] {
            new HotDish("Український борщ", 60, 70.0, "Варіння"),
            new HotDish("Овочевий суп", 35, 68.0, "Варіння"),
            new HotDish("Картопляне пюре", 30, 60.0, "Варіння"),
            new HotDish("Куряча відбивна", 25, 65.0, "Смаження"),
            new HotDish("Гречана каша", 20, 62.0, "Варіння"),
            new HotDish("Овочеве рагу", 45, 67.0, "Тушкування"),
            new HotDish("Запечена риба", 50, 64.0, "Запікання"),
            new HotDish("Сирники", 25, 58.0, "Смаження"),
            new HotDish("Плов", 55, 69.0, "Тушкування"),
            new HotDish("Вареники", 40, 63.0, "Варіння")
        };
    }

    private static void runMenu(HotDishCatalog catalog) {
        boolean isRunning = true;

        while (isRunning) {
            printMenu();
            int choice = readInt("Оберіть дію: ");
            System.out.println();

            try {
                switch (choice) {
                    case 1:
                        catalog.printAll();
                        break;
                    case 2:
                        catalog.sortByNameAscending();
                        System.out.println("Страви за назвою від А до Я:");
                        catalog.printAll();
                        break;
                    case 3:
                        catalog.sortByNameDescending();
                        System.out.println("Страви за назвою від Я до А:");
                        catalog.printAll();
                        break;
                    case 4:
                        filterByNamePattern(catalog);
                        break;
                    case 5:
                        printPreparationTimeExtremes(catalog);
                        break;
                    case 0:
                        isRunning = false;
                        System.out.println("Роботу програми завершено.");
                        break;
                    default:
                        System.out.println("Невідома команда. Введіть число від 0 до 5.");
                        break;
                }
            } catch (IllegalArgumentException exception) {
                System.out.println("Помилка: " + exception.getMessage());
            }
        }
    }

    private static void filterByNamePattern(HotDishCatalog catalog) {
        System.out.print(
                "Введіть регулярний вираз для назви, наприклад ^К або ка$: ");
        String regularExpression = scanner.nextLine().trim();
        HotDish[] matchingDishes = catalog.findDishesByNamePattern(regularExpression);

        System.out.println("Страви, назви яких відповідають виразу «"
                + regularExpression + "»:");
        HotDishCatalog.printDishes(matchingDishes);
    }

    private static void printPreparationTimeExtremes(HotDishCatalog catalog) {
        HotDish minimum = catalog.findDishWithMinimumPreparationTime();
        HotDish maximum = catalog.findDishWithMaximumPreparationTime();

        System.out.println(
                "Найменший час приготування — "
                        + minimum.getPreparationTimeMinutes()
                        + " хв, його має страва «"
                        + minimum.getName()
                        + "».");
        System.out.println(
                "Найбільший час приготування — "
                        + maximum.getPreparationTimeMinutes()
                        + " хв, його має страва «"
                        + maximum.getName()
                        + "».");
    }

    private static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException exception) {
                System.out.println("Помилка введення: введіть ціле число.");
            }
        }
    }

    private static void printTitle() {
        System.out.println("============================================================");
        System.out.println("ЛР9. Пошук і сортування страв, варіант 11");
        System.out.println("============================================================");
    }

    private static void printMenu() {
        System.out.println();
        System.out.println("Меню:");
        System.out.println("1 — вивести масив");
        System.out.println("2 — сортувати назви від А до Я");
        System.out.println("3 — сортувати назви від Я до А");
        System.out.println("4 — знайти страви за регулярним виразом");
        System.out.println("5 — показати мінімальний і максимальний час приготування");
        System.out.println("0 — завершити програму");
    }
}
