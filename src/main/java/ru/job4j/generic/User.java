package ru.job4j.generic;

public class User extends Base {
    private final String name;

    public User(String id, String name) {
        super(id);
        this.name = name;
    }
}
