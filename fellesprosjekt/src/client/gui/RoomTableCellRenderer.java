package client.gui;

import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class RoomTableCellRenderer extends DefaultTableCellRenderer {
	
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
    {
		hasFocus = false;
        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        if(isSelected){
        	c.setBackground(Settings2.COLOR_LIGHT_BLUE);
        } else 
        	c.setBackground(null);
        c.setForeground(Settings2.COLOR_VERY_DARK_GRAY);
        return c;
    }

}
