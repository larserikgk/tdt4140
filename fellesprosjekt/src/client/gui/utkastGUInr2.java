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
import javax.swing.UIManager;
import javax.swing.JPanel;
import javax.swing.JList;
import javax.swing.AbstractListModel;
import java.awt.Font;
import javax.swing.JMenuItem;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Component;



public class utkastGUInr2 extends JFrame{
	private JMenuBar menuBar;
	
	//Colors
	public static Color PANELGRAY	= new Color(33,33,33);
	public static Color BUTTONBLUE	= new Color(24,161,195);
	
	
	public utkastGUInr2() {
		getContentPane().setBackground(Color.LIGHT_GRAY);
		
		menuBar = new JMenuBar();
		menuBar.setToolTipText("");
		menuBar.setForeground(Color.WHITE);
		menuBar.setBackground(Color.BLACK);
		setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntmLogOut = new JMenuItem("Log out");
		mnFile.add(mntmLogOut);
		
		JMenuItem mntmExit = new JMenuItem("Exit");
		mnFile.add(mntmExit);
		
		JMenu mnEdit = new JMenu("Edit");
		menuBar.add(mnEdit);
		
		JMenuItem mntmMenuItem = new JMenuItem("Menu item 1");
		mnEdit.add(mntmMenuItem);
		getContentPane().setLayout(new MigLayout("", "[50%,grow][10%,grow][10%,grow]", "[6%,grow][49%,grow][44%,grow]"));
		
		JLabel lblMarch = new JLabel("March 2013");
		lblMarch.setFont(new Font("Tahoma", Font.PLAIN, 14));
		getContentPane().add(lblMarch, "cell 0 0");
		
		JLabel lblJensStoltenberg = new JLabel("Jens Stoltenberg");
		getContentPane().add(lblJensStoltenberg, "cell 1 0");
		
		JPanel panel_2 = new JPanel();
		getContentPane().add(panel_2, "cell 2 0,grow");
		panel_2.setLayout(new GridLayout(0, 1, 0, 0));
		
		JMenuBar menuBar_1 = new JMenuBar();
		panel_2.add(menuBar_1);
		
		JMenu mnNotifications = new JMenu("Notifications");
		menuBar_1.add(mnNotifications);
		
		JMenuItem mntmInvitasjonTilmte = new JMenuItem("Invitasjon til \"M\u00F8te 2\"");
		mnNotifications.add(mntmInvitasjonTilmte);
		
		JPanel panel_4 = new JPanel();
		getContentPane().add(panel_4, "cell 0 1,grow");
		panel_4.setLayout(new GridLayout(0, 1, 0, 0));
		CoolCalendar c = new CoolCalendar();
		panel_4.add(c);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(PANELGRAY);
		getContentPane().add(panel_1, "cell 1 1 2 2,grow");
		
		JLabel lblUpcomingEvents = new JLabel("Upcoming events");
		lblUpcomingEvents.setForeground(Color.WHITE);
		lblUpcomingEvents.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel_1.add(lblUpcomingEvents);
		
		JPanel panel = new JPanel();
		panel.setBackground(PANELGRAY);
		getContentPane().add(panel, "cell 0 2,grow");
		panel.setLayout(new MigLayout("", "[grow]", "[90%,grow][10%,grow]"));
		
		JList list = new JList();
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
		panel.add(list, "cell 0 0,grow");
		
		JPanel panel_3 = new JPanel();
		panel_3.setBackground(BUTTONBLUE);
		panel.add(panel_3, "cell 0 1,alignx right,aligny bottom");
		panel_3.setLayout(new GridLayout(1, 0, 0, 0));
		
		JButton btnNewButton = new JButton("+ Create event");
		btnNewButton.setForeground(Color.WHITE);
		btnNewButton.setContentAreaFilled(false);
		panel_3.add(btnNewButton);
	}
}