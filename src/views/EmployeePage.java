package views;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTabbedPane;
import javax.swing.JLabel;
import javax.swing.JButton;

import java.awt.Font;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.swing.JTable;
import javax.swing.JScrollPane;

import Helpers.SqlDateFormatHelper;
import Interfaces.IQueryManager;
import data_classes.Customer;
import data_classes.Employee;
import data_classes.Reservation;
import data_classes.Room;

import javax.swing.JTextField;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;

import javax.swing.JRadioButton;

public class EmployeePage extends JFrame {

	private JPanel contentPane;
	private JTable occupied_rooms;
	private JTable occupied_room_table;

	private IQueryManager _queryManager;
	private Employee _employee;
	private JTextField start_date_field;
	private JTextField end_date_field;
	private JTable current_cust_table;
	private JTextField stay_start_date_field;
	private JTextField stay_end_date_field;
	private JTable valued_cust_table;
	private JTextField reservation_cust_name_field;
	private JTextField reservation_cust_phone_field;
	private JTable reservations_customer_table;
	private JTextField emp_rank_start;
	private JTextField emp_rank_end;

	/**
	 * Create the frame.
	 */
	public EmployeePage(IQueryManager queryManager, Employee employee) {
		
		_queryManager = queryManager;
		_employee = employee;
		
		setTitle("Employee: " + _employee.getName() + " (" + _employee.getId() + ")");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 578, 418);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 0, 562, 379);
		contentPane.add(tabbedPane);
		
		JPanel room_panel = new JPanel();
		tabbedPane.addTab("Rooms", null, room_panel, null);
		room_panel.setLayout(null);
		
		JLabel lblOccupiedRooms = new JLabel("Occupied Rooms");
		lblOccupiedRooms.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblOccupiedRooms.setBounds(10, 11, 110, 14);
		room_panel.add(lblOccupiedRooms);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 36, 537, 164);
		room_panel.add(scrollPane);
		
		
		
		occupied_room_table = new JTable();
		RefreshOccupiedRooms(occupied_room_table);
		scrollPane.setViewportView(occupied_room_table);
		
		JLabel lblNewLabel = new JLabel("Most Popular Room: ");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel.setBounds(123, 237, 138, 14);
		room_panel.add(lblNewLabel);
		
		start_date_field = new JTextField();
		start_date_field.setBounds(263, 261, 86, 20);
		room_panel.add(start_date_field);
		start_date_field.setColumns(10);
		
		end_date_field = new JTextField();
		end_date_field.setBounds(263, 292, 86, 20);
		room_panel.add(end_date_field);
		end_date_field.setColumns(10);
		
		JLabel lblStartDate = new JLabel("Start Date (yyyy-mm-dd)");
		lblStartDate.setBounds(123, 264, 138, 14);
		room_panel.add(lblStartDate);
		
		JLabel lblEndDate = new JLabel("End Date (yyyy-mm-dd)");
		lblEndDate.setBounds(123, 295, 138, 14);
		room_panel.add(lblEndDate);
		
		final JLabel popular_room_label = new JLabel("None");
		popular_room_label.setFont(new Font("Dialog", Font.BOLD, 12));
		popular_room_label.setBounds(263, 238, 86, 14);
		room_panel.add(popular_room_label);
		
		final JLabel start_error = new JLabel("");
		start_error.setForeground(Color.RED);
		start_error.setBounds(364, 264, 183, 14);
		room_panel.add(start_error);
		
		final JLabel end_error = new JLabel("");
		end_error.setForeground(Color.RED);
		end_error.setBounds(364, 295, 183, 14);
		room_panel.add(end_error);
		
		JButton button = new JButton("Find Most Popular Room!");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				String startDateText = start_date_field.getText();
				String endDateText = end_date_field.getText();
				
				if(!SqlDateFormatHelper.isValidSqlDateString(startDateText)){
					start_error.setText("Invalid date format!");
				}else{
					start_error.setText("");
				}
				
				if(!SqlDateFormatHelper.isValidSqlDateString(endDateText)){
					end_error.setText("Invalid date format!");
				}else{
					end_error.setText("");
				}
				
				if(SqlDateFormatHelper.isValidSqlDateString(startDateText) && SqlDateFormatHelper.isValidSqlDateString(endDateText)){
					Calendar startDate = SqlDateFormatHelper.SQLDateStringToCalendar(startDateText);
					Calendar endDate = SqlDateFormatHelper.SQLDateStringToCalendar(endDateText);
					String mostPopularRoom = (_queryManager.getMostPopularRoomType(startDate, endDate)).getType();
					popular_room_label.setText(mostPopularRoom);
				}
			}
		});
		
		button.setBounds(123, 320, 226, 20);
		room_panel.add(button);
		
		JPanel customer_panel = new JPanel();
		tabbedPane.addTab("Customers", null, customer_panel, null);
		customer_panel.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("Current Customers");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_1.setForeground(new Color(0, 0, 0));
		lblNewLabel_1.setBounds(10, 11, 135, 14);
		customer_panel.add(lblNewLabel_1);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 36, 537, 162);
		customer_panel.add(scrollPane_1);
		
		
		current_cust_table = new JTable();
		RefreshCurrentCustomers(current_cust_table);
		scrollPane_1.setViewportView(current_cust_table);
		
		JLabel lblAverageStayDuration = new JLabel("Average Stay Duration: ");
		lblAverageStayDuration.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblAverageStayDuration.setBounds(110, 226, 148, 14);
		customer_panel.add(lblAverageStayDuration);
		
		final JLabel stay_duration_label = new JLabel("None");
		stay_duration_label.setFont(new Font("Dialog", Font.BOLD, 12));
		stay_duration_label.setBounds(268, 226, 86, 14);
		customer_panel.add(stay_duration_label);
		
		stay_start_date_field = new JTextField();
		stay_start_date_field.setColumns(10);
		stay_start_date_field.setBounds(250, 250, 86, 20);
		customer_panel.add(stay_start_date_field);
		
		stay_end_date_field = new JTextField();
		stay_end_date_field.setColumns(10);
		stay_end_date_field.setBounds(250, 281, 86, 20);
		customer_panel.add(stay_end_date_field);
		
		JLabel label_2 = new JLabel("End Date (yyyy-mm-dd)");
		label_2.setBounds(110, 284, 138, 14);
		customer_panel.add(label_2);
		
		JLabel label_3 = new JLabel("Start Date (yyyy-mm-dd)");
		label_3.setBounds(110, 253, 138, 14);
		customer_panel.add(label_3);
		
		final JLabel stay_start_error = new JLabel("");
		stay_start_error.setForeground(Color.RED);
		stay_start_error.setBounds(364, 248, 183, 14);
		customer_panel.add(stay_start_error);
		
		final JLabel stay_end_error = new JLabel("");
		stay_end_error.setForeground(Color.RED);
		stay_end_error.setBounds(364, 279, 183, 14);
		customer_panel.add(stay_end_error);
		
		JButton btnFindAverageStay = new JButton("Find Average Stay!");
		btnFindAverageStay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String startDateText = stay_start_date_field.getText();
				String endDateText = stay_end_date_field.getText();
				
				if(!SqlDateFormatHelper.isValidSqlDateString(startDateText)){
					stay_start_error.setText("Invalid date format!");
				}else{
					stay_start_error.setText("");
				}
				
				if(!SqlDateFormatHelper.isValidSqlDateString(endDateText)){
					stay_end_error.setText("Invalid date format!");
				}else{
					stay_end_error.setText("");
				}
				
				if(SqlDateFormatHelper.isValidSqlDateString(startDateText) && SqlDateFormatHelper.isValidSqlDateString(endDateText)){
					Calendar startDate = SqlDateFormatHelper.SQLDateStringToCalendar(startDateText);
					Calendar endDate = SqlDateFormatHelper.SQLDateStringToCalendar(endDateText);
					int averageStay = _queryManager.getAverageStayDuration(startDate, endDate);
					stay_duration_label.setText(String.valueOf(averageStay));
				}
			}
		});
		btnFindAverageStay.setBounds(110, 309, 226, 20);
		customer_panel.add(btnFindAverageStay);
		
		JPanel panel = new JPanel();
		tabbedPane.addTab("Valued Customers", null, panel, null);
		panel.setLayout(null);
		
		JLabel lblNewLabel_2 = new JLabel("Valued Customers");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_2.setBounds(10, 11, 110, 14);
		panel.add(lblNewLabel_2);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(10, 36, 537, 304);
		panel.add(scrollPane_2);
		
		valued_cust_table = new JTable();
		RefreshValuedCustomers(valued_cust_table);
		scrollPane_2.setViewportView(valued_cust_table);
		
		JButton btnLogout = new JButton("Logout");
		btnLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LoginPage login = new LoginPage(_queryManager);
				login.setVisible(true);
				setVisible(false);
			}
		});
		
		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("Reservations", null, panel_1, null);
		panel_1.setLayout(null);
		
		JLabel lblCustomerName = new JLabel("Customer Name");
		lblCustomerName.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblCustomerName.setBounds(10, 13, 106, 14);
		panel_1.add(lblCustomerName);
		
		reservation_cust_name_field = new JTextField();
		reservation_cust_name_field.setBounds(141, 11, 157, 20);
		panel_1.add(reservation_cust_name_field);
		reservation_cust_name_field.setColumns(10);
		
		JLabel lblCustomerTextField = new JLabel("Customer Phone");
		lblCustomerTextField.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblCustomerTextField.setBounds(10, 38, 135, 14);
		panel_1.add(lblCustomerTextField);
		
		reservation_cust_phone_field = new JTextField();
		reservation_cust_phone_field.setBounds(141, 36, 157, 20);
		panel_1.add(reservation_cust_phone_field);
		reservation_cust_phone_field.setColumns(10);
		
		JLabel lblSearchAttributes = new JLabel("Search Attributes");
		lblSearchAttributes.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblSearchAttributes.setBounds(360, 11, 111, 14);
		panel_1.add(lblSearchAttributes);
		
		final JRadioButton start_date_select = new JRadioButton("Start Date");
		start_date_select.setBounds(326, 32, 109, 23);
		panel_1.add(start_date_select);
		
		final JRadioButton end_date_select = new JRadioButton("End Date");
		end_date_select.setBounds(326, 66, 109, 23);
		panel_1.add(end_date_select);
		
		final JRadioButton room_number_select = new JRadioButton("Room #");
		room_number_select.setBounds(442, 33, 109, 23);
		panel_1.add(room_number_select);
		
		final JRadioButton room_deposit_select = new JRadioButton("Deposit");
		room_deposit_select.setBounds(442, 66, 109, 23);
		panel_1.add(room_deposit_select);
		
		JButton btnNewButton = new JButton("Find Reservations!");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RefreshReservationsSelected(reservations_customer_table, reservation_cust_name_field.getText(), 
						reservation_cust_phone_field.getText(), start_date_select.isSelected(), end_date_select.isSelected(), 
						room_number_select.isSelected(), room_deposit_select.isSelected());
			}
		});
		btnNewButton.setBounds(10, 63, 288, 29);
		panel_1.add(btnNewButton);
		
		JScrollPane scrollPane_3 = new JScrollPane();
		scrollPane_3.setBounds(10, 105, 537, 235);
		panel_1.add(scrollPane_3);
		
		reservations_customer_table = new JTable();
		scrollPane_3.setViewportView(reservations_customer_table);
		
		JPanel panel_2 = new JPanel();
		tabbedPane.addTab("Employee Rankings", null, panel_2, null);
		panel_2.setLayout(null);
		
		JLabel lblBestEmployee = new JLabel("Best Employee:");
		lblBestEmployee.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblBestEmployee.setBounds(100, 172, 122, 25);
		panel_2.add(lblBestEmployee);
		
		JLabel lblWorstEmployee = new JLabel("Worst Employee: ");
		lblWorstEmployee.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblWorstEmployee.setBounds(100, 268, 122, 25);
		panel_2.add(lblWorstEmployee);
		
		JLabel best_employee_label = new JLabel("");
		best_employee_label.setForeground(new Color(0, 128, 0));
		best_employee_label.setFont(new Font("Tahoma", Font.BOLD, 14));
		best_employee_label.setBounds(261, 179, 215, 18);
		panel_2.add(best_employee_label);
		
		JLabel worst_employee_label = new JLabel("");
		worst_employee_label.setForeground(new Color(255, 0, 0));
		worst_employee_label.setFont(new Font("Tahoma", Font.BOLD, 14));
		worst_employee_label.setBounds(261, 275, 215, 17);
		panel_2.add(worst_employee_label);
		
		JLabel lblDuration = new JLabel("Duration: ");
		lblDuration.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblDuration.setBounds(142, 25, 148, 14);
		panel_2.add(lblDuration);
		
		emp_rank_start = new JTextField();
		emp_rank_start.setColumns(10);
		emp_rank_start.setBounds(282, 49, 86, 20);
		panel_2.add(emp_rank_start);
		
		emp_rank_end = new JTextField();
		emp_rank_end.setColumns(10);
		emp_rank_end.setBounds(282, 80, 86, 20);
		panel_2.add(emp_rank_end);
		
		JLabel label_4 = new JLabel("End Date (yyyy-mm-dd)");
		label_4.setBounds(142, 83, 138, 14);
		panel_2.add(label_4);
		
		JLabel label_5 = new JLabel("Start Date (yyyy-mm-dd)");
		label_5.setBounds(142, 52, 138, 14);
		panel_2.add(label_5);
		
		JLabel emp_rank_start_error = new JLabel("");
		emp_rank_start_error.setForeground(Color.RED);
		emp_rank_start_error.setBounds(374, 49, 183, 14);
		panel_2.add(emp_rank_start_error);
		
		JLabel emp_rank_end_error = new JLabel("");
		emp_rank_end_error.setForeground(Color.RED);
		emp_rank_end_error.setBounds(374, 80, 183, 14);
		panel_2.add(emp_rank_end_error);
		tabbedPane.addTab("Logout", null, btnLogout, null);
		
		JButton btnFindBestAnd = new JButton("Find Best and Worst Employees!");
		btnFindBestAnd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String startDateText = emp_rank_start.getText();
				String endDateText = emp_rank_end.getText();
				
				if(!SqlDateFormatHelper.isValidSqlDateString(startDateText)){
					emp_rank_start_error.setText("Invalid date format!");
				}else{
					emp_rank_start_error.setText("");
				}
				
				if(!SqlDateFormatHelper.isValidSqlDateString(endDateText)){
					emp_rank_end_error.setText("Invalid date format!");
				}else{
					emp_rank_end_error.setText("");
				}
				
				if(SqlDateFormatHelper.isValidSqlDateString(startDateText) && SqlDateFormatHelper.isValidSqlDateString(endDateText)){
					Calendar startDate = SqlDateFormatHelper.SQLDateStringToCalendar(startDateText);
					Calendar endDate = SqlDateFormatHelper.SQLDateStringToCalendar(endDateText);
					RefreshEmployeeRankings(best_employee_label, worst_employee_label, startDate, endDate);
				}
			}
		});
		btnFindBestAnd.setBounds(142, 108, 226, 20);
		panel_2.add(btnFindBestAnd);
	}
	
	private void RefreshOccupiedRooms(JTable table){
		DefaultTableModel dtm = new DefaultTableModel(){
			@Override
		    public boolean isCellEditable(int row, int column) {
		        return false;
		    }
		};
		
		String[] headers = new String[] {
				"Room #", "Room Type", "Daily Rate", "Security Deposit", "Location"
			};
		
		dtm.setColumnIdentifiers(headers);
		
		List<Room> rooms = _queryManager.getOccupiedRooms();
		for(Room room : rooms){
			Object[] rowdata = new Object[] { room.getRoomNumber(), room.getRoomType().getType(), room.getRoomType().getDailyRate(), room.getRoomType().getSecurityDeposit(), room.getLocation().getAddressNumber() + " " + room.getLocation().getStreet()};
			dtm.addRow(rowdata);
		}
		
		table.setModel(dtm);
	}
	
	private void RefreshCurrentCustomers(JTable table){
		DefaultTableModel current_customer_model = new DefaultTableModel(){
			@Override
		    public boolean isCellEditable(int row, int column) {
		        return false;
		    }
		};
		
		String[] cust_headers = new String[] {
				"Customer Name", "Customer Phone Number"
			};
		
		current_customer_model.setColumnIdentifiers(cust_headers);
		
		List<Customer> customers = _queryManager.getCurrentCustomers();
		for(Customer customer : customers){
			Object[] rowdata = new Object[] { customer.getName(), customer.getPhoneNumber()};
			current_customer_model.addRow(rowdata);
		}
		
		table.setModel(current_customer_model);
	}
	
	private void RefreshValuedCustomers(JTable table){
		DefaultTableModel current_customer_model = new DefaultTableModel(){
			@Override
		    public boolean isCellEditable(int row, int column) {
		        return false;
		    }
		};
		
		String[] cust_headers = new String[] {
				"Customer Name", "Customer Phone Number"
			};
		
		current_customer_model.setColumnIdentifiers(cust_headers);
		
		List<Customer> customers = _queryManager.getValuedCustomers(2);
		for(Customer customer : customers){
			Object[] rowdata = new Object[] { customer.getName(), customer.getPhoneNumber()};
			current_customer_model.addRow(rowdata);
		}
		
		table.setModel(current_customer_model);
	}
	
	private void RefreshReservationsSelected(JTable table, String name, String phone,  boolean checkin, boolean checkout, boolean room_number, boolean deposit){
		DefaultTableModel reservation_model = new DefaultTableModel(){
			@Override
		    public boolean isCellEditable(int row, int column) {
		        return false;
		    }
		};
		
		List<String> headers = new ArrayList<String>();
		if(checkin){headers.add("Checkin Date");}
		if(checkout){headers.add("Checkout Date");}
		if(room_number){headers.add("Room #");}
		if(deposit){headers.add("Security Deposit");}
		
		reservation_model.setColumnIdentifiers(headers.toArray());
		
		List<Reservation> reservations = _queryManager.getReservations(name, phone, checkin, checkout, room_number, deposit);
		for(Reservation reservation : reservations){
			List<Object> data = new ArrayList<Object>();
			if(checkin){data.add(SqlDateFormatHelper.CalendarToSqlDateString(reservation.getCheckinDate()));}
			if(checkout){data.add(SqlDateFormatHelper.CalendarToSqlDateString(reservation.getCheckoutDate()));}
			if(room_number){data.add(reservation.getRoom().getRoomNumber());}
			if(deposit){data.add("$" + reservation.getRoom().getRoomType().getSecurityDeposit());}
			reservation_model.addRow(data.toArray());
		}
		
		table.setModel(reservation_model);
	}
	
	private void RefreshEmployeeRankings(JLabel bestLabel, JLabel worstLabel, Calendar start, Calendar end){
		String bestEmployee = _queryManager.getBestEmployee(start, end).getName();
		String worstEmployee = _queryManager.getWorstEmployee(start, end).getName();
		bestLabel.setText(bestEmployee);
		worstLabel.setText(worstEmployee);
	}
}
