package Graphs.main;

public class Program {

    public static void main(String[] args) {
        /*
         * The following demonstrates how to create a
         * Graph with String nodes:
         * abc   def
         *   \   /
         *    v v
         *    xyz
         */
        Graph<String> dgs = new Graph<String>();
        dgs.addNode("abc");
        dgs.addNode("def");
        dgs.addEdge("abc", "def");
        dgs.addNode("xyz");
        dgs.addEdge("xyz", "def");
        System.out.println(dgs);
        
        /*
         * The following demonstrates how to create a
         * Graph with Integer nodes, containing partitions partitions:
         * 1 --> 2
         *  \   /        4 --> 5
         *   v v
         *    3
         */
        Graph<Integer> dgi = new Graph<Integer>();
        dgi.addNode(1);
        dgi.addNode(2);
        dgi.addNode(3);
        dgi.addNode(4);
        dgi.addNode(5);
        dgi.addEdge(1, 2);
        dgi.addEdge(2, 3);
        dgi.addEdge(3, 1);
        dgi.addEdge(4, 5);
        System.out.println(dgi);
    }
}
