package ru.job4j.io;

import java.io.FileInputStream;

public class EvenNumberFile {
    public static void main(String[] args) {
        try (var in = new FileInputStream("even.txt")) {
            var textNumbers = new StringBuilder();
            int readByte;
            while ((readByte = in.read()) != -1) {
                textNumbers.append((char) readByte);
            }
            for (var textNumber : textNumbers.toString()
                    .split(System.lineSeparator())) {
                System.out.print(textNumber);
                System.out.println(Integer.parseInt(textNumber) % 2 == 0
                            ? " - чётное число."
                            : " - нечётное число.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}