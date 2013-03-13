package common.util;

import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSSerializer;

import common.models.Event;
import common.models.Notification;
import common.models.User;

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
	
	public Document dateToDOMDocument(Date date)
	{
		Document doc = documentFactory.newDocument();
		doc.appendChild(dateToDOMElement(date, doc));
		
		return doc;
	}
	
	public Document userToDOMDocument(User user)
	{
		Document doc = documentFactory.newDocument();
		doc.appendChild(userToDOMElement(user, doc));
		
		return doc;
	}
	
	public Document eventToDOMDocument(Event event, boolean complete)
	{
		Document doc = documentFactory.newDocument();
		doc.appendChild(eventToDOMElement(event, doc, complete));
		
		return doc;
	}
	
	public Document notificationToDOMDocument(Notification notification, boolean complete)
	{
		Document doc = documentFactory.newDocument();
		doc.appendChild(notificationToDOMElement(notification, doc, complete));
		
		return doc;
	}
	
	@SuppressWarnings("deprecation")
	private Element dateToDOMElement(Date date, Document doc)
	{
		Element year, month, day, hour, minute, seconds, date_;
		
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
		
		return date_;
	}
	
	private Element userToDOMElement(User user, Document doc)
	{
		Element username, name, user_;
		username 	= doc.createElement("username");
		name		= doc.createElement("name");
		user_ 		= doc.createElement("user");
		
		
		user_.appendChild(username);
		user_.appendChild(name);
		
		username.appendChild(doc.createTextNode(user.getUsername()));
		name.appendChild(doc.createTextNode(user.getName()));
		
		return user_;
	}
	
	private Element eventToDOMElement(Event event, Document doc, boolean complete)
	{
		Element 	id, 
					start, 
					end, 
					name, 
					description, 
					location, 
					bookingID, 
					isMeeting, 
					participants, 
					event_;
		
		id 			= doc.createElement("ID");
		start		= doc.createElement("start");
		end			= doc.createElement("end");
		name		= doc.createElement("name");
		description = doc.createElement("description");
		location	= doc.createElement("location");
		bookingID	= doc.createElement("bookingID");
		isMeeting	= doc.createElement("isMeeting");
		participants= doc.createElement("participants");
		event_		= doc.createElement("event");
		
		event_.appendChild(id);
		event_.appendChild(start);
		event_.appendChild(end);
		event_.appendChild(name);
		event_.appendChild(location);
		event_.appendChild(bookingID);
		event_.appendChild(isMeeting);
		event_.appendChild(participants);
		
		id.appendChild(doc.createTextNode(""+event.getId()));
		start.appendChild(dateToDOMElement(event.getStart(), doc));
		end.appendChild(dateToDOMElement(event.getEnd(), doc));
		name.appendChild(doc.createTextNode(event.getName()));
		description.appendChild(doc.createTextNode(event.getDescription()));
		location.appendChild(doc.createTextNode(event.getLocation()));
		bookingID.appendChild(doc.createTextNode(""+event.getBookingId()));
		isMeeting.appendChild(doc.createTextNode(event.isMeeting() ? "true": "false"));
		
		if(complete)
			for(User user : event.getParticipants())
				participants.appendChild(userToDOMElement(user, doc));
		else
			for(User user : event.getParticipants())
				participants.appendChild(doc.createTextNode(user.getName()));
		
		
		return event_;
	}

	private Element notificationToDOMElement(Notification notification, Document doc, boolean complete)
	{
		Element 	id,
					type,
					description,
					sentDate,
					event,
					notification_;
		
		id				= doc.createElement("id");
		type			= doc.createElement("type");
		description		= doc.createElement("description");
		sentDate		= doc.createElement("sentDate");
		event			= doc.createElement("event");
		notification_	= doc.createElement("notification");
		
		notification_.appendChild(id);
		notification_.appendChild(type);
		notification_.appendChild(description);
		notification_.appendChild(sentDate);
		notification_.appendChild(event);
		
		id.appendChild(doc.createTextNode(""+notification.getId()));
		
		switch(notification.getType())
		{
		case INVITATION: 	type.appendChild(doc.createTextNode("INVITATION")); break;
		case INV_RESPONSE: 	type.appendChild(doc.createTextNode("INV_RESPONSE")); break;
		case EVENT_UPDATE:	type.appendChild(doc.createTextNode("EVENT_UPDATE")); break;
		}
		
		description.appendChild(doc.createTextNode(notification.getDescription()));
		sentDate.appendChild(dateToDOMElement(notification.getSentDate(), doc));
		
		if(complete)
			event.appendChild(eventToDOMElement(notification.getEvent(), doc, complete));
		else
			event.appendChild(doc.createTextNode(""+notification.getEvent().getId()));
		
		return notification_;
	}
	
	public String DOMtoString(Document doc)
	{
		DOMImplementation impl = doc.getImplementation();
		DOMImplementationLS implLS = (DOMImplementationLS) impl.getFeature("LS", "3.0");
		LSSerializer ser = implLS.createLSSerializer();
		return ser.writeToString(doc);
	}
}












