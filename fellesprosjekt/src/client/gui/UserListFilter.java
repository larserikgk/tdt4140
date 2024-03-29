package client.gui;

import java.awt.Component;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import net.miginfocom.swing.MigLayout;

import common.models.User;
import common.tests.SampleUsers;

public class UserListFilter extends JPanel{
	
	private JTextField TextLine;
	private JList userList;
	private JScrollPane scrollPane;
	private DefaultListModel listModel, filteredListModel;

	public UserListFilter(ArrayList<User> users) {
		setBackground(Settings2.COLOR_VERY_DARK_GRAY);
		TextLine = new JTextField(35);
		add(TextLine, "growx");
	    
	    setListModel(users);
	    userList.setCellRenderer(new UserListRenderer());
	    setLayout(new MigLayout("", "[300.00px,grow]", "[23px][grow]"));
		
	    scrollPane = new JScrollPane(userList);
	    add(scrollPane, "cell 0 1,grow");
	    
		addListener();
       	}
	
	private void addListener(){	
		TextLine.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent evt) {
                filter();
            }

            @Override
            public void removeUpdate(DocumentEvent evt) {
                filter();
            }

            @Override
            public void changedUpdate(DocumentEvent evt) {
                filter();
            }
        });
	}
	
	private void filter(){
		String[] searchWords = TextLine.getText().split(" ");
		filteredListModel = new DefaultListModel();
		for (int i=0; i<listModel.getSize(); i++){
			if(containsAll(((User) listModel.get(i)).getName(), searchWords)){
				filteredListModel.addElement(listModel.get(i));
			}
		}
		userList.setModel(filteredListModel);
		System.out.println(TextLine.getText());
		System.out.println(getArrayList());
	}
	
	public void addUsers(ArrayList<User> users){
		if (users == null) return;
		for (User user : users){
			listModel.addElement(user);
		}
	}
	
	public ArrayList<User> removeUsers(){
		if (userList.isSelectionEmpty()) return null;
		ArrayList<User> removedUsers = new ArrayList<User>();
		int[] indices = userList.getSelectedIndices();
		for (int i=0; i<indices.length; i++) {
			User user = (User) userList.getModel().getElementAt(indices[i]-i);
			removedUsers.add(user);
			if (filteredListModel!=null) filteredListModel.removeElement(user);
			listModel.removeElement(user);
		}
		return removedUsers;
	}
	
	private boolean containsAll(String str, String[] searchTerm){
		for (String s : searchTerm){
			if(!str.toLowerCase().contains(s.toLowerCase())){
				return false;
			}
		}
		return true;
	}
	
	public ArrayList<User> getArrayList(){
		Object[] list = listModel.toArray();
		ArrayList<User> al = new ArrayList<User>();
		for (Object obj : list){
			al.add((User) obj);
		}
		return al;
	}
	
	public void setListModel(ArrayList<User> users){
		listModel = new DefaultListModel();
	    if (users != null){
	    	for (User user : users){
	    		listModel.addElement(user);
	    	}
	    }
	    userList=new JList(listModel);
	}
	
    public static void main (String args[]) { 
    	ArrayList<User> allUsers = SampleUsers.getSampleUsers();
        JFrame frame = new JFrame("JList Filter Search"); 
        frame.getContentPane().add(new UserListFilter(allUsers)); 
        frame.pack(); 
        frame.setVisible(true);   
        }
    
    public void setSingleSelectionMode(boolean b){
    	if (b) userList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    	else userList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
    }
    
    public User getSelectedUser(){
    	return (User) userList.getSelectedValue();
    }
    
    
}