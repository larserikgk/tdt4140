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
import java.awt.GridLayout;
import java.util.Arrays;

public class LoginFrame extends BaseFrame {
	private JPasswordField passwordField;
	private JTextField textField_user;
	private JLabel lblFeedback;
	
	public static void main(String[] args){
		  BaseFrame frame = new LoginFrame();
	      frame.setVisible(true);
	}
	
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
				String user = textField_user.getText();
				if (isCorrectPassword(passwordField.getPassword())) {
					System.out.println("ACCESS GRANTED for user: "+user);
					MainFrame mf = new MainFrame();
					mf.setUser(user);
					mf.setVisible(true);
					dispose();
				} else {
					//lblFeedback.setText("The username or password you entered is incorrect.");
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
}