package ru.job4j.cache;

import java.util.Scanner;

public class ConsoleInput implements Input {
    private final Scanner scan = new Scanner(System.in);

    @Override
    public String askString(String question) {
        System.out.print(question);
        return scan.nextLine();
    }

    @Override
    public int askInt(String question) throws NumberFormatException {
        return Integer.parseInt(askString(question));
    }
}
