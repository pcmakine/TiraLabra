/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import logic.Node;
import logic.Graph;
import logic.PathFinder;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Pete
 */
public class Controller {

    private Graph graph;
    private PathFinder finder;

    public Controller() {
    }

    public Node[][] createGraph(int size) {
        this.graph = new Graph(size);
        return graph.getNodes();
    }

    public void makeRandomGraph(int size, int walls){
        graph = new Graph(size, walls);
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
