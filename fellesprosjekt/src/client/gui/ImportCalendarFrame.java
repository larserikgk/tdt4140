package client.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.color.ICC_ProfileRGB;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

import net.miginfocom.swing.MigLayout;

import common.models.Event;
import common.models.User;

public class ImportCalendarFrame extends BaseFrame {
	
	private UserListFilter allUsersList;
	
	public static void main(String[] args) {
		ImportCalendarFrame ic = new ImportCalendarFrame();
		ic.setVisible(true);
	}
	
	public ImportCalendarFrame() {
		super();
		setResizable(false);
		setSize(464, 473);
		setCentered();
		getContentPane().setBackground(Settings2.COLOR_VERY_DARK_GRAY);
		Border border = BorderFactory.createLineBorder(Settings2.COLOR_ORANGE);
		//getContentPane().setBorder(border);
		getContentPane().setLayout(new MigLayout("", "[2%,grow][4.69%][20%,grow][20%,grow][5%,grow][10%,grow][2%,grow]", "[1%,grow][5%,grow][60%,grow][5.52%,grow][5%,grow]"));
	
		setupParticipants();
		
		JLabel lblAddParticipants = new JLabel("Import calendar");
		lblAddParticipants.setFont(Settings2.FONT_TITLE1);
		lblAddParticipants.setFont(Settings2.FONT_TEXT1);
		lblAddParticipants.setForeground(Color.WHITE);
		getContentPane().add(lblAddParticipants, "cell 1 0 2 1,alignx left,aligny center");
		
		JLabel lblUsers = new JLabel("Users");
		lblUsers.setFont(Settings2.FONT_TEXT2);
		lblUsers.setForeground(Color.WHITE);
		getContentPane().add(lblUsers, "cell 2 1,alignx left,aligny center");
		
		
		getContentPane().add(allUsersList, "cell 2 2 3 1,grow");
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Settings2.COLOR_LIGHT_BLUE);
		getContentPane().add(panel_1, "flowx,cell 4 4,alignx right,aligny center");
		panel_1.setLayout(new GridLayout(1, 0, 0, 0));
		
		JButton btnCancel = new JButton("Cancel");
		panel_1.add(btnCancel);
		btnCancel.setForeground(Color.WHITE);
		btnCancel.setBorderPainted(false);
		btnCancel.setContentAreaFilled(false);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(24, 161, 195));
		getContentPane().add(panel, "cell 5 4,alignx left,aligny center");
		panel.setLayout(new GridLayout(1, 0, 0, 0));
		
		JButton btnApply = new JButton("Import");
		panel.add(btnApply);
		btnApply.setForeground(Color.WHITE);
		btnApply.setContentAreaFilled(false);
		btnApply.setBorderPainted(false);
		
		btnApply.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				/*
				
				 */
				close();
			}
		});
		
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				close();
			}
		});
	}

	
	public void setupParticipants(){
		ArrayList<User> allUsers = getServerConnector().getAllUsers();
		allUsers.add(new User("Tom", "Tom"));
		allUsersList = new UserListFilter(allUsers);
	}
}
