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
	
	List<Customer> getCurrentCustomers();
	List<Customer> getValuedCustomers();
	
	List<Reservation> getReservations(String name, String phone_number, boolean checkin, boolean checkout, boolean roomNumber, boolean securityDeposit);
	
	int getAverageStayDuration(Calendar startDate, Calendar endDate);
	
	Employee getBestEmployee(Calendar startDate, Calendar endDate);
	Employee getWorstEmployee(Calendar startDate, Calendar endDate);
	
	void deleteReservation(Reservation reservationToDelete);
	void updateEmployeePassword(Employee employee, String newPassword);
	void updateReservationRoom(Reservation reservation, Room newRoom);
	
	//Customer Queries
	void updateCustomerPassword(Customer customer, String newPassword);
	List<Reservation> getReservations(Customer customer);
	
	//Basic Queries needed
	Customer getCustomer(String name, String phone_number, String password);
	Employee getEmployee(int eid, String password);
}
