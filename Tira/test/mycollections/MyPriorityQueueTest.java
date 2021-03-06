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
import logicwithmycollections.Node;
import static org.junit.Assert.*;

/**
 *
 * @author Pete
 */
public class MyPriorityQueueTest {

    private static MyPriorityQueue<Node> q;
    private static Node a;
    private static Node b;
    private static Node c;

    public MyPriorityQueueTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        q = new MyPriorityQueue();
        a = new Node(1, 1, 1);
        b = new Node(2, 1, 1);
        c = new Node(3, 1, 1);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void addWorks() {
        MyPriorityQueue<Integer> q = new MyPriorityQueue();

        q.add(1);
        q.add(0);
        q.add(5);
        q.add(5);
        q.add(-2);

        int min = q.poll();

        assertEquals(-2, min);
    }

    @Test
    public void elementsInCorrectOrder() {
        MyPriorityQueue<Integer> q = new MyPriorityQueue();

        q.add(1);
        q.add(0);
        q.add(5);
        q.add(5);
        q.add(-2);

        int min = q.poll();

        assertEquals(-2, min);
        assertEquals(0, (int) q.poll());
        assertEquals(1, (int) q.poll());
        assertEquals(5, (int) q.poll());
        assertEquals(5, (int) q.poll());
        assertEquals(null, (Integer) q.poll());
    }

    @Test
    public void incKeyWorks() {
        a.setDist(5);

        b.setDist(3);

        c.setDist(2);

        q.add(a);
        q.add(b);
        q.add(c);

        c.setDist(7);
        q.incKey(c);

        b.setDist(6);
        q.incKey(b);

        assertEquals(a, q.poll());
        assertEquals(b, q.poll());
        assertEquals(c, q.poll());
    }

    @Test
    public void decKeyWorks() {
        MyPriorityQueue<Node> q = new MyPriorityQueue();

        a.setDist(5);
        b.setDist(4);
        c.setDist(3);

        q.add(a);
        q.add(b);
        q.add(c);

        a.setDist(1);
        q.decKey(a);

        b.setDist(2);
        q.decKey(b);

        assertEquals(a, q.poll());
        assertEquals(b, q.poll());
        assertEquals(c, q.poll());

    }
}
