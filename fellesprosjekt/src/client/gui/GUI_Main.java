package client.gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.GridLayout;
import javax.swing.JProgressBar;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.JLabel;
import javax.swing.BoxLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Insets;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.CardLayout;
import com.jgoodies.forms.factories.DefaultComponentFactory;
import javax.swing.JScrollBar;
import net.miginfocom.swing.MigLayout;
import java.awt.SystemColor;
import javax.swing.JButton;
import javax.swing.JTextPane;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.SwingConstants;

public class GUI_Main extends JPanel {
	
    public static void main (String args[]) { 
        JFrame frame = new JFrame("FP Calendar"); 
        frame.getContentPane().add(new GUI_Main()); 
        frame.pack(); 
        frame.setVisible(true);   
        }
    
    
	public GUI_Main() {
		setBackground(new Color(Integer.parseInt("ffffff",16)));
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{519, 205, 10, 0};
		gridBagLayout.rowHeights = new int[]{41, 27, 0, 26, 293, 233, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 0.0, 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JPanel panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		panel.setBackground(new Color(Integer.parseInt("181818",16)));
		gbc_panel.gridwidth = 3;
		gbc_panel.insets = new Insets(0, 0, 5, 0);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 0;
		add(panel, gbc_panel);
		panel.setLayout(new GridLayout(0, 1, 0, 0));
		
		JLabel lblFileEdit = DefaultComponentFactory.getInstance().createLabel("File                  Edit");
		lblFileEdit.setHorizontalAlignment(SwingConstants.LEFT);
		lblFileEdit.setForeground(Color.WHITE);
		panel.add(lblFileEdit);
		
		JLabel lblMarch = DefaultComponentFactory.getInstance().createTitle("March 2013");
		GridBagConstraints gbc_lblMarch = new GridBagConstraints();
		gbc_lblMarch.anchor = GridBagConstraints.WEST;
		gbc_lblMarch.insets = new Insets(0, 0, 5, 5);
		gbc_lblMarch.gridx = 0;
		gbc_lblMarch.gridy = 1;
		add(lblMarch, gbc_lblMarch);
		
		JLabel lblJensStoltenberg = DefaultComponentFactory.getInstance().createLabel("Jens Stoltenberg");
		GridBagConstraints gbc_lblJensStoltenberg = new GridBagConstraints();
		gbc_lblJensStoltenberg.anchor = GridBagConstraints.WEST;
		gbc_lblJensStoltenberg.insets = new Insets(0, 0, 5, 5);
		gbc_lblJensStoltenberg.gridx = 1;
		gbc_lblJensStoltenberg.gridy = 1;
		add(lblJensStoltenberg, gbc_lblJensStoltenberg);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(new Color(Integer.parseInt("181818",16)));
		GridBagConstraints gbc_panel_2 = new GridBagConstraints();
		gbc_panel_2.fill = GridBagConstraints.BOTH;
		gbc_panel_2.gridheight = 3;
		gbc_panel_2.insets = new Insets(0, 0, 0, 5);
		gbc_panel_2.gridx = 1;
		gbc_panel_2.gridy = 3;
		add(panel_2, gbc_panel_2);
		FlowLayout fl_panel_2 = new FlowLayout(FlowLayout.CENTER, 5, 5);
		panel_2.setLayout(fl_panel_2);
		
		JLabel lblUpcomingEvents = DefaultComponentFactory.getInstance().createLabel("Upcoming events");
		lblUpcomingEvents.setForeground(Color.WHITE);
		panel_2.add(lblUpcomingEvents);
		
		JPanel panel_1 = new JPanel();
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.fill = GridBagConstraints.BOTH;
		gbc_panel_1.insets = new Insets(0, 0, 0, 5);
		gbc_panel_1.gridx = 0;
		gbc_panel_1.gridy = 5;
		add(panel_1, gbc_panel_1);
		panel_1.setBackground(new Color(Integer.parseInt("181818",16)));
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[]{203, 107, 0, 0, 0, 0, 0, 0, 0};
		gbl_panel_1.rowHeights = new int[]{23, 0, 0, 0, 0, 0, 0, 0};
		gbl_panel_1.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panel_1.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel_1.setLayout(gbl_panel_1);
		panel_1.setAlignmentX(RIGHT_ALIGNMENT);
		
		JButton btnCreateEvent = new JButton("+ Create event");
		GridBagConstraints gbc_btnCreateEvent = new GridBagConstraints();
		gbc_btnCreateEvent.insets = new Insets(0, 0, 0, 5);
		gbc_btnCreateEvent.anchor = GridBagConstraints.NORTHWEST;
		gbc_btnCreateEvent.gridx = 6;
		gbc_btnCreateEvent.gridy = 6;
		panel_1.add(btnCreateEvent, gbc_btnCreateEvent);
	}
}
