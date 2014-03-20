/*************************************************************************
 *  Compilation:  javac BisonMaps.java
 *  Execution:    java BisonMaps mapfile < input.txt
 *  Dependencies: EuclideanGraph.java Dijkstra.java In.java StdIn.java
 *
 *  Reads in a map from a file, and repeatedly reads in two integers s
 *  and d from standard input, and prints the shortest path from s
 *  to d to standard output.
 *
 ****************************************************************************/
import java.io.*;
import java.io.FileInputStream;
import java.io.BufferedReader;
import java.io.FileReader;

public class BisonMaps {

    public static void main(String[] args) {
    	
    	
    	    	
        // read in the graph from a file
    	In graphin;
    	EuclideanGraph G;
        graphin = new In(args[0]);
        G = new EuclideanGraph(graphin);
        System.err.println("Done reading the graph " + args[0]);
        System.out.println();
        System.out.println("Possible Starting and Destination Points:");
        BufferedReader in = null;
    	try {
    		in = new BufferedReader(new FileReader("UserViewBuildings.txt"));
    		String content;
    		while((content = in.readLine()) != null)
    		{
    			System.out.println(content);
    		}
    	} catch (IOException e) {
    		e.printStackTrace();
    	} finally {
    		try {
    			if (in != null)
    				in.close();
    		} catch (IOException ex) {
    			ex.printStackTrace();
    		}
    	}
    	System.out.println();
    	System.out.println("Please choose a starting point and a destination by selecting two numbers pressing enter between both.");
        System.out.println();
    	// read in the s-d pairs from standard input
        Dijkstra blah = new Dijkstra(G);
        while(!StdIn.isEmpty()) {
            int s = StdIn.readInt();
            int d = StdIn.readInt();
            blah.showPath(s,d);
            System.out.println();
        }
    }
}
