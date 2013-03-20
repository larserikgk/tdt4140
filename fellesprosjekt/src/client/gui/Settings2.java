package client.gui;

import java.awt.Color;
import java.awt.Font;

import javax.swing.UIManager;

import common.models.User;

public class Settings2 
{		
	static final Color COLOR_VERY_DARK_GRAY = new Color(33,33,33);
	static final Color COLOR_DARK_GRAY = Color.DARK_GRAY;
	static final Color COLOR_LIGHT_BLUE = new Color(24,161,195);
	static final Color COLOR_ORANGE = new Color(Integer.parseInt("c53d2c",16));
	static final Color COLOR_RED = new Color(235, 40, 40);
	
	static final Font FONT_TITLE1 = new Font("Myriad Pro", Font.BOLD, 18);
	static final Font FONT_TEXT1 = new Font("Myriad Pro", Font.PLAIN, 18);
	static final Font FONT_TEXT2 = new Font("Myriad Pro", Font.PLAIN, 16);
	
	static final String[] MONTHS = {"January", "February", "March", "April", "May", "June", "July", 
		"August", "September", "October", "November", "December"};
	
	static final String[] DAYS = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
	
	static void setUI(){
		//Testing
		UIManager.put("MenuItem.selectionBackground", COLOR_DARK_GRAY);
		UIManager.put("MenuItem.background", COLOR_VERY_DARK_GRAY);
		UIManager.put("MenuItem.foreground", Color.WHITE);
		UIManager.put("MenuItem.selectionForeground", Color.WHITE);
		UIManager.put("Menu.selectionBackground", COLOR_DARK_GRAY);
		UIManager.put("Menu.selectionForeground", Color.WHITE);
		//UIManager.put("MenuBar.selectionBackground", COLOR_DARK_GRAY);
		//UIManager.put("MenuBar.selectionForeground", Color.WHITE);
	}
		
}
