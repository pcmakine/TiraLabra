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

        LinkedHashMap<Integer, Node> nodeMap = new LinkedHashMap();
        HashMap<Integer, Node> handled = new HashMap();
        Node start = graph.getNode(startId);
        Node goal = graph.getNode(goalId);

        int[] prev = new int[graph.getNumberofNodes()];

        nodeMap.put(start.getId(), start);
        prev[start.getId() - 1] = -1;

        while (!nodeMap.isEmpty()) {

            int id = nodeMap.keySet().iterator().next();
            Node node = nodeMap.get(id);

            nodeMap.remove(id);

            if (!handled.containsKey(node.getId())) {
                handled.put(node.getId(), node);

                if (node == goal) {
                    return getSolution(prev, node.getId(), startId);
                }
                add(node, nodeMap, handled, prev);
            }

        }
        return null;
    }

    
    private void add(Node node, LinkedHashMap nodeMap, HashMap handled, int[] prev) {
        ArrayList<Node> neighbours = node.getNeighbours();
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

    public Node[] aStar(int startId, int goalId) {
        PriorityQueue<Node> open = new PriorityQueue();
        HashMap<Integer, Node> closed = new HashMap();
        int[] prev = new int[graph.getNumberofNodes()];
        int prevId = -1;

        HashMap nodes = graph.getNodes();
        Iterator it = nodes.keySet().iterator();

        while (it.hasNext()) {
            int key = (int) it.next();
            Node node = (Node) nodes.get(key);
            node.setDist(Integer.MAX_VALUE);
        }

        graph.getNode(startId).setDist(0);
        open.add(graph.getNode(startId));

        while (!open.isEmpty()) {
            Node node = open.poll();

            if (!closed.containsKey(node.getId())) {
                closed.put(node.getId(), node);
                if (node == graph.getNode(goalId)) {
                    return getSolution(prev, node.getId(), startId);
                }
                addForAstar(node, open, prev, graph.getNode(goalId), closed);
            }
        }
        return null;
    }

    private void addForAstar(Node node, PriorityQueue queue, int[] prev, Node target, HashMap<Integer, Node> closed) {
        ArrayList<Node> neighbours = node.getNeighbours();

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

    //Returns the euclidian distance of two nodes
    private double getHeuristics(Node start, Node target) {
        double a = Math.abs(start.getX() - target.getX());
        double b = Math.abs(start.getY() - target.getY());

        double distance = Math.sqrt((a * a) + (b * b));

        return distance / 20;
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