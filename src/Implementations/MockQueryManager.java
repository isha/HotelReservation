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

	public List<Room> getOccupiedRooms() {
		List<Room> rooms = new ArrayList<Room>();
		RoomType type1 = new RoomType("Single", 1000, 200);
		RoomType type2 = new RoomType("Double", 1250, 400);
		Location loc1 = new Location(767, "Albert St.", "V6V7G7", "Vancouver", "BC");
		Location loc2 = new Location(887, "King  St.", "K9V7G9", "Calgary", "AB");
		rooms.add(new Room(123, type1, loc1));
		rooms.add(new Room(123, type2, loc2));
		rooms.add(new Room(123, type1, loc2));
		rooms.add(new Room(123, type2, loc1));
		return rooms;
	}

	public RoomType getMostPopularRoomType(Calendar startDate, Calendar endDate) {
		return new RoomType("Single", 1000, 200);
	}
	
	public List<Customer> getCurrentCustomers() {
		List<Customer> customers = new ArrayList<Customer>();
		customers.add(new Customer("MacKenzie Judd", "1112223333", "password"));
		customers.add(new Customer("Denise", "7778885555", "password"));
		customers.add(new Customer("Isha", "123456789", "password"));
		customers.add(new Customer("John", "1112223333", "password"));
		return customers;
	}

	public List<Customer> getValuedCustomers() {
		List<Customer> customers = new ArrayList<Customer>();
		customers.add(new Customer("MacKenzie Judd", "1112223333", "password"));
		return customers;
	}
	
	public double getAverageStayDuration(Calendar startDate, Calendar endDate) {
		return 2;
	}

	public Employee getBestEmployee(Calendar startDate, Calendar endDate) {
		// TODO Auto-generated method stub
		return null;
	}

	public Employee getWorstEmployee(Calendar startDate, Calendar endDate) {
		// TODO Auto-generated method stub
		return null;
	}

	public void deleteReservation(Reservation reservationToDelete) {
		
	}

	public void updateCustomerPassword(Customer customer, String newPassword) {
		
	}

	public void updateEmployeePassword(Employee employee, String newPassword) {
		
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

	public Employee getEmployee(int eid) {
		return new Employee(eid, "George", "PASS");
	}

	public List<Reservation> getReservations(String name, String phone_number,
			boolean checkin, boolean checkout, boolean roomNumber,
			boolean securityDeposit) {
List<Reservation> reservations = new ArrayList<Reservation>();
		
		Location fakeLocation = new Location(1234, "Prince Albert", "V6G2L2", "Vancouver", "BC");
		RoomType fakeType = new RoomType("Big Room", 10000, 100);
		Room fakeRoom = new Room(123, fakeType, fakeLocation);
		Calendar fakeIn = new GregorianCalendar(1992, 1, 12);
		Calendar fakeOut = new GregorianCalendar(1992, 1, 16);
		
		CreditCard fakeCard = new CreditCard("1234567891019", fakeOut, fakeLocation);
		Employee fakeEmployee = new Employee(123, "John", "password");
		
		Customer testCust = new Customer(name, phone_number, "asdf");
		
		reservations.add(new Reservation(123, fakeIn, fakeOut, "Great place!", fakeRoom, testCust, fakeCard, fakeEmployee));
		reservations.add(new Reservation(126, fakeIn, fakeOut, "Great place!", fakeRoom, testCust, fakeCard, fakeEmployee));
		reservations.add(new Reservation(129, fakeIn, fakeOut, "Great place!", fakeRoom, testCust, fakeCard, fakeEmployee));
		reservations.add(new Reservation(1200, fakeIn, fakeOut, "Great place!", fakeRoom, testCust, fakeCard, fakeEmployee));
		
		return reservations;
	}

	@Override
	public Customer getCustomer(String name, String phone_number,
			String password) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Employee getEmployee(int eid, String password) {
		// TODO Auto-generated method stub
		return null;
	}
}
