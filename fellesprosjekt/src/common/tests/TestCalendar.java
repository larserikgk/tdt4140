package common.tests;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Calendar;

import javax.swing.JFrame;

import common.models.EventCalendar;

import client.gui.CoolCalendar;

public class TestCalendar
{
	public static void main(String[] args)
	{
		JFrame frame 				= new JFrame();
		CoolCalendar calendar 		= new CoolCalendar(new EventCalendar());
		calendar.previousMonth();
		calendar.previousMonth();
		frame.setSize(800,400);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.add(calendar);
		frame.setVisible(true);
	}
}
