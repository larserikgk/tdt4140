package client.gui;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import net.miginfocom.swing.MigLayout;
import javax.swing.JButton;
import javax.swing.JMenu;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.ListSelectionModel;
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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Component;
import java.awt.BorderLayout;



public class MainFrame extends JFrame{
	private static JFrame frame;
	private JMenuBar menuBar;
	
	//Colors
	public static Color PANELGRAY	= new Color(33,33,33);
	public static Color BUTTONBLUE	= new Color(24,161,195);
	
	
	 public static void main (String args[]) { 
	        frame = new MainFrame(); 
	        frame.setSize(700, 500);
	        frame.setLocation(300, 200);
	        frame.setVisible(true);
	 }
	 
	public MainFrame() {
		getContentPane().setBackground(Color.LIGHT_GRAY);
		
		menuBar = new JMenuBar();
		menuBar.setToolTipText("");
		menuBar.setForeground(Color.WHITE);
		menuBar.setBackground(Color.BLACK);
		setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		mnFile.setForeground(Color.WHITE);
		menuBar.add(mnFile);
		
		JMenuItem mntmLogOut = new JMenuItem("Log out");
		mnFile.add(mntmLogOut);
		
		JMenuItem mntmExit = new JMenuItem("Exit");
		mnFile.add(mntmExit);
		
		JMenu mnEdit = new JMenu("Edit");
		mnEdit.setForeground(Color.WHITE);
		menuBar.add(mnEdit);
		
		JMenuItem mntmMenuItem = new JMenuItem("Menu item 1");
		mnEdit.add(mntmMenuItem);
		
		JMenu mnNewMenu = new JMenu("New menu");
		menuBar.add(mnNewMenu);
		getContentPane().setLayout(new MigLayout("", "[70%,grow][30%,grow]", "[6%,grow][49%,grow][44%,grow]"));
		
		JLabel lblMarch = new JLabel("March 2013");
		lblMarch.setFont(Settings2.FONT_TITLE1);
		getContentPane().add(lblMarch, "cell 0 0");
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(Color.LIGHT_GRAY);
		getContentPane().add(panel_2, "cell 1 0,grow");
		panel_2.setLayout(new MigLayout("", "[60%,grow][40%,grow]", "[6%,grow]"));
		
		JLabel lblFullName = new JLabel("Full Name");
		panel_2.add(lblFullName, "cell 0 0");
		
		JPanel panel_5 = new JPanel();
		panel_5.setBackground(Color.red);
		panel_2.add(panel_5, "cell 1 0,grow");
		panel_5.setLayout(new GridLayout(0, 1, 0, 0));
		
		JButton btnNotifications = new JButton("Notifications");
		btnNotifications.setContentAreaFilled(false);
		btnNotifications.setBorderPainted(false);
		btnNotifications.setForeground(Color.white);
		panel_5.add(btnNotifications);

		
		JPanel panel_4 = new JPanel();
		getContentPane().add(panel_4, "cell 0 1,grow");
		panel_4.setLayout(new GridLayout(0, 1, 0, 0));
		
		CoolCalendar coolCalendar = new CoolCalendar();
		panel_4.add(coolCalendar);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(PANELGRAY);
		getContentPane().add(panel_1, "cell 1 1 1 2,grow");
		panel_1.setLayout(new MigLayout("", "[291px]", "[657px]"));
		
		JLabel label = new JLabel("Upcoming events");
		label.setForeground(Color.WHITE);
		label.setFont(Settings2.FONT_TEXT1);
		panel_1.add(label, "cell 0 0,growx,aligny top");
		
		JPanel panel = new JPanel();
		panel.setBackground(PANELGRAY);
		getContentPane().add(panel, "cell 0 2,grow");
		panel.setLayout(new MigLayout("", "[grow][]", "[][80%,grow][10%,grow]"));
		
		JLabel lblMarch_1 = new JLabel("15 March 2013");
		lblMarch_1.setFont(Settings2.FONT_TEXT1);
		lblMarch_1.setForeground(Color.WHITE);
		panel.add(lblMarch_1, "cell 0 0");
		
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
		        JList list = (JList)evt.getSource();
		        System.out.println("Click count: "+evt.getClickCount());
		        if (evt.getClickCount() == 2) {
		            int index = list.locationToIndex(evt.getPoint());
		            System.out.println("Double click: "+index+", "+list.getSelectedIndex());
		            //Should open show/edit event
		            EventFrame ef = new EventFrame();
		            ef.setEventTitle((String) list.getSelectedValue());
		            openEventFrame(ef);
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
			@Override
			public void actionPerformed(ActionEvent e) {
				openEventFrame(new EventFrame());
			}
		});
	}
	
	public void openEventFrame(JFrame eventFrame){
		//frame.setUndecorated(true);
		eventFrame.setLocation(300, 100);
		eventFrame.setAlwaysOnTop(true);
		((EventFrame) eventFrame).setParentFrame(frame);
		setFocusableWindowState(false);
		setEnabled(false);
		eventFrame.pack();
		eventFrame.setVisible(true);
	}
}