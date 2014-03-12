import java.io.*;
import java.sql.*;
import java.util.ArrayList;

public class ManagerInterface
{
    private int managerID;
    private ConnectionHandler custConn;
    
    
    public void printMonthlySales()
    {
	
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

	String queryStatus = "UPDATE Customer SET status = '" + newStatus + "' WHERE identifier = '" + custID + "'";

	try {
	    Statement stm = custConn.getConnection().createStatement();
	    ResultSet r = stm.executeQuery(queryStatus);
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
	    Statement stmt = custConn.getConnection().createStatement();
	    ResultSet rs = stmt.executeQuery(updateQuery);
	} catch (Exception e) {
	    System.out.println("Error changing price. Exiting.");
	    System.exit(0);
	}

	System.out.println("Successfully changed price");

	
    }
    
    public void deleteUnneededTrans()
    {
	
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
