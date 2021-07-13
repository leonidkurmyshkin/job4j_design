package ru.job4j.collection.map;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

public class EqualsOverriding {
    public static void main(String[] args) {
        var user1 = new User("Ковалев Павел", 2,
                new GregorianCalendar(1960, Calendar.JANUARY, 1));
        var user2 = new User("Ковалев Павел", 2,
                new GregorianCalendar(1960, Calendar.JANUARY, 1));
        Map<User, Object> users = new HashMap<>();
        users.put(user1, new Object());
        users.put(user2, new Object());
        users.forEach((user, object) ->
                System.out.println("User: " + user + " Object " + object));
    }
}
