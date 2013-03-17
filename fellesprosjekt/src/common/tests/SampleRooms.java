package common.tests;

import java.util.ArrayList;
import java.util.Arrays;

import common.models.Room;

public class SampleRooms {
	
	private static int roomCount = 100;
	private static Room[] sampleRoomList;
	private static ArrayList<Room> sampleRooms;
	
	private static void generateLists(){
		sampleRooms = new ArrayList<Room>();
		sampleRoomList = new Room[roomCount];
		
		for (int i=0; i<roomCount; i++){
			Room r = new Room("Room "+(i+1), (i+1)*5);
			sampleRooms.add(r);
			sampleRoomList[i]=r;
		}
	}
	
	public static Room[] getSampleList(){
		generateLists();
		return sampleRoomList;
	}
	
	public static ArrayList<Room> getSampleRooms(){
		generateLists();
		return sampleRooms;
	}

}
