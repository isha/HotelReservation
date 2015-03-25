package main;

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
		db.deleteCustomer("Mal Mallard", "4069089820");
		db.deleteEmployee(3);
		db.deleteEmployee(11234);
		db.deleteRoom(303, 1260, "Bidwell Street", "V6G 2L2");
		db.deleteRoomType("king");
		db.deleteReservation(11300);
		db.deleteCreditCard("5555555555554444");
	}
}