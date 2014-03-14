import java.io.*;
import java.sql.*;
import java.util.ArrayList;

public class ManagerInterface
{
    private int managerID;
    private ConnectionHandler custConn;
    
    
    public void printMonthlySales()
    {
	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	String commandArg = null;

	System.out.println("Please enter the month.");

	try{
	    commandArg = br.readLine();
	} catch(IOException ioe) {
	    System.out.println("Error reading month.Exiting...");
	    System.exit(0);
	}

	String monthString = commandArg;
	int monthInt = 0;
	switch(monthString) 
	{
	case "January": 
	    monthInt = 1;
	    break;
	case "February":
	    monthInt = 2;
	    break;
	case "March": 
	    monthInt = 3;
	    break;
	case "April": 
	    monthInt = 4;
	    break;
	case "May": 
	    monthInt = 5;
	    break;
	case "June": 
	    monthInt = 6;
	    break;
	case "July": 
	    monthInt = 7;
	    break;
	case "August": 
 	    monthInt = 8;
	    break;
	case "September": 
	    monthInt = 9;
	    break;
	case "October": 
	    monthInt = 10;
	    break;
	case "November": 
	    monthInt = 11;
	    break;
	case "December": 
	    monthInt = 12;
	    break;
	}

	//query sales table, returning sales report for the given month

	//print out customer who spent the most money in the month
	String id = "";
	String queryCust = "SELECT temp.customer_ID FROM (SELECT s.customer_ID, SUM(s.final_cost) AS money FROM Orders s WHERE s.month = '" + monthInt + "' GROUP BY s.customer_ID) temp WHERE temp.money = (SELECT MAX(money2) FROM (SELECT SUM(s2.final_cost) AS money2 FROM Orders s2 WHERE s2.month = '" + monthInt + "' GROUP BY s2.customer_ID))";
	Statement stmt = null;
	ResultSet rs;
	try {
	    stmt = custConn.getConnection().createStatement();
	    rs = stmt.executeQuery(queryCust);
	    while (rs.next()) 
		id = rs.getString("customer_ID");
	} catch(SQLException e) {
	    System.out.println("Error finding customer. Exiting.");
	    System.exit(1);
	}
	
	System.out.println("SALES REPORT FOR THE MONTH OF " + monthString);

    
	System.out.println("Customer who spent the most in " + monthString + ": " + id);

	//print out the (quantity, price) of sale per product in given month

	String productReport = "SELECT s.stock_number, SUM(s.quantity) AS qty, SUM(s.final_cost) AS cost FROM Orders s WHERE s.month = '" + monthInt + "' GROUP BY s.stock_number";

	try {
	    //Statement stm = custConn.getConnection().createStatement();
	    rs = stmt.executeQuery(productReport);
	    System.out.println("Summary of sales per product in " + monthString);	    
	    while (rs.next()) 
		System.out.println("Product: " + rs.getString("stock_number") + " quantity: " + rs.getInt("qty") + "price: " + rs.getDouble("cost"));
	} catch(SQLException e) {
	    System.out.println("Error finding product info. Exiting");
	    System.exit(1);
	}


	//print out the (quantity, price) of sale per category in given month

	String categoryReport = "SELECT p.category, SUM(s.quantity) AS qty, SUM(s.final_cost) AS cost FROM Orders s, Product p WHERE p.stock_number = s.stock_number AND s.month = '" + monthInt + "' GROUP BY p.category";
	
	try {
	    //Statement stm = custConn.getConnection().createStatement();
	    rs = stmt.executeQuery(categoryReport);
	    System.out.println("Summary of sales per category in " + monthString);
	    while (rs.next()) 
		System.out.println("Category: " + rs.getString("category") + " quantity: " + rs.getInt("qty") + "price: " + rs.getDouble("cost"));

	} catch(SQLException e) {
	    System.out.println("Error finding category info. Exiting");
	    System.exit(1);
	}


	System.out.println("End of monthly report");
			   
    }
    
    // Manager can adjust a customer's status manually
    public void customerAdjust()
    {

	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	String commandArg = null;

	System.out.println("Please enter the customer ID.");

	try{
	    commandArg = br.readLine();
	} catch(IOException ioe) {
	    System.out.println("Error reading customer ID.Exiting...");
	    System.exit(1);
	}

	String custID = commandArg;

	String queryCustID = "SELECT * FROM Customer c WHERE c.customer_ID = '" + custID + "'";
	Statement stmt = null;
	try {
	    stmt = custConn.getConnection().createStatement();
	    ResultSet rs = stmt.executeQuery(queryCustID);
	} catch(Exception e) {
	    System.out.println("Error looking up Customer ID. Exiting.");
	    System.exit(1);
	}

	System.out.println("Enter the new status of the customer.");
	
	try {
	    commandArg = br.readLine();
	} catch (IOException ioe) {
	    System.out.println("Error reading status. Exiting.");
	    System.exit(1);
	}

	String newStatus = commandArg;

	String updateStatus = "UPDATE Customer SET status = '" + newStatus + "' WHERE customer_ID = '" + custID + "'";

	try {
	    //Statement stm = custConn.getConnection().createStatement();
	    int r = stmt.executeUpdate(updateStatus);
	} catch (Exception e) {
	    System.out.println("Error changing customer status. Exiting.");
	    System.exit(1);
	}

	System.out.println("Successfully changed customer status.");

	
    }
    
    //Fairly big priority. Links together the two databases.
    public void sendOrder()
    {
	//query the orders table by order number
	//

	ArrayList<Item> order = new ArrayList<Item>();

	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	
	String commandArg = null;

	System.out.println("Please enter the name of the manufacturer.");

	String manu = "";

	try {
	    commandArg = br.readLine();
	}
	catch (IOException ioe) {
	    System.out.println("Error reading manufacturer. Exiting...");
	    System.exit(1);
	}

	manu = commandArg;

	System.out.println("Enter products in the form 'model_number quantity'. Type 'done' when finished");

	boolean continueOrder = true;
	String productQuery = "";
	ArrayList<String> products = new ArrayList<String>(); 
	int productCounter = 0;
	while (continueOrder == true)
	{
		try {
			productQuery = br.readLine();
		} catch (IOException ioe) {
			System.out.println("Error");
		}

		if (!productQuery.equals("done"))
		{
			products.add(productQuery);
			productCounter++;
		}
		else
			continueOrder = false;
	}
	//	System.out.println("oi");

	for (int i = 0; i < productCounter; i++)
	{
		String[] parts = products.get(i).split(" ");
		String modNum  = parts[0];
		int qty = Integer.parseInt(parts[1]);
		Item item = new Item(modNum, qty);
		//	System.out.println("hey");
		order.add(item);
		// String qry = " " + parts[0] + "=" + "'" + parts[1] + "'" + " ";
		// queries.add(qry);
	}
	
	ExternalWorldInterface e_w_i = new ExternalWorldInterface(custConn);

	int random1 = (int)(Math.random()*9 + 1);
	int random2 = (int)(Math.random()*9 + 1);
	int random3 = (int)(Math.random()*9 + 1);
	int random4 = (int)(Math.random()*9 + 1);
	String shipid = "111" + random4 + random3 + random2 + random1;
	String SNinsert = "INSERT INTO ShippingNotice(shipID, manufacturer) VALUES ('" + shipid + "', '" + manu + "')";
	Statement stm = null;
	ResultSet rs;
	try {
	    stm = custConn.getConnection().createStatement();
	    rs = stm.executeQuery(SNinsert);
	}
	catch (Exception e) {
	    System.out.println("Error inserting into ShippingNotice. Exiting");
	    System.exit(1);
	}
	//insert into shipped items    
	for (int i = 0; i < productCounter; i++) {
	    Item t = order.get(i);
	    
	    String SIinsert = "INSERT INTO ShippedItems(model_number, quantity, shipID) VALUES ('" + t.modNum + "', '" + t.qty + "', '" + shipid + "')"; 

	    try {
		//	Statement stt = custConn.getConnection().createStatement();
		rs = stm.executeQuery(SIinsert);
		System.out.println("inserted into shipped items");
	    } catch (Exception e) {
		System.out.println("Unable to insert into shipped items");
		System.exit(1);
	    }
        }
    
	System.out.println("Sent!");
	System.out.println("ORDER SUMMARY");
	
	System.out.println("Manufacturer: " + manu);


	for(int i = 0; i < productCounter; i++)
	{
	    Item t = order.get(i);
	    System.out.print("Item/Quantity: ");

	    System.out.println(t.modNum + "/" + t.qty);
	}

	e_w_i.receiveShippingNotice(shipid, manu, order);
	e_w_i.receiveShipment(shipid);
    }
    
    
    public void changePrice()
    {
	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	String commandArg = null;

	System.out.println("Please enter the stock number of the item.");

	try {
	    commandArg = br.readLine();
	} catch (IOException ioe) {
	    System.out.println("Error reading command. Exiting...");
	    System.exit(1);
	}

	String stockNum = commandArg;

	String queryStockNum = "SELECT * FROM Product p WHERE p.stock_number = '" + stockNum + "'";   // make sure the stock number exists
	Statement stmtt = null;
	try {
	    stmtt = custConn.getConnection().createStatement();
	    ResultSet rs = stmtt.executeQuery(queryStockNum);
	} catch (Exception e) {
	    System.out.println("Error looking up Stock number...Exiting.");
	    System.exit(1);
	}

	System.out.println("Enter the new price.");
	
	try {
	    commandArg = br.readLine();
	} catch(IOException ioe) {
	    System.out.println("Error reading command. Exiting.");
	    System.exit(1);
	}

	double newPrice = 0;

	try {
	    newPrice = Double.valueOf(commandArg);
	} catch(NumberFormatException nfe) {
	    System.out.println("Enter a number. Exiting.");
	    System.exit(1);
	}

	String updateQuery = "UPDATE Product p SET p.Price = '" + newPrice + "' WHERE p.stock_number = '" + stockNum + "'";

	try {
	    //Statement stmt = custConn.getConnection().createStatement();
	    int r = stmtt.executeUpdate(updateQuery);
	} catch (Exception e) {
	    System.out.println("Error changing price. Exiting.");
	    System.exit(1);
	}

	System.out.println("Successfully changed price");

	
    }
    
    public void deleteUnneededTrans()
    {
	
	//query the database, deleting all but the most recent 3 sales transactions for each customer
	
	String deleteOrders = "DELETE FROM Orders o1 WHERE (SELECT COUNT(o2.order_number) FROM Orders o2 WHERE o2.timestamp > o1.timestamp AND o2.customer_ID = o1.customer_ID) > 2";

	Statement stm = null;
	
	try {
	    stm = custConn.getConnection().createStatement();
	    ResultSet rs = stm.executeQuery(deleteOrders);
	} catch (Exception e) {
	    System.out.println("Error deleting unneeded transactions");
	    System.exit(1);
	}

	System.out.println("Successfully deleted unneeded transactions");


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
			sendOrder();
			break;
		    case "change item price":
			changePrice();
			break;
		    case "delete unneeded transactions":
			deleteUneededTrans();
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
