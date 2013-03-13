package client.gui;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

public abstract class BaseFrame extends JFrame {
	
	JFrame parentFrame;
	
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
	
	public void openFrameOnTop(JFrame frame){
		frame.setAlwaysOnTop(true);
		((BaseFrame) frame).setParentFrame(frame);
		setFocusableWindowState(false);
		setEnabled(false);
		frame.pack();
		frame.setVisible(true);
	}
	
	private void setParentFrame(JFrame parentFrame){
		this.parentFrame = parentFrame;
	}

}