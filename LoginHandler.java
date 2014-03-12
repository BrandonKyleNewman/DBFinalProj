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

	if (correctUserPass != password) {
	    System.out.println("Incorrect password. Quitting Program...");
	    System.exit(1);

	}

	if (correctUserAccount != account) {
	    System.out.println("Incorrect account. Quitting Program...");
	    System.exit(1);
	}
    }

    private String pullPassword(String uname) throws InvalidUsernameException, SQLException {
	
	String queryResult = "SELECT * FROM customer c WHERE c.identifier = '" + uname + "'";
	
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

	String a = "";

	String queryResult = "SELECT * FROM customer c WHERE c.identifier = '" + uname + "'";
	    
	Statement stmt = myC.getConnection().createStatement();
	    
	ResultSet rs = stmt.executeQuery(queryResult);
	    
	int acc = 0;

	while (rs.next())
	    {
		acc = (rs.getInt("manager"));
		if (acc == 0)
		    a = "customer";
		else
		    a = "manager";
	    }

	return a;
    }
    
}
