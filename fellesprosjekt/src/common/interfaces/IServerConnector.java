package common.interfaces;

import java.util.ArrayList;

import common.models.Notification;

import common.models.*;

public interface IServerConnector 
{
	public User getUser(String username, String password);
	
	//count angir hvor mange instanser metoden maksimalt skal returnere.
	//count = 0 gir alle tilgjengelige
	
	public ArrayList<Event>getEvents(User user, int count);
	public ArrayList<Event>getMeetings(User user, int count);
	public ArrayList<Event>getAppointment(User user, int count);
	public ArrayList<Notification>getNotifications(User user, int count);
}
