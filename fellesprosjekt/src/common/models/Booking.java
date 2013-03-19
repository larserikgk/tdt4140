package common.models;

import java.util.Date;

public class Booking {
	int booking_id;
	Event event; 
	Room room; 
	
	public Booking(Room room, Event event)
	{
		this.room = room; 
		this.event = event; 
	} 
	
	public int getBooking_id() {
		return booking_id;
	}
	public void setBooking_id(int booking_id) {
		this.booking_id = booking_id;
	}
	
	
}
