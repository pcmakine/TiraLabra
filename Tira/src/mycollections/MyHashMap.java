/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mycollections;

import java.util.ArrayList;

/**
 *
 * @author Pete
 */
//dependencies hashcode()
public class MyHashMap<K, V> {

    private MyLinkedList<K, V>[] elements;
    private double constant = 0.61803;
    private int size;
    private final static int treashold = 2;

    public MyHashMap(int initialCapacity) {
        if (initialCapacity <= 0 || !isPowerOfTwo(initialCapacity)) {
            throw new IllegalArgumentException("Illegal capacity: " + initialCapacity);
        }

        elements = new MyLinkedList[initialCapacity];
    }

    private boolean isPowerOfTwo(int number) {
        return (number & -number) == number;
    }

    public void put(K key, V value) {
        int hash = key.hashCode();
        int index = getIndex(hash);

        if (elements[index] == null) {
            MyLinkedList l = new MyLinkedList();
            l.insert(key, value);
            elements[index] = l;
        } else {
            elements[index].insert(key, value);
        }
        size++;
        if(size / elements.length > treashold){
            rehash();
        }
    }

    private int getIndex(int key) {
        int m = elements.length;
        int index = (int) (m * fr(constant * key));
        return index;
    }

    public double fr(double x) {
        return x - (int) x;
    }

    public V get(K key) {
        int hash = key.hashCode();
        int index = getIndex(hash);

        if (elements[index] == null) {
            return null;
        } else {
            return (V) elements[index].get(key).getValue();
        }
    }

    public V remove(K key) {
        int hash = key.hashCode();
        int index = getIndex(hash);

        if (elements[index] == null) {
            return null;
        } else {
            size--;
            return (V) elements[index].remove(key).getValue();
        }
    }

    private void rehash() {
        MyLinkedList<K, V>[] bigTable = new MyLinkedList[elements.length*2];
        
        for (int i = 0; i < elements.length; i++) {
            if (elements[i] != null) {
                MyEntry node = elements[i].peekFirst();
            }
        }
    }
}
