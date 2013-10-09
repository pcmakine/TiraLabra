/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mycollections.hashmap;

/**
 *
 * @author Pete
 */
public class MyEntry<K, V> {
    private MyEntry next;
    private MyEntry prev;
    private final K key;
    private V value;

    public MyEntry getPrev() {
        return prev;
    }
    
    public MyEntry(K key, V value){
        this.key = key;
        this.value = value;
    }

    public MyEntry getNext() {
        return next;
    }

    public void setNext(MyEntry next) {
        this.next = next;
    }
        public void setPrev(MyEntry prev) {
        this.prev = prev;
    }
        
    public K getKey(){
        return key;
    }
    
    public V getValue(){
        return value;
    }
}
