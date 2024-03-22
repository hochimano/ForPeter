package Graphs.main;
import java.security.spec.ECFieldF2m;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Map.Entry;


//import org.omg.CosNaming._BindingIteratorImplBase;
//import org.omg.DynamicAny._DynEnumStub;

/**
 * Class definition for a generic (Directed) Graph.
 * The Graph contains a collection of Nodes, each Node contains
 * a collection of references (edges) to their neighboring Nodes.
 * @param <T> - reference type of Nodes contained in the Graph.
 * The type T is expected to implement the Comparable interface, 
 * such that Nodes can be compared to each other.<br>
 * E.g.:<pre>Graph&lt;String&gt; g = new Graph&ltString&gt();</pre>
 * @see Node
 */
public class Graph<T extends Comparable<T>> {
    volatile boolean Circuit = false;
    volatile Queue<Node<T>> runMe;

    /**
     * Private Map keying each Node in the Graph by the hashCode of its data
     * E.g: Given <pre>Node<String> n = new Node<String>("abc");</pre> added to the graph,
     * the _nodes map contains a Map.Entry with
     * <pre>key="abc".hashCode()<br>value=n<pre>
     * @see java.lang.Object#hashCode()
     */
    private Map<Integer, Node<T>> _nodes;
    
    /**
     * Constructs a new Graph as an empty container fit for Nodes of the type T.
     * @see Graph
     * @see Node
     */
    public Graph() {
        _nodes = new TreeMap<Integer, Node<T>>();
        runMe = new LinkedList<Node<T>>();
    }
    
    /**
     * Gets the size of this Graph. The size of the Graph is equal to the number
     * of Nodes it contains.
     * @return number of Nodes in this Graph.
     */
    public int size() {
        return _nodes.size();
    }
    
    /**
     * Checks if the state of all the Nodes in the Graph matches a given value.
     * @param state - the value to check against all Nodes in the Graph.
     * @return true if all the Nodes in the Graph have a state matching the
     * given value, false otherwise.
     * @see Node#getState()
     */
    public boolean checkState(int state) {
        for (Node<?> n : _nodes.values()) {
            if (state != n.getState()) {
                return false;
            }
        }
        
        return true;
    }
    
    /**
     * Adds a new Node to the Graph containing the <i>data</i>. The method 
     * throws if the Graph already contains a Node with data having the same
     * hashCode().
     * @param data - the data reference (of type T) contained in the new Node.
     * @throws RuntimeException if the Graph already contains a Node for the
     * given data.
     * @see java.lang.Object#hashCode()
     */
    public void addNode(T data) {
        int nodeHash = data.hashCode();
        if (_nodes.containsKey(nodeHash)) {
            throw new RuntimeException("Ambiguous graph!");
        }
        
        _nodes.put(nodeHash, new Node<T>(data));
    }
    
    /**
     * Adds a new directed Edge to the Graph, linking the Nodes containing
     * <i>from</i> and <i>to</i> data. It is expected the two Nodes exist
     * otherwise the method throws an exception.
     * @param from - Node where the Edge is starting.
     * @param to - Node where the Edge is ending.
     * @throws RuntimeException if either of the two Nodes are not present in the Graph.
     * @see Node
     * @see Graph#removeEdge(Comparable, Comparable)
     */
    public void addEdge(T from, T to) {
        Node<T> fromNode = _nodes.get(from.hashCode());
        Node<T> toNode = _nodes.get(to.hashCode());
        if (fromNode == null || toNode == null) {
            throw new RuntimeException("Node(s) not in the graph!");
        }
        
        fromNode.addEdge(toNode);
    }
    
    /**
     * Removes an existent directed Edge from the Graph, if one exists. 
     * The Edge to be removed is linking the nodes containing the <i>from</i> 
     * and <i>to</i> data references. The two Nodes must exist, otherwise the 
     * method throws an exception.
     * @param from - Node at the starting point of the Edge.
     * @param to - Node at the ending point of the Edge.
     * @throws RuntimeException if either of the two Nodes are not present in the Graph.
     * @see Node
     * @see Graph#addEdge(Comparable, Comparable)
     */
    public void removeEdge(T from, T to) {
        // TODO: Implement this method according to
        // TODO: the specification in javadocs
        Node<T> fromNode = _nodes.get(from.hashCode());
        Node<T> toNode = _nodes.get(to.hashCode());
        if(fromNode == null || toNode == null){
            throw new IllegalArgumentException();
        }
        fromNode.removeEdge(toNode); 
    }
    
    /**
     * Removes a Node from the Graph if one exists, along with all
     * its outgoing (egress) and incoming (ingress) edges. If there
     * is no Node hosting the <i>data</i> reference the method does
     * nothing.
     * @param data - Node to be removed from the Graph.
     */
    public void removeNode(T data) {
        // TODO: Implement this method according to
        // TODO: the specification in javadocs
        Node<T> removeMe = _nodes.get(data.hashCode());
        if(removeMe == null){
            throw new RuntimeException("L BOZO");
        }
        for(Map.Entry<Integer, Node<T>> m: _nodes.entrySet()){
            if(m.getValue().hasEdge(removeMe)){
                m.getValue().removeEdge(removeMe);
            }
        }
        removeMe.removeAllEdges();
        _nodes.remove(data.hashCode());
        
    }


    /**
     * Checks if the Graph is undirected.
     * @return true if Graph is undirected, false otherwise.
     */
    public boolean isUGraph() {
        return _nodes.values().iterator().next().isUGraph(null);
        // TODO: Implement this method according to
        // TODO: the specification in javadocs
    }

    /**
     * Checks is the Graph is connected.
     * @return true if the Graph is connected, false otherwise.
     */
    public boolean isConnected() {
        // TODO: Implement this method according to
        // TODO: the specification in javadocs
        boolean answer = true;
        for(Node<T> n: _nodes.values()){
            for(Node<T> nu: _nodes.values()){
                nu.reset();
            }

            n.isConnected();

            for(Node<T> node: _nodes.values()){
                if(node.getState() != 1){
                    answer = false;
                } 
            }
        


        }
        return answer;
           


        
        

        
    }


    /**
     * Checks if the Graph is Directed Acyclic graph.
     * @return true if Graph is Directed Acyclic, false otherwise.
     */
    public boolean isDAGraph() {
        boolean answer = false;

        for(Node<T> n: _nodes.values()){
            answer = n.loops(new HashSet<Integer>());
            if(answer){
                return !answer;
            }
        }
        return !answer;
    }

    /**
     * Generates the adjacency matrix for this Graph.
     * @return the adjacency matrix.
     */
    public String[][] getAdjacencyMatrix() {
        TreeSet<String> names = new TreeSet<String>();
        String[][] array = new String[_nodes.size() + 1][_nodes.size() + 1];
        for(Node<T> n : _nodes.values()){
            names.add(n.getName());
        }

        for(int x = 0; x < array.length; x++){
            for(int y = 0; y < array[x].length; y++){

            }
        }
        return null;
    }

    /**
     * Gives a multi-line String representation of this Graph. Each line in the returned
     * string represent a Node in the graph, followed by its outgoing (egress) Edges.
     * E.g: If the graph contains 3 nodes, A, B an C, where A and B point to each other and
     * both of them point to C, the value returned by toString() will be as follows:
     * <pre>
     * A > B C
     * B > A C
     * C > 
     * </pre>
     * <u>Note:</u> Each line is a space-separated sequence of token. A Node with no
     * outgoing (egress) edges, like C in the example above still has a line where 
     * the ' > ' token is surrounded by the space characters.
     * @return multi-line String reflecting the content and structure of this Graph.
     */
    @Override
    public String toString() {
        String output = "";
        boolean first = true;
        for(Node<?> n : _nodes.values()) {
            if (!first) {
                output += "\n";
            }
            output += n.toString();
            first = false;
        }
        
        return output;
    }

    public TreeMap<Integer, TreeSet<String>> getOutDegrees() {
        TreeMap<Integer, TreeSet<String>> map = new TreeMap<Integer, TreeSet<String>>();
        Queue<Node<T>> q = new LinkedList<Node<T>>();
        q.addAll(_nodes.values());
        while(!q.isEmpty()){
            if(map.get(q.peek().getEdges().size()) == null){
                TreeSet<String> s = new TreeSet<String>();
                s.add(q.peek().getName());
                map.put(q.remove().getEdges().size(), s);
            } else map.get(q.peek().getEdges().size()).add(q.remove().getName());
        }
        return map;
    }

    public TreeMap<Integer, TreeSet<String>> getInDegrees() {
        TreeMap<Integer, TreeSet<String>> map = new TreeMap<Integer, TreeSet<String>>();
        Queue<Node<T>> q = new LinkedList<Node<T>>();
        HashSet<Node<T>> s = new HashSet<Node<T>>();
        s.addAll(_nodes.values());
        q.addAll(_nodes.values());
        while(!q.isEmpty()){
            Node<T> current = q.remove();
            int i = 0;
            for(Node<T> n : s){
                if(n.getEdges().containsValue(current)){
                    i = i + 1;
                }
            }
            if(map.get(i) == null){
                TreeSet<String> set = new TreeSet<String>();
                set.add(current.getName());
                map.put(i, set);
            } else map.get(i).add(current.getName());
        }
            
        return map;
    }

    public TreeMap<Integer, TreeSet<String>> topoSort() {
        TreeMap<Integer, TreeSet<String>> map = new TreeMap<Integer, TreeSet<String>>();
        if(!isDAGraph()){
            return null;
        }
        Queue<Node<T>> q = new LinkedList<Node<T>>();
        q.addAll(_nodes.values());
        
        while(!q.isEmpty()){
            if(map.get(q.peek().getState()) == null){
            }
            q.addAll(q.remove().topoSort());
        }

        for(Node<T> node: _nodes.values()){
            if(map.get(node.getState()) == null){
                TreeSet<String> s = new TreeSet<String>();
                s.add(node.getName());
                map.put((Integer) node.getState(), s);
            }
            map.get(node.getState()).add(node.getName());
        }
        System.out.println(map);
        return map;
    }

    public Object countPartitions() {
        HashSet<Node<T>> s = new HashSet<Node<T>>();
        s.addAll(_nodes.values());
        int i = 1;
        if(s.size() == 0){
            return 0;
        }
        for(Node<T> node: s){
            int temp = node.setPartition(i);
            if(temp > 0){
                System.out.println(temp);
                node.setPartition(node.setPartition(temp));
            }

            i = node.getState() + 1;
        }
        HashSet<Integer> set = new HashSet<Integer>();
        for(Node<T> node: s){
            set.add(node.getState());
        }
        return set.size();
    }

    public TreeMap<String, Integer> dijkstra(String string) {
        TreeMap<String, Integer> map = new TreeMap<String, Integer>();

        _nodes.get(string.hashCode()).setDistances(0);
        for(Node<T> n: _nodes.values()){
            map.put(n.getName(), n.getDistance(_nodes.get(string.hashCode())));
        }
        return map;
    }

    
    public boolean isEulerianCircuit(){
        Queue<eulerianSet> q = new LinkedList<eulerianSet>();
        HashSet<eulerianSet> s = new HashSet<eulerianSet>();
        
        for(Node<T> n : _nodes.values()){
            s.add(new eulerianSet(n));
        }
        q.addAll(s);
        while(!q.isEmpty()){
            eulerianSet temp = q.remove();
            for(Node<T> nu : temp.data.circuitHelper(temp.trace)){
                eulerianSet temp2 = new eulerianSet(nu, temp.trace);
                temp.addBranch(temp2);
                q.add(temp2);
            }
        }
        for(eulerianSet set : s){
            System.out.println(set);
        }
        return true;

    }
        
    


    public class eulerianSet{
        Node<T> data;
        HashSet<eulerianSet> branches;
        Set<Node<T>> trace;

        public eulerianSet(Node<T> d){
            data = d;
            branches = new HashSet<eulerianSet>();
            trace = new HashSet<Node<T>>();
            trace.add(data);
        }

        public eulerianSet(Node<T> d, Set<Node<T>> s){
            data = d;
            branches = new HashSet<eulerianSet>();
            trace = new HashSet<Node<T>>();
            trace.addAll(s);
            trace.add(d);
        }

        public void addBranch(eulerianSet next){
            branches.add(next);

        }


        public Node<T> getData(){
            return data;
        }

        public Set<eulerianSet> getBranches(){
            return branches;
        }

        public Set<Node<T>> getTrace(){
            return trace;
        }

        public String toString(){
            String s = data.getName() + "> ";
            for(eulerianSet set : branches){
                s += set.toString();
                
            }
            return s;
              
        }
    }
}





