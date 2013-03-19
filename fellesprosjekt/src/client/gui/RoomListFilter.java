package client.gui;

import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import net.miginfocom.swing.MigLayout;

import common.models.Room;
import common.tests.SampleRooms;

public class RoomListFilter extends JPanel{
	
	private JTextField TextLine;
	private JList roomList;
	private JScrollPane scrollPane;
	private DefaultListModel listModel, filteredListModel;
	private JSpinner spinnerCapacity;
	private JLabel lblRoomName;
	private JLabel lblCapacity;
	private int defaultCapacity = 1;
	
	public RoomListFilter(ArrayList<Room> rooms, int capacity){
		this(rooms);
		spinnerCapacity.setValue(capacity);
		filter();
	}

	public RoomListFilter(ArrayList<Room> rooms) {
		setBackground(Settings2.COLOR_VERY_DARK_GRAY);
	    
	    listModel = new DefaultListModel();
	    if (rooms != null){
	    	for (Room room : rooms){
	    		listModel.addElement(room);
	    	}
	    }

	    roomList=new JList(listModel);
	    roomList.setCellRenderer(new RoomListRenderer());
	    roomList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	    setLayout(new MigLayout("", "[85%:400px,grow][15%]", "[][23px][grow]"));
	    
	    spinnerCapacity = new JSpinner();
	    spinnerCapacity.setModel(new SpinnerNumberModel(defaultCapacity,1,999,1));
	    add(spinnerCapacity, "cell 1 1,growx");
	    
	    lblRoomName = new JLabel("Room name:");
	    lblRoomName.setForeground(Color.WHITE);
	    add(lblRoomName, "cell 0 0");
	    
	    lblCapacity = new JLabel("Capacity:");
	    lblCapacity.setForeground(Color.WHITE);
	    add(lblCapacity, "cell 1 0");
	    TextLine = new JTextField(20);
	    add(TextLine, "cell 0 1,growx");
	    
		
	    scrollPane = new JScrollPane(roomList);
	    add(scrollPane, "cell 0 2 2 1,grow");
	    
		addListeners();
       	}
	
	private void addListeners(){	
		DocumentListener dl = new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent evt) {
                filter();
            }

            @Override
            public void removeUpdate(DocumentEvent evt) {
                filter();
            }

            @Override
            public void changedUpdate(DocumentEvent evt) {
                filter();
            }
        };
        TextLine.getDocument().addDocumentListener(dl);
        
        ChangeListener cl = new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				filter();
			}
		};
		
        spinnerCapacity.getModel().addChangeListener(cl);
	}
	
	private void filter(){
		String[] searchWords = TextLine.getText().split(" ");
		filteredListModel = new DefaultListModel();
		for (int i=0; i<listModel.getSize(); i++){
			if(containsAll(((Room) listModel.get(i)).getName(), searchWords)){
				filteredListModel.addElement(listModel.get(i));
			}
		}
		roomList.setModel(filteredListModel);
		filterCapacity();
	}
	
	private void filterCapacity(){
		int capacityInput = (Integer) spinnerCapacity.getValue();
		for (int i=0; i<filteredListModel.getSize(); i++){
			if(((Room) filteredListModel.get(i)).getCapacity()<capacityInput){
				filteredListModel.removeElement(filteredListModel.get(i));
				i--;
				}
			}
		roomList.setModel(filteredListModel);
		}
	/*
	public void addRooms(ArrayList<Room> rooms){
		if (rooms == null) return;
		for (Room room : rooms){
			listModel.addElement(room);
		}
	}
	
	public ArrayList<Room> removeRooms(){
		if (roomList.isSelectionEmpty()) return null;
		ArrayList<Room> removedRooms = new ArrayList<Room>();
		int[] indices = roomList.getSelectedIndices();
		for (int i=0; i<indices.length; i++) {
			Room room = roomList.getModel().getElementAt(indices[i]-i);
			removedRooms.add(room);
			if (filteredListModel!=null) filteredListModel.removeElement(room);
			listModel.removeElement(room);
		}
		return removedRooms;
	}*/
	
	private boolean containsAll(String str, String[] searchTerm){
		for (String s : searchTerm){
			if(!str.toLowerCase().contains(s.toLowerCase())){
				return false;
			}
		}
		return true;
	}
	
	public Room getSelectedRoom(){
		return (Room) roomList.getSelectedValue();
	}
	
	public ArrayList<Room> getArrayList(){
		Object[] list = listModel.toArray();
		ArrayList<Room> al = new ArrayList<Room>();
		for (Object obj : list){
			al.add((Room) obj);
		}
		return al;
	}
	
    public static void main (String args[]) { 
    	ArrayList<Room> allRooms = SampleRooms.getSampleRooms();
        JFrame frame = new JFrame("Room List Filter Search"); 
        frame.getContentPane().add(new RoomListFilter(allRooms, 100)); 
        frame.pack(); 
        frame.setVisible(true);   
        }
    
}