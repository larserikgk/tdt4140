package common.models;

import java.util.Date;

public class Notification {
	
	public enum NotificationType {INVITATION, INV_RESPONSE, EVENT_UPDATE};
	
	private int id;
	private NotificationType type;
	private String description;
	private Date sentDate;
	private Event event;

	public Notification(int id, NotificationType type, String description, Event event, Date sentDate) {
		this.type = type;
		this.id = id;
		this.description = description;
		this.event = event;
		this.sentDate = sentDate;
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
	
	public Date getSentDate() {
		return sentDate;
	}

	public void setSentDate(Date sentDate) {
		this.sentDate = sentDate;
	}

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}
	
}
