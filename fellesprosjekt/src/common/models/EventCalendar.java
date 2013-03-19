package common.models;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Vector;


public class EventCalendar {
	private Vector<Event> eventList;
	private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        this.pcs.addPropertyChangeListener(listener);
    }
	
	public EventCalendar() {
		eventList = new Vector<Event>();
	}
	
	public Vector<Event> getEventList(){
		return eventList;
	}
	
	public void add(Event event, Date date) {
		eventList.add(event);
		this.pcs.firePropertyChange("EventCalendarChanged", null, this);
	}
	
	public void remove(Event event) {
		eventList.remove(event);
		this.pcs.firePropertyChange("EventCalendarChanged", null, this);
	}
	
	public ArrayList<Event> getEvents(Date date) {
		ArrayList<Event> events = new ArrayList<Event>();
		for (int i = 0; i < eventList.size(); i++) 
		{
			Date tmpDate = eventList.get(i).getStart();
			if (tmpDate.getYear() == date.getYear() && tmpDate.getDate() == date.getDate() && tmpDate.getMonth() == date.getMonth()) {
				events.add(eventList.get(i));
			}
		}
		return events;
	}

}