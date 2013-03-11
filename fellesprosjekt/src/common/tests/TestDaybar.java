package common.tests;

import javax.swing.JFrame;
import client.gui.Daybar;

public class TestDaybar 
{
	public static void main(String[] args)
	{
		JFrame frame = new JFrame();
		Daybar bar = new Daybar();
		
		frame.add(bar);
		frame.setSize(700,100);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
}
