package common.tests;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import common.models.Event;
import common.models.User;

public class SampleEvents {
	
	private ArrayList<Event> sampleEvents = new ArrayList<Event>();
	
	public SampleEvents(){
		for (int i=0; i<10; i++){
			Event event = new Event();
			event.setName("Meeting "+i);
			event.setDescription(i+". møte i 2013");
			event.setStart(new Date(2013, 3, i, 10, 0));
			event.setEnd(new Date(2013, 3, i, 12, 0));
			SampleUsers su = new SampleUsers();
			event.setParticipants((ArrayList<User>) su.getSampleUsers().subList(0, i));
			sampleEvents.add(event);
		}
	}
	
	public ArrayList<Event> getSampleEvents(){
		return sampleEvents;
	}


}
