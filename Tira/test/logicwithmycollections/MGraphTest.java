/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package logicwithmycollections;

import logicwithjava.Node;
import logicwithjava.Graph;
import java.util.Map;
import junit.framework.TestCase;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;
import mycollections.MyArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

/**
 *
 * @author pcmakine
 */
public class MGraphTest {

    private MGraph smallGraph;

    public MGraphTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        smallGraph = new MGraph(3);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void idToColumnReturnsCorrectColumn() {
        int firstColumn = smallGraph.idToColumn(1);
        int secondColumn = smallGraph.idToColumn(5);
        int thirdColumn = smallGraph.idToColumn(9);
        int thirdColumnagain = smallGraph.idToColumn(3);
        assertEquals(firstColumn, 0);
        assertEquals(secondColumn, 1);
        assertEquals(thirdColumn, 2);
        assertEquals(thirdColumnagain, 2);
    }

    @Test
    public void idToRowReturnsCorrectColumn() {
        int firstRow = smallGraph.idToRow(1);
        int secondRow = smallGraph.idToColumn(5);
        int thirdRow = smallGraph.idToRow(9);
        int thirdRowagain = smallGraph.idToRow(7);
        assertEquals(firstRow, 0);
        assertEquals(secondRow, 1);
        assertEquals(thirdRow, 2);
        assertEquals(thirdRowagain, 2);
    }

    @Test
    public void getVerticalAndHorizontalNeighboursWorks() {
        int[] firstNeighbours = nodeListToIdArray(smallGraph.getVerticalAndHorizontalNeighbours(1));
        int[] firstExpected = {2, 4};
        assertArrayEquals(firstExpected, firstNeighbours);
    }

    private int[] nodeListToIdArray(MyArrayList<MNode> list) {
        int[] array = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            array[i] = list.get(i).getId();
        }
        return array;
    }
}
