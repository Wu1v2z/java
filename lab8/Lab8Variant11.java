package lab8;

import java.util.Locale;
import java.util.Scanner;
import lab6.dishes.HotDish;
import lab8.menu.HotDishCatalog;

/**
 * Лабораторна робота №8, варіант 11 — «Їстівна страва».
 *
 * <p>Програма демонструє створення масиву об'єктів, зміну вибраного
 * елемента, відбір за умовою та сортування за числовим полем.</p>
 */
public class Lab8Variant11 {
    private static final Scanner SCANNER = new Scanner(System.in);

    public static void main(String[] args) {
        SCANNER.useLocale(Locale.US);
        HotDishCatalog catalog = new HotDishCatalog(createInitialDishes());

        printTitle();
        System.out.println("Початковий масив страв:");
        catalog.printAll();

        runMenu(catalog);
        SCANNER.close();
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
                        updateSelectedDish(catalog);
                        break;
                    case 3:
                        filterDishes(catalog);
                        break;
                    case 4:
                        catalog.sortByPreparationTimeAscending();
                        System.out.println("Масив відсортовано за зростанням часу приготування:");
                        catalog.printAll();
                        break;
                    case 5:
                        catalog.sortByPreparationTimeDescending();
                        System.out.println("Масив відсортовано за спаданням часу приготування:");
                        catalog.printAll();
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

    private static void updateSelectedDish(HotDishCatalog catalog) {
        catalog.printAll();
        int dishNumber = readInt("Номер страви для зміни: ");
        if (dishNumber < 1 || dishNumber > catalog.size()) {
            throw new IllegalArgumentException(
                    "Номер елемента має бути від 1 до " + catalog.size() + ".");
        }
        String name = readNonEmptyLine("Нова назва: ");
        int preparationTime = readInt("Новий час приготування, хв: ");
        double servingTemperature = readDouble("Нова температура подачі, °C: ");
        String cookingMethod = readNonEmptyLine("Новий спосіб приготування: ");

        catalog.updateDish(
                dishNumber - 1,
                name,
                preparationTime,
                servingTemperature,
                cookingMethod);

        System.out.println("Елемент №" + dishNumber + " успішно змінено:");
        catalog.printAll();
    }

    private static void filterDishes(HotDishCatalog catalog) {
        int maximumMinutes = readInt("Максимальний час приготування, хв: ");
        HotDish[] matchingDishes = catalog.findDishesPreparedWithin(maximumMinutes);

        System.out.println(
                "Страви з часом приготування не більше " + maximumMinutes + " хв:");
        HotDishCatalog.printDishes(matchingDishes);
    }

    private static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = SCANNER.nextLine().trim();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException exception) {
                System.out.println("Помилка введення: введіть ціле число.");
            }
        }
    }

    private static double readDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = SCANNER.nextLine().trim().replace(',', '.');
            try {
                double value = Double.parseDouble(input);
                if (Double.isFinite(value)) {
                    return value;
                }
            } catch (NumberFormatException exception) {
                // Повідомлення виводиться нижче для всіх помилкових значень.
            }
            System.out.println("Помилка введення: введіть скінченне дійсне число.");
        }
    }

    private static String readNonEmptyLine(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = SCANNER.nextLine().trim();
            if (!input.isEmpty()) {
                return input;
            }
            System.out.println("Помилка введення: значення не може бути порожнім.");
        }
    }

    private static void printTitle() {
        System.out.println("============================================================");
        System.out.println("ЛР8. Масив об'єктів «Їстівна страва», варіант 11");
        System.out.println("============================================================");
    }

    private static void printMenu() {
        System.out.println();
        System.out.println("Меню:");
        System.out.println("1 — вивести масив");
        System.out.println("2 — змінити вибраний елемент");
        System.out.println("3 — відібрати швидкі страви за часом приготування");
        System.out.println("4 — сортувати час за зростанням");
        System.out.println("5 — сортувати час за спаданням");
        System.out.println("0 — завершити програму");
    }
}
