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
public class MyPriorityQueue<E extends Comparable<E>> {

    private ArrayList<E> elements;

    public MyPriorityQueue() {
        this.elements = new ArrayList();
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
        } else {
            elements.set(i, e);
        }
    }

    //returns and removes the first element of the queue
    public E poll() {
        E min = elements.get(0);
        if (elements.size() > 1) {
            elements.set(0, elements.remove(elements.size() - 1));
            heapify(0);
        } else {
            elements.remove(0);
        }
        return min;
    }

    public boolean isEmpty() {
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
            }
        } else if (l == elements.size() - 1 && elements.get(i).compareTo(elements.get(l)) > 0) {
            swap(i, l);
        }
    }

    private void swap(int i, int j) {
        E temp;
        if (i > elements.size() - 1) {
            temp = null;
            elements.add(elements.get(j));
        } else {
            temp = elements.get(i);
        }

        elements.set(i, elements.get(j));
        elements.set(j, temp);
    }

    private int parent(int i) {
        return (int) Math.ceil(i / 2);
    }

    private int left(int i) {
        return 2 * i;
    }

    private int right(int i) {
        return 2 * i + 1;
    }
}
