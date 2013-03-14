package client.gui;

import javax.swing.JButton;
import javax.swing.JFrame;

import common.models.Event;

public class ShowEventFrame extends EventFrame {
	
	public ShowEventFrame(Event event) {
		super(event);
		setMainTitle("Show event");
		setIsEditable(false);
		setFinishButtonText("Close");
		setFinishButtonAction(true);
	}
}
