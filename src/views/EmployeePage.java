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
		lblOccupiedRooms.setBounds(39, 21, 110, 14);
		room_panel.add(lblOccupiedRooms);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(39, 46, 476, 164);
		room_panel.add(scrollPane);
		
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
		
		occupied_room_table = new JTable();
		scrollPane.setViewportView(occupied_room_table);
		occupied_room_table.setModel(dtm);
		
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
		
		
		
		
		JPanel cust_panel = new JPanel();
		tabbedPane.addTab("Customers", null, cust_panel, null);
	}
}
