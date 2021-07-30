package ru.job4j.io.duplicates;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DuplicatesVisitor extends SimpleFileVisitor<Path> {
    private final HashMap<FileProperty, List<Path>> filePaths = new HashMap<>();

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        var newKey = new FileProperty(file.toFile().length(),
                file.getFileName().toString());
        filePaths.putIfAbsent(newKey, new ArrayList<>());
        filePaths.get(newKey).add(file);
        return super.visitFile(file, attrs);
    }

    public HashMap<FileProperty, List<Path>> getFilePaths() {
        return filePaths;
    }
}
