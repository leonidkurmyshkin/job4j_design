package ru.job4j.cache;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;

public class DirFileCache extends AbstractCache<String, String> {
    private final String cachingDir;

    public DirFileCache(String cachingDir) {
        this.cachingDir = cachingDir;
    }

    @Override
    protected String load(String key) {
        String text = null;
        try {
            text = Files.readString(takeFullName(key).toPath(), Charset.forName("Windows-1251"));
        } catch (IOException e) {
            System.out.printf("Не удалось прочитать файл %s%n", key);
        }
        return text;
    }

    public File takeFullName(String key) {
        return new File(cachingDir, key);
    }

    public String getCachingDir() {
        return cachingDir;
    }
}