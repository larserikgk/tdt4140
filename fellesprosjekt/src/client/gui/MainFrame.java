package client.gui;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Window;

import net.miginfocom.swing.MigLayout;
import javax.swing.JButton;
import javax.swing.JMenu;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.ListSelectionModel;
import javax.swing.UIDefaults;
import javax.swing.UIManager;
import javax.swing.JPanel;
import javax.swing.JList;
import javax.swing.AbstractListModel;
import java.awt.Font;
import javax.swing.JMenuItem;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.Component;
import java.awt.BorderLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Date;
import javax.swing.SwingConstants;
import java.awt.Dimension;
import javax.swing.JLayeredPane;
import javax.swing.JPopupMenu;

import common.models.Event;
import common.models.User;



public class MainFrame extends BaseFrame implements PropertyChangeListener {
	private static JFrame frame;
	private JMenuBar menuBar;
	private JLabel lblSelectedDate, lblMonth, lblFullName;
	private JButton btnNextMonth, btnPrevMonth;
	private CoolCalendar coolCalendar;
	private Date selectedDate;
	private JPanel panel_1;
	
	//Colors
	public static Color PANELGRAY	= new Color(33,33,33);
	public static Color BUTTONBLUE	= new Color(24,161,195);
	
	
	//TESTING TESTING! 1, 2, 1, 2
	public static void main (String args[]) {
		User testUser = new User("TestUserName", "test", "TestUser");
		Event testEvent = new Event(12, "TestMøte", new Date(2013, 2, 21, 12, 30), new Date(2013, 2, 21, 14, 30));
		testUser.addEvent(testEvent);
		frame = new MainFrame(testUser); 
		frame.setSize(700, 500);
		frame.setLocation(300, 200);
		frame.setVisible(true);
	}
	 
	public MainFrame(User user) {
		super();
		setMaximized();
		getContentPane().setBackground(Color.LIGHT_GRAY);
				
		// MENU BAR
		menuBar = new JMenuBar();
		menuBar.setToolTipText("");
		menuBar.setForeground(Color.WHITE);
		menuBar.setBackground(Color.BLACK);
		setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		mnFile.setForeground(Color.WHITE);
		menuBar.add(mnFile);
		
		JMenuItem mntmLogOut = new JMenuItem("Log out");
		mntmLogOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				close();
				BaseFrame frame = new LoginFrame();
				frame.setVisible(true);
			}
		});
		
		mnFile.add(mntmLogOut);
		
		JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				close();			
			}
		});
		
		mnFile.add(mntmExit);
		
		JMenu mnEdit = new JMenu("Edit");
		mnEdit.setForeground(Color.WHITE);
		menuBar.add(mnEdit);
		
		JMenuItem mntmMenuItem = new JMenuItem("Menu item 1");
		mnEdit.add(mntmMenuItem);
		
		// Content Pane
		getContentPane().setLayout(new MigLayout("", "[70%,grow][30%,grow]", "[6%,grow][49%,grow][44%,grow]"));
		
		JPanel panel_7 = new JPanel();
		getContentPane().add(panel_7, "cell 0 0,alignx center,growy");
		panel_7.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_8 = new JPanel();
		panel_8.setBackground(Color.LIGHT_GRAY);
		panel_7.add(panel_8, BorderLayout.WEST);
		panel_8.setLayout(new GridLayout(0, 1, 0, 0));
		
		btnPrevMonth = new JButton("prev");
		btnPrevMonth.setContentAreaFilled(false);
		btnPrevMonth.setBorderPainted(false);
		panel_8.add(btnPrevMonth);
		btnPrevMonth.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				coolCalendar.previousMonth();
				updateMonthLabels();
			}
		});
		
		JPanel panel_9 = new JPanel();
		panel_9.setBackground(Color.LIGHT_GRAY);
		panel_7.add(panel_9, BorderLayout.EAST);
		panel_9.setLayout(new GridLayout(0, 1, 0, 0));
		
		btnNextMonth = new JButton("next");
		btnNextMonth.setContentAreaFilled(false);
		btnNextMonth.setBorderPainted(false);
		panel_9.add(btnNextMonth);
		btnNextMonth.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				coolCalendar.nextMonth();
				updateMonthLabels();
			}
		});
		
		JPanel panel_10 = new JPanel();
		panel_10.setMinimumSize(new Dimension(200, 10));
		panel_7.add(panel_10, BorderLayout.CENTER);
		panel_10.setLayout(new GridLayout(0, 1, 0, 0));
		panel_10.setBackground(Color.LIGHT_GRAY);
		
		lblMonth = new JLabel("New label");
		lblMonth.setFont(Settings2.FONT_TITLE1);
		lblMonth.setHorizontalAlignment(SwingConstants.CENTER);
		panel_10.add(lblMonth);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(Color.LIGHT_GRAY);
		getContentPane().add(panel_2, "cell 1 0,grow");
		panel_2.setLayout(new MigLayout("", "[60%,grow][40%,grow]", "[6%,grow]"));
		
		lblFullName = new JLabel("Full Name");
		panel_2.add(lblFullName, "cell 0 0");
		
		JPanel panel_5 = new JPanel();
		panel_5.setBackground(Color.red);
		panel_2.add(panel_5, "cell 1 0,grow");
		panel_5.setLayout(new GridLayout(0, 1, 0, 0));
		
		final JButton btnNotifications = new JButton("Notifications");
		btnNotifications.setContentAreaFilled(false);
		btnNotifications.setBorderPainted(false);
		btnNotifications.setForeground(Color.white);
		panel_5.add(btnNotifications);
		
		final JPopupMenu popupMenu = new JPopupMenu();
		//popupMenu.setBackground(new Color(200, 100, 100));
		
		JMenuItem mntmInvitationTomte = new JMenuItem("Invitation to \"M\u00F8te 1\"");
		mntmInvitationTomte.setContentAreaFilled(false);
		mntmInvitationTomte.setBorderPainted(false);
		popupMenu.add(mntmInvitationTomte);
		
		JMenuItem mntmmteIs = new JMenuItem("\"M\u00F8te 2\" is canceled");
		popupMenu.add(mntmmteIs);
		
		/*
		if (popupMenu.getSubElements().length == 0) {
			JMenuItem noNots = new JMenuItem("No notifications");
			popupMenu.add(noNots);
		}*/
		
		btnNotifications.addMouseListener(new MouseListener () {
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1) {
					popupMenu.show(btnNotifications, - btnNotifications.getWidth(), btnNotifications.getHeight());
					popupMenu.setPopupSize(2 * btnNotifications.getWidth(), 3 * btnNotifications.getHeight());
				}
			}

			public void mouseEntered(MouseEvent e) {}

			public void mouseExited(MouseEvent e) {}
			
			public void mousePressed(MouseEvent e) {}
			
			public void mouseReleased(MouseEvent e) {}
		});
		
		
		//Calendar
		JPanel panel_4 = new JPanel();
		getContentPane().add(panel_4, "cell 0 1,grow");
		panel_4.setLayout(new GridLayout(0, 1, 0, 0));
		
		coolCalendar = new CoolCalendar(user.getEventCalendar());
		panel_4.add(coolCalendar);
		
		coolCalendar.addPropertyChangeListener(this);
		//this.addPropertyChangeListener(coolCalendar);
				
		
		panel_1 = new JPanel();
		getContentPane().add(panel_1, "cell 1 1 1 2,grow");
		panel_1.setBackground(Settings2.COLOR_DARK_GRAY);
		panel_1.setLayout(new MigLayout("", "[138px]", "[24px]"));
		
		JLabel label = new JLabel("Upcoming events");
		label.setForeground(Color.WHITE);
		label.setFont(Settings2.FONT_TEXT1);
		panel_1.add(label, "cell 0 0,alignx left,aligny top");
		
		JPanel panel = new JPanel();
		panel.setBackground(PANELGRAY);
		getContentPane().add(panel, "cell 0 2,grow");
		panel.setLayout(new MigLayout("", "[grow][]", "[][80%,grow][10%,grow]"));
		
		lblSelectedDate = new JLabel("15 March 2013");
		
		lblSelectedDate.setFont(Settings2.FONT_TEXT1);
		lblSelectedDate.setForeground(Color.WHITE);
		panel.add(lblSelectedDate, "cell 0 0");
		
		final JList list = new JList();
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setCellRenderer(new SelectedListCellRenderer());
		list.setForeground(Color.WHITE);
		list.setBackground(PANELGRAY);
		list.setModel(new AbstractListModel() {
			String[] values = new String[] {"Tannlege idag, der og da", "Avtale imorgen, hvem/hva/hvor", "w", "x", "y", "z"};
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
		panel.add(list, "cell 0 1 2 1,grow");
		
		list.addMouseListener(new MouseAdapter() {
		    public void mouseClicked(MouseEvent evt) {
		        if (evt.getClickCount() == 2 && !list.isSelectionEmpty()) {
		            //SHOW OR EDIT????? MUST KNOW! URGENT!
		            EventFrame ef = new ShowEventFrame();
		            ef.setEventTitle((String) list.getSelectedValue());
		            openFrameOnTop(ef);
		        }
		    }
		});
		
		final JPanel panel_6 = new JPanel();
		panel_6.setBackground(Settings2.COLOR_LIGHT_BLUE);
		panel.add(panel_6, "cell 0 2,alignx right,aligny center");
		panel_6.setLayout(new GridLayout(1, 0, 0, 0));
		
		final JButton btnShowEvent = new JButton("Show event");
		btnShowEvent.setContentAreaFilled(false);
		btnShowEvent.setBorderPainted(false);
		btnShowEvent.setForeground(Color.white);
		btnShowEvent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//SHOW OR EDIT????? MUST KNOW! URGENT!
	            EventFrame ef = new ShowEventFrame();
	            ef.setEventTitle((String) list.getSelectedValue());
	            openFrameOnTop(ef);	
			}
		});
		
		panel_6.add(btnShowEvent);
		btnShowEvent.setVisible(false);
		panel_6.setVisible(false);
		
		list.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if (list.isSelectionEmpty()) {
					btnShowEvent.setVisible(false);
					panel_6.setVisible(false);
				} else {
					btnShowEvent.setVisible(true);
					panel_6.setVisible(true);
				}
			}
		});
		
				
		JPanel panel_3 = new JPanel();
		panel_3.setBackground(BUTTONBLUE);
		panel.add(panel_3, "cell 1 2,alignx right,aligny bottom");
		panel_3.setLayout(new GridLayout(1, 0, 0, 0));
		
		JButton btnCreateEvent = new JButton("+ Create event");
		btnCreateEvent.setBorderPainted(false);
		btnCreateEvent.setForeground(Color.WHITE);
		btnCreateEvent.setContentAreaFilled(false);
		panel_3.add(btnCreateEvent);
		btnCreateEvent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openFrameOnTop(new CreateEventFrame());
			}
		});
		
		updateMonthLabels();
	}

	public void updateMonthLabels() {
		lblMonth.setText(Settings2.MONTHS[coolCalendar.getMonth()]);
		btnNextMonth.setText(Settings2.MONTHS[((coolCalendar.getMonth() + 1) % 12 + 12) % 12].substring(0, 3));
		btnPrevMonth.setText(Settings2.MONTHS[((coolCalendar.getMonth() - 1) % 12 + 12) % 12].substring(0, 3));
	}
	
	public void propertyChange(PropertyChangeEvent evt) 
	{		
		
		if(evt.getPropertyName().equals("selectedDate"))
		{
			if(!(evt.getNewValue() instanceof Date))
				return;
			
			selectedDate = (Date)evt.getNewValue();
			lblSelectedDate.setText(selectedDate.getDate() + " " + Settings2.MONTHS[selectedDate.getMonth()] + " " + (selectedDate.getYear() + 1900));
		}
	}
	
	public void setUser(String name){
		lblFullName.setText(name);
	}
}