package ru.job4j.io;

import java.io.FileOutputStream;

public class MultiplicationTable {
    public static void tableOutToFile(int n) {
        try (var out = new FileOutputStream("table.txt")) {
            for (var i = 1; i <= n; i++) {
                for (var j = 1; j <= n; j++) {
                    out.write(String.valueOf(i * j).getBytes());
                    if (j < n) {
                        out.write("\t".getBytes());
                    }
                }
                out.write("\n".getBytes());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        tableOutToFile(5);
    }
}
