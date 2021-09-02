package ru.job4j.serialization.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.job4j.serialization.java.Contact;

import java.util.Arrays;

public class Person {
    private final String name;
    private final boolean sex;
    private final int age;
    private final Contact contact;
    private final String[] statuses;

    public Person(String name, boolean sex, int age, Contact contact, String... statuses) {
        this.name = name;
        this.sex = sex;
        this.age = age;
        this.contact = contact;
        this.statuses = statuses;
    }

    @Override
    public String toString() {
        return "Person{"
                + "name=" + name
                + ", sex=" + sex
                + ", age=" + age
                + ", contact=" + contact
                + ", statuses=" + Arrays.toString(statuses)
                + '}';
    }

    public static void main(String[] args) {
        var person = new Person("Ivanov", false, 30,
                new Contact(123456, "+7 (111) 111-11-99"), "Worker", "Married");
        System.out.println(person);
        final Gson gson = new GsonBuilder().create();
        String savedPerson = gson.toJson(person);
        System.out.println(savedPerson);
        person = null;
        person = gson.fromJson(savedPerson, Person.class);
        System.out.println(person);
    }
}