/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tira;

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
    private static Graph small;
    private static Graph bigger;
    private static Graph big;
    private static Graph biggest;

    public PathFinderTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        makeRandomGraphsForTimeTests();

    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        graph = new Graph(5);
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

     //doesn't seem to be very reliable. Huge variance.
    @Test
    public void testbfsTime() {
        int testRuns = 20;


        System.out.println("BFS Elapsed time for small: " + runBFSTimeTests(small, testRuns) + "ms");
        System.out.println("BFS Elapsed time for bigger: " + runBFSTimeTests(bigger, testRuns) + "ms");
        System.out.println("BFS Elapsed time for big: " + runBFSTimeTests(big, testRuns) + "ms");

        System.out.println("BFS Elapsed time for biggest: " + runBFSTimeTests(biggest, testRuns) + "ms");
    }

    private long runBFSTimeTests(Graph graph, int testRuns) {
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

    //doesn't seem to be very reliable. Huge variance.
    @Test
    public void testAstarTime() {
        int testRuns = 20;

        System.out.println("Astar Elapsed time for small: " + runAstarTimeTests(small, testRuns) + "ms");
        System.out.println("Astar Elapsed time for bigger: " + runAstarTimeTests(bigger, testRuns) + "ms");
        System.out.println("Astar Elapsed time for big: " + runAstarTimeTests(big, testRuns) + "ms");
        System.out.println("Astar Elapsed time for biggest: " + runAstarTimeTests(biggest, testRuns) + "ms");
    }

    private long runAstarTimeTests(Graph graph, int testRuns) {
        long elapsedAverage;
        PathFinder finder = new PathFinder(graph);

        long startTime = System.currentTimeMillis();
        for (int i = 0; i < testRuns; i++) {
            finder.aStar(1, graph.getMaxId());
        }
        long stopTime = System.currentTimeMillis();
        elapsedAverage = (stopTime - startTime) / testRuns;
        return elapsedAverage;
    }

    private static void makeRandomGraphsForTimeTests() {
        int smallSize = 200;
        int biggerSize = 283;
        int bigSize = 400;
        int biggestSize = 566;

        long startTime = System.currentTimeMillis();
        small = new Graph(smallSize, (smallSize * smallSize) / 20);
        small.addNode(1);
        small.addNode(smallSize*smallSize);
        long stopTime = System.currentTimeMillis();
        long elapsedTime = (stopTime - startTime);
        System.out.println("small made in " + elapsedTime + " ms.");

        startTime = System.currentTimeMillis();
        bigger = new Graph(biggerSize, (biggerSize * biggerSize) / 20);
        bigger.addNode(1);
        bigger.addNode(biggerSize*biggerSize);
        stopTime = System.currentTimeMillis();
        elapsedTime = (stopTime - startTime);
        System.out.println("bigger made in " + elapsedTime + " ms.");

        startTime = System.currentTimeMillis();
        big = new Graph(bigSize, (bigSize * bigSize) / 20);
        big.addNode(1);
        big.addNode(bigSize*bigSize);
        stopTime = System.currentTimeMillis();
        elapsedTime = (stopTime - startTime);
        System.out.println("big made in " + elapsedTime + " ms.");

        startTime = System.currentTimeMillis();
        biggest = new Graph(biggestSize, (biggestSize * biggestSize) / 20);
        biggest.addNode(1);
        biggest.addNode(biggestSize*biggestSize);
        stopTime = System.currentTimeMillis();
        elapsedTime = (stopTime - startTime);
        System.out.println("biggest made in " + elapsedTime + " ms.");

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

}
