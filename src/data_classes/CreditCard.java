package data_classes;

public class CreditCard {

	private int _cc_number;
	private String _expiry_date;
	private Location _location;
	
	public CreditCard(int cc_number, String expiry_date, Location location){
		_cc_number = cc_number;
		_expiry_date = expiry_date;
		_location = location;
	}
	
	public int getCreditCardNumber(){
		return _cc_number;
	}
	
	public String getExpiryDate(){
		return _expiry_date;
	}
	
	public Location getLocation(){
		return _location;
	}
}
