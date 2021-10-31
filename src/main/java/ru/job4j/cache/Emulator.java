package ru.job4j.cache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Emulator {
    private final Map<String, DirFileCache> dirToCache = new HashMap<>();
    private String dirName;

    public static void start(Input in, Emulator emul, List<Action> actions) {
        var isRunning = true;
        do {
            printMenu(actions);
            try {
                var select = in.askInt("Введите номер пункта меню: ");
                if (select < 0 || select >= actions.size()) {
                    System.out.printf("Можно вводить только номера с 0 до %s%n", actions.size());
                    continue;
                }
                isRunning = actions.get(select).execute(in, emul);
            } catch (NumberFormatException e) {
                System.out.printf("Можно вводить только номера с 0 до %s%n", actions.size());
            }
        } while (isRunning);
    }

    public static void printMenu(List<Action> actions) {
        System.out.println("Меню.");
        for (var i = 0; i < actions.size(); i++) {
            System.out.printf("%s. %s%n", i, actions.get(i).takeName());
        }
    }

    boolean dirNameIsNull() {
        boolean rsl = dirName == null;
        if (rsl) {
            System.out.println("Кэшируемая директория ещё не указана.");
        }
        return rsl;
    }

    public Map<String, DirFileCache> getDirToCache() {
        return dirToCache;
    }

    public void setDirName(String dirName) {
        this.dirName = dirName;
    }

    public String getDirName() {
        return dirName;
    }

    public static void main(String[] args) {
        start(new ConsoleInput(),
                new Emulator(),
                List.of(
                        new AskDirectory(),
                        new LoadFile(),
                        new TakeValue(),
                        new Exit()
                ));
    }
}
