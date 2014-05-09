package com.example.model;

public class NodeLocation {
	public int SID;
	public int X;
	public int Y;
	
	public NodeLocation (int sID, int x, int y) {
		SID = sID;
		X = x;
		Y = y;
	}

	public int getSID() {
		return SID;
	}

	public void setSID(int sID) {
		SID = sID;
	}

	public int getX() {
		return X;
	}

	public void setX(int x) {
		X = x;
	}

	public int getY() {
		return Y;
	}

	public void setY(int y) {
		Y = y;
	}
	
}
