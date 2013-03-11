package common.tests;

import javax.swing.JFrame;
import client.gui.CoolCalendar;

public class TestCalendar
{
	public static void main(String[] args)
	{
		JFrame frame 				= new JFrame();
		CoolCalendar calendar 		= new CoolCalendar();
		
		frame.setSize(800,400);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.add(calendar);
		frame.setVisible(true);
	}
}
