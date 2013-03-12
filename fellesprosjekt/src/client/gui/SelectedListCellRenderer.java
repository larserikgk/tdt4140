package client.gui;

import java.awt.Color;
import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;

public class SelectedListCellRenderer extends DefaultListCellRenderer {
	public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
		Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
		if (isSelected) {
			c.setBackground(Settings2.COLOR_LIGHT_BLUE);
		}
		c.setForeground(Color.white);
		c.setFont(Settings2.FONT_TEXT2);
		return c;
	}
}
