import java.io.*;
import java.sql.*;
import java.util.*;

public class CustomerInterface
{
    private String customerID;
    private ConnectionHandler custConn;
    
    private int getCurrentMonth() {
	Calendar cal = Calendar.getInstance();
	int month = cal.get(cal.MONTH);
	return month;
    }
    
    public CustomerInterface(String username, ConnectionHandler conn)
    {
	custConn = conn;
	customerID = username;
	
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
			System.out.println("Not a valid command.");
		    }
		
		switch( command )
		    {
		    case "search":
			searchPrompt();
			break;
		    case "add":
			addItem();
			break;
		    case "delete":
			
			break;
		    case "display":
			displayItem();
			break;
		    case "check out":
			break;
		    case "find":
			break;
		    case "re-run":
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
    
    
    private void search(String[] searchItem)
    {
	//for every item in search item
	//split it into attribute and identifier
	//add to query
	//once finished, search query
    }
    
    private void searchPrompt()
    {
	ArrayList<String> searches = new ArrayList<String>();
	ArrayList<String> queries = new ArrayList<String>();
	
	System.out.println("Search in the form 'attribute identifier'. Press 'done' when finished with searches.");
	
	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	boolean continueSearch = true;
	int stringCounter = 0;
	String searchQuery = null;
	
	while (continueSearch == true)
	{
		try {
			searchQuery = br.readLine();
		} catch (IOException ioe) {
			System.out.println("Error");
		}

		if (!searchQuery.equals("done"))
		{
			searches.add(searchQuery);
			stringCounter++;
		}
		else
			continueSearch = false;
	}

	//need to modify for description
	for (int i = 0; i < stringCounter; i++)
	{
		String[] parts    = searches.get(i).split(" ");
		String attribute  = parts[0];
		String identifier = parts[1];

		String qry = " " + parts[0] + "=" + "'" + parts[1] + "'" + " ";
		queries.add(qry);
	}

	String finalQuery = "select * from Product P where";

	for (int i = 0; i < queries.size(); i++)
	{
		String queryAddition = queries.get(i);
		if (i != queries.size()-1)
			finalQuery = finalQuery + queryAddition + "AND";
		else
			finalQuery = finalQuery + queryAddition;
	}

	Statement st = null;
	ResultSet rs = null;

	System.out.println(finalQuery);

	try {
		st = custConn.getConnection().createStatement();
		rs = st.executeQuery(finalQuery);
		
		while (rs.next())
		{
			System.out.println("Product Stock number" + " " + rs.getString("stock_number"));
			System.out.println("Product Manufacturer" + " " + rs.getString("manufacturer_name"));	
		}
	} catch (Exception e) {
		System.out.println("Error executing queries");
	}

	
    }
    
    private void addItem()
    {
	String stock_num = null;
	System.out.println("Type the stock number of the item you would like to purchase.");
	try {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		stock_num = br.readLine();
	} catch (Exception e) {
		System.out.println("error reading input");
	}

	Statement st = null;
	ResultSet rs = null;

	try {
		st = custConn.getConnection().createStatement();
		String qry = "select p.stock_number from Product p where p.stock_number = '" + stock_num + "'";
		rs = st.executeQuery(qry);
	
		if (rs.next() != false)
		{
			System.out.println("How much of this item do you want to purcase?");
			Scanner in = new Scanner(System.in);
			int num = in.nextInt();

			//query price of item
			String insertData = "INSERT INTO ShoppingCart(customer_ID, stock_number, quantity)" + 
							 " VALUES('" + customerID + "','" + stock_num + "'," + num + ")";
			try {
				rs = st.executeQuery(insertData);
			} catch (Exception e) {
				System.out.println("Item already in cart");
			} //Might want to add functionality of letting a user put more of an item that's already in the cart in the cart
		}
		else
			System.out.println("Please enter a valid stock number");
		
	} catch (Exception e) {
		System.out.println("error accessing tables");
	}

	
    }
    
    private void deleteItem(String stock_num)
    {
	
    }
    
    private void displayItem()
    {
	Statement st = null;
	ResultSet rs = null;
	System.out.println("dklsfj");
	try {
		st = custConn.getConnection().createStatement();
		
		String qry = "select * from ShoppingCart sc";
		rs = st.executeQuery(qry);

		while(rs.next())
		{
			System.out.println("stock number: " + rs.getString("stock_number"));
			System.out.println("qty: " + rs.getInt("quantity"));
		}

	while (rs.next())
	{
		System.out.println("Shopping Cart:");
	
	}		

	} catch (Exception e) {
		System.out.println("error with sql statement");
	}
		
    }
    
    private void checkOut()
    {
	//drop table?
	//Transfers information from cart to order, prescribes ID
    }
    
    private void find(int orderNumber)
    {
	
    }

    private void reRun(int orderNumber)
    {
	
    }

	

}

























