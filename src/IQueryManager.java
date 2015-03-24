import java.util.List;

import data_classes.Customer;
import data_classes.Employee;
import data_classes.Reservation;
import data_classes.Room;
import data_classes.RoomType;


public interface IQueryManager {
	List<Room> getOccupiedRooms(String date);
	
	List<Customer> getCurrentCustomers(String date);
	
	int getAverageStayDuration(String startDate, String endDate);
	
	RoomType getMostPopularRoomType(String startDate, String endDate);
	
	Employee getBestEmployee(String startDate, String endDate);
	Employee getWorstEmployee(String startDate, String endDate);
	
	List<Customer> getValuedCustomers(int reservationNumberThreshold);
	
	void deleteReservation(Reservation reservationToDelete);
	
	void updateCustomerPassword(Customer customer, String newPassword);
	void updateEmployeePassword(Employee employee, String newPassword);
	
	void updateReservationRoom(Reservation reservation, Room newRoom);
}
