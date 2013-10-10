/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mycollections.linkedlist;

/**
 *
 * @author Pete
 */
public class MyLinkedList<E> {

    private ListNode<E> header;
    private int size;

    public MyLinkedList() {
    }

    public void add(E value) {
        ListNode node = new ListNode(value);

        if (header == null) {
            insertInEmptyList(node);
        } else if (header.getPrev() == null) {
            insertInOneEntryList(node);
        } else {
            insertInMultiEntryList(node);
        }
        size++;
    }

    public void addFirst(E value) {
        ListNode node = new ListNode(value);
        if (header == null) {
            insertInEmptyList(node);
        }
    }

    private void insertInEmptyList(ListNode node) {
        node.setNext(null);
        node.setPrev(null);
        header = node;
    }

    private void insertInOneEntryList(ListNode node) {
        header.setPrev(node);
        header.setNext(node);
        node.setPrev(header);
        node.setNext(header);
    }

    private void insertHeadOfOneEntryList(ListNode node) {
        header.setPrev(node);
        header.setNext(node);
        
        node.setPrev(header);
        node.setNext(header);
        
        header = node;

    }

    private void insertInMultiEntryList(ListNode node) {
        ListNode last = header.getPrev();
        header.setPrev(node);
        node.setNext(header);
        node.setPrev(last);
        last.setNext(node);
    }

    public E pollFirst() {
        if (header == null) {
            return null;
        }
        ListNode<E> first = header;
        ListNode<E> second = header.getNext();
        ListNode<E> last = header.getPrev();

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

    public E getValue(E value) {
        ListNode<E> node = get(value);
        if (node == null) {
            return null;
        } else {
            return node.getValue();
        }
    }

    public ListNode get(E value) {
        if (header == null) {
            return null;
        }
        if (header.getValue() == value) {
            return header;
        }
        ListNode<E> node = header.getNext();
        while (node != header && node != null) {
            if (node.getValue() == value) {
                return node;
            }
            node = node.getNext();
        }
        return null;
    }

    public E remove(E value) {
        ListNode<E> node = get(value);

        if (node == null) {
            return null;
        }

        if (node == header && header.getNext() == null) {
            size--;
            return removeFromOneEntryList(node).getValue();
        }

        if (node.getNext().getNext() == node) {
            size--;
            return removeFromTwoEntryList(node).getValue();
        }

        node.getPrev().setNext(node.getNext());
        node.getNext().setPrev(node.getPrev());
        if (node == header) {
            header = header.getNext();
        }
        size--;
        return node.getValue();
    }

    private ListNode<E> removeFromOneEntryList(ListNode<E> node) {
        header = null;
        return node;
    }

    private ListNode<E> removeFromTwoEntryList(ListNode<E> node) {
        node.getNext().setNext(null);
        node.getNext().setPrev(null);
        header = node.getNext();        //whether the removable node is the current header or not, the other will be the header after the removal
        return node;
    }

    public E peekFirst() {
        return header.getValue();
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }
}
