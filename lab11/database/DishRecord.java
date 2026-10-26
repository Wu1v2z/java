package lab11.database;

import lab6.dishes.HotDish;

/** Один запис таблиці dishes: первинний ключ і об'єкт гарячої страви. */
public class DishRecord {
    private final int id;
    private final HotDish dish;

    public DishRecord(int id, HotDish dish) {
        if (id <= 0) {
            throw new IllegalArgumentException("Ідентифікатор запису має бути додатним.");
        }
        if (dish == null) {
            throw new IllegalArgumentException("Страва не може бути null.");
        }
        this.id = id;
        this.dish = dish;
    }

    public int getId() {
        return id;
    }

    public HotDish getDish() {
        return dish;
    }
}
