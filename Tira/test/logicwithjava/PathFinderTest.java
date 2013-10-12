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
    private static int[] sizes = {283, 400, 566, 800, 1131, 1599};
    private static Graph[] graphs = {bigger, big, huge, huger, a, biggest};
    private static Graph biggerRandom;
    private static Graph bigRandom;
    private static Graph hugeRandom;
    private static Graph[] randoms = {biggerRandom, bigRandom, hugeRandom};
    private static int testRuns = 50;

    public PathFinderTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        System.out.println("");
        System.out.println("PathFinder test with java tools");
        for (int i = 0; i < graphs.length; i++) {
            graphs[i] = makeGraph(sizes[i], 1, sizes[i] * sizes[i]);
        }

        for (int i = 0; i < randoms.length; i++) {
            randoms[i] = makeRandomGraph(sizes[i], 1, sizes[i] * sizes[i], 3);
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
        System.out.println("BFS time test using java tools:");

        for (int i = 0; i < 6; i++) {
            System.out.println(", time: " + runTimeTests(graphs[i], testRuns, "bfs") + "ms");
        }

        System.out.println("");
        System.out.println("BFS time tests with random graphs using java tools:");
        for (int i = 0; i < randoms.length; i++) {
            System.out.print(", time: " + runTimeTests(randoms[i], testRuns, "bfs") + "ms.");
            System.out.println("");
        }
    }

    @Test
    public void testAstarTime() {
        System.out.println("");
        System.out.println("A* time test using java tools:");

        for (int i = 0; i < graphs.length; i++) {
            System.out.println(", time: " + runTimeTests(graphs[i], testRuns, "astar") + "ms.");
        }
        System.out.println("");
        System.out.println("A* time tests with random graphs using java tools:");
        for (int i = 0; i < randoms.length; i++) {
            System.out.println(", time: " + runTimeTests(randoms[i], testRuns, "astar") + "ms.");
        }
    }

    private long runTimeTests(Graph graph, int testRuns, String algo) {
        long elapsedAverage;
        Node[] sol = new Node[1];
        PathFinder finder = new PathFinder(graph);
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < testRuns; i++) {
            if (algo.equals("bfs")) {
                sol = finder.bfs(1, graph.getMaxId());
            } else {
                sol = finder.aStar(1, graph.getMaxId());
            }
        }
        if (sol == null) {
            System.out.print("No solution for a graph of " + graph.getColumns() * graph.getRows() + " nodes. Visited " + finder.getVisited() + " nodes");
        }else{
            System.out.print("Length of path for a graph of " + graph.getColumns() * graph.getRows() + " nodes: " + sol.length + ". Visited " + finder.getVisited() + " nodes");
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

    private static Graph makeRandomGraph(int size, int originId, int goalId, int wallsPortion) {
        long startTime = System.currentTimeMillis();
        Graph test = new Graph(size, (size * size) / wallsPortion);
        test.addNode(originId);
        test.addNode(goalId);
        long stopTime = System.currentTimeMillis();
        long elapsedTime = (stopTime - startTime);
//        System.out.println("random Graph of " + (size * size) + " nodes made in " + elapsedTime + " ms.");
        return test;
    }
}
