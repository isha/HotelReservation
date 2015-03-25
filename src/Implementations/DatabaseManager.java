package Implementations;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.ibatis.jdbc.ScriptRunner;

import data_classes.Customer;
import data_classes.Employee;
import data_classes.Location;
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
	 * Run a SQL command which returns a recordset:
	 * 
	 * @throws SQLException If something goes wrong
	 */
	public static List<Customer> executeReadCustomer (String command) throws SQLException {
		List<Customer> result_records = new ArrayList<Customer>();
		
		Connection conn = DatabaseManager.getConnection();
	    Statement stmt = null;
	    
	    try {
	        stmt = conn.createStatement();
	        ResultSet rs = stmt.executeQuery(command);
	        while (rs.next()) {
	            result_records.add(new Customer(rs.getString("name"), rs.getString("phone_number"), rs.getString("password")));
	        }
	    } finally {
	        if (stmt != null) { stmt.close(); }
	        if (conn != null) { conn.close(); }
	    }
	    
		return result_records;
	}
	
	public static List<RoomType> executeReadRoomType (String command) throws SQLException {
		List<RoomType> result_records = new ArrayList<RoomType>();
		
		Connection conn = DatabaseManager.getConnection();
	    Statement stmt = null;
	    
	    try {
	        stmt = conn.createStatement();
	        ResultSet rs = stmt.executeQuery(command);
	        while (rs.next()) {
	            result_records.add(new RoomType(rs.getString("type"), rs.getInt("securityDeposit"), rs.getInt("daily_rate")));
	        }
	    } finally {
	        if (stmt != null) { stmt.close(); }
	        if (conn != null) { conn.close(); }
	    }
	    
		return result_records;
	}
	
	public static List<Employee> executeReadEmployee (String command) throws SQLException {
		List<Employee> result_records = new ArrayList<Employee>();
			
		Connection conn = DatabaseManager.getConnection();
		Statement stmt = null;
		      
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(command);
			while (rs.next()) {
				result_records.add(new Employee(rs.getInt("eid"), rs.getString("name"), rs.getString("password")));
			}
		} finally {
			if (stmt != null) { stmt.close(); }
			if (conn != null) { conn.close(); }
		}
      
		return result_records;
	}
	
	public static List<Room> executeReadRoom (String command) throws SQLException {
		List<Room> result_records = new ArrayList<Room>();
			
		Connection conn = DatabaseManager.getConnection();
		Statement stmt = null;
		      
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(command);
			while (rs.next()) {
				result_records.add(new Room(rs.getInt("r_number"), 
											new RoomType(rs.getString("type"), rs.getInt("securityDeposit"), rs.getInt("daily_rate")), 
											new Location(rs.getInt("address_no"), rs.getString("street"), rs.getString("postal_code"), rs.getString("city"),rs.getString("province"))
										   )
								  );
			}
		} finally {
			if (stmt != null) { stmt.close(); }
			if (conn != null) { conn.close(); }
		}
      
		return result_records;
	}
	
	
	/**
	 * Run a SQL command which does not return a recordset:
	 * CREATE/INSERT/UPDATE/DELETE/DROP/etc.
	 * 
	 * @throws SQLException If something goes wrong
	 */
	public static boolean executeUpdate(String command) throws SQLException {
	    Statement stmt = null;
	    Connection conn = null;
	    try {
	    	conn = DatabaseManager.getConnection();
	        stmt = conn.createStatement();
	        stmt.executeUpdate(command); // This will throw a SQLException if it fails
	        return true;
	    } finally {

	    	// This will run whether we throw an exception or not
	        if (stmt != null) { stmt.close(); }
	        if (conn != null) { conn.close(); }
	    }
	}
	
	public Customer createCustomer(String name, String phone_number, String password) {
		try {
		    String createString =
			        "INSERT INTO Customer VALUES ('"+name+"', '"+phone_number
			        +"', '"+password+"');";
			DatabaseManager.executeUpdate(createString);
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
			conn = DatabaseManager.getConnection();
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
		try {
		    String createString =
			        "INSERT INTO Employee VALUES ("+id+", '"+name
			        +"', '"+password+"');";
			DatabaseManager.executeUpdate(createString);
			System.out.println("Inserted Employee record");
	    } catch (SQLException e) {
			System.out.println("ERROR: Could not insert record");
			e.printStackTrace();
			return null;
		}
		return new Employee(id, name, password);
		
	}

	public RoomType createRoomType(String type, int sec_deposit, int daily_rate) {
		try {
		    String createString =
			        "INSERT INTO RoomType VALUES ('"+type+"', "+sec_deposit
			        +", "+daily_rate+");";
			DatabaseManager.executeUpdate(createString);
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
		try {
		    String createString =
			        "INSERT INTO Room VALUES ("+r_number+", '"+type
			        +"', "+address_no+", '"+street+"', '"+postal_code+"');";
		    DatabaseManager.executeUpdate(createString);
			System.out.println("Inserted Room record");
	    } catch (SQLException e) {
			System.out.println("ERROR: Could not insert record");
			e.printStackTrace();
		}
		
	}
	
	//DELETE methods go here

	public void deleteCustomer(String name, String phone_number){
		try{
			String deleteString =
					"DELETE FROM Customer WHERE name = ('"+name+"') AND phone_number = ('"+phone_number+"');";
			DatabaseManager.executeUpdate(deleteString);
			System.out.println("Deleted Customer record for name = " + name);
		} catch (SQLException e) {
			System.out.println("ERROR: Could not delete record for name = "+ name);
			e.printStackTrace();
			return;
		}
	}

	public void deleteEmployee(int eid){
		try{
			String deleteString =
					"DELETE FROM Employee WHERE eid = ('"+eid+"');";
			DatabaseManager.executeUpdate(deleteString);
			System.out.println("Deleted Employee record for eid = " + eid);
		} catch (SQLException e) {
			System.out.println("ERROR: Could not delete record for eid = " + eid);
			e.printStackTrace();
			return;
		}
	}

	public void deleteRoom(int r_number, int address_no, String street, String postal_code){
		try{
			String deleteString =
					"DELETE FROM Room WHERE r_number = ('"+r_number+"') AND address_no = ('"+address_no+"')	AND street = ('"+street+"') AND postal_code = ('"+postal_code+"');";
			DatabaseManager.executeUpdate(deleteString);
			System.out.println("Deleted Room record");
		} catch (SQLException e) {
			System.out.println("ERROR: Could not delete record");
			e.printStackTrace();
			return;
		}
	}
	
	public void deleteRoomType(String type){
		try{
			String deleteString =
					"DELETE FROM RoomType WHERE type = ('"+type+"');";
			DatabaseManager.executeUpdate(deleteString);
			System.out.println("Deleted RoomType record for type = " + type);
		} catch (SQLException e) {
			System.out.println("ERROR: Could not delete record");
			e.printStackTrace();
			return;
		}
	}
	
	public void deleteReservation(int conf_no){
		try{
			String deleteString =
					"DELETE FROM Reservation WHERE conf_no = ('"+conf_no+"');";
			DatabaseManager.executeUpdate(deleteString);
			System.out.println("Deleted Reservation record for conf_no = " + conf_no);
		} catch (SQLException e) {
			System.out.println("ERROR: Could not delete record");
			e.printStackTrace();
			return;
		}
	}
	
	public void deleteCreditCard(String cc_number){
		try{
			String deleteString =
					"DELETE FROM CreditCard WHERE cc_number = ('"+cc_number+"');";
			DatabaseManager.executeUpdate(deleteString);
			System.out.println("Deleted Reservation record for cc_number = " + cc_number);
		} catch (SQLException e) {
			System.out.println("ERROR: Could not delete record");
			e.printStackTrace();
			return;
		}
	}
}
