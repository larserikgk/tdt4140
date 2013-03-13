package client.gui;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

public abstract class BaseFrame extends JFrame {
	
	private JFrame parentFrame;
	
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
		//frame.pack();
		frame.setVisible(true);
	}
	
	private void setParentFrame(JFrame parentFrame){
		this.parentFrame = parentFrame;
	}
	
	public void close(){
		if (parentFrame != null){
			parentFrame.setEnabled(true);
			parentFrame.setFocusable(true);
		}
		dispose();
	}
}