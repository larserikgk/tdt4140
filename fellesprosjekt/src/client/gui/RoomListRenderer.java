package client.gui;

import java.awt.Color;
import java.awt.Component;
import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import common.models.Room;
import common.models.User;

public class RoomListRenderer extends DefaultListCellRenderer implements ListCellRenderer<Object>{

	@Override
	public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
		JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
		if (isSelected){
			label.setBackground(Settings2.COLOR_LIGHT_BLUE);
		}
		label.setForeground(Settings2.COLOR_VERY_DARK_GRAY);
		Room r = ((Room) value);
		String name = r.getName();
		int capacity = r.getCapacity();
		//label.setText(name+" (Capacity: "+capacity+")");
		//label.setText("<html>"+name+"<font size=2> Capacity: "+capacity+"</font></html>");

		StringBuilder buff = new StringBuilder();
        buff.append("<html><table>");
        buff.append(String.format("<tr><td>%s</td><td align='right'>%s</td></tr>", name, "<font size=2>Capacity: "+capacity+"</font>"));
        buff.append("</table></html>");
        label.setText(buff.toString());
		return label;
	}
}