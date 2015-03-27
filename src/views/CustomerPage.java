package views;

import java.awt.EventQueue;
import java.awt.TextField;
import java.awt.Toolkit;

import javax.swing.JFrame;

import data_classes.CreditCard;
import data_classes.Customer;
import data_classes.Location;
import data_classes.Reservation;
import data_classes.Room;
import data_classes.RoomType;
import Helpers.SqlDateFormatHelper;
import Interfaces.IQueryManager;

import javax.swing.JLabel;

import java.awt.Font;
import java.util.List;

import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class CustomerPage extends JFrame{
	private JPanel contentPane;
	private JFrame frmCustomerMackenzie;
	private IQueryManager _queryManager;
	private Customer _customer;
	private JTable reservation_table;
	private JTable available_rooms_table;
	private JPasswordField old_pass;
	private JPasswordField new_pass;
	private JPasswordField new_pass_conf;
	private JTextField ccNumber_field;
	private JTextField ccExpireDate_field;
	private JTextField addressNo_field;
	private JTextField street_field;
	private JTextField postal_field;
	private JTextField city_field;
	private JTextField prov_field;
	private JTextField checkin_field;
	private JTextField checkout_field;
	/**
	 * Create the application.
	 */
	public CustomerPage() {
		initialize();
	}
	
	public CustomerPage(IQueryManager queryManager, Customer customer){
		_queryManager = queryManager;
		_customer = customer;
		setTitle("Customer: " + _customer.getName());
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
			
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 700, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		setIconImage(Toolkit.getDefaultToolkit().getImage(Main.class.getResource("/resources/8652.png")));
		contentPane.setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 0, 700, 480);
		contentPane.add(tabbedPane);
		
//		USER INFO & SETTINGS: 
		JPanel information_panel = new JPanel(); 
		tabbedPane.addTab("Your Information", null, information_panel, null);
		information_panel.setLayout(null);
		
		JLabel welcome_text = new JLabel("Welcome " + _customer.getName() + "!");
		welcome_text.setFont(new Font("Tahoma", Font.BOLD, 14));
		welcome_text.setBounds(10, 17, 522, 14);
		information_panel.add(welcome_text);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(220, 80, 370, 300);
		information_panel.add(scrollPane);
		
		reservation_table = new JTable();
		scrollPane.setViewportView(reservation_table);
		
		RefreshReservations(reservation_table);
		
		JButton refreshBtn = new JButton("Refresh Reservations");
		refreshBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				RefreshReservations(reservation_table);	
			}
		});
		refreshBtn.setBounds(220, 390, 180, 23);
		information_panel.add(refreshBtn);
		
		JLabel lblReservations = new JLabel("Your Reservations");
		lblReservations.setBounds(220, 50, 278, 30);
		information_panel.add(lblReservations);
		lblReservations.setFont(new Font("Tahoma", Font.BOLD, 14));
		
		JLabel lblChangePassword = new JLabel("Change Password");
		lblChangePassword.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblChangePassword.setBounds(33, 50, 150, 30);
		information_panel.add(lblChangePassword);

		JLabel lblNewLabel = new JLabel("Old Password");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel.setBounds(34, 93, 83, 14);
		information_panel.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("New Password");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_1.setBounds(33, 161, 117, 14);
		information_panel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("New Password (Retype)");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_2.setBounds(32, 231, 151, 20);
		information_panel.add(lblNewLabel_2);
		
		final JLabel new_pass_error = new JLabel("");
		new_pass_error.setForeground(new Color(255, 0, 0));
		new_pass_error.setBounds(33, 206, 246, 14);
		information_panel.add(new_pass_error);
		
		final JLabel new_pass_re_error = new JLabel("");
		new_pass_re_error.setForeground(Color.RED);
		new_pass_re_error.setBounds(32, 283, 246, 14);
		information_panel.add(new_pass_re_error);
		
		old_pass = new JPasswordField();
		old_pass.setBounds(33, 108, 150, 20);
		information_panel.add(old_pass);
		
		new_pass = new JPasswordField();
		new_pass.setBounds(32, 175, 151, 20);
		information_panel.add(new_pass);
		
		new_pass_conf = new JPasswordField();
		new_pass_conf.setBounds(32, 252, 151, 20);
		information_panel.add(new_pass_conf);
		
		final JLabel old_pass_error = new JLabel("");
		old_pass_error.setForeground(Color.RED);
		old_pass_error.setBounds(33, 139, 246, 14);
		information_panel.add(old_pass_error);
		
		JButton changePassBtn = new JButton("Change Password");
		changePassBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				clearLabel(old_pass_error);
				clearLabel(new_pass_error);
				clearLabel(new_pass_re_error);
				
				boolean allValid = true;
				allValid =  validateCorrectPassword(old_pass_error, old_pass) && allValid;
				allValid = validatePasswordLength(new_pass_error, new_pass, 6) && allValid;
				allValid = validatePasswordMatch(new_pass_error, new_pass_re_error, new_pass, new_pass_conf) && allValid;
				
				if(allValid){
					_queryManager.updateCustomerPassword(_customer, new_pass.getText());
					_customer = _queryManager.getCustomer(_customer.getName(), _customer.getPhoneNumber());
					PasswordChanged passwordAlert = new PasswordChanged();
					passwordAlert.setVisible(true);
					old_pass.setText("");
					new_pass.setText("");
					new_pass_conf.setText("");
				}
			}
		});
		changePassBtn.setBounds(32, 305, 151, 23);
		information_panel.add(changePassBtn);
		
//		MAKE RESERVATION
		final JPanel reservation_panel = new JPanel(); 
		tabbedPane.addTab("Make a Reservation", null, reservation_panel, null);
		reservation_panel.setLayout(null);
		
//		Done Label
		final JLabel lblDone = new JLabel("Reservation Made!");
		lblDone.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblDone.setBounds(460, 386, 120, 14);
		lblDone.setVisible(false);
		reservation_panel.add(lblDone);
		
		JLabel lblAvailableRooms = new JLabel("Select an Available Room:");
		lblAvailableRooms.setBounds(15, 85, 182, 15);
		lblAvailableRooms.setFont(new Font("Tahoma", Font.BOLD, 14));
		reservation_panel.add(lblAvailableRooms);
		
		JLabel lblDuration = new JLabel("When:");
		lblDuration.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblDuration.setBounds(15, 36, 55, 30);
		reservation_panel.add(lblDuration);

		JLabel lblCheckin = new JLabel("Check-In");
		lblCheckin.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblCheckin.setBounds(80, 11, 60, 14);
		reservation_panel.add(lblCheckin);

		checkin_field = new JTextField();
		checkin_field.setBounds(81, 43, 80, 20);
		reservation_panel.add(checkin_field);
		
		JLabel lblCheckout = new JLabel("Check-Out");
		lblCheckout.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblCheckout.setBounds(180, 11, 60, 14);
		reservation_panel.add(lblCheckout);

		checkout_field = new JTextField();
		checkout_field.setBounds(171, 43, 80, 20);
		reservation_panel.add(checkout_field);
		
		available_rooms_table = new JTable();
		available_rooms_table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		JScrollPane scrollPane2 = new JScrollPane();
		scrollPane2.setBounds(15, 100, 420, 300);
		scrollPane2.setViewportView(available_rooms_table);
		reservation_panel.add(scrollPane2);
		
		final JButton makeReservationBtn = new JButton("Make Reservation");
		makeReservationBtn.setEnabled(false);
		
		JLabel res_chkn_date_error = new JLabel("");
		res_chkn_date_error.setForeground(Color.RED);
		res_chkn_date_error.setBounds(41, 65, 120, 14);
		reservation_panel.add(res_chkn_date_error);
		
		JLabel res_chko_date_error = new JLabel("");
		res_chko_date_error.setForeground(Color.RED);
		res_chko_date_error.setBounds(171, 65, 120, 14);
		reservation_panel.add(res_chko_date_error);
		
		JButton findAvailableBtn = new JButton("Find Rooms");
		findAvailableBtn.setBounds(268, 43, 100, 20);
		findAvailableBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				clearLabel(res_chkn_date_error);
				clearLabel(res_chko_date_error);
				
				boolean allValid = true;
				allValid = validateDate(res_chkn_date_error, checkin_field) && allValid;
				allValid = validateDate(res_chko_date_error, checkout_field) && allValid;
				
				if(allValid){
					RefreshAvailableRooms(available_rooms_table);
					makeReservationBtn.setEnabled(true);
				}
			}
			}
		);
		
		reservation_panel.add(findAvailableBtn);
		
		JLabel lblPayment = new JLabel("Payment Info:");
		lblPayment.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblPayment.setBounds(460, 24, 150, 30);
		reservation_panel.add(lblPayment);

//		Credit Card Number
		JLabel lblCreditCardNumber = new JLabel("Credit Card #");
		lblCreditCardNumber.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblCreditCardNumber.setBounds(460, 48, 150, 20);
		reservation_panel.add(lblCreditCardNumber);
		
		ccNumber_field = new JTextField();
		ccNumber_field.setBounds(460, 68, 150, 20);
		reservation_panel.add(ccNumber_field);
		
//		Credit Card Expiry Date
		JLabel lblCreditCardExpiryDate = new JLabel("Expiry Date (yyyy-mm-dd)");
		lblCreditCardExpiryDate.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblCreditCardExpiryDate.setBounds(460, 110, 172, 14);
		reservation_panel.add(lblCreditCardExpiryDate);

		ccExpireDate_field = new JTextField();
		ccExpireDate_field.setBounds(460, 125, 80, 20);
		reservation_panel.add(ccExpireDate_field);
		
//		Credit Card Address No
		JLabel lblCCAddressNo = new JLabel("Street #");
		lblCCAddressNo.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblCCAddressNo.setBounds(460, 150, 80, 14);
		reservation_panel.add(lblCCAddressNo);

		addressNo_field = new JTextField();
		addressNo_field.setBounds(460, 170, 80, 20);
		reservation_panel.add(addressNo_field);
		
//		Credit Card Street
		JLabel lblCCStreet = new JLabel("Street Name");
		lblCCStreet.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblCCStreet.setBounds(549, 150, 83, 14);
		reservation_panel.add(lblCCStreet);

		street_field = new JTextField();
		street_field.setBounds(550, 170, 120, 20);
		reservation_panel.add(street_field);	
		
//		Credit Card Postal Code
		JLabel lblCCPostalCode = new JLabel("Postal Code");
		lblCCPostalCode.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblCCPostalCode.setBounds(460, 211, 83, 14);
		reservation_panel.add(lblCCPostalCode);

		postal_field = new JTextField();
		postal_field.setBounds(460, 231, 80, 20);
		reservation_panel.add(postal_field);		
		
//		Credit Card City
		JLabel lblCCCity = new JLabel("City");
		lblCCCity.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblCCCity.setBounds(460, 256, 90, 14);
		reservation_panel.add(lblCCCity);

		city_field = new JTextField();
		city_field.setBounds(460, 276, 80, 20);
		reservation_panel.add(city_field);
		
//		Credit Card Province
		JLabel lblProvince = new JLabel("Province");
		lblProvince.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblProvince.setBounds(460, 301, 83, 14);
		reservation_panel.add(lblProvince);

		prov_field = new JTextField();
		prov_field.setBounds(460, 321, 80, 20);
		reservation_panel.add(prov_field);		
		
		JLabel res_expiry_date_error = new JLabel("");
		res_expiry_date_error.setForeground(Color.RED);
		res_expiry_date_error.setBounds(550, 128, 120, 14);
		reservation_panel.add(res_expiry_date_error);
		
		JLabel lblyyyymmdd_1 = new JLabel("(yyyy-mm-dd)");
		lblyyyymmdd_1.setBounds(180, 24, 81, 14);
		reservation_panel.add(lblyyyymmdd_1);
		
		JLabel lblyyyymmdd = new JLabel("(yyyy-mm-dd)");
		lblyyyymmdd.setBounds(80, 24, 81, 14);
		reservation_panel.add(lblyyyymmdd);
		
		JLabel street_num_error = new JLabel("");
		street_num_error.setForeground(Color.RED);
		street_num_error.setBounds(460, 190, 150, 14);
		reservation_panel.add(street_num_error);
		
		JLabel room_pick_error = new JLabel("");
		room_pick_error.setForeground(Color.RED);
		room_pick_error.setBounds(207, 85, 192, 14);
		reservation_panel.add(room_pick_error);
		
		JLabel cc_number_error = new JLabel("");
		cc_number_error.setForeground(Color.RED);
		cc_number_error.setBounds(460, 87, 161, 14);
		reservation_panel.add(cc_number_error);
		
		makeReservationBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				int row = available_rooms_table.getSelectedRow();
				TableModel dtm = available_rooms_table.getModel();
				
				clearLabel(res_expiry_date_error);
				clearLabel(street_num_error);
				clearLabel(room_pick_error);
				clearLabel(cc_number_error);
				
				boolean allValid = true;
				allValid = validateDate(res_expiry_date_error, ccExpireDate_field) && allValid;
				allValid = validateNumeric(street_num_error, addressNo_field) && allValid;
				allValid = validateRoomPick(room_pick_error, available_rooms_table) && allValid;
				allValid = validateNotEmpty(cc_number_error, ccNumber_field) && allValid;
				
				if(allValid){
					System.out.println("Making Reservation~");
					
					RoomType roomType = new RoomType(dtm.getValueAt(row, 0).toString(), Integer.parseInt(dtm.getValueAt(row, 6).toString()), 
							Integer.parseInt(dtm.getValueAt(row, 5).toString()));
					Location roomLoc = new Location(Integer.parseInt(dtm.getValueAt(row, 2).toString()), dtm.getValueAt(row, 3).toString(), 
							dtm.getValueAt(row, 4).toString(), null, null);
					Room room = new Room(Integer.parseInt(dtm.getValueAt(row, 1).toString()), roomType, roomLoc);
					Location ccLocation = new Location(Integer.parseInt(addressNo_field.getText()), street_field.getText(), 
							postal_field.getText(), city_field.getText(), prov_field.getText());
					CreditCard cc = new CreditCard(ccNumber_field.getText(), SqlDateFormatHelper.SQLDateStringToCalendar(ccExpireDate_field.getText()), 
							ccLocation);
					_queryManager.makeReservation(_customer, room, cc, checkin_field.getText(), checkout_field.getText());
					lblDone.setVisible(true);
					RefreshReservations(reservation_table);
				}
			}
		});
		
		makeReservationBtn.setBounds(460, 352, 151, 23);
		reservation_panel.add(makeReservationBtn);	
			
		
//		LOG OUT
		JButton btnLogout = new JButton("Logout");
		btnLogout.setFont(new Font("Tahoma", Font.BOLD, 20));
		btnLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LoginPage login = new LoginPage(_queryManager);
				login.setVisible(true);
				setVisible(false);
			}
		});		
		tabbedPane.addTab("Logout", null, btnLogout, null);		
		
	}
	
	private void RefreshReservations(JTable table) { 
		DefaultTableModel dtm = new DefaultTableModel(){
			@Override
		    public boolean isCellEditable(int row, int column) {
		        return false;
		    }
		};
		String[] headers = new String[] {
				"Conf #", "Checkin", "Checkout", "Room #"
			};
		
		dtm.setColumnIdentifiers(headers);
		
		List<Reservation> reservations = _queryManager.getReservations(_customer);
		for(Reservation reservation : reservations){
			Object[] rowdata = new Object[] { reservation.getConfirmationNumber(), SqlDateFormatHelper.CalendarToSqlDateString(reservation.getCheckinDate()), SqlDateFormatHelper.CalendarToSqlDateString(reservation.getCheckoutDate()), reservation.getRoom().getRoomNumber()};
			dtm.addRow(rowdata);
		}

		table.setModel(dtm);
	}
	
	private void RefreshAvailableRooms(JTable table){
		DefaultTableModel dtm = new DefaultTableModel(){
			@Override
		    public boolean isCellEditable(int row, int column) {
		        return false;
		    }
		};
		
		String[] headers = new String[] {
				"Type", "Room #", "Address No.", "Street", "Postal Code", "Daily Rate", "Security Deposit"
			};
		
		dtm.setColumnIdentifiers(headers);
		List<Room> rooms = _queryManager.getAvailableRooms(SqlDateFormatHelper.SQLDateStringToCalendar(checkin_field.getText()), 
				SqlDateFormatHelper.SQLDateStringToCalendar(checkout_field.getText()));
		for(Room r : rooms){
			Object[] rowdata = new Object[] { r.getRoomType().getType(), r.getRoomNumber(), r.getLocation().getAddressNumber(), r.getLocation().getStreet(), 
					r.getLocation().getPostalCode(), r.getRoomType().getDailyRate(), r.getRoomType().getSecurityDeposit() };
			dtm.addRow(rowdata);
		}
		table.setModel(dtm);		
	}
	
	private boolean validatePasswordLength(JLabel errorLabel, JPasswordField field, int minLength){
		if(field.getText().length() < minLength){
			errorLabel.setText("Password must be at least " + minLength + " characters long!");
			return false;
		}
		return true;
	}
	
	private boolean validateNotEmpty(JLabel errorLabel, JTextField field){
		if(field.getText().equals("")){
			errorLabel.setText("Can not be empty!");
			return false;
		}
		return true;
	}
	
	private boolean validatePasswordMatch(JLabel errorLabel1, JLabel errorLabel2, JPasswordField field1, JPasswordField field2){
		if(!field1.getText().equals(field2.getText())){
			errorLabel1.setText("Passwords don't match!");
			errorLabel2.setText("Passwords don't match!");
			return false;
		}
		return true;
	}
	
	private boolean validateNumeric(JLabel errorLabel, JTextField field){
		try{
			Integer.valueOf(field.getText());
		}catch(NumberFormatException e){
			errorLabel.setText("Must be a number!");
			return false;
		}
		return true;
	}
	
	private boolean validateDate(JLabel errorLabel, JTextField field){
		if(SqlDateFormatHelper.isValidSqlDateString(field.getText())){
			return true;
		}else{
			errorLabel.setText("Invalid date format!");
			return false;
		}
	}
	
	private boolean validateRoomPick(JLabel errorLabel, JTable td){
		if(td.getSelectedRow() == -1){
			errorLabel.setText("Must pick a room!");
			return false;
		}else{
			return true;
		}
	}
	
	private boolean validateCorrectPassword(JLabel errorLabel, JPasswordField field){
		
		if(!field.getText().equals(_customer.getPassword())){
			errorLabel.setText("Incorrect Password!");
			return false;
		}
		return true;
	}
	
	private void clearLabel(JLabel label){
		label.setText("");
	}
}
