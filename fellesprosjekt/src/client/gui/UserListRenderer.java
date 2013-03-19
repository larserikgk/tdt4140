package client.gui;

import java.awt.Component;
import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import common.models.User;

public class UserListRenderer extends DefaultListCellRenderer implements ListCellRenderer{

	@Override
	public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
		JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
		if (isSelected){
			label.setBackground(Settings2.COLOR_LIGHT_BLUE);
		}
		label.setForeground(Settings2.COLOR_VERY_DARK_GRAY);
		User u = ((User) value);
		String name = u.getName();
		label.setText(name);
//		Har forel�pig bare satt opp noen random if-setninger for � teste ikonene
		if (Integer.parseInt(u.getPassword())%3==0) label.setIcon(new ImageIcon("images/icon_yes_20px.png"));
		else if ((Integer.parseInt(u.getPassword())%7==0)) label.setIcon(new ImageIcon("images/icon_no_20px.png"));
		else if ((Integer.parseInt(u.getPassword())%5==0)) label.setIcon(new ImageIcon("images/icon_noReply_20px.png"));
		return label;
	}
}