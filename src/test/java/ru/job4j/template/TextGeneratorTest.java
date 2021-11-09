package ru.job4j.template;

import org.junit.Ignore;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.*;

public class TextGeneratorTest {
    @Ignore
    @Test
    public void whenStringGenerationWasSuccessful() {
        var generator = new TextGenerator();
        var template = "I am ${name}. Who are ${subject}? ";
        Map<String, String> replacements = Map.of(
                "name", "Ivan Sokoloff",
                "subject", "you"
        );
        var rsl = "I am Ivan Sokoloff. Who are you? ";
        assertEquals(rsl, generator.produce(template, replacements));
    }

    @Ignore
    @Test(expected = IllegalArgumentException.class)
    public void whenTemplateKeyIsMissingFromMap() {
        var generator = new TextGenerator();
        var template = "I am ${firstname}. Who are ${subject}? ";
        Map<String, String> replacements = Map.of(
                "subject", "you"
        );
        generator.produce(template, replacements);
    }

    @Ignore
    @Test(expected = IllegalArgumentException.class)
    public void whenMapContainsWasteKey() {
        var generator = new TextGenerator();
        var template = "I am John. Who are ${subject}? ";
        Map<String, String> replacements = Map.of(
                "name", "Ivan Sokoloff",
                "subject", "you"
        );
        generator.produce(template, replacements);
    }
}