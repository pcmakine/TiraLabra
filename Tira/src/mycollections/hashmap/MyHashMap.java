/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mycollections.hashmap;

import java.util.ArrayList;

/**
 *
 * @author Pete
 */
//dependencies hashcode(). Only works for initialcapacity == 2^22 and smaller
//current implementation does not decrease the table size at any point --> Not very efficient in using memory
public class MyHashMap<K, V> {

    private MyEntryList<K, V>[] elements;
    private double constant = 0.61803;
    private int size;
    private static final int threshold = 2;
    private static final int DEFAULT_INIT_CAPACITY = 16;

    public MyHashMap() {
        this(DEFAULT_INIT_CAPACITY);
    }

    public MyHashMap(int initialCapacity) {
        if (initialCapacity <= 0 || !isPowerOfTwo(initialCapacity) || initialCapacity > 4194304) {
            throw new IllegalArgumentException("Illegal capacity: " + initialCapacity);
        }

        elements = new MyEntryList[initialCapacity];
    }

    private boolean isPowerOfTwo(int number) {
        return (number & -number) == number;
    }

    public boolean contains(K key) {
        if (get(key) != null) {
            return true;
        }
        return false;
    }

    public void put(K key, V value) {
        int hash = key.hashCode();
        int index = getIndex(hash, elements.length);

        if (elements[index] == null) {
            MyEntryList l = new MyEntryList();
            l.insert(key, value);
            elements[index] = l;
        } else {
            MyEntry toReplace = elements[index].get(key);
            if (toReplace == null) {
                elements[index].insert(key, value);
            }else{
                toReplace.setValue(value);
            }
        }
        size++;
        if ((1.0 * size) / elements.length > threshold) {
            rehash();
        }
    }

    private int getIndex(int key, int m) {
        int index = (int) (m * fr(constant * key));
        return abs(index);
    }

    private int abs(int x) {
        if (x < 0) {
            return x * -1;
        }
        return x;
    }

    public double fr(double x) {
        return x - (int) x;
    }

    public V get(K key) {
        int hash = key.hashCode();
        int index = getIndex(hash, elements.length);

        if (elements[index] == null) {
            return null;
        }
        MyEntry entry = elements[index].get(key);
        if (entry != null) {
            return (V) entry.getValue();
        } else {
            return null;
        }
    }

    public V remove(K key) {
        int hash = key.hashCode();
        int index = getIndex(hash, elements.length);

        if (elements[index] == null) {
            return null;
        } else {
            size--;
            MyEntry<K, V> ret = elements[index].remove(key);
            if (ret == null) {
                return null;
            }
            return ret.getValue();
        }
    }

    private void rehash() {
        MyEntryList<K, V>[] bigTable = new MyEntryList[elements.length * 2];

        for (int i = 0; i < elements.length; i++) {
            if (elements[i] != null && elements[i].peekFirst() != null) {
                MyEntry first = elements[i].peekFirst();
                put((K) first.getKey(), (V) first.getValue(), bigTable);
                MyEntry node = first.getNext();
                while (node != null && node != first) {
                    put((K) node.getKey(), (V) node.getValue(), bigTable);
                    node = node.getNext();
                }
            }
        }
        elements = bigTable;
    }

    public void put(K key, V value, MyEntryList<K, V>[] bigTable) {
        int hash = key.hashCode();
        int index = getIndex(hash, bigTable.length);

        if (bigTable[index] == null) {
            MyEntryList l = new MyEntryList();
            l.insert(key, value);
            bigTable[index] = l;
        } else {
            bigTable[index].insert(key, value);
        }
    }

    public int size() {
        return size;
    }

    //for testing
    public MyEntryList<K, V>[] getTable() {
        return elements;
    }
}
