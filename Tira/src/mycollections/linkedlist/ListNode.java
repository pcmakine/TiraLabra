/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mycollections.linkedlist;

/**
 *
 * @author Pete
 */
public class ListNode<E> {

    private ListNode next;
    private ListNode prev;
    private E value;

    public ListNode(E value) {
        this.value = value;
    }

    public ListNode getNext() {
        return next;
    }

    public void setNext(ListNode next) {
        this.next = next;
    }

    public void setPrev(ListNode prev) {
        this.prev = prev;
    }
    
    
    public ListNode getPrev() {
        return prev;
    }

    public E getValue() {
        return value;
    }

}
