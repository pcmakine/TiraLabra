/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tira;

import gui.Controller;
import java.util.ArrayList;
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

    @Before
    public void setUp() {
        graph = new Graph(5);
        graph.makeNodes();
        graph.removeNode(graph.getNode(2));
        graph.removeNode(graph.getNode(7));
        graph.removeNode(graph.getNode(12));
        graph.removeNode(graph.getNode(13));
        graph.removeNode(graph.getNode(14));
        graph.removeNode(graph.getNode(17));
    }

    @After
    public void tearDown() {
    }

    @Test
    public void bfsFindsTheShortestRoute() {
        PathFinder finder = new PathFinder(graph);
        PathFinder finder1 = new PathFinder(graph1);

        int[] expected = {21, 22, 23, 18, 19, 20, 15, 10, 9, 8};
        Node[] result = finder.bfs(21, 8);
        int[] resultIds = resultArray(result);

        assertArrayEquals(expected, resultIds);
    }

    private int[] resultArray(Node[] result) {
        int count = 0;
        for (int i = 0; i < result.length; i++) {
            if (result[i] != null) {
                count++;
            } else {
                break;
            }
        }

        int[] ret = new int[count];
        for (int i = 0; i < count; i++) {
            ret[i] = result[i].getId();
        }
        return ret;
    }

    @Test
    public void aStarFindsTheShortestRoute() {
        PathFinder finder = new PathFinder(graph);

        int[] expected = {21, 22, 23, 18, 19, 20, 15, 10, 9, 8};
        Node[] result = finder.aStar(21, 8);
        int[] resultIds = resultArray(result);;

        assertArrayEquals(expected, resultIds);

    }

    //doesn't seem to be very reliable. Huge variance.
    @Test
    public void testbfsTime() {
        Controller c = new Controller();
        int smallSize = 30;
        int biggerSize = 100;
        int bigSize = 150;
        int biggestSize = 250;
        Graph small = c.makeCustomGraph(smallSize, (c.decideNodesToRemove(smallSize, ((smallSize*smallSize)/20), 1, smallSize*smallSize)));
        Graph bigger = c.makeCustomGraph(biggerSize, (c.decideNodesToRemove(biggerSize, ((biggerSize*biggerSize)/20), 1, biggerSize*biggerSize)));
        Graph big = c.makeCustomGraph(bigSize, (c.decideNodesToRemove(bigSize, ((bigSize*bigSize)/20), 1, bigSize*bigSize)));
//        Graph biggest = c.makeCustomGraph(biggestSize, (c.decideNodesToRemove(biggestSize, ((biggestSize*biggestSize)/20), 1, biggestSize*biggestSize)));
//        Graph big = c.makeCustomGraph(300, c.decideNodesToRemove(100, (100*100)/20));
        int testRuns = 50;

        System.out.println("BFS Elapsed time for small: " + runTimeTests(small, testRuns) + "ms");
        System.out.println("BFS Elapsed time for bigger: " + runTimeTests(bigger, testRuns) + "ms");
        System.out.println("BFS Elapsed time for big: " + runTimeTests(big, testRuns) + "ms");
//        System.out.println("Elapsed time for biggest: " + runBfs(biggest, testRuns) + "ms");
    }
    
        //doesn't seem to be very reliable. Huge variance.
    @Test
    public void testAstarTime() {
        Controller c = new Controller();
        int smallSize = 30;
        int biggerSize = 100;
        int bigSize = 150;
        int biggestSize = 250;
        Graph small = c.makeCustomGraph(smallSize, (c.decideNodesToRemove(smallSize, ((smallSize*smallSize)/20), 1, smallSize*smallSize)));
        Graph bigger = c.makeCustomGraph(biggerSize, (c.decideNodesToRemove(biggerSize, ((biggerSize*biggerSize)/20), 1, biggerSize*biggerSize)));
        Graph big = c.makeCustomGraph(bigSize, (c.decideNodesToRemove(bigSize, ((bigSize*bigSize)/20), 1, bigSize*bigSize)));
//        Graph biggest = c.makeCustomGraph(biggestSize, (c.decideNodesToRemove(biggestSize, ((biggestSize*biggestSize)/20), 1, biggestSize*biggestSize)));
//        Graph big = c.makeCustomGraph(300, c.decideNodesToRemove(100, (100*100)/20));
        int testRuns = 50;

        System.out.println("Astar Elapsed time for small: " + runTimeTests(small, testRuns) + "ms");
        System.out.println("Astar Elapsed time for bigger: " + runTimeTests(bigger, testRuns) + "ms");
        System.out.println("Astar Elapsed time for big: " + runTimeTests(big, testRuns) + "ms");
//        System.out.println("Elapsed time for biggest: " + runBfs(biggest, testRuns) + "ms");
    }
    
    private long runTimeTests(Graph graph, int testRuns) {
        long elapsedAverage;
        PathFinder finder = new PathFinder(graph);

        long startTime = System.currentTimeMillis();
        for (int i = 0; i < testRuns; i++) {
           finder.bfs(1, graph.getMaxId());
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

//    public void testRandomGraph() {
//        Graph test = makeRandomGraph(10, 3);
//        System.out.println(test);
//        PathFinder finder = new PathFinder(test);
//        System.out.println(finder.bfs(4, 9));
//    }



    private static boolean isNeighbourSetAlready(int[] neighbours, int neighbour) {
        for (int i = 0; i < neighbours.length; i++) {
            if (neighbours[i] == neighbour) {
                return true;
            }
        }
        return false;
    }
}
