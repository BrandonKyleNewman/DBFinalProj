import java.io.*;
import java.sql.*;
//test
public class Emart
{
	public static void main(String [] args) throws SQLException
	{
		ConnectionHandler C = new ConnectionHandler();
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String userType = "";
		String userID = "";
		String userPwd = "";
		LoginHandler L;

		System.out.println("Hello, are you a manager or a customer?");
    
		try {
		    userType = br.readLine();
		} catch (IOException ioe) {
		    System.out.println("IO error trying to read account type!");
		    System.exit(1);
		}

		System.out.println("Please enter your account id.");

		try {
		    userID = br.readLine();
		} catch (IOException ioe) {
		    System.out.println("IO error trying to read your ID!");
		    System.exit(1);
		}

		System.out.println("Please enter your password.");

		try {
		    userPwd = br.readLine();
		} catch (IOException ioe) {
		    System.out.println("IO error trying to read your password!");
		    System.exit(1);
		}

		boolean loggedIn = false;

		while (loggedIn == false)
		{		
		   

		    if (userType.equals("customer") == true)
			{
			    L = new LoginHandler("customer", userID, userPwd, C);
			    CustomerInterface U = new CustomerInterface(userID, C);
			    loggedIn = true;
			}
		    else if (userType.equals("manager") == true)
			{	
			    L = new LoginHandler("manager", userID, userPwd, C);
			    ManagerInterface M = new ManagerInterface(userID, C);
			    loggedIn = true;
			}
		    else
			{
			    System.out.println("I'm sorry, I didn't quite get that.");
			    break;
			}
		}
	}
}
