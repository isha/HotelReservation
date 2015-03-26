package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Properties;

import Implementations.DatabaseManager;
import Implementations.QueryManager;

public class App {

	public static void main(String[] args) throws Exception {
		DatabaseManager db = new DatabaseManager();
		db.runSQLScript("setup.sql");
		
		/** Example Create Runs *//*
		db.createCustomer("Isha", "7865652831", "MySuperSecretPassword");
		db.createEmployee(345, "Aaron J", "employeeyuhhkb");
		db.createRoomType("single-1", 130, 345);*/
		
		/** Example Selects */
		
		
		/** Example Updates */
		
		/** Example Deletes */
		/*
		db.deleteCustomer("Mal Mallard", "4069089820");
		db.deleteEmployee(3);
		db.deleteEmployee(11234);
		db.deleteRoom(303, 1260, "Bidwell Street", "V6G 2L2");
		db.deleteRoomType("king");
		db.deleteReservation(11300);
		db.deleteCreditCard("5555555555554444");

		QueryManager qm = new QueryManager();
		Calendar startDate = Calendar.getInstance();
		startDate.add(Calendar.YEAR, -2);
		Calendar endDate = Calendar.getInstance();
		endDate.add(Calendar.YEAR, 1);
		qm.getMostPopularRoomType(startDate, endDate);
		qm.getBestEmployee(startDate, endDate);
		qm.getWorstEmployee(startDate, endDate);
		qm.getReservations("Jimmy Joy", "5139087893", true, true, true, false);
		qm.getValuedCustomers();
		qm.getCustomer("Jimmy Joy", "5139087893", "joy_7893_pw");
		qm.getEmployee(1, "password1");
		qm.getAverageStayDuration(startDate, endDate);*/
	}
}