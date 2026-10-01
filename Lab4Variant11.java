
            for (int i = 0; i < numberOfValues; i++) {
                double x = START_X + i * STEP;

                if (!isInDomain(x, y)) {
                    System.out.printf(
                            Locale.US,
                            "%-4d %-12.6f %s%n",
                            i + 1,
                            x,
                            "пропущено: порушено ОДЗ"
                    );
                    continue;
                }

                double result = calculateExpression(x, y);
                System.out.printf(Locale.US, "%-4d %-12.6f %-14.10f%n", i + 1, x, result);
            }

            System.out.println("\nОбчислення циклом while:");
            System.out.println("--------------------------------");
            System.out.printf("%-4s %-12s %-14s%n", "№", "x", "I");

            int i = 0;
            while (i < numberOfValues) {
                double x = START_X + i * STEP;
                int rowNumber = i + 1;
                i++;

                if (!isInDomain(x, y)) {
                    System.out.printf(
                            Locale.US,
                            "%-4d %-12.6f %s%n",
                            rowNumber,
                            x,
                            "пропущено: порушено ОДЗ"
                    );
                    continue;
                }

                double result = calculateExpression(x, y);
                System.out.printf(Locale.US, "%-4d %-12.6f %-14.10f%n", rowNumber, x, result);
            }
        } catch (InputMismatchException exception) {
            System.out.println("Помилка введення: потрібно ввести дійсне число через крапку.");
        } finally {
            scanner.close();
        }
    }

    private static boolean isInDomain(double x, double y) {
        if (!Double.isFinite(x) || !Double.isFinite(y)) {
            return false;
        }

        double sqrtArgument = 1.0 + Math.pow(Math.cos(y), 2);
        double logArgument = Math.sqrt(sqrtArgument);
        double denominator = Math.exp(y) + Math.pow(Math.sin(x), 2);

        return (sqrtArgument >= 0.0)
                && (logArgument > 0.0)
                && (denominator != 0.0)
                && Double.isFinite(denominator);
    }

    private static double calculateExpression(double x, double y) {
        double numerator = 2.33
                * Math.log(Math.sqrt(1.0 + Math.pow(Math.cos(y), 2)));
        double denominator = Math.exp(y) + Math.pow(Math.sin(x), 2);

        return numerator / denominator;
    }
}