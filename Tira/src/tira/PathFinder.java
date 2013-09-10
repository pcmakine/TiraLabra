/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tira;

import java.util.HashMap;

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
                    printSolution();
                }
                add();
            }
            
        }
        

    }
    
    private void add(){
        
    }
    
    private void printSolution(){
        
    }

    public void aStar() {
    }
}
