package client.gui;

import java.awt.Color;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JPanel;
import javax.swing.SpinnerDateModel;

import com.toedter.calendar.JDateChooser;
import javax.swing.JSpinner;
import javax.swing.JLabel;
import java.awt.Dimension;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import net.miginfocom.swing.MigLayout;

public class DatePicker extends JPanel {
	
	JDateChooser dateChooser;
	
	public DatePicker() {
		
		setBackground(Settings2.COLOR_VERY_DARK_GRAY);
		setLayout(new MigLayout("", "[90px][][35px][2px][35px]", "[20px]"));
		
		dateChooser = new JDateChooser();
		dateChooser.setDateFormatString("dd.mm.yyyy");
		add(dateChooser, "cell 0 0,growx,aligny top");
		
		SpinnerDateModel model = new SpinnerDateModel();
		model.setCalendarField(Calendar.HOUR_OF_DAY);
		
		JSpinner spinner_hour = new JSpinner();
		spinner_hour.setModel(model);
		spinner_hour.setEditor(new JSpinner.DateEditor(spinner_hour, "H"));
		add(spinner_hour, "cell 2 0,alignx left,aligny top");
		
		JLabel label = new JLabel(":");
		label.setForeground(Color.WHITE);
		add(label, "cell 3 0,alignx left,aligny center");
		
//		SpinnerDateModel model = new SpinnerDateModel();
//		model.setCalendarField(Calendar.HOUR_OF_DAY);
		JSpinner spinner_minute = new JSpinner();
		spinner_minute.setModel(model);
		spinner_minute.setEditor(new JSpinner.DateEditor(spinner_minute, "mm"));
		add(spinner_minute, "cell 4 0,alignx left,aligny top");
	}
	
	public void setMinimumDate(Date date){
		dateChooser.setMinSelectableDate(date);
	}
	
	public void addListener(){
		dateChooser.getDateEditor().addPropertyChangeListener(
			    new PropertyChangeListener() {
			        @Override
			        public void propertyChange(PropertyChangeEvent e) {
			            if ("date".equals(e.getPropertyName())) {
			                System.out.println(e.getPropertyName()
			                    + ": " + (Date) e.getNewValue());
			            }
			        }
			    });
			this.add(dateChooser);
	}

}
