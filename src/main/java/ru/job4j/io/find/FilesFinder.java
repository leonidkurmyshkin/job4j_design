package ru.job4j.io.find;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class FilesFinder {
    private final Path startFolder;
    private final Pattern fileName;
    private final Path rslFile;

    public FilesFinder(String startFolder, String fileName, String findType, String rslFile) {
        this.startFolder = Path.of(startFolder);
        this.rslFile = Path.of(rslFile);
        if ("mask".equals(findType)) {
            fileName = convertMaskToRegex(fileName);
        }
        this.fileName = Pattern.compile(fileName);
    }

    private static String convertMaskToRegex(String mask) {
        return mask.replace("*", ".*")
                .replace("?", ".");
    }

    public List<Path> search() throws IOException {
        return Search.search(startFolder,
                path -> fileName.matcher(path.getFileName().toString()).matches());
    }

    public void writeLog(List<Path> files) throws IOException {
        try (var out = new PrintStream(new BufferedOutputStream(new FileOutputStream(rslFile.toFile())),
                false, Charset.forName("Windows-1251"))) {
            files.forEach(path -> out.println(path.toString()));
        }
    }

    public static ArgsName validateCommandLine(String[] args) {
        if (args.length != 4) {
            throw  new IllegalArgumentException("""
                Параметры запуска указаны не полностью. Запускайте так:
                java -jar find.jar -d=c:/ -n=*.txt -t=mask -o=log.txt
                где: -d - каталог, в котором начинать поиск,
                -n - имя файла, маска, либо регулярное выражение,
                -t - тип поиска: mask - искать по маске,
                \tname - по полному совпадению имени, regex - по регулярному выражению,
                -o - результат записать в файл.
                """);
        }
        var argsName = ArgsName.of(args);
        validateDirectory(argsName.get("d", "Каталог поиска не задан"));
        validateFindType(argsName.get("t", "Тип поиска не указан"));
        validateName(argsName.get("n", "Имя файла, маска, либо регулярное выражение не задано(а)"),
                argsName.get("t"));
        argsName.get("o", "Имя файла для записи результата не указано");
        return argsName;
    }

    private static void validateDirectory(String folder) {
        var startFolder = Path.of(folder);
        if (Files.notExists(startFolder)) {
            throw new IllegalArgumentException("Каталог %s не существует"
                    .formatted(startFolder));
        }
        if (!Files.isDirectory(startFolder)) {
            throw new IllegalArgumentException("%s - не каталог"
                    .formatted(startFolder));
        }
    }

    private static void validateFindType(String type) {
        switch (type) {
            case "mask", "name", "regex" -> { }
            default ->
                throw new IllegalArgumentException("Тип поиска может быть только mask, name, или regex");
        }
    }

    private static void validateName(String name, String type) {
        if ("mask".equals(type)) {
            name = FilesFinder.convertMaskToRegex(name);
        }
        try {
            Pattern.compile(name);
        } catch (PatternSyntaxException e) {
            throw new IllegalArgumentException("Неправильное(ая) имя файла, маска, или регулярное выражение");
        }
    }

    public static void main(String[] args) throws IOException {
        var argsName = validateCommandLine(args);
        var finder = new FilesFinder(argsName.get("d"), argsName.get("n"),
                argsName.get("t"), argsName.get("o"));
        finder.writeLog(finder.search());
    }
}
