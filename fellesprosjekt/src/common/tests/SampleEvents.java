package common.tests;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import common.models.Event;
import common.models.Notification;
import common.models.Room;
import common.models.User;

public class SampleEvents {
	
	private static int eventCount = 10;
	private static ArrayList<Event> sampleEvents = new ArrayList<Event>();
	private static ArrayList<User> sampleUsers = SampleUsers.getSampleUsers();
	private static ArrayList<Room> sampleRooms = SampleRooms.getSampleRooms();
	private static ArrayList<Notification> sampleNotifications = new ArrayList<Notification>();
	
	public static void generateEvents(){
		for (int i=0; i<eventCount; i++){			
			
			Event event = new Event(sampleUsers.get(i), i, new Date(2013-1900, 2, i, 10, 0), new Date(2013-1900, 2, i, 11, 0), "TestEvent nr: "+i, "", "", null, sampleRooms.get(i)); 
			
			event.setAdmin(sampleUsers.get(i));
			sampleUsers.get(i).addEvent(event);
			sampleEvents.add(event);
			
	//		Notification n = new Notification(i, Notification.NotificationType.INVITATION, "", event, new Date());
	//		sampleNotifications.add(n);
		}
	}
	
	public static ArrayList<User> getSampleUsers() {
		//generateEvents();
		return sampleUsers;
	}
	
	public static ArrayList<Event> getSampleEvents(){
		generateEvents();
		return sampleEvents;
	}
	
	public ArrayList<Notification> getNotifications() {
		return sampleNotifications;
	}
}
