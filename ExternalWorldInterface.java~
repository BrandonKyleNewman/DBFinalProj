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
					break;
				case "check inventory":
					checkInventory();
				case "fill order":
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
	}

	public void receiveShipment()
	{
		//The replenishment of the product from the shipping notice is added 
		//to the items current quantity
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
