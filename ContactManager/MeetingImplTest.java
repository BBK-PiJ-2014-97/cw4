import static org.junit.Assert.*;

import org.junit.Test;


public class MeetingImplTest {

	@Test
	public void testWeCanCreateNewMeeting() {
		MeetingImpl myMeeting = new MeetingImpl();
		assertNotEquals(null, myMeeting);
	}
	
	@Test
	public void testWeGetValidId() {
		MeetingImpl myMeeting = new MeetingImpl();
		assertEquals(true, myMeeting.getId() > 1);
	}
	
	@Test
	public void testWeCanGetDate() {
		MeetingImpl myMeeting = new MeetingImpl();
		assertNotEquals(null, myMeeting.getDate());
	}
	
	@Test
	public void testWeCanGetContacts() {
		MeetingImpl myMeeting = new MeetingImpl();
		assertNotEquals(null, myMeeting.getContacts());
	}

}
