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
	
	private Element eventToDOMElement(Event event, Document doc)
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
		
		for(User user : event.getParticipants())
			participants.appendChild(userToDOMElement(user, doc));
		
		return event_;
	}


	public String DOMtoString(Document doc)
	{
		DOMImplementation impl = doc.getImplementation();
		DOMImplementationLS implLS = (DOMImplementationLS) impl.getFeature("LS", "3.0");
		LSSerializer ser = implLS.createLSSerializer();
		return ser.writeToString(doc);
	}
}












