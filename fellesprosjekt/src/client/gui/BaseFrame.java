package client.gui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.net.ConnectException;
import java.util.ArrayList;

import javax.swing.JFrame;

import client.net.ServerConnector;

import common.models.Event;
import common.models.User;

public abstract class BaseFrame extends JFrame {
	
	private JFrame parentFrame;
	private static User user;
	private static ServerConnector serverConnector;
	
	public static User getUser() {
		return user;
	}

	public void setUser(User user) throws ConnectException {
		ArrayList<Event> events = getServerConnector().getEvents(user, 0);
		for (Event e : events) {
			System.out.println("baseF event: "+e);
			user.addEvent(e);
		}
		this.user = user;
	}
	
	public BaseFrame(){
		addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent e) {
                close();
            }
        });
	}
	
	public void setFullscreen(){
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setSize(dim.width, dim.height);
	}
	
	public void setCentered(){
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
	}
	
	public void setMaximized(){
		this.setExtendedState(MAXIMIZED_BOTH);
	}
	
	public void openFrameOnTop(BaseFrame frame){
		setFocusableWindowState(false);
		setEnabled(false);
		frame.setParentFrame(this);
		frame.setAlwaysOnTop(true);
		frame.setVisible(true);
	}
	
	private void setParentFrame(JFrame parentFrame){
		this.parentFrame = parentFrame;
	}
	
	public JFrame getParentFrame(){
		return parentFrame;
	}
	
	public void close(){
		dispose();
		if (parentFrame != null){
//			parentFrame.setEnabled(true);
//			parentFrame.setFocusable(true);
//			if (parentFrame instanceof BaseFrame){
//				JFrame parentParentFrame = ((BaseFrame)parentFrame).getParentFrame();
//				if (parentParentFrame != null){
//					parentParentFrame.setVisible(true);
//					System.out.println("ppframe");
//				}
//			}
			JFrame parentParentFrame = (parentFrame instanceof BaseFrame) ? ((BaseFrame) parentFrame).getParentFrame() : null;
			if (parentParentFrame != null) parentParentFrame.setVisible(true);
			parentFrame.setEnabled(true);
			parentFrame.setFocusable(true);
			parentFrame.setFocusableWindowState(true);
			parentFrame.setVisible(true);
		}
	}

	public static ServerConnector getServerConnector() {
		return serverConnector;
	}

	public static void setServerConnector(ServerConnector serverConnector) {
		BaseFrame.serverConnector = serverConnector;
	}
}