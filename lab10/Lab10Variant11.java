package lab10;

import common.ConsoleEncoding;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import lab10.files.DishFileService;
import lab6.dishes.HotDish;
import lab8.menu.HotDishCatalog;

/**
 * Лабораторна робота №10, варіант 11 — «Їстівна страва».
 *
 * <p>Програма читає об'єкти з текстового файлу, сортує їх,
 * записує у бінарний файл і відновлює масив із цього файлу.</p>
 */
public class Lab10Variant11 {
    private static final Path TEXT_FILE =
            Paths.get("lab10", "data", "dishes.txt");
    private static final Path BINARY_FILE =
            Paths.get("lab10", "data", "sorted-dishes.bin");

    public static void main(String[] args) {
        ConsoleEncoding.useUtf8();
        printTitle();

        try {
            HotDish[] dishesFromText = DishFileService.readFromTextFile(TEXT_FILE);
            HotDishCatalog catalog = new HotDishCatalog(dishesFromText);

            printSection("1. Масив, зчитаний із текстового файлу");
            System.out.println("Файл: " + TEXT_FILE.toAbsolutePath());
            catalog.printAll();

            catalog.sortByNameAscending();
            DishFileService.writeToBinaryFile(BINARY_FILE, catalog.toArray());

            printSection("2. Запис відсортованого масиву в нетекстовий файл");
            System.out.println("Масив відсортовано за назвою від А до Я.");
            System.out.println("Бінарний файл створено: " + BINARY_FILE.toAbsolutePath());

            HotDish[] restoredDishes = DishFileService.readFromBinaryFile(BINARY_FILE);

            printSection("3. Масив, відновлений із бінарного файлу");
            HotDishCatalog.printDishes(restoredDishes);
            System.out.println("Успішно відновлено об'єктів: " + restoredDishes.length + ".");
        } catch (IOException exception) {
            System.out.println("Помилка роботи з файлом: " + exception.getMessage());
        } catch (IllegalArgumentException exception) {
            System.out.println("Помилка даних: " + exception.getMessage());
        }
    }

    private static void printTitle() {
        System.out.println("============================================================");
        System.out.println("ЛР10. Робота з файлами, варіант 11");
        System.out.println("============================================================");
    }

    private static void printSection(String title) {
        System.out.println();
        System.out.println("--- " + title + " ---");
    }
}
