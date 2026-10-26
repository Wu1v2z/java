package lab11;

import common.ConsoleEncoding;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import lab11.database.DishDatabase;
import lab11.database.DishRecord;
import lab11.database.DishRepository;
import lab6.dishes.HotDish;

/** Лабораторна робота №11, варіант 11 — клієнт бази даних «Їстівні страви». */
public class Lab11Variant11 {
    private static final Path DEFAULT_DATABASE_DIRECTORY =
            Paths.get("lab11", "data");
    private static final int DEFAULT_SERVER_PORT = 9092;

    private static final Scanner INPUT = new Scanner(System.in, "UTF-8");

    public static void main(String[] args) {
        ConsoleEncoding.useUtf8();
        printTitle();

        Path databaseDirectory = Paths.get(System.getProperty(
                "lab11.db.dir", DEFAULT_DATABASE_DIRECTORY.toString()));
        int serverPort;
        try {
            serverPort = Integer.parseInt(System.getProperty(
                    "lab11.db.port", String.valueOf(DEFAULT_SERVER_PORT)));
        } catch (NumberFormatException exception) {
            System.out.println("Помилка: порт сервера має бути цілим числом.");
            return;
        }

        try (DishDatabase database = new DishDatabase(databaseDirectory, serverPort)) {
            database.start();
            DishRepository repository = new DishRepository(database);
            repository.initializeSchema();
            boolean databaseWasSeeded = repository.seedIfEmpty();

            System.out.println("Сервер: " + database.getServerStatus());
            System.out.println("JDBC URL: " + database.getClientUrl());
            System.out.println("Файли БД: " + database.getDatabaseDirectory());
            System.out.println("Схему БД перевірено: таблиця dishes готова до роботи.");
            if (databaseWasSeeded) {
                System.out.println("Перший запуск: додано 10 страв із попередньої лабораторної.");
            }

            runMenu(repository);
        } catch (ClassNotFoundException exception) {
            System.out.println("Не знайдено JDBC-драйвер H2. Перевірте папку lib.");
        } catch (SQLException exception) {
            System.out.println("Помилка бази даних: " + exception.getMessage());
        } catch (Exception exception) {
            System.out.println("Помилка запуску: " + exception.getMessage());
        }
    }

    private static void runMenu(DishRepository repository) {
        while (true) {
            printMenu();
            int choice = readInt("Ваш вибір: ", 0, 5);

            try {
                switch (choice) {
                    case 1:
                        printRecords("Усі записи бази даних", repository.findAll());
                        break;
                    case 2:
                        addRecord(repository);
                        break;
                    case 3:
                        filterRecords(repository);
                        break;
                    case 4:
                        editRecord(repository);
                        break;
                    case 5:
                        deleteRecord(repository);
                        break;
                    case 0:
                        System.out.println("Роботу завершено. Сервер БД зупинено.");
                        return;
                    default:
                        break;
                }
            } catch (SQLException exception) {
                System.out.println("Помилка SQL-операції: " + friendlySqlMessage(exception));
            }
        }
    }

    private static void addRecord(DishRepository repository) throws SQLException {
        System.out.println("\n--- Додавання нового запису ---");
        HotDish dish = readDish();
        int id = repository.insert(dish);
        System.out.println("Запис успішно додано. Новий ID: " + id + ".");
    }

    private static void filterRecords(DishRepository repository) throws SQLException {
        System.out.println("\n--- Вибірка за часом приготування ---");
        int maximumMinutes = readInt(
                "Максимальний час приготування (0-1440 хв): ", 0, 1440);
        printRecords(
                "Страви, які готуються не довше " + maximumMinutes + " хв",
                repository.findPreparedWithin(maximumMinutes));
    }

    private static void editRecord(DishRepository repository) throws SQLException {
        System.out.println("\n--- Редагування запису ---");
        int id = readInt("ID запису: ", 1, Integer.MAX_VALUE);
        DishRecord current = repository.findById(id);
        if (current == null) {
            System.out.println("Запис з ID " + id + " не знайдено.");
            return;
        }

        printRecords("Поточні дані", java.util.Collections.singletonList(current));
        System.out.println("Введіть нові значення:");
        HotDish updatedDish = readDish();
        if (repository.update(id, updatedDish)) {
            System.out.println("Запис з ID " + id + " успішно оновлено.");
        }
    }

    private static void deleteRecord(DishRepository repository) throws SQLException {
        System.out.println("\n--- Видалення запису ---");
        int id = readInt("ID запису: ", 1, Integer.MAX_VALUE);
        DishRecord current = repository.findById(id);
        if (current == null) {
            System.out.println("Запис з ID " + id + " не знайдено.");
            return;
        }

        printRecords("Запис для видалення", java.util.Collections.singletonList(current));
        if (!readConfirmation("Підтвердити видалення (так/ні): ")) {
            System.out.println("Видалення скасовано.");
            return;
        }

        if (repository.delete(id)) {
            System.out.println("Запис з ID " + id + " видалено.");
        }
    }

    private static HotDish readDish() {
        String name = readNonEmptyLine("Назва: ");
        int preparationTime = readInt("Час приготування (0-1440 хв): ", 0, 1440);
        double servingTemperature = readDouble(
                "Температура подачі (-50..150 °C): ", -50.0, 150.0);
        String cookingMethod = readNonEmptyLine("Спосіб приготування: ");
        return new HotDish(name, preparationTime, servingTemperature, cookingMethod);
    }

    private static void printRecords(String heading, List<DishRecord> records) {
        System.out.println("\n--- " + heading + " ---");
        if (records.isEmpty()) {
            System.out.println("Записів за вказаною умовою немає.");
            return;
        }

        System.out.printf(
                "%-4s %-28s %8s %14s %-18s%n",
                "ID", "Назва", "Час", "Температура", "Спосіб");
        System.out.println("-------------------------------------------------------------------------------");
        for (DishRecord record : records) {
            HotDish dish = record.getDish();
            System.out.printf(
                    Locale.US,
                    "%-4d %-28s %8d %11.1f °C %-18s%n",
                    record.getId(),
                    dish.getName(),
                    dish.getPreparationTimeMinutes(),
                    dish.getServingTemperatureCelsius(),
                    dish.getCookingMethod());
        }
        System.out.println("Кількість записів: " + records.size() + ".");
    }

    private static void printTitle() {
        System.out.println("============================================================");
        System.out.println("ЛР11. База даних, варіант 11 — Їстівна страва");
        System.out.println("============================================================");
    }

    private static void printMenu() {
        System.out.println("\nОперації з БД:");
        System.out.println("1 — переглянути всі записи");
        System.out.println("2 — додати новий запис");
        System.out.println("3 — виконати вибірку за часом приготування");
        System.out.println("4 — редагувати запис");
        System.out.println("5 — видалити запис");
        System.out.println("0 — завершити роботу");
    }

    private static int readInt(String prompt, int minimum, int maximum) {
        while (true) {
            String value = readLine(prompt);
            try {
                int number = Integer.parseInt(value);
                if (number >= minimum && number <= maximum) {
                    return number;
                }
            } catch (NumberFormatException exception) {
                // Нижче буде виведено спільне повідомлення про помилку.
            }
            System.out.println("Введіть ціле число від " + minimum + " до " + maximum + ".");
        }
    }

    private static double readDouble(String prompt, double minimum, double maximum) {
        while (true) {
            String value = readLine(prompt).replace(',', '.');
            try {
                double number = Double.parseDouble(value);
                if (Double.isFinite(number) && number >= minimum && number <= maximum) {
                    return number;
                }
            } catch (NumberFormatException exception) {
                // Нижче буде виведено спільне повідомлення про помилку.
            }
            System.out.printf(
                    Locale.US,
                    "Введіть число від %.1f до %.1f.%n",
                    minimum,
                    maximum);
        }
    }

    private static String readNonEmptyLine(String prompt) {
        while (true) {
            String value = readLine(prompt).trim();
            if (!value.isEmpty()) {
                return value;
            }
            System.out.println("Значення не може бути порожнім.");
        }
    }

    private static boolean readConfirmation(String prompt) {
        while (true) {
            String value = readLine(prompt).trim().toLowerCase(Locale.ROOT);
            if ("так".equals(value) || "т".equals(value)) {
                return true;
            }
            if ("ні".equals(value) || "н".equals(value)) {
                return false;
            }
            System.out.println("Введіть «так» або «ні».");
        }
    }

    private static String readLine(String prompt) {
        System.out.print(prompt);
        return INPUT.nextLine();
    }

    private static String friendlySqlMessage(SQLException exception) {
        String sqlState = exception.getSQLState();
        if ("23505".equals(sqlState)) {
            return "страва з такою назвою вже існує.";
        }
        if ("23513".equals(sqlState)) {
            return "дані порушують обмеження таблиці.";
        }
        return exception.getMessage();
    }
}
