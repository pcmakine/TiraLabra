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
        this.size = 0;
        this.array = new Object[size];
        this.inflateMultiplier = 3;
    }

    public void insert(E e) {
        Object[] inflated;
        if (this.size < (array.length - 1)) {
            array[size] = e;
        } else {
            inflated = new Object[array.length * inflateMultiplier];
            copy(array, inflated);
        }

    }

    private void copy(Object[] array, Object[] inflated) {
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
