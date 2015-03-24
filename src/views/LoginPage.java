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
import Interfaces.IQueryManager;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class LoginPage {

	private JFrame frame;
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
					LoginPage window = new LoginPage(new MockQueryManager());
					window.frame.setVisible(true);
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
		frame = new JFrame();
		frame.setBounds(100, 100, 664, 434);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(Main.class.getResource("/resources/8652.png")));
		frame.getContentPane().setLayout(null);
		
		JLabel lblWelcomToThe = new JLabel("Welcom to the Hotel Reservation System!");
		lblWelcomToThe.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblWelcomToThe.setBounds(110, 11, 423, 25);
		frame.getContentPane().add(lblWelcomToThe);
		
		cust_name = new JTextField();
		cust_name.setBounds(130, 150, 121, 20);
		frame.getContentPane().add(cust_name);
		cust_name.setColumns(10);
		
		JLabel lblUsername = new JLabel("Name");
		lblUsername.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblUsername.setBounds(47, 156, 73, 14);
		frame.getContentPane().add(lblUsername);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblPassword.setBounds(47, 214, 73, 14);
		frame.getContentPane().add(lblPassword);
		
		cust_pass = new JPasswordField();
		cust_pass.setBounds(130, 212, 121, 20);
		frame.getContentPane().add(cust_pass);
		
		JButton emp_login_btn = new JButton("Login as Employee");
		emp_login_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Main newFrame = new Main();
				newFrame.setVisible(true);
				frame.setVisible(false);
			}
		});
		emp_login_btn.setBounds(402, 257, 204, 23);
		frame.getContentPane().add(emp_login_btn);
		
		JButton cust_login_btm = new JButton("Login as Customer");
		cust_login_btm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				CustomerPage customerPage = new CustomerPage(_queryManager, _queryManager.getCustomer(cust_name.getText(), cust_phone.getText()));
				customerPage.setVisible(true);
				frame.setVisible(false);
			}
		});
		cust_login_btm.setBounds(47, 257, 204, 23);
		frame.getContentPane().add(cust_login_btm);
		
		cust_phone = new JTextField();
		cust_phone.setBounds(130, 181, 121, 20);
		frame.getContentPane().add(cust_phone);
		cust_phone.setColumns(10);
		
		JLabel lblPhone = new JLabel("Phone #");
		lblPhone.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblPhone.setBounds(47, 187, 60, 14);
		frame.getContentPane().add(lblPhone);
		
		JLabel lblEid = new JLabel("EID");
		lblEid.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblEid.setBounds(402, 180, 46, 14);
		frame.getContentPane().add(lblEid);
		
		JLabel lblPassword_1 = new JLabel("Password");
		lblPassword_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblPassword_1.setBounds(402, 211, 66, 14);
		frame.getContentPane().add(lblPassword_1);
		
		eid = new JTextField();
		eid.setBounds(490, 177, 116, 20);
		frame.getContentPane().add(eid);
		eid.setColumns(10);
		
		passwordField_1 = new JPasswordField();
		passwordField_1.setBounds(500, 181, -17, 20);
		frame.getContentPane().add(passwordField_1);
		
		emp_pass = new JPasswordField();
		emp_pass.setBounds(490, 208, 116, 20);
		frame.getContentPane().add(emp_pass);
		
		JLabel lblCustomerLogin = new JLabel("Customer Login");
		lblCustomerLogin.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblCustomerLogin.setBounds(47, 116, 204, 23);
		frame.getContentPane().add(lblCustomerLogin);
		
		JLabel lblEmployeeLogin = new JLabel("Employee Login");
		lblEmployeeLogin.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblEmployeeLogin.setBounds(402, 118, 204, 25);
		frame.getContentPane().add(lblEmployeeLogin);
	}
}
