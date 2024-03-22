package Graphs.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.FileNotFoundException;

import org.junit.Test;

import Graphs.main.Graph;

public class Graphs1_tests extends TestsCore {

    /**____ UNDIRECTED GRAPH ____<br>
     * Unit tests for checking if a Graph is undirected.
     * 
     * A generic Graph is undirected if and only if for any Edge linking 
     * any nodes, A and B, there is an Edge linking back B to A.
     */
    @Test
    public void test_isUGraph() throws FileNotFoundException {
        // Read an undirected Graph from the file "basic1.txt".
        // Verify the output of the isUGraph() method and that
        // the graph internal state and structure is left un-altered.
        Graph<Integer> g1 = readGraph("/Graphs/data/basic1.txt", Integer.class);
        assertTrue(g1.isUGraph());
        assertTrue(g1.checkState(0));
        assertSameGraph("/Graphs/data/basic1.txt", g1);
        
        // Read a directed Graph from the file "basic2.txt"
        // and redo the verifications.
        Graph<String> g2 = readGraph("/Graphs/data/basic2.txt");
        assertFalse(g2.isUGraph());
        assertTrue(g2.checkState(0));
        assertSameGraph("/Graphs/data/basic2.txt", g2);
    }
    
    /**____ CONNECTED GRAPH _____<br>
     * Unit tests for checking if a Graph is connected.
     * 
     * A connected Graph is a Graph in which every pair of vertices 
     * is connected by a path. A Graph that is not connected is said 
     * to be disconnected. A Graph with zero or just one vertex is 
     * connected.
     */
    @Test
    public void test_isConnected() throws FileNotFoundException {
        // Verify an empty Graph is connected
        Graph<Character> g = new Graph<Character>();
        assertTrue(g.isConnected());
        
        // Read a disconnected Graph from a file and verify
        // the output of the isConnected() method.  
        g = readGraph("/Graphs/data/basic3.txt", Character.class);
        assertFalse(g.isConnected());
        
        // Add a few Edges to make it connected and verify again.
        g.addEdge('B', 'E');
        g.addEdge('D', 'C');
        assertTrue(g.isConnected());
    }
    
    /**____ DIRECTED ACYCLIC GRAPH (DAG) ____<br>
     * Unit tests for checking if a Graph is a DAG.
     * 
     * A Directed Acyclic Graph (DAG) is a directed graph that has no cycles.
     * A cycle is a closed loop that starts and ends at the same Node.
     */
    @Test
    public void test_isDAGraph() throws FileNotFoundException {
        // Read a partitioned directed acyclic Graph from the file
        // and verify the output of the isDAGraph() method.
        Graph<String> g = readGraph("/Graphs/data/basic4.txt");
        assertTrue(g.isDAGraph());
        
        // Add an edge to make the graph connected DAG
        // and verify again.
        g.addEdge("a2", "b3");
        assertTrue(g.isDAGraph());
        
        // Add another edge to create a cycle and
        // verify this time isDAGraph this time returns false.
        g.addEdge("b4", "a2");
        assertFalse(g.isDAGraph());
    }
    
    /**____ ADJACENCY MATRIX ____<br>
     * Unit tests for checking the adjacency matrix of a Graph.
     * 
     * In a Graph of N nodes each Node is assigned an index in the range [0, N-1].
     * The adjacency matrix is a matrix of N x N integers. If two Nodes in the Graph
     * A (index i) and B (index j) are linked by an Edge, then
     * adjMatrix[i][j] = 1, otherwise adjMatrix[i][j] = 0.
     */
    @Test
    public void test_adjacencyMatrix() throws FileNotFoundException {
        // Read a complex (partitioned) graph from a file
        // and verify the adjacency matrix returned by getAdjacencyMatrix()
        Graph<String> g = readGraph("/Graphs/data/basic4.txt");
        String[] expArr = {
          ".xxx.....",
          ".........",
          "....x....",
          "....x....",
          ".........",
          "......xx.",
          "........x",
          "........x",
          ".........",
        };
        String[][] adjMatrix = g.getAdjacencyMatrix();
        for(int r = 0; r < adjMatrix.length; r++) {
            String row = "";
            for (int c = 0; c < adjMatrix[r].length; c++) {
                row += adjMatrix[r][c];
            }
            assertEquals(expArr[r], row);
        }
    }
}
