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
    private int ROWS;
    private int COLUMNS;
    private int graphPos;
    private int maxId;

    public Graph(int size) {
        this.nodes = new HashMap();
        this.ROWS = size;
        this.COLUMNS = size;
        graphPos = 5;
        this.maxId = size*size;
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

    public void setAllNeighbours() {
        for (int i = 0; i < nodes.size(); i++) {
            Node node = nodes.get(i + 1);
            for (int j = 0; j < nodes.size(); j++) {
                Node possibleNeighbour = nodes.get(j + 1);
                if (i != j && isVerticalOrHorizontalNeighbour(node, possibleNeighbour)) {
                    if (!node.getNeighbours().contains(possibleNeighbour)) {
                        node.setNeighbour(possibleNeighbour);
                    }
                }
            }
        }
    }

    public boolean isVerticalOrHorizontalNeighbour(Node node, Node possibleNeighbour) {
        int nodeX = node.getX();
        int nodeY = node.getY();
        int neibX = possibleNeighbour.getX();
        int neibY = possibleNeighbour.getY();

        //neighbour on top of (and down from) node
        if (Math.abs(nodeY - neibY) == Node.getHeight() && nodeX == neibX) {
            return true;
        }
        if (Math.abs(nodeX - neibX) == Node.getWidth() && nodeY == neibY) {
            return true;
        }

        return false;
    }

    private boolean isNeighbour(Node node, Node possibleNeighbour) {
        int nodeX = node.getX();
        int nodeY = node.getY();
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if (possibleNeighbour.getX() == (nodeX + j * Node.getWidth()) && possibleNeighbour.getY() == (nodeY + i * Node.getHeight())) {
                    return true;
                }
            }
        }
        return false;
    }

    public HashMap makeNodes() {
        nodes = new HashMap();
        for (int i = 0; i < (this.COLUMNS * this.ROWS); i++) {
            int id = i + 1;
            int x = (((id - 1) % getColumns() * Node.getWidth()) + graphPos * Node.getWidth());
            int y = (int) Math.ceil(((id - 1) / getColumns()) * Node.getHeight() + graphPos * Node.getHeight());
            nodes.put(id, new Node(id, x, y));
            numberofNodes++;
        }
        setAllNeighbours();
        return nodes;
    }

    public void removeNode(Node node) {
        node.removeAllNeighbours();
        nodes.remove(node.getId());
    }

    public void addNode(Node node) {
        nodes.put(node.getId(), node);
        numberofNodes++;
    }

    public void clearAllNodes() {
        nodes.clear();
    }

    public Node getNode(int id) {
        return nodes.get(id);
    }

    public HashMap<Integer, Node> getNodes() {
        return nodes;
    }

    public int getNumberofNodes() {
        return numberofNodes;
    }

    public int getRows() {
        return ROWS;
    }

    public int getColumns() {
        return COLUMNS;
    }

    public int getPos() {
        return graphPos;
    }
    
    public int getMaxId(){
        return maxId;
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
            while (i < neighbourList.size()) {
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
