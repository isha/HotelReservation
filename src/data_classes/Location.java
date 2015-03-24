package data_classes;

public class Location {
	
	private int _address_no;
	private String _street;
	private String _postal_code;
	private String _city;
	private String _province;
	
	public Location(int address_no, String street, String postal_code, String city, String province){
		_address_no = address_no;
		_street = street;
		_postal_code = postal_code;
		_city = city;
		_province = province;
	}
	
	public int getAddressNumber(){
		return _address_no;
	}
	
	public String getStreet(){
		return _street;
	}
	
	public String getPostalCode(){
		return _postal_code;
	}
	
	public String getCity(){
		return _city;
	}
	
	public String getProvince(){
		return _province;
	}
}
