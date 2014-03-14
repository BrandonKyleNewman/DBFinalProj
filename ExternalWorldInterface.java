import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.lang.Math;

public class ExternalWorldInterface
{
    private ConnectionHandler custConn;
    
    public ExternalWorldInterface(ConnectionHandler conn)
    {
	custConn = conn;
    }

    public ExternalWorldInterface(ConnectionHandler conn, String user)
    {
	custConn = conn;
	System.out.println("Welcome to eDEPOT!");
		
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
			System.out.println("Not a valid command.");
		    }

		switch( command )
		    {
		    case "receive shipping notice":
			// receiveShippingNotice();
			break;
		    case "receive shipment":
			//receiveShipment();
			break;
		    case "check inventory":
			checkInventory();
		    case "fill order":
			//fillOrder();
			break;
		    case "quit":
			continueAccess = false;
			break;
		    case "help":
			break;
		    default:
			System.out.println("Unrecognized command. Enter help for list of commands");
		    }
	    }

    }

    public void receiveShippingNotice(String shipID, String manu, ArrayList<Item> items)
    {
	    // This will have information to populate the Products Depot table, specifically, the 
	    // replenishment amount.

	    // If a new product, have to insert all values. Otherwise, only need to update replenishment.
	for (int i = 0; i < items.size(); i++) {
	    String modNum = items.get(i).modNum;
	    int q = items.get(i).qty;

	    String qry = "SELECT COUNT(*) as count FROM ProductDepot p WHERE p.manufacturer = '" + manu + "' AND p.model_number = '" + modNum + "'";
	    Statement s = null;
	    int c = 0;
	    ResultSet rs;
	    try {
	    	s = custConn.getConnection().createStatement();
	    	rs = s.executeQuery(qry);
	    	while (rs.next()) {
	    	    c = rs.getInt("count");
	    	} 
	    } catch (Exception e) {
	    	System.out.println("Unable to run query. Exiting.");
	    	System.exit(1);
	    }

	    if (c == 0 ) {               //new product
	    	int random1 = (int)(Math.random()*9 + 1);
	    	int random2 = (int)(Math.random()*9 + 1);
	    	int random3 = (int)(Math.random()*9 + 1);
	    	int random4 = (int)(Math.random()*9 + 1);
	    	int random5 = (int)(Math.random()*9 + 1);

	    	String loc = "B" + random3;
	    	String stockNum = "ab" + random1 + random2 + random3 + random4 + random5;
		System.out.println("stock number: " + stockNum + "   manufacturer: " + manu + "    model_number: " + modNum);
	    	String newProd = "INSERT INTO ProductDepot(stock_number, manufacturer, model_number, quantity, min_stock_level, max_stock_level, location, replenishment) VALUES ('" + stockNum + "', '" + manu + "', '" + modNum + "', 0, 1, 10, '" + loc + "', '" + q + "')";
	    	try {
		    //Statement st = custConn.getConnection().createStatement();
	    	    rs = s.executeQuery(newProd);
	    	} catch (Exception e) {
	    	    System.out.println("Unable to insert new product");
	    	    System.exit(1);
	    	}
	    }

	    else {
	    	String updateRep = "UPDATE ProductDepot p SET p.replenishment = '" + q + "' WHERE p.manufacturer = '" + manu + "' AND p.model_number = '" + modNum + "'";
	    	try {
	    	    int rst = s.executeUpdate(updateRep);
	    	} catch (Exception e) {
	    	    System.out.println("Unable to update replenishment");
	    	    System.exit(1);
	    	}
	    }
	}
    }

    public void receiveShipment(String shippingID)
    {
	// The replenishment of the product from the shipping notice is added 
	// to the items current quantity
	
	// Assumes that a new product in the shipment is already inserted in the ProductDepot
	// table in receiveShippingNotice
	
	System.out.println("Receiving shipment...");
	
	String queryShipID = "SELECT * FROM ShippedItems s WHERE s.shipID = '" + shippingID + "'";
	
	
	
	try {
	    Statement stm = custConn.getConnection().createStatement();
	    ResultSet rs = stm.executeQuery(queryShipID);
	    while(rs.next()) 
		{
		    
		    System.out.println("hey");
		    String manu = rs.getString("manufacturer");
		    String modelNum= rs.getString("model_number");
			
		    String keep = "UPDATE ProductDepot p SET p.quantity = p.max_stock_level WHERE p.manufacturer = '" + manu + "' AND p.model_numer = '" + modelNum + "'";

		    String update = "UPDATE ProductDepot p SET p.quantity = p.quantity + p.replenishment, p.replenishment = 0 WHERE p.manufacturer = '" + manu + "' AND p.model_number = '" + modelNum + "'"; 
		
		    String check = "SELECT p.quantity, p.max_stock_level FROM ProductDepot p WHERE p.manufacturer = '" + manu + "' AND p.model_number = '" + modelNum + "'";
	
		    try {
			//Statement st = custConn.getConnection().createStatement();
			int rst = stm.executeUpdate(update);
			//	System.out.println("please work");
		    } catch (Exception e) {
			System.out.println("Error updating product quantity. Exiting");
			System.exit(1);
		    }
		    // try {
		    // 	ResultSet r = stm.executeQuery(check);
		    // 	System.out.println("whats going on");
		    // 	//while(r.next()) {
		    // 	    System.out.println("yo");
		    // 	    System.out.println("quantity: " + r.getInt("quantity"));
		    // 	    int qq = r.getInt(1);
		    // 		System.out.println("good");

		    // 	    int max = r.getInt("max_stock_level");
		    // 	    System.out.println("ok");
		    // 	    if (qq > max) {
		    // 		System.out.println("good");
		    // 		try {
		    // 		    int rr = stm.executeUpdate(keep);
		    // 		    System.out.println("better");
		    // 		} catch (Exception e) {
		    // 		    System.out.println("Error setting stock level to max level. Exiting");
		    // 		    System.exit(1);
		    // 		}
		    // 		System.out.println("too far");
		    // 	    }
		    // 	    else
		    // 		continue;
		    // 	    //	}
		    // } catch (Exception e) {
		    // 	System.out.println("Error checking max stock level. Exiting");
		    // 	System.exit(1);
		    // }
			
		}
	} catch (Exception e) 
	    {
		System.out.println("Error receiving shipment. Exiting.");
		System.exit(1);
	    }


	 System.out.println("Received shipment successfully!");

    }

    public int checkInventory()
    {
	 
	System.out.println("Enter the stock number you wish to check the quantity of");
	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	String stock_number = null;
	try {
	    stock_number = br.readLine();
	} catch (IOException ioe) {
	    System.out.println("Error reading stock number. Exiting.");
	    System.exit(0);
	}
	Statement st = null;
	try {
	    st = custConn.getConnection().createStatement();

	    String qry = "SELECT P.quantity FROM ProductDepot P WHERE P.stock_number = '" + stock_number + "'";
		
	    ResultSet rs = st.executeQuery(qry);
	    //Right now, this displays nothing if there is no matching ID. Might fix later.
	    while (rs.next())
		{
		    System.out.println("Quantity: " + rs.getInt(1));
		}
			

	} catch (Exception e) {
	    System.out.println("Error. Exiting.");
	}
	return 0;
		
    }

    public void fillOrder(String orderNum)
    {
	    
	//Called when a customer checks out
	//Query the Orders table 

	System.out.println("Filling order: " + orderNum);

	String queryOrders = "SELECT o.stock_number, o.quantity FROM Orders o WHERE o.order_number = '" + orderNum + "'";

	Statement stm = null;
	try {
	    stm = custConn.getConnection().createStatement();
		
	    ResultSet rs = stm.executeQuery(queryOrders);

	    while (rs.next()) {
		    
		String stockNum = rs.getString("stock_number");
		int qty = rs.getInt("quantity");
		    
		String updateDepot = "UPDATE ProductDepot p SET p.quantity = p.quantity - '" + qty + "' WHERE p.stock_number = '" + stockNum + "'";
		String lowStock = "SELECT x.manufacturer, x.model_number FROM(SELECT p.manufacturer FROM ProductDepot p WHERE p.min_stock_level > p.quantity GROUP BY p.manufacturer HAVING COUNT(*) > 2)temp                                     , ProductDepot x WHERE temp.manufacturer = x.manufacturer AND x.quantity < x.min_stock_level"; 

		    
		try {
		    int rst = stm.executeUpdate(updateDepot);
		} catch (Exception e) {
		    System.out.println("Unable to update eDepot product quantity. Exiting");
		    System.exit(1);
		}
		try {
		    ResultSet rst = stm.executeQuery(lowStock);
		    while(rst.next()) 
			replenishmentOrder(rst.getString("manufacturer"), rst.getString("model_number"), qty);
		} catch (Exception e) {
		    System.out.println("Error sending replenishment order to manufacturer");
		    System.exit(1);
		}
	    }
	} catch (Exception e) {
	    System.out.println("Unable to fill order. Exiting");
	    System.exit(1);
        }
	
	//query database to find manufacturers with 3 products below their min_stock_level


	System.out.println("Ordered filled successfully!");
	

    }


    public void replenishmentOrder(String manu, String modNum, int q)
    {

	System.out.println("Sending replenishment order...");

	Statement s = null;
	String updateRep = "UPDATE ProductDepot p SET p.replenishment = '" + q + "' WHERE p.manufacturer = '" + manu + "' AND p.model_number = '" + modNum + "'";
	try {
	    s = custConn.getConnection().createStatement();
	    int rst = s.executeUpdate(updateRep);
	} catch (Exception e) {
	    System.out.println("Unable to update replenishment");
	    System.exit(1);
	}
	
	String update = "UPDATE ProductDepot p SET p.quantity = p.quantity + p.replenishment, p.replenishment = 0 WHERE p.manufacturer = '" + manu + "' AND p.model_number = '" + modNum + "'"; 
			
	try {
	    //Statement st = custConn.getConnection().createStatement();
	    int rst = s.executeUpdate(update);
	} catch (Exception e) {
	    System.out.println("Error updating product quantity. Exiting");
	    System.exit(1);
	}

	System.out.println("ProductDepot replenished!");
    }

}
