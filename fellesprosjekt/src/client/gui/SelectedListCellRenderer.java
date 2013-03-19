package client.gui;

import java.awt.Color;
import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;

import common.models.Event;

public class SelectedListCellRenderer extends DefaultListCellRenderer {
	public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
		Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
		if (isSelected) {
			c.setBackground(Settings2.COLOR_LIGHT_BLUE);
		}
		c.setForeground(Color.white);
		c.setFont(Settings2.FONT_TEXT2);
		Event evt = (Event) value;
		String start = getTimeFormatValue(evt.getStart().getHours())+":"+getTimeFormatValue(evt.getStart().getMinutes());
		String end = getTimeFormatValue(evt.getEnd().getHours())+":"+getTimeFormatValue(evt.getEnd().getMinutes());
		String name = evt.getName();
		JLabel label = (JLabel) c;
		label.setText(start+" - "+end+": "+name);
		return label;
	}
	
	public static String getTimeFormatValue(int n){
		return (n<10) ? "0"+n : n+"";
	}
}
