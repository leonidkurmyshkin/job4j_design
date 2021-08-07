package ru.job4j.io;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.charset.Charset;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class CSVReaderTest {
    @Test(expected = IllegalArgumentException.class)
    public void whenWrongNumberOfArguments() {
        var args = "-path=./data/file.csv -out=./data/out.csv -filter=name,age,children"
                .split(" ");
        var argsName = CSVReader.validateCommandLine(args);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenCSVFileIsAbsent() {
        var args = "-path=./data/none.csv -delimiter=; -out=./data/out.csv -filter=name,age,children"
                .split(" ");
        var argsName = CSVReader.validateCommandLine(args);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenFilterColumnNameIsAbsent() {
        var args = "-path=./data/file.csv -delimiter=; -out=./data/out.csv -filter=namm,age,children"
                .split(" ");
        var argsName = CSVReader.validateCommandLine(args);
        var reader = new CSVReader(argsName.get("path"), argsName.get("delimiter"),
                argsName.get("out"), argsName.get("filter"));
        reader.readCSV();
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenEmptyCSV() {
        var args = "-path=./data/empty.csv -delimiter=; -out=./data/out.csv -filter=name,age,children"
                .split(" ");
        var argsName = CSVReader.validateCommandLine(args);
        var reader = new CSVReader(argsName.get("path"), argsName.get("delimiter"),
                argsName.get("out"), argsName.get("filter"));
        reader.readCSV();
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenCSVIsNotTable() {
        var args = "-path=./data/notTable.csv -delimiter=; -out=./data/out.csv -filter=name,age,children"
                .split(" ");
        var argsName = CSVReader.validateCommandLine(args);
        var reader = new CSVReader(argsName.get("path"), argsName.get("delimiter"),
                argsName.get("out"), argsName.get("filter"));
        reader.readCSV();
    }

    @Test
    public void readNameAndAgeColumns() {
        var args = "-path=./data/file.csv -delimiter=; -out=./data/out.csv -filter=name,age"
                .split(" ");
        var argsName = CSVReader.validateCommandLine(args);
        var reader = new CSVReader(argsName.get("path"), argsName.get("delimiter"),
                argsName.get("out"), argsName.get("filter"));
        reader.writeCSV(reader.readCSV());
        try (var in = new BufferedReader(new FileReader(argsName.get("out"),
                Charset.forName("Windows-1251")))) {
            assertThat(in.readLine(), is("name;age"));
            assertThat(in.readLine(), is("Иванов Николай;28"));
            assertThat(in.readLine(), is("Петров Олег;42"));
            assertThat(in.readLine(), is("Смит Джон;33"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}