package common.util;

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
	
	public Element userToDOMElement(User user, Document doc)
	{
		Element username, password, name, user_;
		username 	= doc.createElement("username");
		password 	= doc.createElement("password");
		name		= doc.createElement("name");
		user_ 		= doc.createElement("user");
		
		
		user_.appendChild(username);
		user_.appendChild(password);
		user_.appendChild(name);
		
		username.appendChild(doc.createTextNode(user.getUsername()));
		password.appendChild(doc.createTextNode(user.getPassword()));
		name.appendChild(doc.createTextNode(user.getName()));
		
		return user_;
	}
	
	public Element eventToDOMElement(Event event, Document doc)
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
	}


	public String DOMtoString(Document doc)
	{
		DOMImplementation impl = doc.getImplementation();
		DOMImplementationLS implLS = (DOMImplementationLS) impl.getFeature("LS", "3.0");
		LSSerializer ser = implLS.createLSSerializer();
		return ser.writeToString(doc);
	}
}












