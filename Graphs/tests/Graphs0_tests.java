package Graphs.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.FileNotFoundException;

import org.junit.Test;

import Graphs.main.Graph;

public class Graphs0_tests extends TestsCore {

    /**
     * Unit test for creating a Graph containing a few Nodes
     */
    @Test
    public void test_addNode() {
        Graph<Integer> g = new Graph<Integer>();
        g.addNode(1);
        g.addNode(2);
        
        // The Graph has two Nodes, in their initial state (0)
        assertEquals(2, g.size());
        assertTrue(g.checkState(0));
        String expected = "1 > \n"
                        + "2 > ";
        assertEquals(expected, g.toString());
    }
    
    /**
     * Unit test for creating a Graph and linking its nodes with directed Edges.
     */
    @Test
    public void test_addEdge() {
        Graph<String> g = new Graph<String>();
        g.addNode("abc");
        g.addNode("def");
        g.addNode("xyz");
        g.addEdge("abc", "xyz");
        g.addEdge("def", "xyz");
        g.addEdge("abc", "def");
        
        // The Graph has three Nodes, in their initial state (0)
        // and linked through edges: abc and def point to xyz,
        // in addition abc points to def. xyz doesn't point to any node.
        assertEquals(3, g.size());
        assertTrue(g.checkState(0));
        String expected = "abc > def xyz\n"
                        + "def > xyz\n"
                        + "xyz > ";
        assertEquals(expected, g.toString());
    }
    
    /**
     * Unit test for reading a Graph from a file and verifying
     * the resulting Graph has the same structure as in the input file.
     * @throws FileNotFoundException
     */
    @Test
    public void test_readGraph() throws FileNotFoundException {
        Graph<String> g = readGraph("/Graphs/data/basic1.txt");
        
        // The Graph contains five Nodes in their initial state (0)
        // and their links described in the initial file.
        assertEquals(5, g.size());
        assertTrue(g.checkState(0));
        assertSameGraph("/Graphs/data/basic1.txt", g);
    }

    /**
     * Unit test for removing an Edge from a given Graph
     * @throws FileNotFoundException
     */
    @Test
    public void test_removeEdge() {
        Graph<Character> g = new Graph<Character>();
        g.addNode('A');
        g.addNode('B');
        g.addEdge('A', 'B');
        
        // The Graph as only two nodes with an Edge between them: A --> B.
        assertEquals(2, g.size());
        String expected = "A > B\n"
                        + "B > ";
        assertEquals(expected, g.toString());
        
        // After removing, the Graph should contain two unlinked nodes.
        g.removeEdge('A', 'B');
        expected = "A > \n"
                 + "B > ";
        assertEquals(expected, g.toString());
    }
    
    /**
     * Unit test for removing a Node from a given Graph.
     * Removing a Node means removing all its outgoing (egress)
     * and incoming (ingress) edges too.
     * @throws FileNotFoundException
     */
    @Test
    public void test_removeNode() throws FileNotFoundException {
        // Read the test graph (three linked nodes) from the file
        Graph<String> g = readGraph("/Graphs/data/basic0.txt");
        assertSameGraph("/Graphs/data/basic0.txt", g);
        
        // Remove one node and verify the structure of updated Graph
        g.removeNode("three");
        String expected = "one > two\n"
                        + "two > ";
        assertEquals(expected, g.toString());

        // Remove a second node and re-verify Graph has one node only
        g.removeNode("two");
        expected = "one > ";
        assertEquals(expected, g.toString());

        // Remove the last node and verify Graph is now empty.
        g.removeNode("one");
        assertEquals("", g.toString());
    }
}
