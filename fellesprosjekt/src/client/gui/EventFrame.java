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
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextPane;

import common.models.Event;
import common.models.EventCalendar;
import common.models.Room;
import common.models.User;
import javax.swing.JToggleButton;
import java.awt.FlowLayout;

public abstract class EventFrame extends BaseFrame implements PropertyChangeListener {
	
	private JTextField textField_name, textField_location;
	private JTextPane textPane_description; 
	private JLabel lblMainTitle;
	private JComboBox comboBox_repeat;
	private JPanel panel, panel_8;
	private JButton btnFinish, btnCancel, btnDeleteEvent;
	private Event event, eventOldValue;
	private JList listParticipants;
	private DefaultListModel listModel;
	private ResponseToggleButton tbAccept, tbDecline;
	
	private JButton btnEditParticipants, btnFindRoom;
	private DatePicker datePickerEnd, datePickerStart;
	private JPanel panel_3;
	private JPanel panel_2;
	private JPanel panel_4;
	
	public EventFrame(final Event event) {
		super();
		
		this.event = event;
		eventOldValue = event;
		
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
		
		panel_4 = new JPanel();
		panel_4.setBackground(Settings2.COLOR_VERY_DARK_GRAY);
		getContentPane().add(panel_4, "cell 2 0,grow");
		
		tbAccept = new ResponseToggleButton("Accept");
		tbAccept.setBounds(12, 5, 100, 23);
		tbDecline = new ResponseToggleButton("Decline");
		tbDecline.setBounds(124, 5, 100, 23);
		panel_4.setLayout(null);
		panel_4.add(tbAccept);
		panel_4.add(tbDecline);
		
		btnG.add(tbAccept);
		btnG.add(tbDecline);
		
		JLabel lblTitle = new JLabel("Title");
		lblTitle.setFont(Settings2.FONT_TEXT2);
		lblTitle.setForeground(Color.WHITE);
		getContentPane().add(lblTitle, "cell 1 1,alignx left,aligny center");
		
		textField_name = new JTextField("Event");
		getContentPane().add(textField_name, "cell 2 1,growx,aligny center");
		textField_name.setColumns(10);
		
		JLabel lblFrom = new JLabel("From");
		lblFrom.setFont(Settings2.FONT_TEXT2);
		lblFrom.setForeground(Color.WHITE);
		getContentPane().add(lblFrom, "cell 1 2,alignx left,aligny center");
		
		datePickerStart = new DatePicker(event.getStart());
		getContentPane().add(datePickerStart, "cell 2 2,alignx left,aligny center");
		
		JLabel lblTo = new JLabel("To");
		lblTo.setFont(Settings2.FONT_TEXT2);
		lblTo.setForeground(Color.WHITE);
		getContentPane().add(lblTo, "cell 1 3,alignx left,aligny center");
		
		datePickerEnd = new DatePicker(event.getEnd());
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
		
		panel_2 = new JPanel();
		panel_2.setBackground(Color.DARK_GRAY);
		getContentPane().add(panel_2, "cell 3 4,growx,aligny center");
		panel_2.setLayout(new GridLayout(0, 1, 0, 0));
		
		btnEditParticipants = new JButton("Edit participants");
		btnEditParticipants.setForeground(Color.WHITE);
		btnEditParticipants.setContentAreaFilled(false);
		btnEditParticipants.setBorderPainted(false);
		btnEditParticipants.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ParticipantsFrame frame = new ParticipantsFrame(event);
				openFrameOnTop(frame);				
			}
		});
		panel_2.add(btnEditParticipants);
		
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
		
		textField_location = new JTextField();
		getContentPane().add(textField_location, "cell 2 6,growx,aligny center");
		textField_location.setColumns(10);
		
		panel_3 = new JPanel();
		getContentPane().add(panel_3, "cell 3 6,growx,aligny center");
		panel_3.setBackground(Settings2.COLOR_DARK_GRAY);
		panel_3.setLayout(new GridLayout(0, 1, 0, 0));
		
		btnFindRoom = new JButton("Find room");
		panel_3.add(btnFindRoom);
		btnFindRoom.setContentAreaFilled(false);
		btnFindRoom.setBorderPainted(false);
		btnFindRoom.setForeground(Color.white);
		btnFindRoom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openFrameOnTop(new RoomFinderFrame(1));
			}
		});
		
		JLabel lblDescription = new JLabel("Description");
		lblDescription.setFont(Settings2.FONT_TEXT2);
		lblDescription.setForeground(Color.WHITE);
		getContentPane().add(lblDescription, "cell 1 7,aligny top");
		
		textPane_description = new JTextPane();
		getContentPane().add(textPane_description, "cell 2 7,grow");
	
		panel_8 = new JPanel();
		panel_8.setBackground(Settings2.COLOR_LIGHT_BLUE);
		getContentPane().add(panel_8, "cell 1 8,alignx left,aligny center");
		panel_8.setLayout(new GridLayout(0, 1, 0, 0));
		
		btnDeleteEvent = new JButton("Delete event");
		btnDeleteEvent.setForeground(Color.WHITE);
		btnDeleteEvent.setContentAreaFilled(false);
		btnDeleteEvent.setBorderPainted(false);
		panel_8.add(btnDeleteEvent);
		btnDeleteEvent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				User admin = event.getAdmin();
				EventCalendar oldValue = admin.getEventCalendar();
				admin.deleteEvent(event);
				event.delete();
				firePropertyChange("EventsCalendarChanged", oldValue, admin.getEventCalendar());
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
		panel_8.setVisible(false);
		
		datePickerStart.addPropertyChangeListener(this);
		setupEvent(event);
	}
	
	public void setupEvent(Event event) {
		textField_name.setText(event.getName());
		textPane_description.setText(event.getDescription());
		datePickerStart.setDate(event.getStart());
		datePickerEnd.setDate(event.getEnd());
		textField_location.setText(event.getLocation());
		setupParticipants();
	}
	
	public void setIsEditable(boolean b) {
		datePickerStart.setEnabled(b);
		datePickerEnd.setEnabled(b);
		tbAccept.setEnabled(!b);
		tbDecline.setEnabled(!b);
		panel_4.setVisible(!b);
		comboBox_repeat.setEnabled(b);
		btnEditParticipants.setEnabled(b);
		panel.setVisible(b);
		btnCancel.setEnabled(b);
		btnDeleteEvent.setEnabled(b);
		panel_2.setVisible(b);
		panel_3.setVisible(b);
		panel_8.setVisible(b);
		btnFindRoom.setEnabled(b);
		textField_name.setEditable(b);
		textField_location.setEditable(b);
		textPane_description.setEditable(b); 
	}
	
	public void setFinishButtonAction(final boolean onlyClose) {
		btnFinish.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (onlyClose){
					close();
					return;
				}
				if (!saveAttributes()) return;
				close();
				Event newValue = event;
				User admin = event.getAdmin();
				EventCalendar oldValue = admin.getEventCalendar();
				if (EventFrame.this instanceof CreateEventFrame) {
					admin.addEvent(event);
				} else {
					admin.editEvent(event, eventOldValue);
				}
				firePropertyChange("EventsCalendarChanged", oldValue, admin.getEventCalendar());
			}
		});
	}

	@SuppressWarnings("deprecation")
	public boolean saveAttributes() {
		if (datePickerEnd.getDate().compareTo(datePickerStart.getDate()) < 0) return false;
		//Bruker b�r f� opp melding om at sluttid ikke kan v�re f�r starttid
		System.out.println("fortsetter");
		event.setName(textField_name.getText());
		event.setStart(datePickerStart.getDate());
		event.setEnd(datePickerEnd.getDate());
		event.setDescription(textPane_description.getText());
		event.setLocation(textField_location.getText());
		return true;
	}
	
	public void setDeleteEventButtonEnabled(boolean b) {
		btnDeleteEvent.setEnabled(b);
		panel_8.setVisible(b);
	}
	
	public void setFinishButtonText(String s) {
		btnFinish.setText(s);
	}
	
	public void setMainTitle(String s) {
		lblMainTitle.setText(s);
	}
	
	public void setupParticipants(){
		listModel.clear();
		if (event.getParticipants() != null){
	    	for (User user : event.getParticipants()){
	    		listModel.addElement(user);
	    	}
	    }
	}
	
	public void setRoom(Room room){
		event.setLocation(room.getName());
		textField_location.setText(room.getName());
	}
	
	public Event getEvent(){
		return event;
	}
	
	public void propertyChange(PropertyChangeEvent evt) 
	{		
		if(evt.getPropertyName().equals("datePickerDate"))
		{
			System.out.println("RECIEVED IN EVENTFRAME: "+evt.getNewValue());
			datePickerEnd.setMinimumDate((Date) evt.getNewValue());
		}
	}
	
}