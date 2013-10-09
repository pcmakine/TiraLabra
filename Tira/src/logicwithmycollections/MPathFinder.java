/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package logicwithmycollections;

import logic.*;

import java.util.Stack;
import mycollections.MyArrayList;
import mycollections.MyPriorityQueue;
import mycollections.linkedlist.MyLinkedList;

/**
 *
 * @author pcmakine
 */
//http://www.policyalmanac.org/games/aStarTutorial.htm
public class MPathFinder {

    private MGraph graph;
    private boolean closed[][];

    public MPathFinder(MGraph graph) {
        this.graph = graph;
    }

    public MNode[] bfs(int startId, int goalId) {
        MyLinkedList<Integer> openList = new MyLinkedList();
        closed = new boolean[graph.getRows()][graph.getColumns()];
        boolean[][] open = new boolean[graph.getRows()][graph.getColumns()];
        int[] prev = new int[graph.getNumberofNodes()];
        
        openList.add(startId);
        open[graph.idToRow(startId)][graph.idToColumn(startId)] = true;

        prev[startId - 1] = -1;

        while (!openList.isEmpty()) {
            int id = openList.pollFirst();
            open[graph.idToRow(id)][graph.idToColumn(id)] = false;
            MNode node = graph.getNode(id);
            if (!closed[graph.idToRow(id)][graph.idToColumn(id)]) {
                closed[graph.idToRow(id)][graph.idToColumn(id)] = true;

                if (node == graph.getNode(goalId)) {
                    return getSolution(prev, node.getId(), startId);
                }
                add(node, openList, open, closed, prev);
            }
        }
        return null;
    }

    private void add(MNode node, MyLinkedList openList, boolean[][] open, boolean[][] closed, int[] prev) {
        MyArrayList<MNode> neighbours = graph.getVerticalAndHorizontalNeighbours(node.getId());
        for (int i = 0; i < neighbours.size(); i++) {
            MNode neighbour = neighbours.get(i);
            int id = neighbour.getId();
            if (!open[graph.idToRow(neighbour.getId())][graph.idToColumn(neighbour.getId())] && !closed[graph.idToRow(id)][graph.idToColumn(id)]) {
                openList.add(id);
                open[graph.idToRow(id)][graph.idToColumn(id)] = true;
                prev[id - 1] = node.getId();
            }
        }
    }

    private MNode[] getSolution(int[] prev, int node, int origin) {
        Stack<MNode> resultStack = new Stack();
        MNode[] reverseOrder = new MNode[prev.length];
        MNode[] ordered = new MNode[prev.length];
        int nodes = 0;
        while (node != -1) {
            reverseOrder[nodes] = graph.getNode(node);
            nodes++;

            if (node == origin) {
                break;
            }
            node = prev[node - 1];
        }
        int j = 0;
        for (int i = nodes - 1; i >= 0; i--) {
            ordered[j] = reverseOrder[i];
            j++;
        }
        return ordered;
    }
    public MNode[] aStar(int startId, int goalId) {
        MyPriorityQueue<MNode> open = new MyPriorityQueue();
        closed = new boolean[graph.getRows()][graph.getColumns()];  //true if closed, otherwise false
        int[] prev = new int[graph.getNumberofNodes()];
        int prevId = -1;

        MNode[][] nodes = graph.getNodes();
        initDistances(nodes);

        graph.getNode(startId).setDist(0);
        open.add(graph.getNode(startId));

        while (!open.isEmpty()) {
            MNode node = open.poll();
            int id = node.getId();

            if (!closed[graph.idToRow(id)][graph.idToColumn(id)]) {
                closed[graph.idToRow(id)][graph.idToColumn(id)] = true;
                if (node == graph.getNode(goalId)) {
                    return getSolution(prev, node.getId(), startId);
                }
                addForAstar(node, open, prev, graph.getNode(goalId), closed);
            }
        }
        return null;
    }
    
        private void initDistances(MNode[][] nodes) {
        for (int i = 0; i < nodes.length; i++) {
            for (int j = 0; j < nodes[0].length; j++) {
                MNode node = nodes[i][j];
                if (node != null) {
                    node.setDist(Integer.MAX_VALUE);
                }
            }
        }
    }

    private void addForAstar(MNode node, MyPriorityQueue queue, int[] prev, MNode target, boolean[][] closed) {
        MyArrayList<MNode> neighbours = graph.getVerticalAndHorizontalNeighbours(node.getId());
        for (int i = 0; i < neighbours.size(); i++) {
            MNode neighbour = neighbours.get(i);
            
            int heuristics = getManhattanHeuristics(neighbour, target);
            neighbour.setHeuristics((int) heuristics);
            if (neighbour.getDist() > node.getDist() + 1) {
                int oldDist = neighbour.getDist();
                neighbour.setDist(node.getDist() + 1);
                updatePriority(neighbour, queue, oldDist, node.getDist()+1);
                prev[neighbour.getId() - 1] = node.getId();
            }
        }
    }
    
    private void updatePriority(MNode node, MyPriorityQueue queue, int oldDist, int newDist){
        if (oldDist < newDist) {
            if (!queue.incKey(node)) {
                queue.add(node);
            }
        }else{
            if(!queue.decKey(node)){
                queue.add(node);
            }
        }
    }

//    //Returns the euclidian distance of two nodes
//    private double getHeuristics(Node start, Node target) {
//        double a = Math.abs(start.getX() - target.getX());
//        double b = Math.abs(start.getY() - target.getY());
//
//        double distance = Math.sqrt((a * a) + (b * b));
//
//        return distance / 20;
//    }

    private int getManhattanHeuristics(MNode start, MNode target) {
        int startrow = (start.getId() - 1) / graph.getColumns();
        int startcolumn = (start.getId() - 1) % graph.getColumns();
        int targetrow = (target.getId() - 1) / graph.getColumns();
        int targetcolumn = (target.getId() - 1) % graph.getColumns();

        int distance = Math.abs(startrow - targetrow) + Math.abs(startcolumn - targetcolumn);

        return distance;
    }
    
    public boolean[][] getClosed(){
        return closed;
    }
}