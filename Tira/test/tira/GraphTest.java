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
    private Graph graph;

    public GraphTest(String testName) {
        super(testName);
        
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        
        int[][] neighboursarray = new int[][]{{2, 3}, {1, 4}, {1, 4}, {2, 3, 5, 6}, {4}, {4}};   
        HashMap<Integer, List> cities = buildCityHash(neighboursarray);
        graph = makeTestGraph(cities);
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    //Test graph:
    // Node:          Neighbours:
    //  1               4
    //  2               3, 1
    //  3               2
    //  4               
    @Test
    public void testConstructorCreatesNodesAndSetsNeighboursCorrectly() {
        int[][] neighboursarray = new int[][]{{4}, {3, 1}, {2}, {}};      
        HashMap<Integer, List> cities = buildCityHash(neighboursarray);
        Graph testGraph = makeTestGraph(cities);

        System.out.println(testGraph);
        
        ArrayList<Node> nodeNeighbours = testGraph.getNode(1).getNeighbours();
        assertArrayEquals(neighboursarray[0], nodeListToIdArray(nodeNeighbours));
    }
    
    private int[] nodeListToIdArray(List<Node> list){
        int[] array = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            array[i] = list.get(i).getId();
        }
        return array;
    }

    public static Graph makeTestGraph(HashMap cities) {
        Graph graph = new Graph(cities);
        return graph;
    }

    public static HashMap buildCityHash(int[][] neighboursarray) {
        HashMap<Integer, List> cities = new HashMap();
        for (int i = 0; i < neighboursarray.length; i++) {
            ArrayList neighbours = new ArrayList();
            for (int j = 0; j < neighboursarray[i].length; j++) {
                int neighbour = neighboursarray[i][j];
                if (neighbour != 0) {
                    neighbours.add(neighboursarray[i][j]);
                }           
            }
            cities.put(i + 1, neighbours);         
        }
        return cities;
    }
}
