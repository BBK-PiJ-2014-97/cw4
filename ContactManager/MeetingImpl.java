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
	
	private Calendar date;
	private Set<Contact> contacts;
	
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

	public void setDate(Calendar date) {
		this.date = date;
	}
	
	public Calendar getDate() {
		return this.date;
	}

	public void setContacts(Set<Contact> contacts) {
		this.contacts = contacts;
	}
	
	public Set<Contact> getContacts() {
		return this.contacts;
	}

}
