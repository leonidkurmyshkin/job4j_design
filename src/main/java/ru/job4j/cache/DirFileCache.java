package ru.job4j.cache;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;

public class DirFileCache extends AbstractCache<String, String> {
    private final String cachingDir;

    public DirFileCache(String cachingDir) {
        this.cachingDir = cachingDir;
    }

    @Override
    protected String load(String key) {
        String text = get(key);
        if (text == null) {
            try (var in = new BufferedReader(
                    new FileReader(takeFullName(key), Charset.forName("Windows-1251")))) {
                var tempText = new StringBuilder();
                in.lines().forEach(row -> tempText.append(row).append(System.lineSeparator()));
                text = tempText.toString();
            } catch (IOException e) {
                System.out.printf("Не удалось прочитать файл %s%n", key);
            }
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