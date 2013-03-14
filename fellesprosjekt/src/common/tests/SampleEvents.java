package common.tests;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import common.models.Event;
import common.models.User;

public class SampleEvents {
	
	private ArrayList<Event> sampleEvents = new ArrayList<Event>();
	private SampleUsers su;
	
	public SampleEvents(){
		su = new SampleUsers();
		for (int i=0; i<10; i++){			
			
			Event event = new Event(su.getSampleUsers().get(i), i, new Date(2013, 2, i, 10, 0), new Date(2013, 2, i, 10, 0), "TestEvent nr: "+i, "",
					"Rom "+i, i, true); 
			
			event.setAdmin(su.getSampleUsers().get(i));
			su.getSampleUsers().get(i).addEvent(event);
			sampleEvents.add(event);
		}
	}
	
	public SampleUsers getSampleUsers() {
		return su;
	}
	
	public ArrayList<Event> getSampleEvents(){
		return sampleEvents;
	}

	public static void main(String args[]) {
		SampleEvents s = new SampleEvents(); 
	}
}
