/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mycollections;

import java.util.HashMap;
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

    int allSum;

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

    @Test //shouldn't throw any exceptions
    public void constructorWorkingForPowersOfTwo() {
        MyHashMap<Node, Integer> map = new MyHashMap(4);
        MyHashMap<Node, Integer> map2 = new MyHashMap(4194304);
    }

    @Test
    public void constructorNotWorkingForNonPowersOfTwo() {
        try {
            MyHashMap<Node, Integer> map1 = new MyHashMap(5);
            fail("My method didn't throw when I expected it to");
        } catch (IllegalArgumentException expectedException) {
        }

        try {
            MyHashMap<Node, Integer> map1 = new MyHashMap(31);
            fail("My method didn't throw when I expected it to");
        } catch (IllegalArgumentException expectedException) {
        }
    }

    @Test
    public void putWorks() {
        MyHashMap<Node, Integer> map = new MyHashMap(32);
        Node node = new Node(1, 1, 1);
        Node otherNode = new Node(2, 1, 1);
        map.put(node, 5);
        map.put(otherNode, 99);

        assertEquals(5, (int) map.get(node));
        assertEquals(99, (int) map.get(otherNode));
    }

    @Test //doesn't necessarily always pass, but should pass usually
    public void hashingPutsOnAverageTwoOnEachBucketWhenTwiceAsManyElementsAsHashTableSize() {
        int sum = 0;
        int testRuns = 10;
        allSum = 0;
        int mapSize = 131072;
        for (int j = 0; j < testRuns; j++) {

            MyHashMap<Node, Integer> map = new MyHashMap(mapSize);
            for (int i = 0; i < mapSize * 2; i++) {
                map.put(new Node(i + 1, 1, 1), i);
            }
            MyLinkedList lists[] = map.getTable();
            int max = 0;
            max = findMaxSize(lists, max);
            sum = sum + max;
        }
        int limit = (int) ((((mapSize * 2.0)) / mapSize) + 2);
        int avg = sum / testRuns;
        System.out.println("Biggest bucket on average: " + avg + " mapSize: " + mapSize);
        System.out.println("Average bucket size: " + (allSum / mapSize/testRuns) + " mapSize: " + mapSize);
        
        assertEquals(true, (allSum/mapSize/testRuns) == 2);
    }

    private int findMaxSize(MyLinkedList[] lists, int max) {
        for (int i = 0; i < lists.length; i++) {
            if (lists[i] != null) {
                allSum = allSum + lists[i].size();
                if (lists[i].size() > max) {
                    max = lists[i].size();

                }
            }
        }
        return max;
    }
}
