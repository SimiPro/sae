package week1;
import java.util.*;

/**
 * Created by simipro on 28.02.17.
 */
public class Problem1 {

    public static void main(String[] args) {

        Node n1 = new Node();
        Node n2 = new Node();
        Node n3 = new Node();
        Node n4 = new Node();
        Node n5 = new Node();
        Node n6 = new Node();
        Node n7 = new Node();

        Edge e1 = new Edge(n1, n2, 4);
        Edge e2 = new Edge(n2, n3, 1);
        Edge e3 = new Edge(n2, n4, 3);
        Edge e4 = new Edge(n3, n5, 5);
        Edge e5 = new Edge(n5, n4, 7);
        Edge e6 = new Edge(n4, n6, 1);
        Edge e7 = new Edge(n5, n6, 1);
        Edge e8 = new Edge(n6, n7, 3);

        STGraph stGraph = new STGraph(n1, n7);
        stGraph.addEdge(e1);
        stGraph.addEdge(e2);
        stGraph.addEdge(e3);
        stGraph.addEdge(e4);
        stGraph.addEdge(e5);
        stGraph.addEdge(e6);
        stGraph.addEdge(e7);
        stGraph.addEdge(e8);

        stGraph.shortestPath().forEach(N -> System.out.println(N.distanceToRoot));



    }

}


class Node {

    // need for dijkstra
    int distanceToRoot = Integer.MAX_VALUE;
    Node cameFrom;

    Node() {

    }

}

class Edge {
    Node from;
    Node to;
    int distance;

    Edge(Node from, Node to, int distance) {
        this.from = from;
        this.to = to;
        this.distance = distance;
    }
}

class STGraph {
    List<Edge> edges = new ArrayList<Edge>();
    Node source;
    Node target;
    Map<Integer, List<Node>> pathCache = new HashMap<>();

    STGraph(Node source, Node target) {
        this.source = source;
        this.target = target;
    }

    void setST(Node source, Node target) {
        this.source = source;
        this.target = target;
    }

    void addEdge(Edge e) {
        edges.add(e);
        pathCache.clear();
    }
    
    List<Node> shortestPath() {
    	
    	// first check cache
    	int key = this.source.hashCode() + this.target.hashCode();
    	if (pathCache.containsKey(key)) {
    		return pathCache.get(key);
    	}
    	
    	// then calculate shortest path
        PriorityQueue<Node> minNode = new PriorityQueue<>(10, (o1, o2) -> new Integer(o1.distanceToRoot).compareTo(o2.distanceToRoot));
        source.distanceToRoot = 0;

        minNode.offer(source);
        Node min;
        while((min = minNode.poll()) != null) {
            final Node min2 = min;
            edges.forEach(E -> {
                if (E.from == min2) {
                    int newD = min2.distanceToRoot + E.distance;
                    if (newD < E.to.distanceToRoot) {
                        E.to.distanceToRoot = newD;
                        E.to.cameFrom = min2;
                    }
                    minNode.offer(E.to);
                }
            });
        }


        // restore shortest path
        Stack<Node> stack = new Stack<>();
        stack.push(target);

        Node to = target;
        Node from;

        while((from = to.cameFrom) != null) {
            stack.push(from);
            to = from;
        }

        List<Node> shortestPath = new ArrayList<Node>();
        stack.forEach(shortestPath::add);
        pathCache.put(key, shortestPath);
        	
        return shortestPath;
    }

}
