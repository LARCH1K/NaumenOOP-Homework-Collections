package ru.naumen.collection.task4;

import java.util.concurrent.*;
import java.util.function.Supplier;

/**
 * Класс управления расчётами
 */
public class ConcurrentCalculationManager<T> {

    // Выбрана BlockingQueue, т.к. она поддерживает ожидание при извлечении элементов из очереди
    private final BlockingQueue<CompletableFuture<T>> tasks = new LinkedBlockingQueue<>();

    /**
     * Добавить задачу на параллельное вычисление
     */
    public void addTask(Supplier<T> task) {
        tasks.add(CompletableFuture.supplyAsync(task)); // добавляем задачу в конец очереди - O(1)
    }

    /**
     * Получить результат вычисления.
     * Возвращает результаты в том порядке, в котором добавлялись задачи.
     */
    public T getResult() {
        try {
            return tasks.take().get(); // извлекаем задачу из начала очереди - O(1)
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
} // Итоговая сложность O(1) + O(1) ~ O(1)