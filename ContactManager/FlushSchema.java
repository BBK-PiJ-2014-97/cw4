import java.sql.Timestamp;
import java.util.Date;
 
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Elmario Husha
 * This class is simply used to construct the structure
 * of the XML file we'll be producing. In the future we could
 * use transform documents, but this will do fine for now.
 *
 */
public class FlushSchema {
	public Timestamp XMLCreationTimestamp = null;
	public List<PastMeetingImpl> pastMeetings = new ArrayList<PastMeetingImpl>();
	public List<MeetingImpl> currentMeetings = new ArrayList<MeetingImpl>();
	public List<FutureMeetingImpl> futureMeetings = new ArrayList<FutureMeetingImpl>();
	
	FlushSchema() {
		java.util.Date date = new java.util.Date();
		this.XMLCreationTimestamp = new Timestamp(date.getTime());
	}
	
}
