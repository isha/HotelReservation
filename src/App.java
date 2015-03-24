
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import Implementations.DatabaseManager;

public class App {

	public static void main(String[] args) throws Exception {
		DatabaseManager db = new DatabaseManager();
		db.runSQLScript("setup.sql");
		
		/** Example Create Runs */
		db.createCustomer("Isha", "7865652831", "MySuperSecretPassword");
		db.createEmployee(345, "Aaron J", "employeeyuhhkb");
		db.createRoomType("single-1", 130, 345);
		
		/** Example Selects */
		
		
		/** Example Updates */
		
		/** Example Deletes */
	}
}