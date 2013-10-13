/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package logicwithmycollections;

import java.util.LinkedList;
import mycollections.MyArrayList;
import mycollections.MyPriorityQueue;
import mycollections.MyStack;
import mycollections.linkedlist.MyLinkedList;

/**
 *
 * @author pcmakine
 */
//http://www.policyalmanac.org/games/aStarTutorial.htm
public class PathFinder {

    private Graph graph;
    private boolean closed[][];
    private int visited;        //for testing

    public PathFinder(Graph graph) {
        this.graph = graph;
        this.visited = 0;
    }

    public Node[] bfs(int startId, int goalId) {
        visited = 0;
        MyLinkedList<Integer> openList = new MyLinkedList();
        closed = new boolean[graph.getRows()][graph.getColumns()];
        boolean[][] open = new boolean[graph.getRows()][graph.getColumns()];
        int[] prev = new int[graph.getNumberofNodes()];

        openList.add(startId);
        open[graph.idToRow(startId)][graph.idToColumn(startId)] = true;

        prev[startId - 1] = -1;

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

    private void add(Node node, MyLinkedList openList, boolean[][] open, boolean[][] closed, int[] prev) {
        MyArrayList<Node> neighbours = graph.getVerticalAndHorizontalNeighbours(node.getId());
        for (int i = 0; i < neighbours.size(); i++) {
            Node neighbour = neighbours.get(i);
            int id = neighbour.getId();
            if (!open[graph.idToRow(id)][graph.idToColumn(id)] && !closed[graph.idToRow(id)][graph.idToColumn(id)]) {
                openList.add(id);
                open[graph.idToRow(id)][graph.idToColumn(id)] = true;
                prev[id - 1] = node.getId();
            }
        }
    }

    private Node[] getSolution(int[] prev, int node, int origin) {
        MyStack<Node> stack = new MyStack();
        stack.push(graph.getNode(node));
        int nodes = 1;
        while (node != origin) {
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
        MyPriorityQueue<Node> open = new MyPriorityQueue();
        closed = new boolean[graph.getRows()][graph.getColumns()];  //true if closed, otherwise false
        int[] prev = new int[graph.getNumberofNodes()];
        int prevId = -1;

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
                addForAstar(node, open, prev, graph.getNode(goalId), closed);
            }
        }
        return null;
    }

    private void addForAstar(Node node, MyPriorityQueue queue, int[] prev, Node target, boolean[][] closed) {
        MyArrayList<Node> neighbours = graph.getVerticalAndHorizontalNeighbours(node.getId());
        for (int i = 0; i < neighbours.size(); i++) {
            Node neighbour = neighbours.get(i);
            neighbour.setHeuristics((int) getManhattanHeuristics(neighbour, target));

            if (closed[graph.idToRow(neighbour.getId())][graph.idToColumn(neighbour.getId())] && node.getDist() + 1 >= neighbour.getDist()) {
                continue;
            }
            if (!queue.contains(neighbour) || (neighbour.getDist() > node.getDist() + 1)) {
                neighbour.setDist(node.getDist() + 1);
                updatePriority(neighbour, queue, node.getDist() + 1);
                prev[neighbour.getId() - 1] = node.getId();
            }
        }
    }

    private void updatePriority(Node node, MyPriorityQueue queue, int newDist) {
        if (!queue.decKey(node)) {
            queue.add(node);
        }
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