/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tira;

/**
 *
 * @author pcmakine
 */
import java.awt.Point;
import java.util.*;

public class Node implements Comparable<Node> {

    private ArrayList<Node> neighbours;
    private int id;
    private int x;
    private int y;
    private int heuristics;
    private static int WIDTH = 20;
    private static int HEIGHT = 20;

    public Node(int id, int x, int y) {
        neighbours = new ArrayList();
        this.id = id;
        this.x = x;
        this.y = y;
    }
    
    public void removeAllNeighbours(){
        for (int i = 0; i < neighbours.size(); i++) {
            Node neighbour = neighbours.get(i);
            ArrayList<Node> neighbourlist = neighbour.getNeighbours();
            for (int j = 0; j < neighbourlist.size(); j++) {
                if (this == neighbourlist.get(j)) {
                   neighbourlist.remove(this);
                   neighbours.remove(neighbour);
                }
            }
        }
    }

    public int getId() {
        return id;
    }

    public ArrayList getNeighbours() {
        return neighbours;
    }

    public void setNeighbour(Node neighbour) {
        neighbours.add(neighbour);
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public static int getWidth() {
        return WIDTH;
    }

    public static int getHeight() {
        return HEIGHT;
    }

    public void setHeuristics(int heuristics) {
        this.heuristics = heuristics;
    }

    @Override
    public int compareTo(Node node) {
        if (this.heuristics > node.heuristics) {
            return 1;
        }
        if (this.heuristics < node.heuristics) {
            return -1;
        }
        return 0;
    }
}
