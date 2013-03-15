package client.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.swing.JPanel;

import common.models.EventCalendar;

//No worries, skal gj�re litt heftig refactor av navn her..
public class CoolCalendar extends JPanel implements PropertyChangeListener 
{
	private static final long 	serialVersionUID = 1L;
	private GridBagConstraints constraints;
	private GregorianCalendar calendar;
	private Daybar 	daybar;
	private Core 	core;
	private EventCalendar eventCalendar;
	
	public CoolCalendar(EventCalendar eventCalendar)
	{	
		this.eventCalendar = eventCalendar;
		calendar = new GregorianCalendar();
		init(eventCalendar);
	}
	
	/*
	public CoolCalendar(GregorianCalendar calendar)
	{	
		this.calendar = calendar;
		init();
	}*/
	
	private void init(EventCalendar eventCalendar)
	{
		constraints 		= new GridBagConstraints();
		constraints.fill 	= GridBagConstraints.BOTH;
		constraints.weightx	= 1;
		setLayout(new GridBagLayout());
		setCore(new Core(calendar, eventCalendar));
		setDaybar(new Daybar());
	}
	
	private void addComponents()
	{
		if(core==null||daybar==null)
			return;
		
		removeAll();
		validate();
		repaint();
		
		constraints.gridx=0;
		constraints.gridy=0;
		constraints.weighty= 1;
		
		add(daybar,constraints);
		validate();
		
		constraints.gridx=0;
		constraints.gridy=1;
		constraints.weighty= 25;
		
		add(core,constraints);
		validate();
		repaint();
	}
	
	public void setDaybar(Daybar daybar)
	{
		this.daybar = daybar;
		
		if(core!=null)
			core.addPropertyChangeListener(daybar);
		addComponents();
	}
	
	public Daybar getDaybar()
	{
		return daybar;
	}
	
	public void setCore(Core core)
	{	
		this.core = core;
		this.core.addPropertyChangeListener(this);
		
		if(daybar!=null)
			core.addPropertyChangeListener(daybar);
		addComponents();
	}
	
	public Core getCore()
	{
		return core;
	}
	
	public int getMonth(){
		return calendar.get(Calendar.MONTH);
	}
	
	//Flytter kalenderen til neste m�ned.
	public void nextMonth()
	{
		calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH)+1);
		setCore(new Core(calendar, eventCalendar));
	}
	
	//Flytter kalenderen til forrige m�ned.
	public void previousMonth()
	{
		calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH)-1);
		setCore(new Core(calendar, eventCalendar));
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName().equals("EventsCalendarChanged")) {
			System.out.println("Evetscalchanged");
			init((EventCalendar) evt.getNewValue());
		}
		if (evt.getPropertyName().equals("selectedDate")) {
			firePropertyChange(evt.getPropertyName(), evt.getOldValue(), evt.getNewValue());
		}
	}	
}
