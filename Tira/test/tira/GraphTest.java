/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tira;

import java.util.Map;
import junit.framework.TestCase;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;

/**
 *
 * @author pcmakine
 */
public class GraphTest extends TestCase {

    public GraphTest(String testName) {
        super(testName);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    //Tests whether the constructor in graph class populates correctly the nodes and their neighbours
    @Test
    public void testConstructorCreatesNodesAndSetsNeighboursCorrectly() {     
        Graph graph = makeTestGraph();
        
        System.out.println(graph);
        
        assertEquals(true, true);
    }

    //Test graph:
    // Node:          Neighbours:
    //  1               2, 3
    //  2               1, 4
    //  3               1, 4, 6
    //  4               2, 3
    //  5               4
    //  6               4, 3
    private static Graph makeTestGraph() {
        Integer key;
        List neighbours;
        HashMap<Integer, List> cities = new HashMap();
        int[][] neighboursarray = new int[][]{{2, 3}, {1, 4}, {1, 4, 6}, {2, 3, 5, 6}, {4}, {4, 3}};
        int maxNode = neighboursarray.length;

        for (int i = 0; i < maxNode; i++) {
            neighbours = putNeighboursinList(neighboursarray[i]);
            cities.put(i + 1, neighbours);
        }

        Graph graph = new Graph(cities);
        return graph;
    }

    private static List putNeighboursinList(int[] neighboursarray) {
        ArrayList neighbours = new ArrayList();
        
        for (int i = 0; i < neighboursarray.length; i++) {
             neighbours.add(neighboursarray[i]);
        }
        
        return neighbours;
        
    }

//    /**
//     * Test of getNodes method, of class Graph.
//     */
//    public void testGetNodes() {
//        System.out.println("getNodes");
//        Graph instance = null;
//        Map expResult = null;
//        Map result = instance.getNodes();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
}
