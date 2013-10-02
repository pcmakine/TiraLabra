/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tira;

import mycollections.MyPriorityQueue;
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
public class MyPriorityQueueTest {
    
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
    }
    
    @After
    public void tearDown() {
    }

     @Test
     public void addWorksOnEmptyqueue() {
         MyPriorityQueue q = new MyPriorityQueue();
         
         q.add(1);
         q.add(0);
         q.add(5);
         q.add(5);
         q.add(-2);
         
         q.add(new Node (0,0,0));
         
         int min = (int) q.poll();
         
         assertEquals(-2, min);
     }
}
