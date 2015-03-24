package data_classes;

public class Customer {

	private String _name;
	private String _phone_number;
	private String _password;
	
	public Customer(String name, String phone_number, String password){
		_name = name;
		_phone_number = phone_number;
		_password = password;
	}
	
	public String getName(){
		return _name;
	}
	
	public String getPhoneNumber(){
		return _phone_number;
	}
	
	public String getPassword(){
		return _password;
	}
}
