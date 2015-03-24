package Implementations;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.apache.ibatis.jdbc.ScriptRunner;

import data_classes.Customer;
import data_classes.Employee;
import data_classes.Room;
import data_classes.RoomType;


public class DatabaseManager {
	/** DB settings */
	private static final String userName = "hotel_user";
	private static final String password = "hotel_pass";
	private static final String serverName = "localhost";
	private static final int portNumber = 3306;
	private static final String dbName = "test";
	private static final String tableName = "JDBC_TEST";
	
	/**
	 * Get a new database connection
	 * 
	 * @return
	 * @throws SQLException
	 */
	public static Connection getConnection() throws SQLException {
		Connection conn = null;
		Properties connectionProps = new Properties();
		connectionProps.put("user", userName);
		connectionProps.put("password", password);

		conn = DriverManager.getConnection("jdbc:mysql://"
				+ serverName + ":" + portNumber + "/" + dbName,
				connectionProps);

		return conn;
	}
	
	/**
	 * Run a SQL command which does not return a recordset:
	 * CREATE/INSERT/UPDATE/DELETE/DROP/etc.
	 * 
	 * @throws SQLException If something goes wrong
	 */
	public static boolean executeUpdate(Connection conn, String command) throws SQLException {
	    Statement stmt = null;
	    try {
	        stmt = conn.createStatement();
	        stmt.executeUpdate(command); // This will throw a SQLException if it fails
	        return true;
	    } finally {

	    	// This will run whether we throw an exception or not
	        if (stmt != null) { stmt.close(); }
	    }
	}
	
	public Customer createCustomer(String name, String phone_number, String password) {
		Connection conn = null;
		try {
			conn = this.getConnection();
			System.out.println("Connected to database");
		} catch (SQLException e) {
			System.out.println("ERROR: Could not connect to the database");
			e.printStackTrace();
			return null;
		}
		
		try {
		    String createString =
			        "INSERT INTO Customer VALUES ('"+name+"', '"+phone_number
			        +"', '"+password+"');";
			this.executeUpdate(conn, createString);
			System.out.println("Inserted Customer record");
	    } catch (SQLException e) {
			System.out.println("ERROR: Could not insert record");
			e.printStackTrace();
			return null;
		}
		return new Customer(name, phone_number, password);
	}

	public void runSQLScript(String filename) throws Exception {
		Connection conn = null;
		try {
			conn = this.getConnection();
			System.out.println("Connected to database");
		} catch (SQLException e) {
			System.out.println("ERROR: Could not connect to the database");
			e.printStackTrace();
		}
		
		ScriptRunner runner = new ScriptRunner(conn);
		InputStreamReader reader = new InputStreamReader(new FileInputStream(filename));
		runner.runScript(reader);
		reader.close();
		conn.close();
		
	}

	public Employee createEmployee(int id, String name, String password) {
		Connection conn = null;
		try {
			conn = this.getConnection();
			System.out.println("Connected to database");
		} catch (SQLException e) {
			System.out.println("ERROR: Could not connect to the database");
			e.printStackTrace();
			return null;
		}
		
		try {
		    String createString =
			        "INSERT INTO Employee VALUES ("+id+", '"+name
			        +"', '"+password+"');";
			this.executeUpdate(conn, createString);
			System.out.println("Inserted Employee record");
	    } catch (SQLException e) {
			System.out.println("ERROR: Could not insert record");
			e.printStackTrace();
			return null;
		}
		return new Employee(id, name, password);
		
	}

	public RoomType createRoomType(String type, int sec_deposit, int daily_rate) {
		Connection conn = null;
		try {
			conn = this.getConnection();
			System.out.println("Connected to database");
		} catch (SQLException e) {
			System.out.println("ERROR: Could not connect to the database");
			e.printStackTrace();
			return null;
		}
		
		try {
		    String createString =
			        "INSERT INTO RoomType VALUES ('"+type+"', "+sec_deposit
			        +", "+daily_rate+");";
			this.executeUpdate(conn, createString);
			System.out.println("Inserted RoomType record");
	    } catch (SQLException e) {
			System.out.println("ERROR: Could not insert record");
			e.printStackTrace();
			return null;
		}
		return new RoomType(type, sec_deposit, daily_rate);
		
	}

	public void createRoom(int r_number, String type, int address_no, String street,
			String postal_code) {
		Connection conn = null;
		try {
			conn = this.getConnection();
			System.out.println("Connected to database");
		} catch (SQLException e) {
			System.out.println("ERROR: Could not connect to the database");
			e.printStackTrace();
		}
		
		try {
		    String createString =
			        "INSERT INTO Room VALUES ("+r_number+", '"+type
			        +"', "+address_no+", '"+street+"', '"+postal_code+"');";
			this.executeUpdate(conn, createString);
			System.out.println("Inserted Room record");
	    } catch (SQLException e) {
			System.out.println("ERROR: Could not insert record");
			e.printStackTrace();
		}
		
	}

}
