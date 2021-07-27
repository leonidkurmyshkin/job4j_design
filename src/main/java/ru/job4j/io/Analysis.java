package ru.job4j.io;

import java.io.*;

public class Analysis {
    final static int PROBLEM_BOUND = 400;

    public static void unavailable(String source, String target) {
        try (
                var in = new BufferedReader(new FileReader(source));
                var out = new PrintWriter(
                    new BufferedOutputStream(
                        new FileOutputStream(target)))) {
            String line;
            var startIsFound = false;
            while ((line = in.readLine()) != null) {
                String[] words = line.split(" ");
                if (!startIsFound) {
                    if (Integer.parseInt(words[0]) >= PROBLEM_BOUND) {
                        out.printf("%s;", words[1]);
                        startIsFound = true;
                    }
                } else  if (Integer.parseInt(words[0]) < PROBLEM_BOUND) {
                    out.printf("%s;%n", words[1]);
                    startIsFound = false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}