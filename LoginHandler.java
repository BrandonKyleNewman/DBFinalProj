import java.io.*;
import java.sql.*;


public class LoginHandler {

    private ConnectionHandler myC;

    public LoginHandler(String account, String identification, String password, ConnectionHandler c)
    {
	myC = c;

	String correctUserPass = "";
	String correctUserAccount = "";

	if (account == "customer") {
	    try {
		correctUserPass = pullPassword(identification);
		correctUserAccount = pullAccount(identification);
	    }
	    catch (InvalidUsernameException iue) {
		System.out.println(iue.getError() + "Quitting Program...");
		System.exit(1);
	    }
	    catch (SQLException e) {
		System.out.println("Error getting customer account info. Exiting");
		System.exit(1);
	    }
	}
	else if (account == "manager") {
	    try {
		correctUserPass = pullPassword(identification);
		correctUserAccount = pullAccount(identification);
	    }
	    catch (SQLException e) {
		System.out.println("Error getting manager account info. Exiting");
		System.exit(1);
	    }
	    catch (InvalidUsernameException iue) {
		System.out.println(iue.getError() + "Quitting Program...");
		System.exit(1);
	    }

	}

<<<<<<< HEAD
	if (correctUserPass.trim().equals(password) == false) {
	    System.out.println("Incorrect password. Quitting Program...");
	    System.exit(1);
=======
>>>>>>> 4b17b9026c0d0d139955188d67e0ace3d5f5c361


	if (correctUserAccount.trim().equals(identification) == false) {
	    System.out.println("Incorrect account. Quitting Program...");
	    System.exit(1);
	}
	System.out.println("Correct account id.");
	/*
	if (correctUserPass != password) {
	    System.out.println("correct password: " + correctUserPass + "entered password: " + password);
	    System.out.println("Incorrect password. Quitting Program...");
	    System.exit(1);
	    }*/
    }

    private String pullPassword(String password) throws InvalidUsernameException, SQLException {
	
<<<<<<< HEAD
	String queryResult = "SELECT * FROM Customer c WHERE c.password = '" + password + "'";
=======
	String queryResult = "SELECT * FROM Customer c WHERE c.customer_ID = '" + uname + "'";
>>>>>>> 4b17b9026c0d0d139955188d67e0ace3d5f5c361
	
	Statement stmt = myC.getConnection().createStatement();
	
	ResultSet rs = stmt.executeQuery(queryResult);
	
	String pulledPassword = "";

	while (rs.next())
	{
		pulledPassword = (rs.getString("password"));
	}

	return pulledPassword;
    }

    private String pullAccount(String uname) throws InvalidUsernameException, SQLException{

<<<<<<< HEAD
=======
	String a = "";

>>>>>>> 4b17b9026c0d0d139955188d67e0ace3d5f5c361
	String queryResult = "SELECT * FROM Customer c WHERE c.customer_ID = '" + uname + "'";
	    
	Statement stmt = myC.getConnection().createStatement();
	    
	ResultSet rs = stmt.executeQuery(queryResult);
	    
	String pulledAccount = "";

	while (rs.next())
<<<<<<< HEAD
	{
		pulledAccount = (rs.getString("customer_ID"));
	}
=======
	    {
		acc = (rs.getInt("isManager"));
		if (acc == 0)
		    a = "customer";
		else
		    a = "manager";
	    }
>>>>>>> 4b17b9026c0d0d139955188d67e0ace3d5f5c361

	return pulledAccount;
    }
    
}
