package ru.job4j.kiss;

import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

public class MaxMin {
    private <T> T extremum(List<T> values, Comparator<T> comparator, Predicate<Integer> predicate) {
        T rsl = values.isEmpty() ? null : values.get(0);
        for (var value : values) {
            if (predicate.test(comparator.compare(value, rsl))) {
                rsl = value;
            }
        }
        return rsl;
    }

    public <T> T max(List<T> values, Comparator<T> comparator) {
        return extremum(values, comparator, rsl -> rsl > 0);
    }

    public <T> T min(List<T> values, Comparator<T> comparator) {
        return extremum(values, comparator, rsl -> rsl < 0);
    }
}