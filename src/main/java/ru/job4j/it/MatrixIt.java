package ru.job4j.it;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class MatrixIt implements Iterator<Integer> {
    private final int[][] data;
    private int row = 0;
    private int column = 0;

    public MatrixIt(int[][] data) {
        this.data = data;
    }

    @Override
    public boolean hasNext() {
        boolean rsl = false;
        while (row < data.length) {
            if (data[row].length != 0) {
                rsl = true;
                break;
            }
            row++;
        }
        return rsl;
    }

    @Override
    public Integer next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        int rsl = data[row][column];
        if (column != data[row].length - 1) {
            column++;
        } else {
            column = 0;
            row++;
        }
        return rsl;
    }
}