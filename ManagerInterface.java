import java.io.*;
import java.sql.*;
import java.util.ArrayList;

public class ManagerInterface
{
    private int managerID;
    private ConnectionHandler custConn;
    
    
    public void printMonthlySales()
    {
	BufferedReader br = new BufferedReader(new InputStreamReader(system.in));
	String commandArg = null;

	System.out.println("Please enter the month.");

	try{
	    commandArg = br.readLine();
	} catch(IOException ioe) {
	    System.out.println("Error reading month.Exiting...");
	    System.out.exit(0);
	}

	String month = commandArg;

	//query sales table, returning sales report for the given month

	//print out customer who spent the most money in the month

	String queryCust = "SELECT temp.customerID FROM (SELECT s.customerID, SUM(s.finalcost) AS money FROM Sales s WHERE s.month = '" + month + "' GROUP BY s.customerID) temp WHERE temp.money = (SELECT MAX(money2) FROM (SELECT SUM(s2.finalcost) AS money2 FROM Sales s2 WHERE s2.month = '" + month + "' GROUP BY s2.customerID))";

	try {
	    Statement stmt = custConn.getConnection().createStatement();
	    ResultSet rs = stmt.executeQuery(queryCust);
	} catch(SQLException e) {
	    System.out.println("Error finding customer. Exiting.");
	    System.exit(0);
	}
	
	System.out.println("Sales report for the month of " + month);

	String id = "";
    
	try {
	    while (rs.next()) {
		id = rs.getString("customerID"); }
	} catch (SQLException e) {
	    System.out.println("Error finding customer. Exiting.");
	    System.exit(0);
	}
	
	System.out.println("Customer who spent the most in " + month + ": " + id);

	//print out the (quantity, price) of sale per product in given month

	String productReport = "SELECT s.stocknumber, SUM(s.quantity) AS qty, SUM(s.finalcost) AS cost FROM Sales s WHERE s.month = '" + month + "' GROUP BY s.stocknumber";

	try {
	    //Statement stm = custConn.getConnection().createStatement();
	    rs = stmt.executeQuery(productReport);
	} catch(SQLException e) {
	    System.out.println("Error finding product info. Exiting");
	    System.exit(0);
	}

	System.out.println("Summary of sales per product in " + month);

	try{
	    while (rs.next()) {
		System.out.println("Product: " + rs.getString("stocknumber") + " quantity: " + rs.getInt("qty") + "price: " + rs.getDouble("cost"));
	    }
	} catch (SQLException e) {
	    System.out.println("Error finding product info. Exiting.");
	    System.out.exit(0);
	}

	//print out the (quantity, price) of sale per category in given month

	String categoryReport = "SELECT s.category, SUM(s.quantity) AS qty, SUM(s.finalcost) AS cost FROM Sales s, Product p WHERE p.stocknumber = s.stocknumber AND s.month = '" +  month + "' GROUP BY s.category";
	
	try {
	    // Statement stm = custConn.getConnection().createStatement();
	    rs = stmt.executeQuery(categoryReport);
	} catch(SQLException e) {
	    System.out.println("Error finding category info. Exiting");
	    System.exit(0);
	}

	System.out.println("Summary of sales per category in " + month);

	try{
	    while (rs.next()) {
		System.out.println("Category: " + rs.getString("category") + " quantity: " + rs.getInt("qty") + "price: " + rs.getDouble("cost"));
	    }
	} catch (SQLException e) {
	    System.out.println("Error finding category info. Exiting.");
	    System.out.exit(0);
	}
	

	System.out.println("End of monthly report");
			   
		
	
    }
    
    // Manager can adjust a customer's status manually
    public void customerAdjust()
    {

	BufferedReader br = new BufferedReader(new InputStreamReader(system.in));
	String commandArg = null;

	System.out.println("Please enter the customer ID.");

	try{
	    commandArg = br.readLine();
	} catch(IOException ioe) {
	    System.out.println("Error reading customer ID.Exiting...");
	    System.out.exit(0);
	}

	String custID = commandArg;

	String queryCustID = "SELECT * FROM Customer c WHERE c.identifier = '" + custID + "'";
       
	try {
	    Statement stmt = custConn.getConnection().createStatement();
	    ResultSet rs = stmt.executeQuery(queryCustID);
	} catch(Exception e) {
	    System.out.println("Error looking up Customer ID. Exiting.");
	    System.exit(0);
	}

	System.out.println("Enter the new status of the customer.");
	
	try {
	    commandArg = br.readLine();
	} catch (IOException ioe) {
	    System.out.println("Error reading status. Exiting.");
	    System.exit(0);
	}

	String newStatus = commandArg;

	String updateStatus = "UPDATE Customer SET status = '" + newStatus + "' WHERE identifier = '" + custID + "'";

	try {
	    // Statement stm = custConn.getConnection().createStatement();
	    int r = stmt.executeUpdate(updateStatus);
	} catch (Exception e) {
	    System.out.println("Error changing customer status. Exiting.");
	    System.out.exit(0);
	}

	System.out.println("Successfully changed customer status.");

	
    }
    
    //Fairly big priority. Links together the two databases.
    public void sendOrder()
    {
	
    }
    
    
    
    public void changePrice()
    {
	BufferedReader br = new BufferedReader(new InputStreamReader(system.in));
	String commandArg = null;

	System.out.println("Please enter the stock number of the item.");

	try {
	    commandArg = br.readLine();
	} catch (IOException ioe) {
	    System.out.println("Error reading command. Exiting...");
	    System.exit(0);
	}

	String stockNum = commandArg;

	String queryStockNum = "SELECT * FROM Product p WHERE p.stocknumber = '" + stockNum + "'";   // make sure the stock number exists

	try {
	    Statement stmt = custConn.getConnection().createStatement();
	    ResultSet rs = stmt.executeQuery(queryStockNum);
	} catch (Exception e) {
	    System.out.println("Error looking up Stock number...Exiting.");
	    System.exit(0);
	}

	System.out.println("Enter the new price.");
	
	try {
	    commandArg = br.readLine();
	} catch(IOException ioe) {
	    System.out.println("Error reading command. Exiting.");
	    System.exit(0);
	}

	double newPrice = 0;

	try {
	    newPrice = Double.valueOf(commandArg);
	} catch(NumberFormatException nfe) {
	    System.out.println("Enter a number. Exiting.");
	    System.exit(0);
	}

	String updateQuery = "UPDATE Product p SET p.price = '" + newPrice + "' WHERE p.stocknumber = '" + stockNum + "'";

	try {
	    // Statement stmt = custConn.getConnection().createStatement();
	    int r = stmt.executeUpdate(updateQuery);
	} catch (Exception e) {
	    System.out.println("Error changing price. Exiting.");
	    System.exit(0);
	}

	System.out.println("Successfully changed price");

	
    }
    
    public void deleteUnneededTrans()
    {
	
	//query the database, deleting all but the most recent 3 sales transactions for each customer
	
	


    }
    
    public ManagerInterface(String username, ConnectionHandler conn)
    {
	custConn = conn;
	
	System.out.println("Welcome to eMART!");
	
	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	String command = null;
	
	boolean continueAccess = true;
	
	while (continueAccess == true)
	    {
		System.out.println("What would you like to do?");
		
		try
		    {
			command = br.readLine();
		    }
		catch (IOException ioe)
		    {
			System.out.println("Fuck");
		    }
		
		switch( command )
		    {
		    case "print monthly sales":
			printMonthlySales();
			break;
		    case "adjust customer status":
			customerAdjust();
			break;
		    case "send order":
			break;
		    case "change item price":
			changePrice();
			break;
		    case "delete unneeded transactions":
			break;
		    case "help":
			break;
		    case "quit":
			continueAccess = false;
			break;
		    default:
			System.out.println("Unrecognized command. Enter help for list of commands");
		    }
	    }
	
    }
    
}
