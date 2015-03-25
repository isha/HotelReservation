package Implementations;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import data_classes.Customer;
import data_classes.Employee;
import data_classes.Location;
import data_classes.Reservation;
import data_classes.Room;
import data_classes.RoomType;
import Helpers.SqlDateFormatHelper;
import Interfaces.IQueryManager;

public class QueryManager implements IQueryManager {

	public List<Room> getOccupiedRooms() {
		String command = "SELECT conf_no, R.r_number, type, R.address_no, R.street, R.postal_code, R.checkin_date, R.checkout_date" +
				"FROM Reservation R, Room O" +
				"WHERE R.r_number = O.r_number" +
				"AND R.address_no = O.address_no" +
				"AND R.street = O.street" +
				"AND R.postal_code = O.postal_code" +
			"AND checkin_date < NOW() AND checkout_date > NOW();";
		
		Connection conn = null;
	    Statement stmt = null;
	    List<Room> rooms = new ArrayList<Room>();
	    try {
		    try {
		    	conn = DatabaseManager.getConnection();
		        stmt = conn.createStatement();
		        ResultSet rs = stmt.executeQuery(command);
		        while (rs.next()) {
		            rooms.add(new Room(rs.getInt("r_number"), new RoomType(rs.getString("type"), -1, -1 ), 
		            		new Location(rs.getInt("address_no"), rs.getString("street"), rs.getString("postal_code"), rs.getString("city"), rs.getString("province")) 
		            ));
		        }
			} finally {
		        if (stmt != null) { stmt.close(); }
		        if (conn != null) { conn.close(); }
		    }
	    } catch (SQLException ex) {
			ex.printStackTrace();
			return null;
		}
		    
		return rooms;
	}

	public RoomType getMostPopularRoomType(Calendar startDate, Calendar endDate)
	{
		try {
			String query = "SELECT MAX(R.type) as type, Rt.security_deposit, Rt.daily_rate"
			+ " FROM Room R, Reservation Re, RoomType Rt"
			+ " WHERE Re.r_number = R.r_number AND Re.address_no = R.address_no AND Re.street = R.street AND Re.postal_code = R.postal_code"
			+ " AND "
			+ dateRangeQueryBuilder(startDate, endDate)
			+ " GROUP BY R.type;";
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
		String command = "SELECT MAX(AvgRate), EmployeeName FROM (" +
				"SELECT E.name AS EmployeeName, AVG(Rt.daily_rate) AS AvgRate" +
				"FROM Reservation Re, Room R, RoomType Rt, Employee E" +
				"WHERE Re.r_number = R.r_number" +
				"AND Re.address_no = R.address_no" +
				"AND Re.street = R.street" +
				"AND Re.postal_code = R.postal_code" +
				"AND R.type = Rt.type" +
				"AND Re.eid IS NOT NULL" +
				"AND Re.eid = E.eid" +
			"AND checkin_date BETWEEN" + startDate + " AND " + endDate + 
			"AND checkout_date BETWEEN"+ startDate + " AND " + endDate +
			"GROUP BY Re.eid" +
			") MaxRates;";
		
		Connection conn = null;
	    Statement stmt = null;
	    Employee e = null;
	    try {
		    try {
		    	conn = DatabaseManager.getConnection();
		        stmt = conn.createStatement();
		        ResultSet rs = stmt.executeQuery(command);
		        while (rs.next()) {
		            e = new Employee(-1, rs.getString("EmployeeName"), "**********");
		        }
			} finally {
		        if (stmt != null) { stmt.close(); }
		        if (conn != null) { conn.close(); }
		    }
	    } catch (SQLException ex) {
			ex.printStackTrace();
			return null;
		}
		    
		return e;
	}

	public Employee getWorstEmployee(Calendar startDate, Calendar endDate) {
		String command = "SELECT MIN(AvgRate), EmployeeName FROM (" +
				"SELECT E.name AS EmployeeName, AVG(Rt.daily_rate) AS AvgRate" +
				"FROM Reservation Re, Room R, RoomType Rt, Employee E" +
				"WHERE Re.r_number = R.r_number" +
				"AND Re.address_no = R.address_no" +
				"AND Re.street = R.street" +
				"AND Re.postal_code = R.postal_code" +
				"AND R.type = Rt.type" +
				"AND Re.eid IS NOT NULL" +
				"AND Re.eid = E.eid" +
			"AND checkin_date BETWEEN" + startDate + " AND " + endDate + 
			"AND checkout_date BETWEEN"+ startDate + " AND " + endDate +
			"GROUP BY Re.eid" +
			") MaxRates;";
		
		Connection conn = null;
	    Statement stmt = null;
	    Employee e = null;
	    try {
		    try {
		    	conn = DatabaseManager.getConnection();
		        stmt = conn.createStatement();
		        ResultSet rs = stmt.executeQuery(command);
		        while (rs.next()) {
		            e = new Employee(-1, rs.getString("EmployeeName"), "**********");
		        }
			} finally {
		        if (stmt != null) { stmt.close(); }
		        if (conn != null) { conn.close(); }
		    }
	    } catch (SQLException ex) {
			ex.printStackTrace();
			return null;
		}
		    
		return e;
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

	public List<Reservation> getReservations(String name, String phone_number,
			boolean checkin, boolean checkout, boolean roomNumber,
			boolean securityDeposit) {
		// TODO Auto-generated method stub
		return null;
	}

	 private String dateRangeQueryBuilder(Calendar startDate, Calendar endDate) {
		 String q = "checkin_date BETWEEN " 
				+ "'" + SqlDateFormatHelper.CalendarToSqlDateString(startDate) + "'"
				+ " AND " 
				+ "'" + SqlDateFormatHelper.CalendarToSqlDateString(endDate) + "'"
				+ " AND checkout_date BETWEEN "
				+ "'" + SqlDateFormatHelper.CalendarToSqlDateString(startDate) + "'"
				+ " AND " 
				+ "'" + SqlDateFormatHelper.CalendarToSqlDateString(endDate) + "'";
		return q;
	}
}
