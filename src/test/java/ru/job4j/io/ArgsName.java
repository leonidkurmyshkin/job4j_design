package ru.job4j.io;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ArgsName {
    private final Map<String, String> values = new HashMap<>();
    private final static Pattern CORRECT_KEY = Pattern.compile("^-([A-Za-z]+?)=(.+)$");

    public String get(String key) {
        if (!values.containsKey(key)) {
            throw new IllegalArgumentException("Неверный ключ, или значение.");
        }
        return values.get(key);
    }

    private void parse(String[] args) {
        Arrays.stream(args)
                .map(CORRECT_KEY::matcher)
                .filter(Matcher::matches)
                .forEach(matcher -> values.put(matcher.group(1), matcher.group(2)));
    }

    public static ArgsName of(String... args) {
        ArgsName names = new ArgsName();
        names.parse(args);
        return names;
    }
}