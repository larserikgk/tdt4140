 package client.gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
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
import java.beans.PropertyChangeSupport;
import java.util.Date;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextPane;

import common.models.Event;
import common.models.EventCalendar;
import common.models.User;

public abstract class EventFrame extends BaseFrame {
	
	private JTextField textField_name, textField_location;
	private JTextPane textPane_description; 
	private JLabel lblMainTitle;
	private JComboBox comboBox_repeat;
	private JPanel panel_4, panel_5, panel, panel_8;
	private JButton btnFinish, btnCancel, btnDeleteEvent;
	private Event event, eventOldValue;
	private JList listParticipants;
	private DefaultListModel<User> listModel;
	
	private JButton btnAddParticipants, btnFindRoom;
	private DatePicker datePickerEnd, datePickerStart;
	
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
		getContentPane().setLayout(new MigLayout("", "[][10.00,grow][99px,grow][236px,grow][112px,grow][65px,grow]", "[5%,grow][1%,grow][5%,grow][5%,grow][5%,grow][25%,grow][5%,grow][5%,grow][10%,grow][1%,grow][5%,grow]"));
	
		lblMainTitle = new JLabel("Create event");
		lblMainTitle.setFont(Settings2.FONT_TITLE1);
		lblMainTitle.setFont(Settings2.FONT_TEXT1);
		lblMainTitle.setForeground(Color.WHITE);
		getContentPane().add(lblMainTitle, "cell 1 0 2 1,alignx left,aligny center");
		
		
		JLabel lblTitle = new JLabel("Title");
		lblTitle.setFont(Settings2.FONT_TEXT2);
		lblTitle.setForeground(Color.WHITE);
		getContentPane().add(lblTitle, "cell 2 2,alignx left,aligny center");
		
		textField_name = new JTextField("Event");
		getContentPane().add(textField_name, "cell 3 2,growx,aligny center");
		textField_name.setColumns(10);
		
		JLabel lblFrom = new JLabel("From");
		lblFrom.setFont(Settings2.FONT_TEXT2);
		lblFrom.setForeground(Color.WHITE);
		getContentPane().add(lblFrom, "cell 2 3,alignx left,aligny center");
		
		datePickerStart = new DatePicker(event.getStart());
		getContentPane().add(datePickerStart, "cell 3 3,alignx left,aligny center");
		
		JLabel lblTo = new JLabel("To");
		lblTo.setFont(Settings2.FONT_TEXT2);
		lblTo.setForeground(Color.WHITE);
		getContentPane().add(lblTo, "cell 2 4,alignx left,aligny center");
		
		datePickerEnd = new DatePicker(event.getEnd());
		getContentPane().add(datePickerEnd, "cell 3 4,alignx left,aligny center");
		
		JLabel lblParticipants = new JLabel("Participants");
		lblParticipants.setFont(Settings2.FONT_TEXT2);
		lblParticipants.setForeground(Color.WHITE);
		getContentPane().add(lblParticipants, "cell 2 5,alignx left,aligny top");
		
		listModel = new DefaultListModel<User>();
		listParticipants = new JList(listModel);
		listParticipants.setCellRenderer(new UserListRenderer());
		setupParticipants();
		JScrollPane participantsScrollPane = new JScrollPane(listParticipants);
		getContentPane().add(participantsScrollPane, "cell 3 5,grow");
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(Settings2.COLOR_VERY_DARK_GRAY);
		panel_2.setVisible(true);
		getContentPane().add(panel_2, "cell 4 5 1 4,grow");
		panel_2.setLayout(new MigLayout("", "[125px]", "[23px][][][][][][][]"));
		
		panel_4 = new JPanel();
		panel_4.setBackground(Settings2.COLOR_DARK_GRAY);
		panel_2.add(panel_4, "cell 0 0,grow");
		panel_4.setLayout(new GridLayout(0, 1, 0, 0));
		
		btnAddParticipants = new JButton("Add participants");
		btnAddParticipants.setContentAreaFilled(false);
		btnAddParticipants.setForeground(Color.WHITE);
		btnAddParticipants.setBorderPainted(false);
		btnAddParticipants.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BaseFrame frame = new ParticipantsFrame();
				openFrameOnTop(frame);				
			}
		});
		
		panel_4.add(btnAddParticipants);
		
		panel_5 = new JPanel();
		panel_5.setBackground(Settings2.COLOR_DARK_GRAY);
		panel_2.add(panel_5, "cell 0 7,grow");
		panel_5.setLayout(new GridLayout(0, 1, 0, 0));
		
		btnFindRoom = new JButton("Find room");
		panel_5.add(btnFindRoom);
		btnFindRoom.setContentAreaFilled(false);
		btnFindRoom.setBorderPainted(false);
		btnFindRoom.setForeground(Color.white);
		btnFindRoom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openFrameOnTop(new RoomFinderFrame(1));
			}
		});
		
		JLabel lblRepeat = new JLabel("Repeat");
		lblRepeat.setFont(Settings2.FONT_TEXT2);
		lblRepeat.setForeground(Color.WHITE);
		getContentPane().add(lblRepeat, "cell 2 6,alignx left");
		
		comboBox_repeat = new JComboBox();
		comboBox_repeat.setModel(new DefaultComboBoxModel(new String[] {"One-time event", "Daily", "Weekdays (Mon - Fri)", "Weekly", "Monthly", "Yearly"}));
		getContentPane().add(comboBox_repeat, "cell 3 6,growx");
		
		JLabel lblLocation = new JLabel("Location");
		lblLocation.setFont(Settings2.FONT_TEXT2);
		lblLocation.setForeground(Color.WHITE);
		getContentPane().add(lblLocation, "cell 2 7,alignx left,aligny center");
		
		textField_location = new JTextField();
		getContentPane().add(textField_location, "cell 3 7,growx,aligny center");
		textField_location.setColumns(10);
		
		JLabel lblDescription = new JLabel("Description");
		lblDescription.setFont(Settings2.FONT_TEXT2);
		lblDescription.setForeground(Color.WHITE);
		getContentPane().add(lblDescription, "cell 2 8,aligny top");
		
		textPane_description = new JTextPane();
		getContentPane().add(textPane_description, "cell 3 8,grow");
	
		panel_8 = new JPanel();
		panel_8.setBackground(Settings2.COLOR_LIGHT_BLUE);
		getContentPane().add(panel_8, "cell 2 10,alignx left,aligny center");
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
				event.delete();
				firePropertyChange("EventsCalendarChanged", oldValue, admin.getEventCalendar());
			}
		});
		
		panel = new JPanel();
		getContentPane().add(panel, "cell 4 10,alignx right,aligny center");
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
		getContentPane().add(panel_1, "cell 5 10,alignx left,aligny center");
		panel_1.setLayout(new GridLayout(1, 0, 0, 0));
		
		btnFinish = new JButton("Create");
		btnFinish.setForeground(Color.WHITE);
		btnFinish.setContentAreaFilled(false);
		btnFinish.setBorderPainted(false);
		panel_1.add(btnFinish);
		
		//Delete event notEnabled by default
		btnDeleteEvent.setEnabled(false);
		panel_8.setVisible(false);
	}
	
	public void setEventTitle(String title){
		textField_name.setText(title);
		event.setName(title);
	}
	
	public void setIsEditable(boolean b) {
		comboBox_repeat.setEnabled(b);
		btnAddParticipants.setEnabled(b);
		panel.setVisible(b);
		btnCancel.setEnabled(b);
		btnDeleteEvent.setEnabled(b);
		panel_8.setVisible(b);
		panel_4.setVisible(b);
		panel_5.setVisible(b);
		btnFindRoom.setEnabled(b);
		textField_name.setEditable(b);
		textField_location.setEditable(b);
		textPane_description.setEditable(b); 
	}
	
	public void setFinishButtonAction(final boolean onlyClose) {
		btnFinish.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				close();
				if (onlyClose) return;
				setEventNewAttributes();
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
	public void setEventNewAttributes() {
		event.setName(textField_name.getText());
		event.setStart(datePickerStart.getDate());
		event.setEnd(datePickerEnd.getDate());
		event.setDescription(textPane_description.getText());
		event.setLocation(textField_location.getText());
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
		if (event.getParticipants() != null){
	    	for (User user : event.getParticipants()){
	    		listModel.addElement(user);
	    	}
	    }
	}
	
	public Event getEvent(){
		return event;
	}
	
}