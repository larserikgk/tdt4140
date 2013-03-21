package common.util;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSSerializer;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import common.models.Event;
import common.models.Notification;
import common.models.Room;
import common.models.User;
import common.models.Notification.NotificationType;

/*Konverterer mellom XML og objekter av klassene man finner i common.models
 *Konvertere et objekt til XML:
 *1. 	Hent et en instans av Document ved hjelp av getNewDocument()
 *
 *2. 	Benytt metoden på formen [klassenavn]ToDOMElement([klassenavn], Document, Element).
 *		Nå vil instansen av Document inneholde en representasjon av objektet.
 *
 *3. 	For å få det hele representert som en String av XML så benytter man
 *			DOMDocumentToString(Document doc)	
 *
 *Konvertere XML til objekter av klassene fra common.models
 *1.	Lag en instans av Document ved hjelp av StringToDOMDocument(String xml_string)
 *
 *2.	Her er det et skille etter hvor vidt du vet hva slags klasse XML-meldingen din representerer
 *		Gitt at du ikke vet hva XML-meldingen inneholder så kan du bruke genericObjectBuilder(Node current)
 *		denne metoden returnerer bare et Object så du må bruke instanceof for å bestemme hva du har fått tilbake.
 *
 *		Gitt at du vet hva XML-meldingen inneholder så kan du gjøre et direkte kall på construct[klassenavn]FromNode(Node node)
 *		Det man da sender som parameter til metoden er Document.getFirstChild()
 *
 *
 */
public class XMLConverter 
{
	private DocumentBuilderFactory 	builderFactory;
	private DocumentBuilder 		documentFactory;
	
	public XMLConverter()
	{
		try 
		{
			builderFactory 	= DocumentBuilderFactory.newInstance();
			documentFactory = builderFactory.newDocumentBuilder();
		} 
		catch (ParserConfigurationException e) 
		{
			e.printStackTrace();
		}
	}

	@SuppressWarnings("deprecation")
	public void dateToDOMElement(Date date, Document doc, Element parent)
	{
		Element year, month, day, hour, minute, date_;
		
		year		= doc.createElement("year");
		month		= doc.createElement("month");
		day			= doc.createElement("day");
		hour		= doc.createElement("hour");
		minute		= doc.createElement("minute");
		date_		= doc.createElement("date");
		
		year.appendChild(doc.createTextNode(""+date.getYear()));
		month.appendChild(doc.createTextNode(""+date.getMonth()));
		day.appendChild(doc.createTextNode(""+date.getDate()));
		hour.appendChild(doc.createTextNode(""+date.getHours()));
		minute.appendChild(doc.createTextNode(""+date.getMinutes()));
		
		date_.appendChild(year);
		date_.appendChild(month);
		date_.appendChild(day);
		date_.appendChild(hour);
		date_.appendChild(minute);
		
		(parent==null ? doc : parent).appendChild(date_);
	}
	
	public void userToDOMElement(User user, Document doc, Element parent)
	{
		Element username, name, password, user_;
		username 	= doc.createElement("username");
		password	= doc.createElement("password");
		name		= doc.createElement("name");
		user_ 		= doc.createElement("user");
		
		user_.appendChild(password);
		user_.appendChild(username);
		user_.appendChild(name);
		
		username.appendChild(doc.createTextNode(user.getUsername()));
		name.appendChild(doc.createTextNode(user.getName()));
		password.appendChild(doc.createTextNode(user.getPassword()));
		
		(parent==null ? doc : parent).appendChild(user_);
	}
	
	public void userListToDOMElement(ArrayList<User> users, Document doc, Element parent)
	{
		Element userlist_ = doc.createElement("userlist");
		
		for(User u : users)
			userToDOMElement(u, doc, userlist_);
		
		(parent==null ? doc : parent).appendChild(userlist_);
	}
	
	public void eventListToDOMElement(ArrayList<Event> events, Document doc, Element parent)
	{
		Element eventlist_ = doc.createElement("eventlist");
		
		for(Event e : events)
			eventToDOMElement(e, doc, eventlist_, true);
		
		(parent==null ? doc : parent).appendChild(eventlist_);
	}
	
	public void notificationListToDOMElement(ArrayList<Notification> notifications, Document doc, Element parent)
	{
		Element notificationlist_ = doc.createElement("notificationlist");
		
		for(Notification n : notifications)
			notificationToDOMElement(n, doc, notificationlist_, true);
		
		(parent==null ? doc : parent).appendChild(notificationlist_);
	}
	
	public void eventToDOMElement(Event event, Document doc, Element parent, boolean complete)
	{
		Element 	id, start, end, name, description, location, room, participants, admin, event_;
		
		id 			= doc.createElement("ID");
		start		= doc.createElement("start");
		end			= doc.createElement("end");
		name		= doc.createElement("name");
		description = doc.createElement("description");
		location	= doc.createElement("location");
		room		= doc.createElement("room");
		participants= doc.createElement("participants");
		admin		= doc.createElement("admin");
		event_		= doc.createElement("event");
		
		event_.appendChild(id);
		event_.appendChild(start);
		event_.appendChild(end);
		event_.appendChild(name);
		event_.appendChild(location);
		event_.appendChild(room);
		event_.appendChild(participants);
		event_.appendChild(admin);
		
		id.appendChild(doc.createTextNode(""+event.getId()));
		dateToDOMElement(event.getStart(),doc, start);
		dateToDOMElement(event.getEnd(), doc, end);
		name.appendChild(doc.createTextNode(event.getName()));
		description.appendChild(doc.createTextNode(event.getDescription()));
		location.appendChild(doc.createTextNode(event.getLocation()));
		roomToDOMElement(event.getRoom(), doc, room);
		userToDOMElement(event.getAdmin(), doc, admin);
		
		if(complete)
			for(User user : event.getParticipants())
				userToDOMElement(user, doc, participants);
		else
			for(User user : event.getParticipants())
				participants.appendChild(doc.createTextNode(user.getName()));
		
		(parent==null ? doc : parent).appendChild(event_);
	}
	
	public void roomToDOMElement(Room room, Document doc, Element parent)
	{
		Element name, capacity, room_;
		
		name 		= doc.createElement("name");
		capacity 	= doc.createElement("capacity");
		room_		= doc.createElement("room");
		
		room_.appendChild(name);
		room_.appendChild(capacity);
		
		name.appendChild(doc.createTextNode(room.getName()));
		capacity.appendChild(doc.createTextNode(""+room.getCapacity()));
		
		(parent==null ? doc : parent).appendChild(room_);
	}

	public void notificationToDOMElement(Notification notification, Document doc, Element parent, boolean complete)
	{
		Element 	id, type, description, event, notification_;
		
		id				= doc.createElement("id");
		type			= doc.createElement("type");
		description		= doc.createElement("description");
		event			= doc.createElement("event");
		notification_	= doc.createElement("notification");
		
		notification_.appendChild(id);
		notification_.appendChild(type);
		notification_.appendChild(description);
		notification_.appendChild(event);
		
		id.appendChild(doc.createTextNode(""+notification.getId()));
		
		switch(notification.getType())
		{
		case INVITATION: 	type.appendChild(doc.createTextNode("INVITATION")); break;
		case INV_RESPONSE: 	type.appendChild(doc.createTextNode("INV_RESPONSE")); break;
		case EVENT_UPDATE:	type.appendChild(doc.createTextNode("EVENT_UPDATE")); break;
		}
		
		description.appendChild(doc.createTextNode(notification.getDescription()));
		
		if(complete)
			eventToDOMElement(notification.getEvent(), doc, event, complete);
		else
			event.appendChild(doc.createTextNode(""+notification.getEvent().getId()));
		
		(parent==null ? doc : parent).appendChild(notification_);
	}
	
	public String DOMDocumentToString(Document doc)
	{
		DOMImplementation impl = doc.getImplementation();
		DOMImplementationLS implLS = (DOMImplementationLS) impl.getFeature("LS", "3.0");
		LSSerializer ser = implLS.createLSSerializer();
		return ser.writeToString(doc);
	}
	
	public Document StringToDOMDocument(String xml_string)
	{
		Document doc = null;
		
		try {
			doc = documentFactory.parse(new InputSource(new StringReader(xml_string)));
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return doc;
	}

	public Object genericObjectBuilder(Node current)
	{			
		
		if(current.getNodeName().equals("date"))
			return constructDateFromNode(current);
		else if(current.getNodeName().equals("user"))
			return constructUserFromNode(current);
		else if(current.getNodeName().equals("event"))
			return constructEventFromNode(current);
		else if(current.getNodeName().equals("notification"))
			return constructNotificationFromNode(current);
		else if(current.getNodeName().equals("participants"))
			return constructUserListFromNode(current);
		
		return null;
	}
	
	public Notification constructNotificationFromNode(Node node)
	{
		NodeList children 				= node.getChildNodes();
		Node temp;
		
		int id;
		NotificationType type;
		String description;
		Event event;
		
		id = 0;
		type = null;
		description = "";
		event = null;
		
		for(int i = 0; i < children.getLength(); i++)
		{
			temp = children.item(i);
			if(temp.getNodeName().equals("id"))
				id = Integer.parseInt(temp.getFirstChild().getTextContent());
			else if(temp.getNodeName().equals("type"))
			{
				if(temp.getFirstChild().getTextContent().equals("INVITATION"))
					type = NotificationType.INVITATION;
				else if(temp.getFirstChild().getTextContent().equals("INV_RESPONSE"))
					type = NotificationType.INV_RESPONSE;
				else if(temp.getFirstChild().getTextContent().equals("EVENT_UPDATE"))
					type = NotificationType.EVENT_UPDATE;
			}
			else if(temp.getNodeName().equals("description"))
				description = temp.getFirstChild().getTextContent();
			else if(temp.getNodeName().equals("event"))
				event = constructEventFromNode(temp);
		}
		
		
		return new Notification(id, type, description, event);
	}
	
	public Event constructEventFromNode(Node node)
	{
		NodeList children 				= node.getChildNodes();
		Node temp;
		
		int 			id;
		Date 			start, end;
		String 			name, description, location;
		ArrayList<User> participants;;
		User			admin;
		Room 			room;
		
		id = 0;
		start = null;
		end = null;
		name = "";
		description = "";
		location = "";
		participants = null;
		admin = null;
		room = null;
		
		
		for(int i = 0; i < children.getLength(); i++)
		{
			temp = children.item(i);
			if(temp.getNodeName().equals("id"))
				id = Integer.parseInt(temp.getFirstChild().getTextContent());
			else if(temp.getNodeName().equals("start"))
				start = constructDateFromNode(temp);
			else if(temp.getNodeName().equals("end"))
				end = constructDateFromNode(temp);
			else if(temp.getNodeName().equals("name"))
				name = temp.getFirstChild().getTextContent();
			else if(temp.getNodeName().equals("description"))
				description = temp.getFirstChild().getTextContent();
			else if(temp.getNodeName().equals("location"))
				location = temp.getFirstChild().getTextContent();
			else if(temp.getNodeName().equals("participants"))
				participants = constructUserListFromNode(temp);
			else if(temp.getNodeName().equals("admin"))
				admin = constructUserFromNode(temp);
			else if(temp.getNodeName().equals("room"))
				room = constructRoomFromNode(temp);
		}
		
		return new Event(admin, id, start, end, name, description, location,participants, room);
	}
	
	public ArrayList<User> constructUserListFromNode(Node node)
	{
		ArrayList<User> users = new ArrayList<User>();
		NodeList children = node.getChildNodes();
		
		for(int i = 0; i < children.getLength(); i++)
			users.add(constructUserFromNode(children.item(i)));
		
		return users;
	}
	
	public ArrayList<Event> constructEventListFromNode(Node node)
	{
		ArrayList<Event> events = new ArrayList<Event>();
		NodeList children = node.getChildNodes();
		
		for(int i = 0; i < children.getLength(); i++)
			events.add(constructEventFromNode(children.item(i)));
		
		return events;
	}
	
	public ArrayList<Notification> constructNotificationListFromNode(Node node)
	{
		ArrayList<Notification> notifications = new ArrayList<Notification>();
		NodeList children = node.getChildNodes();
		
		for(int i = 0; i < children.getLength(); i++)
			notifications.add(constructNotificationFromNode(children.item(i)));
		
		return notifications;
	}
	
	public User constructUserFromNode(Node node)
	{
		NodeList children = node.getChildNodes();
		Node temp;
		
		String username, name, password;
		
		username 	= "";
		name		= "";
		password 	= "";
		
		for(int i = 0; i < children.getLength(); i++)
		{
			temp = children.item(i);
			if(temp.getNodeName().equals("username"))
				username = temp.getFirstChild().getTextContent();
			else if(temp.getNodeName().equals("name"))
				name = temp.getFirstChild().getTextContent();
			else if(temp.getNodeName().equals("password"))
				password = temp.getFirstChild().getTextContent();
				
		}
		
		return new User(username, password, name);
	}
	
	public Date constructDateFromNode(Node node)
	{
		NodeList children = node.getChildNodes();
		Node temp;
		
		int year, month, day, hour, minute;
		
		year 	= 0;
		month	= 0;
		day		= 0;
		hour 	= 0;
		minute	= 0;
		
		for(int i = 0; i < children.getLength(); i++)
		{
			temp = children.item(i);
			if(temp.getNodeName().equals("year"))
				year = Integer.parseInt(temp.getFirstChild().getTextContent());
			else if(temp.getNodeName().equals("month"))
				month = Integer.parseInt(temp.getFirstChild().getTextContent());
			else if(temp.getNodeName().equals("day"))
				day = Integer.parseInt(temp.getFirstChild().getTextContent());
			else if(temp.getNodeName().equals("hour"))
				hour = Integer.parseInt(temp.getFirstChild().getTextContent());
			else if(temp.getNodeName().equals("minute"))
				minute = Integer.parseInt(temp.getFirstChild().getTextContent());
		}
		
		return new Date(year,month,day,hour,minute);
	}
	
	public Room constructRoomFromNode(Node node)
	{
		NodeList children = node.getChildNodes();
		Node temp;
		
		String name		= "";
		int capacity 	= 0;
		
		for(int i = 0; i < children.getLength(); i++)
		{
			temp = children.item(i);
			if(temp.getNodeName().equals("name"))
				name = temp.getFirstChild().getTextContent();
			else if(temp.getNodeName().equals("capacity"))
				capacity = Integer.parseInt(temp.getFirstChild().getTextContent());
		}
		
		return new Room(name, capacity);
	}
	
	public Document getNewDocument()
	{
		return documentFactory.newDocument();
	}
}












