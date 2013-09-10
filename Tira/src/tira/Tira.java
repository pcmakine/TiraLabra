/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tira;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author pcmakine
 */
public class Tira {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Graph graph = makeTestGraph();

        System.out.println(graph);
    }

    private static Graph makeTestGraph() {
        Integer key;
        List neighbours;
        HashMap<Integer, List> cities = new HashMap();
        int[][] neighboursarray = new int[][]{{2, 3}, {1, 4}, {1, 4, 6}, {2, 3, 5, 6}, {4}, {4, 3}};
        int maxNode = neighboursarray.length;

        for (int i = 0; i < maxNode; i++) {
            neighbours = putNeighboursinList(neighboursarray[i]);
            cities.put(i + 1, neighbours);
        }

        Graph graph = new Graph(cities);
        return graph;
    }

    private static List putNeighboursinList(int[] neighboursarray) {
        ArrayList neighbours = new ArrayList();

        for (int i = 0; i < neighboursarray.length; i++) {
            neighbours.add(neighboursarray[i]);
        }

        return neighbours;

    }
}
