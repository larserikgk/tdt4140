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
import javax.swing.JSpinner;
import java.awt.BorderLayout;

public class ParticipantsFrame extends BaseFrame {
	private JTextField textField_title;
	
	public ParticipantsFrame() {
		super();
		setResizable(false);
		setSize(709, 517);
		setCentered();
		getContentPane().setBackground(Settings2.COLOR_VERY_DARK_GRAY);
		Border border = BorderFactory.createLineBorder(Settings2.COLOR_ORANGE);
		//getContentPane().setBorder(border);
		getContentPane().setLayout(new MigLayout("", "[2%,grow][5%,grow][15%,grow][25%,grow][5%,grow][20%,grow][20%,grow][2%,grow]", "[5%,grow][1%,grow][5%,grow][1%,grow][5%,grow][40%,grow][1%,grow][5%,grow]"));
	
		JLabel lblAddParticipants = new JLabel("Add participants");
		lblAddParticipants.setFont(Settings2.FONT_TITLE1);
		lblAddParticipants.setFont(Settings2.FONT_TEXT1);
		lblAddParticipants.setForeground(Color.WHITE);
		getContentPane().add(lblAddParticipants, "cell 1 0 2 1,alignx left,aligny center");
		
		
		JLabel lblSearch = new JLabel("Search");
		lblSearch.setFont(Settings2.FONT_TEXT2);
		lblSearch.setForeground(Color.WHITE);
		getContentPane().add(lblSearch, "cell 2 2,alignx left,aligny center");
		
		textField_title = new JTextField();
		getContentPane().add(textField_title, "cell 3 2,growx,aligny center");
		textField_title.setColumns(10);
		
		JLabel lblUsers = new JLabel("Users");
		lblUsers.setFont(Settings2.FONT_TEXT2);
		lblUsers.setForeground(Color.WHITE);
		getContentPane().add(lblUsers, "cell 2 4,alignx left,aligny top");
		
		JLabel lblParticipants = new JLabel("Participants");
		lblParticipants.setForeground(Color.WHITE);
		lblParticipants.setFont(Settings2.FONT_TEXT2);
		getContentPane().add(lblParticipants, "cell 5 4");
		
		JList list_participants = new JList();
		getContentPane().add(list_participants, "cell 2 5 2 1,grow");
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(Settings2.COLOR_VERY_DARK_GRAY);
		getContentPane().add(panel_2, "cell 4 5,alignx center,growy");
		panel_2.setLayout(new MigLayout("", "[grow]", "[][grow][grow][grow][]"));
		
		JPanel panel_4 = new JPanel();
		panel_4.setBackground(Color.gray);
		panel_2.add(panel_4, "cell 0 1,growx,aligny center");
		panel_4.setLayout(new GridLayout(0, 1, 0, 0));
		
		JButton button_2 = new JButton(">");
		button_2.setForeground(Color.BLUE);
		button_2.setContentAreaFilled(false);
		button_2.setBorderPainted(false);
		button_2.setFont(new Font("Tahoma", Font.PLAIN, 20));
		panel_4.add(button_2);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBackground(Color.gray);
		panel_2.add(panel_3, "cell 0 3,alignx center,aligny center");
		panel_3.setLayout(new GridLayout(0, 1, 0, 0));
		
		JButton button_1 = new JButton("<");
		button_1.setForeground(Color.BLUE);
		button_1.setContentAreaFilled(false);
		button_1.setBorderPainted(false);
		button_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		panel_3.add(button_1);
		
		JTextPane textPane_description = new JTextPane();
		getContentPane().add(textPane_description, "cell 5 5 2 1,grow");
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Settings2.COLOR_LIGHT_BLUE);
		getContentPane().add(panel_1, "flowx,cell 5 7,alignx right,aligny center");
		panel_1.setLayout(new GridLayout(1, 0, 0, 0));
		
		JButton btnCancel = new JButton("Cancel");
		panel_1.add(btnCancel);
		btnCancel.setForeground(Color.WHITE);
		btnCancel.setBorderPainted(false);
		btnCancel.setContentAreaFilled(false);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(24, 161, 195));
		getContentPane().add(panel, "cell 6 7,alignx left,aligny center");
		panel.setLayout(new GridLayout(1, 0, 0, 0));
		
		JButton btnApply = new JButton("Apply");
		panel.add(btnApply);
		btnApply.setForeground(Color.WHITE);
		btnApply.setContentAreaFilled(false);
		btnApply.setBorderPainted(false);
		
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				close();
			}
		});
	}
}
