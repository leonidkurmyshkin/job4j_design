package ru.job4j.kiss;

import java.util.Comparator;
import java.util.List;

public class MaxMin {
    public <T> T max(List<T> values, Comparator<T> comparator) {
        T max = null;
        for (var value : values) {
            if (max == null || comparator.compare(value, max) > 0) {
                max = value;
            }
        }
        return max;
    }

    public <T> T min(List<T> values, Comparator<T> comparator) {
        return max(values, comparator.reversed());
    }
}