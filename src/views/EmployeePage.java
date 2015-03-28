package views;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.ButtonGroup;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTabbedPane;
import javax.swing.JLabel;
import javax.swing.JButton;

import java.awt.Font;
import java.text.SimpleDateFormat;
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
import data_classes.RevenueReport;
import data_classes.Room;

import javax.swing.JTextField;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
	private JTextField stay_start_date_field;
	private JTextField stay_end_date_field;
	private JTable valued_cust_table;
	private JTextField reservation_cust_name_field;
	private JTextField reservation_cust_phone_field;
	private JTable reservations_customer_table;
	private JTextField emp_rank_start;
	private JTextField emp_rank_end;
	private JTextField cust_rem_name;
	private JTextField cust_rem_phone;
	private JTable revenue_report_table;

	/**
	 * Create the frame.
	 */
	public EmployeePage(IQueryManager queryManager, Employee employee) {
		
		_queryManager = queryManager;
		_employee = employee;
		
		setTitle("Employee: " + _employee.getName() + " (" + _employee.getId() + ")");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setIconImage(Toolkit.getDefaultToolkit().getImage(Main.class.getResource("/resources/8652.png")));
		setBounds(100, 100, 610, 452);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 0, 594, 413);
		contentPane.add(tabbedPane);
		
//		REVENUE REPORT: 
		JPanel report_panel = new JPanel(); 
		tabbedPane.addTab("Revenue Report", null, report_panel, null);
		report_panel.setLayout(null);

		JLabel lblReportType = new JLabel("Report Type");
		lblReportType.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblReportType.setBounds(10, 11, 110, 14);
		report_panel.add(lblReportType);
		
		final JRadioButton month_date_select = new JRadioButton("Month");
		month_date_select.setBounds(10, 32, 100, 23);
		report_panel.add(month_date_select);
		
		final JRadioButton day_date_select = new JRadioButton("Day");
		day_date_select.setSelected(true);
		day_date_select.setBounds(110, 32, 100, 23);
		report_panel.add(day_date_select);
		
		final JRadioButton year_date_select = new JRadioButton("Year");
		year_date_select.setBounds(210, 32, 100, 23);
		report_panel.add(year_date_select);
		
		ButtonGroup reportGroup = new ButtonGroup();
		reportGroup.add(month_date_select);
		reportGroup.add(day_date_select);
		reportGroup.add(year_date_select);
		
		JButton reportButton = new JButton("Get Report");
		reportButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RefreshRevenueReportSelected(revenue_report_table, month_date_select.isSelected(), day_date_select.isSelected(), 
						year_date_select.isSelected());
			}
		});
		reportButton.setBounds(10, 63, 288, 29);
		report_panel.add(reportButton);
		
		JScrollPane scrollPane_revenue = new JScrollPane();
		scrollPane_revenue.setBounds(10, 105, 569, 269);
		report_panel.add(scrollPane_revenue);
		
		revenue_report_table = new JTable();
		scrollPane_revenue.setViewportView(revenue_report_table);
		
		
		
//		OCCUPIED ROOMS: 
		JPanel room_panel = new JPanel();
		tabbedPane.addTab("Rooms", null, room_panel, null);
		room_panel.setLayout(null);
		
		JLabel lblOccupiedRooms = new JLabel("Occupied Rooms");
		lblOccupiedRooms.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblOccupiedRooms.setBounds(10, 11, 110, 14);
		room_panel.add(lblOccupiedRooms);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 36, 569, 164);
		room_panel.add(scrollPane);
		
		occupied_room_table = new JTable();
		RefreshOccupiedRooms(occupied_room_table);
		scrollPane.setViewportView(occupied_room_table);
		
		JLabel lblNewLabel = new JLabel("Most Popular Room: ");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel.setBounds(184, 234, 138, 14);
		room_panel.add(lblNewLabel);
		
		start_date_field = new JTextField();
		start_date_field.setBounds(324, 258, 86, 20);
		room_panel.add(start_date_field);
		start_date_field.setColumns(10);
		
		end_date_field = new JTextField();
		end_date_field.setBounds(324, 289, 86, 20);
		room_panel.add(end_date_field);
		end_date_field.setColumns(10);
		
		JLabel lblStartDate = new JLabel("Start Date (yyyy-mm-dd)");
		lblStartDate.setBounds(184, 261, 138, 14);
		room_panel.add(lblStartDate);
		
		JLabel lblEndDate = new JLabel("End Date (yyyy-mm-dd)");
		lblEndDate.setBounds(184, 292, 138, 14);
		room_panel.add(lblEndDate);
		
		final JLabel popular_room_label = new JLabel("None");
		popular_room_label.setFont(new Font("Dialog", Font.BOLD, 12));
		popular_room_label.setBounds(324, 235, 86, 14);
		room_panel.add(popular_room_label);
		
		final JLabel start_error = new JLabel("");
		start_error.setForeground(Color.RED);
		start_error.setBounds(420, 264, 183, 14);
		room_panel.add(start_error);
		
		final JLabel end_error = new JLabel("");
		end_error.setForeground(Color.RED);
		end_error.setBounds(420, 295, 183, 14);
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
		
		button.setBounds(184, 317, 226, 20);
		room_panel.add(button);
		
//		VALUED CUSTOMER:
		JPanel panel = new JPanel();
		tabbedPane.addTab("Valued Customers", null, panel, null);
		panel.setLayout(null);
		
		JLabel lblNewLabel_2 = new JLabel("Valued Customers");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_2.setBounds(10, 11, 110, 14);
		panel.add(lblNewLabel_2);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(10, 36, 569, 338);
		panel.add(scrollPane_2);
		
		valued_cust_table = new JTable();
		RefreshValuedCustomers(valued_cust_table);
		scrollPane_2.setViewportView(valued_cust_table);
		
		JButton btnLogout = new JButton("Logout");
		btnLogout.setFont(new Font("Tahoma", Font.BOLD, 20));
		btnLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LoginPage login = new LoginPage(_queryManager);
				login.setVisible(true);
				setVisible(false);
			}
		});
		
//		RESERVATIONS: 
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
		
		final JRadioButton room_deposit_select = new JRadioButton("Conf #");
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
		scrollPane_3.setBounds(10, 105, 569, 269);
		panel_1.add(scrollPane_3);
		
		reservations_customer_table = new JTable();
		scrollPane_3.setViewportView(reservations_customer_table);
		
//		EMPLOYEE RANKING:
		JPanel panel_2 = new JPanel();
		tabbedPane.addTab("Employee Rankings", null, panel_2, null);
		panel_2.setLayout(null);
		
		JLabel lblBestEmployee = new JLabel("Best Employee:");
		lblBestEmployee.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblBestEmployee.setBounds(118, 172, 122, 25);
		panel_2.add(lblBestEmployee);
		
		JLabel lblWorstEmployee = new JLabel("Worst Employee: ");
		lblWorstEmployee.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblWorstEmployee.setBounds(118, 267, 122, 25);
		panel_2.add(lblWorstEmployee);
		
		final JLabel best_employee_label = new JLabel("");
		best_employee_label.setForeground(new Color(0, 128, 0));
		best_employee_label.setFont(new Font("Tahoma", Font.BOLD, 14));
		best_employee_label.setBounds(261, 172, 215, 18);
		panel_2.add(best_employee_label);
		
		final JLabel worst_employee_label = new JLabel("");
		worst_employee_label.setForeground(new Color(255, 0, 0));
		worst_employee_label.setFont(new Font("Tahoma", Font.BOLD, 14));
		worst_employee_label.setBounds(261, 267, 215, 17);
		panel_2.add(worst_employee_label);
		
		JLabel lblDuration = new JLabel("Duration: ");
		lblDuration.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblDuration.setBounds(170, 24, 148, 14);
		panel_2.add(lblDuration);
		
		emp_rank_start = new JTextField();
		emp_rank_start.setColumns(10);
		emp_rank_start.setBounds(310, 48, 86, 20);
		panel_2.add(emp_rank_start);
		
		emp_rank_end = new JTextField();
		emp_rank_end.setColumns(10);
		emp_rank_end.setBounds(310, 79, 86, 20);
		panel_2.add(emp_rank_end);
		
		JLabel label_4 = new JLabel("End Date (yyyy-mm-dd)");
		label_4.setBounds(170, 82, 138, 14);
		panel_2.add(label_4);
		
		JLabel label_5 = new JLabel("Start Date (yyyy-mm-dd)");
		label_5.setBounds(170, 51, 138, 14);
		panel_2.add(label_5);
		
		final JLabel emp_rank_start_error = new JLabel("");
		emp_rank_start_error.setForeground(Color.RED);
		emp_rank_start_error.setBounds(406, 54, 183, 14);
		panel_2.add(emp_rank_start_error);
		
		final JLabel emp_rank_end_error = new JLabel("");
		emp_rank_end_error.setForeground(Color.RED);
		emp_rank_end_error.setBounds(406, 85, 183, 14);
		panel_2.add(emp_rank_end_error);
		
		JPanel customer_panel = new JPanel();
		tabbedPane.addTab("Customers", null, customer_panel, null);
		customer_panel.setLayout(null);
		
		JLabel lblAverageStayDuration = new JLabel("Average Stay Duration: ");
		lblAverageStayDuration.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblAverageStayDuration.setBounds(153, 106, 148, 14);
		customer_panel.add(lblAverageStayDuration);
		
		final JLabel stay_duration_label = new JLabel("None");
		stay_duration_label.setFont(new Font("Dialog", Font.BOLD, 12));
		stay_duration_label.setBounds(311, 106, 86, 14);
		customer_panel.add(stay_duration_label);
		
		stay_start_date_field = new JTextField();
		stay_start_date_field.setColumns(10);
		stay_start_date_field.setBounds(293, 130, 86, 20);
		customer_panel.add(stay_start_date_field);
		
		stay_end_date_field = new JTextField();
		stay_end_date_field.setColumns(10);
		stay_end_date_field.setBounds(293, 161, 86, 20);
		customer_panel.add(stay_end_date_field);
		
		JLabel label_2 = new JLabel("End Date (yyyy-mm-dd)");
		label_2.setBounds(153, 164, 138, 14);
		customer_panel.add(label_2);
		
		JLabel label_3 = new JLabel("Start Date (yyyy-mm-dd)");
		label_3.setBounds(153, 133, 138, 14);
		customer_panel.add(label_3);
		
		final JLabel stay_start_error = new JLabel("");
		stay_start_error.setForeground(Color.RED);
		stay_start_error.setBounds(389, 136, 183, 14);
		customer_panel.add(stay_start_error);
		
		final JLabel stay_end_error = new JLabel("");
		stay_end_error.setForeground(Color.RED);
		stay_end_error.setBounds(389, 167, 183, 14);
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
					int averageStay = (int)_queryManager.getAverageStayDuration(startDate, endDate);
					stay_duration_label.setText(String.valueOf(averageStay) + " days");
				}
			}
		});
		btnFindAverageStay.setBounds(153, 189, 226, 20);
		customer_panel.add(btnFindAverageStay);
		
//		USER MANAGEMENT
		JPanel panel_3 = new JPanel();
		tabbedPane.addTab("User Management", null, panel_3, null);
		panel_3.setLayout(null);
		
		JLabel lblRemoveCustomer = new JLabel("Remove Customer");
		lblRemoveCustomer.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblRemoveCustomer.setBounds(168, 85, 140, 14);
		panel_3.add(lblRemoveCustomer);
		
		JLabel lblName = new JLabel("Name");
		lblName.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblName.setBounds(168, 124, 46, 14);
		panel_3.add(lblName);
		
		JLabel lblPhone = new JLabel("Phone #");
		lblPhone.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblPhone.setBounds(168, 166, 59, 14);
		panel_3.add(lblPhone);
		
		cust_rem_name = new JTextField();
		cust_rem_name.setBounds(253, 122, 111, 20);
		panel_3.add(cust_rem_name);
		cust_rem_name.setColumns(10);
		
		cust_rem_phone = new JTextField();
		cust_rem_phone.setBounds(253, 164, 111, 20);
		panel_3.add(cust_rem_phone);
		cust_rem_phone.setColumns(10);
		
		final JLabel rem_status = new JLabel("");
		rem_status.setForeground(new Color(255, 69, 0));
		rem_status.setBounds(168, 251, 196, 14);
		panel_3.add(rem_status);
		
		JLabel um_name_error = new JLabel("");
		um_name_error.setForeground(Color.RED);
		um_name_error.setBounds(375, 125, 183, 14);
		panel_3.add(um_name_error);
		
		JLabel um_phone_error = new JLabel("");
		um_phone_error.setForeground(Color.RED);
		um_phone_error.setBounds(375, 167, 183, 14);
		panel_3.add(um_phone_error);
		
		JButton btnNewButton_1 = new JButton("Remove Customer");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				clearLabel(um_name_error);
				clearLabel(um_phone_error);
				
				boolean allValid = true;
				allValid = validateNotEmpty(um_name_error, cust_rem_name) && allValid;
				allValid = validateNotEmpty(um_phone_error, cust_rem_phone) && allValid;
				
				if(allValid){
					_queryManager.deleteCustomer(new Customer(cust_rem_name.getText(), cust_rem_phone.getText(), "NULL"));
					rem_status.setText("Removed Customer: " + cust_rem_name.getText());
					cust_rem_name.setText("");
					cust_rem_phone.setText("");
				}
			}
		});
		btnNewButton_1.setBounds(168, 204, 196, 23);
		panel_3.add(btnNewButton_1);
		
//		LOG OUT
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
		btnFindBestAnd.setBounds(170, 107, 226, 20);
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
		
		List<Customer> customers = _queryManager.getValuedCustomers();
		for(Customer customer : customers){
			Object[] rowdata = new Object[] { customer.getName(), customer.getPhoneNumber()};
			current_customer_model.addRow(rowdata);
		}
		
		table.setModel(current_customer_model);
	}
	
	private void RefreshRevenueReportSelected(JTable table, boolean month, boolean day, boolean year) {
		DefaultTableModel revenue_model = new DefaultTableModel(){
			@Override
		    public boolean isCellEditable(int row, int column) {
		        return false;
		    }
		};
		List<String> headers = new ArrayList<String>();
		SimpleDateFormat sdf = new SimpleDateFormat();
		if(month) {
			headers.add("Month"); 
			sdf.applyPattern("MMM yyyy");
		}
		else if(day){
			headers.add("Day");
			sdf.applyPattern("MMM dd, yyyy");
		}
		else if(year){
			headers.add("Year");
			sdf.applyPattern("yyyy");
		}
		
		headers.add("Number of Reservations");
		headers.add("Revenue");
		
		revenue_model.setColumnIdentifiers(headers.toArray());
		List<RevenueReport> reports = _queryManager.produceRevenueReport(headers.get(0).toLowerCase());
		for(RevenueReport r : reports){
			List<Object> data = new ArrayList<Object>();
			if(month) {
				data.add(sdf.format(r.getDate().getTime()));
			}
			if(day) {
				data.add(sdf.format(r.getDate().getTime()));
			}
			if(year) {
				data.add(sdf.format(r.getDate().getTime()));
			}
			
			data.add(r.getNumReservations());
			data.add("$" + r.getRevenue());		
			
			revenue_model.addRow(data.toArray());
		}
		
		table.setModel(revenue_model);
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
		if(deposit){headers.add("Confirmation Number");}
		
		reservation_model.setColumnIdentifiers(headers.toArray());
		
		List<Reservation> reservations = _queryManager.getReservations(name, phone, checkin, checkout, room_number, deposit);
		for(Reservation reservation : reservations){
			List<Object> data = new ArrayList<Object>();
			if(checkin){data.add(SqlDateFormatHelper.CalendarToSqlDateString(reservation.getCheckinDate()));}
			if(checkout){data.add(SqlDateFormatHelper.CalendarToSqlDateString(reservation.getCheckoutDate()));}
			if(room_number){data.add(reservation.getRoom().getRoomNumber());}
			if(deposit){data.add("#" + reservation.getConfirmationNumber());}
			reservation_model.addRow(data.toArray());
		}
		
		table.setModel(reservation_model);
	}
	
	private void RefreshEmployeeRankings(JLabel bestLabel, JLabel worstLabel, Calendar start, Calendar end){
		Employee bestEmployee = _queryManager.getBestEmployee(start, end);
		Employee worstEmployee = _queryManager.getWorstEmployee(start, end);
		
		String bestEmployeeName = "No Data";
		if(bestEmployee != null){
			bestEmployeeName = bestEmployee.getName();
		}
		
		String worstEmployeeName = "No Data";
		if(worstEmployee != null){
			worstEmployeeName = worstEmployee.getName();
		}
		
		bestLabel.setText(bestEmployeeName);
		worstLabel.setText(worstEmployeeName);
	}
	
	private boolean validateNotEmpty(JLabel errorLabel, JTextField field){
		if(field.getText().equals("")){
			errorLabel.setText("Can not be empty!");
			return false;
		}
		return true;
	}
	
	private void clearLabel(JLabel label){
		label.setText("");
	}
}
