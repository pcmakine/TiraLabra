/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package logicwithmycollections;

import logic.*;
import logic.PathFinder;
import logic.Node;
import logic.Graph;
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
    private static MGraph biggest;

    public MPathFinderTest() {
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
        MNode[] result = finder.bfs(21, 8);
        int[] resultIds = resultArray(result);

        assertArrayEquals(expected, resultIds);
    }

    private int[] resultArray(MNode[] result) {
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
        MNode[] result = finder.aStar(21, 8);
        int[] resultIds = resultArray(result);;

        assertArrayEquals(expected, resultIds);
    }

    @Test
    public void aStarVisitsCorrectNodesNoWalls(){
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
    public void testbfsTime() {
        int testRuns = 50;
        System.out.println("BFS Elapsed time for small: " + runBFSTimeTests(small, testRuns) + "ms");
        System.out.println("BFS Elapsed time for bigger: " + runBFSTimeTests(bigger, testRuns) + "ms");
        System.out.println("BFS Elapsed time for big: " + runBFSTimeTests(big, testRuns) + "ms");

        System.out.println("BFS Elapsed time for biggest: " + runBFSTimeTests(biggest, testRuns) + "ms");
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

    //doesn't seem to be very reliable. Huge variance.
    @Test
    public void testAstarTime() {
        int testRuns = 50;

        System.out.println("Astar Elapsed time for small: " + runAstarTimeTests(small, testRuns) + "ms");
        System.out.println("Astar Elapsed time for bigger: " + runAstarTimeTests(bigger, testRuns) + "ms");
        System.out.println("Astar Elapsed time for big: " + runAstarTimeTests(big, testRuns) + "ms");
        System.out.println("Astar Elapsed time for biggest: " + runAstarTimeTests(biggest, testRuns) + "ms");
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

    private static void makeRandomGraphsForTimeTests() {
        int smallSize = 200;
        int biggerSize = 283;
        int bigSize = 400;
        int biggestSize = 566;
        int wallsPortion = 10;
        
        long startTime = System.currentTimeMillis();
        small = new MGraph(smallSize);
        small.addNode(1);
        small.addNode(smallSize * smallSize);
        long stopTime = System.currentTimeMillis();
        long elapsedTime = (stopTime - startTime);
        System.out.println("small made in " + elapsedTime + " ms.");

        startTime = System.currentTimeMillis();
        bigger = new MGraph(biggerSize);
        bigger.addNode(1);
        bigger.addNode(biggerSize * biggerSize);
        stopTime = System.currentTimeMillis();
        elapsedTime = (stopTime - startTime);
        System.out.println("bigger made in " + elapsedTime + " ms.");

        startTime = System.currentTimeMillis();
        big = new MGraph(bigSize);
        big.addNode(1);
        big.addNode(bigSize * bigSize);
        stopTime = System.currentTimeMillis();
        elapsedTime = (stopTime - startTime);
        System.out.println("big made in " + elapsedTime + " ms.");

        startTime = System.currentTimeMillis();
        biggest = new MGraph(biggestSize);
        biggest.addNode(1);
        biggest.addNode(biggestSize * biggestSize);
        stopTime = System.currentTimeMillis();
        elapsedTime = (stopTime - startTime);
        System.out.println("biggest made in " + elapsedTime + " ms.");

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
