package client.gui;

import common.models.Event;

public class CreateEventFrame extends EventFrame {
	
	public CreateEventFrame(Event event) {
		super(event);
		setMainTitle("Create event");
		setFinishButtonText("Create");
		setIsEditable(true);
		setFinishButtonAction(false);
		setDeleteEventButtonEnabled(false);
	}
}
