package client.gui;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import net.miginfocom.swing.MigLayout;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import client.net.ServerConnector;

import server.db.SqlConnector;

import common.models.Event;
import common.models.Notification;
import common.models.User;
import common.tests.SampleEvents;

import java.awt.GridLayout;
import java.net.ConnectException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class LoginFrame extends BaseFrame {
	private JPasswordField passwordField;
	private JTextField textField_user;
	private JLabel lblFeedback;
	private static ArrayList<Event> sampleEvents = SampleEvents.getSampleEvents();
	
	
	public LoginFrame() {
		setSize(300, 200);
		setMinimumSize(new Dimension(300,200));
	    setCentered();
	      
		getContentPane().setLayout(new MigLayout("", "[grow][][143.00][grow]", "[grow][][][][][][grow]"));
		getContentPane().setBackground(Settings2.COLOR_VERY_DARK_GRAY);
		
		JLabel lblLogin = new JLabel("Login");
		lblLogin.setForeground(Color.WHITE);
		lblLogin.setFont(Settings2.FONT_TITLE1);
		getContentPane().add(lblLogin, "cell 1 1");
		
		JLabel lblUsername = new JLabel("Username");
		lblUsername.setForeground(Color.WHITE);
		getContentPane().add(lblUsername, "cell 1 2,alignx trailing");
		
		textField_user = new JTextField();
		getContentPane().add(textField_user, "cell 2 2,growx");
		textField_user.setColumns(10);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setForeground(Color.WHITE);
		getContentPane().add(lblPassword, "cell 1 3,alignx trailing");
		
		passwordField = new JPasswordField();
		getContentPane().add(passwordField, "cell 2 3,growx");
		
		JPanel panel = new JPanel();
		panel.setBackground(Settings2.COLOR_LIGHT_BLUE);
		getContentPane().add(panel, "cell 2 4,grow");
		panel.setLayout(new GridLayout(0, 1, 0, 0));
		
		JButton btnLogin = new JButton("Login");
		btnLogin.setContentAreaFilled(false);
		btnLogin.setBorderPainted(false);
		btnLogin.setForeground(Color.WHITE);
		panel.add(btnLogin);
		
		lblFeedback = new JLabel("");
		lblFeedback.setForeground(Color.WHITE);
		getContentPane().add(lblFeedback, "cell 2 5");
		
		ActionListener al = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				User user = getServerConnector().login(textField_user.getText(), passwordField.getPassword());
				if (user!=null) {      // && isCorrectPassword(passwordField.getPassword())
					System.out.println("ACCESS GRANTED for user: "+user);
					System.out.println(user.getName());
					
					close();
					try {
						((MainFrame) getParentFrame()).init(user);
						((MainFrame) getParentFrame()).setVisible(true);
					} catch (ConnectException e1) {
						LoginFrame.this.openFrameOnTop(LoginFrame.this);
						setServerConnector(new ServerConnector(settings, username));
						e1.printStackTrace();
					}
					
				} else {
					lblFeedback.setText("<html>The username or password you entered is incorrect.</html>");
				}
			}
		};
		
		btnLogin.addActionListener(al);
		textField_user.addActionListener(al);
		passwordField.addActionListener(al);
	}
	
	private boolean isCorrectPassword(char[] input){
		boolean isCorrect = true;
	    char[] correctPassword = { 't', 'e', 's', 't' };

	    if (input.length != correctPassword.length) {
	        isCorrect = false;
	    } else {
	        isCorrect = Arrays.equals (input, correctPassword);
	    }

	    //Zero out the password.
	    Arrays.fill(correctPassword,'0');

	    return isCorrect;
	}
	
	private User getUser(String username){
		for (User user : SampleEvents.getSampleUsers()){
//		for (User user : SampleUsers.getSampleUsers()){
			if (user.getUsername().equals(username)){
				return user;
			}
		}
		return null;
	}
}