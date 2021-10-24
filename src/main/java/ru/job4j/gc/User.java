package ru.job4j.gc;

public class User {

    private String login;
    private int rating;

    public User(String login, int rating) {
        this.login = login;
        this.rating = rating;
        System.out.printf("User %s, %d создан%n", login, rating);
    }

    @Override
    protected void finalize() throws Throwable {
        System.out.printf("!User %s, %d удалён%n", login, rating);
    }

    public static void main(String[] args) throws InterruptedException {
        new User(new String("main_administrator"), 1);
        new User(new String("angry_bird"), 2);
        new User(new String("guest"), 3);
        Thread.sleep(5000);
    }
}