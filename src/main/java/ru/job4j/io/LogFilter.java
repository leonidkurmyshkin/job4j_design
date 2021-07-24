package ru.job4j.io;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;
import java.util.stream.Collectors;

public class LogFilter {
    public static List<String> filter(String file) {
        List<String> notFoundLines = List.of();
        try (var in = new BufferedReader(new FileReader(file))) {
            notFoundLines = in.lines()
                    .filter(line -> line.matches("^.+ 404 (\\d+|-)$"))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return notFoundLines;
    }

    public static void main(String[] args) {
        List<String> log = filter("log.txt");
        log.forEach(System.out::println);
    }
}