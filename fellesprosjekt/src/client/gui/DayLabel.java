package client.gui;

import java.awt.Graphics;
import javax.swing.BorderFactory;
import javax.swing.JToggleButton;

public class DayLabel extends JToggleButton
{
	private static final long serialVersionUID = 1L;

	public DayLabel(String label)
	{
		super(label);
		setBorder(BorderFactory.createLineBorder(Settings.CELL_BORDER_COLOR));
		this.setEnabled(false);
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
		g.setColor(Settings.FONT_ENABLED_COLOR);
		g.setFont(Settings.FONT);
		g.drawString(	
						getText(), 
						(getWidth()-g.getFontMetrics().stringWidth(getText()))/2,
						getHeight()-10
		);
	}
}
