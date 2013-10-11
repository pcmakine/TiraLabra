/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mycollections;

import mycollections.linkedlist.MyLinkedList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author pcmakine
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
    public void getElementAtOneElementList() {
        MyLinkedList l = new MyLinkedList();
        l.add(0);

        assertEquals(0, l.removeElementAt(0));
    }

    @Test
    public void removeElementAtWorks() {
        MyLinkedList l = new MyLinkedList();
        l.add(0);
        l.add(1);
        l.add(2);

        assertEquals(0, l.removeElementAt(0));
        assertEquals(2, l.removeElementAt(1));
        assertEquals(1, l.removeElementAt(0));
        assertEquals(null, l.removeElementAt(0));
    }

    @Test
    public void getElementAtWorksWithIndexOutOfBounds() {
        MyLinkedList l = new MyLinkedList();
        l.add(0);
        l.add(1);
        l.add(2);

        assertEquals(null, l.removeElementAt(3));
    }
    
        @Test
    public void pollFirstWorks() {
        MyLinkedList<Integer> list = new MyLinkedList();
        list.add(1);
        list.add(2);
        list.add(3);

        assertEquals(1, (int) list.pollFirst());
        assertEquals(2, (int) list.pollFirst());
        assertEquals(3, (int) list.pollFirst());
        assertEquals(null, list.pollFirst());
    }
}