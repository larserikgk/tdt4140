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
	static User derp 	= new User("kwrl","swag" , "HÃ¥kon");
	static User derpina = new User("derpz","yolo", "Derpina");
	static Room room1 	= new Room("R1", 123);
	static Event party	= new Event(derp,1,date1,date2,"Fest","My place","Epic", new ArrayList<User>(), room1);
	static Notification not = new Notification(NotificationType.INVITATION, "Awesome parteh", party);
	static ArrayList<User> participants = new ArrayList<User>();
	static ArrayList<Event> events = new ArrayList<Event>();
	static XMLConverter converter = new XMLConverter();
	static Document doc;
	
	public static void main(String[] args)
	{
		
		
//		testEventConversion();
//		testListConversion();
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
		converter.eventToDOMElement(party, doc, null, true);
		
		System.out.println(converter.DOMDocumentToString(doc));
		
		party = converter.constructEventFromNode(doc.getFirstChild());
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
	
	public static void testUserListConversion() {
		participants.add(new User("lars1","123","lars1"));
		participants.add(new User("lars4","312","lars2"));
		participants.add(new User("lars3","321","lars3"));
		participants.add(new User("lars2","213","lars4"));
		doc = converter.getNewDocument();
		converter.userListToDOMElement(participants, doc, null);
		
		System.out.println(converter.DOMDocumentToString(doc));
		
		ArrayList<User> users = converter.constructUserListFromNode(doc.getFirstChild());
		for (int i = 0; i < users.size(); i++) {
			System.out.println("name: "+ users.get(i).getName() +"  username: "+ users.get(i).getUsername() +" password: "+users.get(i).getPassword());
		}
	}
	
	public static void testEventListConversion() {
		participants.add(new User("per","1","per"));
		participants.add(new User("pål","2","pål"));
		participants.add(new User("askeladden","3","askeladden"));
		
		events.add(new Event(new User("grasdalk", "Lars Erik","navn?"),1,new Date(System.currentTimeMillis()),new Date(System.currentTimeMillis()+10000), "møte", "p15", "p15", participants, new Room("421", 30)));
		
		doc = converter.getNewDocument();
		converter.eventListToDOMElement(events, doc, null);
		
		System.out.println(converter.DOMDocumentToString(doc));
		
		ArrayList<Event> events = converter.constructEventListFromNode(doc.getFirstChild());
		for (int i = 0; i < events.size(); i++) {
			System.out.println("name: "+ events.get(i).getAdmin().getName() +"  start: "+ events.get(i).getStart().toString() +" end: "+events.get(i).getEnd().toString());
		}
	}
}
