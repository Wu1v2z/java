package lab8.menu;

import java.text.Collator;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Locale;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import lab6.dishes.HotDish;

/**
 * Каталог, який агрегує масив гарячих страв і виконує операції над ним.
 */
public class HotDishCatalog {
    private static final int MINIMUM_DISH_COUNT = 10;

    private final HotDish[] dishes;

    public HotDishCatalog(HotDish[] dishes) {
        if (dishes == null || dishes.length < MINIMUM_DISH_COUNT) {
            throw new IllegalArgumentException(
                    "Масив повинен містити щонайменше " + MINIMUM_DISH_COUNT + " страв.");
        }

        this.dishes = Arrays.copyOf(dishes, dishes.length);
        for (HotDish dish : this.dishes) {
            if (dish == null) {
                throw new IllegalArgumentException("Масив не може містити null-елементи.");
            }
        }
    }

    public int size() {
        return dishes.length;
    }

    /** Виводить усі елементи масиву у вигляді компактної таблиці. */
    public void printAll() {
        printDishes(dishes);
    }

    /**
     * Змінює поля страви з указаним індексом.
     */
    public void updateDish(
            int index,
            String name,
            int preparationTimeMinutes,
            double servingTemperatureCelsius,
            String cookingMethod) {
        requireValidIndex(index);

        // Спочатку перевіряємо всі нові значення, щоб у разі помилки
        // початковий об'єкт не залишився зміненим лише частково.
        new HotDish(
                name,
                preparationTimeMinutes,
                servingTemperatureCelsius,
                cookingMethod);

        HotDish dish = dishes[index];
        dish.setName(name);
        dish.setPreparationTimeMinutes(preparationTimeMinutes);
        dish.setServingTemperatureCelsius(servingTemperatureCelsius);
        dish.setCookingMethod(cookingMethod);
    }

    /**
     * Відбирає страви, час приготування яких не перевищує задану межу.
     */
    public HotDish[] findDishesPreparedWithin(int maximumMinutes) {
        if (maximumMinutes < 0) {
            throw new IllegalArgumentException("Максимальний час не може бути від'ємним.");
        }

        int matchCount = 0;
        for (HotDish dish : dishes) {
            if (dish.getPreparationTimeMinutes() <= maximumMinutes) {
                matchCount++;
            }
        }

        HotDish[] matchingDishes = new HotDish[matchCount];
        int resultIndex = 0;
        for (HotDish dish : dishes) {
            if (dish.getPreparationTimeMinutes() <= maximumMinutes) {
                matchingDishes[resultIndex] = dish;
                resultIndex++;
            }
        }
        return matchingDishes;
    }

    /** Сортує масив за зростанням часу приготування. */
    public void sortByPreparationTimeAscending() {
        Arrays.sort(dishes, new Comparator<HotDish>() {
            @Override
            public int compare(HotDish first, HotDish second) {
                return Integer.compare(
                        first.getPreparationTimeMinutes(),
                        second.getPreparationTimeMinutes());
            }
        });
    }

    /** Сортує масив за спаданням часу приготування. */
    public void sortByPreparationTimeDescending() {
        Arrays.sort(dishes, new Comparator<HotDish>() {
            @Override
            public int compare(HotDish first, HotDish second) {
                return Integer.compare(
                        second.getPreparationTimeMinutes(),
                        first.getPreparationTimeMinutes());
            }
        });
    }

    /** Сортує масив за назвою в алфавітному порядку. */
    public void sortByNameAscending() {
        sortByName(true);
    }

    /** Сортує масив за назвою у зворотному алфавітному порядку. */
    public void sortByNameDescending() {
        sortByName(false);
    }

    /**
     * Відбирає страви, назви яких відповідають регулярному виразу.
     * Пошук не залежить від регістру символів.
     */
    public HotDish[] findDishesByNamePattern(String regularExpression) {
        if (regularExpression == null || regularExpression.trim().isEmpty()) {
            throw new IllegalArgumentException("Регулярний вираз не може бути порожнім.");
        }

        final Pattern pattern;
        try {
            pattern = Pattern.compile(
                    regularExpression,
                    Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);
        } catch (PatternSyntaxException exception) {
            throw new IllegalArgumentException(
                    "Некоректний регулярний вираз: " + exception.getDescription());
        }

        int matchCount = 0;
        for (HotDish dish : dishes) {
            if (pattern.matcher(dish.getName()).find()) {
                matchCount++;
            }
        }

        HotDish[] matchingDishes = new HotDish[matchCount];
        int resultIndex = 0;
        for (HotDish dish : dishes) {
            if (pattern.matcher(dish.getName()).find()) {
                matchingDishes[resultIndex] = dish;
                resultIndex++;
            }
        }
        return matchingDishes;
    }

    /** Повертає страву з найменшим часом приготування. */
    public HotDish findDishWithMinimumPreparationTime() {
        HotDish result = dishes[0];
        for (int index = 1; index < dishes.length; index++) {
            if (dishes[index].getPreparationTimeMinutes()
                    < result.getPreparationTimeMinutes()) {
                result = dishes[index];
            }
        }
        return result;
    }

    /** Повертає страву з найбільшим часом приготування. */
    public HotDish findDishWithMaximumPreparationTime() {
        HotDish result = dishes[0];
        for (int index = 1; index < dishes.length; index++) {
            if (dishes[index].getPreparationTimeMinutes()
                    > result.getPreparationTimeMinutes()) {
                result = dishes[index];
            }
        }
        return result;
    }

    public static void printDishes(HotDish[] dishes) {
        if (dishes.length == 0) {
            System.out.println("Страв, що відповідають умові, не знайдено.");
            return;
        }

        System.out.println("--------------------------------------------------------------------------------");
        System.out.printf(
                "%-4s %-28s %-10s %-12s %-20s%n",
                "№", "Назва", "Час, хв", "Подача, °C", "Спосіб");
        System.out.println("--------------------------------------------------------------------------------");

        for (int index = 0; index < dishes.length; index++) {
            HotDish dish = dishes[index];
            System.out.printf(
                    Locale.US,
                    "%-4d %-28s %-10d %-12.1f %-20s%n",
                    index + 1,
                    dish.getName(),
                    dish.getPreparationTimeMinutes(),
                    dish.getServingTemperatureCelsius(),
                    dish.getCookingMethod());
        }

        System.out.println("--------------------------------------------------------------------------------");
    }

    private void requireValidIndex(int index) {
        if (index < 0 || index >= dishes.length) {
            throw new IllegalArgumentException(
                    "Номер елемента має бути від 1 до " + dishes.length + ".");
        }
    }

    private void sortByName(final boolean ascending) {
        final Collator ukrainianCollator = Collator.getInstance(new Locale("uk", "UA"));
        ukrainianCollator.setStrength(Collator.PRIMARY);

        Arrays.sort(dishes, new Comparator<HotDish>() {
            @Override
            public int compare(HotDish first, HotDish second) {
                int result = ukrainianCollator.compare(first.getName(), second.getName());
                return ascending ? result : -result;
            }
        });
    }
}
