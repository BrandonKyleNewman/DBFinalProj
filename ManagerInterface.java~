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


	public void customerAdjust()
	{
	
	}

	//Fairly big priority. Links together the two databases.
	public void sendOrder()
	{
	
	}

	public void changePriceOfItem(String itemName, int newPrice)
	{
	
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
					break;
				case "send order":
					break;
				case "change item price":
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
