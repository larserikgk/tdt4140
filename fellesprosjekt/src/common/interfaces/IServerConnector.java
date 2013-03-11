package common.interfaces;

import java.util.ArrayList;
import client.models.*;

public interface IServerConnector 
{
	public ArrayList<Event>getEvents(User user);
	public ArrayList<Event>getEvents(int userID);
	
}
