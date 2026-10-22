package project1.core;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

/** Перенаправляє консольний вивід старих лабораторних у текстове поле GUI. */
public final class ConsoleCapture {
    private static final Object LOCK = new Object();

    private ConsoleCapture() {
    }

    public static String capture(Runnable action) {
        synchronized (LOCK) {
            PrintStream originalOutput = System.out;
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();

            try {
                PrintStream temporaryOutput = new PrintStream(
                        buffer,
                        true,
                        StandardCharsets.UTF_8.name());
                System.setOut(temporaryOutput);
                action.run();
                temporaryOutput.flush();
                return new String(buffer.toByteArray(), StandardCharsets.UTF_8);
            } catch (Exception exception) {
                return "Не вдалося отримати результат: " + exception.getMessage();
            } finally {
                System.setOut(originalOutput);
            }
        }
    }
}
