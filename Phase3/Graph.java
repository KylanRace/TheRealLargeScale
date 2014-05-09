//Class for Graph
package com.example.model;

public class Graph {
	
	public int SID;
	public int DID;
	
	public Graph(int sID, int dID) {
		SID = sID;
		DID = dID;
	}
	//getters and setters for the column values
	public int getSID() {
		return SID;
	}

	public void setSID(int sID) {
		SID = sID;
	}

	public int getDID() {
		return DID;
	}

	public void setDID(int dID) {
		DID = dID;
	}
	
}
