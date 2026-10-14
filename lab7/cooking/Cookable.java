package lab7.cooking;

/**
 * Контракт для всіх страв, які можна приготувати.
 *
 * <p>Методи інтерфейсу не містять конкретної реалізації —
 * її повинні надати класи, що реалізують цей інтерфейс.</p>
 */
public interface Cookable {
    void cook();

    String getCookingInstructions();
}
