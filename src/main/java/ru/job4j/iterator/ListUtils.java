package ru.job4j.iterator;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class ListUtils {
    private static <T> void add(List<T> list, int index, T value) {
        var listIt = list.listIterator();
        while (listIt.nextIndex() != index) {
            listIt.next();
        }
        listIt.add(value);
    }

    public static <T> void addBefore(List<T> list, int index, T value) {
        Objects.checkIndex(index, list.size());
        add(list, index, value);
    }

    public static <T> void addAfter(List<T> list, int index, T value) {
        Objects.checkIndex(index, list.size());
        add(list, index + 1, value);
    }

    private static <T> void doIf(List<T> list,
                                 Predicate<T> filter,
                                 Consumer<ListIterator<T>> action) {
        for (var listIt = list.listIterator();
             listIt.hasNext();) {
            if (filter.test(listIt.next())) {
                action.accept(listIt);
            }
        }
    }

    public static <T> void replaceIf(List<T> list, Predicate<T> filter, T value) {
        doIf(list, filter, it -> it.set(value));
    }

    public static <T> void removeIf(List<T> list, Predicate<T> filter) {
        doIf(list, filter, ListIterator::remove);
    }

    public static <T> void removeAll(List<T> list, List<T> elements) {
        for (var element : elements) {
            removeIf(list, e -> e.equals(element));
        }
    }
}