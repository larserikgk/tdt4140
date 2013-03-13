package client.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import net.miginfocom.swing.MigLayout;
import java.awt.GridLayout;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextPane;

public class EventFrame extends JFrame {
	private JTextField textField;
	private JTextField textField_1;
	private JFrame parentFrame;
	
	public EventFrame() {
		getContentPane().setBackground(Settings2.COLOR_VERY_DARK_GRAY);
		Border border = BorderFactory.createLineBorder(Settings2.COLOR_ORANGE);
		//getContentPane().setBorder(border);
		getContentPane().setLayout(new MigLayout("", "[][10.00,grow][99px,grow][236px,grow][112px,grow][65px,grow]", "[5%,grow][1%,grow][5%,grow][5%,grow][5%,grow][25%,grow][5%,grow][5%,grow][10%,grow][1%,grow][5%,grow]"));
	
		JLabel lblCreateEvent = new JLabel("Create event");
		lblCreateEvent.setFont(Settings2.FONT_TITLE1);
		lblCreateEvent.setFont(Settings2.FONT_TEXT1);
		lblCreateEvent.setForeground(Color.WHITE);
		getContentPane().add(lblCreateEvent, "cell 1 0 2 1,alignx left,aligny center");
		
		
		JLabel lblTitle = new JLabel("Title");
		lblTitle.setFont(Settings2.FONT_TEXT2);
		lblTitle.setForeground(Color.WHITE);
		getContentPane().add(lblTitle, "cell 2 2,alignx left,aligny center");
		
		textField = new JTextField();
		getContentPane().add(textField, "cell 3 2,growx,aligny center");
		textField.setColumns(10);
		
		JLabel lblFrom = new JLabel("From");
		lblFrom.setFont(Settings2.FONT_TEXT2);
		lblFrom.setForeground(Color.WHITE);
		getContentPane().add(lblFrom, "cell 2 3,alignx left,aligny center");
		
		JLabel lblTo = new JLabel("To");
		lblTo.setFont(Settings2.FONT_TEXT2);
		lblTo.setForeground(Color.WHITE);
		getContentPane().add(lblTo, "cell 2 4,alignx left,aligny center");
		
		JLabel lblParticipants = new JLabel("Participants");
		lblParticipants.setFont(Settings2.FONT_TEXT2);
		lblParticipants.setForeground(Color.WHITE);
		getContentPane().add(lblParticipants, "cell 2 5,alignx left,aligny top");
		
		JList list = new JList();
		getContentPane().add(list, "cell 3 5,grow");
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(Settings2.COLOR_VERY_DARK_GRAY);
		panel_2.setVisible(true);
		getContentPane().add(panel_2, "cell 4 5 1 4,grow");
		panel_2.setLayout(new MigLayout("", "[125px]", "[23px][][][][][][][]"));
		
		JPanel panel_4 = new JPanel();
		panel_4.setBackground(Settings2.COLOR_DARK_GRAY);
		panel_2.add(panel_4, "cell 0 0,grow");
		panel_4.setLayout(new GridLayout(0, 1, 0, 0));
		
		JButton btnAddParticipants = new JButton("Add participants");
		btnAddParticipants.setContentAreaFilled(false);
		btnAddParticipants.setForeground(Color.WHITE);
		btnAddParticipants.setBorderPainted(false);
		
		panel_4.add(btnAddParticipants);
		
				
		JPanel panel_3 = new JPanel();
		panel_3.setBackground(Settings2.COLOR_DARK_GRAY);
		panel_2.add(panel_3, "cell 0 1,grow");
		panel_3.setLayout(new GridLayout(0, 1, 0, 0));
		
		JButton btnDeleteParticipants = new JButton("Delete participants");
		btnDeleteParticipants.setContentAreaFilled(false);
		btnDeleteParticipants.setForeground(Color.white);
		btnDeleteParticipants.setBorderPainted(false);
		panel_3.add(btnDeleteParticipants);
		
		JPanel panel_5 = new JPanel();
		panel_5.setBackground(Settings2.COLOR_DARK_GRAY);
		panel_2.add(panel_5, "cell 0 7,grow");
		panel_5.setLayout(new GridLayout(0, 1, 0, 0));
		
		JButton btnFindRoom = new JButton("Find room");
		panel_5.add(btnFindRoom);
		btnFindRoom.setContentAreaFilled(false);
		btnFindRoom.setBorderPainted(false);
		btnFindRoom.setForeground(Color.white);
		
		JLabel lblRepeat = new JLabel("Repeat");
		lblRepeat.setFont(Settings2.FONT_TEXT2);
		lblRepeat.setForeground(Color.WHITE);
		getContentPane().add(lblRepeat, "cell 2 6,alignx left");
		
		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"One-time event", "Daily", "Weekdays (Mon - Fri)", "Weekly", "Monthly", "Yearly"}));
		getContentPane().add(comboBox, "cell 3 6,growx");
		
		JLabel lblLocation = new JLabel("Location");
		lblLocation.setFont(Settings2.FONT_TEXT2);
		lblLocation.setForeground(Color.WHITE);
		getContentPane().add(lblLocation, "cell 2 7,alignx left,aligny center");
		
		textField_1 = new JTextField();
		getContentPane().add(textField_1, "cell 3 7,growx,aligny center");
		textField_1.setColumns(10);
		
		JLabel lblDescription = new JLabel("Description");
		lblDescription.setFont(Settings2.FONT_TEXT2);
		lblDescription.setForeground(Color.WHITE);
		getContentPane().add(lblDescription, "cell 2 8,aligny top");
		
		JTextPane textPane = new JTextPane();
		getContentPane().add(textPane, "cell 3 8,grow");
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, "cell 4 10,alignx right,aligny center");
		panel.setBackground(Settings2.COLOR_LIGHT_BLUE);
		panel.setLayout(new GridLayout(1, 0, 0, 0));
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.setForeground(Color.WHITE);
		btnCancel.setBorderPainted(false);
		btnCancel.setContentAreaFilled(false);
		panel.add(btnCancel);
		
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				parentFrame.setEnabled(true);
				parentFrame.setFocusable(true);
				dispose();
			}
		});
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Settings2.COLOR_LIGHT_BLUE);
		getContentPane().add(panel_1, "cell 5 10,alignx left,aligny center");
		panel_1.setLayout(new GridLayout(1, 0, 0, 0));
		
		JButton btnCreate = new JButton("Create");
		btnCreate.setForeground(Color.WHITE);
		btnCreate.setContentAreaFilled(false);
		btnCreate.setBorderPainted(false);
		panel_1.add(btnCreate);
	}
	
	public void setParentFrame(JFrame parentFrame){
		this.parentFrame = parentFrame;
	}
	
	public void setEventTitle(String title){
		textField.setText(title);
	}

}
