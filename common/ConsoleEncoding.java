package common;

import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.Locale;

/** Налаштовує коректне UTF-8 введення та виведення у Windows-терміналі. */
public final class ConsoleEncoding {
    private ConsoleEncoding() {
    }

    public static void useUtf8() {
        if (isWindows()) {
            switchWindowsConsoleToUtf8();
        }

        try {
            System.setOut(new PrintStream(
                    new FileOutputStream(FileDescriptor.out), true, "UTF-8"));
            System.setErr(new PrintStream(
                    new FileOutputStream(FileDescriptor.err), true, "UTF-8"));
        } catch (UnsupportedEncodingException exception) {
            throw new IllegalStateException("Поточна Java не підтримує UTF-8.", exception);
        }
    }

    private static boolean isWindows() {
        return System.getProperty("os.name", "")
                .toLowerCase(Locale.ROOT)
                .contains("win");
    }

    private static void switchWindowsConsoleToUtf8() {
        try {
            Process process = new ProcessBuilder(
                    "cmd.exe", "/d", "/c", "chcp 65001 > nul")
                    .inheritIO()
                    .start();
            process.waitFor();
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
        } catch (IOException exception) {
            // Якщо кодову сторінку змінити не вдалося, програма все одно
            // продовжить роботу з UTF-8 потоками.
        }
    }
}
