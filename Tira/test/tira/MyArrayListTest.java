/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tira;

import mycollections.MyArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Pete
 */
public class MyArrayListTest {

    public MyArrayListTest() {
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
    public void elementsInsertedCorrectly() {
        MyArrayList test = new MyArrayList();
        test.add(5);
        
        MyArrayList<Node> test2 = new MyArrayList();
        Node node = new Node(1, 2, 3);
        test2.add(node);
        
        int[] expected = new int[16];
        for (int i = 0; i < 16; i++) {
            expected[i] = i;
        }
        MyArrayList<Integer> list = new MyArrayList(5);
        for (int i = 0; i < 16; i++) {
            list.add(i);
        }
        assertArrayEquals(expected, list.toIntArray());
    }
    
    @Test
    public void elementsReturnedCorrectly(){
        MyArrayList list = new MyArrayList();
        list.add(2);
        list.add(4);
        
        int four = (int) list.get(1);
        assertEquals(4, four);
    }
}
