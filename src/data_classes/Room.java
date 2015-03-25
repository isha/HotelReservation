package data_classes;

public class Room {

	private int _r_number;
	private RoomType _room_type;
	private Location _location;
	
	public Room(int r_number, RoomType room_type, Location location){
		_r_number = r_number;
		_room_type = room_type;
		_location = location;
	}
	
	public int getRoomNumber(){
		return _r_number;
	}
	
	public RoomType getRoomType(){
		return _room_type;
	}
	
	public Location getLocation(){
		return _location;
	}
	
	public void setRoomType(RoomType roomType) { 
		_room_type = roomType;
	}
}
