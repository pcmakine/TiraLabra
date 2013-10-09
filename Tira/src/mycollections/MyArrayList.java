/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mycollections;

/**
 *
 * @author Pete
 */
public class MyArrayList<E> {

    private int size;           //number of elements in the list
    private E[] array;
    private static final int inflateMultiplier  = 3; //how much we increase the array's size every time it has to be increased

    public MyArrayList() {
        this(32);
    }

    public MyArrayList(int initialSize) {
        if (initialSize < 1) {
            throw new IllegalArgumentException("Illegal Capacity: " + initialSize + ". Capacity must be at least 1");
        }
        this.size = 0;
        this.array = (E[]) new Object[initialSize];
    }

    public void add(E e) {
        E[] inflated;
        if (this.size < array.length) {
            insert(e);
        } else {
            inflated = (E[]) new Object[array.length * inflateMultiplier];
            extendArray(inflated);
            insert(e);
        }
    }

    private void insert(E e) {
        array[size] = e;
        size++;
    }

    private void extendArray(E[] inflated) {
        for (int i = 0; i < array.length; i++) {
            inflated[i] = array[i];
        }
        array = inflated;
        
    }
    
    public void set(int index, E element){
        array[index] = element;
    }

    public E[] toArray() {
        trim();
        return array;
    }
    
    public void trim() {
        E[] trimmed = (E[]) new Object[size];
        for (int i = 0; i < size; i++) {
            trimmed[i] = array[i];
        }
        array = trimmed;
    }

    public int size() {
        return this.size;
    }

    public E get(int index) {
        return array[index];
    }
    
    public int getCapacity(){
        return array.length;
    }
}
