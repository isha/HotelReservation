package data_classes;

public class Employee {
	private int _id;
	private String _name;
	private String _password;
	
	public Employee(int id, String name, String password){
		_id = id;
		_name = name;
		_password = password;
	}
	
	public int getId(){
		return _id;
	}
	
	public String getName(){
		return _name;
	}
	
	public String getPassword(){
		return _password;
	}
}

