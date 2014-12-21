import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Set;

import com.thoughtworks.xstream.*;

/**
 * 
 * @author Elmario Husha
 * Our ContactManager
 */

public class ContactManagerImpl implements ContactManager{
	private List<PastMeetingImpl> pastMeetings = new ArrayList<PastMeetingImpl>();;
	private List<MeetingImpl> currentMeetings = new ArrayList<MeetingImpl>();;
	private List<FutureMeetingImpl> futureMeetings = new ArrayList<FutureMeetingImpl>();;
	
	public ContactManagerImpl() {
		// TODO: load up meetings from our XML
	}
	
	public int addFutureMeeting(Set<Contact> contacts, Calendar date) {
		FutureMeetingImpl newFutureMeeting = new FutureMeetingImpl();
		newFutureMeeting.setContacts(contacts);
		newFutureMeeting.setDate(date);

		this.futureMeetings.add(newFutureMeeting);
		
		return newFutureMeeting.getId();
	}

	public PastMeeting getPastMeeting(int id) {
		PastMeetingImpl returnMeeting = null;
		for(PastMeetingImpl mt: this.pastMeetings) {
			if(mt.getId() == id) {
				returnMeeting = mt;
			}
		}
		return returnMeeting;
	}

	public FutureMeeting getFutureMeeting(int id) {
		FutureMeetingImpl returnMeeting = null;
		for(FutureMeetingImpl mt: this.futureMeetings) {
			if(mt.getId() == id) {
				returnMeeting = mt;
			}
		}
		return returnMeeting;
	}

	public Meeting getMeeting(int id) {
		MeetingImpl returnMeeting = null;
		for(MeetingImpl mt: this.currentMeetings) {
			if(mt.getId() == id) {
				returnMeeting = mt;
			}
		}
		return returnMeeting;
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
		// We must loop through all of the meetings and find those
		// with the same date as our param
		
		List<Meeting> tempMeetings = null;

		for(FutureMeetingImpl mt: this.futureMeetings) {
			if(mt.getDate().equals(date)) {
				tempMeetings.add((Meeting) mt);
			}
		}
		
		for(MeetingImpl mt: this.currentMeetings) {
			if(mt.getDate().equals(date)) {
				tempMeetings.add((Meeting) mt);
			}
		}
		
		for(PastMeetingImpl mt: this.pastMeetings) {
			if(mt.getDate().equals(date)) {
				tempMeetings.add((Meeting) mt);
			}
		}
		
		return tempMeetings;
	}

	public List<PastMeeting> getPastMeetingList(Contact contact) {
		// Our temp List holding filtered meetings
		List<PastMeeting> tempMeetings = null;
		
		for(PastMeetingImpl mt: this.pastMeetings) {
			for(Contact ct: mt.getContacts()) {
				if(ct.equals(contact)) {
					tempMeetings.add((PastMeeting) mt);
				}
			}
		}
		return tempMeetings;
	}

	public void addNewPastMeeting(Set<Contact> contacts, Calendar date, String text) {
		PastMeetingImpl newPastMeeting = new PastMeetingImpl();
		newPastMeeting.setContacts(contacts);
		newPastMeeting.setDate(date);
		newPastMeeting.setNotes(text);
		
		this.pastMeetings.add(newPastMeeting);		
	}

	public void addMeetingNotes(int id, String text) {
		((PastMeetingImpl) this.getFutureMeeting(id)).setNotes(text);
		((PastMeetingImpl) this.getPastMeeting(id)).setNotes(text);
	}

	public void addNewContact(String name, String notes)  {
		// TODO Auto-generated method stub
		// Waiting for comments from PROF
	}

	public Set<Contact> getContacts(int... ids) {
		Set<Contact> contactsToReturn = null;
		for(FutureMeetingImpl mt: this.futureMeetings) {
			for(Contact ct: mt.getContacts()) {
				if(Arrays.asList(ids).contains(ct.getId())) {
					contactsToReturn.add(ct);
				}
			}
		}
		
		for(MeetingImpl mt: this.currentMeetings) {
			for(Contact ct: mt.getContacts()) {
				if(Arrays.asList(ids).contains(ct.getId())) {
					contactsToReturn.add(ct);
				}
			}
		}
		
		for(PastMeetingImpl mt: this.pastMeetings) {
			for(Contact ct: mt.getContacts()) {
				if(Arrays.asList(ids).contains(ct.getId())) {
					contactsToReturn.add(ct);
				}
			}
		}
		return contactsToReturn;
	}

	public Set<Contact> getContacts(String name) {
		Set<Contact> contactsToReturn = null;
		
		for(FutureMeetingImpl mt: this.futureMeetings) {
			for(Contact ct: mt.getContacts()) {
				if(ct.getName().contains(name)) {
					contactsToReturn.add(ct);
				}
			}
		}
		
		for(MeetingImpl mt: this.currentMeetings) {
			for(Contact ct: mt.getContacts()) {
				if(ct.getName().contains(name)) {
					contactsToReturn.add(ct);
				}
			}
		}
		
		for(PastMeetingImpl mt: this.pastMeetings) {
			for(Contact ct: mt.getContacts()) {
				if(ct.getName().contains(name)) {
					contactsToReturn.add(ct);
				}
			}
		}
		return contactsToReturn;
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
//		xstream.alias("PastMeeting", PastMeetingImpl.class);
//		xstream.alias("CurrentMeeting", MeetingImpl.class);
//		xstream.alias("FutureMeeting", FutureMeetingImpl.class);
		
//		FlushSchema flushSchema = new FlushSchema();
		
		String xml = xstream.toXML(PastMeetingImpl.class);
		System.out.println("Saving xml, " + xml);
	}

}
