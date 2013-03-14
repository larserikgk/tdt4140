package common.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;


public class EventCalendar {
	public Vector<Event> eventList;
	
	public EventCalendar() {
		eventList = new Vector<Event>();
	}
	
	public void add(Event event, Date date) {
		eventList.add(event);	
	}
	
	public void remove(Event event) {
		eventList.remove(event);
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
