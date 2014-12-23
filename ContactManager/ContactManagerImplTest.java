import static org.junit.Assert.*;

import java.io.File;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;


public class ContactManagerImplTest {

	@Test
	public void testCanAddFutureMeeting() {
		ContactManagerImpl myctm = new ContactManagerImpl();
		
		Set<Contact> testContacts = new HashSet<Contact>();
		testContacts.add(new ContactImpl("Mario", "Bla"));
		testContacts.add(new ContactImpl("Mario2", "Bla"));
		
		Calendar.getInstance().add(Calendar.DAY_OF_MONTH, +25);
		assertNotEquals(null, myctm.addFutureMeeting(testContacts, Calendar.getInstance()));
	}

	@Test
	public void testCanGetFutureMeetingById() {
		ContactManagerImpl myctm = new ContactManagerImpl();
		
		Set<Contact> testContacts = new HashSet<Contact>();
		testContacts.add(new ContactImpl("Mario", "Bla"));
		testContacts.add(new ContactImpl("Mario2", "Bla"));
		
		Calendar.getInstance().add(Calendar.DAY_OF_MONTH, +25);

		int returnedId = myctm.addFutureMeeting(testContacts, Calendar.getInstance());
		
		assertNotEquals(null, myctm.getFutureMeeting(returnedId));
		
	}
	
	@Test
	public void testCanAddPastMeeting() {
		ContactManagerImpl myctm = new ContactManagerImpl();
		
		Set<Contact> testContacts = new HashSet<Contact>();
		testContacts.add(new ContactImpl("Mario", "Bla"));
		
		Calendar.getInstance().add(Calendar.DAY_OF_MONTH, -5);
		myctm.addNewPastMeeting(testContacts, Calendar.getInstance(), "Test Notes");
	}
	
	@Test
	public void testCanGetPastMeetingById() {
		ContactManagerImpl myctm = new ContactManagerImpl();
		
		Set<Contact> testContacts = new HashSet<Contact>();
		testContacts.add(new ContactImpl("Mario", "Bla"));
		
		Calendar.getInstance().add(Calendar.DAY_OF_MONTH, -5);
		myctm.addNewPastMeeting(testContacts, Calendar.getInstance(), "Test Notes");
		// For some reason we don't have to return the ID, so can't actually test well
//		assertNotEquals(null, myctm.getPastMeeting(2));
	}
	
	@Test
	public void testCanGetFutureMeetingListByContact() {
		ContactManagerImpl myctm = new ContactManagerImpl();
		
		ContactImpl testContact = new ContactImpl("Mario", "Bla");
		
		Set<Contact> testContacts = new HashSet<Contact>();
		testContacts.add(testContact);
		
		Calendar.getInstance().add(Calendar.DAY_OF_MONTH, +25);

		myctm.addFutureMeeting(testContacts, Calendar.getInstance());
		assertNotEquals(null, myctm.getFutureMeetingList(testContact));
	}
	
	@Test
	public void testCanGetPastMeetingByContact() {
		ContactManagerImpl myctm = new ContactManagerImpl();
		
		ContactImpl testContact = new ContactImpl("Mario", "Bla");
		
		Set<Contact> testContacts = new HashSet<Contact>();
		testContacts.add(testContact);
		
		Calendar.getInstance().add(Calendar.DAY_OF_MONTH, +25);

		myctm.addNewPastMeeting(testContacts, Calendar.getInstance(), "Testing");
		assertNotEquals(null, myctm.getPastMeetingList(testContact));
	}
	
	@Test
	public void testCanAddMeetingNotes() {
		ContactManagerImpl myctm = new ContactManagerImpl();
		
		Set<Contact> testContacts = new HashSet<Contact>();
		testContacts.add(new ContactImpl("Mario", "Bla"));
		testContacts.add(new ContactImpl("Mario2", "Bla"));
		
		Calendar.getInstance().add(Calendar.DAY_OF_MONTH, +25);

		int returnedId = myctm.addFutureMeeting(testContacts, Calendar.getInstance());
		myctm.addMeetingNotes(returnedId, "MY TEST MEETING NOTES"); // Turns to PAST meeting
	}
	
	@Test
	public void testCanAddNewContact() {
		ContactManagerImpl myctm = new ContactManagerImpl();
		myctm.addNewContact("Mario", "asdasd");
		assertEquals(1, myctm.contacts.size());
	}
	
	@Test
	public void testCanGetContactsById() {
		ContactManagerImpl myctm = new ContactManagerImpl();
		Contact contact1 = new ContactImpl("Mario", "Bla");
		int contact1Id = contact1.getId();
		Contact contact2 = new ContactImpl("Mario2", "Bla");
		int contact2Id = contact2.getId();
		
		Set<Contact> testContacts = new HashSet<Contact>();
		testContacts.add(contact1);
		testContacts.add(contact2);
		
		Calendar.getInstance().add(Calendar.DAY_OF_MONTH, +25);
		myctm.addNewPastMeeting(testContacts, Calendar.getInstance(), "Testing");
		
		assertNotEquals(null, myctm.getContacts(0,1));
	}
	
	@Test
	public void testCanGetContactsByName() {
		ContactManagerImpl myctm = new ContactManagerImpl();
		
		Set<Contact> testContacts = new HashSet<Contact>();
		testContacts.add(new ContactImpl("Mario", "Bla"));
		
		Calendar.getInstance().add(Calendar.DAY_OF_MONTH, -5);
		myctm.addNewPastMeeting(testContacts, Calendar.getInstance(), "Test Notes");
		assertNotEquals(null, myctm.getContacts("Mario"));
	}
	
	@Test
	public void testCanGetMeetingsByDate() {
		ContactManagerImpl myctm = new ContactManagerImpl();
		
		Set<Contact> testContacts = new HashSet<Contact>();
		testContacts.add(new ContactImpl("Mario", "Bla"));
		
		Calendar calInstance = Calendar.getInstance();
		
		myctm.addNewPastMeeting(testContacts, calInstance, "Test Notes");
		assertNotEquals(null, myctm.getFutureMeetingList(calInstance));	
	}
	
	@Test
	public void testCanFlushStateToFile() {
		ContactManagerImpl myctm = new ContactManagerImpl();
		
		Set<Contact> testContacts = new HashSet<Contact>();
		testContacts.add(new ContactImpl("Mario", "Bla"));
		
		Calendar calInstance = Calendar.getInstance();
		
		myctm.addNewPastMeeting(testContacts, calInstance, "Test Notes");
		
		Set<Contact> testContacts2 = new HashSet<Contact>();
		testContacts2.add(new ContactImpl("George", "Bla"));
		
		Calendar calInstance2 = Calendar.getInstance();
		
		myctm.addNewPastMeeting(testContacts2, calInstance2, "Test Notes 2");
		
		Set<Contact> testContacts3 = new HashSet<Contact>();
		testContacts3.add(new ContactImpl("Mike", "Bla"));
		
		Calendar calInstance3 = Calendar.getInstance();
		
		myctm.addNewPastMeeting(testContacts3, calInstance3, "Test Notes");
		
		Set<Contact> testContacts4 = new HashSet<Contact>();
		testContacts4.add(new ContactImpl("John", "Bla"));
		
		Calendar.getInstance().add(Calendar.DAY_OF_MONTH, +25);
		myctm.addFutureMeeting(testContacts4, Calendar.getInstance());
		
		myctm.flush();
		
		File testXmlExists = new File("contacts.xml");
		assertEquals(true, testXmlExists.exists());
	}
	
	@Test
	public void testCanLoadStateFromXMLFile() {
		ContactManagerImpl myctm = new ContactManagerImpl();
	}
}
