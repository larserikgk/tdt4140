package common.tests;

import java.util.ArrayList;

import common.models.User;

public class SampleUsers {
	
	private ArrayList<User> sampleUsers = new ArrayList<User>();
	
	public SampleUsers(){
		for (int i=0; i<10; i++){
			User user = new User("user"+i,i+"", "Person "+i);
			sampleUsers.add(user);
		}
	}
	
	public ArrayList<User> getSampleUsers(){
		return sampleUsers;
	}

}
