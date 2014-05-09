package com.example.controller;

import java.sql.*;
import java.util.*;
import java.io.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.example.model.DepartmentLocation;
import com.example.model.Graph;
import com.example.model.NodeDescription;
import com.example.model.NodeLayer;
import com.example.model.NodeLocation;

public class BisonMaps extends HttpServlet {
	private static String url;
	private static DepartmentLocation[] DepartmentL;
	private static Graph[] GraphT;
	private static NodeDescription[] NodeD;
	private static NodeLocation[] NodeLo;
	private static NodeLayer[] NodeL;
	private static Properties props = new Properties();
	private static Connection conn;
	@Override
	public void init() {
		//Connect to my database
		try {
			Class.forName("org.postgresql.Driver");
			url = "jdbc:postgresql://ec2-54-197-241-78.compute-1.amazonaws.com:5432/d1sp7uh9etk0jf";
			props.setProperty("user", "whkskqvqqatyqe");
			props.setProperty("password", "Hpo87VVTl8yU4P174asS63nJXK");
			props.setProperty("ssl", "true");
			props.setProperty("sslfactory", "org.postgresql.ssl.NonValidatingFactory");
			conn = DriverManager.getConnection(url, props);
			System.out.println("Connected to the database.");
		}
		catch (Exception e) {
			System.out.println(e);
		}
		//put info from each database table into arrays
		try {
			String query = "SELECT COUNT(*) FROM \"DepartmentLocation\"";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			rs.next(); 
			String k = rs.getString(1);
			DepartmentL = new DepartmentLocation[Integer.parseInt(k)]; 
			
			String query1 = "SELECT * FROM \"DepartmentLocation\"";
			Statement stmt1 = conn.createStatement();
			ResultSet rs1 = stmt1.executeQuery(query1);
			for (int ok = 0; ok < DepartmentL.length; ok++)
			{
				if (rs1.next()) {
					int sid = Integer.parseInt(rs1.getString("Building"));
					String name = rs1.getString("DepartmentName");
					DepartmentL[ok] = new DepartmentLocation(sid,name); //array of table values
				}
			}
			
			String query2 = "SELECT COUNT(*) FROM \"Graph\"";
			Statement stmt2 = conn.createStatement();
			ResultSet rs2 = stmt2.executeQuery(query2);
			rs2.next();
			String k2 = rs2.getString(1);
			GraphT = new Graph[Integer.parseInt(k2)];
			
			String query3 = "SELECT * FROM \"Graph\"";
			Statement stmt3 = conn.createStatement();
			ResultSet rs3 = stmt3.executeQuery(query3);
			for (int ok = 0; ok < DepartmentL.length; ok++)
			{
				if (rs3.next()) {
					int sid = Integer.parseInt(rs3.getString("SID"));
					int did = Integer.parseInt(rs3.getString("DID"));
					GraphT[ok] = new Graph(sid,did);
				}
			}
			//same as before, used to put values from each table into an array
			String query4 = "SELECT COUNT(*) FROM \"NodeDescription\"";
			Statement stmt4 = conn.createStatement();
			ResultSet rs4 = stmt4.executeQuery(query4);
			rs4.next();
			String k3 = rs4.getString(1);
			NodeD = new NodeDescription[Integer.parseInt(k3)];
			
			String query5 = "SELECT * FROM \"NodeDescription\"";
			Statement stmt5 = conn.createStatement();
			ResultSet rs5 = stmt5.executeQuery(query5);
			for (int ok = 0; ok < NodeD.length; ok++)
			{
				if (rs5.next()) { //loop through all the values and add to the NodeD array
					int sid = Integer.parseInt(rs5.getString("SID"));
					String name = (rs5.getString("NAME"));
					String des = (rs5.getString("Description"));
					int ty = Integer.parseInt(rs5.getString("Type"));
					NodeD[ok] = new NodeDescription(sid,name,des,ty);
				}
			}
			
			String query6 = "SELECT COUNT(*) FROM \"NodeLayer\"";
			Statement stmt6 = conn.createStatement();
			ResultSet rs6 = stmt6.executeQuery(query6);
			rs6.next();
			String k4 = rs6.getString(1);
			NodeL = new NodeLayer[Integer.parseInt(k4)];
			
			String query7 = "SELECT * FROM \"NodeLayer\"";
			Statement stmt7 = conn.createStatement();
			ResultSet rs7 = stmt7.executeQuery(query7);
			for (int ok = 0; ok < NodeL.length; ok++)
			{
				if (rs7.next()) {
					int sid = Integer.parseInt(rs7.getString("SID"));
					int atm = Integer.parseInt(rs7.getString("ATM"));
					int ven = Integer.parseInt(rs7.getString("Vending"));
					int res = Integer.parseInt(rs7.getString("RestRoom"));
					int pub = Integer.parseInt(rs7.getString("PublicPhone"));
					int com = Integer.parseInt(rs7.getString("ComputerLab"));
					int wi = Integer.parseInt(rs7.getString("WiFi"));
					int sec = Integer.parseInt(rs7.getString("Security"));
					int bus = Integer.parseInt(rs7.getString("BusStop"));
					NodeL[ok] = new NodeLayer(sid,atm,ven,res,pub,com,wi,sec,bus);
				}
			}
			
			String query8 = "SELECT COUNT(*) FROM \"NodeLocation\"";
			Statement stmt8 = conn.createStatement();
			ResultSet rs8 = stmt8.executeQuery(query8);
			rs8.next();
			String k5 = rs8.getString(1);
			NodeLo = new NodeLocation[Integer.parseInt(k5)];
			
			String query9 = "SELECT * \"NodeLocation\"";
			Statement stmt9 = conn.createStatement();
			ResultSet rs9 = stmt9.executeQuery(query9);
			for (int ok = 0; ok < NodeLo.length;ok++)
			{
				if (rs9.next()) {
					int sid = Integer.parseInt(rs7.getString("SID"));
					int x = Integer.parseInt(rs7.getString("X"));
					int y = Integer.parseInt(rs7.getString("Y"));
					NodeLo[ok] = new NodeLocation(sid,x,y);
				}
			}
			
		}
		catch (Exception e) {
			System.out.println(e);
		}
	}
	
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
	{
		doGet(request, response); //just used to call doget
	}
	
	@SuppressWarnings("unchecked")
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
	{
		HttpSession session = request.getSession(); //start a session
		
		List<DepartmentLocation> DepartmentLs = (List<DepartmentLocation>)session.getAttribute("DepartmentLs");
		if (DepartmentLs == null) //Add something if nothing is there
		{
			DepartmentLs = new ArrayList<DepartmentLocation>();	//object is created
		}
		DepartmentLs = Arrays.asList(DepartmentL);
		session.setAttribute("DepartmentLos", DepartmentLs);	//The object here will get stored into the current session
		
		
		List<Graph> GraphTs = (List<Graph>)session.getAttribute("GraphTs");
		if (GraphTs == null) //Add something if nothing is there
		{
			GraphTs = new ArrayList<Graph>();//object is created
		}
		GraphTs = Arrays.asList(GraphT);
		session.setAttribute("GraphTos", GraphTs);	//The object here will get stored into the current session
		
		
		List<NodeDescription> NodeDs = (List<NodeDescription>)session.getAttribute("NodeDs");
		if (NodeDs == null) //Add something if nothing is there
		{
			NodeDs = new ArrayList<NodeDescription>();	//object is created
		}
		NodeDs = Arrays.asList(NodeD);
		session.setAttribute("NodeDos", NodeDs);	//The object here will get stored into the current session
		
		
		List<NodeLocation> NodeLoss = (List<NodeLocation>)session.getAttribute("NodeLoss");
		if (NodeLoss == null) //Add something if nothing is there
		{
			NodeLoss = new ArrayList<NodeLocation>();	//object is created
		}
		NodeLoss = Arrays.asList(NodeLo);
		session.setAttribute("NodeLooss", NodeLoss);	//The object here will get stored into the current session
		
		
		List<NodeLayer> NodeLs = (List<NodeLayer>)session.getAttribute("NodeLs");
		if (NodeLs == null) 	//Add something if nothing is there
		{
			NodeLs = new ArrayList<NodeLayer>();	//object is created
		}
		NodeLs = Arrays.asList(NodeL);
		session.setAttribute("NodeLos", NodeLs);	//The object here will get stored into the current session
		request.getRequestDispatcher("/index.jsp").forward(request, response);
	}

}
