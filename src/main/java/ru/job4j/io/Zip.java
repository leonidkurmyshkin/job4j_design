package ru.job4j.io;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Zip {
    public static void packFiles(List<Path> sources, Path target) {
        try (var zip = new ZipOutputStream(
                new BufferedOutputStream(
                        new FileOutputStream(target.toFile())))) {
            for (var path : sources) {
                zip.putNextEntry(new ZipEntry(path.toString()));
                try (var in = new BufferedInputStream(
                        new FileInputStream(path.toFile()))) {
                    zip.write(in.readAllBytes());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void packSingleFile(File source, File target) {
        packFiles(List.of(source.toPath()), target.toPath());
    }

    private static ArgsName validateCommandLine(String[] args) {
        if (args.length != 3) {
            throw  new IllegalArgumentException(String.format("%s%n %s%n %s%n %s%n %s%n",
                    "Параметры запуска указаны не полностью. Запускайте так:",
                    "java -jar pack.jar -d=directory -e=exclude -o=output",
                    "где: directory - каталог, который надо архивировать,",
                    "exclude - расширение файлов, которые не попадут в архив",
                    "output - имя архива с расширением zip"));
        }
        var argsName = ArgsName.of(args);
        var startFolder = Path.of(argsName.get("d"));
        if (Files.notExists(startFolder)) {
            throw new IllegalArgumentException(String.format(
                    "Каталог %s не существует", startFolder));
        }
        if (!Files.isDirectory(startFolder)) {
            throw new IllegalArgumentException(String.format(
                    "%s - не каталог", startFolder));
        }
        var archiveName = Path.of(argsName.get("o"));
        if (!archiveName.toString().endsWith(".zip")) {
            throw new IllegalArgumentException(String.format(
                    "%s - неправильное имя архива", archiveName));
        }
        return argsName;
    }

    public static void main(String[] args) throws IOException {
        var argsName = validateCommandLine(args);
        packFiles(Search.search(
                Path.of(argsName.get("d")), path -> !path.toString().endsWith(argsName.get("e"))),
                Path.of(argsName.get("o")));
    }
}