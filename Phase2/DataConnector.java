import java.util.*;

import java.sql.*;

public class DataConnector {

	private static DataConnector instance = null;
	
	private static String url;
	
	private static Properties props = new Properties();
	
	private static Connection conn;
	
	public static void main(String[] args) {
	
		if(instance == null) {
	
			instance = new DataConnector();
	
		}
	
	}
	
	public static DataConnector getInstance() {
	
		return instance;
	
	}
	
	public DataConnector() {
	
		try {
		
			Class.forName("org.postgresql.Driver");
			
			url = "jdbc:postgresql://ec2-54-197-241-78.compute-1.amazonaws.com:5432/d1sp7uh9etk0jf";
			
			props.setProperty("user", "whkskqvqqatyqe");
			
			props.setProperty("password", "Hpo87VVTl8yU4P174asS63nJXK");
			
			props.setProperty("ssl", "true");
			
			props.setProperty("sslfactory", "org.postgresql.ssl.NonValidatingFactory");
			
			conn = DriverManager.getConnection(url, props);
			
			System.out.println("Connected to the database.");
			
			conn.close();
		
		}
		
		catch (Exception e) {
		
			System.out.println(e);
		
		}
	
	}
	
	public static int getVertices() throws SQLException {
	
		conn = DriverManager.getConnection(url, props);
		
		String query = "SELECT COUNT(*) FROM \"NodeLocation\""; // find out how many points there are
		
		Statement stmt = conn.createStatement();
		
		ResultSet rs = stmt.executeQuery(query);
		
		rs.next();
		
		String v = rs.getString(1);
		
		int vertices = Integer.parseInt(v); // convert result from String to integer
		
		conn.close();
		
		return vertices;
	
	}
	
	public static int getEdges() throws SQLException {
	
		conn = DriverManager.getConnection(url, props);
		
		String query = "SELECT COUNT(*) FROM \"Graph\""; // edges
		
		Statement stmt = conn.createStatement();
		
		ResultSet rs = stmt.executeQuery(query);
		
		rs.next();
		
		String v = rs.getString(1);
		
		int vertices = Integer.parseInt(v); // convert result from String to integer
		
		conn.close();
		
		return vertices;
	}
	
	public static void printPath(EuclideanGraph G, int s, int d) throws SQLException {
	
	conn = DriverManager.getConnection(url, props);
	
	Dijkstra dijkstra = new Dijkstra(G);
	
	String query = "SELECT \"SID\",\"NAME\" FROM \"NodeDescription\" WHERE \"SID\" = ANY (?)"; // find the names that matched with sid's in the path
	
	String[] values = dijkstra.getPath(s, d); // get an array of sid's in order of the shortest path
	
	PreparedStatement stmt = conn.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE); // make it possible to scroll through the result data
	
	stmt.setArray(1, conn.createArrayOf("integer", values));
	
	ResultSet rs = stmt.executeQuery();
	
	int x = 0;
	
	String next = " ";
	
	String end = " ";
	
	for (int index = Integer.parseInt(values[x]); x < values.length - 1; x++) {
		
		if (x != 0)
		
			index = Integer.parseInt(values[x]);
		
		while(rs.next()) {
		
			String start = rs.getString("SID");
		
		    String name = rs.getString("NAME");
		
		    if (index == Integer.parseInt(start)) {
		    	rs.first();
		
		    	do {
		
		    		end = rs.getString("SID");
		
		    		String tempName = rs.getString("NAME");
		
		    		if (x >= 0 && x < values.length - 2) {
		
		    			if (Integer.parseInt(values[x + 1]) == Integer.parseInt(end))
		
		    				next = tempName;
		    		}
		
		    		else if (x == values.length - 2);
		
		    			if (d == Integer.parseInt(end))
		
		    				next = tempName;
		
		    	} while(rs.next());
		
		    	System.out.print("from: " + name.trim() + "\t\t\tto: " + next.trim());
		
		    	Point begin = G.point(Integer.parseInt(start));
		
		    	Point finish = G.point(Integer.parseInt(end));
		
		    	System.out.println("\t\t\tDist: " + begin.distanceTo(finish) + "m");
		    	}
		    }
		rs.first();
		}
	conn.close();
	}
	
	public static void getDirections() throws SQLException {
	
	conn = DriverManager.getConnection(url, props);
	
	String sourceID, destinationID, name = " ", sid = " ";
	
	int sID = 0, dID = 0;
	
	conn = DriverManager.getConnection(url, props);
	
	String query = "SELECT \"SID\",\"NAME\" FROM \"NodeDescription\" WHERE \"Type\" = 0 ORDER BY \"SID\"";
	
	Statement stmt2 = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
	
	ResultSet rs2 = stmt2.executeQuery(query);
	
	Scanner in = new Scanner(System.in);
	
	System.out.print("Enter Starting Location (Building): ");
	
	sourceID = in.nextLine();
	
	do {
	
	while(rs2.next()) {
	
	name = rs2.getString("NAME");
	
	sid = rs2.getString("SID");
	
	if (name.trim().equals(sourceID.trim())) {
	
	name = sourceID;
	
	sID = Integer.parseInt(sid);
	
	break;
	
	}
	
	}
	
	if (name.trim().equals(sourceID.trim())) {
	
	break;
	
	}
	
	else {
	
	System.out.print("Enter Starting Location (Building): ");
	
	sourceID = in.nextLine();
	
	rs2.first();
	
	}
	
	} while(!name.trim().equals(sourceID.trim()));
	
	query = "SELECT \"SID\",\"NAME\" FROM \"NodeDescription\" WHERE \"Type\" = 0 ORDER BY \"SID\"";
	
	stmt2 = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
	
	rs2 = stmt2.executeQuery(query);
	
	System.out.print("Enter Destination Location (Building): ");
	
	destinationID = in.nextLine();
	
	rs2.first();
	
	do {
	
	while(rs2.next()) {
	
	name = rs2.getString("NAME");
	
	String did = rs2.getString("SID");
	
	if (name.trim().equals(destinationID.trim())) {
	
	name = destinationID;
	
	dID = Integer.parseInt(did);
	
	break;
	
	}
	
	}
	
	if (name.trim().equals(destinationID.trim())) {
	
	break;
	
	}
	
	else {
	
	System.out.print("Enter Destination Location (Building): ");
	
	destinationID = in.nextLine();
	
	rs2.first();
	
	}
	
	} while(!name.trim().equals(destinationID.trim()));
	
	System.out.println();
	
	String query2 = "SELECT * FROM \"NodeLocation\"";
	
	Statement stmt3 = conn.createStatement();
	
	ResultSet rs3 = stmt3.executeQuery(query2);
	
	String query3 = "SELECT * FROM \"Graph\"";
	
	Statement stmt4 = conn.createStatement();
	
	ResultSet rs4 = stmt4.executeQuery(query3);
	
	/*ResultSet[] results = new ResultSet[2];
	
	results[0] = rs3;
	
	results[1] = rs4;
	
	EuclideanGraph.main(results);*/
	
	EuclideanGraph G = new EuclideanGraph(rs3, rs4);
	
	conn.close();
	
	conn = DriverManager.getConnection(url, props);
	
	String query4 = "SELECT \"SID\",\"NAME\" FROM \"NodeDescription\" WHERE \"SID\" = " + sID + " OR \"SID\" = " + dID;
	
	Statement stmt5 = conn.createStatement();
	
	ResultSet rs5 = stmt5.executeQuery(query4);
	
	String source = " ", destination = " ";
	
	while(rs5.next()) {
	
	sid = rs5.getString("SID");
	
	name = rs5.getString("NAME");
	
	if (sID == Integer.parseInt(sid))
	
	source = name;
	
	else if (dID == Integer.parseInt(sid))
	
	destination = name;
	
	}
	
	System.out.println("\t\t\t\t\t\t\tBISON MAP");
	
	System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------");
	
	System.out.println("Directions\nFROM: " + source + "TO: " + destination);
	
	System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------");
	
	printPath(G, sID, dID);
	
	System.out.println("=================================================================================================================================================");
	
	Point sP = G.point(sID);
	
	Point dP = G.point(dID);
	
	double distance = (sP.distanceTo(dP)) / 3;
	
	System.out.println("Total Distance: " + distance + "m");
	
	System.out.println("Time: " + (distance / 67.056) + " min");
	
	System.out.println();
	
	conn.close();
	
	}
	
	public static void findCampusResources() throws SQLException {
	
	conn = DriverManager.getConnection(url, props);
	
	int user_option;
	
	String query;
	
	Statement stmt2 = conn.createStatement();
	
	ResultSet rs2;
	
	String sourceID = " ", name = " ", sid = " ", finalSource = " ", finalDestination = " ";
	
	int destinationID = 0, sID = 0, counter = 1, dID = 0;
	
	double distance = 0;
	
	do {
	
	System.out.println("1. ATM");
	
	System.out.println("2. Public Phone");
	
	System.out.println("3. WiFi Hot Spot");
	
	System.out.println("4. Security Phone/Guard Station");
	
	System.out.println("5. Vending Machine");
	
	System.out.println("6. Rest Room");
	
	System.out.println("7. Shuttle Bus Stop");
	
	System.out.println("8. Computer Lab");
	
	System.out.println("9. Previous Menu");
	
	System.out.println();
	
	do {
	
	System.out.print("Enter Resource (1 - 9): ");
	
	user_option = StdIn.readInt();
	
	} while(user_option < 1 || user_option > 9);
	
	switch (user_option) {
	
	case 1:
	
	conn = DriverManager.getConnection(url, props);
	
	counter = 1;
	
	query = "SELECT \"SID\",\"NAME\" FROM \"NodeDescription\" WHERE \"Type\" = 0 ORDER BY \"SID\"";
	
	stmt2 = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
	
	rs2 = stmt2.executeQuery(query);
	
	Scanner in = new Scanner(System.in);
	
	System.out.print("Enter Starting Location (Building): ");
	
	sourceID = in.nextLine();
	
	do {
	
	while(rs2.next()) {
	
	name = rs2.getString("NAME");
	
	sid = rs2.getString("SID");
	
	if (name.trim().equals(sourceID.trim())) {
	
	name = sourceID;
	
	sID = Integer.parseInt(sid);
	
	break;
	
	}
	
	}
	
	if (name.trim().equals(sourceID.trim())) {
	
	break;
	
	}
	
	else {
	
	System.out.print("Enter Starting Location (Building): ");
	
	sourceID = in.nextLine();
	
	rs2.first();
	
	}
	
	} while(!name.trim().equals(sourceID.trim()));
	
	finalSource = name;
	
	System.out.println();
	
	String query2 = "SELECT \"SID\" FROM \"NodeLayer\" WHERE \"ATM\" > 0 ORDER BY \"SID\"";
	
	Statement stmt3 = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
	
	ResultSet rs3 = stmt3.executeQuery(query2);
	
	String query3 = "SELECT * FROM \"NodeLocation\"";
	
	Statement stmt4 = conn.createStatement();
	
	ResultSet rs4 = stmt4.executeQuery(query3);
	
	String query4 = "SELECT * FROM \"Graph\"";
	
	Statement stmt5 = conn.createStatement();
	
	ResultSet rs5 = stmt5.executeQuery(query4);
	
	EuclideanGraph G = new EuclideanGraph(rs4, rs5);
	
	Dijkstra dijkstra = new Dijkstra(G);
	
	while(rs3.next()) {
	
	sid = rs3.getString("SID");
	
	destinationID = Integer.parseInt(sid);
	
	if (counter == 1) {
	
	distance = dijkstra.distance(sID, destinationID);
	
	dID = destinationID;
	
	}
	
	else if (counter > 1) {
	
	if (distance > dijkstra.distance(sID, destinationID)) {
	
	distance = dijkstra.distance(sID, destinationID);
	
	dID = destinationID;
	
	}
	
	}
	
	counter++;
	
	}
	
	conn = DriverManager.getConnection(url, props);
	
	String query5 = "SELECT \"SID\",\"NAME\" FROM \"NodeDescription\" WHERE \"SID\" = " + sID  + " OR \"SID\" = " + dID;
	
	Statement stmt6 = conn.createStatement();
	
	ResultSet rs6 = stmt6.executeQuery(query5);
	
	while(rs6.next()) {
	
	sid = rs6.getString("SID");
	
	name = rs6.getString("NAME");
	
	if (sID == Integer.parseInt(sid))
	
	finalSource = name;
	
	if (dID == Integer.parseInt(sid))
	
	finalDestination = name;
	
	}
	
	System.out.println("\t\t\t\t\t\t\tBISON MAP");
	
	System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------");
	
	System.out.println("Directions\nFROM: " + finalSource + "TO: " + finalDestination);
	
	System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------");
	
	printPath(G, sID, dID);
	
	System.out.println("=================================================================================================================================================");
	
	Point sP = G.point(sID);
	
	Point dP = G.point(dID);
	
	distance = (sP.distanceTo(dP)) / 3;
	
	System.out.println("Total Distance: " + distance + "m");
	
	System.out.println("Time: " + (distance / 67.056) + " min");
	
	System.out.println();
	
	conn.close();
	
	break;
	
	case 2:
	
	conn = DriverManager.getConnection(url, props);
	
	counter = 1;
	
	query = "SELECT \"SID\",\"NAME\" FROM \"NodeDescription\" WHERE \"Type\" = 0 ORDER BY \"SID\"";
	
	stmt2 = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
	
	rs2 = stmt2.executeQuery(query);
	
	in = new Scanner(System.in);
	
	System.out.print("Enter Starting Location (Building): ");
	
	sourceID = in.nextLine();
	
	do {
	
	while(rs2.next()) {
	
	name = rs2.getString("NAME");
	
	sid = rs2.getString("SID");
	
	if (name.trim().equals(sourceID.trim())) {
	
	name = sourceID;
	
	sID = Integer.parseInt(sid);
	
	break;
	
	}
	
	}
	
	if (name.trim().equals(sourceID.trim())) {
	
	break;
	
	}
	
	else {
	
	System.out.print("Enter Starting Location (Building): ");
	
	sourceID = in.nextLine();
	
	rs2.first();
	
	}
	
	} while(!name.trim().equals(sourceID.trim()));
	
	finalSource = name;
	
	System.out.println();
	
	query2 = "SELECT \"SID\" FROM \"NodeLayer\" WHERE \"PublicPhone\" > 0 ORDER BY \"SID\"";
	
	stmt3 = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
	
	rs3 = stmt3.executeQuery(query2);
	
	query3 = "SELECT * FROM \"NodeLocation\"";
	
	stmt4 = conn.createStatement();
	
	rs4 = stmt4.executeQuery(query3);
	
	query4 = "SELECT * FROM \"Graph\"";
	
	stmt5 = conn.createStatement();
	
	rs5 = stmt5.executeQuery(query4);
	
	G = new EuclideanGraph(rs4, rs5);
	
	dijkstra = new Dijkstra(G);
	
	while(rs3.next()) {
	
	sid = rs3.getString("SID");
	
	destinationID = Integer.parseInt(sid);
	
	if (counter == 1) {
	
	distance = dijkstra.distance(sID, destinationID);
	
	dID = destinationID;
	
	}
	
	else if (counter > 1) {
	
	if (distance > dijkstra.distance(sID, destinationID)) {
	
	distance = dijkstra.distance(sID, destinationID);
	
	dID = destinationID;
	
	}
	
	}
	
	counter++;
	
	}
	
	conn = DriverManager.getConnection(url, props);
	
	query5 = "SELECT \"SID\",\"NAME\" FROM \"NodeDescription\" WHERE \"SID\" = " + sID  + " OR \"SID\" = " + dID;
	
	stmt6 = conn.createStatement();
	
	rs6 = stmt6.executeQuery(query5);
	
	while(rs6.next()) {
	
	sid = rs6.getString("SID");
	
	name = rs6.getString("NAME");
	
	if (sID == Integer.parseInt(sid))
	
	finalSource = name;
	
	if (dID == Integer.parseInt(sid))
	
	finalDestination = name;
	
	}
	
	System.out.println("\t\t\t\t\t\t\tBISON MAP");
	
	System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------");
	
	System.out.println("Directions\nFROM: " + finalSource + "TO: " + finalDestination);
	
	System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------");
	
	printPath(G, sID, dID);
	
	System.out.println("=================================================================================================================================================");
	
	sP = G.point(sID);
	
	dP = G.point(dID);
	
	distance = (sP.distanceTo(dP)) / 3;
	
	System.out.println("Total Distance: " + distance + "m");
	
	System.out.println("Time: " + (distance / 67.056) + " min");
	
	System.out.println();
	
	conn.close();
	
	break;
	
	case 3:
	
	conn = DriverManager.getConnection(url, props);
	
	counter = 1;
	
	query = "SELECT \"SID\",\"NAME\" FROM \"NodeDescription\" WHERE \"Type\" = 0 ORDER BY \"SID\"";
	
	stmt2 = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
	
	rs2 = stmt2.executeQuery(query);
	
	in = new Scanner(System.in);
	
	System.out.print("Enter Starting Location (Building): ");
	
	sourceID = in.nextLine();
	
	do {
	
	while(rs2.next()) {
	
	name = rs2.getString("NAME");
	
	sid = rs2.getString("SID");
	
	if (name.trim().equals(sourceID.trim())) {
	
	name = sourceID;
	
	sID = Integer.parseInt(sid);
	
	break;
	
	}
	
	}
	
	if (name.trim().equals(sourceID.trim())) {
	
	break;
	
	}
	
	else {
	
	System.out.print("Enter Starting Location (Building): ");
	
	sourceID = in.nextLine();
	
	rs2.first();
	
	}
	
	} while(!name.trim().equals(sourceID.trim()));
	
	finalSource = name;
	
	System.out.println();
	
	query2 = "SELECT \"SID\" FROM \"NodeLayer\" WHERE \"WiFi\" > 0 ORDER BY \"SID\"";
	
	stmt3 = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
	
	rs3 = stmt3.executeQuery(query2);
	
	query3 = "SELECT * FROM \"NodeLocation\"";
	
	stmt4 = conn.createStatement();
	
	rs4 = stmt4.executeQuery(query3);
	
	query4 = "SELECT * FROM \"Graph\"";
	
	stmt5 = conn.createStatement();
	
	rs5 = stmt5.executeQuery(query4);
	
	G = new EuclideanGraph(rs4, rs5);
	
	dijkstra = new Dijkstra(G);
	
	while(rs3.next()) {
	
	sid = rs3.getString("SID");
	
	destinationID = Integer.parseInt(sid);
	
	if (counter == 1) {
	
	distance = dijkstra.distance(sID, destinationID);
	
	dID = destinationID;
	
	}
	
	else if (counter > 1) {
	
	if (distance > dijkstra.distance(sID, destinationID)) {
	
	distance = dijkstra.distance(sID, destinationID);
	
	dID = destinationID;
	
	}
	
	}
	
	counter++;
	
	}
	
	conn = DriverManager.getConnection(url, props);
	
	query5 = "SELECT \"SID\",\"NAME\" FROM \"NodeDescription\" WHERE \"SID\" = " + sID  + " OR \"SID\" = " + dID;
	
	stmt6 = conn.createStatement();
	
	rs6 = stmt6.executeQuery(query5);
	
	while(rs6.next()) {
	
	sid = rs6.getString("SID");
	
	name = rs6.getString("NAME");
	
	if (sID == Integer.parseInt(sid))
	
	finalSource = name;
	
	if (dID == Integer.parseInt(sid))
	
	finalDestination = name;
	
	}
	
	System.out.println("\t\t\t\t\t\t\tBISON MAP");
	
	System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------");
	
	System.out.println("Directions\nFROM: " + finalSource + "TO: " + finalDestination);
	
	System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------");
	
	printPath(G, sID, dID);
	
	System.out.println("=================================================================================================================================================");
	
	sP = G.point(sID);
	
	dP = G.point(dID);
	
	distance = (sP.distanceTo(dP)) / 3;
	
	System.out.println("Total Distance: " + distance + "m");
	
	System.out.println("Time: " + (distance / 67.056) + " min");
	
	System.out.println();
	
	conn.close();
	
	break;
	
	case 4:
	
	conn = DriverManager.getConnection(url, props);
	
	counter = 1;
	
	query = "SELECT \"SID\",\"NAME\" FROM \"NodeDescription\" WHERE \"Type\" = 0 ORDER BY \"SID\"";
	
	stmt2 = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
	
	rs2 = stmt2.executeQuery(query);
	
	in = new Scanner(System.in);
	
	System.out.print("Enter Starting Location (Building): ");
	
	sourceID = in.nextLine();
	
	do {
	
	while(rs2.next()) {
	
	name = rs2.getString("NAME");
	
	sid = rs2.getString("SID");
	
	if (name.trim().equals(sourceID.trim())) {
	
	name = sourceID;
	
	sID = Integer.parseInt(sid);
	
	break;
	
	}
	
	}
	
	if (name.trim().equals(sourceID.trim())) {
	
	break;
	
	}
	
	else {
	
	System.out.print("Enter Starting Location (Building): ");
	
	sourceID = in.nextLine();
	
	rs2.first();
	
	}
	
	} while(!name.trim().equals(sourceID.trim()));
	
	finalSource = name;
	
	System.out.println();
	
	query2 = "SELECT \"SID\" FROM \"NodeLayer\" WHERE \"Security\" > 0 ORDER BY \"SID\"";
	
	stmt3 = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
	
	rs3 = stmt3.executeQuery(query2);
	
	query3 = "SELECT * FROM \"NodeLocation\"";
	
	stmt4 = conn.createStatement();
	
	rs4 = stmt4.executeQuery(query3);
	
	query4 = "SELECT * FROM \"Graph\"";
	
	stmt5 = conn.createStatement();
	
	rs5 = stmt5.executeQuery(query4);
	
	G = new EuclideanGraph(rs4, rs5);
	
	dijkstra = new Dijkstra(G);
	
	while(rs3.next()) {
	
	sid = rs3.getString("SID");
	
	destinationID = Integer.parseInt(sid);
	
	if (counter == 1) {
	
	distance = dijkstra.distance(sID, destinationID);
	
	dID = destinationID;
	
	}
	
	else if (counter > 1) {
	
	if (distance > dijkstra.distance(sID, destinationID)) {
	
	distance = dijkstra.distance(sID, destinationID);
	
	dID = destinationID;
	
	}
	
	}
	
	counter++;
	
	}
	
	conn = DriverManager.getConnection(url, props);
	
	query5 = "SELECT \"SID\",\"NAME\" FROM \"NodeDescription\" WHERE \"SID\" = " + sID  + " OR \"SID\" = " + dID;
	
	stmt6 = conn.createStatement();
	
	rs6 = stmt6.executeQuery(query5);
	
	while(rs6.next()) {
	
	sid = rs6.getString("SID");
	
	name = rs6.getString("NAME");
	
	if (sID == Integer.parseInt(sid))
	
	finalSource = name;
	
	if (dID == Integer.parseInt(sid))
	
	finalDestination = name;
	
	}
	
	System.out.println("\t\t\t\t\t\t\tBISON MAP");
	
	System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------");
	
	System.out.println("Directions\nFROM: " + finalSource + "TO: " + finalDestination);
	
	System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------");
	
	printPath(G, sID, dID);
	
	System.out.println("=================================================================================================================================================");
	
	sP = G.point(sID);
	
	dP = G.point(dID);
	
	distance = (sP.distanceTo(dP)) / 3;
	
	System.out.println("Total Distance: " + distance + "m");
	
	System.out.println("Time: " + (distance / 67.056) + " min");
	
	System.out.println();
	
	conn.close();
	
	break;
	
	case 5:
	
	conn = DriverManager.getConnection(url, props);
	
	counter = 1;
	
	query = "SELECT \"SID\",\"NAME\" FROM \"NodeDescription\" WHERE \"Type\" = 0 ORDER BY \"SID\"";
	
	stmt2 = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
	
	rs2 = stmt2.executeQuery(query);
	
	in = new Scanner(System.in);
	
	System.out.print("Enter Starting Location (Building): ");
	
	sourceID = in.nextLine();
	
	do {
	
	while(rs2.next()) {
	
	name = rs2.getString("NAME");
	
	sid = rs2.getString("SID");
	
	if (name.trim().equals(sourceID.trim())) {
	
	name = sourceID;
	
	sID = Integer.parseInt(sid);
	
	break;
	
	}
	
	}
	
	if (name.trim().equals(sourceID.trim())) {
	
	break;
	
	}
	
	else {
	
	System.out.print("Enter Starting Location (Building): ");
	
	sourceID = in.nextLine();
	
	rs2.first();
	
	}
	
	} while(!name.trim().equals(sourceID.trim()));
	
	finalSource = name;
	
	System.out.println();
	
	query2 = "SELECT \"SID\" FROM \"NodeLayer\" WHERE \"Vending\" > 0 ORDER BY \"SID\"";
	
	stmt3 = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
	
	rs3 = stmt3.executeQuery(query2);
	
	query3 = "SELECT * FROM \"NodeLocation\"";
	
	stmt4 = conn.createStatement();
	
	rs4 = stmt4.executeQuery(query3);
	
	query4 = "SELECT * FROM \"Graph\"";
	
	stmt5 = conn.createStatement();
	
	rs5 = stmt5.executeQuery(query4);
	
	G = new EuclideanGraph(rs4, rs5);
	
	dijkstra = new Dijkstra(G);
	
	while(rs3.next()) {
	
	sid = rs3.getString("SID");
	
	destinationID = Integer.parseInt(sid);
	
	if (counter == 1) {
	
	distance = dijkstra.distance(sID, destinationID);
	
	dID = destinationID;
	
	}
	
	else if (counter > 1) {
	
	if (distance > dijkstra.distance(sID, destinationID)) {
	
	distance = dijkstra.distance(sID, destinationID);
	
	dID = destinationID;
	
	}
	
	}
	
	counter++;
	
	}
	
	conn = DriverManager.getConnection(url, props);
	
	query5 = "SELECT \"SID\",\"NAME\" FROM \"NodeDescription\" WHERE \"SID\" = " + sID  + " OR \"SID\" = " + dID;
	
	stmt6 = conn.createStatement();
	
	rs6 = stmt6.executeQuery(query5);
	
	while(rs6.next()) {
	
	sid = rs6.getString("SID");
	
	name = rs6.getString("NAME");
	
	if (sID == Integer.parseInt(sid))
	
	finalSource = name;
	
	if (dID == Integer.parseInt(sid))
	
	finalDestination = name;
	
	}
	
	System.out.println("\t\t\t\t\t\t\tBISON MAP");
	
	System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------");
	
	System.out.println("Directions\nFROM: " + finalSource + "TO: " + finalDestination);
	
	System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------");
	
	printPath(G, sID, dID);
	
	System.out.println("=================================================================================================================================================");
	
	sP = G.point(sID);
	
	dP = G.point(dID);
	
	distance = (sP.distanceTo(dP)) / 3;
	
	System.out.println("Total Distance: " + distance + "m");
	
	System.out.println("Time: " + (distance / 67.056) + " min");
	
	System.out.println();
	
	conn.close();
	
	break;
	
	case 6:
	
	conn = DriverManager.getConnection(url, props);
	
	counter = 1;
	
	query = "SELECT \"SID\",\"NAME\" FROM \"NodeDescription\" WHERE \"Type\" = 0 ORDER BY \"SID\"";
	
	stmt2 = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
	
	rs2 = stmt2.executeQuery(query);
	
	in = new Scanner(System.in);
	
	System.out.print("Enter Starting Location (Building): ");
	
	sourceID = in.nextLine();
	
	do {
	
	while(rs2.next()) {
	
	name = rs2.getString("NAME");
	
	sid = rs2.getString("SID");
	
	if (name.trim().equals(sourceID.trim())) {
	
	name = sourceID;
	
	sID = Integer.parseInt(sid);
	
	break;
	
	}
	
	}
	
	if (name.trim().equals(sourceID.trim())) {
	
	break;
	
	}
	
	else {
	
	System.out.print("Enter Starting Location (Building): ");
	
	sourceID = in.nextLine();
	
	rs2.first();
	
	}
	
	} while(!name.trim().equals(sourceID.trim()));
	
	finalSource = name;
	
	System.out.println();
	
	query2 = "SELECT \"SID\" FROM \"NodeLayer\" WHERE \"RestRoom\" > 0 ORDER BY \"SID\"";
	
	stmt3 = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
	
	rs3 = stmt3.executeQuery(query2);
	
	query3 = "SELECT * FROM \"NodeLocation\"";
	
	stmt4 = conn.createStatement();
	
	rs4 = stmt4.executeQuery(query3);
	
	query4 = "SELECT * FROM \"Graph\"";
	
	stmt5 = conn.createStatement();
	
	rs5 = stmt5.executeQuery(query4);
	
	G = new EuclideanGraph(rs4, rs5);
	
	dijkstra = new Dijkstra(G);
	
	while(rs3.next()) {
	
	sid = rs3.getString("SID");
	
	destinationID = Integer.parseInt(sid);
	
	if (counter == 1) {
	
	distance = dijkstra.distance(sID, destinationID);
	
	dID = destinationID;
	
	}
	
	else if (counter > 1) {
	
	if (distance > dijkstra.distance(sID, destinationID)) {
	
	distance = dijkstra.distance(sID, destinationID);
	
	dID = destinationID;
	
	}
	
	}
	
	counter++;
	
	}
	
	conn = DriverManager.getConnection(url, props);
	
	query5 = "SELECT \"SID\",\"NAME\" FROM \"NodeDescription\" WHERE \"SID\" = " + sID  + " OR \"SID\" = " + dID;
	
	stmt6 = conn.createStatement();
	
	rs6 = stmt6.executeQuery(query5);
	
	while(rs6.next()) {
	
	sid = rs6.getString("SID");
	
	name = rs6.getString("NAME");
	
	if (sID == Integer.parseInt(sid))
	
	finalSource = name;
	
	if (dID == Integer.parseInt(sid))
	
	finalDestination = name;
	
	}
	
	System.out.println("\t\t\t\t\t\t\tBISON MAP");
	
	System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------");
	
	System.out.println("Directions\nFROM: " + finalSource + "TO: " + finalDestination);
	
	System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------");
	
	printPath(G, sID, dID);
	
	System.out.println("=================================================================================================================================================");
	
	sP = G.point(sID);
	
	dP = G.point(dID);
	
	distance = (sP.distanceTo(dP)) / 3;
	
	System.out.println("Total Distance: " + distance + "m");
	
	System.out.println("Time: " + (distance / 67.056) + " min");
	
	System.out.println();
	
	conn.close();
	
	break;
	
	case 7:
	
	conn = DriverManager.getConnection(url, props);
	
	counter = 1;
	
	query = "SELECT \"SID\",\"NAME\" FROM \"NodeDescription\" WHERE \"Type\" = 0 ORDER BY \"SID\"";
	
	stmt2 = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
	
	rs2 = stmt2.executeQuery(query);
	
	in = new Scanner(System.in);
	
	System.out.print("Enter Starting Location (Building): ");
	
	sourceID = in.nextLine();
	
	do {
	
	while(rs2.next()) {
	
	name = rs2.getString("NAME");
	
	sid = rs2.getString("SID");
	
	if (name.trim().equals(sourceID.trim())) {
	
	name = sourceID;
	
	sID = Integer.parseInt(sid);
	
	break;
	
	}
	
	}
	
	if (name.trim().equals(sourceID.trim())) {
	
	break;
	
	}
	
	else {
	
	System.out.print("Enter Starting Location (Building): ");
	
	sourceID = in.nextLine();
	
	rs2.first();
	
	}
	
	} while(!name.trim().equals(sourceID.trim()));
	
	finalSource = name;
	
	System.out.println();
	
	query2 = "SELECT \"SID\" FROM \"NodeLayer\" WHERE \"BusStop\" > 0 ORDER BY \"SID\"";
	
	stmt3 = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
	
	rs3 = stmt3.executeQuery(query2);
	
	query3 = "SELECT * FROM \"NodeLocation\"";
	
	stmt4 = conn.createStatement();
	
	rs4 = stmt4.executeQuery(query3);
	
	query4 = "SELECT * FROM \"Graph\"";
	
	stmt5 = conn.createStatement();
	
	rs5 = stmt5.executeQuery(query4);
	
	G = new EuclideanGraph(rs4, rs5);
	
	dijkstra = new Dijkstra(G);
	
	while(rs3.next()) {
	
	sid = rs3.getString("SID");
	
	destinationID = Integer.parseInt(sid);
	
	if (counter == 1) {
	
	distance = dijkstra.distance(sID, destinationID);
	
	dID = destinationID;
	
	}
	
	else if (counter > 1) {
	
	if (distance > dijkstra.distance(sID, destinationID)) {
	
	distance = dijkstra.distance(sID, destinationID);
	
	dID = destinationID;
	
	}
	
	}
	
	counter++;
	
	}
	
	conn = DriverManager.getConnection(url, props);
	
	query5 = "SELECT \"SID\",\"NAME\" FROM \"NodeDescription\" WHERE \"SID\" = " + sID  + " OR \"SID\" = " + dID;
	
	stmt6 = conn.createStatement();
	
	rs6 = stmt6.executeQuery(query5);
	
	while(rs6.next()) {
	
	sid = rs6.getString("SID");
	
	name = rs6.getString("NAME");
	
	if (sID == Integer.parseInt(sid))
	
	finalSource = name;
	
	if (dID == Integer.parseInt(sid))
	
	finalDestination = name;
	
	}
	
	System.out.println("\t\t\t\t\t\t\tBISON MAP");
	
	System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------");
	
	System.out.println("Directions\nFROM: " + finalSource + "TO: " + finalDestination);
	
	System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------");
	
	printPath(G, sID, dID);
	
	System.out.println("=================================================================================================================================================");
	
	sP = G.point(sID);
	
	dP = G.point(dID);
	
	distance = (sP.distanceTo(dP)) / 3;
	
	System.out.println("Total Distance: " + distance + "m");
	
	System.out.println("Time: " + (distance / 67.056) + " min");
	
	System.out.println();
	
	conn.close();
	
	break;
	
	case 8:
	
	conn = DriverManager.getConnection(url, props);
	
	counter = 1;
	
	query = "SELECT \"SID\",\"NAME\" FROM \"NodeDescription\" WHERE \"Type\" = 0 ORDER BY \"SID\"";
	
	stmt2 = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
	
	rs2 = stmt2.executeQuery(query);
	
	in = new Scanner(System.in);
	
	System.out.print("Enter Starting Location (Building): ");
	
	sourceID = in.nextLine();
	
	do {
	
	while(rs2.next()) {
	
	name = rs2.getString("NAME");
	
	sid = rs2.getString("SID");
	
	if (name.trim().equals(sourceID.trim())) {
	
	name = sourceID;
	
	sID = Integer.parseInt(sid);
	
	break;
	
	}
	
	}
	
	if (name.trim().equals(sourceID.trim())) {
	
	break;
	
	}
	
	else {
	
	System.out.print("Enter Starting Location (Building): ");
	
	sourceID = in.nextLine();
	
	rs2.first();
	
	}
	
	} while(!name.trim().equals(sourceID.trim()));
	
	finalSource = name;
	
	System.out.println();
	
	query2 = "SELECT \"SID\",\"NAME\" FROM \"NodeLayer\" WHERE \"ComputerLab\" > 0 ORDER BY \"SID\"";
	
	stmt3 = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
	
	rs3 = stmt3.executeQuery(query2);
	
	query3 = "SELECT * FROM \"NodeLocation\"";
	
	stmt4 = conn.createStatement();
	
	rs4 = stmt4.executeQuery(query3);
	
	query4 = "SELECT * FROM \"Graph\"";
	
	stmt5 = conn.createStatement();
	
	rs5 = stmt5.executeQuery(query4);
	
	G = new EuclideanGraph(rs4, rs5);
	
	dijkstra = new Dijkstra(G);
	
	while(rs3.next()) {
	
	sid = rs3.getString("SID");
	
	destinationID = Integer.parseInt(sid);
	
	if (counter == 1) {
	
	distance = dijkstra.distance(sID, destinationID);
	
	dID = destinationID;
	
	}
	
	else if (counter > 1) {
	
	if (distance > dijkstra.distance(sID, destinationID)) {
	
	distance = dijkstra.distance(sID, destinationID);
	
	dID = destinationID;
	
	}
	
	}
	
	counter++;
	
	}
	
	conn = DriverManager.getConnection(url, props);
	
	query5 = "SELECT \"SID\",\"NAME\" FROM \"NodeDescription\" WHERE \"SID\" = " + sID  + " OR \"SID\" = " + dID;
	
	stmt6 = conn.createStatement();
	
	rs6 = stmt6.executeQuery(query5);
	
	while(rs6.next()) {
	
	sid = rs6.getString("SID");
	
	name = rs6.getString("NAME");
	
	if (sID == Integer.parseInt(sid))
	
	finalSource = name;
	
	if (dID == Integer.parseInt(sid))
	
	finalDestination = name;
	
	}
	
	System.out.println("\t\t\t\t\t\t\tBISON MAP");
	
	System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------");
	
	System.out.println("Directions\nFROM: " + finalSource + "TO: " + finalDestination);
	
	System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------");
	
	printPath(G, sID, dID);
	
	System.out.println("=================================================================================================================================================");
	
	sP = G.point(sID);
	
	dP = G.point(dID);
	
	distance = (sP.distanceTo(dP)) / 3;
	
	System.out.println("Total Distance: " + distance + "m");
	
	System.out.println("Time: " + (distance / 67.056) + " min");
	
	System.out.println();
	
	conn.close();
	
	break;
	
	case 9:
	
	default:
	
	break;
	
	}
	
	} while(user_option != 9);
	
	System.out.println();
	
	conn.close();
	
	}
	
	public static void displayBuildings() throws SQLException {
	
	conn = DriverManager.getConnection(url, props);
	
	String query = "SELECT \"NAME\" FROM \"NodeDescription\" WHERE \"Type\" = 0 ORDER BY \"SID\"";
	
	Statement stmt2 = conn.createStatement();
	
	ResultSet rs2 = stmt2.executeQuery(query);
	
	String query2 = "SELECT \"X\", \"Y\" FROM \"NodeLocation\" ORDER BY \"SID\"";
	
	Statement stmt3 = conn.createStatement();
	
	ResultSet rs3 = stmt3.executeQuery(query2);
	
	System.out.println("Building Name\t\t\t\t\t\t\t\t\tX\t\tY");
	
	while(rs2.next() && rs3.next()) {
	
	String name = rs2.getString("NAME");
	
	String x = rs3.getString("X");
	
	String y = rs3.getString("Y");
	
	System.out.println(name + x + '\t' + y);
	
	}
	
	System.out.println();
	
	conn.close();
	
	}
	
	public static void displayResources() throws SQLException {
	
	conn = DriverManager.getConnection(url, props);
	
	String query = "SELECT \"NAME\" FROM \"NodeDescription\" ORDER BY \"SID\"";
	
	Statement stmt = conn.createStatement();
	
	ResultSet rs = stmt.executeQuery(query);
	
	String query2 = "SELECT \"SID\" FROM \"NodeLayer\" WHERE \"ATM\" != 0";
	
	Statement stmt2 = conn.createStatement();
	
	ResultSet rs2 = stmt2.executeQuery(query2);
	
	System.out.println("Resource\t\t\tBuilding");
	
	int counter = 0;
	
	while(rs.next() && rs2.next()) {
	
	String name = rs.getString("NAME");
	
	String SID = rs2.getString("SID");
	
	while (Integer.parseInt(SID) != counter) {
	
	rs.next();
	
	counter++;
	
	}
	
	System.out.println("ATM\t\t\t\t" + name);
	
	counter++;
	
	}
	
	query = "SELECT \"NAME\" FROM \"NodeDescription\" ORDER BY \"SID\"";
	
	stmt = conn.createStatement();
	
	rs = stmt.executeQuery(query);
	
	query2 = "SELECT \"SID\" FROM \"NodeLayer\" WHERE \"Vending\" != 0";
	
	stmt2 = conn.createStatement();
	
	rs2 = stmt2.executeQuery(query2);
	
	counter = 0;
	
	while(rs.next() && rs2.next()) {
	
	String name = rs.getString("NAME");
	
	String SID = rs2.getString("SID");
	
	while (Integer.parseInt(SID) != counter) {
	
	rs.next();
	
	counter++;
	
	}
	
	System.out.println("Vending Machine\t\t\t" + name);
	
	counter++;
	
	}
	
	query = "SELECT \"NAME\" FROM \"NodeDescription\" ORDER BY \"SID\"";
	
	stmt = conn.createStatement();
	
	rs = stmt.executeQuery(query);
	
	query2 = "SELECT \"SID\" FROM \"NodeLayer\" WHERE \"RestRoom\" != 0";
	
	stmt2 = conn.createStatement();
	
	rs2 = stmt2.executeQuery(query2);
	
	counter = 0;
	
	while(rs.next() && rs2.next()) {
	
	String name = rs.getString("NAME");
	
	String SID = rs2.getString("SID");
	
	while (Integer.parseInt(SID) != counter) {
	
	rs.next();
	
	counter++;
	
	}
	
	System.out.println("Rest Room\t\t\t" + name);
	
	counter++;
	
	}
	
	query = "SELECT \"NAME\" FROM \"NodeDescription\" ORDER BY \"SID\"";
	
	stmt = conn.createStatement();
	
	rs = stmt.executeQuery(query);
	
	query2 = "SELECT \"SID\" FROM \"NodeLayer\" WHERE \"PublicPhone\" != 0";
	
	stmt2 = conn.createStatement();
	
	rs2 = stmt2.executeQuery(query2);
	
	counter = 0;
	
	while(rs.next() && rs2.next()) {
	
	String name = rs.getString("NAME");
	
	String SID = rs2.getString("SID");
	
	while (Integer.parseInt(SID) != counter) {
	
	rs.next();
	
	counter++;
	
	}
	
	System.out.println("Public Phone\t\t\t" + name);
	
	counter++;
	
	}
	
	query = "SELECT \"NAME\" FROM \"NodeDescription\" ORDER BY \"SID\"";
	
	stmt = conn.createStatement();
	
	rs = stmt.executeQuery(query);
	
	query2 = "SELECT \"SID\" FROM \"NodeLayer\" WHERE \"ComputerLab\" != 0";
	
	stmt2 = conn.createStatement();
	
	rs2 = stmt2.executeQuery(query2);
	
	counter = 0;
	
	while(rs.next() && rs2.next()) {
	
	String name = rs.getString("NAME");
	
	String SID = rs2.getString("SID");
	
	while (Integer.parseInt(SID) != counter) {
	
	rs.next();
	
	counter++;
	
	}
	
	System.out.println("ComputerLab\t\t\t" + name);
	
	counter++;
	
	}
	
	System.out.println("WiFi\t\t\t\tWiFi available everywhere through HOWARD (sometimes)");
	
	query = "SELECT \"NAME\" FROM \"NodeDescription\" ORDER BY \"SID\"";
	
	stmt = conn.createStatement();
	
	rs = stmt.executeQuery(query);
	
	query2 = "SELECT \"SID\" FROM \"NodeLayer\" WHERE \"Security\" != 0";
	
	stmt2 = conn.createStatement();
	
	rs2 = stmt2.executeQuery(query2);
	
	counter = 0;
	
	while(rs.next() && rs2.next()) {
	
	String name = rs.getString("NAME");
	
	String SID = rs2.getString("SID");
	
	while (Integer.parseInt(SID) != counter) {
	
	rs.next();
	
	counter++;
	
	}
	
	System.out.println("Security Phone\t\t\t" + name);
	
	counter++;
	
	}
	
	query = "SELECT \"NAME\" FROM \"NodeDescription\" ORDER BY \"SID\"";
	
	stmt = conn.createStatement();
	
	rs = stmt.executeQuery(query);
	
	query2 = "SELECT \"SID\" FROM \"NodeLayer\" WHERE \"BusStop\" != 0";
	
	stmt2 = conn.createStatement();
	
	rs2 = stmt2.executeQuery(query2);
	
	counter = 0;
	
	while(rs.next() && rs2.next()) {
	
	String name = rs.getString("NAME");
	
	String SID = rs2.getString("SID");
	
	while (Integer.parseInt(SID) != counter) {
	
	rs.next();
	
	counter++;
	
	}
	
	System.out.println("Shuttle Stop\t\t\t" + name);
	
	counter++;
	
	}
	
	System.out.println();
	
	conn.close();
	
	}
	
	public static void showDepartments() throws SQLException {
	
	conn = DriverManager.getConnection(url, props);
	
	String query = "SELECT \"DepartmentName\",\"Building\" FROM \"DepartmentLocation\" ORDER BY \"DepartmentName\"";
	
	Statement stmt2 = conn.createStatement();
	
	ResultSet rs2 = stmt2.executeQuery(query);
	
	System.out.println("Department\t\t\t\t\t\t\t\t\tBuilding");
	
	while(rs2.next()) {
	
	String department = rs2.getString("DepartmentName");
	
	String building = rs2.getString("Building");
	
	System.out.println(department + building);
	
	}
	
	System.out.println();
	
	conn.close();
	
	}

}