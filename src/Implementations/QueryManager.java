package Implementations;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import data_classes.Customer;
import data_classes.Employee;
import data_classes.Reservation;
import data_classes.Room;
import data_classes.RoomType;
import Interfaces.IQueryManager;

public class QueryManager implements IQueryManager {

	public List<Room> getOccupiedRooms() {
		// TODO Auto-generated method stub
		return null;
	}

	public RoomType getMostPopularRoomType(Calendar startDate, Calendar endDate)
	{
		try {
			StringBuilder sb = new StringBuilder("SELECT MAX(R.type) as type, Rt.security_deposit, Rt.daily_rate");
			sb.append(" FROM Room R, Reservation Re, RoomType Rt");
			sb.append(" WHERE Re.r_number = R.r_number AND Re.address_no = R.address_no AND Re.street = R.street AND Re.postal_code = R.postal_code");
			sb.append(" AND ");
			sb.append(dateRangeQueryBuilder(startDate, endDate));
			sb.append(" GROUP BY R.type;");
			String query = sb.toString();
			ArrayList<RoomType> roomTypes = (ArrayList<RoomType>) DatabaseManager.executeReadRoomType(query);
			System.out.println("Returned most popular RoomType: " + roomTypes.get(0).getType());
			return roomTypes.get(0);
		} catch (SQLException e) {
			System.out.println("ERROR: Could not query");
			e.printStackTrace();
		}
		return null;
	}

	public List<Customer> getCurrentCustomers() {
		return null;
	}

	public List<Customer> getValuedCustomers(int reservationNumberThreshold) {
		// TODO Auto-generated method stub
		return null;
	}

	public int getAverageStayDuration(Calendar startDate, Calendar endDate) {
		// TODO Auto-generated method stub
		return 0;
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

	public void updateReservationRoom(Reservation reservation, Room newRoom) {
		// TODO Auto-generated method stub

	}

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

	public List<Reservation> getReservations(Customer customer) {
		// TODO Auto-generated method stub
		return null;
	}

	public Customer getCustomer(String name, String phone_number) {
		// TODO Auto-generated method stub
		return null;
	}

	public Employee getEmployee(int eid) {
		// TODO Auto-generated method stub
		return null;
	}

	public Reservation getReservation(String name, String phone_number,
			boolean checkin, boolean checkout, boolean roomNumber,
			boolean securityDeposit) {
		// TODO Auto-generated method stub
		return null;
	}

	 private String dateRangeQueryBuilder(Calendar startDate, Calendar endDate) {
		StringBuilder sb = new StringBuilder("checkin_date BETWEEN '");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		sb.append(sdf.format(startDate.getTime()));
		sb.append("' AND '");
		sb.append(sdf.format(endDate.getTime()));
		sb.append("' AND checkout_date BETWEEN '");
		sb.append(sdf.format(startDate.getTime()));
		sb.append("' AND '");
		sb.append(sdf.format(endDate.getTime()));
		sb.append("'");
		return sb.toString();
	}
}
