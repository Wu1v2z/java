package project1.core;

/** Спільна математична логіка лабораторних робіт №1–4. */
public final class ExpressionService {
    public static final int VARIANT = 11;
    public static final double START_X = -10.0 - 2.5 * VARIANT;
    public static final double END_X = 5.0 + 1.2 * VARIANT;
    public static final double STEP = 0.5 + VARIANT / 20.0;

    private ExpressionService() {
    }

    public static double calculateUnchecked(double x, double y) {
        double numerator = 2.33
                * Math.log(Math.sqrt(1.0 + Math.pow(Math.cos(y), 2)));
        double denominator = Math.exp(y) + Math.pow(Math.sin(x), 2);
        return numerator / denominator;
    }

    public static double calculate(double x, double y) {
        validateDomain(x, y);
        return calculateUnchecked(x, y);
    }

    public static boolean isInDomain(double x, double y) {
        if (!Double.isFinite(x) || !Double.isFinite(y)) {
            return false;
        }

        double sqrtArgument = getSqrtArgument(y);
        double logArgument = Math.sqrt(sqrtArgument);
        double denominator = getDenominator(x, y);

        return sqrtArgument >= 0.0
                && logArgument > 0.0
                && denominator != 0.0
                && Double.isFinite(denominator);
    }

    public static void validateDomain(double x, double y) {
        if (!isInDomain(x, y)) {
            throw new IllegalArgumentException(
                    "Значення x та y не належать області допустимих значень виразу.");
        }
    }

    public static double getSqrtArgument(double y) {
        return 1.0 + Math.pow(Math.cos(y), 2);
    }

    public static double getDenominator(double x, double y) {
        return Math.exp(y) + Math.pow(Math.sin(x), 2);
    }

    public static int getNumberOfValues() {
        return (int) Math.floor((END_X - START_X) / STEP) + 1;
    }

    public static double parseNumber(String text) {
        return Double.parseDouble(text.trim().replace(',', '.'));
    }
}
