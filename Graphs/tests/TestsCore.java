package Graphs.tests;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;

import Graphs.main.Graph;

public class TestsCore {
    
    private Scanner getScanner(String graphFile) throws FileNotFoundException {
        URL url = this.getClass().getResource(graphFile);
        File file = new File(url.getFile());
        String filePath = file.getAbsolutePath();
        try {
            filePath = java.net.URLDecoder.decode(filePath, StandardCharsets.UTF_8.name());
        } catch (Exception e) {
        }
        System.out.println(file.getAbsolutePath());
        return new Scanner(new File(filePath));
    }
    
    public <T extends Comparable<T>> T parseT(String s, Class<T> realType) {
        if (realType == Integer.class) {
            return realType.cast(Integer.parseInt(s));
        } else if (realType == String.class) {
            return realType.cast(s);
        } else if (realType == Double.class) {
            return realType.cast(Double.parseDouble(s));
        } else if (realType == Character.class) {
            if (s.length() != 1) {
                throw new RuntimeException("Invalid format in graph parsing!");
            }
            return realType.cast(s.charAt(0));
        } else {
            throw new RuntimeException("Unsupported type in graph parsing!");
        }
    }

    public <T extends Comparable<T>> Graph<T> readGraph(String graphFile, Class<T> realType) throws FileNotFoundException {
        Scanner input = getScanner(graphFile);
        Map<String, List<String>> map = new HashMap<String, List<String>>();
        while(input.hasNextLine()) {
            String line = input.nextLine();
            String[] tokens = line.split(" ");
            if (tokens.length < 2 || !tokens[1].equals(">")) {
                input.close();
                throw new RuntimeException("Syntax error in parsing graph!");
            }
            map.put(
                    tokens[0],
                    Arrays.asList(Arrays.copyOfRange(tokens, 2, tokens.length)));
        }
        input.close();
        
        Graph<T> graph = new Graph<T>();
        for(String node : map.keySet()) {
            T n = parseT(node, realType);
            graph.addNode(n);
        }
        
        for(Map.Entry<String, List<String>> kvp : map.entrySet()) {
            T fromNode = parseT(kvp.getKey(), realType);
            for(String v : kvp.getValue()) {
                T toNode = parseT(v, realType);
                graph.addEdge(fromNode, toNode);
            }
        }

        return graph;
    }

    public Graph<String> readGraph(String graphFile) throws FileNotFoundException {
        return readGraph(graphFile, String.class);
    }
    
    public void assertSameGraph(String graphFile, Graph<?> g) throws FileNotFoundException {
        String expected = "";
        boolean first = true;
        Scanner parser = getScanner(graphFile);
        while(parser.hasNextLine()) {
            if (!first) {
                expected += "\n";
            }
            expected += parser.nextLine();
            first = false;
        }
        parser.close();
        assertEquals(expected, g.toString());
    }
}
