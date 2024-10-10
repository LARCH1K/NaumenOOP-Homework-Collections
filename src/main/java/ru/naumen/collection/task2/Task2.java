package ru.naumen.collection.task2;

import java.util.*;

/**
 * Дано:
 * <pre>
 * public class User {
 *     private String username;
 *     private String email;
 *     private byte[] passwordHash;
 *     …
 * }
 * </pre>
 * Нужно реализовать метод
 * <pre>
 * public static List<User> findDuplicates(Collection<User> collA, Collection<User> collB);
 * </pre>
 * <p>который возвращает дубликаты пользователей, которые есть в обеих коллекциях.</p>
 * <p>Одинаковыми считаем пользователей, у которых совпадают все 3 поля: username,
 * email, passwordHash. Дубликаты внутри коллекций collA, collB можно не учитывать.</p>
 * <p>Метод должен быть оптимален по производительности.</p>
 * <p>Пользоваться можно только стандартными классами Java SE.
 * Коллекции collA, collB изменять запрещено.</p>
 *
 * См. {@link User}
 *
 * @author vpyzhyanov
 * @since 19.10.2023
 */
public class Task2
{

    /**
     * Возвращает дубликаты пользователей, которые есть в обеих коллекциях
     */
    public static List<User> findDuplicates(Collection<User> collA, Collection<User> collB) {
        //Сложность операции сначала O(n), где n - кол-во элементов в collA, т.к. пришлось перекопировать их в HashSet
        //Далее идем по collB, получаем O(n) + O(m) * O(1), т.к. метод contains() - O(1)
        //Периодически массив users расширяется, O(n) + O(m) * O(1) * O(1) = O(n) + O(m), т.к. метод add() - O(1)
        //Итоговая сложность O(n) + O(m) = O(n + m) ~ O(n)
        Set<User> set = new HashSet<>(collA);
        List<User> users = new ArrayList<>();

        for(User user : collB) {
            if(set.contains(user)){
                users.add(user);
            }
        }

        return users;
    }
}
