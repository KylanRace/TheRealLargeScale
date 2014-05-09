//Class for NodeLayer
package com.example.model;
	
public class NodeLayer {
	public int SID;
	public int ATM;
	public int Vending;
	public int RestRoom;
	public int PublicPhone;
	public int ComputerLab;
	public int WiFi;
	public int Security;
	public int BusStop;
	
	public NodeLayer(int sID, int aTM, int vending, int restroom, int publicphone, int computerlab, int wifi, int security, int busstop) {
		SID = sID;
		ATM = aTM;
		Vending = vending;
		RestRoom = restroom;
		PublicPhone = publicphone;
		ComputerLab = computerlab;
		WiFi = wifi;
		Security = security;
		BusStop = busstop;
	}
	//getters and setters for the column values
	public int getSID() {
		return SID;
	}

	public void setSID(int sID) {
		SID = sID;
	}

	public int getATM() {
		return ATM;
	}

	public void setATM(int aTM) {
		ATM = aTM;
	}

	public int getVending() {
		return Vending;
	}

	public void setVending(int vending) {
		Vending = vending;
	}

	public int getRestRoom() {
		return RestRoom;
	}

	public void setRestRoom(int restRoom) {
		RestRoom = restRoom;
	}

	public int getPublicPhone() {
		return PublicPhone;
	}

	public void setPublicPhone(int publicPhone) {
		PublicPhone = publicPhone;
	}

	public int getComputerLab() {
		return ComputerLab;
	}

	public void setComputerLab(int computerLab) {
		ComputerLab = computerLab;
	}

	public int getWiFi() {
		return WiFi;
	}

	public void setWiFi(int wiFi) {
		WiFi = wiFi;
	}

	public int getSecurity() {
		return Security;
	}

	public void setSecurity(int security) {
		Security = security;
	}

	public int getBusStop() {
		return BusStop;
	}

	public void setBusStop(int busStop) {
		BusStop = busStop;
	}
	
}
