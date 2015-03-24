
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class App {

	public static void main(String[] args) throws Exception {
		DatabaseManager db = new DatabaseManager();
		db.runSQLScript("setup.sql");
		db.createCustomer("Isha", "7865652831", "MySuperSecretPassword");
	}
}