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
        
        int[] expected = new int[16];
        for (int i = 0; i < 16; i++) {
            expected[i] = i;
        }
        MyArrayList<Integer> list = new MyArrayList(5);
        for (int i = 0; i < 16; i++) {
            list.add(i);
        }
        assertArrayEquals(expected, intArray(list));
    }
    
    private int[] intArray(MyArrayList list){
        int[] intArray = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            intArray[i] = (int) list.get(i);
        }
        return intArray;
    }
    
    @Test
    public void elementsReturnedCorrectly(){
        MyArrayList<Integer> list = new MyArrayList();
        list.add(2);
        list.add(4);
        
        int four = list.get(1);
        assertEquals(4, four);
    }
}
