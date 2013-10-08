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

    public MyLinkedListTest() {
    }

    @BeforeClass
    public static void setUpClass() {
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
        Node a = new Node(1, 1, 1);
        Node b = new Node(2, 1, 1);
        Node c = new Node(3, 1, 1);

        nodeList.insert(a, 1);
        nodeList.insert(b, 2);
        nodeList.insert(c, 3);

        assertEquals(1, (int) nodeList.pollFirst());
        assertEquals(2, (int) nodeList.pollFirst());
        assertEquals(3, (int) nodeList.pollFirst());
        assertEquals(null, nodeList.pollFirst());
    }

    @Test
    public void removeWorks() {
        MyLinkedList<Node, Integer> nodeList = new MyLinkedList();
        Node a = new Node(1, 1, 1);
        Node b = new Node(2, 1, 1);
        Node c = new Node(3, 1, 1);

        nodeList.insert(a, 1);
        nodeList.insert(b, 2);
        nodeList.insert(c, 3);

        assertEquals(1, (int) nodeList.pollFirst());
        assertEquals(2, (int) nodeList.pollFirst());
        assertEquals(3, (int) nodeList.pollFirst());
        assertEquals(null, nodeList.pollFirst());
    }
}
