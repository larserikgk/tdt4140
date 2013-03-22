package common.tests;

import java.util.ArrayList;
import java.util.Date;
import org.w3c.dom.Document;

import common.models.Event;
import common.models.Notification;
import common.models.Room;
import common.models.User;
import common.models.Notification.NotificationType;
import common.util.XMLConverter;

public class TestXMLConverter 
{
	static Date date1 	= new Date();
	static Date date2 	= new Date();
	static User derp 	= new User("kwrl","swag" , "Håkon");
	static User derpina = new User("derpz","yolo", "Derpina");
	static Room room1 	= new Room("R1", 123);
	static Event party	= new Event(derp,4,date1,date2,"Fest","My place","Epic", new ArrayList<User>(), room1);
	static Notification not = new Notification(NotificationType.INVITATION, "Awesome parteh", party);
	static XMLConverter converter = new XMLConverter();
	static Document doc;
	
	public static void main(String[] args)
	{
		testEventListConversion();
	}
	
	public static void testUserConversion()
	{
		doc = converter.getNewDocument();
		converter.userToDOMElement(derp, doc, null);
		
		System.out.println(converter.DOMDocumentToString(doc));
		
		derp = converter.constructUserFromNode(doc.getFirstChild());
		System.out.println(derp.getName());
		System.out.println(derp.getUsername());
		System.out.println(derp.getPassword());
	}
	
	public static void testNotificationConversion()
	{
		doc = converter.getNewDocument();
		converter.notificationToDOMElement(not, doc, null, true);
		
		System.out.println(converter.DOMDocumentToString(doc));
	}
	public static void testEventConversion()
	{
		doc = converter.getNewDocument();
		party.getParticipants().add(derp);
		party.getParticipants().add(derpina);
		converter.eventToDOMElement(party, doc, null, true);
		
		System.out.println(converter.DOMDocumentToString(doc));
		
		party = converter.constructEventFromNode(doc.getFirstChild());
		
		System.out.println(party.getId());
	}
	
	public static void testEventListConversion()
	{
		ArrayList<Event> events = new ArrayList<Event>();
		doc = converter.getNewDocument();
		party.getParticipants().add(derp);
		party.getParticipants().add(derpina);
		
		
		events.add(party);
		converter.eventListToDOMElement(events, doc, null);
		
		System.out.println(converter.DOMDocumentToString(doc));
		
		events = converter.constructEventListFromNode(doc.getFirstChild());
		
		System.out.println(events.get(0).getId());
	}
	
	public static void testRoomConversion()
	{
		doc = converter.getNewDocument();
		converter.roomToDOMElement(room1, doc, null);
		
		System.out.println(converter.DOMDocumentToString(doc));
		
		room1 = converter.constructRoomFromNode(doc.getFirstChild());
		
		System.out.println(room1.getName());
		System.out.println(room1.getCapacity());
	}
	
	public static void testDateConversion()
	{
		doc		= converter.getNewDocument();		
		converter.dateToDOMElement(date1, doc, null);
		
		date2 = (Date) converter.genericObjectBuilder(doc.getFirstChild());
		
		System.out.println(date1);
		System.out.println(date2);
	}
}
