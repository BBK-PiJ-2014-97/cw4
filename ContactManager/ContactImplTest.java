

import static org.junit.Assert.*;

import org.junit.Test;

public class ContactImplTest {

	@Test
	public void testWeCanCreateNewContact() {
		ContactImpl newContact = new ContactImpl("Mario", "");
		assertNotEquals(null, newContact);
	}

	@Test
	public void testCanGetContactId() {
		ContactImpl newContact = new ContactImpl("Mario", "");
		assertNotEquals(null, newContact.getId());
	}

	@Test
	public void testContactIdIsUnique() {
		ContactImpl newContact1 = new ContactImpl("Mario", "");
		ContactImpl newContact2 = new ContactImpl("Bob", "");
		assertNotEquals(newContact1.getId(), newContact2.getId());
	}

	@Test
	public void testCanGetContactName() {
		ContactImpl newContact = new ContactImpl("Mario", "");
		assertNotEquals(null, newContact.getName());
	}

	@Test
	public void testCanGetContactNotes() {
		ContactImpl newContact = new ContactImpl("Mario", "Test");
		assertNotEquals(null, newContact.getNotes());
	}
	
	@Test
	public void testCanChangeContactNotes() {
		ContactImpl newContact = new ContactImpl("Mario", "");
		newContact.addNotes("BLABLA");
		assertNotEquals("", newContact.getNotes());
	}


}
