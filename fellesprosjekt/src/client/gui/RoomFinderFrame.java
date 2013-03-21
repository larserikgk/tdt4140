package client.gui;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import java.awt.Color;
import javax.swing.JList;

import common.models.Event;
import common.tests.SampleRooms;
import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.ConnectException;

public class RoomFinderFrame extends BaseFrame {
	
	private static Event event;
	private RoomListFilter roomList;
	
	public RoomFinderFrame(final Event event) {
		this.event = event;
		int defaultCapacity = 1;
		if (event != null && event.getParticipants() != null && event.getParticipants().size() > 1) {
			defaultCapacity = event.getParticipants().size();
		}
		setResizable(false);
		setSize(709, 517);
		setCentered();
		getContentPane().setBackground(Settings2.COLOR_VERY_DARK_GRAY);
		getContentPane().setLayout(new MigLayout("", "[55.00][509.00,grow][][]", "[][375.00][grow]"));
		
		JLabel lblFindRoom = new JLabel("Find room");
		lblFindRoom.setForeground(Color.WHITE);
		lblFindRoom.setFont(Settings2.FONT_TEXT1);
		getContentPane().add(lblFindRoom, "cell 0 0 2 1");
		
		try {
			roomList = new RoomListFilter(getServerConnector().getAllRooms(),defaultCapacity);
		} catch (ConnectException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		getContentPane().add(roomList, "cell 1 1 2 1,grow");
		
		JPanel panel = new JPanel();
		panel.setBackground(Settings2.COLOR_LIGHT_BLUE);
		getContentPane().add(panel, "cell 2 2,alignx center,aligny center");
		panel.setLayout(new GridLayout(0, 1, 0, 0));
		
		JButton btnCancel = new JButton("Cancel");
		panel.add(btnCancel);
		btnCancel.setContentAreaFilled(false);
		btnCancel.setBorderPainted(false);
		btnCancel.setForeground(Color.WHITE);
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				close();
			}
		});
		
		JPanel panel_1 = new JPanel();
		getContentPane().add(panel_1, "cell 3 2,alignx center,aligny center");
		panel_1.setBackground(Settings2.COLOR_LIGHT_BLUE);
		panel_1.setLayout(new GridLayout(0, 1, 0, 0));
		
		JButton btnSave = new JButton("Save");
		panel_1.add(btnSave);
		btnSave.setContentAreaFilled(false);
		btnSave.setBorderPainted(false);
		btnSave.setForeground(Color.WHITE);
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				event.setRoom(roomList.getSelectedRoom());
				((EventFrame) getParentFrame()).setLocationText(event.getRoom().getName());
				close();
			}
		});
		
	}
}