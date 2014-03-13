import java.io.*;
import java.sql.*;
import java.util.ArrayList;

public class ExternalWorldInterface
{
	private ConnectionHandler custConn;

	public ExternalWorldInterface(ConnectionHandler conn)
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
				    receiveShippingNotice();
				case "receive shipment":
				    receiveShipment();
				    break;
				case "check inventory":
				    checkInventory();
				case "fill order":
				    fillOrder();
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

	public void receiveShippingNotice()
	{
	    //This will have information to populate the Products Depot table, specifically, the 
	    //replenishment amount.

	    //If a new product, have to insert all values. Otherwise, only need to update replenishment.

	    



	}

	public void receiveShipment(String shippingID)
	{
	    //The replenishment of the product from the shipping notice is added 
	    //to the items current quantity

	    //Assumes that a new product in the shipment is already inserted in the ProductDepot
	    //table in receiveShippingNotice
	    
	    System.out.println("Receiving shipment...");

	    String queryShipID = "SELECT * FROM ShippedItems s WHERE s.shipID = '" + shippingID + "'";

	    

	    try {
		Statement stm = custConn.getConnection().createStatement();
		ResultSet rs = stm.executeQuery(queryShipID);
		while(rs.next()) 
		    {

			String manu = rs.getString("manufacturer");
			String modelNum= rs.getString("model_number");
			
			String update = "UPDATE ProductDepot p SET p.quantity = p.quantity + p.replenishment WHERE p.manufacturer = '" + manu + "' AND p.model_number = '" + modelNum + "'"; 
			
			try {
			    Statement st = custConn.getConnection().createStatement();
			    int rs = st.executeUpdate(update);
			} catch (Exception e) {
			    System.out.println("Error updating product quantity. Exiting");
			    System.exit(1);
			}
			
		    }
	    } catch (Exception e) {
		System.out.println("Error receiving shipment. Exiting.");
		System.exit(1);
	    }

	    System.out.println("Received shipment successfully!");

	}

	public int checkInventory()
	{
	    System.out.println("Enter the stock number you wish to check the quanity of");
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

		String qry = 	"SELECT P.quantity FROM ProductDepot P WHERE P.stock_number = " 
		    + "'" + stock_number + "'";
		
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

	public void fillOrder()
	{

	}

}
