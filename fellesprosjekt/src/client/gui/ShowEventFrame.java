package client.gui;

import javax.swing.JButton;
import javax.swing.JFrame;

public class ShowEventFrame extends EventFrame {
	
	public ShowEventFrame() {
		super();
		setMainTitle("Show event");
		setIsEditable(false);
		setCloseButton("Close");
	}
	public static void main(String[] args) {
		JFrame f = new ShowEventFrame();
		f.setVisible(true);
	}
}
