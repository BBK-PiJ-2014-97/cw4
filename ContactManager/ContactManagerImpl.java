import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
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
	private List<FutureMeetingImpl> futureMeetings = new ArrayList<FutureMeetingImpl>();
	public Set<Contact> contacts = new HashSet<Contact>();
	
	// This is the name of the file we will use for XML IO
	private String fileName = "contacts.xml";
	
	public ContactManagerImpl() {
		this.checkState();
	}

	/*
	 * @param contacts	The contacts we would like to add with our meeting
	 * @param data		The date this meeting will take place
	 * @see ContactManager#addFutureMeeting(java.util.Set, java.util.Calendar)
	 */
	public int addFutureMeeting(Set<Contact> contacts, Calendar date) {
		if(contacts.isEmpty()) {
			throw new IllegalArgumentException("Invalid Params provided for ContactManagerImpl.addFutureMeeting");
		}
		
		FutureMeetingImpl newFutureMeeting = new FutureMeetingImpl();
		newFutureMeeting.setContacts(contacts);
		newFutureMeeting.setDate(date);

		this.futureMeetings.add(newFutureMeeting);
		
		return newFutureMeeting.getId();
	}

	/*
	 * @param id	Id of the meeting we would like to retrieve
	 * @see ContactManager#getPastMeeting(int)
	 */
	public PastMeeting getPastMeeting(int id) {
		PastMeetingImpl returnMeeting = null;
		for(PastMeetingImpl mt: this.pastMeetings) {
			if(mt.getId() == id) {
				returnMeeting = mt;
			}
		}
		return returnMeeting;
	}

	/*
	 * @param id	Id of the meeting we would like to retrieve
	 * @see ContactManager#getFutureMeeting(int)
	 */
	public FutureMeeting getFutureMeeting(int id) {
		if(id < 0) {
			throw new IllegalArgumentException("Invalid Params provided for ContactManagerImpl.getFutureMeeting");
		}
		
		FutureMeetingImpl returnMeeting = null;
		for(FutureMeetingImpl mt: this.futureMeetings) {
			if(mt.getId() == id) {
				returnMeeting = mt;
			}
		}
		return returnMeeting;
	}

	/*
	 * @param id	Id of the meeting we would like to retrieve
	 * @see ContactManager#getMeeting(int)
	 */
	public Meeting getMeeting(int id) {
		if(id < 0) {
			throw new IllegalArgumentException("Invalid Params provided for ContactManagerImpl.getMeeting");
		}
		
		MeetingImpl returnMeeting = null;
		
		returnMeeting = (MeetingImpl) this.getFutureMeeting(id);
		if(returnMeeting == null) {
			returnMeeting = (MeetingImpl) this.getPastMeeting(id);
		}

		return returnMeeting;
	}
	
	/*
	 * @param contact	Contact we would like to check for inside our meetings
	 * @see ContactManager#getFutureMeetingList(Contact)
	 */
	public List<Meeting> getFutureMeetingList(Contact contact) {
		// Our temp List holding filtered meetings
		List<Meeting> tempMeetings = new ArrayList<Meeting>();
		
		for(FutureMeetingImpl mt: this.futureMeetings) {
			if(mt.getContacts().contains(contact) && !tempMeetings.contains((Meeting) mt)) { // Avoid having to do another loop
				tempMeetings.add((Meeting) mt);
			}
		}
		return tempMeetings;
	}

	/*
	 * @param date	The date we want to check against
	 * @see ContactManager#getFutureMeetingList(java.util.Calendar)
	 */
	public List<Meeting> getFutureMeetingList(Calendar date) {
		// We must loop through all of the meetings and find those
		// with the same date as our param
		
		List<Meeting> tempMeetings = new ArrayList<Meeting>();

		for(FutureMeetingImpl mt: this.futureMeetings) {
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

	/*
	 * @param contact		the Contact we want to check against
	 * @see ContactManager#getPastMeetingList(Contact)
	 */
	public List<PastMeeting> getPastMeetingList(Contact contact) {
		// Our temp List holding filtered meetings
		List<PastMeeting> tempMeetings = new ArrayList<PastMeeting>();
		
		for(PastMeetingImpl mt: this.pastMeetings) {
			if(mt.getContacts().contains(contact)) { // Avoid having to do another loop
				tempMeetings.add((PastMeeting) mt);
			}
		}
		return tempMeetings;
	}

	/*
	 * @param contacts	List of contacts for our new meeting
	 * @param date		Date our meeting took place
	 * @param text		Notes describing what happened at the meeting
	 * @see ContactManager#addNewPastMeeting(java.util.Set, java.util.Calendar, java.lang.String)
	 */
	public void addNewPastMeeting(Set<Contact> contacts, Calendar date, String text) {
		PastMeetingImpl newPastMeeting = new PastMeetingImpl();
		newPastMeeting.setContacts(contacts);
		newPastMeeting.setDate(date);
		newPastMeeting.setNotes(text);
		
		this.pastMeetings.add(newPastMeeting);		
	}

	/*
	 * @param id	Id of meeting we would like to modify
	 * @param text	Notes we would like to add to the meeting
	 * @see ContactManager#addMeetingNotes(int, java.lang.String)
	 */
	public void addMeetingNotes(int id, String text) {
		if(id < 0 || text.length() < 1) {
			throw new IllegalArgumentException("Invalid Params provided for ContactManagerImpl.addMeetingNotes");
		}
		
		PastMeetingImpl meeting = (PastMeetingImpl) this.getPastMeeting(id);
		if(meeting != null) {
			// Only if we found something we should bother updating notes
			meeting.setNotes(text);
		} else {
			// Else lets try to find Future meeting and convert it to Past meeting
			FutureMeetingImpl futureMeeting = (FutureMeetingImpl) this.getFutureMeeting(id);
			if(futureMeeting != null) {
				this.futureMeetings.remove(futureMeeting);
				// we have a match, so lets copy it over
				this.addNewPastMeeting(futureMeeting.getContacts(), futureMeeting.getDate(), text);
			}
		}
	}

	/*
	 * @param name	Name of our new contact
	 * @param notes	Notes belonging to our new contact
	 * @see ContactManager#addNewContact(java.lang.String, java.lang.String)
	 */
	public void addNewContact(String name, String notes)  {
		ContactImpl newContact = new ContactImpl(name, notes);
		this.contacts.add(newContact);
	}

	/*
	 * @param ids	Ids of the contacts we would like to retrieve
	 * @see ContactManager#getContacts(int[])
	 */
	public Set<Contact> getContacts(int... ids) {
		// We just need to loop through each id, and then check each of our lists
		Set<Contact> contactsToReturn = new HashSet<Contact>();
		for(int i = 0; i < ids.length; i++){
			for(FutureMeetingImpl mt: this.futureMeetings) {
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

	/*
	 * @param	name	Name of contact we would like to retrieve
	 * @see ContactManager#getContacts(java.lang.String)
	 */
	public Set<Contact> getContacts(String name) {
		Set<Contact> contactsToReturn = new HashSet<Contact>();
		
		for(FutureMeetingImpl mt: this.futureMeetings) {
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
	 * @see ContactManager#flush()
	 */
	public void flush() {
		XStream xstream = new XStream(new DomDriver());
		xstream.alias("PastMeeting", PastMeetingImpl.class);
		xstream.alias("FutureMeeting", FutureMeetingImpl.class);
		
		FlushSchema flushSchema = new FlushSchema();
		
		flushSchema.pastMeetings = this.pastMeetings;
		flushSchema.futureMeetings = this.futureMeetings;
		
		String xml = xstream.toXML(flushSchema);
		
		try {
			File savexml = new File(this.fileName);
			FileWriter fileWriter = new FileWriter(savexml.getAbsoluteFile());
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			bufferedWriter.write(xml);
			bufferedWriter.close();
			System.out.println("Flushed current state into " + this.fileName);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	/*
	 * This method will simply check if we can load a previous state
	 * into memory from an XML file
	 */
	public void checkState() {
		// Check if we can find something resembling our file
		File loadFile = new File(this.fileName);
		if(loadFile.exists()) {
			// Load it up
			XStream xstream = new XStream(new DomDriver());
			
			// Need to do this to prevent loading issues
			xstream.alias("PastMeeting", PastMeetingImpl.class);
			xstream.alias("FutureMeeting", FutureMeetingImpl.class);
			xstream.alias("Contact", ContactImpl.class);
			
			FlushSchema loadedData = (FlushSchema) xstream.fromXML(loadFile);
			
			// Assign everything to our local state
			this.pastMeetings    = loadedData.pastMeetings;
			this.futureMeetings  = loadedData.futureMeetings;
			this.contacts	     = loadedData.contacts;
			
			System.out.println("Loaded State: [" + loadedData.XMLCreationTimestamp + "]");
		}
	}

}
