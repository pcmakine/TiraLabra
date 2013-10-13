/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mycollections.hashmap;

import java.util.HashMap;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import logicwithmycollections.Node;

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
        System.out.println("");
        System.out.println("MyHashMap tests: ");
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
        MyHashMap<Node, Integer> map2 = new MyHashMap(1048576);
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
    public void putAndGetWorks() {
        MyHashMap<Node, Integer> map = new MyHashMap(32);
        Node node = new Node(1, 1, 1);
        Node otherNode = new Node(2, 1, 1);
        map.put(node, 5);
        map.put(otherNode, 99);

        assertEquals(5, (int) map.get(node));
        assertEquals(99, (int) map.get(otherNode));
    }

    @Test //doesn't necessarily always pass, but should pass most of the time
    public void hashingPutsOnAverageTwoOnEachBucketWhenTwiceAsManyElementsAsHashTableSize() {
        int sum = 0;
        int testRuns = 10;
        allSum = 0;
        int mapSize = 131072;
        sum = averages(testRuns, mapSize, sum);
        int limit = (int) ((((mapSize * 2.0)) / mapSize) + 2);
        int avg = sum / testRuns;
        System.out.println("Biggest bucket on average: " + avg + " mapSize: " + mapSize);
        System.out.println("Average bucket size: " + (allSum / mapSize / testRuns) + " mapSize: " + mapSize);

        assertEquals(true, (allSum / mapSize / testRuns) == 2);
    }

    private int findAverageSize(MyEntryList[] lists, int max) {
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

    private int averages(int testRuns, int mapSize, int sum) {
        for (int j = 0; j < testRuns; j++) {

            MyHashMap<Node, Integer> map = new MyHashMap(mapSize);
            for (int i = 0; i < mapSize * 2; i++) {
                map.put(new Node(i + 1, 1, 1), i);
            }
            MyEntryList lists[] = map.getTable();
            int max = 0;
            max = findAverageSize(lists, max);
            sum = sum + max;
        }
        return sum;
    }

    @Test
    public void rehashingDoublesArraySize() {
        MyHashMap map = new MyHashMap(2);

        for (int i = 0; i < 5; i++) {
            map.put(new Node(i + 1, 1, 1), i);
        }
        assertEquals(4, map.getTable().length);
    }

    @Test
    public void rehashingPreservesElements() {
        MyHashMap map = new MyHashMap(2);
        int n = 9;
        Node[] nodes = new Node[n];
        
        for (int i = 0; i < n; i++) {
            Node node = new Node(i + 1, 1, 1);
            map.put(node, i);
            nodes[i] = node;
        }

        for (int i = 0; i < n; i++) {
            assertEquals(i, map.get(nodes[i]));
        }
    }

    @Test
    public void removeWorks() {
        MyHashMap map = new MyHashMap(2);
        Node[] nodes = new Node[5];

        for (int i = 0; i < 5; i++) {
            Node node = new Node(i + 1, 1, 1);
            map.put(node, i);
            nodes[i] = node;
        }
        
        map.remove(nodes[2]);
        assertEquals(null, map.get(nodes[2]));
    }
}
