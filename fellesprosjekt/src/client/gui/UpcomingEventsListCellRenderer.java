package client.gui;

import java.awt.Color;
import java.awt.Component;
import java.util.Date;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;

import common.models.Event;

public class UpcomingEventsListCellRenderer extends DefaultListCellRenderer {
	public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
		Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
		if (isSelected) {
			c.setBackground(Settings2.COLOR_LIGHT_BLUE);
		}
		c.setForeground(Color.white);
		c.setFont(Settings2.FONT_TEXT2);
		Event evt = (Event) value;
		String startDate = getDateFormatString(evt.getStart());
		String startTime = getTimeFormatString(evt.getStart().getHours())+":"+getTimeFormatString(evt.getStart().getMinutes());
		String name = evt.getName();
		JLabel label = (JLabel) c;
		label.setText(startDate+" "+startTime+": "+name);
		return label;
	}
	
	public static String getTimeFormatString(int n){
		return (n<10) ? "0"+n : n+"";
	}
	
	public static String getDateFormatString(Date date){
		return Settings2.DAYS[date.getDay()]+" "+date.getDate()+" "+Settings2.MONTHS[date.getMonth()];
	}
}
