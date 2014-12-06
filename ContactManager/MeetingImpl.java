import java.util.Calendar;
import java.util.Set;

/**
 * 
 * @author Elmario Husha
 * Meetings for our Contact Manager
 */

public class MeetingImpl implements Meeting {
	private static int globalId;
	private int id;
	
	public MeetingImpl() {
		this.setId(this.generateId());
	}
	
	public int getId() {
		return this.id;
	}
	
	public int generateId() {
		int newId = MeetingImpl.globalId++;
		return newId;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	public Calendar getDate() {
		// TODO Auto-generated method stub
		return null;
	}

	public Set<Contact> getContacts() {
		// TODO Auto-generated method stub
		return null;
	}

}
