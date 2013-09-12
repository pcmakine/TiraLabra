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

//TODO less copy paste. Going through hashmap should be made in one method
public class Graph {

    private HashMap<Integer, Node> nodes;
    private int numberofNodes;

    public Graph(HashMap<Integer, List> cities) {
        this.nodes = new HashMap();
        numberofNodes = 0;
        HashMap savedCities = (HashMap) cities.clone();
        populateNodes(cities);
        setNeighbours(savedCities);
    }

    private void populateNodes(HashMap<Integer, List> cities) {
        HashMap citiesClone = (HashMap) cities.clone();
        Iterator it = citiesClone.entrySet().iterator();
        
        while (it.hasNext()) {
            Map.Entry pairs = (Map.Entry) it.next();

            int key = (int) pairs.getKey();
            Node node = new Node(key);
            nodes.put(key, node);
            numberofNodes++;

            
            it.remove(); // avoids a ConcurrentModificationException
        }
    }

    public int getNumberofNodes() {
        return numberofNodes;
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
    
    public Node getNode(int id){
        return nodes.get(id);
    }

    public Map<Integer, Node> getNodes() {
        return nodes;
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
