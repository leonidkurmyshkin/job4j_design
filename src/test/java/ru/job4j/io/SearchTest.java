package ru.job4j.io;

import org.junit.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.hamcrest.Matchers.hasItems;
import static org.junit.Assert.*;

public class SearchTest {
    @Test
    public void whenLogsFound() throws IOException {
        Path start = Paths.get(".");
        var path4 = Paths.get("./data/whenEndingIsUnknown.log");
        var path2 = Paths.get("./data/whenOneRangeUnavailable.log");
        var path3 = Paths.get("./data/whenTwoRangeUnavailable.log");
        var path1 = Paths.get("./data/whenPermanentAvailable.log");
        var pathsRsl = Search.search(start,
                p -> p.toString().endsWith(".log"));
        assertEquals(pathsRsl.size(), 4);
        assertThat(pathsRsl, hasItems(path1, path2, path3, path4));
    }
}
