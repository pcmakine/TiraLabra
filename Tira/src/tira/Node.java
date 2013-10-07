/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tira;

/**
 *
 * @author pcmakine
 */

public class Node implements Comparable<Node> {

    private int id;
    private int x;
    private int y;
    private int heuristics;
    private int dist;
    private static int WIDTH = 20;
    private static int HEIGHT = 20;

    public Node(int id, int x, int y) {
        this.id = id;
        this.x = x;
        this.y = y;
    }
    
    public Node(int id){
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }
    public int getHeuristics(){
        return heuristics;
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

    public void setDist(int dist) {
        this.dist = dist;
    }

    public int getDist() {
        return this.dist;
    }

    @Override
    public int compareTo(Node node) {
        if (this.heuristics + this.dist > node.heuristics + node.dist) {
            return 1;
        }
        if (this.heuristics + this.dist < node.heuristics + node.dist) {
            return -1;
        }
        if((this.heuristics + this.dist) == (node.heuristics + node.dist) && this.dist > node.dist){
            return -1;
        }
        if((this.heuristics + this.dist) == (node.heuristics + node.dist) && this.dist < node.dist){
            return 1;
        }
        return 0;
    }
}
