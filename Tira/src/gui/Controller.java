/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.util.ArrayList;
import java.util.HashMap;
import tira.*;

/**
 *
 * @author Pete
 */
public class Controller {

    private Graph graph;
    private PathFinder finder;

    public Controller() {
    }

    public void createGraph(int size) {
        this.graph = new Graph(size);
    }

    public ArrayList decideNodesToRemove(int size, int maxnodesToRemove) {
        ArrayList removed = new ArrayList();
        for (int i = 0; i < maxnodesToRemove; i++) {
            int randomId = 1 + (int) (Math.random() * (size * size));
            removed.add(randomId);
        }
        return removed;
    }

    //for testing, doesn't remove the nodes it gets as parameters
    public ArrayList decideNodesToRemove(int size, int maxnodesToRemove, int origin, int target) {
        ArrayList removed = new ArrayList();
        for (int i = 0; i < maxnodesToRemove; i++) {
            int randomId = 1 + (int) (Math.random() * (size * size));
            if (randomId != origin && randomId != target) {
                removed.add(randomId);
            }

        }
        return removed;
    }

    public Graph makeCustomGraph(int size, ArrayList<Integer> toBeRemoved) {
        graph = new Graph(size);
        graph.makeNodes();
        for (int i = 0; i < toBeRemoved.size(); i++) {
            int id = toBeRemoved.get(i);
            Node removed = graph.getNode(id);
            if (removed != null) {
                graph.removeNode(removed);
            }
        }
        return graph;
    }

    //for tests
    public Graph makeRandomGraph(int size, int maxnodesToRemove, int origin, int target) {
        graph = new Graph(size);

        graph.makeNodes();
        for (int i = 0; i < maxnodesToRemove; i++) {
            int randomId = 1 + (int) (Math.random() * (size * size));
            Node removed = graph.getNode(randomId);
            if (removed != null && randomId != origin && randomId != target) {
                graph.removeNode(removed);
            }
        }
        return graph;
    }

    public Node createNode(int id, int x, int y) {
        Node node = new Node(id, x, y);
        graph.addNode(node);
        return node;
    }

    public void setNeighbours(Node[] neighbours) {
        neighbours[0].setNeighbour(neighbours[1]);
        neighbours[1].setNeighbour(neighbours[0]);
    }

    public HashMap makeNodes(int nodes) {
        return graph.makeNodes();
    }

    public void removeNode(Node node) {
        graph.removeNode(node);

    }

    public Node[] getBfsResult(int startId, int targetId) {
        this.finder = new PathFinder(graph);
        return finder.bfs(startId, targetId);
    }

    public Node[] getAstarResult(int startId, int targetId) {
        this.finder = new PathFinder(graph);
        return finder.aStar(startId, targetId);
    }

    public Graph getGraph() {
        return graph;
    }
}
