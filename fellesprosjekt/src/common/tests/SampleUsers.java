package common.tests;

import java.util.ArrayList;

import common.models.User;

public class SampleUsers {
	
	private static int userCount = 100;
	private static ArrayList<User> sampleUsers = new ArrayList<User>();
	private static User[] sampleUsersList = new User[userCount];
	
	public static void generateLists(){
		for (int i=0; i<userCount; i++){
			User user = new User("user"+i,i+"", "Person "+i);
			sampleUsersList[i] = user;
			sampleUsers.add(user);
		}
	}
	
	public static ArrayList<User> getSampleUsers(){
		generateLists();
		return sampleUsers;
	}
	
	public static User[] getSampleUsersList(){
		generateLists();
		return sampleUsersList;
	}

}
