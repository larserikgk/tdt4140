package common.models;

import java.util.ArrayList;
import java.util.Date;

public class User {
	private String username;
	private String password;
	private String name;
	private EventCalendar eventCalendar;
	
	public User(String username, String password, String name){
		this.username = username;
		this.password = password;
		this.name = name;
		eventCalendar = new EventCalendar();
	}
	
	//brukt for querries. 
	public User(String username, String name)
	{
		this.name = name; 
		this.username = username; 
	}
	
	public void editEvent(Event newEvent, Event oldEvent) {
		deleteEvent(oldEvent);
		addEvent(newEvent);
	}
	
	public EventCalendar getEventCalendar() {
		return eventCalendar;
	}
	
	public void deleteEvent(Event event) {
		eventCalendar.remove(event);
	}
	
	public void addEvent(Event event) {
		eventCalendar.add(event, event.getStart());
		
	}
	
	public ArrayList<Event> getEvents(Date date) {
		return eventCalendar.getEvents(date);
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}	
}
