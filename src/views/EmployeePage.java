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
		
		JLabel popular_room_label = new JLabel("None");
		popular_room_label.setFont(new Font("Dialog", Font.BOLD, 12));
		popular_room_label.setBounds(263, 238, 86, 14);
		room_panel.add(popular_room_label);
		
		JLabel start_error = new JLabel("");
		start_error.setForeground(Color.RED);
		start_error.setBounds(364, 264, 183, 14);
		room_panel.add(start_error);
		
		JLabel end_error = new JLabel("");
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
		tabbedPane.addTab("Customer", null, customer_panel, null);
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
		
		JLabel stay_duration_label = new JLabel("None");
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
		
		JLabel stay_start_error = new JLabel("");
		stay_start_error.setForeground(Color.RED);
		stay_start_error.setBounds(364, 248, 183, 14);
		customer_panel.add(stay_start_error);
		
		JLabel stay_end_error = new JLabel("");
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
}
