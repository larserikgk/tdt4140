package client.gui;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.swing.ButtonGroup;
import javax.swing.JPanel;

import common.models.Event;
import common.models.EventCalendar;

public class Core extends JPanel implements ActionListener
{
	private static final long 	serialVersionUID = 1L;
	private GregorianCalendar	calendar, copy;
	private ButtonGroup			cells;
	private Date 				defaultSelectedDate;
	
	@SuppressWarnings("deprecation")
	public Core(GregorianCalendar calendar, EventCalendar eventCalendar)
	{
		Cell temp;
				
		this.calendar 	= calendar;
		this.cells		= new ButtonGroup();
		this.defaultSelectedDate = new Date(calendar.get(Calendar.YEAR)-1900, calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE));
		
		setLayout(new GridLayout(6,7));
		setBackground(Settings.BACKGROUND_COLOR);
		
		copy = (GregorianCalendar) calendar.clone();
		getFirstMonday(copy);
		
				
//		Date defaultSelectedDate = new Date(new Date().getYear(), new Date().getMonth(), calendar.get(Calendar.DATE));
		System.out.println("Default selected date:"+defaultSelectedDate);
		
		if(copy.get(Calendar.MONTH)!=calendar.get(Calendar.MONTH))
			for(int i=copy.get(Calendar.DAY_OF_MONTH); i<=copy.getActualMaximum(Calendar.DAY_OF_MONTH);i++)
			{
				temp = new Cell(""+i, false, false);
				add(temp);
				cells.add(temp);
			}
		
		for (Event e : eventCalendar.eventList) {
			System.out.println(e.getStart().toString());
		}
		
		for(int i=1; i <= calendar.getActualMaximum(Calendar.DAY_OF_MONTH);i++)
		{
			Date tmpDate = new Date(calendar.get(Calendar.YEAR)-1900, calendar.get(Calendar.MONTH), i);
			boolean b=false;
			if (eventCalendar.getEvents(tmpDate).size() != 0) {
				System.out.println("Found Event: " + tmpDate);
				b = true;
			}
			temp = new Cell(""+i, true, b);
			if (tmpDate.equals(defaultSelectedDate)){
				firePropertyChange("selectedDate", null, tmpDate);
				temp.setSelected(true);
			}
			add(temp);
			temp.addActionListener(this);
			cells.add(temp);
		}
		
		int i = 1;
		while(getComponentCount()<42)
		{
			temp = new Cell(""+i, false, false);
			add(temp);
			cells.add(temp);
			i++;
		}
		
		
	}
	
	private void getFirstMonday(GregorianCalendar copy)
	{
		copy.set(Calendar.DAY_OF_MONTH, 1);
		
        while(copy.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY)
        {
        	if(copy.get(Calendar.DAY_OF_YEAR) == 0)
        		copy.roll(Calendar.YEAR, false);
        	copy.roll(Calendar.DAY_OF_YEAR, false);
        }
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		Date oldDate, newDate;
		oldDate = calendar.getTime();
		
		if(e.getSource() instanceof Cell)
		{
			calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(((Cell)e.getSource()).getText()));
		}

		newDate = calendar.getTime();
		firePropertyChange("selectedDate", oldDate, newDate);
	}

	public GregorianCalendar getCalendar() 
	{
		return calendar;
	}
	
	public Date getDefaultSelectedDate(){
		return defaultSelectedDate;
	}
}
