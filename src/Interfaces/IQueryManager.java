package Interfaces;
import java.util.Calendar;
import java.util.List;

import data_classes.Customer;
import data_classes.Employee;
import data_classes.Reservation;
import data_classes.Room;
import data_classes.RoomType;


public interface IQueryManager {
	//Employee Queries
	List<Room> getOccupiedRooms();
	RoomType getMostPopularRoomType(Calendar startDate, Calendar endDate);
	
	List<Customer> getValuedCustomers();
	
	List<Reservation> getReservations(String name, String phone_number, boolean checkin, boolean checkout, boolean roomNumber, boolean securityDeposit);
	
	double getAverageStayDuration(Calendar startDate, Calendar endDate);
	
	Employee getBestEmployee(Calendar startDate, Calendar endDate);
	Employee getWorstEmployee(Calendar startDate, Calendar endDate);
	
	void deleteReservation(Reservation reservationToDelete);
	void updateEmployeePassword(Employee employee, String newPassword);
	
	//Customer Queries
	void updateCustomerPassword(Customer customer, String newPassword);
	List<Reservation> getReservations(Customer customer);
	
	public void deleteCustomer(Customer customerToDelete);
	
	//Basic Queries needed
	Customer getCustomer(String name, String phone_number);
	Customer getCustomer(String name, String phone_number, String password);
	Employee getEmployee(int eid, String password);
}
