package ru.job4j.io;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.function.Predicate;

public class Search {
    public static List<Path> search(Path root, Predicate<Path> condition)
            throws IOException {
        SearchFiles searcher = new SearchFiles(condition);
        Files.walkFileTree(root, searcher);
        return searcher.getPaths();
    }

    public static void main(String[] args) throws IOException {
        if (args.length < 2) {
            throw  new IllegalArgumentException(String.format(
                    "Параметры запуска указаны не полностью. Запускайте так:%n%s",
                    "java -jar search.jar начальный_каталог расширение_файла"));
        }
        final var startFolder = Paths.get(args[0]);
        final var fileExtension = args[1];
        if (!startFolder.toFile().exists()) {
            throw new IllegalArgumentException(String.format(
                    "Каталог %s не существует", startFolder));
        }
        if (!startFolder.toFile().isDirectory()) {
            throw new IllegalArgumentException(String.format(
                    "%s - не каталог", startFolder));
        }
        search(startFolder, path -> path.toString().endsWith(fileExtension))
                .forEach(System.out::println);
    }
}