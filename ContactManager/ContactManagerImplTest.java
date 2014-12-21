import static org.junit.Assert.*;

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
		assertNotEquals(null, myctm.getPastMeeting(2));
	}
	
	@Test
	public void testCanGetFutureMeetingListByContact() {
		
	}
	
	@Test
	public void testCanGetPastMeetingByContact() {
		
	}
	
	@Test
	public void testCanAddMeetingNotes() {
		
	}
	
	@Test
	public void testCanAddNewContact() {
		
	}
	
	@Test
	public void testCanGetContactsById() {
		
	}
	
	@Test
	public void testCanGetContactsByName() {
		
	}
	
	@Test
	public void testCanGetMeetingsByDate() {
		
	}
	
	@Test
	public void testCanFlushStateToFile() {
		
	}
}
