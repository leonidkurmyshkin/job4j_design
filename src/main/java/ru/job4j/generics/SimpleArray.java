package ru.job4j.generics;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

public class SimpleArray<T> implements Iterable<T> {
    private final T[] simpleArray;
    public final int length;
    private int size;

    public SimpleArray(int length) {
        simpleArray = (T[]) new Object[length];
        this.length = length;
        size = 0;
    }

    public void add(T model) {
        simpleArray[size++] = model;
    }

    public void set(int index, T model) {
        Objects.checkIndex(index, size);
        simpleArray[index] = model;
    }

    public void remove(int index) {
        Objects.checkIndex(index, size);
        if (index != size - 1) {
            System.arraycopy(simpleArray, index + 1,
                    simpleArray, index,
                    size - index - 1);
        }
        simpleArray[--size] = null;
    }

    public T get(int index) {
        Objects.checkIndex(index, size);
        return simpleArray[index];
    }

    public int getSize() {
        return size;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<>() {
            private int pointer = 0;

            @Override
            public boolean hasNext() {
                return pointer < size;
            }

            @Override
            public T next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return simpleArray[pointer++];
            }
        };
    }
}
