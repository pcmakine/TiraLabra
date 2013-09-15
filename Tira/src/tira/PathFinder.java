/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tira;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.PriorityQueue;

/**
 *
 * @author pcmakine
 */
public class PathFinder {

    Graph graph;

    public PathFinder(Graph graph) {
        this.graph = graph;
    }

    public String bfs(int startId, int goalId) {

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
                    return printSolution(prev, node.getId());
                }
                add(node, nodeMap, handled, prev);
            }

        }

        return "no solution";
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

    private String printSolution(int[] prev, int node) {
        String[] reverse = new String[prev.length];
        int nodes = 0;
        String solution = "";
        while (node != -1) {
            reverse[nodes] = "Node: " + node + "\n";
            nodes++;
            node = prev[node - 1];
        }
        for (int i = nodes - 1; i >= 0; i--) {
            solution = solution + reverse[i];
        }
        return solution;
    }

    public String aStar(int startId, int goalId) {
        PriorityQueue<Node> queue = new PriorityQueue();
        HashMap<Integer, Node> handled = new HashMap();
        int[] prev = new int[graph.getNumberofNodes()];

        queue.add(graph.getNode(startId));

        while (!queue.isEmpty()) {
            Node node = queue.poll();

            if (!handled.containsKey(node.getId())) {
                handled.put(node.getId(), node);
                if (node == graph.getNode(goalId)) {
                    return printSolution(prev, node.getId());
                }
                //add(node, node.getNeighbours(), nodeMap, handled, prev);
            }
        }
        return "no solution";
    }

    private void addForAstar(Node node, PriorityQueue queue, int[] prev, Node target) {
        ArrayList<Node> neighbours = node.getNeighbours();
        
        for (int i = 0; i < neighbours.size(); i++) {
            Node neighbour = neighbours.get(i);
            double heuristics = getHeuristics(neighbour, target);
            queue.add(neighbour);
        }

    }

    //Returns the euclidian distance of two nodes
    private double getHeuristics(Node origin, Node target) {
        double a = Math.abs(origin.getX() - target.getX());
        double b = Math.abs(origin.getY() - target.getY());

        double distance = Math.sqrt((a * a) + (b * b));

        return distance;
    }
}