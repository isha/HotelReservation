package data_classes;

public class RoomType {
	
	private String _type;
	private int _security_deposit;
	private int _daily_rate;
	
	public RoomType(String type, int security_deposit, int daily_rate){
		_type = type;
		_security_deposit = security_deposit;
		_daily_rate = daily_rate;
	}
	
	public String getType(){
		return _type;
	}
	
	public int getSecurityDeposit(){
		return _security_deposit;
	}
	
	public int getDailyRate(){
		return _daily_rate;
	}
}
