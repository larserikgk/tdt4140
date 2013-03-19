package client.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

import com.toedter.calendar.JDateChooser;
import javax.swing.JButton;
import java.awt.Dimension;

public class MainDatePicker extends BaseFrame {
	private JDateChooser dateChooser;
	private Date date, minDate, maxDate;
	private JButton btnGoTo;
	
	private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        this.pcs.addPropertyChangeListener(listener);
    }
	
	//For testing
	public static void main(String[] args){
		MainDatePicker mdp = new MainDatePicker();
		mdp.setVisible(true);
	}
	
	//BRUK DENNE KONSTRUKTØREN
	//Setter dato ved opprettelse, slik at feltet ikke er tomt
	public MainDatePicker(Date date){
		this();
		this.date = date;
		setDefaultDate(date);
	}
	
	public MainDatePicker() {
		setBackground(Settings2.COLOR_VERY_DARK_GRAY);		
		getContentPane().setLayout(new MigLayout("insets 0", "[44.98%,grow]", "[40.00px]"));
		getContentPane().setBackground(Settings2.COLOR_VERY_DARK_GRAY);
		setSize(new Dimension(140, 80));
		
		minDate = new Date(0,0,1); //Min date: 1 Jan 1900
		maxDate = new Date(199,11,31);
		
		dateChooser = new JDateChooser();
		dateChooser.setDateFormatString("dd.MM.y");
		dateChooser.setMinSelectableDate(minDate); 
		dateChooser.setMaxSelectableDate(maxDate);
		
		getContentPane().add(dateChooser, "cell 0 0,growx,aligny center");
		
//		btnGoTo = new JButton("Go to");
//		getContentPane().add(btnGoTo, "cell 3 0");
		
		addListeners();
	}
	
	public void setDefaultDate(Date date){
		dateChooser.setDate(date);
	}
	
	public void setMinimumDate(Date date){
		dateChooser.setMinSelectableDate(date);
	}
	
	public void addListeners(){
		dateChooser.getDateEditor().addPropertyChangeListener(new PropertyChangeListener() {
			  @Override
			  public void propertyChange(PropertyChangeEvent e) {
				  if ("date".equals(e.getPropertyName())) {
					  if (isValidDate(dateChooser.getDate())){
						  parseDate();
						  System.out.println("Valgt dato: "+date);
						  MainDatePicker.this.pcs.firePropertyChange("DatePickerDate", null, date);
					  }
				  }
			  }
		});
		/*
		btnGoTo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainDatePicker.this.pcs.firePropertyChange("DatePickerDate", null, date);
				System.out.println("INVOKED");
			}
		});*/
	}
	
	public void parseDate(){
		date = dateChooser.getDate();
		if (!isValidDate(date)){
			date = null;
		}
	}
	
	public Date getDate(){
		return date;
	}
	
	public boolean isValidDate(Date date){
		System.out.println("Gyldig dato: "+ (date.compareTo(minDate)>=0 && date.compareTo(maxDate)<=0));
		return (date.compareTo(minDate)>=0 && date.compareTo(maxDate)<=0);
	}

}