package ru.naumen.collection.task3;

import java.nio.file.Path;
import java.util.*;

/**
 * <p>Написать консольное приложение, которое принимает на вход произвольный текстовый файл в формате txt.
 * Нужно собрать все встречающийся слова и посчитать для каждого из них количество раз, сколько слово встретилось.
 * Морфологию не учитываем.</p>
 * <p>Вывести на экран наиболее используемые (TOP) 10 слов и наименее используемые (LAST) 10 слов</p>
 * <p>Проверить работу на романе Льва Толстого “Война и мир”</p>
 *
 * @author vpyzhyanov
 * @since 19.10.2023
 */
public class WarAndPeace {

    private static final Path WAR_AND_PEACE_FILE_PATH = Path.of("src/main/resources",
            "Лев_Толстой_Война_и_мир_Том_1,_2,_3,_4_(UTF-8).txt");

    public static void main(String[] args) {
        // Выбрана т.к. итерация по элементам происходит быстрее, чем у HashMap,
        // а доступ к элементу осуществляется за O(1)
        Map<String, Integer> words = new LinkedHashMap<>();

        // Выбрана т.к. хранит элементы в отсортированном виде,
        // а доступ к приоритетному элементу осуществляется за O(log(n)), где n - кол-во элементов в очереди
        Queue<Map.Entry<String, Integer>> topWords = new PriorityQueue<>(
                11, Comparator.comparingInt(Map.Entry::getValue));
        Queue<Map.Entry<String, Integer>> lastWords = new PriorityQueue<>(
                11, Comparator.comparingInt(entry -> -entry.getValue()));

        new WordParser(WAR_AND_PEACE_FILE_PATH)
                .forEachWord(word -> {
                    words.put(word, words.getOrDefault(word, 0) + 1); // Прохождение по всем словам и добавление в Map - O(n)
                });

        for (Map.Entry<String, Integer> entry : words.entrySet()) { // Прохождение по всем Map.Entry - O(n)
            if (topWords.size() < 10 || entry.getValue() > topWords.peek().getValue()) { // O(1) + O(1)
                topWords.offer(entry); // O(log(n)), в этом случае O(log(10)), т.к. в очереди максимум 10 элементов
                if (topWords.size() > 10) { // O(1)
                    topWords.poll(); // O(log(n)), в этом случае O(log(11)), т.к. в очереди 11 элементов
                }
            }

            if (lastWords.size() < 10 || entry.getValue() < lastWords.peek().getValue()) { // O(1) + O(1)
                lastWords.offer(entry);
                if (lastWords.size() > 10) {
                    lastWords.poll();
                }
            } // Выполнение кода внутри условий двух if выполнится максимум n + 10 раз, а их сложность равна так,
              // что в расчёт возьмём данные из предыдущего блока if
        } // По итогу сложность данного цикла равна O(n) * O(1 + 1 + log(10) + 1 + log(11) + 1 + 1) = O(5n + nlog(10) + nlog(11))

        System.out.println("TOP 10 наиболее используемых слов:");
        for (int i = 0; i < 10; i++) {
            // Сложность O(log(n)), при первом проходе O(log(10))
            // при втором проходе O(log(9)) и т.д.
            Map.Entry<String, Integer> entry = topWords.poll();
            System.out.println(entry.getKey() + " - " + entry.getValue() + " раз(а)");
        } // По итогу сложность данного цикла равна O(log(10) + log(9) + ... + log(1)) ~ O(1)
        System.out.println("\nLAST 10 наименее используемых слов:");
        for (int i = 0; i < 10; i++) {
            Map.Entry<String, Integer> entry = lastWords.poll();
            System.out.println(entry.getKey() + " - " + entry.getValue() + " раз(а)");
        } // По итогу сложность данного цикла равна O(log(10) + log(9) + ... + log(1)) ~ O(1)
    }
    // Общая сложность O(n) + O(5n + nlog(10) + nlog(11)) + O(1) + O(1) ~ O(6n + nlog(10) + nlog(11))
}
