package ru.job4j.cache;

public class TakeValue implements Action {
    @Override
    public String takeName() {
        return "Получить содержимое файла из кэша.";
    }

    @Override
    public boolean execute(Input in, Emulator emul) {
        if (!emul.dirNameIsNull()) {
            var dirName = emul.getDirName();
            System.out.printf("Кешируемая директория: %s%n", dirName);
            printAllFileNames(emul);
            var fileName = in.askString("Введите имя файла:");
            var value = emul.getDirToCache().get(dirName)
                    .get(fileName);
            System.out.println(value != null
                    ? value
                    : "Такой файл ещё не загружался в кэш.");
        }
        return true;
    }

    private void printAllFileNames(Emulator emul) {
        System.out.println("Загруженные файлы:");
        emul.getDirToCache().get(emul.getDirName())
                .cache.keySet()
                .forEach(System.out::println);
    }
}
