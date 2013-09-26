/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.util.HashMap;
import tira.*;

/**
 *
 * @author Pete
 */
public class Controller {

    private Graph graph;
    private PathFinder finder;

    public Controller(){
    }
    
    public void createGraph(int size) {
        this.graph = new Graph(size);
    }

    public Node createNode(int id, int x, int y) {
        Node node = new Node(id, x, y);
        graph.addNode(node);
        return node;
    }
    
    public void setNeighbours(Node[] neighbours){
        neighbours[0].setNeighbour(neighbours[1]);
        neighbours[1].setNeighbour(neighbours[0]);
    }
    
    public HashMap makeNodes(int nodes){
        return graph.makeNodes(nodes);
    }
    
    public void removeNodesAllNeighbours(Node node){
        node.removeAllNeighbours();
        
    }

    public Node[] getBfsResult(int startId, int targetId) {
        this.finder = new PathFinder(graph);
        return finder.bfs(startId, targetId);
    }

    public Node[] getAstarResult(int startId, int targetId) {
        this.finder = new PathFinder(graph);
        return finder.aStar(startId, targetId);
    }
    
    public Graph getGraph(){
      return graph;
    }
}
