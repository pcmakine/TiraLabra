/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mycollections;

/**
 *
 * @author Pete
 */
public class MyLinkedList<K, V> {

    private ListNode<K, V> header;

    public MyLinkedList() {
    }

    public void insert(K key, V value) {
        ListNode node = new ListNode(key, value);

        if (header == null) {
            node.setNext(null);
            node.setPrev(null);
            header = node;
        } else if (header.getPrev() == null) {
            header.setPrev(node);
            header.setNext(node);
            node.setPrev(header);
            node.setNext(header);
        } else {
            ListNode last = header.getPrev();
            header.setPrev(node);
            node.setNext(header);
            node.setPrev(last);
            last.setNext(node);
        }
    }

    public V pollFirst() {
        if (header == null) {
            return null;
        }
        ListNode<K, V> first = header;
        ListNode<K, V> second = header.getNext();
        ListNode<K, V> last = header.getPrev();

        if (second == null) {
            header = null;
            return first.getValue();
        } else if (last == second) {
            second.setPrev(null);
            second.setNext(null);
        } else {
            second.setPrev(last);
        }
        header = second;
        return first.getValue();
    }

    public V get(K key) {
        if (header.getKey() == key) {
            return header.getValue();
        }
        ListNode<K, V> node = header.getNext();
        while (node != header) {
            if (node.getKey() == key) {
                return node.getValue();
            }
            node = node.getNext();
        }
        return null;
    }

    public V peekFirst() {
        return header.getValue();
    }
}
