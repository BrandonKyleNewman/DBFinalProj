import java.io.*;
import java.sql.*;

public class CustomerInterface
{
	private int customerID;
	private ConnectionHandler custConn;


	private void search(String searchItem)
	{

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
		}
		
	}

}

























