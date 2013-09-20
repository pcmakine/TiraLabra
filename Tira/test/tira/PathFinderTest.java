/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tira;

import java.util.HashMap;
import java.util.List;
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
public class PathFinderTest {

    private Graph graph;
    private Graph graph1;

    public PathFinderTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    //graph:
    // Node:          Neighbours:
    //  1               2, 3
    //  2               1, 4
    //  3               1, 4, 6
    //  4               2, 3, 5, 6
    //  5               4
    //  6               4
    @Before
    public void setUp() {
        int[][] neighboursarray = new int[][]{{2, 3}, {1, 4}, {1, 4}, {2, 3, 5, 6}, {4}, {4}};
        HashMap<Integer, List> neighbours = GraphTest.buildNeighboursHash(neighboursarray);
        graph = GraphTest.makeTestGraph(neighbours, GraphTest.makeNodes(neighboursarray.length));

        neighbours = GraphTest.buildNeighboursHash(new int[][]{{2, 5}, {1, 3}, {2, 4, 7}, {3, 5, 6}, {1, 4}, {4, 8}, {3, 8}, {6, 7}});
        graph1 = GraphTest.makeTestGraph(neighbours, GraphTest.makeNodes(8));
    }

    @After
    public void tearDown() {
    }

    @Test
    public void bfsLeastNodesFindsTheShortestRoute() {
        PathFinder finder = new PathFinder(graph);
        PathFinder finder1 = new PathFinder(graph1);

        String expected = makeResultString(new int[]{1, 2, 4, 5});
        String result = (String) finder.bfsLeastNodes(1, 5)[0];

        String result1 = (String) finder1.bfsLeastNodes(6, 1)[0];
        String expected1 = makeResultString(new int[]{6, 4, 5, 1});

        assertEquals("Expected string: " + expected + ", Result: " + result, true, expected.equalsIgnoreCase(result));
        assertEquals("Expected string: " + expected1 + ", Result: " + result1, true, expected1.equalsIgnoreCase(result1));
        assertEquals(true, makeResultString(new int[]{7, 3, 4, 5}).equalsIgnoreCase((String) (finder1.bfsLeastNodes(7, 5))[0]));
    }

//    @Test
//    public void aStarFindsTheShortestRoute() {
//        PathFinder finder = new PathFinder(graph);
//        PathFinder finder1 = new PathFinder(graph1);
//
//        String expected = makeResultString(new int[]{1, 2, 4, 5});
//        String result = finder.aStar(1, 5);
//
//        String result1 = finder1.aStar(6, 1);
//        String expected1 = makeResultString(new int[]{6, 4, 5, 1});
//
//        assertEquals("Expected string: " + expected + ", Result: " + result, true, expected.equalsIgnoreCase(result));
//        assertEquals("Expected string: " + expected1 + ", Result: " + result1, true, expected1.equalsIgnoreCase(result1));
//        assertEquals("Expected string: " + finder1.bfs(7, 5)[0] + ", Result: " + finder1.aStar(7, 5), true, makeResultString(new int[]{7, 3, 4, 5}).equalsIgnoreCase(finder1.aStar(7, 5)));
//    }

    //doesn't seem to be very reliable. Huge variance.
    @Test
    public void testbfsLeastNodesTime() {
        Graph small = makeRandomGraph(1000, 5);
        Graph bigger = makeRandomGraph(10000, 5);
        Graph big = makeRandomGraph(100000, 5);
        Graph huge = makeRandomGraph(1000000, 5);
        int testRuns = 1;

        System.out.println("Elapsed time for small: " + runBfs(small, testRuns) + "ms");
        System.out.println("Elapsed time for bigger: " + runBfs(bigger, testRuns) + "ms");
        System.out.println("Elapsed time for big: " + runBfs(big, testRuns) + "ms");
        System.out.println("Elapsed time for huge: " + runBfs(huge, testRuns) + "ms");
    }

    private long runBfs(Graph graph, int testRuns) {
        long elapsedAverage;
        PathFinder finder = new PathFinder(graph);

        long startTime = System.currentTimeMillis();
        for (int i = 0; i < testRuns; i++) {
            finder.bfsLeastNodes(980, 4);
        }
        long stopTime = System.currentTimeMillis();
        elapsedAverage = (stopTime - startTime) / testRuns;
        return elapsedAverage;
    }

    private String makeResultString(int[] route) {
        String result = "";
        for (int i = 0; i < route.length; i++) {
            result = result + "Node: " + route[i] + "\n";
        }
        return result;
    }

    public void testRandomGraph() {
        Graph test = makeRandomGraph(10, 3);
        System.out.println(test);
        PathFinder finder = new PathFinder(test);


        System.out.println(finder.bfsLeastNodes(4, 9));
    }

    public Graph makeRandomGraph(int nodes, int maxNeighbours) {
        Graph random;
        int[] neighboursMade = new int[nodes];
        int neighbours[][] = new int[nodes][maxNeighbours];
        for (int i = 0; i < nodes; i++) {
            int origin = i + 1;
            for (int j = 0; j < maxNeighbours; j++) {
                int neighbour = (int) (Math.random() * nodes) + 1;
                if (neighboursMade[i] < maxNeighbours && neighboursMade[neighbour - 1] < maxNeighbours && neighbour - 1 != i && !isNeighbourSetAlready(neighbours[i], neighbour)) {
                    neighbours[i][neighboursMade[i]] = neighbour;
                    neighboursMade[i]++;
                    neighbours[neighbour - 1][neighboursMade[neighbour - 1]] = origin;
                    neighboursMade[neighbour - 1]++;
                }
            }
        }
        HashMap<Integer, List> cities = GraphTest.buildNeighboursHash(neighbours);
        random = GraphTest.makeTestGraph(cities, GraphTest.makeNodes(nodes));
        return random;
    }

    private static boolean isNeighbourSetAlready(int[] neighbours, int neighbour) {
        for (int i = 0; i < neighbours.length; i++) {
            if (neighbours[i] == neighbour) {
                return true;
            }
        }
        return false;
    }
}
