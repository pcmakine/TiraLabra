/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package logicwithjava;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Stack;
import logicwithmycollections.Node;

/**
 *
 * @author pcmakine
 */
//http://www.policyalmanac.org/games/aStarTutorial.htm
public class JavaPathFinder {

    private JavaGraph graph;
    private boolean closed[][];
    private int visited;

    public JavaPathFinder(JavaGraph graph) {
        this.graph = graph;
    }

    public Node[] bfs(int startId, int goalId) {
        visited = 0;
        LinkedList<Integer> openList = new LinkedList();
        closed = new boolean[graph.getRows()][graph.getColumns()];
        boolean[][] open = new boolean[graph.getRows()][graph.getColumns()];
        int[] prev = new int[graph.getNumberofNodes()];

        openList.add(startId);
        open[graph.idToRow(startId)][graph.idToColumn(startId)] = true;

        while (!openList.isEmpty()) {
            int id = openList.pollFirst();
            open[graph.idToRow(id)][graph.idToColumn(id)] = false;
            Node node = graph.getNode(id);
            if (!closed[graph.idToRow(id)][graph.idToColumn(id)]) {
                closed[graph.idToRow(id)][graph.idToColumn(id)] = true;
                visited++;

                if (node == graph.getNode(goalId)) {
                    return getSolution(prev, node.getId(), startId);
                }
                add(node, openList, open, closed, prev);
            }
        }
        return null;
    }


    private void add(Node node, LinkedList openList, boolean[][] open, boolean[][] closed, int[] prev) {
        ArrayList<Node> neighbours = graph.getVerticalAndHorizontalNeighbours(node.getId());
        for (int i = 0; i < neighbours.size(); i++) {
            Node neighbour = neighbours.get(i);
            int id = neighbour.getId();
            if (!open[graph.idToRow(neighbour.getId())][graph.idToColumn(neighbour.getId())] && !closed[graph.idToRow(id)][graph.idToColumn(id)]) {
                openList.add(id);
                open[graph.idToRow(id)][graph.idToColumn(id)] = true;
                prev[id - 1] = node.getId();
            }
        }
    }

    private Node[] getSolution(int[] prev, int node, int origin) {
        Stack<Node> stack = new Stack();
        stack.push(graph.getNode(node));
        int nodes = 1;
        while (node != -1 && node != origin) {
            node = prev[node - 1];
            stack.push(graph.getNode(node));
            nodes++;
        }
        Node[] ordered = new Node[nodes];
        for (int i = 0; i < nodes; i++) {
            ordered[i] = stack.pop();
        }
        return ordered;
    }

    public Node[] aStar(int startId, int goalId) {
        visited = 0;
        PriorityQueue<Node> open = new PriorityQueue();
        closed = new boolean[graph.getRows()][graph.getColumns()];  //true if closed, otherwise false
        boolean[][] openMatrix = new boolean[graph.getRows()][graph.getColumns()];
        int[] prev = new int[graph.getNumberofNodes()];

        Node[][] nodes = graph.getNodes();

        graph.getNode(startId).setDist(0);
        graph.getNode(startId).setHeuristics(getManhattanHeuristics(graph.getNode(startId), graph.getNode(goalId)));
        open.add(graph.getNode(startId));

        while (!open.isEmpty()) {
            Node node = open.poll();
            int id = node.getId();

            if (!closed[graph.idToRow(id)][graph.idToColumn(id)]) {
                closed[graph.idToRow(id)][graph.idToColumn(id)] = true;
                visited++;
                if (node == graph.getNode(goalId)) {
                    return getSolution(prev, node.getId(), startId);
                }
                addForAstar(node, open, prev, graph.getNode(goalId), closed, openMatrix);
            }
        }
        return null;
    }

    private void addForAstar(Node node, PriorityQueue queue, int[] prev, Node target, boolean[][] closed, boolean[][] openMatrix) {
        ArrayList<Node> neighbours = graph.getVerticalAndHorizontalNeighbours(node.getId());
        for (int i = 0; i < neighbours.size(); i++) {
            Node neighbour = neighbours.get(i);

            int heuristics = getManhattanHeuristics(neighbour, target);
            neighbour.setHeuristics((int) heuristics);

            if (closed[graph.idToRow(neighbour.getId())][graph.idToColumn(neighbour.getId())] && node.getDist() + 1 >= neighbour.getDist()) {
                continue;
            }
            if (!openMatrix[graph.idToRow(neighbour.getId())][graph.idToColumn(neighbour.getId())] || (neighbour.getDist() > node.getDist() + 1)) {
                neighbour.setDist(node.getDist() + 1);
                updatePriority(neighbour, queue, openMatrix);
                prev[neighbour.getId() - 1] = node.getId();
            }
        }
    }

    private void updatePriority(Node node, PriorityQueue queue, boolean[][] openMatrix) {
        queue.remove(node);
        queue.add(node);
        openMatrix[graph.idToRow(node.getId())][graph.idToColumn(node.getId())] = true;
    }

    private int getManhattanHeuristics(Node start, Node target) {
        int startrow = (start.getId() - 1) / graph.getColumns();
        int startcolumn = (start.getId() - 1) % graph.getColumns();
        int targetrow = (target.getId() - 1) / graph.getColumns();
        int targetcolumn = (target.getId() - 1) % graph.getColumns();

        int distance = Math.abs(startrow - targetrow) + Math.abs(startcolumn - targetcolumn);

        return distance;
    }

    public boolean[][] getClosed() {
        return closed;
    }
    
    public int getVisited() {
        return visited;
    }
}