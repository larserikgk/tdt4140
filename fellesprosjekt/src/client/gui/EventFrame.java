 package client.gui;

import java.awt.Color;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.Border;
import net.miginfocom.swing.MigLayout;
import java.awt.GridLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.net.ConnectException;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextPane;

import client.net.ServerConnector;
import common.models.Event;
import common.models.EventCalendar;
import common.models.ParticipantStatus;
import common.models.Room;
import common.models.User;
import javax.swing.JToggleButton;
import java.awt.FlowLayout;

public abstract class EventFrame extends BaseFrame implements PropertyChangeListener {
	
	private JTextField textField_name, textField_location;
	private JTextPane textPane_description; 
	private JLabel lblMainTitle;
	private JComboBox comboBox_repeat;
	private JPanel panel, panel_deletebtn;
	private JButton btnFinish, btnCancel, btnDeleteEvent;
	private Event eventOriginal, eventCopy;
	private JList listParticipants;
	private DefaultListModel listModel;
	private ResponseToggleButton tbAccept, tbDecline;
	
	private JButton btnEditParticipants, btnFindRoom;
	private DatePicker datePickerEnd, datePickerStart;
	private JPanel panel_findroombtn;
	private JPanel panel_editpartbtn;
	private JPanel panel_RSVPtbtn;
	private JLabel lblNameValue, lblLocationValue, lblDescriptionValue;
		
	public EventFrame(final Event event) {
		super();
		this.eventOriginal = event;
		eventCopy = event;
			
		setResizable(false);
		setSize(709, 517);
		setCentered();
		getContentPane().setBackground(Settings2.COLOR_VERY_DARK_GRAY);
		Border border = BorderFactory.createLineBorder(Settings2.COLOR_ORANGE);
		//getContentPane().setBorder(border);
		getContentPane().setLayout(new MigLayout("", "[10.00,grow][99px,grow][236px,grow][112px,grow][65px,grow]", "[5%,grow][5%,grow][5%,grow][5%,grow][25%,grow][5%,grow][5%,grow][10%,grow][5%,grow]"));
	
		lblMainTitle = new JLabel("Create event");
		lblMainTitle.setFont(Settings2.FONT_TITLE1);
		lblMainTitle.setFont(Settings2.FONT_TEXT1);
		lblMainTitle.setForeground(Color.WHITE);
		getContentPane().add(lblMainTitle, "cell 0 0 2 1,alignx left,aligny center");
		
		ButtonGroup btnG = new ButtonGroup();
		
		panel_RSVPtbtn = new JPanel();
		panel_RSVPtbtn.setBackground(Settings2.COLOR_VERY_DARK_GRAY);
		getContentPane().add(panel_RSVPtbtn, "cell 2 0,grow");
		
		tbAccept = new ResponseToggleButton("Accept");
		tbAccept.setBounds(12, 5, 100, 23);
		tbDecline = new ResponseToggleButton("Decline");
		tbDecline.setBounds(124, 5, 100, 23);
		panel_RSVPtbtn.setLayout(null);
		panel_RSVPtbtn.add(tbAccept);
		panel_RSVPtbtn.add(tbDecline);
		
		btnG.add(tbAccept);
		btnG.add(tbDecline);
		
		JLabel lblTitle = new JLabel("Title");
		lblTitle.setFont(Settings2.FONT_TEXT2);
		lblTitle.setForeground(Color.WHITE);
		getContentPane().add(lblTitle, "cell 1 1,alignx left,aligny center");
		
		lblNameValue = new JLabel("Event");
		lblNameValue.setForeground(Color.WHITE);
		
		textField_name = new JTextField("Event");
		getContentPane().add(textField_name, "cell 2 1,growx,aligny center");
		textField_name.setColumns(10);
		
		JLabel lblFrom = new JLabel("From");
		lblFrom.setFont(Settings2.FONT_TEXT2);
		lblFrom.setForeground(Color.WHITE);
		getContentPane().add(lblFrom, "cell 1 2,alignx left,aligny center");
		
		datePickerStart = new DatePicker(eventCopy.getStart());
		getContentPane().add(datePickerStart, "cell 2 2,alignx left,aligny center");
		
		JLabel lblTo = new JLabel("To");
		lblTo.setFont(Settings2.FONT_TEXT2);
		lblTo.setForeground(Color.WHITE);
		getContentPane().add(lblTo, "cell 1 3,alignx left,aligny center");
		
		datePickerEnd = new DatePicker(eventCopy.getEnd());
		datePickerEnd.setDefaultTime(12, 0);
		getContentPane().add(datePickerEnd, "cell 2 3,alignx left,aligny center");
		
		JLabel lblParticipants = new JLabel("Participants");
		lblParticipants.setFont(Settings2.FONT_TEXT2);
		lblParticipants.setForeground(Color.WHITE);
		getContentPane().add(lblParticipants, "cell 1 4,alignx left,aligny top");
		
		listModel = new DefaultListModel();
		listParticipants = new JList(listModel);
		listParticipants.setCellRenderer(new UserListRenderer());
		JScrollPane participantsScrollPane = new JScrollPane(listParticipants);
		getContentPane().add(participantsScrollPane, "cell 2 4,grow");
		
		panel_editpartbtn = new JPanel();
		panel_editpartbtn.setBackground(Color.DARK_GRAY);
		getContentPane().add(panel_editpartbtn, "cell 3 4,growx,aligny center");
		panel_editpartbtn.setLayout(new GridLayout(0, 1, 0, 0));
		
		btnEditParticipants = new JButton("Edit participants");
		btnEditParticipants.setForeground(Color.WHITE);
		btnEditParticipants.setContentAreaFilled(false);
		btnEditParticipants.setBorderPainted(false);
		btnEditParticipants.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ParticipantsFrame frame = new ParticipantsFrame(eventCopy);
				openFrameOnTop(frame);				
			}
		});
		panel_editpartbtn.add(btnEditParticipants);
		
		JLabel lblRepeat = new JLabel("Repeat");
		lblRepeat.setFont(Settings2.FONT_TEXT2);
		lblRepeat.setForeground(Color.WHITE);
		getContentPane().add(lblRepeat, "cell 1 5,alignx left");
		
		comboBox_repeat = new JComboBox();
		comboBox_repeat.setModel(new DefaultComboBoxModel(new String[] {"One-time event", "Daily", "Weekdays (Mon - Fri)", "Weekly", "Monthly", "Yearly"}));
		getContentPane().add(comboBox_repeat, "cell 2 5,growx");
		
		JLabel lblLocation = new JLabel("Location");
		lblLocation.setFont(Settings2.FONT_TEXT2);
		lblLocation.setForeground(Color.WHITE);
		getContentPane().add(lblLocation, "cell 1 6,alignx left,aligny center");
		
		lblLocationValue = new JLabel();
		lblLocationValue.setForeground(Color.WHITE);
		
		textField_location = new JTextField();
		getContentPane().add(textField_location, "cell 2 6,growx,aligny center");
		textField_location.setColumns(10);
		
		panel_findroombtn = new JPanel();
		getContentPane().add(panel_findroombtn, "cell 3 6,growx,aligny center");
		panel_findroombtn.setBackground(Settings2.COLOR_DARK_GRAY);
		panel_findroombtn.setLayout(new GridLayout(0, 1, 0, 0));
		
		btnFindRoom = new JButton("Find room");
		panel_findroombtn.add(btnFindRoom);
		btnFindRoom.setContentAreaFilled(false);
		btnFindRoom.setBorderPainted(false);
		btnFindRoom.setForeground(Color.white);
		btnFindRoom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openFrameOnTop(new RoomFinderFrame(eventCopy));
			}
		});
		
		JLabel lblDescription = new JLabel("Description");
		lblDescription.setFont(Settings2.FONT_TEXT2);
		lblDescription.setForeground(Color.WHITE);
		getContentPane().add(lblDescription, "cell 1 7,aligny top");
		
		lblDescriptionValue = new JLabel();
		lblDescriptionValue.setForeground(Color.WHITE);
		
		textPane_description = new JTextPane();
		getContentPane().add(textPane_description, "cell 2 7,grow");
	
		panel_deletebtn = new JPanel();
		panel_deletebtn.setBackground(Settings2.COLOR_LIGHT_BLUE);
		getContentPane().add(panel_deletebtn, "cell 1 8,alignx left,aligny center");
		panel_deletebtn.setLayout(new GridLayout(0, 1, 0, 0));
		
		btnDeleteEvent = new JButton("Delete event");
		btnDeleteEvent.setForeground(Color.WHITE);
		btnDeleteEvent.setContentAreaFilled(false);
		btnDeleteEvent.setBorderPainted(false);
		panel_deletebtn.add(btnDeleteEvent);
		btnDeleteEvent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				User admin = event.getAdmin();
				EventCalendar oldValue = admin.getEventCalendar();
				try {
					getServerConnector().deleteEvent(eventOriginal);
					getUser().deleteEvent(eventOriginal);
				} catch (ConnectException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				firePropertyChange("EventCalendarChanged", oldValue, admin.getEventCalendar());
				close();
			}
		});
		
		panel = new JPanel();
		getContentPane().add(panel, "cell 3 8,alignx right,aligny center");
		panel.setBackground(Settings2.COLOR_LIGHT_BLUE);
		panel.setLayout(new GridLayout(1, 0, 0, 0));
		
		btnCancel = new JButton("Cancel");
		btnCancel.setForeground(Color.WHITE);
		btnCancel.setBorderPainted(false);
		btnCancel.setContentAreaFilled(false);
		panel.add(btnCancel);
		
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				close();
				if (!(EventFrame.this instanceof ShowEventFrame)) {
					return;
				}
				try {
					if (tbAccept.isSelected())
						getServerConnector().updateStatus(new ParticipantStatus(getUser().getUsername(), eventOriginal.getId(), ParticipantStatus.Status.SKAL));
					else if (tbDecline.isSelected())
						getServerConnector().updateStatus(new ParticipantStatus(getUser().getUsername(), eventOriginal.getId(), ParticipantStatus.Status.SKALIKKE));
				} catch (ConnectException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Settings2.COLOR_LIGHT_BLUE);
		getContentPane().add(panel_1, "cell 4 8,alignx left,aligny center");
		panel_1.setLayout(new GridLayout(1, 0, 0, 0));
		
		btnFinish = new JButton("Create");
		btnFinish.setForeground(Color.WHITE);
		btnFinish.setContentAreaFilled(false);
		btnFinish.setBorderPainted(false);
		panel_1.add(btnFinish);
		
		//Delete event notEnabled by default
		btnDeleteEvent.setEnabled(false);
		panel_deletebtn.setVisible(false);
		
		datePickerStart.addPropertyChangeListener(this);
		setupEvent(event);
	}
	
	public void setupEvent(Event event) {
		textField_name.setText(event.getName());
		lblNameValue.setText(event.getName());
		textPane_description.setText(event.getDescription());
		lblDescriptionValue.setText(event.getDescription());
		datePickerStart.setDate(event.getStart());
		datePickerEnd.setDate(event.getEnd());
		String l ="";
		if (eventOriginal.getRoom() != null) {
			l = event.getRoom().getName();
		}
		if (!l.equals("")) {
			textField_location.setText(l);
			lblLocationValue.setText(l);
		} else {
			textField_location.setText(eventOriginal.getLocation());
			lblLocationValue.setText(eventOriginal.getLocation());
		}
		setupParticipants();
	}
	
	public void setIsEditable(boolean b) {
		panel_RSVPtbtn.setVisible(!b);
		panel_editpartbtn.setVisible(b);
		panel_findroombtn.setVisible(b);
		panel_deletebtn.setVisible(b);
		panel.setVisible(b);
		
		datePickerStart.setEnabled(b);
		datePickerEnd.setEnabled(b);
		tbAccept.setEnabled(!b);
		tbDecline.setEnabled(!b);
		
		comboBox_repeat.setEnabled(b);
		btnEditParticipants.setEnabled(b);
		
		btnCancel.setEnabled(b);
		btnDeleteEvent.setEnabled(b);
		btnFindRoom.setEnabled(b);
		
		if (!b) {
			getContentPane().remove(textField_name);
			getContentPane().add(lblNameValue, "cell 2 1,growx,aligny center");
			getContentPane().remove(textField_location);
			getContentPane().add(lblLocationValue, "cell 2 6,growx,aligny center");
			getContentPane().remove(textPane_description);
			getContentPane().add(lblDescriptionValue, "cell 2 7,grow");
		}
	}
	
	public void setFinishButtonAction(final boolean onlyClose) {
		btnFinish.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (onlyClose){
					close();
					return;
				}
				try {
					if (!saveAttributes()) return;
				} catch (ConnectException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				close();
				User admin = eventOriginal.getAdmin();
				EventCalendar oldValue = admin.getEventCalendar();
				firePropertyChange("EventCalendarChanged", oldValue, admin.getEventCalendar());
			}
		});
	}

	
	public boolean saveAttributes() throws ConnectException {
		if (datePickerEnd.getDate().compareTo(datePickerStart.getDate()) < 0) return false;
		//Bruker b�r f� opp melding om at sluttid ikke kan v�re f�r starttid
		System.out.println("fortsetter");
		eventCopy.setName(textField_name.getText());
		eventCopy.setStart(datePickerStart.getDate());
		eventCopy.setEnd(datePickerEnd.getDate());
		eventCopy.setDescription(textPane_description.getText());
		if (eventCopy.getParticipants() == null || eventCopy.getParticipants().size() == 0) 
			eventCopy.addParticipant(getUser());
		
		eventOriginal = eventCopy;
		System.out.println("gui sender eventId: "+eventOriginal.getId());
		if (this instanceof CreateEventFrame) {
			ArrayList<Event> updatedEvents = getServerConnector().getEvents(getUser(), 0);
			getUser().newEventCalendar();
			for (Event e : updatedEvents) {
				getUser().addEvent(e);
			}
			System.out.println("gui kaller addEvent");
			getServerConnector().addEvent(eventOriginal);
		} else {
			System.out.println("gui kaller editEvent");
			getServerConnector().editEvent(eventOriginal);
		}
		return true;
	}
	
	public void setDeleteEventButtonEnabled(boolean b) {
		btnDeleteEvent.setEnabled(b);
		panel_deletebtn.setVisible(b);
	}
	
	public void setFinishButtonText(String s) {
		btnFinish.setText(s);
	}
	
	public void setMainTitle(String s) {
		lblMainTitle.setText(s);
	}
	
	public void setupParticipants(){
		listModel.clear();
		if (eventOriginal.getParticipants() != null){
	    	for (User user : eventOriginal.getParticipants()){
	    		listModel.addElement(user);
	    	}
	    }
	}
	
	public void setRoom(Room room){
		if (room != null) {
			eventCopy.setLocation(room.getName());
			eventCopy.setRoom(room);
			textField_location.setText(room.getName());		
		} else {
			textField_location.setText(eventOriginal.getLocation());
		}
	}
	
	public Event getEvent(){
		return eventOriginal;
	}
	
	public void propertyChange(PropertyChangeEvent evt) 
	{		
		if(evt.getPropertyName().equals("datePickerDate"))
		{
			System.out.println("RECIEVED IN EVENTFRAME: "+evt.getNewValue());
			datePickerEnd.setMinimumDate((Date) evt.getNewValue());
		}
	}

	public void setLocationText(String location) {
		textField_location.setText(location);		
	}
	
}