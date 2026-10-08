package lab6.dishes;

import java.util.Locale;

/**
 * Різновид страви, який має спосіб приготування і температуру подачі.
 */
public class HotDish extends Dish {
    private double servingTemperatureCelsius;
    private String cookingMethod;

    public HotDish() {
        this("Гаряча страва без назви", 0, 60.0, "Варіння");
    }

    public HotDish(
            String name,
            int preparationTimeMinutes,
            double servingTemperatureCelsius,
            String cookingMethod) {
        super(name, preparationTimeMinutes);
        setServingTemperatureCelsius(servingTemperatureCelsius);
        setCookingMethod(cookingMethod);
    }

    public double getServingTemperatureCelsius() {
        return servingTemperatureCelsius;
    }

    public void setServingTemperatureCelsius(double servingTemperatureCelsius) {
        if (!Double.isFinite(servingTemperatureCelsius)) {
            throw new IllegalArgumentException("Температура має бути скінченним числом.");
        }
        this.servingTemperatureCelsius = servingTemperatureCelsius;
    }

    public String getCookingMethod() {
        return cookingMethod;
    }

    public void setCookingMethod(String cookingMethod) {
        if (cookingMethod == null || cookingMethod.trim().isEmpty()) {
            throw new IllegalArgumentException("Спосіб приготування не може бути порожнім.");
        }
        this.cookingMethod = cookingMethod;
    }

    public boolean isServingTemperatureSafe() {
        return servingTemperatureCelsius >= 55.0 && servingTemperatureCelsius <= 75.0;
    }

    /** Перевизначений метод додає інформацію, властиву гарячій страві. */
    @Override
    public void showInfo() {
        System.out.println("Тип: гаряча страва");
        super.showInfo(true);
        System.out.printf(Locale.US, "Спосіб приготування: %s%n", cookingMethod);
        System.out.printf(Locale.US, "Температура подачі: %.1f °C%n", servingTemperatureCelsius);
        System.out.println(
                "Температура в рекомендованому діапазоні 55-75 °C: "
                        + (isServingTemperatureSafe() ? "так" : "ні"));
    }
}
