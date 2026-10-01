package lab5;

import java.util.InputMismatchException;
import java.util.Locale;
import java.util.Scanner;
import lab5.expression.ExpressionCalculator;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        scanner.useLocale(Locale.US);

        try {
            ExpressionCalculator defaultCalculator = new ExpressionCalculator();

            System.out.println("Об'єкт, створений конструктором за замовчуванням:");
            printObjectData(defaultCalculator);
            System.out.printf(
                    Locale.US,
                    "Результат нестатичного методу: %.10f%n%n",
                    defaultCalculator.calculate()
            );

            defaultCalculator.setX(0.5);
            defaultCalculator.setY(1.5);
            System.out.println("Той самий об'єкт після використання setX і setY:");
            printObjectData(defaultCalculator);
            System.out.printf(
                    Locale.US,
                    "Новий результат нестатичного методу: %.10f%n%n",
                    defaultCalculator.calculate()
            );

            System.out.print("Введіть x для другого об'єкта: ");
            double x = scanner.nextDouble();

            System.out.print("Введіть y для другого об'єкта: ");
            double y = scanner.nextDouble();

            ExpressionCalculator parameterizedCalculator = new ExpressionCalculator(x, y);

            System.out.println("\nОб'єкт, створений конструктором із параметрами:");
            printObjectData(parameterizedCalculator);
            System.out.printf(
                    Locale.US,
                    "Результат нестатичного методу: %.10f%n",
                    parameterizedCalculator.calculate()
            );

            double staticResult = ExpressionCalculator.calculate(
                    parameterizedCalculator.getX(),
                    parameterizedCalculator.getY()
            );

            System.out.printf(
                    Locale.US,
                    "Результат статичного методу:    %.10f%n",
                    staticResult
            );
        } catch (InputMismatchException exception) {
            System.out.println("Помилка введення: потрібно вводити дійсні числа через крапку.");
        } catch (IllegalArgumentException exception) {
            System.out.println("Помилка: " + exception.getMessage());
        } finally {
            scanner.close();
        }
    }

    private static void printObjectData(ExpressionCalculator calculator) {
        System.out.printf(
                Locale.US,
                "x = %.6f, y = %.6f%n",
                calculator.getX(),
                calculator.getY()
        );
    }
}
