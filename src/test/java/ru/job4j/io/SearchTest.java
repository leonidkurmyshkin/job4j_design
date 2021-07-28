package ru.job4j.io;

import org.junit.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.Assert.*;

public class SearchTest {
    @Test
    public void whenLogsFound() throws IOException {
        Path start = Paths.get(".");
        List<Path> expected = List.of(
                Paths.get("./data/whenEndingIsUnknown.log"),
                Paths.get("./data/whenOneRangeUnavailable.log"),
                Paths.get("./data/whenPermanentAvailable.log"),
                Paths.get("./data/whenTwoRangeUnavailable.log")
        );
        var pathsRsl = Search.search(start,
                p -> p.toString().endsWith(".log"));
        for (var path : expected) {
            assertTrue(pathsRsl.contains(path));
        }
    }
}