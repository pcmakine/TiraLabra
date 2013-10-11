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

/**
 *
 * @author pcmakine
 */
public class MyStackTest {

    public MyStackTest() {
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
    public void pushAndPopWorksOneElement() {
        MyStack s = new MyStack();
        s.push(1);

        assertEquals(1, s.pop());
    }

    @Test
    public void pushAndPopWorksManyElements() {
        MyStack s = new MyStack();
        s.push(1);
        s.push(2);
        s.push(3);

        assertEquals(3, s.pop());
        assertEquals(2, s.pop());
        assertEquals(1, s.pop());
        assertEquals(null, s.pop());
    }
}