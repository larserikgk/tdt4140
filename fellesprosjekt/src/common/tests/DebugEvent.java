package common.tests;

import common.models.Event;

public class DebugEvent {
	
	public static void printProperties(Event event){
		print("Event: "+event);
		print("Id: "+event.getId());
		print("Name: "+event.getName());
		print("Admin: "+event.getAdmin());
		print("Start: "+event.getStart());
		print("End: "+event.getEnd());
		print("Desc: "+event.getDescription());
	}
	
	public static void printNullCheck(Event event){
		print("NULLCHECK:");
		print("Event: "+(event == null));
		print("Id: "+(event.getId() == 0));
		print("Name: "+(event.getName() == null));
		print("Admin: "+(event.getAdmin() == null));
		print("Start: "+(event.getStart() == null));
		print("End: "+(event.getEnd() == null));
		print("Desc: "+(event.getDescription() == null));
	}
	
	public static void print(String s){
		System.out.println(s);
	}

}
