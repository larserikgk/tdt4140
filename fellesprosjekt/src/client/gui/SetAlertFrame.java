package client.gui;

import javax.swing.JLabel;

public class SetAlertFrame extends BaseFrame {
	
	public SetAlertFrame() {
		super();
		setResizable(false);
		setSize(300, 100);
		setCentered();
		JLabel label1 =  new JLabel("Please check your 'Upcoming events'");
		getContentPane().add(label1);
	}
}
