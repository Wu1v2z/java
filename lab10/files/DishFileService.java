package lab10.files;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import lab6.dishes.HotDish;

/** Читає страви з текстового файлу та зберігає їх у бінарному форматі. */
public final class DishFileService {
    private static final int FILE_SIGNATURE = 0x44495348; // ASCII: DISH
    private static final int FILE_VERSION = 1;
    private static final int MAXIMUM_DISH_COUNT = 10_000;

    private DishFileService() {
    }

    /**
     * Читає UTF-8 файл формату:
     * назва;час_приготування;температура_подачі;спосіб_приготування
     */
    public static HotDish[] readFromTextFile(Path path) throws IOException {
        List<HotDish> dishes = new ArrayList<HotDish>();

        try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            String line;
            int lineNumber = 0;

            while ((line = reader.readLine()) != null) {
                lineNumber++;
                String trimmedLine = line.trim();
                if (trimmedLine.isEmpty() || trimmedLine.startsWith("#")) {
                    continue;
                }

                dishes.add(parseTextLine(trimmedLine, lineNumber));
            }
        }

        if (dishes.isEmpty()) {
            throw new IOException("Текстовий файл не містить даних про страви.");
        }

        return dishes.toArray(new HotDish[dishes.size()]);
    }

    /** Записує масив у власний бінарний формат. */
    public static void writeToBinaryFile(Path path, HotDish[] dishes) throws IOException {
        if (dishes == null) {
            throw new IllegalArgumentException("Масив страв не може бути null.");
        }

        Path parent = path.getParent();
        if (parent != null) {
            Files.createDirectories(parent);
        }

        try (DataOutputStream output = new DataOutputStream(Files.newOutputStream(path))) {
            output.writeInt(FILE_SIGNATURE);
            output.writeInt(FILE_VERSION);
            output.writeInt(dishes.length);

            for (HotDish dish : dishes) {
                if (dish == null) {
                    throw new IllegalArgumentException(
                            "Масив не може містити null-елементи.");
                }
                output.writeUTF(dish.getName());
                output.writeInt(dish.getPreparationTimeMinutes());
                output.writeDouble(dish.getServingTemperatureCelsius());
                output.writeUTF(dish.getCookingMethod());
            }
        }
    }

    /** Читає масив із бінарного файлу та відновлює об'єкти HotDish. */
    public static HotDish[] readFromBinaryFile(Path path) throws IOException {
        try (DataInputStream input = new DataInputStream(Files.newInputStream(path))) {
            int signature = input.readInt();
            if (signature != FILE_SIGNATURE) {
                throw new IOException("Файл не є бінарним файлом страв ЛР10.");
            }

            int version = input.readInt();
            if (version != FILE_VERSION) {
                throw new IOException("Непідтримувана версія бінарного файлу: " + version + ".");
            }

            int dishCount = input.readInt();
            if (dishCount < 0 || dishCount > MAXIMUM_DISH_COUNT) {
                throw new IOException("Некоректна кількість страв у бінарному файлі.");
            }

            HotDish[] dishes = new HotDish[dishCount];
            for (int index = 0; index < dishCount; index++) {
                dishes[index] = new HotDish(
                        input.readUTF(),
                        input.readInt(),
                        input.readDouble(),
                        input.readUTF());
            }
            return dishes;
        } catch (EOFException exception) {
            throw new IOException("Бінарний файл пошкоджений або записаний не повністю.", exception);
        }
    }

    private static HotDish parseTextLine(String line, int lineNumber) throws IOException {
        String[] fields = line.split(";", -1);
        if (fields.length != 4) {
            throw new IOException(
                    "Рядок " + lineNumber + " повинен містити чотири поля через крапку з комою.");
        }

        try {
            String name = fields[0].trim();
            int preparationTime = Integer.parseInt(fields[1].trim());
            double servingTemperature = Double.parseDouble(
                    fields[2].trim().replace(',', '.'));
            String cookingMethod = fields[3].trim();

            return new HotDish(
                    name,
                    preparationTime,
                    servingTemperature,
                    cookingMethod);
        } catch (NumberFormatException exception) {
            throw new IOException(
                    "Рядок " + lineNumber + " містить некоректне числове значення.",
                    exception);
        } catch (IllegalArgumentException exception) {
            throw new IOException(
                    "Некоректні дані в рядку " + lineNumber + ": " + exception.getMessage(),
                    exception);
        }
    }
}
