package common.interfaces;

import java.util.ArrayList;
import java.util.Date;

import common.models.*;

public interface IServerConnector 
{
	public User getUser(String username, String password);
	
	//count angir hvor mange instanser metoden maksimalt skal returnere.
	//count = 0 gir alle tilgjengelige
	
	public ArrayList<Event>getEvents		(User user, int count);
	public ArrayList<Event>getMeetings		(User user, int count);
	public ArrayList<Event>getAppointments	(User user, int count);
	public ArrayList<Notification>getNotifications(User user, boolean unreadOnly, int count);
	
	
	//Henter alle instanser fra og med from til og med to
	public ArrayList<Event>getEvents		(User user, Date from, Date to);
	public ArrayList<Event>getMeetings		(User user, Date from, Date to);
	public ArrayList<Event>getAppointments	(User user, Date from, Date to); 
	public ArrayList<Notification>getNotifications(User user, boolean unreadOnly, Date from, Date to);
	
	
	
}
