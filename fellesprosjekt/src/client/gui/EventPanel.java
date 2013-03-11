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

public class EventPanel extends JPanel {
	private JTextField textField;
	private JTextField textField_1;
	private JFrame frame;
	
	//Disse variablene er de samme som i GUI_Main (fjerner dem senere)
	static final Color main_bg_light_grey = new Color(Integer.parseInt("e2dfe0",16));
	static final Color main_bg_dark_grey = new Color(Integer.parseInt("595959",16));
	static final Color dark_grey = new Color(Integer.parseInt("181818",16));
	static final Color light_blue = new Color(Integer.parseInt("159ec0",16));
	static final Color orange = new Color(Integer.parseInt("c53d2c",16));
	static final Color notification_red = new Color(Integer.parseInt("ff0000",16));
	static final Font FONT_TITLE1 = new Font("Myriad Pro", Font.BOLD, 18);
	static final Font FONT_TEXT1 = new Font("Myriad Pro", Font.PLAIN, 18);
	static final Font FONT_TEXT2 = new Font("Myriad Pro", Font.PLAIN, 16);
	
	public EventPanel() {
		setBackground(dark_grey);
		Border border = BorderFactory.createLineBorder(orange);
		setBorder(border);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 241, 117, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{17, 0, 0, 0, 0, 73, 73, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JLabel lblCreateEvent = new JLabel("Create event");
		lblCreateEvent.setFont(FONT_TEXT1);
		lblCreateEvent.setForeground(Color.WHITE);
		GridBagConstraints gbc_lblCreateEvent = new GridBagConstraints();
		gbc_lblCreateEvent.insets = new Insets(0, 0, 5, 5);
		gbc_lblCreateEvent.gridx = 1;
		gbc_lblCreateEvent.gridy = 1;
		add(lblCreateEvent, gbc_lblCreateEvent);
		
		JLabel lblTitle = new JLabel("Title");
		GridBagConstraints gbc_lblTitle = new GridBagConstraints();
		lblTitle.setForeground(Color.WHITE);
		gbc_lblTitle.anchor = GridBagConstraints.WEST;
		gbc_lblTitle.insets = new Insets(0, 0, 5, 5);
		gbc_lblTitle.gridx = 1;
		gbc_lblTitle.gridy = 2;
		add(lblTitle, gbc_lblTitle);
		
		textField = new JTextField();
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.insets = new Insets(0, 0, 5, 5);
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 2;
		gbc_textField.gridy = 2;
		add(textField, gbc_textField);
		textField.setColumns(10);
		
		JLabel lblStart = new JLabel("Start");
		lblStart.setForeground(Color.WHITE);
		GridBagConstraints gbc_lblStart = new GridBagConstraints();
		gbc_lblStart.anchor = GridBagConstraints.WEST;
		gbc_lblStart.insets = new Insets(0, 0, 5, 5);
		gbc_lblStart.gridx = 1;
		gbc_lblStart.gridy = 3;
		add(lblStart, gbc_lblStart);
		
		JLabel lblEnd = new JLabel("End");
		lblEnd.setForeground(Color.WHITE);
		GridBagConstraints gbc_lblEnd = new GridBagConstraints();
		gbc_lblEnd.anchor = GridBagConstraints.WEST;
		gbc_lblEnd.insets = new Insets(0, 0, 5, 5);
		gbc_lblEnd.gridx = 1;
		gbc_lblEnd.gridy = 4;
		add(lblEnd, gbc_lblEnd);
		
		JLabel lblParticipants = new JLabel("Participants");
		lblParticipants.setForeground(Color.WHITE);
		GridBagConstraints gbc_lblParticipants = new GridBagConstraints();
		gbc_lblParticipants.anchor = GridBagConstraints.WEST;
		gbc_lblParticipants.gridheight = 2;
		gbc_lblParticipants.insets = new Insets(0, 0, 5, 5);
		gbc_lblParticipants.gridx = 1;
		gbc_lblParticipants.gridy = 5;
		add(lblParticipants, gbc_lblParticipants);
		
		JList list = new JList();
		GridBagConstraints gbc_list = new GridBagConstraints();
		gbc_list.gridheight = 2;
		gbc_list.insets = new Insets(0, 0, 5, 5);
		gbc_list.fill = GridBagConstraints.BOTH;
		gbc_list.gridx = 2;
		gbc_list.gridy = 5;
		add(list, gbc_list);
		
		JButton btnAddParticipants = new JButton("Add participants");
		GridBagConstraints gbc_btnAddParticipants = new GridBagConstraints();
		gbc_btnAddParticipants.gridwidth = 2;
		gbc_btnAddParticipants.anchor = GridBagConstraints.SOUTHWEST;
		gbc_btnAddParticipants.insets = new Insets(0, 0, 5, 5);
		gbc_btnAddParticipants.gridx = 3;
		gbc_btnAddParticipants.gridy = 5;
		add(btnAddParticipants, gbc_btnAddParticipants);
		
		JButton btnDeleteParticipants = new JButton("Delete participants");
		GridBagConstraints gbc_btnDeleteParticipants = new GridBagConstraints();
		gbc_btnDeleteParticipants.gridwidth = 2;
		gbc_btnDeleteParticipants.anchor = GridBagConstraints.NORTHWEST;
		gbc_btnDeleteParticipants.insets = new Insets(0, 0, 5, 5);
		gbc_btnDeleteParticipants.gridx = 3;
		gbc_btnDeleteParticipants.gridy = 6;
		add(btnDeleteParticipants, gbc_btnDeleteParticipants);
		
		JLabel lblLocation = new JLabel("Location");
		lblLocation.setForeground(Color.WHITE);
		GridBagConstraints gbc_lblLocation = new GridBagConstraints();
		gbc_lblLocation.anchor = GridBagConstraints.WEST;
		gbc_lblLocation.insets = new Insets(0, 0, 5, 5);
		gbc_lblLocation.gridx = 1;
		gbc_lblLocation.gridy = 7;
		add(lblLocation, gbc_lblLocation);
		
		textField_1 = new JTextField();
		GridBagConstraints gbc_textField_1 = new GridBagConstraints();
		gbc_textField_1.insets = new Insets(0, 0, 5, 5);
		gbc_textField_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_1.gridx = 2;
		gbc_textField_1.gridy = 7;
		add(textField_1, gbc_textField_1);
		textField_1.setColumns(10);
		
		JButton btnFindRoom = new JButton("Find room");
		GridBagConstraints gbc_btnFindRoom = new GridBagConstraints();
		gbc_btnFindRoom.gridwidth = 2;
		gbc_btnFindRoom.anchor = GridBagConstraints.WEST;
		gbc_btnFindRoom.insets = new Insets(0, 0, 5, 5);
		gbc_btnFindRoom.gridx = 3;
		gbc_btnFindRoom.gridy = 7;
		add(btnFindRoom, gbc_btnFindRoom);
		
		JButton btnCancel = new JButton("Cancel");
		GridBagConstraints gbc_btnCancel = new GridBagConstraints();
		gbc_btnCancel.anchor = GridBagConstraints.EAST;
		gbc_btnCancel.insets = new Insets(0, 0, 0, 5);
		gbc_btnCancel.gridx = 3;
		gbc_btnCancel.gridy = 9;
		add(btnCancel, gbc_btnCancel);
		
		JButton btnCreate = new JButton("Create");
		GridBagConstraints gbc_btnCreate = new GridBagConstraints();
		gbc_btnCreate.insets = new Insets(0, 0, 0, 5);
		gbc_btnCreate.gridx = 4;
		gbc_btnCreate.gridy = 9;
		add(btnCreate, gbc_btnCreate);
		
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frame.dispose();
			}
		});
	}
	
	public void setFrame(JFrame frame){
		this.frame = frame;
	}

}
