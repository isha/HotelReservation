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
import java.util.GregorianCalendar;
import java.util.List;

import javax.swing.JTable;
import javax.swing.JScrollPane;

import Interfaces.IQueryManager;
import data_classes.Employee;
import data_classes.Reservation;
import data_classes.Room;

public class EmployeePage extends JFrame {

	private JPanel contentPane;
	private JTable occupied_rooms;
	private JTable occupied_room_table;

	private IQueryManager _queryManager;
	private Employee _employee;

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
		scrollPane.setBounds(39, 46, 476, 282);
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
		
		JPanel cust_panel = new JPanel();
		tabbedPane.addTab("Customers", null, cust_panel, null);
	}
}
