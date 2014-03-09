import java.io.*;
import java.sql.*;
import java.util.ArrayList;

public class CustomerInterface
{
	private int customerID;
	private ConnectionHandler custConn;


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

		System.out.println("Search in the form 'attribute identifier'. Press 'done' when finished with searches.");

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		boolean continueSearch = true;
		int stringCounter = 0;
		String searchQuery = null;

		while (continueSearch == true)
		{
			try
			{
				searchQuery = br.readLine();
			}
			catch (IOException ioe)
			{
				System.out.println("Fuck");
			}
			if (!searchQuery.equals("done"))
			{
				searches.add(searchQuery);
				stringCounter++;
			}
			else
			{
				System.out.println("See yA!");
				continueSearch = false;
			}
		}

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
				System.out.println("Fuck");
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

}

























