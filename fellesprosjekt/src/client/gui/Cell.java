package client.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.BorderFactory;
import javax.swing.JToggleButton;

public class Cell extends JToggleButton
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public Cell(String label)
	{
		super(label);
		setBorder(BorderFactory.createLineBorder(Settings.CELL_BORDER_COLOR));
	}
	
	public Cell(String label, boolean enabled, boolean hasEvents)
	{
		super(label);
		setEnabled(enabled);
		if (hasEvents) {
			setBorder(BorderFactory.createLineBorder(Color.red));
		} else {
			setBorder(BorderFactory.createLineBorder(Settings.CELL_BORDER_COLOR));
		}
	}
	
	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		if(isSelected())
			g.setColor(Settings.CELL_SELECTED_COLOR);
		else
			g.setColor(Settings.CELL_UNSELECTED_COLOR);
		
		g.fillRect(0, 0, getWidth(), getHeight());
		if(isEnabled())
			g.setColor(Settings.FONT_ENABLED_COLOR);
		else
			g.setColor(Settings.FONT_DISABLED_COLOR);
		g.drawString(getText(), 
					(getWidth() - g.getFontMetrics().stringWidth(getText())-10),
					(g.getFontMetrics().getAscent())+10);
	}
}
