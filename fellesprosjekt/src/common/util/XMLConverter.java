package common.util;

import java.io.IOException;
import java.io.StringReader;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

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
import common.models.User;

public class XMLConverter 
{
	private DocumentBuilderFactory 	builderFactory;
	private DocumentBuilder 		documentFactory;
	private XPathFactory			xpathFactory;
	private XPath					xpath;
	
	public XMLConverter()
	{
		try 
		{
			builderFactory 	= DocumentBuilderFactory.newInstance();
			xpathFactory	= XPathFactory.newInstance();
			xpath			= xpathFactory.newXPath();
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
		Element username, name, user_;
		username 	= doc.createElement("username");
		name		= doc.createElement("name");
		user_ 		= doc.createElement("user");
		
		
		user_.appendChild(username);
		user_.appendChild(name);
		
		username.appendChild(doc.createTextNode(user.getUsername()));
		name.appendChild(doc.createTextNode(user.getName()));
		
		(parent==null ? doc : parent).appendChild(user_);
	}
	
	public void eventToDOMElement(Event event, Document doc, Element parent, boolean complete)
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
		dateToDOMElement(event.getStart(),doc, start);
		dateToDOMElement(event.getEnd(), doc, end);
		name.appendChild(doc.createTextNode(event.getName()));
		description.appendChild(doc.createTextNode(event.getDescription()));
		location.appendChild(doc.createTextNode(event.getLocation()));
		bookingID.appendChild(doc.createTextNode(""+event.getBookingId()));
		isMeeting.appendChild(doc.createTextNode(event.isMeeting() ? "true": "false"));
		
		if(complete)
			for(User user : event.getParticipants())
				userToDOMElement(user, doc, participants);
		else
			for(User user : event.getParticipants())
				participants.appendChild(doc.createTextNode(user.getName()));
		
		(parent==null ? doc : parent).appendChild(event_);
	}

	public void notificationToDOMElement(Notification notification, Document doc, Element parent, boolean complete)
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
		dateToDOMElement(notification.getSentDate(),doc, sentDate);
		
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
		{
			return constructDateFromNode(current);
		}
		else if(current.getNodeName().equals("user"))
		{
			return constructUserFromNode(current);
		}
		else if(current.getNodeName().equals("event"))
		{
			
		}
		
		return null;
	}
	
	public Event constructEventFromNode(Node node)
	{
		NodeList children = node.getChildNodes();
		Node temp;
		
		
		return null;
	}
	
	public User constructUserFromNode(Node node)
	{
		NodeList children = node.getChildNodes();
		Node temp;
		
		String username, name;
		
		username 	= "";
		name		= "";
		
		for(int i = 0; i < children.getLength(); i++)
		{
			temp = children.item(i);
			if(temp.getNodeName().equals("year"))
				username = temp.getNodeValue();
			else if(temp.getNodeName().equals("month"))
				name = temp.getNodeValue();
		}
		
		return new User(username, "", name);
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
	
	public void testXPath(Document doc)
	{
		try {
			System.out.println(xpath.evaluate("/date/year", doc));
		} catch (XPathExpressionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	public User DOMElementToUser(Element element)
	{
		
		return null;
	}
	
	public Document getNewDocument()
	{
		return documentFactory.newDocument();
	}
}












