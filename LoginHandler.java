import java.io.*;
import java.sql.*;


public class LoginHandler {

    private ConnectionHandler myC;

    public LoginHandler(String account, String identification, String password, ConnectionHandler c)
    {
	myC = c;

	String correctUserPass = "";
	String correctUserID = "";
	String correctUserAccount = "";
	if (account == "customer") {
	    try {
		correctUserPass = pullPassword(identification);
		correctUserID = pullID(identification);
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
		correctUserID = pullID(identification);
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

	if (correctUserPass.trim().equals(password) == false) {
	    System.out.println("Incorrect password. Quitting Program...");
	    System.exit(1);
	}

	if (correctUserID.trim().equals(identification) == false) {
	    System.out.println("Incorrect account id. Quitting Program...");
	    System.exit(1);
	}


	if (correctUserAccount.trim().equals(account) == false) {
	    System.out.println("Incorrect Account type. Quitting Program...");
	    System.exit(1);
	}

    }

    private String pullPassword(String password) throws InvalidUsernameException, SQLException {
	
	String queryResult = "SELECT * FROM Customer c WHERE c.password = '" + password + "'";
	
	Statement stmt = myC.getConnection().createStatement();
	
	ResultSet rs = stmt.executeQuery(queryResult);
	
	String pulledPassword = "";

	while (rs.next())
	{
		pulledPassword = (rs.getString("password"));
	}

	return pulledPassword;
    }

    private String pullID(String uname) throws InvalidUsernameException, SQLException{

	//String a = "";

	String queryResult = "SELECT * FROM Customer c WHERE c.customer_ID = '" + uname + "'";
	    
	Statement stmt = myC.getConnection().createStatement();
	    
	ResultSet rs = stmt.executeQuery(queryResult);
	    
	String pulledID = "";

	while (rs.next())
	{
		pulledID = (rs.getString("customer_ID"));
	}

	return pulledID;
    }

    private String pullAccount(String uname) throws InvalidUsernameException, SQLException {

	String a = "";

	String pulledAccount = "";
	
	String queryResult = "SELECT * FROM Customer c WHERE c.customer_ID = '" + uname + "'";
	
	Statement stmt = myC.getConnection().createStatement();
	
	ResultSet rs = stmt.executeQuery(queryResult);
	
	while (rs.next())
	    {
		a = (rs.getString("isManager"));
	    }

	if (a.trim().equals("true") == false) 
	    pulledAccount = "customer";
	
	else
	    pulledAccount = "manager";
	
	return pulledAccount;
    }	    
    
}
