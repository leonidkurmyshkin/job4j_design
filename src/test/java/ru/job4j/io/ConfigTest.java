package ru.job4j.io;

import org.junit.Test;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class ConfigTest {

    @Test
    public void whenPairWithoutComment() {
        String path = "./data/pair_without_comment.properties";
        Config config = new Config(path);
        config.load();
        assertThat(config.value("name"), is("Petr Arsentev"));
    }

    @Test
    public void whenPairsWithCommentsAndEmptyLines() {
        String path = "./data/whenPairsWithCommentsAndEmptyLines.properties";
        Config config = new Config(path);
        config.load();
        assertThat(config.value("name"), is("Ivan Petrov"));
        assertThat(config.value("team.currentPosition"), is("junior developer"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenWrongKey() {
        String path = "./data/whenWrongKey.properties";
        Config config = new Config(path);
        config.load();
        config.value("7name");
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenEmptyValue() {
        String path = "./data/whenEmptyValue.properties";
        Config config = new Config(path);
        config.load();
        config.value("name");
    }
}