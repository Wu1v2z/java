package lab7.cooking;

import lab6.dishes.HotDish;

/**
 * Спільна основа для страв, які потребують приготування.
 *
 * <p>Клас є абстрактним, тому створити його об'єкт напряму не можна.
 * Він успадковує можливості HotDish із ЛР6 і реалізує інтерфейс Cookable.</p>
 */
public abstract class AbstractCookedDish extends HotDish implements Cookable {
    private int difficultyLevel;

    protected AbstractCookedDish() {
        this("Страва без назви", 0, 60.0, "Не вказано", 1);
    }

    protected AbstractCookedDish(
            String name,
            int preparationTimeMinutes,
            double servingTemperatureCelsius,
            String cookingMethod,
            int difficultyLevel) {
        super(name, preparationTimeMinutes, servingTemperatureCelsius, cookingMethod);
        setDifficultyLevel(difficultyLevel);
    }

    public int getDifficultyLevel() {
        return difficultyLevel;
    }

    public void setDifficultyLevel(int difficultyLevel) {
        if (difficultyLevel < 1 || difficultyLevel > 5) {
            throw new IllegalArgumentException("Рівень складності має бути від 1 до 5.");
        }
        this.difficultyLevel = difficultyLevel;
    }

    /** Кожен конкретний вид страви повинен повідомити свою категорію. */
    public abstract String getDishCategory();

    /**
     * Конкретний метод абстрактного класу. Він використовує методи,
     * реалізацію яких надають класи-нащадки.
     */
    public void showCookingPlan() {
        System.out.println("Категорія: " + getDishCategory());
        System.out.println("Складність: " + difficultyLevel + " із 5");
        System.out.println("Інструкція: " + getCookingInstructions());
    }

    @Override
    public void showInfo() {
        super.showInfo();
        System.out.println("Категорія: " + getDishCategory());
        System.out.println("Рівень складності: " + difficultyLevel + " із 5");
    }
}
