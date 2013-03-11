package common.tests;

import javax.swing.JFrame;
import client.gui.CoolCalendar;

public class TestCalendar
{
	public static void main(String[] args)
	{
		JFrame frame 				= new JFrame();
		JFrame control				= new JFrame();
		CoolCalendar calendar 		= new CoolCalendar();
		
		control.setSize(400,200);
		control.setVisible(true);
		
		frame.setSize(800,400);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.add(calendar);
		
		frame.setVisible(true);
	}
}
