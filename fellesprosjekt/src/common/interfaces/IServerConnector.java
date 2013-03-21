package common.interfaces;

import java.net.ConnectException;
import java.util.ArrayList;
import java.util.Date;

import common.models.*;

public interface IServerConnector 
{
	public User getUser(String username, String password) throws ConnectException;
	
	//count angir hvor mange instanser metoden maksimalt skal returnere.
	//count = 0 gir alle tilgjengelige
	
	public ArrayList<Event>getEvents		(User user, int count) throws ConnectException;
	public ArrayList<Event>getMeetings		(User user, int count) throws ConnectException;
	public ArrayList<Event>getAppointments	(User user, int count) throws ConnectException;
	public ArrayList<Notification>getNotifications(User user, boolean unreadOnly, int count) throws ConnectException;
	
	
	//Henter alle instanser fra og med from til og med to
	public ArrayList<Event>getEvents		(User user, Date from, Date to) throws ConnectException;
	public ArrayList<Event>getMeetings		(User user, Date from, Date to) throws ConnectException;
	public ArrayList<Event>getAppointments	(User user, Date from, Date to) throws ConnectException; 
	public ArrayList<Notification>getNotifications(User user, boolean unreadOnly, Date from, Date to) throws ConnectException;
	
	public ArrayList<Room>getAllRooms 		() throws ConnectException;
	public ArrayList<Room>getAllAvailableRooms(Event event) throws ConnectException;
	

	void deleteEvent(Event event) throws ConnectException;
	void addEvent(Event event) throws ConnectException;
	void editEvent(Event event) throws ConnectException;
	ArrayList<User> getAllUsers() throws ConnectException;
	
}
