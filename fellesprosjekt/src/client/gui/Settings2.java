package client.gui;

import java.awt.Color;
import java.awt.Font;

import javax.swing.UIManager;

import common.models.User;

public class Settings2 
{		
	static final Color COLOR_LIGHT_GRAY = new Color(140, 140, 140);
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
		UIManager.put("ScrollBar.allowsAbsolutePositioning", true);
		UIManager.put("ScrollBar.background", COLOR_LIGHT_GRAY);
		UIManager.put("ScrollBar.track", COLOR_LIGHT_GRAY);
		UIManager.put("ScrollBar.foreground", COLOR_DARK_GRAY);
		UIManager.put("ScrollBar.thumb", COLOR_DARK_GRAY);
		UIManager.put("ScrollBar.thumbHighlight", COLOR_DARK_GRAY);
		UIManager.put("ScrollBar.shadow", COLOR_DARK_GRAY);
		
		UIManager.put("ScrollBarUI", "javax.swing.plaf.basic.BasicScrollBarUI");
//				ScrollBar.background                         =javax.swing.plaf.ColorUIResource[r=204,g=204,b=204]
//				ScrollBar.darkShadow                         =javax.swing.plaf.ColorUIResource[r=102,g=102,b=102]
//				ScrollBar.focusInputMap                      =javax.swing.plaf.InputMapUIResource@6025e7
//				ScrollBar.foreground                         =javax.swing.plaf.ColorUIResource[r=204,g=204,b=204]
//				ScrollBar.highlight                          =javax.swing.plaf.ColorUIResource[r=255,g=255,b=255]
//				ScrollBar.maximumThumbSize                   =javax.swing.plaf.DimensionUIResource[width=4096,height=4096]
//				ScrollBar.minimumThumbSize                   =javax.swing.plaf.DimensionUIResource[width=8,height=8]
//				ScrollBar.shadow                             =javax.swing.plaf.ColorUIResource[r=153,g=153,b=153]
//				ScrollBar.thumb                              =javax.swing.plaf.ColorUIResource[r=153,g=153,b=204]
//				ScrollBar.thumbDarkShadow                    =javax.swing.plaf.ColorUIResource[r=102,g=102,b=102]
//				ScrollBar.thumbHighlight                     =javax.swing.plaf.ColorUIResource[r=204,g=204,b=255]
//				ScrollBar.thumbLightShadow                   =javax.swing.plaf.ColorUIResource[r=153,g=153,b=153]
//				ScrollBar.thumbShadow                        =javax.swing.plaf.ColorUIResource[r=102,g=102,b=153]
//				ScrollBar.track                              =javax.swing.plaf.ColorUIResource[r=204,g=204,b=204]
//				ScrollBar.trackHighlight                     =javax.swing.plaf.ColorUIResource[r=102,g=102,b=102]
//				ScrollBar.width                              =17
//				ScrollBarUI                                  =javax.swing.plaf.metal.MetalScrollBarUI
	}
		
}
