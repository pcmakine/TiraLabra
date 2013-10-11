/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package logicwithmycollections;

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
public class MPathFinderTest {

    private MGraph graph;
    private MGraph graph1;
    private static MGraph small;
    private static MGraph bigger;
    private static MGraph big;
    private static MGraph huge;
    private static MGraph huger;
    private static MGraph a;
    private static MGraph biggest;
    private static int[] sizes = {200, 283, 400, 566, 800, 1131, 1599};
    private static MGraph[] graphs = {small, bigger, big, huge, huger, a, biggest};

    public MPathFinderTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        System.out.println("");
        System.out.println("PathFinder test with my collections");
        for (int i = 0; i < graphs.length; i++) {
            graphs[i] = makeGraph(sizes[i], 1, sizes[i] * sizes[i]);
        }
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        graph = new MGraph(5);
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
        MPathFinder finder = new MPathFinder(graph);
        MPathFinder finder1 = new MPathFinder(graph1);

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
        MPathFinder finder = new MPathFinder(graph);

        int[] expected = {21, 22, 23, 18, 19, 20, 15, 10, 9, 8};
        Node[] result = finder.aStar(21, 8);
        int[] resultIds = resultArray(result);;

        assertArrayEquals(expected, resultIds);
    }

    @Test
    public void aStarVisitsCorrectNodesNoWalls() {
        MGraph test = new MGraph(4);
        MPathFinder finder = new MPathFinder(test);

        finder.aStar(13, 3);
        boolean[][] expected = {{true, true, true, false},
            {true, false, false, false},
            {true, false, false, false},
            {true, false, false, false}};
        boolean[][] visited = finder.getClosed();

        assertArrayEquals(expected, visited);
    }

    @Test
    public void aStarVisitsCorrectNodesWithWalls() {
        MPathFinder finder = new MPathFinder(graph);

        finder.aStar(21, 8);
        boolean[][] expected = {{true, false, false, false, false},
            {true, false, true, true, true},
            {true, false, false, false, true},
            {true, false, true, true, true},
            {true, true, true, true, false}};
        boolean[][] visited = finder.getClosed();

        assertArrayEquals(expected, visited);
    }

    @Test
    public void testbfsTime() {
        System.out.println("");
        System.out.println("BFS time test with my collections:");

        int testRuns = 50;
        for (int i = 0; i < 6; i++) {
            System.out.println("Graph of " + graphs[i].getColumns() * graphs[i].getRows() + " nodes: " + runBFSTimeTests(graphs[i], testRuns) + "ms");
        }
    }

    private long runBFSTimeTests(MGraph graph, int testRuns) {
        long elapsedAverage;
        MPathFinder finder = new MPathFinder(graph);
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
        System.out.println("A* time test with my collections:");

        int testRuns = 50;
        for (int i = 0; i < graphs.length; i++) {
            System.out.println("Graph of " + graphs[i].getColumns() * graphs[i].getRows() + " nodes: " + runAstarTimeTests(graphs[i], testRuns) + "ms.");
        }
    }

    private long runAstarTimeTests(MGraph graph, int testRuns) {
        long elapsedAverage;
        MPathFinder finder = new MPathFinder(graph);

        long startTime = System.currentTimeMillis();
        for (int i = 0; i < testRuns; i++) {
            finder.aStar(1, graph.getMaxId());
        }
        long stopTime = System.currentTimeMillis();
        elapsedAverage = (stopTime - startTime) / testRuns;
        return elapsedAverage;
    }

    private static MGraph makeGraph(int size, int originId, int goalId) {
        long startTime = System.currentTimeMillis();
        MGraph test = new MGraph(size);
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
//        small = new MGraph(smallSize, (smallSize * smallSize) / wallsPortion);
//        small.addNode(1);
//        small.addNode(smallSize*smallSize);
//        long stopTime = System.currentTimeMillis();
//        long elapsedTime = (stopTime - startTime);
//        System.out.println("small made in " + elapsedTime + " ms.");
//
//        startTime = System.currentTimeMillis();
//        bigger = new MGraph(biggerSize, (biggerSize * biggerSize) / wallsPortion);
//        bigger.addNode(1);
//        bigger.addNode(biggerSize*biggerSize);
//        stopTime = System.currentTimeMillis();
//        elapsedTime = (stopTime - startTime);
//        System.out.println("bigger made in " + elapsedTime + " ms.");
//
//        startTime = System.currentTimeMillis();
//        big = new MGraph(bigSize, (bigSize * bigSize) / wallsPortion);
//        big.addNode(1);
//        big.addNode(bigSize*bigSize);
//        stopTime = System.currentTimeMillis();
//        elapsedTime = (stopTime - startTime);
//        System.out.println("big made in " + elapsedTime + " ms.");
//
//        startTime = System.currentTimeMillis();
//        biggest = new MGraph(biggestSize, (biggestSize * biggestSize) / wallsPortion);
//        biggest.addNode(1);
//        biggest.addNode(biggestSize*biggestSize);
//        stopTime = System.currentTimeMillis();
//        elapsedTime = (stopTime - startTime);
//        System.out.println("biggest made in " + elapsedTime + " ms.");
    }
}
