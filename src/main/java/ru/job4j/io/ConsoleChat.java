package ru.job4j.io;

import java.io.*;
import java.nio.charset.Charset;
import java.util.*;

public class ConsoleChat {
    private final String path;
    private final String botAnswers;
    private static final String OUT = "закончить";
    private static final String STOP = "стоп";
    private static final String CONTINUE = "продолжить";
    private String botState = CONTINUE;
    private List<String> answers = null;

    public ConsoleChat(String path, String botAnswers) {
        this.path = path;
        this.botAnswers = botAnswers;
    }

    public void run() {
        var rand = new Random();
        var in = new Scanner(System.in);
        var dialog = new StringBuilder();
        while (!botState.equals(OUT)) {
            System.out.print("Ваша фраза: ");
            var question = in.nextLine();
            dialog.append(String.format("Пользователь: %s%n", question));
            changeBotState(question);
            if (botState.equals(CONTINUE)) {
                var answer = getBotAnswer(rand);
                System.out.printf("Бот: %s%n", answer);
                dialog.append(String.format("Бот: %s%n", answer));
            }
        }
        writeToLog(dialog.toString());
    }

    private void changeBotState(String question) {
        switch (question) {
            case OUT:
                botState = OUT;
                break;
            case STOP:
                botState = STOP;
                break;
            case CONTINUE:
                botState = CONTINUE;
                break;
            default:
        }
    }

    private void writeToLog(String dialog) {
        try (var out = new PrintWriter(
                new FileWriter(path, Charset.forName("WINDOWS-1251")))) {
            out.append(dialog);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getBotAnswer(Random rand) {
        if (answers == null) {
            try (var in = new BufferedReader(
                    new FileReader(botAnswers, Charset.forName("WINDOWS-1251")))) {
                answers = new ArrayList<>();
                in.lines().forEach(answers::add);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return answers.get(rand.nextInt(answers.size()));
    }

    public static void main(String[] args) {
        ConsoleChat cc = new ConsoleChat("./data/dialog.txt", "./data/bot_answers.txt");
        cc.run();
    }
}