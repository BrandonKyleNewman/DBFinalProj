import java.io.*;
import java.sql.*;
import java.util.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

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
			deleteItem();
			break;
		    case "display":
			displayItem();
			break;
		    case "check out":
			checkOut();
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
			System.out.println("How much of this item do you want to purchase?");
			Scanner in = new Scanner(System.in);
			int num = in.nextInt();

			String priceOfItem = "select p.Price from Product p where p.stock_number= '" + stock_num + "'";
			rs = st.executeQuery(priceOfItem);

			rs.getInt("Price");

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
    
    private void deleteItem()
    {
	String stock_num = null;
	System.out.println("Type the stock number of the item you would like to delete from the cart.");
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
		String qry = "select sc.stock_number from ShoppingCart sc where sc.stock_number = '" + stock_num + "'";
		rs = st.executeQuery(qry);

		if (rs.next() != false)
		{
			System.out.println("How much of this item do you want to decrease?");
			Scanner in = new Scanner(System.in);
			int decreaseNum = in.nextInt();
			
			String checkQty = "select sc.quantity from ShoppingCart sc where sc.stock_number = '" + stock_num + "'";
			rs = st.executeQuery(checkQty);
			rs.next();
			int actQty = rs.getInt("quantity");
			
			if (actQty < decreaseNum)
			{
				String deleteRow = "delete from ShoppingCart sc where sc.stock_number = '" + stock_num + "'";
				rs = st.executeQuery(deleteRow);
			}
			else
			{
				int updateNum = actQty - decreaseNum;
				String updateRow = "update ShoppingCart sc set sc.quantity = " + Integer.toString(updateNum) + " where sc.stock_number = '" + stock_num + "'";
				rs = st.executeQuery(updateRow);
			}
			
		}
		else
			System.out.println("Please enter a valid stock number");
	}
	catch (Exception e) {
		System.out.println("Error");
	}

		
    }
    
    private void displayItem()
    {
	Statement st = null;
	ResultSet rs = null;
	try {
		st = custConn.getConnection().createStatement();
		System.out.println(customerID);
		String qry = "select * from ShoppingCart sc where sc.customer_ID = '" + customerID + "'";
		rs = st.executeQuery(qry);

		while(rs.next())
		{
			System.out.println("stock number:   " + rs.getString("stock_number"));
			System.out.println("         qty:   " + rs.getInt("quantity"));
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
	String orderNum = "";
	boolean isUnique = false;
	Statement st = null;
	ResultSet rs = null;
	ResultSet rs1 = null;
	Random generator = new Random();

	while (isUnique == false)
	{
		int randInt = generator.nextInt(999999);
		orderNum = Integer.toString(randInt);
		String checkUnique = "select o.order_number from Orders o where o.order_number = '" + orderNum + "'";
		
		try {		
			st = custConn.getConnection().createStatement();
			rs = st.executeQuery(checkUnique);
		
			if(rs.next() == false)
				isUnique = true;

		} catch (Exception e) {
			System.out.println("Error generating number");
		}
	}
	
	try {
		String getStockNums = "select sc.stock_number from Shopping Cart sc where sc.customer_ID = '" + customerID + "'";
		rs = st.executeQuery(getStockNums);

		while (rs.next())
		{
			int month = getCurrentMonth();
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			java.util.Date date = new java.util.Date();
			String finalDate = dateFormat.format(date);
			String stockNum = rs.getString("stock_number");
			int finalQty = rs.getInt("finalQty");

			String priceOfItem = "select p.Price from Product p where p.stock_number= '" + stockNum + "'";
			rs1 = st.executeQuery(priceOfItem);
			double price = rs1.getInt("Price");
			double finalPrice = finalQty*price;

			//find out discount percentage
			String findDiscount = "select c.status from Customer c where c.customer_ID = '" + customerID + "'";
			double discountInt = 0;
			rs1.next();
			String discount = rs1.getString("status");

			if (discount.equals("New"))
				discountInt = .10;
			else if (discount.equals("Green"))
				discountInt = .10;
			else if (discount.equals("Silver"))
				discountInt = .05;
			else if (discount.equals("Gold"))
				discountInt = .10;
			
		
			String newOrder = "insert into Orders(order_number, month, sale_date, customer_ID, stock_number, customer_Discount, final_cost, quantity) values('" + 
								orderNum + "'," + month + ",'" + finalDate + "','" + customerID + "','" + stockNum + "'," + discountInt + "," + finalPrice + "," + finalQty + ")";
			rs1 = st.executeQuery(newOrder);
		}
			//for every stock_number in shopping cart
			//order number is newly calculated order number
			//month is current month
			//sale date is current sale date
			//customer_ID is current customer ID
			//final cost is the total cost of all items
			//quantity is the total number of that product sold
			//Delete rows in shopping cart for that user
	} catch (Exception e) {
		System.out.println("Error checking out");
	}

    }
    
    private void find(int orderNumber)
    {
	
    }

    private void reRun(int orderNumber)
    {
	
    }

	

}
