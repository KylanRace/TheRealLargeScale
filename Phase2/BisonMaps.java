/*************************************************************************
 *  Compilation:  javac BisonMaps.java
 *  Dependencies: StdIn.java
 *
 *  Reads in a map from a file, and repeatedly reads in two integers s
 *  and d from standard input, and prints the shortest path from s
 *  to d to standard output.
 *
 ****************************************************************************/

import java.sql.SQLException;

public class BisonMaps {
    public static void main(String[] args) throws SQLException {
        DataConnector.main(args);
        int user_option;
        do { 
        	System.out.println("1. Get Directions");
        	System.out.println("2. Find Campus Resources");
        	System.out.println("3. Display Campus Building Names");
        	System.out.println("4. Display All Campus Resources");
        	System.out.println("5. Display All Department Locations");
        	System.out.println("6. Quit");
        	System.out.println();
        	do { // read in the users menu option
        		System.out.print("Enter (1 - 6): ");
            	user_option = StdIn.readInt();
        	} while(user_option < 1 || user_option > 6); 
        	switch (user_option) { 
				case 1:
					System.out.println();
					DataConnector.getDirections();
					break;
				case 2:
					System.out.println();
					DataConnector.findCampusResources();
					break;
				case 3:
					System.out.println();
					DataConnector.displayBuildings();
					break;
				case 4:
					System.out.println();
					DataConnector.displayResources();
					break;
				case 5:
					System.out.println();
					DataConnector.showDepartments();
					break;
				case 6:
				default:
					break;
        	}
        } while (user_option != 6); // loop mainmen
    }
}