import java.io.*;
import java.sql.*;
import java.util.*;

public class CustomerInterface
{
    private int customerID;
    private ConnectionHandler custConn;
    
    private int getCurrentMonth() {
	Calendar cal = Calendar.getInstance();
	int month = cal.get(cal.MONTH);
	return month;
    }
    
    public CustomerInterface(String username, ConnectionHandler conn)
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
			System.out.println("Not a valid command.");
		    }
		
		switch( command )
		    {
		    case "search":
			searchPrompt();
		    case "add":
			break;
		    case "delete":
			break;
		    case "display":
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
		String[] parts    = searches[i].split(" ");
		String attribute  = parts[0];
		String identifier = parts[1];

		String qry = " " + parts[0] + "=" + parts[1] + " ";
		queries.add(qry);
	}

	String finalQuery = "select * from Product P where";

	for (int i = 0; i < queries.length(); i++)
	{
		String queryAddition = queries.at(i);
		finalQuery = finalQuery + queryAddition;
	}

	System.out.println(finalQuery);
	
    }
    
    private void addItem(String searchItem)
    {
	
    }
    
    private void deleteItem(String searchItem)
    {
	
    }
    
    private void displayItem(String searchItem)
    {
	
    }
    
    private void checkOut()
    {
	
    }
    
    private void find(int orderNumber)
    {
	
    }

    private void reRun(int orderNumber)
    {
	
    }

	

}

























