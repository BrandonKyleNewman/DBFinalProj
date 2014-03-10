import java.io.*;
import java.sql.*;
//test
public class Emart
{
	public static void main(String [] args) throws SQLException
	{
		ConnectionHandler C = new ConnectionHandler();
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String userType;
		LoginHandler L;

		//System.out.println("Hello, are you a manager or a customer?");
		System.out.println("Hello, please enter your information as: ACCOUNT_TYPE ID PASSWORD ");
		boolean loggedIn = false;
		while (loggedIn == false)
		{		
			try {
			    userType = args[0];
			}catch (IOException ioe){
			    System.out.println("Error reading command. Exiting.");
			    System.exit(0);
			}

			if (userType.equals("customer") == true)
			{
			    L = new LoginHandler("customer", args[1], args[2], C);
			    CustomerInterface U = new CustomerInterface(args[1], C);
			    loggedIn = true;
			}
			else if (userType.equals("manager") == true)
			{	
			    L = new LoginHandler("manager", args[1], args[2], C);
			    ManagerInterface M = new ManagerInterface(args[1], C);
			    loggedIn = true;
			}
			else
			{
			    System.out.println("I'm sorry, I didn't quite get that.");
			}
		}
	}
}
