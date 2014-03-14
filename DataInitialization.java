import java.sql.*;

public class DataInitialization {
	
	private Connection conn;

	DataInitialization() throws SQLException
	{

		try {
			DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
		} catch (SQLException e) {
			System.out.println("Set up a class path to ojdbc6.jar");
			System.exit(1);
		}
		
		String strConn = "jdbc:oracle:thin:@uml.cs.ucsb.edu:1521:xe";
		String strUsername = "bnewman";
		String strPassword = "4672200";
		System.out.println("Connecting...");

		conn = DriverManager.getConnection(strConn,strUsername,strPassword);

		System.out.println("Connected!");

	}

    	public void initialData()
    	{
		try { 
			System.out.println("Initializing eMART Tables");

						//Product Table
			Statement st = conn.createStatement();
			System.out.println("testing");
			String createTable = 	"CREATE TABLE Product(	stock_number CHAR(7)," + 
								  	"Price REAL, manufacturer_name CHAR(20)," +
								 	"model_number CHAR(20)," +
									"category CHAR(20)," +
									"warranty INTEGER," +  
									"PRIMARY KEY (stock_number))";
			System.out.println("before result set");
			ResultSet rs = st.executeQuery(createTable);
			System.out.println("Product table created");
			
						//Accessory Table
			createTable = 		"CREATE TABLE Accessory(parent_stock_number CHAR(7)," +
									"child_stock_number CHAR(7)," + 
									"PRIMARY KEY(parent_stock_number, child_stock_number)," +
									"FOREIGN KEY(parent_stock_number) REFERENCES Product(stock_number)," +
									"FOREIGN KEY(child_stock_number) REFERENCES Product(stock_number))";
			rs = st.executeQuery(createTable);
			System.out.println("Accessory table created");
		
						//Description Table
			createTable = 		"CREATE TABLE Description( attribute CHAR(20)," +
									  "value CHAR(20)," +
									  "stock_number CHAR(7)," + 
									  "PRIMARY KEY (stock_number, attribute)," + 
									  "FOREIGN KEY (stock_number) REFERENCES Product(stock_number))";
			rs = st.executeQuery(createTable);
			System.out.println("Description table created");

			//Customer Table
			createTable = 		"CREATE TABLE Customer( customer_ID CHAR(20)," +
									"password CHAR(20)," +
									"name CHAR(20)," + 
									"email CHAR(20)," +
									"address CHAR(30)," +
									"isManager CHAR(20)," +
									"status CHAR(20)," +
									"PRIMARY KEY (customer_ID))";
			rs = st.executeQuery(createTable);
			System.out.println("Customer table created");

			//Shopping Cart Table
			createTable = 		"CREATE TABLE ShoppingCart(customer_ID CHAR(20)," +
									   "stock_number CHAR(20)," +
									   "quantity INTEGER," +
									   "total_price REAL," +
									   "PRIMARY KEY(customer_ID, stock_number)," +
									   "FOREIGN KEY(stock_number) REFERENCES Product," +
									   "FOREIGN KEY(customer_ID) REFERENCES Customer)";
			rs = st.executeQuery(createTable);
			System.out.println("ShoppingCart table created");

			//Orders Table
			createTable = 		"CREATE TABLE Orders(order_number CHAR(20),"	 +  
			                                                "month INTEGER," +
								    	"sale_date DATE," 	+  
									"customer_ID CHAR(20),"	+ 
									"stock_number CHAR(20)," +
									"customer_Discount REAL," +  
 									"final_cost REAL," +
			                                                "quantity INTEGER," +
									"PRIMARY KEY(order_number, stock_number, customer_ID)," +
									"FOREIGN KEY(customer_ID) REFERENCES Customer," +
									"FOREIGN KEY(stock_number) REFERENCES Product)";
			rs = st.executeQuery(createTable);
			System.out.println("Orders table created");

			System.out.println("Initializing eDEPOT tables");
			
			//Product Depot Table
			createTable = 		"CREATE TABLE ProductDepot(stock_number CHAR(7)," +
									  "manufacturer CHAR(20)," +
									  "model_number CHAR(20)," +
									  "quantity INTEGER," + 
						 			  "min_stock_level INTEGER," + 
						 			  "max_stock_level INTEGER," + 
									  "location CHAR(20)," +  
									  "replenishment INTEGER," + 
						 			  "PRIMARY KEY(stock_number))";
			rs = st.executeQuery(createTable);
			System.out.println("ProductDepot table created");

			//Shipping Notice Table
			createTable = 		"CREATE TABLE ShippingNotice( shipID CHAR(20)," +
									     "manufacturer CHAR(20)," + 
									     "PRIMARY KEY (shipID))";
			rs = st.executeQuery(createTable);
			System.out.println("ShippingNotice table created");

			//Created Shipped Items Table
			createTable = 		"CREATE TABLE ShippedItems(model_number CHAR(20)," +
			                                                   "manufacturer CHAR(20)," + 
									   "quantity INTEGER," +  
									   "shipID CHAR(20)," +  
									   "PRIMARY KEY (model_number, shipID)," +  
									   "FOREIGN KEY (shipID) REFERENCES ShippingNotice)";
			rs = st.executeQuery(createTable);
			System.out.println("ShippedItems table created");


			
			//Inserting the 1st Product into Tables
			String insertData = "INSERT INTO Product(stock_number, Price, manufacturer_name, model_number, category, warranty) VALUES ('ab00101', 1630, 'HP', '6111', 'Laptop', 12)";
			rs = st.executeQuery(insertData);

			insertData = "INSERT INTO Description(attribute, value, stock_number) VALUES ('Processor speed', '3.33GHz', 'ab00101')";
			rs = st.executeQuery(insertData);

			insertData = "INSERT INTO Description(attribute, value, stock_number) VALUES ('RAM size', '512 Mb', 'ab00101')";
			rs = st.executeQuery(insertData);

			insertData = "INSERT INTO Description(attribute, value, stock_number) VALUES ('Hard disk size', '100Gb', 'ab00101')";
			rs = st.executeQuery(insertData);

			insertData = "INSERT INTO Description(attribute, value, stock_number) VALUES ('Display size', '17', 'ab00101')";
			rs = st.executeQuery(insertData);

			insertData = "INSERT INTO ProductDepot(stock_number, manufacturer, model_number, quantity, min_stock_level, max_stock_level, location, replenishment) VALUES ('ab00101', 'HP', '6111', 2, 1, 10, 'A9', 0)";
			rs = st.executeQuery(insertData);
			System.out.println("Inserted product 1");
			
			
			//Inserting 2nd Product into Tables
			insertData = "INSERT INTO Product(stock_number, Price, manufacturer_name, model_number, category, warranty) VALUES ('ab00201', 239, 'Dell', '420', 'Desktop', 12)";
			rs = st.executeQuery(insertData);

			insertData = "INSERT INTO Description(attribute, value, stock_number) VALUES ('Processor speed', '2.53GHz', 'ab00201')";
			rs = st.executeQuery(insertData);

			insertData = "INSERT INTO Description(attribute, value, stock_number) VALUES ('RAM size', '256 Mb', 'ab00201')";
			rs = st.executeQuery(insertData);

			insertData = "INSERT INTO Description(attribute, value, stock_number) VALUES ('Hard disk size', '80Gb', 'ab00201')";
			rs = st.executeQuery(insertData);

			insertData = "INSERT INTO Description(attribute, value, stock_number) VALUES ('OS', 'none', 'ab00201')";
			rs = st.executeQuery(insertData);

			insertData = "INSERT INTO ProductDepot(stock_number, manufacturer, model_number, quantity, min_stock_level, max_stock_level, location, replenishment) VALUES ('ab00201', 'Dell', '420', 3, 2, 15, 'A7', 0)";
			rs = st.executeQuery(insertData);
			System.out.println("Inserted product 2");
	
			

			//Inserting the 3rd Product
			insertData = "INSERT INTO Product(stock_number, Price, manufacturer_name, model_number, category, warranty) VALUES ('ab00202', 369.99, 'Emachine', '3958', 'Desktop', 12)";
			rs = st.executeQuery(insertData);

			insertData = "INSERT INTO Description(attribute, value, stock_number) VALUES ('Processor speed', '2.9GHz', 'ab00202')";
			rs = st.executeQuery(insertData);

			insertData = "INSERT INTO Description(attribute, value, stock_number) VALUES ('RAM size', '512 Mb', 'ab00202')";
			rs = st.executeQuery(insertData);

			insertData = "INSERT INTO Description(attribute, value, stock_number) VALUES ('Hard disk size', '80Gb', 'ab00202')";
			rs = st.executeQuery(insertData);

			insertData = "INSERT INTO ProductDepot(stock_number, manufacturer, model_number, quantity, min_stock_level, max_stock_level, location, replenishment) VALUES ('ab00202', 'Emachine', '3958', 4, 2, 8, 'B52', 0)";
			rs = st.executeQuery(insertData);
			System.out.println("Inserted product 3");




			//Inserting the 4th Product
			insertData = "INSERT INTO Product(stock_number, Price, manufacturer_name, model_number, category, warranty) VALUES ('ab00301', 69.99, 'Envision', '720', 'Monitor', 36)";
			rs = st.executeQuery(insertData);

			insertData = "INSERT INTO Description(attribute, value, stock_number) VALUES ('Size', '17', 'ab00301')";
			rs = st.executeQuery(insertData);

			insertData = "INSERT INTO Description(attribute, value, stock_number) VALUES ('Weight', '25 lb.', 'ab00301')";
			rs = st.executeQuery(insertData);

			insertData = "INSERT INTO Accessory(parent_stock_number, child_stock_number) VALUES ('ab00201', 'ab00301')";
			rs = st.executeQuery(insertData);

			insertData = "INSERT INTO Accessory(parent_stock_number, child_stock_number) VALUES ('ab00202', 'ab00301')";
			rs = st.executeQuery(insertData);

			insertData = "INSERT INTO ProductDepot(stock_number, manufacturer, model_number, quantity, min_stock_level, max_stock_level, location, replenishment) VALUES ('ab00301', 'Envision', '720', 4, 3, 6, 'C27', 0)";
			rs = st.executeQuery(insertData);
			System.out.println("Inserted product 4");



			//Inserting the 5th Product
			insertData = "INSERT INTO Product(stock_number, Price, manufacturer_name, model_number, category, warranty) VALUES ('ab00302', 279.99, 'Samsung', '712', 'Monitor', 36)";
			rs = st.executeQuery(insertData);

			insertData = "INSERT INTO Description(attribute, value, stock_number) VALUES ('Size', '17', 'ab00302')";
			rs = st.executeQuery(insertData);

			insertData = "INSERT INTO Description(attribute, value, stock_number) VALUES ('Weight', '9.6 lb.', 'ab00302')";
			rs = st.executeQuery(insertData);

			insertData = "INSERT INTO Accessory(parent_stock_number, child_stock_number) VALUES ('ab00201','ab00302')";
			rs = st.executeQuery(insertData);

			insertData = "INSERT INTO Accessory(parent_stock_number, child_stock_number) VALUES ('ab00202','ab00302')";
			rs = st.executeQuery(insertData);

			insertData = "INSERT INTO ProductDepot(stock_number, manufacturer, model_number, quantity, min_stock_level, max_stock_level, location, replenishment) VALUES ('ab00302', 'Samsung', '712', 4, 3, 6, 'C13', 0)";
			rs = st.executeQuery(insertData);
			System.out.println("Inserted product 5");



			//Inserting the 6th Product
			insertData = "INSERT INTO Product(stock_number, Price, manufacturer_name, model_number, category, warranty) VALUES ('ab00401', 19.99, 'Symantec', '2005', 'Software', 60)";
			rs = st.executeQuery(insertData);

			insertData = "INSERT INTO Description(attribute, value, stock_number) VALUES ('Required disk size', '128 Mb', 'ab00401')";
			rs = st.executeQuery(insertData);

			insertData = "INSERT INTO Description(attribute, value, stock_number) VALUES ('Required RAM size', '64 Mb', 'ab00401')";
			rs = st.executeQuery(insertData);

			insertData = "INSERT INTO Accessory(parent_stock_number, child_stock_number) VALUES ('ab00101','ab00401')";
			rs = st.executeQuery(insertData);
			
			insertData = "INSERT INTO Accessory(parent_stock_number, child_stock_number) VALUES ('ab00201','ab00401')";
			rs = st.executeQuery(insertData);

			insertData = "INSERT INTO Accessory(parent_stock_number, child_stock_number) VALUES ('ab00202','ab00401')";
			rs = st.executeQuery(insertData);

			insertData = "INSERT INTO ProductDepot(stock_number, manufacturer, model_number, quantity, min_stock_level, max_stock_level, location, replenishment) VALUES ('ab00401', 'Symantec', '2005', 7, 5, 9, 'D27', 0)";
			rs = st.executeQuery(insertData);
			System.out.println("Inserted product 6");



			//Inserting the 7th Product
			insertData = "INSERT INTO Product(stock_number, Price, manufacturer_name, model_number, category, warranty) VALUES ('ab00402', 19.99, 'Mcafee', '2005', 'Software', 60)";
			rs = st.executeQuery(insertData);

			insertData = "INSERT INTO Description(attribute, value, stock_number) VALUES ('Required disk size', '128 Mb', 'ab00402')";
			rs = st.executeQuery(insertData);

			insertData = "INSERT INTO Description(attribute, value, stock_number) VALUES ('Required RAM size', '64 Mb', 'ab00402')";
			rs = st.executeQuery(insertData);

			insertData = "INSERT INTO Accessory(parent_stock_number, child_stock_number) VALUES ('ab00101','ab00402')";
			rs = st.executeQuery(insertData);
			
			insertData = "INSERT INTO Accessory(parent_stock_number, child_stock_number) VALUES ('ab00201','ab00402')";
			rs = st.executeQuery(insertData);

			insertData = "INSERT INTO Accessory(parent_stock_number, child_stock_number) VALUES ('ab00202','ab00402')";
			rs = st.executeQuery(insertData);

			insertData = "INSERT INTO ProductDepot(stock_number, manufacturer, model_number, quantity, min_stock_level, max_stock_level, location, replenishment) VALUES ('ab00402', 'Mcafee', '2005', 7, 5, 9, 'D1', 0)";
			rs = st.executeQuery(insertData);
			System.out.println("Inserted product 7");



			//Inserting the 8th Product
			insertData = "INSERT INTO Product(stock_number, Price, manufacturer_name, model_number, category, warranty) VALUES ('ab00501', 299.99, 'HP', '1320', 'Printer', 12)";
			rs = st.executeQuery(insertData);

			insertData = "INSERT INTO Description(attribute, value, stock_number) VALUES ('Resolution', '1200 dpi', 'ab00501')";
			rs = st.executeQuery(insertData);

			insertData = "INSERT INTO Description(attribute, value, stock_number) VALUES ('Sheet capacity', '500', 'ab00501')";
			rs = st.executeQuery(insertData);

			insertData = "INSERT INTO Description(attribute, value, stock_number) VALUES ('Weight', '0.4 lb', 'ab00501')";
			rs = st.executeQuery(insertData);

			insertData = "INSERT INTO Accessory(parent_stock_number, child_stock_number) VALUES ('ab00201','ab00501')";
			rs = st.executeQuery(insertData);
			
			insertData = "INSERT INTO Accessory(parent_stock_number, child_stock_number) VALUES ('ab00202','ab00501')";
			rs = st.executeQuery(insertData);

			insertData = "INSERT INTO ProductDepot(stock_number, manufacturer, model_number, quantity, min_stock_level, max_stock_level, location, replenishment) VALUES ('ab00501', 'HP', '1320', 3, 2, 5, 'E7', 0)";
			rs = st.executeQuery(insertData);
			System.out.println("Inserted product 8");



			//Inserting the 9th Product
			insertData = "INSERT INTO Product(stock_number, Price, manufacturer_name, model_number, category, warranty) VALUES ('ab00601', 119.99, 'HP', '435', 'Camera', 3)";
			rs = st.executeQuery(insertData);

			insertData = "INSERT INTO Description(attribute, value, stock_number) VALUES ('Resolution', '3.1 Mp', 'ab00601')";
			rs = st.executeQuery(insertData);

			insertData = "INSERT INTO Description(attribute, value, stock_number) VALUES ('Max zoom', '5 times', 'ab00601')";
			rs = st.executeQuery(insertData);

			insertData = "INSERT INTO Description(attribute, value, stock_number) VALUES ('Weight', '24.7 lb', 'ab00601')";
			rs = st.executeQuery(insertData);

			insertData = "INSERT INTO Accessory(parent_stock_number, child_stock_number) VALUES ('ab00201','ab00601')";
			rs = st.executeQuery(insertData);
			
			insertData = "INSERT INTO Accessory(parent_stock_number, child_stock_number) VALUES ('ab00202','ab00601')";
			rs = st.executeQuery(insertData);

			insertData = "INSERT INTO ProductDepot(stock_number, manufacturer, model_number, quantity, min_stock_level, max_stock_level, location, replenishment) VALUES ('ab00601', 'HP', '435', 3, 2, 9, 'F9', 0)";
			rs = st.executeQuery(insertData);
			System.out.println("Inserted product 9");



			//Inserting the 10th Product
			insertData = "INSERT INTO Product(stock_number, Price, manufacturer_name, model_number, category, warranty) VALUES ('ab00602', 329.99, 'Cannon', '738', 'Camera', 1)";
			rs = st.executeQuery(insertData);

			insertData = "INSERT INTO Description(attribute, value, stock_number) VALUES ('Resolution', '3.1 Mp', 'ab00602')";
			rs = st.executeQuery(insertData);

			insertData = "INSERT INTO Description(attribute, value, stock_number) VALUES ('Max zoom', '5 times', 'ab00602')";
			rs = st.executeQuery(insertData);

			insertData = "INSERT INTO Description(attribute, value, stock_number) VALUES ('Weight', '24.7 lb', 'ab00602')";
			rs = st.executeQuery(insertData);

			insertData = "INSERT INTO Accessory(parent_stock_number, child_stock_number) VALUES ('ab00201','ab00602')";
			rs = st.executeQuery(insertData);
			
			insertData = "INSERT INTO Accessory(parent_stock_number, child_stock_number) VALUES ('ab00202','ab00602')";
			rs = st.executeQuery(insertData);

			insertData = "INSERT INTO ProductDepot(stock_number, manufacturer, model_number, quantity, min_stock_level, max_stock_level, location, replenishment) VALUES ('ab00602', 'Cannon', '738', 3, 2, 5, 'F3', 0)";
			rs = st.executeQuery(insertData);
			System.out.println("Inserted product 10");



//Customer Table
//createTable = "CREATE TABLE Customer(customer_ID CHAR(20), password CHAR(20), name CHAR
//(20), email CHAR(20), address CHAR(20), isManager INTEGER, status CHAR(20), PRIMARY KEY 
//(customer_ID))";
			

			//Inserting the 1st User
			insertData = "INSERT INTO Customer(customer_ID, password, name, email, address, isManager, status) VALUES ('Rhagrid', 'Rhagrid', 'Rubeus Hagrid', 'rhagrid@cs', '123 MyStreet, Goleta apt A, Ca', 'false', 'Gold')";
			rs = st.executeQuery(insertData);
			System.out.println("Inserted User 1");

			//Inserting the 2nd User
			insertData = "INSERT INTO Customer(customer_ID, password, name, email, address, isManager, status) VALUES ('Mhooch', 'Mhooch', 'Madam Hooch', 'mhooch@cs', '123 MyStreet, Goleta apt B, Ca', 'false', 'Silver')";
			rs = st.executeQuery(insertData);
			System.out.println("Inserted User 2");

			//Inserting the 3rd User
			insertData = "INSERT INTO Customer(customer_ID, password, name, email, address, isManager, status) VALUES ('Amoody', 'Amoody', 'Alastor Moody', 'amoody@cs', '123 MyStreet, Goleta apt C, Ca', 'false', 'New')";
			rs = st.executeQuery(insertData);
			System.out.println("Inserted User 3");

			//Inserting the 4th User
			insertData = "INSERT INTO Customer(customer_ID, password, name, email, address, isManager, status) VALUES ('Pquirrell', 'Pquirrell', 'Professor Quirrell', 'pquirrell@cs', '123 MyStreet, Goleta apt D, Ca', 'false', 'New')";
			rs = st.executeQuery(insertData);
			System.out.println("Inserted User 4");

			//Inserting the 5th User
			insertData = "INSERT INTO Customer(customer_ID, password, name, email, address, isManager, status) VALUES ('Sblack', 'Sblack', 'Sirius Black', 'sblack@cs', '123 MyStreet, Goleta apt E, Ca', 'true', 'Green')";
			rs = st.executeQuery(insertData);
			System.out.println("Inserted User 5");

			//Inserting the 5th User
			insertData = "INSERT INTO Customer(customer_ID, password, name, email, address, isManager, status) VALUES ('Ddiggle', 'Ddiggle', 'Dedalus Diggle', 'ddiggle@cs', '123 MyStreet, Goleta apt F, Ca', 'false', 'Green')";
			rs = st.executeQuery(insertData);
			System.out.println("Inserted User 6");


	
		} catch (Exception e) {
			System.out.println("Error.");
			System.exit(0);
			
		}
    	}

	public void destroyData()
	{
		try {
			Statement st = conn.createStatement();
			String deleteTable = "Drop table Accessory";
			ResultSet rs = st.executeQuery(deleteTable);

			deleteTable = "Drop table Description";
			rs = st.executeQuery(deleteTable);

			deleteTable = "Drop table ShoppingCart";
			rs = st.executeQuery(deleteTable);

			deleteTable = "Drop table Orders";
			rs = st.executeQuery(deleteTable);

			deleteTable = "Drop table Customer";
			rs = st.executeQuery(deleteTable);

			deleteTable = "Drop table Product";
			rs = st.executeQuery(deleteTable);

			deleteTable = "Drop table ShippedItems";
			rs = st.executeQuery(deleteTable);
	
			deleteTable = "Drop table ShippingNotice";
			rs = st.executeQuery(deleteTable);

			deleteTable = "Drop table ProductDepot";
			rs = st.executeQuery(deleteTable);
			System.out.println("Tables deleted");
	

		} catch (Exception e) {
		}
	}

	public Connection getConnection() {
		return conn;
	}

	public static void main(String [] args) throws SQLException
	{
		
		DataInitialization thing = new DataInitialization();
		if (args[0].equals("destroy"))
		{	
			thing.destroyData();
		}
		else if (args[0].equals("init"))
		{
			thing.initialData();
		}
	}
}
