import java.util.Locale;
import java.util.Scanner;

public class Lab2Variant11 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        scanner.useLocale(Locale.US);

        System.out.print("Введіть значення x у радіанах (дробову частину через крапку): ");
        double x = scanner.nextDouble();
        double y = 1.0;

        // NaN та нескінченність не належать множині дійсних чисел.
        if (!Double.isFinite(x) || !Double.isFinite(y)) {
            System.out.println("Помилка: x та y повинні бути скінченними дійсними числами.");
        } else {
            double sqrtArgument = 1.0 + Math.pow(Math.cos(y), 2);
            double logArgument = Math.sqrt(sqrtArgument);
            double denominator = Math.exp(y) + Math.pow(Math.sin(x), 2);

            boolean isInDomain = (sqrtArgument >= 0.0)
                    && (logArgument > 0.0)
                    && (denominator != 0.0)
                    && Double.isFinite(denominator);

            if (isInDomain) {
                double numerator = 2.33 * Math.log(logArgument);
                double result = numerator / denominator;

                if (!Double.isNaN(result) && !Double.isInfinite(result)) {
                    System.out.printf(Locale.US, "x = %.6f%n", x);
                    System.out.printf(Locale.US, "y = %.6f%n", y);
                    System.out.printf(Locale.US, "I = %.10f%n", result);
                } else {
                    System.out.println("Помилка: результат не є скінченним числом.");
                }
            } else {
                System.out.println("Помилка: значення змінних не належать області допустимих значень виразу.");
            }
        }

        scanner.close();
    }
}