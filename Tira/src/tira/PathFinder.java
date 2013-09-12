/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tira;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

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
        prev[start.getId()-1] = -1;

        while (!nodeMap.isEmpty()) {
            
            int id = nodeMap.keySet().iterator().next();
            Node node = nodeMap.get(id);

            nodeMap.remove(id);

            if (!handled.containsKey(node.getId())) {
                handled.put(node.getId(), node);

                if (node == goal) {
                    return printSolution(prev, node.getId());
                }
                add(node, node.getNeighbours(), nodeMap, handled, prev);
            }

        }

        return "no solution";
    }

    private void add(Node node, ArrayList<Node> neighbours, LinkedHashMap nodeMap, HashMap handled, int[] prev) {
        for (int i = 0; i < neighbours.size(); i++) {
            Node neighbour = neighbours.get(i);
            int id = neighbour.getId();

            if (!nodeMap.containsKey(id) && !handled.containsKey(id)) {
                nodeMap.put(id, neighbour);
                prev[id-1] = node.getId();
            }
        }
    }

    private String printSolution(int[] prev, int node) {
        String[] reverse = new String[prev.length];
        int nodes = 0;
        String solution = "";
        while (prev[node-1] != -1) {
            reverse[nodes] = "Node: " + node + "\n";
            nodes++;
            node = prev[node-1];
        }
        for (int i = nodes-1; i >= 0; i--) {
            solution = solution + reverse[i];
        }
        return solution;
    }

    public void aStar() {
    }
}