package ru.job4j.io;

import java.io.*;
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

    public static void save(List<String> log, String file) {
        try (var out = new PrintWriter(
                new BufferedOutputStream(
                        new FileOutputStream(file)))) {
            log.forEach(line -> out.printf("%s%n", line));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        List<String> log = filter("log.txt");
        save(log, "404.txt");
    }
}