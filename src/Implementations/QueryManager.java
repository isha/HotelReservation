package Implementations;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;

import data_classes.Customer;
import data_classes.Employee;
import data_classes.Reservation;
import data_classes.Room;
import data_classes.RoomType;
import Interfaces.IQueryManager;

public class QueryManager implements IQueryManager {

	@Override
	public List<Room> getOccupiedRooms() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RoomType getMostPopularRoomType(Calendar startDate, Calendar endDate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Customer> getCurrentCustomers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Customer> getValuedCustomers(int reservationNumberThreshold) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getAverageStayDuration(Calendar startDate, Calendar endDate) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Employee getBestEmployee(Calendar startDate, Calendar endDate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Employee getWorstEmployee(Calendar startDate, Calendar endDate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteReservation(Reservation reservationToDelete) {
		try {
		    String createString =
			        "DELETE FROM Reservation WHERE conf_no="+reservationToDelete.getConfirmationNumber()+";";
		    DatabaseManager.executeUpdate(createString);
			System.out.println("Deleted Reservation");
	    } catch (SQLException e) {
			System.out.println("ERROR: Could not delete Reservation");
			e.printStackTrace();
		}
	}

	@Override
	public void updateEmployeePassword(Employee employee, String newPassword) {
		try {
		    String createString =
			        "Update Employee SET password='"+newPassword+"' WHERE eid='"+
			        employee.getId()+"';";
		    DatabaseManager.executeUpdate(createString);
			System.out.println("Updated Employee password");
	    } catch (SQLException e) {
			System.out.println("ERROR: Could not update Employee password");
			e.printStackTrace();
		}
	}

	@Override
	public void updateReservationRoom(Reservation reservation, Room newRoom) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateCustomerPassword(Customer customer, String newPassword) {
		try {
		    String createString =
			        "Update Customer SET password='"+newPassword+"' WHERE name='"+
			        customer.getName()+"' AND phone_number='"+customer.getPhoneNumber()+"';";
		    DatabaseManager.executeUpdate(createString);
			System.out.println("Updated Customer password");
	    } catch (SQLException e) {
			System.out.println("ERROR: Could not update Customer password");
			e.printStackTrace();
		}
	}

	@Override
	public List<Reservation> getReservations(Customer customer) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Customer getCustomer(String name, String phone_number) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Employee getEmployee(int eid) {
		// TODO Auto-generated method stub
		return null;
	}
}
