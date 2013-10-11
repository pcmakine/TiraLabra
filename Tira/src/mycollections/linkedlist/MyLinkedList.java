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
       return removeElementAt(0);
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
            return removeFromOneEntryList(node).getValue();
        }

        if (node.getNext().getNext() == node) {
            return removeFromTwoEntryList(node).getValue();
        }

        removeNode(node);
        return node.getValue();
    }

    private ListNode<E> removeFromOneEntryList(ListNode<E> node) {
        if (node == header) {
            header = null;
            size--;
            return node;
        }
        return null;
    }

    private ListNode<E> removeFromTwoEntryList(ListNode<E> node) {
        node.getNext().setNext(null);
        node.getNext().setPrev(null);
        header = node.getNext();        //whether the removable node is the current header or not, the other will be the header after the removal
        size--;
        return node;
    }

    public E removeElementAt(int index) {
        if (size == 0 || index > (size - 1) || index < 0) {
            return null;
        } else if (size == 1) {
            return removeFromOneEntryList(header).getValue();
        } else if(size == 2){
            ListNode<E> node = searchFromEnd(index);
            return removeFromTwoEntryList(node).getValue();
        }else if (index < (size / 2)) {
            ListNode<E> node = searchFromBeginning(index);
            removeNode(node);
            return node.getValue();
        } else {
            ListNode<E> node = searchFromEnd(index);
            removeNode(node);
            return node.getValue();
        }
    }

    private ListNode getElementAtOneEntryList(int index) {
        if (index == 0) {
            return header;
        } else {
            return null;
        }
    }

    private ListNode searchFromBeginning(int index) {
        ListNode<E> node = header;
        for (int i = 0; i < index; i++) {
            node = node.getNext();
        }
        return node;
    }

    private ListNode searchFromEnd(int index) {
        ListNode<E> node = header;
        for (int i = 0; i < (size - index); i++) {
            node = node.getPrev();
        }
        return node;
    }

    private void removeNode(ListNode node) {
        node.getPrev().setNext(node.getNext());
        node.getNext().setPrev(node.getPrev());
        if (node == header) {
            header = node.getNext();
        }
        size--;
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
