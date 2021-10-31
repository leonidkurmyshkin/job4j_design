package ru.job4j.cache;

public class LoadFile implements Action {
    @Override
    public String takeName() {
        return "Загрузить содержимое файла в кэш.";
    }

    @Override
    public boolean execute(Input in, Emulator emul) {
        if (!emul.dirNameIsNull()) {
            DirFileCache dir = emul.getDirToCache().get(emul.getDirName());
            System.out.printf("Кэшируемая директория: %s%n", dir.getCachingDir());
            var fileName = in.askString("Введите имя файла:");
            var file = dir.takeFullName(fileName);
            if (file.exists() && file.isFile()) {
                dir.put(fileName, dir.load(fileName));
                if (dir.cache.containsKey(fileName)) {
                    System.out.println("Содержимое файла загружено.");
                }
            } else {
                System.out.printf("Файл %s не существует.%n", fileName);
            }
        }
        return true;
    }
}
