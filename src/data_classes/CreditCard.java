package data_classes;

import java.util.Calendar;

public class CreditCard {

	private String _cc_number;
	private Calendar _expiry_date;
	private Location _location;
	
	public CreditCard(String cc_number, Calendar expiry_date, Location location){
		_cc_number = cc_number;
		_expiry_date = expiry_date;
		_location = location;
	}
	
	public String getCreditCardNumber(){
		return _cc_number;
	}
	
	public Calendar getExpiryDate(){
		return _expiry_date;
	}
	
	public Location getLocation(){
		return _location;
	}
}
