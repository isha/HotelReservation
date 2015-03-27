package Implementations;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import data_classes.CreditCard;
import data_classes.Customer;
import data_classes.Employee;
import data_classes.Location;
import data_classes.Reservation;
import data_classes.RevenueReport;
import data_classes.RevenueReport.ReportType;
import data_classes.Room;
import data_classes.RoomType;
import Helpers.SqlDateFormatHelper;
import Interfaces.IQueryManager;

public class QueryManager implements IQueryManager {
	
public List<RevenueReport> produceRevenueReport(String sortBy) { 
		
		String groupByQuery;
		ReportType reportType = ReportType.YEAR;
		
		switch(sortBy) { 
			case "month": 
				groupByQuery = " GROUP BY MONTH(checkin_date), YEAR(checkin_date)";
				reportType = ReportType.MONTH;
				break;
			case "year": 
				groupByQuery = " GROUP BY YEAR(checkin_date)";
				break; 
			default: 
				// Default grouping method is group by day
				groupByQuery = " GROUP BY checkin_date";
				reportType = ReportType.DAY;
				break;
		}
		
		String query = "SELECT count(conf_no) AS numReservations,"
				+ " SUM(DATEDIFF(checkout_date, checkin_date)*daily_rate) AS revenue,"
				+ "checkin_date AS date"
				+ " FROM Reservation R, RoomType T, Room M"
				+ " WHERE R.r_number = M.r_number"
				+ " AND R.street = M.street"
				+ " AND R.postal_code = M.postal_code"
				+ " AND M.type = T.type"
				+ groupByQuery
				+ " ORDER BY checkin_date DESC;";

		Connection conn = null;
	    Statement stmt = null;
	    List<RevenueReport> reports = new ArrayList<RevenueReport>();
	    try {
		    try {
		    	conn = DatabaseManager.getConnection();
		        stmt = conn.createStatement();
		        ResultSet rs = stmt.executeQuery(query);
		        while (rs.next()) {
		        	RevenueReport r = new RevenueReport(rs.getInt("numReservations"), rs.getInt("revenue"), 
		        			reportType, SqlDateFormatHelper.SQLDateStringToCalendar(rs.getString("date")));
		        	reports.add(r);
		        }
			} finally {
		        if (stmt != null) { stmt.close(); }
		        if (conn != null) { conn.close(); }
		    }
	    } catch (SQLException ex) {
			ex.printStackTrace();
			return null;
		}
		    
		return reports;
	}

	public List<Room> getOccupiedRooms() {
		String command = "SELECT conf_no, R.r_number, O.type, R.address_no, R.street, R.postal_code, R.checkin_date, R.checkout_date, PL.city, PL.province, RT.security_deposit, RT.daily_rate" +
				" FROM Reservation R, Room O, postallocation PL, RoomType RT" +
				" WHERE R.r_number = O.r_number" +
				" AND R.address_no = O.address_no" +
				" AND R.street = O.street" +
				" AND R.postal_code = O.postal_code" +
				" AND O.postal_code = PL.postal_code" +
				" AND O.type = RT.type" +
			" AND checkin_date <= NOW() AND checkout_date >= NOW();";
		
		Connection conn = null;
	    Statement stmt = null;
	    List<Room> rooms = new ArrayList<Room>();
	    try {
		    try {
		    	conn = DatabaseManager.getConnection();
		        stmt = conn.createStatement();
		        ResultSet rs = stmt.executeQuery(command);
		        while (rs.next()) {
		            rooms.add(new Room(rs.getInt("r_number"), new RoomType(rs.getString("type"), Integer.valueOf(rs.getString("security_deposit")), Integer.valueOf(rs.getString("daily_rate"))), 
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
			String query =
					"SELECT MAX(R.type) as type, Rt.security_deposit, Rt.daily_rate"
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

	public List<Customer> getValuedCustomers() {
		String command = "SELECT name, phone_number FROM Customer C"
		  + " WHERE NOT EXISTS ("
		  + " SELECT *"
		  + " FROM RoomType RT"
		  + " WHERE NOT EXISTS ("
			  + " SELECT *"
			  + " FROM Reservation Res, Room Rm"
			  + " WHERE Res.r_number=Rm.r_number"
			  + " AND RT.type=Rm.type"
			  + " AND Res.address_no=Rm.address_no AND Res.street=Rm.street AND Res.postal_code=Rm.postal_code"
			  + " AND Res.name=C.name AND Res.phone_number=C.phone_number"
			  + "));";

		List<Customer> result_records = new ArrayList<Customer>();
		
		Connection conn = null;
	    Statement stmt = null;
	    try {
		    try {
		    	conn = DatabaseManager.getConnection();
		        stmt = conn.createStatement();
		        ResultSet rs = stmt.executeQuery(command);
		        while (rs.next()) {
		            result_records.add(new Customer(rs.getString("name"), rs.getString("phone_number"), "********"));
		        }
		    } finally {
		        if (stmt != null) { stmt.close(); }
		        if (conn != null) { conn.close(); }
		    }
	    } catch (SQLException ex) {
			ex.printStackTrace();
			return null;
		}
	    
		return result_records;
	}

	public double getAverageStayDuration(Calendar startDate, Calendar endDate) {
		String query = "SELECT AVG(diff) AS AvgNumDays FROM "
				+ "(SELECT DATEDIFF(checkout_date, checkin_date) AS diff"
				+ " FROM Reservation"
				+ " WHERE "
				+ dateRangeQueryBuilder(startDate, endDate)
				+ ") AvgStay;";
		float average = 0;
		
		Connection conn = null;
	    Statement stmt = null;
	    try {
		    try {
		    	conn = DatabaseManager.getConnection();
		        stmt = conn.createStatement();
		        ResultSet rs = stmt.executeQuery(query);
		        if (rs.next()) {
		        	average = rs.getFloat("AvgNumDays");
		        }
		    } finally {
		        if (stmt != null) { stmt.close(); }
		        if (conn != null) { conn.close(); }
		    }
	    } catch (SQLException ex) {
			ex.printStackTrace();
			return 0;
		}
	    return average;
	}

	public Employee getBestEmployee(Calendar startDate, Calendar endDate) {
		String command = "SELECT SumRate, EmployeeName FROM (" +
				"SELECT E.name AS EmployeeName, SUM(Rt.daily_rate) AS SumRate" +
				" FROM Reservation Re, Room R, RoomType Rt, Employee E" +
				" WHERE Re.r_number = R.r_number" +
				" AND Re.address_no = R.address_no" +
				" AND Re.street = R.street" +
				" AND Re.postal_code = R.postal_code" +
				" AND R.type = Rt.type" +
				" AND Re.eid IS NOT NULL" +
				" AND Re.eid = E.eid" +
				" AND " + 
				dateRangeQueryBuilder(startDate, endDate) + 
				" GROUP BY Re.eid" +
			") MaxRates ORDER BY SumRate DESC LIMIT 1;";
		
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
		String command = "SELECT SumRate, EmployeeName FROM (" +
				"SELECT E.name AS EmployeeName, SUM(Rt.daily_rate) AS SumRate" +
				" FROM Reservation Re, Room R, RoomType Rt, Employee E" +
				" WHERE Re.r_number = R.r_number" +
				" AND Re.address_no = R.address_no" +
				" AND Re.street = R.street" +
				" AND Re.postal_code = R.postal_code" +
				" AND R.type = Rt.type" +
				" AND Re.eid IS NOT NULL" +
				" AND Re.eid = E.eid" +
				" AND " + 
				dateRangeQueryBuilder(startDate, endDate) + 
				" GROUP BY Re.eid" +
			") MaxRates ORDER BY SumRate ASC LIMIT 1;";
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
		    String deleteString =
			        "DELETE FROM Reservation WHERE conf_no="+reservationToDelete.getConfirmationNumber()+";";
		    DatabaseManager.executeUpdate(deleteString);
			System.out.println("Deleted Reservation");
	    } catch (SQLException e) {
			System.out.println("ERROR: Could not delete Reservation");
			e.printStackTrace();
		}
	}

	public void deleteCustomer(Customer customerToDelete) {
		try {
		    String deleteString =
			        "DELETE FROM Customer WHERE name='"+customerToDelete.getName()+"' AND phone_number="+customerToDelete.getPhoneNumber()+";";
		    DatabaseManager.executeUpdate(deleteString);
			System.out.println("Deleted Customer");
	    } catch (SQLException e) {
			System.out.println("ERROR: Could not delete Customer");
			e.printStackTrace();
		}
	}
	
	public void updateEmployeePassword(Employee employee, String newPassword) {
		if (newPassword.length() < 6){
			System.out.println("ERROR: Password length must be greater than 6 characters");
			return;
		} else {
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
	}

	public void updateCustomerPassword(Customer customer, String newPassword) {
		if (newPassword.length() < 6){
			System.out.println("ERROR: Password length must be greater than 6 characters");
			return;
		} else {
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
	}

	public List<Reservation> getReservations(Customer customer) {
		return getReservations(customer.getName(), customer.getPhoneNumber(), true, true, true, true);
	}

	public Customer getCustomer(String name, String phone_number) {
		Customer customer = null;
		try {
			String query = "SELECT * "
					+ " FROM Customer "
					+ " WHERE name='"
					+ name + "'"
					+ " AND phone_number='"
					+ phone_number + "'"
					+ ";";
			customer = DatabaseManager.executeReadCustomer(query);
		} catch (SQLException e) {
			System.out.println("ERROR: Could not query");
			e.printStackTrace();
		}

		return customer;
	}

	public Customer getCustomer(String name, String phone_number, String password) {
		Customer customer = null;
		try { 
			String query = "SELECT * "
					+ " FROM Customer "
					+ " WHERE name='"
					+ name + "'"
					+ " AND phone_number='"
					+ phone_number + "'"
					+ " AND password='"
					+ password +"'"
					+ ";";
			customer = DatabaseManager.executeReadCustomer(query);
		} catch (SQLException e) { 
			System.out.println("ERROR: Could not query");
			e.printStackTrace();
		}
						
		return customer;
	}

	public Employee getEmployee(int eid, String password) {
		Employee employee = null;
		try { 
			String query = "SELECT * "
					+ " FROM Employee "
					+ " WHERE eid='"
					+ eid + "'"
					+ " AND password='"
					+ password +"'"
					+ ";";
			employee = DatabaseManager.executeReadEmployee(query);
		} catch (SQLException e) { 
			System.out.println("ERROR: Could not query");
			e.printStackTrace();
		}
						
		return employee;
	}

	public List<Reservation> getReservations(String name, String phone_number,
			boolean checkin, boolean checkout, boolean roomNumber,
			boolean confNo) {
		ArrayList<String> projList = new ArrayList<String>();
		if(checkin) {
			projList.add("checkin_date");
		}
		if(checkout) {
			projList.add("checkout_date");
		}
		if(roomNumber) {
			projList.add("r_number");
		}
		if(confNo) {
			projList.add("conf_no");
		}
		if(!checkin && !checkout && !roomNumber && !confNo) { 
			return new ArrayList<Reservation>();
		}

		String query = "SELECT " + getCommaSeparatedString(projList)
				+ " FROM Reservation"
				+ " WHERE name='"
				+ name + "'"
				+ " AND phone_number='"
				+ phone_number + "';";

		Connection conn = null;
	    Statement stmt = null;
	    List<Reservation> reservations = new ArrayList<Reservation>();
	    try {
		    try {
		    	conn = DatabaseManager.getConnection();
		        stmt = conn.createStatement();
		        ResultSet rs = stmt.executeQuery(query);
		        while (rs.next()) {
		        	Calendar checkin_date = null, checkout_date = null;
		        	int conf_no = 0;
		        	Room room = null;
		        	if(checkin)
		        		checkin_date = SqlDateFormatHelper.SQLDateStringToCalendar(rs.getString("checkin_date"));
		        	if(checkout)
		        		checkout_date = SqlDateFormatHelper.SQLDateStringToCalendar(rs.getString("checkout_date"));
		        	if(roomNumber)
		        		room = new Room(rs.getInt("r_number"), null, null);
		        	if(confNo) { 
		        		conf_no = rs.getInt("conf_no");
		        	}
		        	
		        	reservations.add(new Reservation(conf_no, checkin_date, checkout_date, null, room, null, null, null)
		            );
		        }
			} finally {
		        if (stmt != null) { stmt.close(); }
		        if (conn != null) { conn.close(); }
		    }
	    } catch (SQLException ex) {
			ex.printStackTrace();
			return null;
		}

		return reservations;
	}

	private String getCommaSeparatedString(List<String> list) {
		StringBuilder sb = new StringBuilder();
	    for (int i = 0; i < list.size(); i++) {
	        if (i > 0)
	        	sb.append(", ");
	        sb.append(list.get(i));
	    }
	    return sb.toString();
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
