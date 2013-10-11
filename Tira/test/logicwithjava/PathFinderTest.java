/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package logicwithjava;

import logicwithmycollections.Node;
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
    private static Graph huge;
    private static Graph huger;
    private static Graph a;
    private static Graph biggest;
    private static int[] sizes = {200, 283, 400, 566, 800, 1131, 1599};
    private static Graph[] graphs = {small, bigger, big, huge, huger, a, biggest};

    public PathFinderTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        System.out.println("");
        System.out.println("PathFinder test with java tools");
        for (int i = 0; i < graphs.length; i++) {
            graphs[i] = makeGraph(sizes[i], 1, sizes[i] * sizes[i]);
        }

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

    @Test
    public void aStarFindsTheShortestRoute() {
        PathFinder finder = new PathFinder(graph);

        int[] expected = {21, 22, 23, 18, 19, 20, 15, 10, 9, 8};
        Node[] result = finder.aStar(21, 8);
        int[] resultIds = resultArray(result);;

        assertArrayEquals(expected, resultIds);
    }

    @Test
    public void aStarVisitsCorrectNodesNoWalls() {
        Graph test = new Graph(4);
        PathFinder finder = new PathFinder(test);

        finder.aStar(13, 3);
        boolean[][] expected = {{true, true, true, false},
            {true, false, false, false},
            {true, false, false, false},
            {true, false, false, false}};
        boolean[][] visited = finder.getClosed();

        assertArrayEquals(expected, visited);
    }

    @Test
    public void testbfsTime() {
        System.out.println("");
        System.out.println("BFS time test with java tools:");

        int testRuns = 50;
        for (int i = 0; i < 6; i++) {
            System.out.println("Graph of " + graphs[i].getColumns() * graphs[i].getRows() + " nodes: " + runBFSTimeTests(graphs[i], testRuns) + "ms");
        }
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

    @Test
    public void testAstarTime() {
        System.out.println("");
        System.out.println("A* time test with java tools:");

        int testRuns = 50;
        for (int i = 0; i < graphs.length; i++) {
            System.out.println("Graph of " + graphs[i].getColumns() * graphs[i].getRows() + " nodes: " + runAstarTimeTests(graphs[i], testRuns) + "ms.");
        }
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

    private static Graph makeGraph(int size, int originId, int goalId) {
        long startTime = System.currentTimeMillis();
        Graph test = new Graph(size);
        test.addNode(originId);
        test.addNode(goalId);
        long stopTime = System.currentTimeMillis();
        long elapsedTime = (stopTime - startTime);
//        System.out.println("Graph of " + (size * size) + " nodes made in " + elapsedTime + " ms.");
        return test;
    }

    private static void makeRandomGraphsForTimeTests() {
        //System.out.println("biggest made in " + elapsedTime + " ms.");
//        long startTime = System.currentTimeMillis();
//        small = new Graph(smallSize, (smallSize * smallSize) / wallsPortion);
//        small.addNode(1);
//        small.addNode(smallSize*smallSize);
//        long stopTime = System.currentTimeMillis();
//        long elapsedTime = (stopTime - startTime);
//        System.out.println("small made in " + elapsedTime + " ms.");
//
//        startTime = System.currentTimeMillis();
//        bigger = new Graph(biggerSize, (biggerSize * biggerSize) / wallsPortion);
//        bigger.addNode(1);
//        bigger.addNode(biggerSize*biggerSize);
//        stopTime = System.currentTimeMillis();
//        elapsedTime = (stopTime - startTime);
//        System.out.println("bigger made in " + elapsedTime + " ms.");
//
//        startTime = System.currentTimeMillis();
//        big = new Graph(bigSize, (bigSize * bigSize) / wallsPortion);
//        big.addNode(1);
//        big.addNode(bigSize*bigSize);
//        stopTime = System.currentTimeMillis();
//        elapsedTime = (stopTime - startTime);
//        System.out.println("big made in " + elapsedTime + " ms.");
//
//        startTime = System.currentTimeMillis();
//        biggest = new Graph(biggestSize, (biggestSize * biggestSize) / wallsPortion);
//        biggest.addNode(1);
//        biggest.addNode(biggestSize*biggestSize);
//        stopTime = System.currentTimeMillis();
//        elapsedTime = (stopTime - startTime);
//        System.out.println("biggest made in " + elapsedTime + " ms.");
    }
}
