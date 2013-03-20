package client.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

import client.net.ServerConnector;

import net.miginfocom.swing.MigLayout;

import common.models.Event;
import common.models.User;
import common.tests.SampleEvents;
import common.tests.SampleUsers;

public class ParticipantsFrame extends BaseFrame {
	private UserListFilter allUsersList, invitedUsersList;
	private Event event;
	private ServerConnector serverConnector;
	
	/*public static void main(String[] args){
		JFrame frame = new ParticipantsFrame(new Event());
		frame.setVisible(true);
	}*/
	
	public ParticipantsFrame(Event event) {
		super();
		setResizable(false);
		setSize(709, 517);
		setCentered();
		getContentPane().setBackground(Settings2.COLOR_VERY_DARK_GRAY);
		Border border = BorderFactory.createLineBorder(Settings2.COLOR_ORANGE);
		//getContentPane().setBorder(border);
		getContentPane().setLayout(new MigLayout("", "[2%,grow][4.69%][20%,grow][20%,grow][5%,grow][30%,grow][10%,grow][2%,grow]", "[1%,grow][5%,grow][60%,grow][5.52%,grow][5%,grow]"));
	
		this.event = event;
		setupParticipants(event);
		
		JLabel lblAddParticipants = new JLabel("Edit participants");
		lblAddParticipants.setFont(Settings2.FONT_TITLE1);
		lblAddParticipants.setFont(Settings2.FONT_TEXT1);
		lblAddParticipants.setForeground(Color.WHITE);
		getContentPane().add(lblAddParticipants, "cell 1 0 2 1,alignx left,aligny center");
		
		JLabel lblUsers = new JLabel("Users");
		lblUsers.setFont(Settings2.FONT_TEXT2);
		lblUsers.setForeground(Color.WHITE);
		getContentPane().add(lblUsers, "cell 2 1,alignx left,aligny center");
		
		JLabel lblParticipants = new JLabel("Participants");
		lblParticipants.setForeground(Color.WHITE);
		lblParticipants.setFont(Settings2.FONT_TEXT2);
		getContentPane().add(lblParticipants, "cell 5 1");
		
//		ArrayList<User> allUsers = SampleUsers.getSampleUsers();
//		allUsersList = new UserListFilter(allUsers);
		getContentPane().add(allUsersList, "cell 2 2 2 1,grow");
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(Settings2.COLOR_VERY_DARK_GRAY);
		getContentPane().add(panel_2, "cell 4 2,alignx center,growy");
		panel_2.setLayout(new MigLayout("", "[grow]", "[][grow][grow][grow][]"));
		
		JPanel panel_4 = new JPanel();
		panel_4.setBackground(Settings2.COLOR_DARK_GRAY);
		panel_2.add(panel_4, "cell 0 1,growx,aligny center");
		panel_4.setLayout(new GridLayout(0, 1, 0, 0));
		
		JButton btnAdd = new JButton(">");
		btnAdd.setForeground(Color.WHITE);
		btnAdd.setContentAreaFilled(false);
		btnAdd.setBorderPainted(false);
		btnAdd.setFont(new Font("Tahoma", Font.PLAIN, 20));
		panel_4.add(btnAdd);
		
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ArrayList<User> users = allUsersList.removeUsers();
				invitedUsersList.addUsers(users);
			}
		});
		
		JPanel panel_3 = new JPanel();
		panel_3.setBackground(Settings2.COLOR_DARK_GRAY);
		panel_2.add(panel_3, "cell 0 3,alignx center,aligny center");
		panel_3.setLayout(new GridLayout(0, 1, 0, 0));
		
		JButton btnRemove = new JButton("<");
		btnRemove.setForeground(Color.WHITE);
		btnRemove.setContentAreaFilled(false);
		btnRemove.setBorderPainted(false);
		btnRemove.setFont(new Font("Tahoma", Font.PLAIN, 20));
		panel_3.add(btnRemove);
		
		btnRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ArrayList<User> users = invitedUsersList.removeUsers();
				allUsersList.addUsers(users);
			}
		});
		
//		invitedUsersList = new UserListFilter(null);
		getContentPane().add(invitedUsersList, "cell 5 2 2 1,grow");
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Settings2.COLOR_LIGHT_BLUE);
		getContentPane().add(panel_1, "flowx,cell 5 4,alignx right,aligny center");
		panel_1.setLayout(new GridLayout(1, 0, 0, 0));
		
		JButton btnCancel = new JButton("Cancel");
		panel_1.add(btnCancel);
		btnCancel.setForeground(Color.WHITE);
		btnCancel.setBorderPainted(false);
		btnCancel.setContentAreaFilled(false);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(24, 161, 195));
		getContentPane().add(panel, "cell 6 4,alignx left,aligny center");
		panel.setLayout(new GridLayout(1, 0, 0, 0));
		
		JButton btnApply = new JButton("Apply");
		panel.add(btnApply);
		btnApply.setForeground(Color.WHITE);
		btnApply.setContentAreaFilled(false);
		btnApply.setBorderPainted(false);
		
		btnApply.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Save new participants list to event and refresh EventFrame (parent frame)
				System.out.println("INVITED: "+invitedUsersList.getArrayList());
				((EventFrame) getParentFrame()).getEvent().setParticipants(invitedUsersList.getArrayList());
				((EventFrame) getParentFrame()).setupParticipants();
				close();
			}
		});
		
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				close();
			}
		});
	}
	
	public void setupParticipants(Event event){
		ArrayList<User> participants = serverConnector.getParticipants(event);
		invitedUsersList = new UserListFilter(participants);
		ArrayList<User> allUsers = serverConnector.getAllUsers();
		if (participants != null){
			allUsers.removeAll(participants);
		}
		allUsersList = new UserListFilter(allUsers);
	}
}
