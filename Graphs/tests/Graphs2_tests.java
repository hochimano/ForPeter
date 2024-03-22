package Graphs.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.FileNotFoundException;
import java.util.TreeMap;
import java.util.TreeSet;

import org.junit.Test;

import Graphs.main.Graph;

public class Graphs2_tests extends TestsCore {
    
    //____ OUT-DEGREES ____
    /** The out-degree of a Node is the number of Edges leading out from it.<br>
     * Code the following method in the Graph class:
     * <pre>TreeMap&lt;Integer, TreeSet&lt;T>> getOutDegrees() {...}</pre>
     * It returns a TreeMap associating each out-degree count (key) to the 
     * set of Nodes having that out-degree (value).
     */
    @Test
    public void test_outDegrees() throws FileNotFoundException {
        Graph<String> g = readGraph("/Graphs/data/medium1.txt");
        TreeMap<Integer, TreeSet<String>> mapOutDeg = g.getOutDegrees();
        assertEquals("{0=[E], 1=[F], 2=[B, C], 3=[A, D]}", mapOutDeg.toString());
    }
    
    //____ IN-DEGREES ____
    /** The in-degree of a Node is the number of Edges leading into it.<br>
     * Code the following method in the Graph class:
     * <pre>TreeMap&lt;Integer, TreeSet&lt;T>> getInDegrees() {...}</pre>
     * It returns a TreeMap associating each in-degree count (key) to the 
     * set of Nodes having that in-degree (value).
     */
    @Test
    public void test_inDegrees() throws FileNotFoundException {
        Graph<String> g = readGraph("/Graphs/data/medium1.txt");
        TreeMap<Integer, TreeSet<String>> mapInDeg = g.getInDegrees();
        assertEquals("{0=[A], 1=[D], 2=[B, F], 3=[C, E]}", mapInDeg.toString());
    }
    
    //____ TOPOLOGICAL SORT ____
    /**A topological sort or topological ordering of a Directed Acyclic Graph 
     * is a linear ordering of its vertices such that for every directed edge uv 
     * from vertex u to vertex v, u comes before v in the ordering.<br>
     * Code the following method in the Graph class:
     * <pre>TreeMap&lt;Integer, TreeSet&lt;T>> topoSort() {...}</pre>
     * It returns a TreeMap associating each position in the topological sort (key)
     * with the set of Nodes at that position (value). If the Graph is not DAG, the method 
     * returns null.
     * Notes: Multiple nodes may have the same position in the sort.
     * For instance:<pre>
     *A > B C
     *B > 
     *C >
     * </pre>should return {A=0, B=1, C=1}
     */
    @Test
    public void test_topologicalSort() throws FileNotFoundException {
        Graph<Integer> g = readGraph("/Graphs/data/basic1.txt", Integer.class);
        assertFalse(g.isDAGraph());
        assertEquals(null, g.topoSort());
        
        Graph<String> g1 = readGraph("/Graphs/data/medium1.txt");
        assertTrue(g1.isDAGraph());
        TreeMap<Integer, TreeSet<String>> mapTopoSort1 = g1.topoSort();
        assertEquals("{0=[A], 1=[D], 2=[B], 3=[C], 4=[F], 5=[E]}", mapTopoSort1.toString());
        
        Graph<String> g2= readGraph("/Graphs/data/medium2.txt");
        assertTrue(g2.isDAGraph());
        TreeMap<Integer, TreeSet<String>> mapTopoSort2 = g2.topoSort();
        assertEquals("{0=[A, E, X, Z], 1=[B, F, G, Y], 2=[C], 3=[D]}", mapTopoSort2.toString());
    }
}
