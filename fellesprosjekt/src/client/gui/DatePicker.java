package client.gui;

import java.awt.Color;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import net.miginfocom.swing.MigLayout;

import com.toedter.calendar.JDateChooser;

public class DatePicker extends JPanel {
	
	private JDateChooser dateChooser;
	private JSpinner spinnerHour, spinnerMinute;
	private Date date, minDate, maxDate;
	
	private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        this.pcs.addPropertyChangeListener(listener);
    }
	
	//For testing
	public static void main(String[] args){
		JFrame frame = new JFrame();
		DatePicker dp = new DatePicker(new Date());
		frame.add(dp);
		frame.pack();
		frame.setVisible(true);
	}
	
	//BRUK DENNE KONSTRUKT�REN
	//Setter dato ved opprettelse, slik at feltet ikke er tomt
	public DatePicker(Date date){
		this();
		this.date = date;
		setDefaultDate(date);
	}
	
	public DatePicker() {
		setBackground(Settings2.COLOR_VERY_DARK_GRAY);		
		setLayout(new MigLayout("insets 0", "[90px][][35px][2px][35px]", "[20px]"));
		setSize(getPreferredSize());
		
		minDate = new Date(0,0,1); //Min date: 1 Jan 1900
		maxDate = new Date(199,11,31); //Max date: 31 Dec 2099
		
		dateChooser = new JDateChooser();
		dateChooser.setDateFormatString("dd.MM.y");
		dateChooser.setMinSelectableDate(minDate); 
		dateChooser.setMaxSelectableDate(maxDate);
		
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
		
		setDefaultTime(11,0); //Default time: 11:00
		
		addListeners();
	}
	
	public void setDefaultDate(Date date){
		dateChooser.setDate(date);
	}
	
	public void setDefaultTime(int hours, int minutes){
		Date time = new Date(0,0,0,hours,minutes);
		spinnerHour.setValue(time);
		spinnerMinute.setValue(time);
	}
	
	public void setDate(Date date){
		dateChooser.setDate(date);
		spinnerHour.setValue(date);
		spinnerMinute.setValue(date);
	}
	
	public void setMinimumDate(Date date){
		date.setSeconds(0);
		dateChooser.setMinSelectableDate(date);
	}
	
	public void addListeners(){
		dateChooser.getDateEditor().addPropertyChangeListener(new PropertyChangeListener() {
			  @Override
			  public void propertyChange(PropertyChangeEvent e) {
				  if ("date".equals(e.getPropertyName())) {
					  if (isValidDate(dateChooser.getDate())){
						  parseDate();
						  //System.out.println("Valgt dato: "+date);
					  }
				  }
			  }
		});
		
		ChangeListener cl = new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				parseDate();
				//System.out.println("Valgt dato: "+date);
			}
		};
		
		spinnerHour.getModel().addChangeListener(cl);
		spinnerMinute.getModel().addChangeListener(cl);
	}
	
	public void parseDate(){
		date = dateChooser.getDate();
		if (isValidDate(date)){
			date.setHours(((Date) spinnerHour.getValue()).getHours());
			date.setMinutes(((Date) spinnerMinute.getValue()).getMinutes());
			date.setSeconds(0);
			DatePicker.this.pcs.firePropertyChange("datePickerDate", null, date);
		} else {
			date = null;
		}
	}
	
	public Date getDate(){
		return date;
	}
	
	public boolean isValidDate(Date date){
		//System.out.println("Gyldig dato: "+ (date.compareTo(minDate)>=0 && date.compareTo(maxDate)<=0));
		return (date.compareTo(minDate)>=0 && date.compareTo(maxDate)<=0);
	}
	
	@Override
	public void setEnabled(boolean b){
		dateChooser.setEnabled(b);
		spinnerHour.setEnabled(b);
		spinnerMinute.setEnabled(b);
	}

}