import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.thoughtworks.xstream.*;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * 
 * @author Elmario Husha
 * Our ContactManager
 */

public class ContactManagerImpl implements ContactManager{
	private List<PastMeetingImpl> pastMeetings = new ArrayList<PastMeetingImpl>();
	private List<MeetingImpl> currentMeetings = new ArrayList<MeetingImpl>();
	private List<FutureMeetingImpl> futureMeetings = new ArrayList<FutureMeetingImpl>();
	
	public ContactManagerImpl() {
		// Check if we can find something named contactManager.xmlq
		File loadFile = new File("contactManager.xml");
		if(loadFile.exists()) {
			// Load it up
			XStream xstream = new XStream(new DomDriver());
			
			xstream.alias("PastMeeting", PastMeetingImpl.class);
			xstream.alias("CurrentMeeting", MeetingImpl.class);
			xstream.alias("FutureMeeting", FutureMeetingImpl.class);
			
			FlushSchema loadedData = (FlushSchema) xstream.fromXML(loadFile);
			
			this.pastMeetings    = loadedData.pastMeetings;
			this.currentMeetings = loadedData.currentMeetings;
			this.futureMeetings  = loadedData.futureMeetings;
			
			System.out.println("Loaded data from XML Dated: " + loadedData.XMLCreationTimestamp);
		}
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
		List<Meeting> tempMeetings = new ArrayList<Meeting>();
		
		for(FutureMeetingImpl mt: this.futureMeetings) {
			for(Contact ct: mt.getContacts()) {
				if(ct.getName().equals(contact.getName())) {
					tempMeetings.add((Meeting) mt);
				}
			}
		}
		return tempMeetings;
	}

	public List<Meeting> getFutureMeetingList(Calendar date) {
		// We must loop through all of the meetings and find those
		// with the same date as our param
		
		List<Meeting> tempMeetings = new ArrayList<Meeting>();

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
		List<PastMeeting> tempMeetings = new ArrayList<PastMeeting>();
		
		for(PastMeetingImpl mt: this.pastMeetings) {
			for(Contact ct: mt.getContacts()) {
				if(ct.getName().equals(contact.getName())) {
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
		PastMeetingImpl meeting = (PastMeetingImpl) this.getPastMeeting(id);
		if(meeting != null) {
			// Only if we found something we should bother updating notes
			meeting.setNotes(text);
		} else {
			// Else lets try to find Future meeting and convert it to Past meeting
			FutureMeetingImpl futureMeeting = (FutureMeetingImpl) this.getFutureMeeting(id);
			if(futureMeeting != null) {
				// we have a match, so lets copy it over
				this.addNewPastMeeting(futureMeeting.getContacts(), futureMeeting.getDate(), text);
			}
		}
	}

	public void addNewContact(String name, String notes)  {
		// TODO Auto-generated method stub
		// Waiting for comments from PROF
	}

	public Set<Contact> getContacts(int... ids) {
		// We just need to loop through each idea, and then check each of our lists
		Set<Contact> contactsToReturn = new HashSet<Contact>();
		for(int i = 0; i < ids.length; i++){
			for(FutureMeetingImpl mt: this.futureMeetings) {
				for(Contact ct: mt.getContacts()) {
					if(ct.getId() == ids[i]) {
						contactsToReturn.add(ct);
					}
				}
			}
			
			for(MeetingImpl mt: this.currentMeetings) {
				for(Contact ct: mt.getContacts()) {
					if(ct.getId() == ids[i]) {
						contactsToReturn.add(ct);
					}
				}
			}
			
			for(PastMeetingImpl mt: this.pastMeetings) {
				for(Contact ct: mt.getContacts()) {
					if(ct.getId() == ids[i]) {
						contactsToReturn.add(ct);
					}
				}
			}
		}
		return contactsToReturn;
	}

	public Set<Contact> getContacts(String name) {
		Set<Contact> contactsToReturn = new HashSet<Contact>();
		
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
		XStream xstream = new XStream(new DomDriver());
		xstream.alias("PastMeeting", PastMeetingImpl.class);
		xstream.alias("CurrentMeeting", MeetingImpl.class);
		xstream.alias("FutureMeeting", FutureMeetingImpl.class);
		
		FlushSchema flushSchema = new FlushSchema();
		
		flushSchema.pastMeetings = this.pastMeetings;
		flushSchema.currentMeetings = this.currentMeetings;
		flushSchema.futureMeetings = this.futureMeetings;
		
		String xml = xstream.toXML(flushSchema);
		
		try {
			File savexml = new File("contactManager.xml");
			FileWriter fileWriter = new FileWriter(savexml.getAbsoluteFile());
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			bufferedWriter.write(xml);
			bufferedWriter.close();
			System.out.println("Flushed current state into contactManager.xml");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
