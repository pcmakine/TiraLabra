/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mycollections.hashmap;

/**
 *
 * @author Pete
 */
public class MyEntryList<K, V> {

    private MyEntry<K, V> header;
    private int size;

    public MyEntryList() {
    }

    public void insert(K key, V value) {
        MyEntry node = new MyEntry(key, value);
        
        if (header == null) {
            insertInEmptyList(node);
        } else if (header.getPrev() == null) {
            insertInOneEntryList(node);
        } else {
            insertInMultiEntryList(node);
        }
        size++;
    }

    private void insertInEmptyList(MyEntry node) {
        node.setNext(null);
        node.setPrev(null);
        header = node;
    }

    private void insertInOneEntryList(MyEntry node) {
        header.setPrev(node);
        header.setNext(node);
        node.setPrev(header);
        node.setNext(header);
    }

    private void insertInMultiEntryList(MyEntry node) {
        MyEntry last = header.getPrev();
        header.setPrev(node);
        node.setNext(header);
        node.setPrev(last);
        last.setNext(node);
    }

    public V pollFirst() {
        if (header == null) {
            return null;
        }
        MyEntry<K, V> first = header;
        MyEntry<K, V> second = header.getNext();
        MyEntry<K, V> last = header.getPrev();

        if (second == null) {
            header = null;
            size--;
            return first.getValue();
        } else if (last == second) {
            second.setPrev(null);
            second.setNext(null);
        } else {
            second.setPrev(last);
        }
        header = second;
        size--;
        return first.getValue();
    }

    public MyEntry get(K key) {
        if(header == null){
            return null;
        }
        if (header.getKey() == key) {
            return header;
        }
        MyEntry<K, V> node = header.getNext();
        while (node != header && node != null) {
            if (node.getKey() == key) {
                return node;
            }
            node = node.getNext();
        }
        return null;
    }
    
    public MyEntry remove(K key) {
        MyEntry node = get(key);

        if (node == null) {
            return null;
        }

        if (node == header && header.getNext() == null) {
            size--;
            return removeFromOneEntryList(node);
        }

        if (node.getNext().getNext() == node) {
            size--;
            return removeFromTwoEntryList(node);
        }

        node.getPrev().setNext(node.getNext());
        node.getNext().setPrev(node.getPrev());
        if (node == header) {
            header = header.getNext();
        }
        size--;
        return node;
    }

    private MyEntry removeFromOneEntryList(MyEntry node) {
        header = null;
        return node;
    }

    private MyEntry removeFromTwoEntryList(MyEntry node) {
        node.getNext().setNext(null);
        node.getNext().setPrev(null);
        header = node.getNext();        //whether the removable node is the current header or not, the other will be the header after the removal
        return node;
    }

    public MyEntry peekFirst() {
        return header;
    }
    
    public int size(){
        return size;
    }
}
