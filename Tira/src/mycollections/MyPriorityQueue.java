/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mycollections;

import mycollections.hashmap.MyHashMap;

/**
 *
 * @author Pete
 */
public class MyPriorityQueue<E extends Comparable<E>> {

    private MyArrayList<E> elements;
    private MyHashMap<E, Integer> indexes;

    public MyPriorityQueue() {
        this.elements = new MyArrayList();
        this.indexes = new MyHashMap();
    }

    //adds the element to the right place in the queue
    public void add(E e) {
        int i = elements.size();
        while (i > 0 && elements.get(parent(i)).compareTo(e) > 0) {
            swap(i, parent(i));
            i = parent(i);
        }
        if (i > elements.size() - 1) {
            elements.add(e);
            indexes.put(e, elements.size() - 1);
        } else {
            elements.set(i, e);
            indexes.put(e, i);
        }
    }

    //returns and removes the first element of the queue
    public E poll() {
        if (elements.isEmpty()) {
            return null;
        }
        E min = elements.get(0);
        if (elements.size() > 1) {
            indexes.remove(elements.get(elements.size()-1));
            elements.set(0, elements.remove(elements.size() - 1));
            heapify(0);
        } else {
            indexes.remove(elements.get(0));
            elements.remove(0);
        }
        indexes.remove(min);
        return min;
    }

    public boolean incKey(E element) {
        if (indexes.get(element) != null) {
            int index = indexes.get(element);

            elements.set(index, element);
            heapify(index);
            return true;
        }
        return false;
    }

    public boolean decKey(E element) {
        if (indexes.containsKey(element)) {

            int index = indexes.get(element);

            while (index >= 1 && (elements.get(index).compareTo(elements.get(parent(index)))) < 0) {
                swap(index, parent(index));
                index = parent(index);
            }
            return true;
        }
        return false;
    }

    private void heapify(int i) {
        int l = left(i);
        int r = right(i);
        int smallest;

        if (right(i) <= elements.size() - 1) {
            if (elements.get(l).compareTo(elements.get(r)) < 0) {
                smallest = l;
            } else {
                smallest = r;
            }
            if (elements.get(i).compareTo(elements.get(smallest)) > 0) {
                swap(i, smallest);
                heapify(smallest);
            }
        } else if (l == elements.size() - 1 && elements.get(i).compareTo(elements.get(l)) > 0) {
            swap(i, l);
        }
    }

    private void swap(int i, int j) {
        if (i > elements.size() - 1) {
            elements.add(elements.get(j));
            indexes.put(elements.get(elements.size() - 1), elements.size() - 1);
        } else {
            E temp = elements.get(i);
            elements.set(i, elements.get(j));
            indexes.put(elements.get(i), i);
            elements.set(j, temp);
            indexes.put(temp, j);
        }
    }

    private int parent(int i) {
        return (int) Math.ceil(i* 1.0 / 2)-1;
    }

    private int left(int i) {
        return 2 * i+1;
    }

    private int right(int i) {
        return 2 * i + 2;
    }

    public boolean isEmpty() {
        return elements.isEmpty();
    }
    
    public boolean contains(E element){
        return indexes.containsKey(element);
    }
}