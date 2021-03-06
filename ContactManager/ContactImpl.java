/**
 * 
 * @author Elmario Husha
 * Contacts for our Contact Manager
 */

public class ContactImpl implements Contact {
	private static int globalId;
	private int id;
	private String name;
	private String notes;
	
	/**
	 * Params required to create a new contact
	 * @param id
	 * @param name
	 * @param notes
	 */
	
	public ContactImpl(String name, String notes) {
		this.setId(this.generateId());
		this.name = name;
		this.notes = notes;
	}

	public int getId() {
		return this.id;
	}
	
	/**
	 * We are using static id increments for our unique ids
	 * @return
	 */
	public int generateId() {
		int newId = ContactImpl.globalId++;
		return newId;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public String getNotes() {
		return this.notes;
	}

	public void addNotes(String note) {
		/*
		 * Notes are stored per line, so we need to add newline
		 */
		this.notes += '\n' + note;
	}

}
