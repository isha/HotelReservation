package views;

import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;

import data_classes.Customer;
import data_classes.Reservation;
import Helpers.SqlDateFormatHelper;
import Interfaces.IQueryManager;

import javax.swing.JLabel;

import java.awt.Font;
import java.util.List;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.JPasswordField;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class CustomerPage extends JFrame{

	private JFrame frmCustomerMackenzie;
	
	private IQueryManager _queryManager;
	private Customer _customer;
	private JTable reservation_table;
	private JPasswordField old_pass;
	private JPasswordField new_pass;
	private JPasswordField new_pass_conf;

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
		setBounds(100, 100, 709, 469);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setIconImage(Toolkit.getDefaultToolkit().getImage(Main.class.getResource("/resources/8652.png")));
		getContentPane().setLayout(null);
		
		JLabel welcome_text = new JLabel("Welcome " + _customer.getName() + "!");
		welcome_text.setFont(new Font("Tahoma", Font.BOLD, 14));
		welcome_text.setBounds(10, 17, 522, 14);
		getContentPane().add(welcome_text);
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
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(330, 97, 353, 322);
		getContentPane().add(scrollPane);
		
		
		
		reservation_table = new JTable();
		scrollPane.setViewportView(reservation_table);
		reservation_table.setModel(dtm);
		
		JLabel lblReservations = new JLabel("Reservations");
		lblReservations.setBounds(330, 81, 278, 15);
		getContentPane().add(lblReservations);
		lblReservations.setFont(new Font("Tahoma", Font.BOLD, 14));
		
		JLabel lblChangePassword = new JLabel("Change Password");
		lblChangePassword.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblChangePassword.setBounds(33, 83, 150, 30);
		getContentPane().add(lblChangePassword);
		
		JLabel lblNewLabel = new JLabel("Old Password");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel.setBounds(34, 124, 83, 14);
		getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("New Password");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_1.setBounds(33, 191, 117, 14);
		getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("New Password (Retype)");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_2.setBounds(32, 261, 151, 20);
		getContentPane().add(lblNewLabel_2);
		
		JLabel new_pass_error = new JLabel("");
		new_pass_error.setForeground(new Color(255, 0, 0));
		new_pass_error.setBounds(33, 236, 246, 14);
		getContentPane().add(new_pass_error);
		
		JLabel new_pass_re_error = new JLabel("");
		new_pass_re_error.setForeground(Color.RED);
		new_pass_re_error.setBounds(32, 313, 246, 14);
		getContentPane().add(new_pass_re_error);
		
		old_pass = new JPasswordField();
		old_pass.setBounds(33, 138, 150, 20);
		getContentPane().add(old_pass);
		
		new_pass = new JPasswordField();
		new_pass.setBounds(32, 205, 151, 20);
		getContentPane().add(new_pass);
		
		new_pass_conf = new JPasswordField();
		new_pass_conf.setBounds(32, 282, 151, 20);
		getContentPane().add(new_pass_conf);
		
		JLabel old_pass_error = new JLabel("");
		old_pass_error.setForeground(Color.RED);
		old_pass_error.setBounds(33, 169, 246, 14);
		getContentPane().add(old_pass_error);
		
		JButton btnNewButton = new JButton("Change Password");
		btnNewButton.addActionListener(new ActionListener() {
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
		btnNewButton.setBounds(33, 342, 151, 23);
		getContentPane().add(btnNewButton);
		
		JButton btnLogout = new JButton("Logout");
		btnLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LoginPage login = new LoginPage(_queryManager);
				login.setVisible(true);
				setVisible(false);
			}
		});
		btnLogout.setBounds(549, 11, 134, 30);
		getContentPane().add(btnLogout);
	}
	
	private boolean validatePasswordLength(JLabel errorLabel, JPasswordField field, int minLength){
		if(field.getText().length() < minLength){
			errorLabel.setText("Password must be at least " + minLength + " characters long!");
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
