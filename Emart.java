import java.io.*;
import java.sql.*;

public class Emart
{
	public static void main(String [] args) throws SQLException
	{
		ConnectionHandler C = new ConnectionHandler();
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String userType = null;


		System.out.println("Hello, are you a manager or a customer?");

		boolean loggedIn = false;
		while (loggedIn == false)
		{		
			try {
				userType = br.readLine();
			}catch (IOException ioe){
				System.out.println("Error reading command. Exiting.");
				System.exit(0);
			}

			if (userType.equals("customer") == true)
			{
				CustomerInterface U = new CustomerInterface("bnewman", C);
				loggedIn = true;
			}
			else if (userType.equals("manager") == true)
			{	
				ManagerInterface M = new ManagerInterface("bnewman", C);
				loggedIn = true;
			}
			else
			{
				System.out.println("I'm sorry, I didn't quite get that.");
			}
		}
	}
}
