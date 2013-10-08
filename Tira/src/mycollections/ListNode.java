/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mycollections;

/**
 *
 * @author Pete
 */
public class ListNode<K, V> {
    private ListNode next;
    private ListNode prev;
    private final K key;
    private V value;

    public ListNode getPrev() {
        return prev;
    }
    
    public ListNode(K key, V value){
        this.key = key;
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
        
    public K getKey(){
        return key;
    }
    
    public V getValue(){
        return value;
    }
}
