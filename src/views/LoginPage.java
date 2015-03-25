package views;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.SwingConstants;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JPasswordField;

import Implementations.MockQueryManager;
import Implementations.QueryManager;
import Interfaces.IQueryManager;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class LoginPage extends JFrame{

	private JTextField cust_name;
	private JPasswordField cust_pass;
	private JTextField cust_phone;
	private JTextField eid;
	private JPasswordField passwordField_1;
	private JPasswordField emp_pass;
	
	private IQueryManager _queryManager;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginPage window = new LoginPage(new QueryManager());
					window.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public LoginPage() {
		initialize();
	}
	
	public LoginPage(IQueryManager queryManager) {
		_queryManager = queryManager;
		initialize();
	}


	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		setBounds(100, 100, 664, 434);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setIconImage(Toolkit.getDefaultToolkit().getImage(Main.class.getResource("/resources/8652.png")));
		getContentPane().setLayout(null);
		
		JLabel lblWelcomToThe = new JLabel("Welcom to the Hotel Reservation System!");
		lblWelcomToThe.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblWelcomToThe.setBounds(110, 11, 423, 25);
		getContentPane().add(lblWelcomToThe);
		
		cust_name = new JTextField();
		cust_name.setBounds(130, 161, 121, 20);
		getContentPane().add(cust_name);
		cust_name.setColumns(10);
		
		JLabel lblUsername = new JLabel("Name");
		lblUsername.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblUsername.setBounds(47, 167, 73, 14);
		getContentPane().add(lblUsername);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblPassword.setBounds(47, 261, 73, 14);
		getContentPane().add(lblPassword);
		
		cust_pass = new JPasswordField();
		cust_pass.setBounds(130, 259, 121, 20);
		getContentPane().add(cust_pass);
		
		JLabel emp_eid_error = new JLabel("");
		emp_eid_error.setForeground(Color.RED);
		emp_eid_error.setBounds(402, 230, 209, 14);
		getContentPane().add(emp_eid_error);
		
		JLabel emp_pwd_error = new JLabel("");
		emp_pwd_error.setForeground(Color.RED);
		emp_pwd_error.setBounds(367, 280, 271, 14);
		getContentPane().add(emp_pwd_error);
		
		eid = new JTextField();
		eid.setBounds(490, 199, 116, 20);
		getContentPane().add(eid);
		eid.setColumns(10);
		
		emp_pass = new JPasswordField();
		emp_pass.setBounds(490, 255, 116, 20);
		getContentPane().add(emp_pass);
		
		JButton emp_login_btn = new JButton("Login as Employee");
		emp_login_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				clearLabel(emp_eid_error);
				clearLabel(emp_pwd_error);
				
				boolean allValid = true;
				allValid = validateNotEmpty(emp_eid_error, eid) && allValid;
				allValid = validateNotEmpty(emp_pwd_error, emp_pass) && allValid;
				allValid = validateNumeric(emp_eid_error, eid) && allValid;
				allValid = validatePasswordLength(emp_pwd_error, emp_pass, 6) && allValid;
				
				if(allValid){
					EmployeePage empPage = new EmployeePage(_queryManager, _queryManager.getEmployee(Integer.valueOf(eid.getText())));
					empPage.setVisible(true);
					setVisible(false);
				}
			}
		});
		emp_login_btn.setBounds(402, 305, 204, 23);
		getContentPane().add(emp_login_btn);
		
		JLabel cust_name_error = new JLabel("");
		cust_name_error.setForeground(new Color(255, 0, 0));
		cust_name_error.setBounds(47, 184, 204, 14);
		getContentPane().add(cust_name_error);
		
		JLabel cust_phone_error = new JLabel("");
		cust_phone_error.setForeground(Color.RED);
		cust_phone_error.setBounds(47, 240, 204, 14);
		getContentPane().add(cust_phone_error);
		
		JLabel cust_pwd_error = new JLabel("");
		cust_pwd_error.setForeground(Color.RED);
		cust_pwd_error.setBounds(47, 286, 276, 14);
		getContentPane().add(cust_pwd_error);
		
		JButton cust_login_btm = new JButton("Login as Customer");
		cust_login_btm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				clearLabel(cust_name_error);
				clearLabel(cust_phone_error);
				clearLabel(cust_pwd_error);
				
				boolean allValid = true;
				allValid = validateNotEmpty(cust_name_error, cust_name) && allValid;
				allValid = validateNotEmpty(cust_phone_error, cust_phone) && allValid;
				allValid = validateNotEmpty(cust_pwd_error, cust_pass) && allValid;
				allValid = validatePasswordLength(cust_pwd_error, cust_pass, 6) && allValid;
				
				if(allValid){
					CustomerPage customerPage = new CustomerPage(_queryManager, _queryManager.getCustomer(cust_name.getText(), cust_phone.getText()));
					customerPage.setVisible(true);
					setVisible(false);
				}
			}
		});
		
		cust_login_btm.setBounds(47, 305, 204, 23);
		getContentPane().add(cust_login_btm);
		
		cust_phone = new JTextField();
		cust_phone.setBounds(130, 209, 121, 20);
		getContentPane().add(cust_phone);
		cust_phone.setColumns(10);
		
		JLabel lblPhone = new JLabel("Phone #");
		lblPhone.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblPhone.setBounds(47, 211, 60, 14);
		getContentPane().add(lblPhone);
		
		JLabel lblEid = new JLabel("EID");
		lblEid.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblEid.setBounds(402, 202, 46, 14);
		getContentPane().add(lblEid);
		
		JLabel lblPassword_1 = new JLabel("Password");
		lblPassword_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblPassword_1.setBounds(402, 258, 66, 14);
		getContentPane().add(lblPassword_1);
		
		JLabel lblCustomerLogin = new JLabel("Customer Login");
		lblCustomerLogin.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblCustomerLogin.setBounds(47, 116, 204, 23);
		getContentPane().add(lblCustomerLogin);
		
		JLabel lblEmployeeLogin = new JLabel("Employee Login");
		lblEmployeeLogin.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblEmployeeLogin.setBounds(402, 118, 204, 25);
		getContentPane().add(lblEmployeeLogin);
	}
	
	private boolean validateNotEmpty(JLabel errorLabel, JTextField field){
		if(field.getText().equals("")){
			errorLabel.setText("Can not be empty!");
			return false;
		}
		return true;
	}
	
	private boolean validatePasswordLength(JLabel errorLabel, JPasswordField field, int minLength){
		if(field.getText().length() < minLength){
			errorLabel.setText("Password must be at least " + minLength + " characters long!");
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
	
	private void clearLabel(JLabel label){
		label.setText("");
	}
}
