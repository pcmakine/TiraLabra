/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tira;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

/**
 *
 * @author pcmakine
 */
public class Graph {

    private HashMap<Integer, Node> nodes;

    public Graph(HashMap<Integer, List> cities) {
        this.nodes = new HashMap();
        HashMap savedCities = (HashMap) cities.clone();
        populateNodes(cities);
        setNeighbours(savedCities);
    }

    private void populateNodes(HashMap<Integer, List> cities) {
        Iterator it = cities.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pairs = (Map.Entry) it.next();

            int key = (int) pairs.getKey();
            Node node = new Node(key);
            nodes.put(key, node);

            
            it.remove(); // avoids a ConcurrentModificationException
        }
    }

    private void setNeighbours(HashMap<Integer, List> cities) {
        HashMap nodesClone = (HashMap) nodes.clone();
        Iterator it = nodesClone.keySet().iterator();
        while (it.hasNext()) {
            int key = (int) it.next();


            Node node = nodes.get(key);

            List neighbours = cities.get(key);
            for (int i = 0; i < neighbours.size(); i++) {
                node.setNeighbour(nodes.get(neighbours.get(i)));
            }

            it.remove(); // avoids a ConcurrentModificationException
        }
    }

    public Map<Integer, Node> getNodes() {
        return nodes;
    }

    @Override
    public String toString() {
        String print = "";
        
        Iterator it = nodes.keySet().iterator();
        while (it.hasNext()) {
            int key = (int) it.next();
            Node value = nodes.get(key);
            print = print + "Node: " + key + ", Neighbours: ";
            List neighbourList = neighboursToList(value.getNeighbours());
            for (int i = 0; i < neighbourList.size(); i++) {
                Node neighbour = (Node) neighbourList.get(i);
                print = print + neighbour.getId() + ", ";
            }
            
            print = print + "\n";
            it.remove(); // avoids a ConcurrentModificationException
        }

        return print;
    }
    
    private List neighboursToList(Map neighbours){
        ArrayList neighbourList = new ArrayList();
        Iterator it = neighbours.entrySet().iterator();
        
        while(it.hasNext()){
            Map.Entry pairs = (Map.Entry) it.next();
            Node neighbourNode = (Node) pairs.getValue();
            neighbourList.add(neighbourNode);
            it.remove();
        }
        
        return neighbourList;
    }
}
