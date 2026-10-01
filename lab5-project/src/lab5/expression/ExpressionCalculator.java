package lab5.expression;

public class ExpressionCalculator {
    private double x;
    private double y;

    public ExpressionCalculator() {
        this(1.0, 1.0);
    }

    public ExpressionCalculator(double x, double y) {
        setX(x);
        setY(y);
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        if (!Double.isFinite(x)) {
            throw new IllegalArgumentException("x повинен бути скінченним дійсним числом.");
        }

        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        if (!Double.isFinite(y)) {
            throw new IllegalArgumentException("y повинен бути скінченним дійсним числом.");
        }

        this.y = y;
    }

    public double calculate() {
        validateDomain(x, y);

        double numerator = 2.33
                * Math.log(Math.sqrt(1.0 + Math.pow(Math.cos(y), 2)));
        double denominator = Math.exp(y) + Math.pow(Math.sin(x), 2);

        return numerator / denominator;
    }

    public static double calculate(double x, double y) {
        validateDomain(x, y);

        double numerator = 2.33
                * Math.log(Math.sqrt(1.0 + Math.pow(Math.cos(y), 2)));
        double denominator = Math.exp(y) + Math.pow(Math.sin(x), 2);

        return numerator / denominator;
    }

    private static void validateDomain(double x, double y) {
        if (!Double.isFinite(x) || !Double.isFinite(y)) {
            throw new IllegalArgumentException("x та y повинні бути скінченними дійсними числами.");
        }

        double sqrtArgument = 1.0 + Math.pow(Math.cos(y), 2);
        double logArgument = Math.sqrt(sqrtArgument);
        double denominator = Math.exp(y) + Math.pow(Math.sin(x), 2);

        boolean isInDomain = (sqrtArgument >= 0.0)
                && (logArgument > 0.0)
                && (denominator != 0.0)
                && Double.isFinite(denominator);

        if (!isInDomain) {
            throw new IllegalArgumentException(
                    "Значення аргументів не належать області допустимих значень виразу."
            );
        }
    }
}
