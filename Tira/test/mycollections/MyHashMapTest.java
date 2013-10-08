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
public class MyHashMapTest {
    
    public MyHashMapTest() {
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
     public void putWorks() {
         MyHashMap map = new MyHashMap(32);      
         Node node = new Node(1, 1, 1);      
         Node otherNode = new Node(2, 1, 1);
         map.put(node, 5);
         map.put(otherNode, 99);
         
         assertEquals(5, map.get(node));
         assertEquals(99, map.get(otherNode));
     }
}
