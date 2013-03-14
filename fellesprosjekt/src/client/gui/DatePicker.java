package client.gui;

import java.util.Calendar;

import javax.swing.JPanel;
import javax.swing.SpinnerDateModel;

import com.toedter.calendar.JDateChooser;
import javax.swing.JSpinner;
import javax.swing.JLabel;

public class DatePicker extends JPanel {
	public DatePicker() {
		
		JDateChooser dateChooser = new JDateChooser();
		dateChooser.setDateFormatString("dd.mm.yyyy");
		add(dateChooser);
		
		SpinnerDateModel model = new SpinnerDateModel();
		model.setCalendarField(Calendar.HOUR_OF_DAY);
		
		JSpinner spinner_hour = new JSpinner();
		spinner_hour.setModel(model);
		spinner_hour.setEditor(new JSpinner.DateEditor(spinner_hour, "H"));
		add(spinner_hour);
		
		JLabel label = new JLabel(":");
		add(label);
		
//		SpinnerDateModel model = new SpinnerDateModel();
//		model.setCalendarField(Calendar.HOUR_OF_DAY);
		JSpinner spinner_minute = new JSpinner();
		spinner_minute.setModel(model);
		spinner_minute.setEditor(new JSpinner.DateEditor(spinner_minute, "mm"));
		add(spinner_minute);
	}

}
