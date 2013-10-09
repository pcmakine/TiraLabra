/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mycollections;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import tira.Node;

/**
 *
 * @author Pete
 */
public class MyLinkedListTest {

    Node a;
    Node b;
    Node c;

    public MyLinkedListTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        Node a = new Node(1, 1, 1);
        Node b = new Node(2, 1, 1);
        Node c = new Node(3, 1, 1);
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void insertWorksOnEmptyList() {
        MyLinkedList<Node, Integer> nodeList = new MyLinkedList();
        Node node = new Node(1, 1, 1);

        nodeList.insert(node, 3);

        assertEquals(3, (int) nodeList.peekFirst().getValue());
    }

    @Test
    public void pollFirstWorks() {
        MyLinkedList<Node, Integer> nodeList = new MyLinkedList();
        nodeList.insert(a, 1);
        nodeList.insert(b, 2);
        nodeList.insert(c, 3);

        assertEquals(1, (int) nodeList.pollFirst());
        assertEquals(2, (int) nodeList.pollFirst());
        assertEquals(3, (int) nodeList.pollFirst());
        assertEquals(null, nodeList.pollFirst());
    }

    @Test
    public void removeWorksForFirstEntry() {
        MyLinkedList<Node, Integer> nodeList = new MyLinkedList();
        nodeList.insert(a, 1);
        nodeList.insert(b, 2);
        nodeList.insert(c, 3);

        Node removed = (Node) nodeList.remove(a).getKey();

        assertEquals(a, removed);
        assertEquals(b, (Node) nodeList.peekFirst().getKey());
        assertEquals(c, (Node) nodeList.peekFirst().getNext().getKey());
        assertEquals(b, (Node) nodeList.peekFirst().getNext().getNext().getKey());
        assertEquals(c, (Node) nodeList.peekFirst().getPrev().getKey());
    }

    @Test
    public void removeWorksForMiddleEntry() {
        MyLinkedList<Node, Integer> nodeList = new MyLinkedList();

        nodeList.insert(a, 1);
        nodeList.insert(b, 2);
        nodeList.insert(c, 3);

        Node removed = (Node) nodeList.remove(b).getKey();

        assertEquals(b, removed);
        assertEquals(a, (Node) nodeList.peekFirst().getKey());
        assertEquals(c, (Node) nodeList.peekFirst().getNext().getKey());
        assertEquals(a, (Node) nodeList.peekFirst().getNext().getNext().getKey());
        assertEquals(c, (Node) nodeList.peekFirst().getPrev().getKey());
    }

    @Test
    public void removeWorksForOnlyEntry() {
        MyLinkedList<Node, Integer> nodeList = new MyLinkedList();

        nodeList.insert(a, 1);
        Node removed = (Node) nodeList.remove(a).getKey();

        assertEquals(a, removed);
        assertEquals(null, nodeList.pollFirst());
    }

    @Test
    public void getWorks() {
        MyLinkedList<Node, Integer> nodeList = new MyLinkedList();

        nodeList.insert(a, 1);
        assertEquals(a, nodeList.get(a).getKey());
        nodeList.insert(b, 1);
        assertEquals(b, nodeList.get(b).getKey());
    }
}
