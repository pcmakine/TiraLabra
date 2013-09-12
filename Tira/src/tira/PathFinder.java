/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tira;

import java.util.HashMap;
import java.util.ArrayList;

/**
 *
 * @author pcmakine
 */
public class PathFinder {

    Graph graph;

    public PathFinder(Graph graph) {
        this.graph = graph;
    }

    public void bfs(int startId, int goalId) {

        HashMap<Integer, Node> nodeMap = new HashMap();
        HashMap<Integer, Node> handled = new HashMap();
        Node start = graph.getNode(startId);
        Node goal = graph.getNode(goalId);
        
        int[] prev = new int[graph.getNumberofNodes()];
        
        nodeMap.put(start.getId(), start);
        prev[start.getId()] = -1;
        
        while(!nodeMap.isEmpty()){
            
            Node node = nodeMap.remove(0);
            
            if(!handled.containsKey(node.getId())){
                handled.put(node.getId(), node);
                
                if (node == goal) {
                    //return solution
                    printSolution();
                }
                add(node.getNeighbours(), nodeMap, handled, graph);
            }
        }
        //TODO return no solution
    }
    
    private void add(ArrayList<Node> neighbours, HashMap nodeMap, HashMap handled, Graph graph){
        for (int i = 0; i < neighbours.size(); i++) {
            Node neighbour = neighbours.get(i);
            
        }
    }
    
    private void printSolution(){
        
    }

    public void aStar() {
    }
}
