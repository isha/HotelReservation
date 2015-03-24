package views;

import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;

import data_classes.Customer;
import Interfaces.IQueryManager;
import javax.swing.JLabel;
import java.awt.Font;

public class CustomerPage extends JFrame{

	private JFrame frmCustomerMackenzie;
	
	private IQueryManager _queryManager;
	private Customer _customer;

	/**
	 * Create the application.
	 */
	public CustomerPage() {
		initialize();
	}
	
	public CustomerPage(IQueryManager queryManager, Customer customer){
		_queryManager = queryManager;
		_customer = customer;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		setTitle("MacKenzie");
		setBounds(100, 100, 709, 469);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setIconImage(Toolkit.getDefaultToolkit().getImage(Main.class.getResource("/resources/8652.png")));
		getContentPane().setLayout(null);
		
		JLabel welcome_text = new JLabel("Welcome " + _customer.getName() + "!");
		welcome_text.setFont(new Font("Tahoma", Font.BOLD, 14));
		welcome_text.setBounds(32, 28, 587, 14);
		getContentPane().add(welcome_text);
	}
}
