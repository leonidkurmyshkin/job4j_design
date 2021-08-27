package ru.job4j.serialization.java;

import java.io.*;
import java.nio.file.Files;

public class Contact implements Serializable {
    private final int zipCode;
    private final String phone;

    public Contact(int zipCode, String phone) {
        this.zipCode = zipCode;
        this.phone = phone;
    }

    public int getZipCode() {
        return zipCode;
    }

    public String getPhone() {
        return phone;
    }

    @Override
    public String toString() {
        return "Contact{"
                + "zipCode=" + zipCode
                + ", phone='" + phone + '\''
                + '}';
    }

    public File serialize() throws IOException {
        File tempFile = Files.createTempFile(null, null).toFile();
        try (var oos = new ObjectOutputStream(new FileOutputStream(tempFile))) {
            oos.writeObject(this);
        }
        return tempFile;
    }

    public Contact deserialize(File file) throws IOException, ClassNotFoundException {
        try (var ois = new ObjectInputStream(new FileInputStream(file))) {
            return (Contact) ois.readObject();
        }
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        final Contact contact = new Contact(123456, "+7 (111) 111-11-99");
        System.out.println("Contact до сериализации: " + contact);
        File file = contact.serialize();
        System.out.println("Contact после десериализации: " + contact.deserialize(file));
    }
}