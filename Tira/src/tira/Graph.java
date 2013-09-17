/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tira;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.Iterator;

/**
 *
 * @author pcmakine
 */

public class Graph {

    private HashMap<Integer, Node> nodes;
    private int numberofNodes;
    
    public Graph(){
        this.nodes = new HashMap();
    }

    public Graph(HashMap<Integer, List> neighbours, HashMap nodes) {
        this.nodes = new HashMap();
        numberofNodes = nodes.size();
        this.nodes = nodes;
        HashMap savedCities = (HashMap) neighbours.clone();
        setNeighbours(savedCities);
    }

    private void setNeighbours(HashMap<Integer, List> cities) {
        HashMap nodesClone = (HashMap) nodes.clone();
        Iterator it = nodesClone.keySet().iterator();
        while (it.hasNext()) {
            int key = (int) it.next();

            Node node = nodes.get(key);

            List neighbours = cities.get(key);
      
            for (int i = 0; i < neighbours.size(); i++) {
                int neig = (int) neighbours.get(i);
                Node neighbour = nodes.get(neig);
                node.setNeighbour(neighbour);
            }

            it.remove(); // avoids a ConcurrentModificationException
        }
    }
    
    public void addNode(Node node){
        nodes.put(node.getId(), node);
        numberofNodes++;
    }
    
    public void clearAllNodes(){
        nodes.clear();
    }
    
    public Node getNode(int id){
        return nodes.get(id);
    }

    public Map<Integer, Node> getNodes() {
        return nodes;
    }
    
        public int getNumberofNodes() {
        return numberofNodes;
    }

    @Override
    public String toString() {
        String print = "";
        HashMap nodesClone = (HashMap) nodes.clone();
        Iterator it = nodesClone.keySet().iterator();
        while (it.hasNext()) {
            int key = (int) it.next();
            Node value = nodes.get(key);
            print = print + "Node: " + key + ", Neighbours: ";
            List neighbourList = value.getNeighbours();
            int i = 0;      
            while(i < neighbourList.size()){
                Node neighbour = (Node) neighbourList.get(i);
                print = print + neighbour.getId() + ", ";
                i++;
            }    
            
            print = print + "\n";
            it.remove(); // avoids a ConcurrentModificationException
        }

        return print;
    }
    
}
