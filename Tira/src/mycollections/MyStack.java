/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mycollections;

import mycollections.linkedlist.MyLinkedList;

/**
 *
 * @author pcmakine
 */
public class MyStack<E> {

    private int size;
    private MyLinkedList<E> elements;

    public MyStack() {
        this.size = 0;
        elements = new MyLinkedList();
    }

    public E peek() {
        return elements.peekFirst();
    }

    public E pop() {
        size--;
        return elements.removeElementAt(elements.size() - 1);
    }

    public void push(E value) {
        elements.add(value);
        size++;
    }
    
    public boolean isEmpty(){
        return size == 0;
    }
}
