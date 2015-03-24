
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class App {

	public static void main(String[] args) throws Exception {
		DatabaseManager db = new DatabaseManager();
		db.runSQLScript("setup.sql");
		
		/** Example Create Runs */
		db.createCustomer("Isha", "7865652831", "MySuperSecretPassword");
		db.createEmployee(345, "Aaron J", "employeeyuhhkb");
		db.createRoomType("single", 130, 345);
		// TODO: Below don't work!
		db.createRoom(101, "single", 2987, "Hudwell St.", "V67 G6H");
		
		/** Example Selects */
		
		
		/** Example Updates */
		
		/** Example Deletes */
	}
}