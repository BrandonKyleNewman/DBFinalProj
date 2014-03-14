import java.io.*;
import java.sql.*;

public class Edepot
{
	public static void main(String[] args) throws SQLException
	{
	    String s = "string";
	    ConnectionHandler C = new ConnectionHandler();
	    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	    
	    ExternalWorldInterface E = new ExternalWorldInterface(C, s);

	}

}
