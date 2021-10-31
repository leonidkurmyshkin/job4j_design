package ru.job4j.cache;

import java.io.File;

public class AskDirectory implements Action {
    @Override
    public String takeName() {
        return "Указать кэшируемую директорию.";
    }

    @Override
    public boolean execute(Input in, Emulator emul) {
        printAllDirectories(emul);
        var dirName = in.askString("Введите имя директории:");
        var file = new File(dirName);
        if (file.exists() && file.isDirectory()) {
            emul.getDirToCache()
                    .putIfAbsent(dirName, new DirFileCache(dirName));
            emul.setDirName(dirName);
        } else {
            System.out.printf("Директория %s не существует.%n", dirName);
        }
        return true;
    }

    private void printAllDirectories(Emulator emul) {
        System.out.println("Кэшируемые директории:");
        emul.getDirToCache()
                .keySet().forEach(System.out::println);
    }
}
