package lab6.dishes;

import java.util.Locale;

/** Окремий продукт, який може входити до складу страви. */
public class Ingredient {
    private String name;
    private double weightGrams;
    private double caloriesPer100Grams;

    public Ingredient() {
        this("Невідомий інгредієнт", 0.0, 0.0);
    }

    public Ingredient(String name, double weightGrams, double caloriesPer100Grams) {
        setName(name);
        setWeightGrams(weightGrams);
        setCaloriesPer100Grams(caloriesPer100Grams);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Назва інгредієнта не може бути порожньою.");
        }
        this.name = name;
    }

    public double getWeightGrams() {
        return weightGrams;
    }

    public void setWeightGrams(double weightGrams) {
        requireNonNegativeFinite(weightGrams, "Маса");
        this.weightGrams = weightGrams;
    }

    public double getCaloriesPer100Grams() {
        return caloriesPer100Grams;
    }

    public void setCaloriesPer100Grams(double caloriesPer100Grams) {
        requireNonNegativeFinite(caloriesPer100Grams, "Калорійність");
        this.caloriesPer100Grams = caloriesPer100Grams;
    }

    public double calculateCalories() {
        return weightGrams * caloriesPer100Grams / 100.0;
    }

    /** Виводить основну інформацію про інгредієнт. */
    public void showInfo() {
        System.out.printf(
                Locale.US,
                "Інгредієнт: %s, маса: %.1f г, калорійність: %.1f ккал%n",
                name,
                weightGrams,
                calculateCalories());
    }

    private static void requireNonNegativeFinite(double value, String fieldName) {
        if (!Double.isFinite(value) || value < 0.0) {
            throw new IllegalArgumentException(fieldName + " має бути невід'ємним скінченним числом.");
        }
    }
}
