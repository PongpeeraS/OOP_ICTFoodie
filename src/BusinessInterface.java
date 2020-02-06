/** Name: Pongpeera Sukasem
 * StudentID: 5988040
 * Section: 1 (Group 1)
 */
public interface BusinessInterface {

	// setter methods
	void setID(int id);
	void setName(String name);
	void setAddress(Address address);
	void setPhone(String phone);
	void setDescription(String desc);
	
	// getter methods
	int getID();
	String getName();
	Address getAddress();
	String getPhone();
	String getDescription();
	
	/*
	 * Return a brief description of this business
	 */
	String getSnippetProfile();
	
	/*
	 * Return a full description of this business
	 */
	String getFullProfile();
	
}
