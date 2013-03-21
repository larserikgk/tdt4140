package common.models;

import java.util.ArrayList;
import java.util.Date;

public class Event implements Comparable<Event>{
	
	private User admin;
	private int id;
	private Date start;
	private Date end;
	private String name, description;
	private String location;
	private ArrayList<User> participants = new ArrayList<User>();
	private Room room;  
	
	public Event(User admin, int id, Date start, Date end, String name,
			String description, String location, ArrayList<User> participants,
			Room room) {
		super();
		this.admin 	= admin;
		this.id 	= id;
		this.start 	= start;
		this.end 	= end;
		this.name 	= name;
		this.description 	= description;
		this.location 		= location;
		this.participants 	= participants;
		this.room 			= room;
	}

	public User getAdmin() {
		return admin;
	}
	
	public void setAdmin(User admin) {
		this.admin = admin;
	}
	
	public Event() {
		// TODO Auto-generated constructor stub
	}
	
	public void delete() {
		for (User participant : participants) {
			participant.deleteEvent(this);
		}
		return;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		if (end.compareTo(start)>=0) this.end = end;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public boolean isMeeting() {
		return participants.size()>1;
	}

	public ArrayList<User> getParticipants() {
		return participants;
	}

	public void setParticipants(ArrayList<User> participants) {
		this.participants = participants;
	}
	
	public void addParticipant(User user){
		participants.add(user);
	}
	
	public void removeParticipant(User user){
		participants.remove(user);
	}
	public Room getRoom() {
		return room;
	}
	public void setRoom(Room room) {
		this.room = room;
	}
	public String toString()
	{
		return(name); 
	}
	
	public Event clone() {
		try {
			return (Event) super.clone();
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public int compareTo(Event arg0) {
		return this.getStart().compareTo(arg0.getStart());
	}
	
	
}