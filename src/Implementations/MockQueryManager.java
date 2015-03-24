package Implementations;

import java.util.List;

import data_classes.Customer;
import data_classes.Employee;
import data_classes.Reservation;
import data_classes.Room;
import data_classes.RoomType;
import Interfaces.IQueryManager;

public class MockQueryManager implements IQueryManager{

	@Override
	public List<Room> getOccupiedRooms(String date) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Customer> getCurrentCustomers(String date) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getAverageStayDuration(String startDate, String endDate) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public RoomType getMostPopularRoomType(String startDate, String endDate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Employee getBestEmployee(String startDate, String endDate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Employee getWorstEmployee(String startDate, String endDate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Customer> getValuedCustomers(int reservationNumberThreshold) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteReservation(Reservation reservationToDelete) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateCustomerPassword(Customer customer, String newPassword) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateEmployeePassword(Employee employee, String newPassword) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateReservationRoom(Reservation reservation, Room newRoom) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Customer getCustomer(String name, String phone_number) {
		return new Customer(name, phone_number, "password");
	}

}
