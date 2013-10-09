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
//dependencies hashcode(). Only works for initialcapacity == 2^22 and smaller
public class MyHashMap<K, V> {

    private MyLinkedList<K, V>[] elements;
    private double constant = 0.61803;
    private int size;
    private final static int threshold = 2;

    public MyHashMap(int initialCapacity) {
        if (initialCapacity <= 0 || !isPowerOfTwo(initialCapacity) || initialCapacity > 4194304) {
            throw new IllegalArgumentException("Illegal capacity: " + initialCapacity);
        }

        elements = new MyLinkedList[initialCapacity];
    }

    private boolean isPowerOfTwo(int number) {
        return (number & -number) == number;
    }
    
    public boolean contains(K key){
        if (get(key) != null) {
            return true;
        }
        return false;
    }

    public void put(K key, V value) {
        int hash = key.hashCode();
        int index = getIndex(hash, elements.length);

        if (elements[index] == null) {
            MyLinkedList l = new MyLinkedList();
            l.insert(key, value);
            elements[index] = l;
        } else {
            elements[index].insert(key, value);
        }
        size++;
        if (size / elements.length > threshold) {
            rehash();
        }
    }

    private int getIndex(int key, int m) {
        int index = (int) (m * fr(constant * key));
        return index;
    }

    public double fr(double x) {
        return x - (int) x;
    }

    public V get(K key) {
        int hash = key.hashCode();
        int index = getIndex(hash, elements.length);

        if (elements[index] == null) {
            return null;
        } else {
            return (V) elements[index].get(key).getValue();
        }
    }

    public V remove(K key) {
        int hash = key.hashCode();
        int index = getIndex(hash, elements.length);

        if (elements[index] == null) {
            return null;
        } else {
            size--;
            return (V) elements[index].remove(key).getValue();
        }
    }

    private void rehash() {
        MyLinkedList<K, V>[] bigTable = new MyLinkedList[elements.length * 2];

        for (int i = 0; i < elements.length; i++) {
            if (elements[i] != null && elements[i].peekFirst() != null) {
                MyEntry node = elements[i].peekFirst();
                put((K) node.getKey(), (V) node.getValue(), bigTable);               
                while (node.getNext() != null && node.getNext() != node) {
                    put((K) node.getNext().getKey(), (V) node.getNext().getValue(), bigTable); 
                    node = node.getNext();
                }
            }
        }
    }

    public void put(K key, V value, MyLinkedList<K, V>[] bigTable) {
        int hash = key.hashCode();
        int index = getIndex(hash, bigTable.length);

        if (bigTable[index] == null) {
            MyLinkedList l = new MyLinkedList();
            l.insert(key, value);
            bigTable[index] = l;
        } else {
            bigTable[index].insert(key, value);
        }
    }
    
    public int size(){
        return size;
    }
    
    //for testing
    public MyLinkedList<K, V>[] getTable(){
        return elements;
    }
}
