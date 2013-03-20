package client.gui;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JToggleButton;

@SuppressWarnings("serial")
public class ResponseToggleButton extends JToggleButton {
	
	public ResponseToggleButton(String s) {
		super(s);
	}
	
	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		if(isSelected())
			g.setColor(Settings2.COLOR_LIGHT_BLUE);
		else
			g.setColor(Settings2.COLOR_DARK_GRAY);
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setColor(Color.WHITE);
		g.drawString(getText(), 25, 15);
	}
}
