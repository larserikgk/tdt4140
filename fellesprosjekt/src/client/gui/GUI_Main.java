package client.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.jgoodies.forms.factories.DefaultComponentFactory;

public class GUI_Main extends JPanel {
	
	//TODO: Flett disse fargene sammen med de som er i Settings.java
	static final Color main_bg_light_grey = new Color(Integer.parseInt("e2dfe0",16));
	static final Color main_bg_dark_grey = new Color(Integer.parseInt("595959",16));
	static final Color dark_grey = new Color(Integer.parseInt("181818",16));
	static final Color light_blue = new Color(Integer.parseInt("159ec0",16));
//	static final Color dark_grey = Settings.CELL_UNSELECTED_COLOR;
//	static final Color light_blue = Settings.CELL_SELECTED_COLOR;
	static final Color orange = new Color(Integer.parseInt("c53d2c",16));
	static final Color notification_red = new Color(Integer.parseInt("ff0000",16));
	
	//TODO: Flytt også dette over til Settings.java
	static final Font FONT_TITLE1 = new Font("Myriad Pro", Font.BOLD, 18);
	static final Font FONT_TEXT1 = new Font("Myriad Pro", Font.PLAIN, 18);
	static final Font FONT_TEXT2 = new Font("Myriad Pro", Font.PLAIN, 16);
	
    public static void main (String args[]) { 
        JFrame frame = new JFrame("FP Calendar"); 
        frame.getContentPane().add(new GUI_Main());
        frame.pack(); 
        frame.setVisible(true);
        }
    
	public GUI_Main() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{26, 670, 257, 10, 0};
		gridBagLayout.rowHeights = new int[]{41, 27, 0, 26, 293, 233, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, 1.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 0.0, 1.0, 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		JPanel panel = new JPanel();
		panel.setBackground(dark_grey);
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.gridwidth = 4;
		gbc_panel.insets = new Insets(0, 0, 5, 0);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 0;
		add(panel, gbc_panel);
		panel.setLayout(new GridLayout(0, 1, 0, 0));
		
		//Preview purposes only
		JLabel lblFileEdit = DefaultComponentFactory.getInstance().createLabel("     File                  Edit");
		lblFileEdit.setHorizontalAlignment(SwingConstants.LEFT);
		lblFileEdit.setForeground(Color.WHITE);
		panel.add(lblFileEdit);
		
		JLabel lblMarch = DefaultComponentFactory.getInstance().createTitle("March 2013");
		lblMarch.setFont(FONT_TITLE1);
		GridBagConstraints gbc_lblMarch = new GridBagConstraints();
		gbc_lblMarch.anchor = GridBagConstraints.WEST;
		gbc_lblMarch.insets = new Insets(0, 0, 5, 5);
		gbc_lblMarch.gridx = 1;
		gbc_lblMarch.gridy = 1;
		add(lblMarch, gbc_lblMarch);
		
		JLabel lblJensStoltenberg = DefaultComponentFactory.getInstance().createLabel("Jens Stoltenberg");
		lblJensStoltenberg.setFont(FONT_TEXT2);
		GridBagConstraints gbc_lblJensStoltenberg = new GridBagConstraints();
		gbc_lblJensStoltenberg.anchor = GridBagConstraints.WEST;
		gbc_lblJensStoltenberg.insets = new Insets(0, 0, 5, 5);
		gbc_lblJensStoltenberg.gridx = 2;
		gbc_lblJensStoltenberg.gridy = 1;
		add(lblJensStoltenberg, gbc_lblJensStoltenberg);
		
		CoolCalendar coolCalendar = new CoolCalendar();
		GridBagConstraints gbc_coolCalendar = new GridBagConstraints();
		gbc_coolCalendar.gridheight = 2;
		gbc_coolCalendar.insets = new Insets(0, 0, 5, 5);
		gbc_coolCalendar.fill = GridBagConstraints.BOTH;
		gbc_coolCalendar.gridx = 1;
		gbc_coolCalendar.gridy = 3;
		add(coolCalendar, gbc_coolCalendar);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(dark_grey);
		GridBagConstraints gbc_panel_2 = new GridBagConstraints();
		gbc_panel_2.fill = GridBagConstraints.BOTH;
		gbc_panel_2.gridheight = 3;
		gbc_panel_2.insets = new Insets(0, 0, 5, 5);
		gbc_panel_2.gridx = 2;
		gbc_panel_2.gridy = 3;
		add(panel_2, gbc_panel_2);
		GridBagLayout gbl_panel_2 = new GridBagLayout();
		gbl_panel_2.columnWidths = new int[]{287, 0};
		gbl_panel_2.rowHeights = new int[]{551, 0};
		gbl_panel_2.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gbl_panel_2.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		panel_2.setLayout(gbl_panel_2);
		
		JLabel lblUpcomingEvents = DefaultComponentFactory.getInstance().createLabel("Upcoming events");
		lblUpcomingEvents.setFont(FONT_TEXT1);
		lblUpcomingEvents.setVerticalAlignment(SwingConstants.TOP);
		lblUpcomingEvents.setForeground(Color.WHITE);
		GridBagConstraints gbc_lblUpcomingEvents = new GridBagConstraints();
		gbc_lblUpcomingEvents.fill = GridBagConstraints.BOTH;
		gbc_lblUpcomingEvents.gridx = 0;
		gbc_lblUpcomingEvents.gridy = 0;
		panel_2.add(lblUpcomingEvents, gbc_lblUpcomingEvents);
		
		JPanel panel_1 = new JPanel();
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.fill = GridBagConstraints.BOTH;
		gbc_panel_1.insets = new Insets(0, 0, 5, 5);
		gbc_panel_1.gridx = 1;
		gbc_panel_1.gridy = 5;
		add(panel_1, gbc_panel_1);
		panel_1.setBackground(dark_grey);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[]{36, 107, 0, 0, 0, 0, 0, 0, 186, 0, 25, 0};
		gbl_panel_1.rowHeights = new int[]{23, 0, 0, 0, 0, 0, 0, 0, 10, 0};
		gbl_panel_1.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panel_1.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel_1.setLayout(gbl_panel_1);
		panel_1.setAlignmentX(RIGHT_ALIGNMENT);
		
		JLabel selectedDate_txt = DefaultComponentFactory.getInstance().createLabel("12 March 2013");
		selectedDate_txt.setFont(FONT_TEXT1);
		selectedDate_txt.setForeground(Color.WHITE);
		GridBagConstraints gbc_selectedDate_txt = new GridBagConstraints();
		gbc_selectedDate_txt.anchor = GridBagConstraints.WEST;
		gbc_selectedDate_txt.insets = new Insets(0, 0, 5, 5);
		gbc_selectedDate_txt.gridx = 1;
		gbc_selectedDate_txt.gridy = 1;
		panel_1.add(selectedDate_txt, gbc_selectedDate_txt);
		
		JLabel selectedDateEvents = new JLabel("No events");
		selectedDateEvents.setFont(FONT_TEXT2);
		selectedDateEvents.setForeground(Color.WHITE);
		GridBagConstraints gbc_selectedDateEvents = new GridBagConstraints();
		gbc_selectedDateEvents.anchor = GridBagConstraints.WEST;
		gbc_selectedDateEvents.insets = new Insets(0, 0, 5, 5);
		gbc_selectedDateEvents.gridx = 1;
		gbc_selectedDateEvents.gridy = 2;
		panel_1.add(selectedDateEvents, gbc_selectedDateEvents);
										
		JButton btnCreateEvent = new JButton("+ Create event");
		btnCreateEvent.setFont(FONT_TEXT1);
		GridBagConstraints gbc_btnCreateEvent = new GridBagConstraints();
		gbc_btnCreateEvent.insets = new Insets(0, 0, 5, 5);
		gbc_btnCreateEvent.anchor = GridBagConstraints.SOUTHEAST;
		gbc_btnCreateEvent.gridx = 9;
		gbc_btnCreateEvent.gridy = 7;
		panel_1.add(btnCreateEvent, gbc_btnCreateEvent);
		
		btnCreateEvent.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFrame frame = new JFrame("Create event");
				EventPanel ep = new EventPanel();
				ep.setFrame(frame);
				frame.getContentPane().add(ep);
				frame.setUndecorated(true);
				frame.setLocation(300, 100);
				frame.pack();
				frame.setVisible(true);
			}
		});
		
	}
}
