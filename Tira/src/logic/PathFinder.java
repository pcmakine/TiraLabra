/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Stack;

/**
 *
 * @author pcmakine
 */
//http://www.policyalmanac.org/games/aStarTutorial.htm
public class PathFinder {

    private Graph graph;
    private boolean closed[][];

    public PathFinder(Graph graph) {
        this.graph = graph;
    }

    public Node[] bfs(int startId, int goalId) {
        LinkedList<Integer> openList = new LinkedList();
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
    public Node[] aStar(int startId, int goalId) {
        PriorityQueue<Node> open = new PriorityQueue();
        closed = new boolean[graph.getRows()][graph.getColumns()];  //true if closed, otherwise false
        int[] prev = new int[graph.getNumberofNodes()];
        int prevId = -1;

        Node[][] nodes = graph.getNodes();
        initDistances(nodes);

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
    
        private void initDistances(Node[][] nodes) {
        for (int i = 0; i < nodes.length; i++) {
            for (int j = 0; j < nodes[0].length; j++) {
                Node node = nodes[i][j];
                if (node != null) {
                    node.setDist(Integer.MAX_VALUE);
                }
            }
        }
    }

    private void addForAstar(Node node, PriorityQueue queue, int[] prev, Node target, boolean[][] closed) {
        ArrayList<Node> neighbours = graph.getVerticalAndHorizontalNeighbours(node.getId());
        for (int i = 0; i < neighbours.size(); i++) {
            Node neighbour = neighbours.get(i);
            
            int heuristics = getManhattanHeuristics(neighbour, target);
            neighbour.setHeuristics((int) heuristics);
            if (neighbour.getDist() > node.getDist() + 1) {
                neighbour.setDist(node.getDist() + 1);
                updatePriority(neighbour, queue);
                prev[neighbour.getId() - 1] = node.getId();
            }
        }
    }
    
    private void updatePriority(Node node, PriorityQueue queue){
        queue.remove(node);
        queue.add(node);
    }

//    //Returns the euclidian distance of two nodes
//    private double getHeuristics(Node start, Node target) {
//        double a = Math.abs(start.getX() - target.getX());
//        double b = Math.abs(start.getY() - target.getY());
//
//        double distance = Math.sqrt((a * a) + (b * b));
//
//        return distance / 20;
//    }

    private int getManhattanHeuristics(Node start, Node target) {
        int startrow = (start.getId() - 1) / graph.getColumns();
        int startcolumn = (start.getId() - 1) % graph.getColumns();
        int targetrow = (target.getId() - 1) / graph.getColumns();
        int targetcolumn = (target.getId() - 1) % graph.getColumns();

        int distance = Math.abs(startrow - targetrow) + Math.abs(startcolumn - targetcolumn);

        return distance;
    }
    
    public boolean[][] getClosed(){
        return closed;
    }
}