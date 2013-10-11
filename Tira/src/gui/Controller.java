/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import logicwithmycollections.MGraph;
import logicwithmycollections.Node;
import logicwithmycollections.MPathFinder;

/**
 *
 * @author Pete
 */
public class Controller {

    private MGraph graph;
    private MPathFinder finder;

    public Controller() {
    }

    public Node[][] createGraph(int size) {
        this.graph = new MGraph(size);
        return graph.getNodes();
    }

    public void makeRandomGraph(int size, int walls){
        graph = new MGraph(size, walls);
    }

    public void removeNode(Node node) {
        graph.removeNode(node);

    }

    public Node[] getBfsResult(int startId, int targetId) {
        this.finder = new MPathFinder(graph);
        return finder.bfs(startId, targetId);
    }

    public Node[] getAstarResult(int startId, int targetId) {
        this.finder = new MPathFinder(graph);
        return finder.aStar(startId, targetId);
    }

    public MGraph getGraph() {
        return graph;
    }
}
