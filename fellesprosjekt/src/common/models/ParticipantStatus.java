package common.models;

public class ParticipantStatus {
	private String username;
	private int eventId;
	private Status status;
	public enum Status {ATTENDING, DECLINED, PENDING};
	
	
	public ParticipantStatus(String username, int eventId, Status status) {
		this.username = username;
		this.eventId = eventId;
		this.status = status;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getEventId() {
		return eventId;
	}

	public void setEventId(int eventId) {
		this.eventId = eventId;
	}

	public Status getStatus() {
		return status;
	}


	public void setStatus(Status status) {
		this.status = status;
	}
}
