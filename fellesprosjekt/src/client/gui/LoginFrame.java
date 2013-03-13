package client.gui;

import java.awt.Color;
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

public class LoginFrame extends JFrame {
	private JPasswordField passwordField;
	private JTextField textField_user;
	
	public static void main(String[] args){
		  JFrame frame = new LoginFrame(); 
	      frame.setSize(250, 150);
	      //frame.setLocation(300, 200);
	      Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
	      frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
	      frame.setVisible(true);
	 }
	
	public LoginFrame() {
		getContentPane().setLayout(new MigLayout("", "[][143.00]", "[][][][]"));
		getContentPane().setBackground(Settings2.COLOR_VERY_DARK_GRAY);
		
		JLabel lblLogin = new JLabel("Login");
		lblLogin.setForeground(Color.WHITE);
		lblLogin.setFont(Settings2.FONT_TITLE1);
		getContentPane().add(lblLogin, "cell 0 0");
		
		JLabel lblUsername = new JLabel("Username");
		lblUsername.setForeground(Color.WHITE);
//		lblUsername.setFont(Settings2.FONT_TEXT2);
		getContentPane().add(lblUsername, "cell 0 1,alignx trailing");
		
		textField_user = new JTextField();
		getContentPane().add(textField_user, "cell 1 1,growx");
		textField_user.setColumns(10);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setForeground(Color.WHITE);
		//lblPassword.setFont(Settings2.FONT_TEXT2);
		getContentPane().add(lblPassword, "cell 0 2,alignx trailing");
		
		passwordField = new JPasswordField();
		getContentPane().add(passwordField, "cell 1 2,growx");
		
		JPanel panel = new JPanel();
		panel.setBackground(Settings2.COLOR_LIGHT_BLUE);
		getContentPane().add(panel, "cell 1 3,grow");
		panel.setLayout(new GridLayout(0, 1, 0, 0));
		
		JButton btnLogin = new JButton("Login");
		btnLogin.setContentAreaFilled(false);
		btnLogin.setBorderPainted(false);
		btnLogin.setForeground(Color.WHITE);
		panel.add(btnLogin);
		
		
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String user = textField_user.getText();
				if (passwordField.getText().equals("test")) {
					System.out.println("ACCESS GRANTED for user: "+user);
					MainFrame mf = new MainFrame();
					mf.setUser(user);
					openMainFrame(mf);
					dispose();
				}
			}
		});
	}
	
	public void openMainFrame(JFrame frame){
//		JFrame frame = new MainFrame(); 
		frame.setSize(250, 150);
		//frame.setLocation(300, 200);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setSize(dim.width, dim.height);
		//frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
		frame.setVisible(true);
	}
}