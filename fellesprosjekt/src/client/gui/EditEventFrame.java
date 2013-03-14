package client.gui;

import common.models.Event;

public class EditEventFrame extends EventFrame {
	
	public EditEventFrame(Event event) {
		super(event);
		setMainTitle("Edit event");
		setFinishButtonText("Save");
		setIsEditable(true);
		setFinishButtonAction(false);		
		setDeleteEventButtonEnabled(true);
	}
}