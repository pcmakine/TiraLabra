/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tira;

/**
 *
 * @author pcmakine
 */

import java.util.*;

public class Node {
    
    private ArrayList neighbours;
    private int id;
    private int x;
    private int y;

    public Node(int id) {
        neighbours = new ArrayList();
        this.id = id;
    }

    public int getId() {
        return id;
    }
    
    public ArrayList getNeighbours(){
        return neighbours;
    }
    
    public void setNeighbour(Node neighbour){
        neighbours.add(neighbour);
    }
    
    
    
    
}
