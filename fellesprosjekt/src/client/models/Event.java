import java.util.ArrayList;
import java.util.Date;

public class Event {
	
	private int id;
	private Date start;
	private Date end;
	private String name, description;
	private String location;
	private int bookingId;
	private boolean isMeeting;
	private ArrayList<User> participants = new ArrayList<User>();
	
	public Event(int id, Date start, Date end, String name, String description,
			String location, int bookingId, boolean isMeeting,
			ArrayList<User> participants) {
		this.id = id;
		this.name = name;
		this.start = start;
		this.end = end;
		this.description = description;
		this.location = location;
		this.bookingId = bookingId;
		this.isMeeting = isMeeting;
		this.participants = participants;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Event(int id, String name, Date start, Date end){
		this.id = id;
		this.name = name;
		this.start = start;
		this.end = end;
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

	public int getBookingId() {
		return bookingId;
	}

	public void setBookingId(int bookingId) {
		this.bookingId = bookingId;
	}

	public boolean isMeeting() {
		return isMeeting;
	}

	public void setMeeting(boolean isMeeting) {
		this.isMeeting = isMeeting;
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
	
}