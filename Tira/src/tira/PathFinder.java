/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tira;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Stack;

/**
 *
 * @author pcmakine
 */
//http://www.policyalmanac.org/games/aStarTutorial.htm
public class PathFinder {

    Graph graph;

    public PathFinder(Graph graph) {
        this.graph = graph;
    }

    public Node[] bfs(int startId, int goalId) {

        LinkedHashMap<Integer, Node> open = new LinkedHashMap();
        HashMap<Integer, Node> handled = new HashMap();
        Node start = graph.getNode(startId);
        Node goal = graph.getNode(goalId);

        int[] prev = new int[graph.getNumberofNodes()];

        open.put(start.getId(), start);
        prev[start.getId() - 1] = -1;

        while (!open.isEmpty()) {

            int id = open.keySet().iterator().next();
            Node node = open.get(id);

            open.remove(id);

            if (!handled.containsKey(node.getId())) {
                handled.put(node.getId(), node);

                if (node == goal) {
                    return getSolution(prev, node.getId(), startId);
                }
                add(node, open, handled, prev);
            }

        }
        return null;
    }

    private void add(Node node, LinkedHashMap nodeMap, HashMap handled, int[] prev) {
        ArrayList<Node> neighbours = graph.getVerticalAndHorizontalNeighbours(node.getId());
        for (int i = 0; i < neighbours.size(); i++) {
            Node neighbour = neighbours.get(i);

            int id = neighbour.getId();

            if (!nodeMap.containsKey(id) && !handled.containsKey(id)) {
                nodeMap.put(id, neighbour);
                prev[id - 1] = node.getId();
            }
        }
    }

    private Node[] getSolution(int[] prev, int node, int origin) {
        Stack<Node> resultStack = new Stack();
        Node[] reverseOrder = new Node[prev.length];
        Node[] ordered = new Node[prev.length];
        int nodes = 0;
        while (node != -1) {
            reverseOrder[nodes] = graph.getNode(node);
            nodes++;

            if (node == origin) {
                break;
            }
            node = prev[node - 1];
        }
        int j = 0;
        for (int i = nodes - 1; i >= 0; i--) {
            ordered[j] = reverseOrder[i];
            j++;
        }
        return ordered;
    }

//    private int[] getIntSolution(int[] prev, int node, int origin) {
//        Stack<Node> resultStack = new Stack();
//        int[] reverseOrder = new int[prev.length];
//        int[] ordered = new int[prev.length];
//        int nodes = 0;
//        while (node != -1) {
//            reverseOrder[nodes] = node;
//            nodes++;
//
//            if (node == origin) {
//                break;
//            }
//            node = prev[node - 1];
//        }
//        int j = 0;
//        for (int i = nodes - 1; i >= 0; i--) {
//            ordered[j] = reverseOrder[i];
//            j++;
//        }
//        return ordered;
//    }

    public Node[] aStar(int startId, int goalId) {
        PriorityQueue<Node> open = new PriorityQueue();
        boolean[][] closed = new boolean[graph.getRows()][graph.getColumns()];  //true if closed, otherwise false
        int[] prev = new int[graph.getNumberofNodes()];
        int prevId = -1;

        Node[][] nodes = graph.getNodes();

        for (int i = 0; i < nodes.length; i++) {
            for (int j = 0; j < nodes[0].length; j++) {
                Node node = nodes[i][j];
                if (node != null) {
                    node.setDist(Integer.MAX_VALUE);
                }
            }
        }

        graph.getNode(startId).setDist(0);
        open.add(graph.getNode(startId));

        while (!open.isEmpty()) {
            Node node = open.poll();
            int id = node.getId();

            if (!closed[graph.idToRow(id)][graph.idToColumn(id)]) {
                closed[graph.idToRow(id)][graph.idToColumn(id)] = true;
                if (node == graph.getNode(goalId)) {
                    return getSolution(prev, node.getId(), startId);
                }
                addForAstar(node, open, prev, graph.getNode(goalId), closed);
            }
        }
        return null;
    }

    private void addForAstar(Node node, PriorityQueue queue, int[] prev, Node target, boolean[][] closed) {
        ArrayList<Node> neighbours = graph.getVerticalAndHorizontalNeighbours(node.getId());

        for (int i = 0; i < neighbours.size(); i++) {
            Node neighbour = neighbours.get(i);

            int heuristics = getManhattanHeuristics(neighbour, target);
            neighbour.setHeuristics((int) heuristics);
            if (neighbour.getDist() > node.getDist() + 1) {
                neighbour.setDist(node.getDist() + 1);
                prev[neighbour.getId() - 1] = node.getId();
            }
            queue.add(neighbour);
        }
    }

//    public int[] arrayAStar(int startId, int goalId) {
//        PriorityQueue<Integer> open = new PriorityQueue();
//        int size = graph.getRows();
//        boolean[][] closed = new boolean[size][size];
//        int[][] distToStart = new int[size][size];
//        int[][] heuristics = new int[size][size];
//        int[] prev = new int[graph.getMaxId()];
//
//        int prevId = -1;
//        initDistance(distToStart);
//
//        distToStart[graph.idToRow(startId)][graph.idToColumn(startId)] = 0;
//        open.add(startId);
//
//        while (!open.isEmpty()) {
//            int node = open.poll();
//
//            if (!closed[graph.idToRow(node)][graph.idToColumn(node)]) {
//                closed[graph.idToRow(node)][graph.idToColumn(node)] = true;
//                if (node == goalId) {
//                    return getIntSolution(prev, node, startId);
//                }
//                addForArrayAstar(node, open, prev, goalId, closed, heuristics, distToStart);
//            }
//        }
//        return null;
//    }
    private void initDistance(int[][] distToStart) {
        for (int i = 0; i < distToStart.length; i++) {
            for (int j = 0; j < distToStart[0].length; j++) {
                distToStart[i][j] = Integer.MAX_VALUE;
            }
        }
    }

//    private void addForArrayAstar(int node, PriorityQueue queue, int[] prev, int target, boolean[][] closed, int[][] heuristics, int[][] distToStart) {
//        ArrayList<Integer> neighbours = graph.getVerticalAndHorizontalNeighbours(node);
//
//        for (int i = 0; i < neighbours.size(); i++) {
//            int neighbour = neighbours.get(i);
//            int neighbourDist = distToStart[graph.idToRow(neighbour)][graph.idToColumn(neighbour)];
//            int nodeDist = distToStart[graph.idToRow(node)][graph.idToColumn(node)];
//            heuristics[graph.idToRow(neighbour)][graph.idToColumn(neighbour)] = getPrimitiveManhattanHeuristics(neighbour, target);
//            if (neighbourDist > nodeDist + 1) {
//                distToStart[graph.idToRow(neighbour)][graph.idToColumn(neighbour)] = nodeDist + 1;
//                prev[neighbour - 1] = node;
//            }
//            queue.add(neighbour);
//        }
//    }
    //Returns the euclidian distance of two nodes
    private double getHeuristics(Node start, Node target) {
        double a = Math.abs(start.getX() - target.getX());
        double b = Math.abs(start.getY() - target.getY());

        double distance = Math.sqrt((a * a) + (b * b));

        return distance / 20;
    }

    private int getPrimitiveManhattanHeuristics(int start, int target) {
        int startrow = graph.idToRow(start);
        int startcolumn = graph.idToColumn(start);
        int targetrow = graph.idToRow(target);
        int targetcolumn = graph.idToColumn(target);

        int distance = Math.abs(startrow - targetrow) + Math.abs(startcolumn - targetcolumn);

        return distance;
    }

    private int getManhattanHeuristics(Node start, Node target) {
        int startrow = (start.getId() - 1) / graph.getColumns();
        int startcolumn = (start.getId() - 1) % graph.getColumns();
        int targetrow = (target.getId() - 1) / graph.getColumns();
        int targetcolumn = (target.getId() - 1) % graph.getColumns();

        int distance = Math.abs(startrow - targetrow) + Math.abs(startcolumn - targetcolumn);

        return distance;
    }
}