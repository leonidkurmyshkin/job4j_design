package ru.job4j.question;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class Analyze {
    public static Info diff(Set<User> previous, Set<User> current) {
        int changed = 0, deleted = 0;
        Map<User, String> currentMap = new HashMap<>();
        current.forEach(user -> currentMap.put(user, user.getName()));
        for (var user : previous) {
            if (!currentMap.containsKey(user)) {
               deleted++;
           } else if (Objects.hashCode(currentMap.get(user))
                    != Objects.hashCode(user.getName())) {
               changed++;
           }
        }
        return new Info(
                current.size() + deleted - previous.size(),
                changed,
                deleted);
    }

}