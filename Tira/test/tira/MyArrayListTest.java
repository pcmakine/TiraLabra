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
    public void sizeIncreasedCorrectly() {
        int[] arr = new int[15];
        for (int i = 0; i < 15; i++) {
            arr[i] = i;
        }
        MyArrayList<Integer> list = new MyArrayList(5);
        for (int i = 0; i < 15; i++) {
            list.add(i);
        }

//        assertArrayEquals(expected, list.toIntArray());
    }

    @Test
    public void elementsInsertedCorrectly() {

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
}
