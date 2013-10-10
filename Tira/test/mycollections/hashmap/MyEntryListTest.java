/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mycollections.hashmap;

import mycollections.hashmap.MyEntryList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import logicwithjava.Node;
import logicwithmycollections.MNode;

/**
 *
 * @author Pete
 */
public class MyEntryListTest {

    MNode a;
    MNode b;
    MNode c;

    public MyEntryListTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        MNode a = new MNode(1, 1, 1);
        MNode b = new MNode(2, 1, 1);
        MNode c = new MNode(3, 1, 1);
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
        MyEntryList<MNode, Integer> nodeList = new MyEntryList();
        MNode node = new MNode(1, 1, 1);

        nodeList.insert(node, 3);

        assertEquals(3, (int) nodeList.peekFirst().getValue());
    }

    @Test
    public void pollFirstWorks() {
        MyEntryList<MNode, Integer> nodeList = new MyEntryList();
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
        MyEntryList<MNode, Integer> nodeList = new MyEntryList();
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
        MyEntryList<MNode, Integer> nodeList = new MyEntryList();

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
        MyEntryList<MNode, Integer> nodeList = new MyEntryList();

        nodeList.insert(a, 1);
        Node removed = (Node) nodeList.remove(a).getKey();

        assertEquals(a, removed);
        assertEquals(null, nodeList.pollFirst());
    }

    @Test
    public void getWorks() {
        MyEntryList<MNode, Integer> nodeList = new MyEntryList();

        nodeList.insert(a, 1);
        assertEquals(a, nodeList.get(a).getKey());
        nodeList.insert(b, 1);
        assertEquals(b, nodeList.get(b).getKey());
    }
}
