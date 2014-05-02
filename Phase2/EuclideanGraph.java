import java.sql.ResultSet;
import java.sql.SQLException;

/*************************************************************************
 *  Compilation:  javac EuclideanGraph.java
 *  Execution:    java EuclideanGraph
 *  Dependencies: In.java IntIterator.java
 *  
 *  Undirected graph of points in the plane, where the edge weights
 *  are the Euclidean distances.
 *
 *************************************************************************/


public class EuclideanGraph {
    // for portability
    private final static String NEWLINE = System.getProperty("line.separator");

    private int V;            // number of vertices
    private int E;            // number of edges
    private Node[]  adj;      // adjacency lists
    private Point[] points;   // points in the plane
    
    // node helper class for adjacency list
    private static class Node {
        int v;
        Node next;
        Node(int v, Node next) { this.v = v; this.next = next; }
    }

    // iterator for adjacency list
    private class AdjListIterator implements IntIterator {
        private Node x;
        AdjListIterator(Node x)  { this.x = x; }
        public boolean hasNext() { return x != null; }
        public int next() { 
            int v = x.v;
            x = x.next;
            return v;
        }
    }


   /*******************************************************************
    *  Carries in two query result sets as parameters and uses those
    *  to enter in node and edge information into respective arrays
    *  V E is retrieved from DataConnector methods
    *  node: id x y
    *  edge: from to
    *  @throws SQLException 
    *******************************************************************/
    public EuclideanGraph(ResultSet rs, ResultSet rs2) throws SQLException {
        V = DataConnector.getVertices(); // DataConnector method that gets number of vertices
        E = DataConnector.getEdges(); // DataConnector method that gets number of edges

        // iterate through results and insert vertices
        points = new Point[V];
        int v, x, y;
        while(rs.next()) {
			String sid = rs.getString("SID");
			String tempX = rs.getString("X");
			String tempY = rs.getString("Y");
			v = Integer.parseInt(sid);
			x = Integer.parseInt(tempX);
			y = Integer.parseInt(tempY);
            if (v < 0 || v >= V) throw new RuntimeException("Illegal vertex number");
            points[v] = new Point(x, y);
        }

        // iterate through results and insert edges
        adj = new Node[V];
        int w;
        while(rs2.next()) {
			String sid = rs2.getString("SID");
			String did = rs2.getString("DID");
			v = Integer.parseInt(sid);
			w = Integer.parseInt(did);
            if (v < 0 || v >= V) throw new RuntimeException("Illegal vertex number");
            if (w < 0 || w >= V) throw new RuntimeException("Illegal vertex number");
            adj[v] = new Node(w, adj[v]);
            adj[w] = new Node(v, adj[w]);
        }
    }


    // accessor methods
    public int V() { return V; }
    public int E() { return E; }
    public Point point(int i) { return points[i]; }

    // Euclidean distance from v to w
    public double distance(int v, int w) { return points[v].distanceTo(points[w]); }


    // return iterator for list of neighbors of v
    public IntIterator neighbors(int v) {
        return new AdjListIterator(adj[v]);
    }


    // string representation - takes quadratic time because of string concat
    public String toString() {
        String s = "";
        s += "V = " + V + NEWLINE;
        s += "E = " + E + NEWLINE;
        for (int v = 0; v < V && v < 100; v++) {
            String t = v + " " + points[v] + ": ";
            for (Node x = adj[v]; x != null; x = x.next)
                t += x.v + " ";
            s += t + NEWLINE;
        }
        return s;
    }


    // test client
    public static void main(ResultSet[] args) throws SQLException {
        EuclideanGraph G = new EuclideanGraph(args[0], args[1]);
        System.out.println(G);
    }

}