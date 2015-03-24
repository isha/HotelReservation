package Implementations;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import data_classes.CreditCard;
import data_classes.Customer;
import data_classes.Employee;
import data_classes.Location;
import data_classes.Reservation;
import data_classes.Room;
import data_classes.RoomType;
import Interfaces.IQueryManager;

public class MockQueryManager implements IQueryManager{

	public List<Room> getOccupiedRooms(Calendar date) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Customer> getCurrentCustomers(Calendar date) {
		// TODO Auto-generated method stub
		return null;
	}

	public int getAverageStayDuration(Calendar startDate, Calendar endDate) {
		// TODO Auto-generated method stub
		return 0;
	}

	public RoomType getMostPopularRoomType(Calendar startDate, Calendar endDate) {
		// TODO Auto-generated method stub
		return null;
	}

	public Employee getBestEmployee(Calendar startDate, Calendar endDate) {
		// TODO Auto-generated method stub
		return null;
	}

	public Employee getWorstEmployee(Calendar startDate, Calendar endDate) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Customer> getValuedCustomers(int reservationNumberThreshold) {
		// TODO Auto-generated method stub
		return null;
	}

	public void deleteReservation(Reservation reservationToDelete) {
		// TODO Auto-generated method stub
		
	}

	public void updateCustomerPassword(Customer customer, String newPassword) {
		Connection conn = null;
		try {
			conn = DatabaseManager.getConnection();
			System.out.println("Connected to database");
		} catch (SQLException e) {
			System.out.println("ERROR: Could not connect to the database");
			e.printStackTrace();
		}

		try {
		    String createString =
			        "Update Customer SET password='"+newPassword+"' WHERE name='"+
			        customer.getName()+"' AND phone_number='"+customer.getPhoneNumber()+"';";
			DatabaseManager.executeUpdate(conn, createString);
			System.out.println("Updated Customer password");
	    } catch (SQLException e) {
			System.out.println("ERROR: Could not update Customer password");
			e.printStackTrace();
		}
	}

	public void updateEmployeePassword(Employee employee, String newPassword) {
		Connection conn = null;
		try {
			conn = DatabaseManager.getConnection();
			System.out.println("Connected to database");
		} catch (SQLException e) {
			System.out.println("ERROR: Could not connect to the database");
			e.printStackTrace();
		}

		try {
		    String createString =
			        "Update Employee SET password='"+newPassword+"' WHERE eid='"+
			        employee.getId()+"';";
			DatabaseManager.executeUpdate(conn, createString);
			System.out.println("Updated Employee password");
	    } catch (SQLException e) {
			System.out.println("ERROR: Could not update Employee password");
			e.printStackTrace();
		}
		
	}

	public void updateReservationRoom(Reservation reservation, Room newRoom) {
		// TODO Auto-generated method stub
		
	}

	public Customer getCustomer(String name, String phone_number) {
		return new Customer(name, phone_number, "password");
	}

	public List<Reservation> getReservations(Customer customer) {
		List<Reservation> reservations = new ArrayList<Reservation>();
		
		Location fakeLocation = new Location(1234, "Prince Albert", "V6G2L2", "Vancouver", "BC");
		RoomType fakeType = new RoomType("Big Room", 10000, 100);
		Room fakeRoom = new Room(1, fakeType, fakeLocation);
		Calendar fakeIn = new GregorianCalendar(1992, 1, 12);
		Calendar fakeOut = new GregorianCalendar(1992, 1, 16);
		
		CreditCard fakeCard = new CreditCard("1234567891019", fakeOut, fakeLocation);
		Employee fakeEmployee = new Employee(123, "John", "password");
		
		reservations.add(new Reservation(123, fakeIn, fakeOut, "Great place!", fakeRoom, customer, fakeCard, fakeEmployee));
		reservations.add(new Reservation(126, fakeIn, fakeOut, "Great place!", fakeRoom, customer, fakeCard, fakeEmployee));
		reservations.add(new Reservation(129, fakeIn, fakeOut, "Great place!", fakeRoom, customer, fakeCard, fakeEmployee));
		reservations.add(new Reservation(1200, fakeIn, fakeOut, "Great place!", fakeRoom, customer, fakeCard, fakeEmployee));
		
		return reservations;
	}

}
