
import java.util.TreeSet;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.Map;
import java.util.Queue;
import java.util.ArrayDeque;
import java.util.HashMap;

/* A mutable and finite Graph object. Edge labels are stored via a HashMap
   where labels are mapped to a key calculated by the following. The graph is
   undirected (whenever an Edge is added, the dual Edge is also added). Vertices
   are numbered starting from 0. */
public class Graph {

    /* Maps vertices to a list of its neighboring vertices. */
    private HashMap <Integer, Set <Integer>> neighbors = new HashMap <>();
    /* Maps vertices to a list of its connected edges. */
    private HashMap <Integer, Set <Edge>> edges = new HashMap <>();
    /* A sorted set of all edges. */
    private TreeSet <Edge> allEdges = new TreeSet <>();

    /* Returns the vertices that neighbor V. */
    public TreeSet <Integer> getNeighbors(int v) {
        return new TreeSet <Integer>(neighbors.get(v));
    }

    /* Returns all edges adjacent to V. */
    public TreeSet <Edge> getEdges(int v) {
        return new TreeSet <Edge>(edges.get(v));
    }

    /* Returns a sorted list of all vertices. */
    public TreeSet <Integer> getAllVertices() {
        return new TreeSet <Integer>(neighbors.keySet());
    }

    /* Returns a sorted list of all edges. */
    public TreeSet <Edge> getAllEdges() {
        return new TreeSet <Edge>(allEdges);
    }

    /* Adds vertex V to the graph. */
    public void addVertex(Integer v) {
        if (neighbors.get(v) == null) {
            neighbors.put(v, new HashSet <Integer>());
            edges.put(v, new HashSet <Edge>());
        }
    }

    /* Adds Edge E to the graph. */
    public void addEdge(Edge e) {
        addEdgeHelper(e.getSource(), e.getDest(), e.getWeight());
    }

    /* Creates an Edge between V1 and V2 with no weight. */
    public void addEdge(int v1, int v2) {
        addEdgeHelper(v1, v2, 0);
    }

    /* Creates an Edge between V1 and V2 with weight WEIGHT. */
    public void addEdge(int v1, int v2, int weight) {
        addEdgeHelper(v1, v2, weight);
    }

    /* Returns true if V1 and V2 are connected by an edge. */
    public boolean isNeighbor(int v1, int v2) {
        return neighbors.get(v1).contains(v2) && neighbors.get(v2).contains(v1);
    }

    /* Returns true if the graph contains V as a vertex. */
    public boolean containsVertex(int v) {
        return neighbors.get(v) != null;
    }

    /* Returns true if the graph contains the edge E. */
    public boolean containsEdge(Edge e) {
        return allEdges.contains(e);
    }

    /* Returns if this graph spans G. */
    public boolean spans(Graph g) {
        TreeSet <Integer> all = getAllVertices();
        if (all.size() != g.getAllVertices().size()) {
            return false;
        }
        Set <Integer> visited = new HashSet <>();
        Queue <Integer> vertices = new ArrayDeque <>();
        Integer curr;

        vertices.add(all.first());
        while ((curr = vertices.poll()) != null) {
            if (!visited.contains(curr)) {
                visited.add(curr);
                for (int n : getNeighbors(curr)) {
                    vertices.add(n);
                }
            }
        }
        return visited.size() == g.getAllVertices().size();
    }

    /* Overrides objects equals method. */
    public boolean equals(Object o) {
        if (!(o instanceof Graph)) {
            return false;
        }
        Graph other = (Graph) o;
        return neighbors.equals(other.neighbors) && edges.equals(other.edges);
    }

    /* A helper function that adds a new edge from V1 to V2 with WEIGHT as the
       label. */
    private void addEdgeHelper(int v1, int v2, int weight) {
        addVertex(v1);
        addVertex(v2);

        neighbors.get(v1).add(v2);
        neighbors.get(v2).add(v1);

        Edge e1 = new Edge(v1, v2, weight);
        Edge e2 = new Edge(v2, v1, weight);
        edges.get(v1).add(e1);
        edges.get(v2).add(e2);
        allEdges.add(e1);
    }

    public Graph prims(int start) {
        //add edges to graph, but first vertex
        Graph result = new Graph();

        Map<Integer, Edge> distFromTree = new HashMap<>();
        Set<Integer> visited = new HashSet<>();

        PriorityQueue<Integer> fringe = new PriorityQueue(new VertexComparator(distFromTree));


        for (Integer i : getAllVertices()) {
            result.addVertex(i);
        }
        //priority queue contains all vertices that are not part of the graph
        //priority value of a particular vertex v will be
        // the weight of the shortest edge that connects v to T


        fringe.add(start);

        while (!fringe.isEmpty()) {
            int v = fringe.poll();
            visited.add(v);
            result.addVertex(v);

            if (visited.size() == getAllVertices().size() - 1) {
                break;
            }

            if (!visited.contains(v)) {
                if (v != start) {
                    result.addEdge(distFromTree.get(v));
                }

                if (result.getAllEdges().size() == getAllVertices().size() - 1) {
                    break;
                }

                for (Edge edge : getEdges(v)) {
                    if (!visited.contains(edge.getDest())) {
                        if (!distFromTree.containsKey(edge.getDest())) {
                            distFromTree.put(edge.getDest(), edge);
                            fringe.add(edge.getDest());

                        } else if (distFromTree.get(edge.getDest()).getWeight()
                                > edge.getWeight()) {
                            distFromTree.replace(edge.getDest(), edge);
                            fringe.add(edge.getDest());
                        }
                    }
                }

                visited.add(v);
            }
        }
        return result;
    }

    private class VertexComparator implements Comparator<Integer> {
        Map<Integer, Edge> distTo;

        VertexComparator(Map<Integer, Edge> x) {
            distTo = x;
        }

        @Override
        public int compare(Integer o1, Integer o2) {
            Integer a = distTo.get(o1).getWeight();
            Integer b = distTo.get(o2).getWeight();
            return a.compareTo(b);

        }
    }

    public Graph kruskals() {
        Graph result = new Graph();
        for (Integer i : getAllVertices()) {
            result.addVertex(i);
        }

        UnionFind disjointSet = new UnionFind(getAllVertices().size());
        int edgeCount = 0;
        ArrayList<Edge> sortedEdge = new ArrayList();

        for (Edge edge : getAllEdges()) {
            sortedEdge.add(edge);
        }

        while (edgeCount < getAllVertices().size() - 1 && !sortedEdge.isEmpty()) {

            Edge edge1 = sortedEdge.remove(0);
            if (disjointSet.find(edge1.getSource()) != disjointSet.find(edge1.getDest())) {
                disjointSet.union(edge1.getSource(), edge1.getDest());
                result.addEdge(edge1);
                edgeCount++;
            }
        }
        return result;
    }
}
