import java.util.Locale;
import java.util.Scanner;

public class Lab1Variant11 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        scanner.useLocale(Locale.US);

        System.out.print("Введіть значення x у радіанах (дробову частину через крапку): ");
        double x = scanner.nextDouble();
        double y = 1.0;

        double numerator = 2.33
                * Math.log(Math.sqrt(1.0 + Math.pow(Math.cos(y), 2)));
        double denominator = Math.exp(y) + Math.pow(Math.sin(x), 2);
        double result = numerator / denominator;

        System.out.printf(Locale.US, "x = %.6f%n", x);
        System.out.printf(Locale.US, "y = %.6f%n", y);
        System.out.printf(Locale.US, "I = %.10f%n", result);

        scanner.close();
    }
}
