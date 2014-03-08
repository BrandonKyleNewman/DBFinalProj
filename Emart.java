import java.io.*;
import java.sql.*;

public class Emart
{
	public static void main(String [] args) throws SQLException
	{
		ConnectionHandler C = new ConnectionHandler();
		CustomerInterface U = new CustomerInterface("bnewman", C);
	}
}