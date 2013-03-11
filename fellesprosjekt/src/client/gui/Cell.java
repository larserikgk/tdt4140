package client.gui;

import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JToggleButton;

import common.models.Event;

public class Cell extends JToggleButton
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<Event> events;
		
	public Cell(ArrayList<Event> events, String label, boolean enabled)
	{
		setText(label);
		setEvents(events);
		setEnabled(enabled);
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

		if(events.size()==1)
			g.drawString(events.get(0).getName(), 10, getHeight()/2);
		else if(events.size()>1)
			g.drawString(events.get(0).getName()+" + " + (events.size()-1) + "other events.", 10, getHeight()/2);
	}

	public ArrayList<Event> getEvents(){
		return events;
	}

	public void setEvents(ArrayList<Event> events) 
	{
		this.events = events;
		repaint();
	}
}
