package Implementations;

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
		// TODO Auto-generated method stub
		
	}

	public void updateEmployeePassword(Employee employee, String newPassword) {
		// TODO Auto-generated method stub
		
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
