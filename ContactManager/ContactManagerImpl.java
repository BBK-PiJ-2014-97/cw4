import java.util.Calendar;
import java.util.List;
import java.util.Set;

import com.thoughtworks.xstream.XStream;

/**
 * 
 * @author Elmario Husha
 * Our ContactManager
 */

@SuppressWarnings("null")
public class ContactManagerImpl implements ContactManager{
	private List<PastMeetingImpl> pastMeetings;
	private List<MeetingImpl> currentMeetings;
	private List<FutureMeetingImpl> futureMeetings;
	
	public ContactManagerImpl() {
		// TODO: load up older meetings from our XML
	}
	
	public int addFutureMeeting(Set<Contact> contacts, Calendar date) {
		FutureMeetingImpl newFutureMeeting = new FutureMeetingImpl();
		newFutureMeeting.setContacts(contacts);
		newFutureMeeting.setDate(date);
		
		this.futureMeetings.add(newFutureMeeting);
		
		return newFutureMeeting.getId();
	}

	public PastMeeting getPastMeeting(int id) {
		for(PastMeetingImpl mt: this.pastMeetings) {
			if(mt.getId() == id) {
				return mt;
			}
		}
		return null;
	}

	public FutureMeeting getFutureMeeting(int id) {
		for(FutureMeetingImpl mt: this.futureMeetings) {
			if(mt.getId() == id) {
				return mt;
			}
		}
		return null;
	}

	public Meeting getMeeting(int id) {
		for(MeetingImpl mt: this.currentMeetings) {
			if(mt.getId() == id) {
				return mt;
			}
		}
		return null;
	}
	
	public List<Meeting> getFutureMeetingList(Contact contact) {
		// Our temp List holding filtered meetings
		List<Meeting> tempMeetings = null;
		
		for(FutureMeetingImpl mt: this.futureMeetings) {
			for(Contact ct: mt.getContacts()) {
				if(ct.equals(contact)) {
					tempMeetings.add((Meeting) mt);
				}
			}
		}
		return tempMeetings;
	}

	public List<Meeting> getFutureMeetingList(Calendar date) {
		// WAITING FOR CLARIFICATION FROM PROF DUE TO TYPOS
		return null;
	}

	public List<PastMeeting> getPastMeetingList(Contact contact) {
		// TODO Auto-generated method stub
		return null;
	}

	public void addNewPastMeeting(Set<Contact> contacts, Calendar date,
			String text) {
		// TODO Auto-generated method stub
		
	}

	public void addMeetingNotes(int id, String text) {
		// TODO Auto-generated method stub
		
	}

	public void addNewContact(String name, String notes)  {
		// TODO Auto-generated method stub
		
	}

	public Set<Contact> getContacts(int... ids) {
		// TODO Auto-generated method stub
		return null;
	}

	public Set<Contact> getContacts(String name) {
		//loop through each meeting List
		// for each contact in list, check if we have a match
		// and that it's not already in our temp set
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see ContactManager#flush()
	 * File format:XML
	 * <Calendar>
	 * 		<Created>
	 * 		<PastMeetings>
	 * 		<CurrentMeetings>
	 * 		<FutureMeetings>
	 */
	public void flush() {
		// TODO Auto-generated method stub
		XStream xstream = new XStream();
		xstream.alias("PastMeeting", PastMeetingImpl.class);
		xstream.alias("CurrentMeeting", MeetingImpl.class);
		xstream.alias("FutureMeeting", FutureMeetingImpl.class);
		
		FlushSchema flushSchema = new FlushSchema();
		
		String xml = xstream.toXML(flushSchema);
	}

}
