package client.gui;

import java.awt.GridLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Date;

import javax.swing.ButtonGroup;
import javax.swing.JPanel;

public class Daybar extends JPanel implements PropertyChangeListener
{
	private static final long serialVersionUID = 1L;
	private ButtonGroup group;
	
	DayLabel 	monday, 
				tuesday, 
				wednesday, 
				thursday, 
				friday, 
				saturday, 
				sunday;
	
	public Daybar()
	{
		setLayout(new GridLayout(1,7));
		setBackground(Settings.BACKGROUND_COLOR);
		monday 		= new DayLabel(Settings.MONDAY);
		tuesday 	= new DayLabel(Settings.TUESDAY);
		wednesday 	= new DayLabel(Settings.WEDNESDAY);
		thursday 	= new DayLabel(Settings.THURSDAY);
		friday 		= new DayLabel(Settings.FRIDAY);
		saturday 	= new DayLabel(Settings.SATURDAY);
		sunday 		= new DayLabel(Settings.SUNDAY);
		group = new ButtonGroup();

		addComponents();
	}
	
	public void addComponents()
	{
		add(monday);
		add(tuesday);
		add(wednesday);
		add(thursday);
		add(friday);
		add(saturday);
		add(sunday);
		
		group.add(monday);
		group.add(tuesday);
		group.add(wednesday);
		group.add(thursday);
		group.add(friday);
		group.add(saturday);
		group.add(sunday);
	}
	
	@Override
	public void propertyChange(PropertyChangeEvent evt) 
	{		
		if(evt.getPropertyName().equals("selectedDate"))
		{
			if(!(evt.getNewValue() instanceof Date))
				return;
			
			Date newValue = (Date)evt.getNewValue();
			setDay(newValue.getDay());
		}
	}
	
	public void setDay(int day){
		switch(day)
		{
			case 0: sunday.setSelected(true); break;
			case 1: monday.setSelected(true); break;
			case 2: tuesday.setSelected(true); break;
			case 3: wednesday.setSelected(true); break;
			case 4: thursday.setSelected(true); break;
			case 5: friday.setSelected(true); break;
			case 6: saturday.setSelected(true); break;
		}
	}
}
