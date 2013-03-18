package common.tests;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import common.models.Event;
import common.models.Room;
import common.models.User;

public class SampleEvents {
	
	private static int eventCount = 100;
	private static ArrayList<Event> sampleEvents = new ArrayList<Event>();
	private static ArrayList<User> sampleUsers = SampleUsers.getSampleUsers();
	private static ArrayList<Room> sampleRooms = SampleRooms.getSampleRooms();
	
	public static void generateEvents(){
		for (int i=0; i<eventCount; i++){			
			
			Event event = new Event(sampleUsers.get(i), i, new Date(2013, 2, i, 10, 0), new Date(2013, 2, i, 11, 0), "TestEvent nr: "+i, "",
					sampleRooms.get(i).getName(), i, true); 
			
			event.setAdmin(sampleUsers.get(i));
			sampleUsers.get(i).addEvent(event);
			sampleEvents.add(event);
		}
	}
	
	public static ArrayList<User> getSampleUsers() {
		generateEvents();
		return sampleUsers;
	}
	
	public static ArrayList<Event> getSampleEvents(){
		generateEvents();
		return sampleEvents;
	}

}