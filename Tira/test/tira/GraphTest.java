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
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

/**
 *
 * @author pcmakine
 */
public class GraphTest {

    private Graph smallGraph;

    public GraphTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }
    
        @Before
    public void setUp() {
        smallGraph = new Graph(3);
    }

    @After
    public void tearDown() {
    }

    
    @Test
    public void isVerticalOrHorizontalNeighbourWorks(){
        Node node = new Node(1, 0, 0);
        Node downNode = new Node(2, Node.getHeight(), 0);
        Node rightNode = new Node(3, 0, Node.getWidth());
        Node randomNode = new Node(4, 50, 80);
        boolean result = smallGraph.isVerticalOrHorizontalNeighbour(node, downNode);
        boolean result2 = smallGraph.isVerticalOrHorizontalNeighbour(node, rightNode);
        boolean result3 = smallGraph.isVerticalOrHorizontalNeighbour(node, randomNode);
        
        assertEquals(true, result);
        assertEquals(true, result2);
        assertEquals(false, result3);
    }

//    Test graph:
//     Node:          Neighbours:
//      1               2, 4
//      3               2, 6
//      5               2, 4, 6, 8
//      9               6, 8  
    @Test
    public void neighboursMadeCorrectlyForSmallGraph() {
        smallGraph.makeNodes();
        int[] onesNeibs = {2, 4};
        int[] threesNeibs = {2, 6};
        int[] fivesNeibs = {2, 4, 6, 8};
        int[] ninesNeibs = {6, 8};

        ArrayList<Node> nodeoneNeighbours = smallGraph.getNode(1).getNeighbours();
        assertArrayEquals(onesNeibs, nodeListToIdArray(nodeoneNeighbours));
        assertArrayEquals(threesNeibs, nodeListToIdArray(smallGraph.getNode(3).getNeighbours()));
        assertArrayEquals(fivesNeibs, nodeListToIdArray(smallGraph.getNode(5).getNeighbours()));
        assertArrayEquals(ninesNeibs, nodeListToIdArray(smallGraph.getNode(9).getNeighbours()));
    }

    private int[] nodeListToIdArray(List<Node> list) {
        int[] array = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            array[i] = list.get(i).getId();
        }
        return array;
    }

}
