package lab6.dishes;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Їстівна страва, яка агрегує декілька об'єктів Ingredient.
 */
public class Dish {
    private String name;
    private int preparationTimeMinutes;
    private final List<Ingredient> ingredients;

    public Dish() {
        this("Страва без назви", 0);
    }

    public Dish(String name, int preparationTimeMinutes) {
        ingredients = new ArrayList<Ingredient>();
        setName(name);
        setPreparationTimeMinutes(preparationTimeMinutes);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Назва страви не може бути порожньою.");
        }
        this.name = name;
    }

    public int getPreparationTimeMinutes() {
        return preparationTimeMinutes;
    }

    public void setPreparationTimeMinutes(int preparationTimeMinutes) {
        if (preparationTimeMinutes < 0) {
            throw new IllegalArgumentException("Час приготування не може бути від'ємним.");
        }
        this.preparationTimeMinutes = preparationTimeMinutes;
    }

    /** Додає до страви вже створений об'єкт Ingredient. */
    public void addIngredient(Ingredient ingredient) {
        if (ingredient == null) {
            throw new IllegalArgumentException("Інгредієнт не може бути null.");
        }
        ingredients.add(ingredient);
    }

    /** Перевантажена версія: створює Ingredient із переданих значень. */
    public void addIngredient(String name, double weightGrams, double caloriesPer100Grams) {
        addIngredient(new Ingredient(name, weightGrams, caloriesPer100Grams));
    }

    public double calculateTotalCalories() {
        double totalCalories = 0.0;
        for (Ingredient ingredient : ingredients) {
            totalCalories += ingredient.calculateCalories();
        }
        return totalCalories;
    }

    public void showInfo() {
        showInfo(true);
    }

    /** Перевантажена версія дозволяє приховати або показати склад страви. */
    public void showInfo(boolean showIngredients) {
        System.out.printf(
                Locale.US,
                "Страва: %s, час приготування: %d хв, загальна калорійність: %.1f ккал%n",
                name,
                preparationTimeMinutes,
                calculateTotalCalories());

        if (showIngredients) {
            System.out.println("Склад:");
            if (ingredients.isEmpty()) {
                System.out.println("  інгредієнти ще не додані");
            } else {
                for (Ingredient ingredient : ingredients) {
                    System.out.print("  ");
                    ingredient.showInfo();
                }
            }
        }
    }
}
