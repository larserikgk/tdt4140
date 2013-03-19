package common.models;

import java.util.Date;

public class Notification {
	
	public enum NotificationType {INVITATION, INV_RESPONSE, EVENT_UPDATE};
	
	private int id;
	private NotificationType type;
	private String description;
	private Event event;

	public Notification(int id, NotificationType type, String description, Event event) {
		this.type = type;
		this.id = id;
		this.description = description;
		this.event = event;
	}
	public Notification(NotificationType type, String description, Event event) {
		this.type = type;
		this.description = description;
		this.event = event;
		this.id = -1; 
	}
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public NotificationType getType() {
		return type;
	}

	public void setType(NotificationType type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}
	public String toString()
	{
		return(this.id + " " + this.type + " " + this.event.toString() +" " + description); 
	}
}
