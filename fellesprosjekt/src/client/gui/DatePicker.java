package client.gui;

import java.awt.Color;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SpinnerDateModel;

import com.toedter.calendar.JDateChooser;
import javax.swing.JSpinner;
import javax.swing.JLabel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.Dimension;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import net.miginfocom.swing.MigLayout;

public class DatePicker extends JPanel {
	
	private JDateChooser dateChooser;
	private JSpinner spinnerHour, spinnerMinute;
	
	//For testing
	public static void main(String[] args){
		JFrame frame = new JFrame();
		DatePicker dp = new DatePicker(new Date());
		frame.add(dp);
		frame.pack();
		frame.setVisible(true);
	}
	
	//Setter dato ved opprettelse, slik at feltet ikke er tomt
	public DatePicker(Date date){
		this();
		setDefaultDate(date);
	}
	
	public DatePicker() {
		setBackground(Settings2.COLOR_VERY_DARK_GRAY);		
		setLayout(new MigLayout("insets 0", "[90px][][35px][2px][35px]", "[20px]"));
		
		dateChooser = new JDateChooser();
		dateChooser.setDateFormatString("dd.MM.yyyy");
		add(dateChooser, "cell 0 0,growx,aligny top");
		
		SpinnerDateModel model = new SpinnerDateModel();
		model.setCalendarField(Calendar.HOUR_OF_DAY);
		
		spinnerHour = new JSpinner();
		spinnerHour.setModel(model);
		spinnerHour.setEditor(new JSpinner.DateEditor(spinnerHour, "HH"));
		add(spinnerHour, "cell 2 0,alignx left,aligny top");
		
		JLabel label = new JLabel(":");
		label.setForeground(Color.WHITE);
		add(label, "cell 3 0,alignx left,aligny center");
		
		SpinnerDateModel model_minute = new SpinnerDateModel();
		model_minute.setCalendarField(Calendar.MINUTE);
		spinnerMinute = new JSpinner();
		spinnerMinute.setModel(model_minute);
		spinnerMinute.setEditor(new JSpinner.DateEditor(spinnerMinute, "mm"));
		add(spinnerMinute, "cell 4 0,alignx left,aligny top");
		
		setDefaultTime(new Date(0,0,0,11,0)); //Default time: 11:00
		addListeners();
	}
	
	public void setDefaultDate(Date date){
		dateChooser.setDate(date);
	}
	
	public void setDefaultTime(Date date){
		spinnerHour.setValue(date);
		spinnerMinute.setValue(date);
	}
	
	public void setMinimumDate(Date date){
		dateChooser.setMinSelectableDate(date);
	}
	
	public void addListeners(){
		dateChooser.getDateEditor().addPropertyChangeListener(new PropertyChangeListener() {
			  @Override
			  public void propertyChange(PropertyChangeEvent e) {
				  if ("date".equals(e.getPropertyName())) {
					  System.out.println("Valgt dato: "+getDate());
				  }
			  }
		});
		
		ChangeListener cl = new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				System.out.println("Valgt dato: "+getDate());
			}
		};
		
		spinnerHour.getModel().addChangeListener(cl);
		spinnerMinute.getModel().addChangeListener(cl);
	}
	
	public JDateChooser getDateChooser() {
		return dateChooser;
	}
	public JSpinner getSpinnerHour() {
		return spinnerHour;
	}
	public JSpinner getSpinnerMinute() {
		return spinnerMinute;
	}
	
	private Date parsedDate(){
		Date date = dateChooser.getDate();
		date.setHours(((Date) spinnerHour.getValue()).getHours());
		date.setMinutes(((Date) spinnerMinute.getValue()).getMinutes());
		date.setSeconds(0);
		return date;
	}
	
	public Date getDate(){
		return parsedDate();
	}

}