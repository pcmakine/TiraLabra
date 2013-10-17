/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package logicwithjava;

import java.util.ArrayList;
import logicwithmycollections.Node;

/**
 *
 * @author pcmakine
 */
public class JavaGraph {

    private int ROWS;
    private int COLUMNS;
    private int graphPos;
    private int maxId;
    private boolean[][] nodeArray;
    private Node[][] nodeMatrix;

    public JavaGraph(int size) {
        init(size);
        makeNodeMatrix();
    }

    public JavaGraph(int size, int walls) {
        init(size);
        if (walls < size * size) {
            makeRandomMatrix(walls);
        }
    }

    private void init(int size) {
//        this.nodes = new HashMap();
        this.ROWS = size;
        this.COLUMNS = size;
        graphPos = 5;
        this.maxId = size * size;

    }

    public ArrayList<Node> getVerticalAndHorizontalNeighbours(int node) {
        ArrayList ret = new ArrayList();
        int top = node - COLUMNS;
        int below = node + COLUMNS;
        int left = node - 1;
        int right = node + 1;

        if (top > 0 && nodeMatrix[idToRow(top)][idToColumn(top)] != null) {
            ret.add(nodeMatrix[idToRow(top)][idToColumn(top)]);
        }
        if (idToColumn(node) != 0 && nodeMatrix[idToRow(left)][idToColumn(left)] != null) {
            ret.add(nodeMatrix[idToRow(left)][idToColumn(left)]);
        }

        if (idToColumn(node) != this.COLUMNS - 1 && right <= this.COLUMNS * this.ROWS && nodeMatrix[idToRow(right)][idToColumn(right)] != null) {
            ret.add(nodeMatrix[idToRow(right)][idToColumn(right)]);
        }

        if (below > 0 && below <= this.COLUMNS * this.ROWS && nodeMatrix[idToRow(below)][idToColumn(below)] != null) {
            ret.add(nodeMatrix[idToRow(below)][idToColumn(below)]);
        }

        return ret;
    }

    public int idToColumn(int id) {
        return ((id - 1) % this.COLUMNS);
    }

    public int idToRow(int id) {
        return ((id - 1) / this.COLUMNS);
    }

    public void makeRandomMatrix(int walls) {
        this.nodeMatrix = new Node[ROWS][COLUMNS];
        int id = 1;
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                if (Math.random() > (1.0 * walls / (ROWS * COLUMNS))) {
                    addNode(id);
                } else {
                    nodeMatrix[i][j] = null;
                }
                id++;
            }
        }
    }

    public void makeNodeMatrix() {
        this.nodeMatrix = new Node[ROWS][COLUMNS];
        int id = 1;
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                addNode(id);
                id++;
            }
        }
    }

    public void addNode(int id) {
        int x = (((id - 1) % getColumns() * Node.getWidth()) + graphPos * Node.getWidth());
        int y = (int) Math.ceil(((id - 1) / getColumns()) * Node.getHeight() + graphPos * Node.getHeight());
        nodeMatrix[idToRow(id)][idToColumn(id)] = new Node(id, x, y);
    }

    public void removeNode(Node node) {
        int id = node.getId();
        nodeMatrix[idToRow(id)][idToColumn(id)] = null;
    }

    public void clearAllNodes() {
        nodeMatrix = null;
    }

    public Node getNode(int id) {
        return nodeMatrix[idToRow(id)][idToColumn(id)];
    }

    public Node[][] getNodes() {
        return nodeMatrix;
    }

    public int getNumberofNodes() {
        return (ROWS * COLUMNS);
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

    public int getMaxId() {
        return maxId;
    }
}
