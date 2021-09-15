package ru.job4j.serialization.xml;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.*;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Arrays;

@XmlRootElement(name = "person")
@XmlAccessorType(XmlAccessType.FIELD)
public class Person {
    @XmlAttribute
    private String name;
    @XmlAttribute
    private boolean sex;
    @XmlAttribute
    private int age;
    @XmlElement
    private Contact contact;
    @XmlElementWrapper
    @XmlElement(name = "status")
    private String[] statuses;

    public Person() { }

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

    public static void main(String[] args) throws JAXBException, IOException {
        var context = JAXBContext.newInstance(Person.class);
        var unMarshaller = context.createUnmarshaller();
        final var person = (Person) unMarshaller.unmarshal(new File("./data/person.xml"));
        System.out.println(person);
        var marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        try (var writer = new StringWriter()) {
            marshaller.marshal(person, writer);
            String result = writer.toString();
            System.out.println(result);
        }
    }
}