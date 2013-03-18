package common.models;

public class Booking {
	int booking_id; 
	String room_name; 
	int event_id; 
	 
	
	public Booking(int id, String room_name, int event_id)
	{
		this.booking_id = id; 
		this.room_name = room_name; 
		this.event_id = event_id; 
	}
	public int getBooking_id() {
		return booking_id;
	}
	public void setBooking_id(int booking_id) {
		this.booking_id = booking_id;
	}
	public String getRoom_name() {
		return room_name;
	}
	public void setRoom_name(String room_name) {
		this.room_name = room_name;
	}
	public int getEvent_id() {
		return event_id;
	}
	public void setEvent_id(int event_id) {
		this.event_id = event_id;
	}
	
}
