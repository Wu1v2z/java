package lab7.cooking;

import java.util.Locale;

/** Конкретна реалізація абстрактної приготовленої страви — суп. */
public class Soup extends AbstractCookedDish {
    private double liquidVolumeLiters;

    public Soup() {
        this("Суп без назви", 30, 65.0, 1, 1.0);
    }

    public Soup(
            String name,
            int preparationTimeMinutes,
            double servingTemperatureCelsius,
            int difficultyLevel,
            double liquidVolumeLiters) {
        super(
                name,
                preparationTimeMinutes,
                servingTemperatureCelsius,
                "Варіння",
                difficultyLevel);
        setLiquidVolumeLiters(liquidVolumeLiters);
    }

    public double getLiquidVolumeLiters() {
        return liquidVolumeLiters;
    }

    public void setLiquidVolumeLiters(double liquidVolumeLiters) {
        if (!Double.isFinite(liquidVolumeLiters) || liquidVolumeLiters <= 0.0) {
            throw new IllegalArgumentException("Об'єм супу має бути додатним скінченним числом.");
        }
        this.liquidVolumeLiters = liquidVolumeLiters;
    }

    @Override
    public String getDishCategory() {
        return "Перша страва — суп";
    }

    @Override
    public String getCookingInstructions() {
        return "Підготувати інгредієнти, додати їх у воду та варити до готовності.";
    }

    @Override
    public void cook() {
        System.out.println(
                "Виконується варіння супу «" + getName()
                        + "» об'ємом " + liquidVolumeLiters + " л.");
    }

    @Override
    public void showInfo() {
        super.showInfo();
        System.out.printf(Locale.US, "Об'єм супу: %.1f л%n", liquidVolumeLiters);
    }
}
