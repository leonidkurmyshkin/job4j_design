package ru.job4j.io.find;

import org.junit.*;

import static org.junit.Assert.*;
import org.junit.rules.TemporaryFolder;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;

public class FilesFinderTest {
    static File file1, file2, file3, file4;
    ArgsName argsName;

    @ClassRule
    public static TemporaryFolder temp = new TemporaryFolder();

    @BeforeClass
    public static void createFiles() throws IOException {
        file1 = temp.newFile("f1.txt");
        file2 = temp.newFile("f2.txt");
        file3 = temp.newFile("f3.log");
        file4 = temp.newFile("f.csv");
    }

    @After
    public void deleteLog() {
        if (argsName != null) {
            var log = new File(argsName.get("o"));
            if (log.exists()) {
                log.delete();
            }
        }
    }

    @Test
    public void whenFindTypeIsMask() throws IOException {
        String[] args = "-d=%s -n=*.txt -t=mask -o=%s/log.txt"
                .formatted(temp.getRoot().getAbsolutePath(), temp.getRoot().getAbsolutePath())
                .split(" ");
        argsName = FilesFinder.validateCommandLine(args);
        var finder = new FilesFinder(argsName.get("d"), argsName.get("n"),
                argsName.get("t"), argsName.get("o"));
        List<Path> found = finder.search();
        Collections.sort(found);
        finder.writeLog(found);
        try (var in = new BufferedReader(new FileReader(argsName.get("o"),
                Charset.forName("Windows-1251")))) {
            assertEquals(file1.getAbsolutePath(), in.readLine());
            assertEquals(file2.getAbsolutePath(), in.readLine());
            assertNull(in.readLine());
        }
    }

    @Test
    public void whenFindTypeIsName() throws IOException {
        String[] args = "-d=%s -n=f.csv -t=name -o=%s/log.txt"
                .formatted(temp.getRoot().getAbsolutePath(), temp.getRoot().getAbsolutePath())
                .split(" ");
        argsName = FilesFinder.validateCommandLine(args);
        var finder = new FilesFinder(argsName.get("d"), argsName.get("n"),
                argsName.get("t"), argsName.get("o"));
        List<Path> found = finder.search();
        Collections.sort(found);
        finder.writeLog(found);
        try (var in = new BufferedReader(new FileReader(argsName.get("o"),
                Charset.forName("Windows-1251")))) {
            assertEquals(file4.getAbsolutePath(), in.readLine());
            assertNull(in.readLine());
        }
    }

    @Test
    public void whenFindTypeIsRegex() throws IOException {
        String[] args = "-d=%s -n=.*\\.txt|.*\\.log -t=regex -o=%s/log.txt"
                .formatted(temp.getRoot().getAbsolutePath(), temp.getRoot().getAbsolutePath())
                .split(" ");
        argsName = FilesFinder.validateCommandLine(args);
        var finder = new FilesFinder(argsName.get("d"), argsName.get("n"),
                argsName.get("t"), argsName.get("o"));
        List<Path> found = finder.search();
        Collections.sort(found);
        finder.writeLog(found);
        try (var in = new BufferedReader(new FileReader(argsName.get("o"),
                Charset.forName("Windows-1251")))) {
            assertEquals(file1.getAbsolutePath(), in.readLine());
            assertEquals(file2.getAbsolutePath(), in.readLine());
            assertEquals(file3.getAbsolutePath(), in.readLine());
            assertNull(in.readLine());
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenWrongNumberOfArguments() {
        var args = "-d=./data -n=.*\\.txt|.*\\.csv -o=./data/log.txt"
                .split(" ");
        FilesFinder.validateCommandLine(args);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenFindFolderIsNotSet() {
        var args = "-d= -n=.*\\.txt|.*\\.csv -t=regex -o=./data/log.txt"
                .split(" ");
        FilesFinder.validateCommandLine(args);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenFindFolderNotExist() {
        var args = "-d=./da -n=.*\\.txt|.*\\.csv -t=regex -o=./data/log.txt"
                .split(" ");
        FilesFinder.validateCommandLine(args);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenFindFolderIsNotFolder() {
        var args = "-d=./data/car.sql -n=.*\\.txt|.*\\.csv -t=regex -o=./data/log.txt"
                .split(" ");
        FilesFinder.validateCommandLine(args);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenFindTypeIsNotSet() {
        var args = "-d=./data -n=.*\\.txt|.*\\.csv -t= -o=./data/log.txt"
                .split(" ");
        FilesFinder.validateCommandLine(args);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenFindTypeIsInvalid() {
        var args = "-d=./data -n=.*\\.txt|.*\\.csv -t=yui7 -o=./data/log.txt"
                .split(" ");
        FilesFinder.validateCommandLine(args);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenFindNameIsNotSet() {
        var args = "-d=./data -n= -t=regex -o=./data/log.txt"
                .split(" ");
        FilesFinder.validateCommandLine(args);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenFindNameIsInvalid() {
        var args = "-d=./data -n=.** -t=regex -o=./data/log.txt"
                .split(" ");
        FilesFinder.validateCommandLine(args);
    }
}