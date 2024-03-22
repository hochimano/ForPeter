package Graphs.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.FileNotFoundException;
import java.util.TreeMap;

import org.junit.Test;

import Graphs.main.Graph;

public class Graphs3_tests extends TestsCore {

    //____ PARTITIONS COUNT ____
    /**In Graph theory, a graph G is a tuple (V, E) where V is the set of vertices and
     * E is a set of Edges connecting vertices from V.<br>
     * A partition in the graph G is a graph g = (v, e) where v &#x2286; V, e &#x2286; E,
     * every edge in e is connecting vertices from v and and every edge in {E \ e} 
     * is connecting vertices from {V \ v}.<br>
     * In other words, there is no edge connecting a vertex in a partition with a vertex from 
     * outside that partition, in either direction.<br>
     * Code the following method in the Graph class, counting the number of 
     * partitions in the Graph:
     * <pre>int countPartitions() {...}</pre>
     * For instance:<br>
     * - an empty graph has 0 partitions,<br>
     * - a weakly connected graph has 1 partition,<br>
     * - the graph in the example below has 2 partitions.
     * <pre>
     *A > B C
     *B > 
     *C >
     *D > E
     *E > D</pre>
     * @throws FileNotFoundException 
     */
    @Test    
    public void test_partitionsCount() throws FileNotFoundException {
        Graph<String> g = new Graph<String>();
        assertEquals(0, g.countPartitions());
        g = readGraph("/Graphs/data/basic0.txt");
        assertEquals(1, g.countPartitions());
        g = readGraph("/Graphs/data/basic1.txt");
        assertEquals(2, g.countPartitions());
        g = readGraph("/Graphs/data/medium2.txt");
        assertEquals(3, g.countPartitions());
    }

    
    //____ DIJKSTRA DISTANCES ____
    /**In a Graph, the Dijkstra distances algorithm is finding the <b>shortest</b> distances
     * between a given (source) node and all the other nodes that can be reached from the source.
     * There are many flavors of this algorithm, in its simplest form, consider the distance
     * between two nodes u and v as the number of edges that can be followed starting from
     * u in order to reach v.
     * <p>
     * Code the following method in the Graph class returning the Dijkstra distances
     * from the node given as parameter to all the other nodes in the Graph:
     * <pre>TreeMap&lt;T, Integer> dijkstra(T source) {...}</pre>
     * The method returns a TreeMap where the key is each Node in the Graph (given by its data)
     * and the value is the Dijkstra distance from the <i>source</i> Node to that node.
     * <p>
     * Special cases:<br>
     * - the Dijkstra distance between a node and itself is 0.<br>
     * - if a node v cannot be reached from the source node, its dijkstra distance is -1.
     * <p>
     * For instance, given the following Graph:
     * <pre>
     * A > B C
     * B > C
     * C > A
     * D > </pre>
     * Should return the following map:
     * <pre>{A=2, B=0, C=1, D=-1}</pre>
     * @throws FileNotFoundException 
     */
    @Test
    public void test_dijkstra() throws FileNotFoundException {
        Graph<String> g = readGraph("/Graphs/data/basic2.txt");
        TreeMap<String, Integer> dm = g.dijkstra("X");
        assertEquals("{A=1, B=1, C=1, X=0}", dm.toString());
        g = readGraph("/Graphs/data/medium2.txt");
        dm = g.dijkstra("E");
        assertEquals("{A=-1, B=-1, C=2, D=3, E=0, F=1, G=1, X=-1, Y=-1, Z=-1}", dm.toString());
        g = readGraph("/Graphs/data/complex1.txt");
        dm = g.dijkstra("A");
        assertEquals("{A=0, B=1, C=2, D=2, E=3, F=2, G=1}", dm.toString());
    }
    
    @Test
    public void test_eulerianCircuit() throws FileNotFoundException{
       Graph<String> g = readGraph("/Graphs/data/BasicEulerian.txt");
       assertTrue(g.isEulerianCircuit());
    }
}
