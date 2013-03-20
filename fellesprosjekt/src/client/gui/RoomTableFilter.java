package client.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.regex.PatternSyntaxException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.RowFilter.ComparisonType;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import net.miginfocom.swing.MigLayout;

import common.models.Room;
import common.tests.SampleRooms;

public class RoomTableFilter extends JPanel {
    
    private JTextField filterText;
    private JSpinner spinnerCapacity;
    private JTable table;
    private TableRowSorter<TableModel> sorter;
    private RowFilter searchFilter, capacityFilter;
    private ArrayList<RowFilter<Object, Object>> andFilters;
    private TableModel roomTableModel;
    
    private int defaultCapacity = 1;
    private JLabel lblCapacity;
    private JLabel lblRoomName;
    
    public RoomTableFilter(int capacity){
    	this();
    	spinnerCapacity.setValue(capacity);
    	filter();
    }

    public RoomTableFilter() {
        setBackground(Settings2.COLOR_VERY_DARK_GRAY);
        
        andFilters = new ArrayList<RowFilter<Object, Object>>();
        setLayout(new MigLayout("insets 0", "[450px,grow]", "[30px][265px,grow]"));
        
        filterText = new JTextField();
        filterText.setPreferredSize(new Dimension(200, 20));
        filterText.setMinimumSize(new Dimension(30, 20));
        filterText.setForeground(Settings2.COLOR_VERY_DARK_GRAY);
        
        JPanel filterPanel = new JPanel();
        filterPanel.setBackground(Settings2.COLOR_VERY_DARK_GRAY);
        filterPanel.setPreferredSize(new Dimension(300, 30));
        filterPanel.setLayout(new MigLayout("insets 0", "[80%,grow][50px]", "[][]"));
        
        lblRoomName = new JLabel("Room name:");
        lblRoomName.setHorizontalTextPosition(SwingConstants.LEADING);
        lblRoomName.setForeground(Color.WHITE);
        filterPanel.add(lblRoomName, "cell 0 0,alignx left");
        
        lblCapacity = new JLabel("Capacity:");
        lblCapacity.setHorizontalAlignment(SwingConstants.TRAILING);
        lblCapacity.setForeground(Color.WHITE);
        filterPanel.add(lblCapacity, "cell 1 0,alignx left,growy");
        filterPanel.add(filterText, "cell 0 1,grow");
        add(filterPanel, "cell 0 0,growx,aligny top");
        
        spinnerCapacity = new JSpinner();
	    spinnerCapacity.setModel(new SpinnerNumberModel(defaultCapacity,1,999,1));
        filterPanel.add(spinnerCapacity, "cell 1 1,grow");
        
        table = new JTable(new RoomTableModel());
        table.setPreferredScrollableViewportSize(new Dimension(500, 160));
        table.setFillsViewportHeight(true);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, "cell 0 1,grow");
        roomTableModel = table.getModel();
        sorter = new TableRowSorter<TableModel>(roomTableModel);
        table.setRowSorter(sorter);
        table.setDefaultRenderer(String.class, new RoomTableCellRenderer());
        table.setDefaultRenderer(Integer.class, new RoomTableCellRenderer());
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        addListeners();
    }
    
    private void addListeners(){
    	spinnerCapacity.getModel().addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				filter();
			}
		});
    	filterText.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent evt) {
            	 filter();
            }
            public void removeUpdate(DocumentEvent evt) {
            	filter();
            }
            public void changedUpdate(DocumentEvent evt) {
            	filter();
            }
        });
    }
    
    private void filter(){
    	andFilters.clear();
    	searchFieldFilter();
    	capacityFilter();
    	if (searchFilter != null) andFilters.add(searchFilter);
    	if (capacityFilter != null) andFilters.add(capacityFilter);
    	sorter.setRowFilter(RowFilter.andFilter(andFilters));
    }
    
    private void searchFieldFilter() {
        String text = filterText.getText();
        if (text.length() == 0) {
            searchFilter = null;
            table.clearSelection();
        } else {
            try {
            	searchFilter = RowFilter.regexFilter("(?i)" + text, 0);
                table.clearSelection();
            } catch (PatternSyntaxException pse) {}
        }
    }
    
    private void capacityFilter() {
    	int capacity = (int) spinnerCapacity.getValue();
    	try {
    		capacityFilter = RowFilter.numberFilter(ComparisonType.AFTER, capacity-1, 1);
    		table.clearSelection();
    	} catch (PatternSyntaxException pse) {}
    }


    private class RoomTableModel extends AbstractTableModel {

        private String[] columnNames = {"Room name", "Capacity"};
        private ArrayList<Object[]> data;
        
        public RoomTableModel(ArrayList<Room> rooms){
        	data = generateArray(rooms);
        }
        
        //For testing
        public RoomTableModel(){
        	data = generateArray(SampleRooms.getSampleRooms());
        }
        
        public ArrayList<Object[]> generateArray(ArrayList<Room> rooms){
        	ArrayList<Object[]> data = new ArrayList<Object[]>();
        	for (Room r : rooms){
        		Object[] row = {r.getName(), r.getCapacity()};
        		data.add(row);
        	}
        	return data;
        }

        @Override
        public int getColumnCount() {
            return columnNames.length;
        }

        @Override
        public int getRowCount() {
            return data.size();
        }

        @Override
        public String getColumnName(int col) {
            return columnNames[col];
        }

        @Override
        public Object getValueAt(int row, int col) {
            return data.get(row)[col];
        }

        @Override
        public Class<?> getColumnClass(int c) {
            return getValueAt(0, c).getClass();
        }
    }

    public static void main(String[] args) {
    	JFrame frame = new JFrame("Room Finder");
        frame.getContentPane().add(new RoomTableFilter(23));
        frame.pack();
        frame.setVisible(true);
    }
}