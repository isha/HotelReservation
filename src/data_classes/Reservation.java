package data_classes;

public class Reservation {

	private int _conf_no;
	private String _checkin_date;
	private String _checkout_date;
	private String _comments;
	private Room _room;
	private Customer _customer;
	private CreditCard _credit_card;
	private Employee _employee;
	
	public Reservation(int conf_no, String checkin_date, String checkout_date, String comments, Room room, Customer customer, CreditCard credit_card, Employee employee){
		_conf_no = conf_no;
		_checkin_date = checkin_date;
		_checkout_date = checkout_date;
		_comments = comments;
		_room = room;
		_customer = customer;
		_credit_card = credit_card;
		_employee = employee;
	}
	
	public int getConfirmationNumber(){
		return _conf_no;
	}
	
	public String getCheckinDate(){
		return _checkin_date;
	}
	
	public String getCheckoutDate(){
		return _checkout_date;
	}
	
	public String getComments(){
		return _comments;
	}
	
	public Room getRoom(){
		return _room;
	}
	
	public Customer getCustomer(){
		return _customer;
	}
	
	public CreditCard getCreditCard(){
		return _credit_card;
	}
	
	public Employee getEmployee(){
		return _employee;
	}
}
