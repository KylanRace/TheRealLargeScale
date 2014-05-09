package com.example.model;

public class DepartmentLocation {
	
	public String DepartmentName;
	public int Building;
	
	
	public DepartmentLocation(int building, String departmentName) {
		Building = building;
		DepartmentName = departmentName;
	}


	public String getDepartmentName() {
		return DepartmentName;
	}


	public void setDepartmentName(String departmentName) {
		DepartmentName = departmentName;
	}


	public int getBuilding() {
		return Building;
	}


	public void setBuilding(int building) {
		Building = building;
	}
	

}