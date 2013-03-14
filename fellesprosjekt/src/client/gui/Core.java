package client.gui;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.swing.ButtonGroup;
import javax.swing.JPanel;

import common.models.EventCalendar;

public class Core extends JPanel implements ActionListener, PropertyChangeListener
{
	private static final long 	serialVersionUID = 1L;
	private GregorianCalendar	calendar, copy;
	private ButtonGroup			cells;
	
	public Core(GregorianCalendar calendar, EventCalendar eventCalendar)
	{
		Cell temp;
				
		this.calendar 	= calendar;
		this.cells		= new ButtonGroup();
		
		setLayout(new GridLayout(6,7));
		setBackground(Settings.BACKGROUND_COLOR);
		
		copy = (GregorianCalendar) calendar.clone();
		getFirstMonday(copy);
		
		if(copy.get(Calendar.MONTH)!=calendar.get(Calendar.MONTH))
			for(int i=copy.get(Calendar.DAY_OF_MONTH); i<=copy.getActualMaximum(Calendar.DAY_OF_MONTH);i++)
			{
				temp = new Cell(""+i, false, false);
				add(temp);
				cells.add(temp);
			}
		
		for(int i=1; i <= calendar.getActualMaximum(Calendar.DAY_OF_MONTH);i++)
		{
			Date tmpDate = new Date(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), i);
			System.out.println(tmpDate);
			boolean b=false;			
			if (eventCalendar.getEvents(tmpDate) != null) {
				b=true;
			}
			temp = new Cell(""+i, true, b);
			add(temp);
			temp.addActionListener(this);
			cells.add(temp);
			this.addPropertyChangeListener(temp);
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

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		firePropertyChange(evt.getPropertyName(), evt.getOldValue(), evt.getNewValue());
	}
}
