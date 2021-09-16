package ru.job4j.serialization.json;

import org.json.JSONArray;
import org.json.JSONObject;

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

    public String getName() {
        return name;
    }

    public boolean isSex() {
        return sex;
    }

    public int getAge() {
        return age;
    }

    public Contact getContact() {
        return contact;
    }

    public String[] getStatuses() {
        return statuses;
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
        JSONArray jsonStatuses = new JSONArray(new String[]
                {"Worker", "Married"});
        JSONObject jsonContact = new JSONObject()
                .put("zipCode", 123456)
                .put("phone", "+7 (111) 111-11-99");
        JSONObject jsonPerson = new JSONObject()
                .put("name", "Ivanov")
                .put("sex", false)
                .put("age", 30)
                .put("contact", jsonContact)
                .put("statuses", jsonStatuses);
        System.out.println(jsonPerson.toString());
    }
}