
public class Notification {
	
	public enum NotificationType {INVITATION, INV_RESPONSE, EVENT_UPDATE, MESSAGE}; //legg til flere hvis nødvendig
	
	private int id;
	private NotificationType type;
	private String description;
	
	public Notification(int id, NotificationType type, String description) {
		this.type = type;
		this.id = id;
		this.description = description;
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
	
	
	
	

}
