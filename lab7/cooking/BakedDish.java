package lab7.cooking;

/** Конкретна реалізація абстрактної приготовленої страви — запечена страва. */
public class BakedDish extends AbstractCookedDish {
    private int ovenTemperatureCelsius;

    public BakedDish() {
        this("Запечена страва без назви", 40, 60.0, 1, 180);
    }

    public BakedDish(
            String name,
            int preparationTimeMinutes,
            double servingTemperatureCelsius,
            int difficultyLevel,
            int ovenTemperatureCelsius) {
        super(
                name,
                preparationTimeMinutes,
                servingTemperatureCelsius,
                "Запікання",
                difficultyLevel);
        setOvenTemperatureCelsius(ovenTemperatureCelsius);
    }

    public int getOvenTemperatureCelsius() {
        return ovenTemperatureCelsius;
    }

    public void setOvenTemperatureCelsius(int ovenTemperatureCelsius) {
        if (ovenTemperatureCelsius < 50 || ovenTemperatureCelsius > 300) {
            throw new IllegalArgumentException("Температура духовки має бути від 50 до 300 °C.");
        }
        this.ovenTemperatureCelsius = ovenTemperatureCelsius;
    }

    @Override
    public String getDishCategory() {
        return "Друга страва — запечена";
    }

    @Override
    public String getCookingInstructions() {
        return "Викласти інгредієнти у форму та запікати до утворення рум'яної скоринки.";
    }

    @Override
    public void cook() {
        System.out.println(
                "Запікаємо страву «" + getName()
                        + "» при " + ovenTemperatureCelsius + " °C.");
    }

    @Override
    public void showInfo() {
        super.showInfo();
        System.out.println("Температура духовки: " + ovenTemperatureCelsius + " °C");
    }
}
