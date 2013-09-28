/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mycollections;

import java.lang.reflect.Array;

/**
 *
 * @author Pete
 */
public class MyArrayList<E> {

    private int size;           //number of elements in the list
    private Object[] array;
    private int inflateMultiplier;

    public MyArrayList() {
        this(20);
    }

    public MyArrayList(int initialSize) {
        if (initialSize < 1) {
            throw new IllegalArgumentException("Illegal Capacity: " + initialSize + ". Capacity must be at least 1");
        }
        this.size = 0;
        this.array = new Object[initialSize];
        this.inflateMultiplier = 3;
    }

    public void add(E e) {
        Object[] inflated;
        if (this.size < array.length) {
            insert(e);
        } else {
            inflated = new Object[array.length * inflateMultiplier];
            extendArray(inflated);
            insert(e);
        }
    }

    private void insert(E e) {
        array[size] = e;
        size++;
    }

    private void extendArray(Object[] inflated) {
        for (int i = 0; i < array.length; i++) {
            inflated[i] = array[i];
        }
        array = inflated;
    }

    public Object[] toArray() {
        trim();
        return array;
    }

    public int[] toIntArray() {
        trim();
        if (array[0] instanceof Integer) {
            int[] intArray = new int[array.length];
            this.toArray();
            for (int i = 0; i < array.length; i++) {
                intArray[i] = (int) array[i];
            }
            return intArray;
        }
        return null;
    }

    public void trim() {
        Object[] trimmed = new Object[size];
        for (int i = 0; i < size; i++) {
            trimmed[i] = array[i];
        }
        array = trimmed;
    }

    public int size() {
        return this.size;
    }

    public Object get(int index) {
        return array[index];
    }
}
