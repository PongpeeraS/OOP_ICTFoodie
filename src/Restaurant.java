import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/** Name: Pongpeera Sukasem
 * StudentID: 5988040
 * Section: 1 (Group 1)
 */

public class Restaurant implements BusinessInterface{
	public static final SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
	
	public static enum PRICE{
		$, $$, $$$, UNKNOWN			// the default value is PRICE.UNKNOWN
	}
	
	private int id;
	private String name;
	private Address address;
	private String phone;
	private String description;
	private Set<String> categories;
	private PRICE priceRange;
	private TimeInterval[] hours;	//(0 = Sunday, 1 = Monday, 2 = Tuesday, 3 = Wednesday, 4 = Thursday, 5 = Friday, 6 = Saturday) 
	private List<Review> reviews;
	private double rating;
	
	//Created variables
	private double simRow; //Stores similarity score of the row requested, only used to for sorting in findSimilarRestaurant
	
	public Restaurant(int id, String name){
		// initialize all values
		// YOUR CODE GOES HERE
		this.id = id;
		this.name = name;
		this.phone = "";
		this.description = "";
		this.address = new Address("");
		this.categories = new HashSet<String>();
		this.priceRange = PRICE.UNKNOWN;
		this.reviews = new ArrayList<Review>();
		this.rating = 0.0;
		this.hours = new TimeInterval[7];
	}
	
	public Restaurant(int id, String name, Address address, String phone, String desc){
		// initialize all values
		// YOUR CODE GOES HERE
		this.id = id;
		this.name = name;
		this.phone = phone;
		this.description = desc;
		this.address = address;
		this.categories = new HashSet<String>();
		this.priceRange = PRICE.UNKNOWN;
		this.reviews = new ArrayList<Review>();
		this.rating = 0.0;
		this.hours = new TimeInterval[7];
	}
	
	/*
	 * additional method for setting address
	 */
	public void setAddress(String street, String district, String province, String postalCode, double lat, double lon) {
		this.address = new Address(street, district, province, postalCode, lat, lon);
	}
	
	/*
	 * additional method for setting categories
	 * You will have to split each category in the input string and add into categories Set
	 * For example, if the string is "[Thai,Cafe]", you will add two categories into the categories Set
	 */
	public void setCategories(String str){
		// YOUR CODE GOES HERE
		str = str.replace("[", "");
		str = str.replace("]", "");
		String dummy[] = str.split(",");
		if(dummy.length != 0){
			for(int i=0;i<dummy.length;i++){
				this.categories.add(dummy[i]);
			}
		}
	}
	
	/*
	 * additional method for setting price range
	 * You must convert from String to enum PRICE
	 * For example, if the string is "$", then the priceRange will be enum of PRICE.$
	 */
	public void setPriceRange(String pr){
		// YOUR CODE GOES HERE
		if(pr.equals("$")){
			this.priceRange = PRICE.$;
		}
		else if(pr.equals("$$")){
			this.priceRange = PRICE.$$;
		}
		else if(pr.equals("$$$")){
			this.priceRange = PRICE.$$$;
		}
		else{
			this.priceRange = PRICE.UNKNOWN;
		}
	}
	
	
	/*
	 * additional method for setting open hours
	 * You must split the input string into an array containing exactly 7 elements.
	 * Each element will be used to create a TimeInterval object 
	 * where the start time is the open hour and the end time is the close hour.
	 * If an element equals to "close", then the TimeInterval object is null.
	 * For example, if string is "[8-20,close,8-20,8-20,8-20,8-20,8-20]", then
	 * 		hours[0] = new TimeInterval("8-20");
	 * 		hours[1] = null;
	 * 		hours[2] = new TimeInterval("8-20"); .... etc
	 */
	public void setHours(String str){
		// YOUR CODE GOES HERE
			str = str.replace("[", "");
			str = str.replace("]", "");
			String dummy[] = str.split(",");
			for(int i=0;i<hours.length;i++){ 
				if(dummy[i].equals("close")){
					hours[i] = new TimeInterval();//No arguments = startTime & endTime = null
				}
				else{
					hours[i] = new TimeInterval(dummy[i]);
				}
			}
	}
	
	public Set<String> getCategories(){
		//No "YOUR CODE GOES HERE", I could be wrong :P
		return this.categories;
		//return null;
	}
	
	public String getCategoriesString(){
		String str = "";
		for(String c: this.categories){
			str += c + ", ";
		}
		if(!str.isEmpty())
			str = str.substring(0, str.lastIndexOf(","));
		return str;
	}
	
	public PRICE getPriceRange(){
		// YOUR CODE GOES HERE
		return priceRange;
		//return null;
	}
	
	public TimeInterval[] getOpenHours(){
		// YOUR CODE GOES HERE
		return hours;
		//return null;
	}
	
	public TimeInterval getOpenHoursOnDay(int index){
		// YOUR CODE GOES HERE
		return hours[index];
		//return null;
	}
	
	public double getAverageRating(){
		// YOUR CODE GOES HERE
		return rating;
		//return -1;
	}
	
	public List<Review> getReviews(){
		// YOUR CODE GOES HERE
		return reviews;
		//return null;
	}
	
	public double calculateAverageRating(){
		// YOUR CODE GOES HERE
		double count = 0.0, total = 0.0;
		for(int i=0;i<reviews.size();i++){
			count++;
			total += (double) reviews.get(i).getRating();
		}
		if(total != 0){
			rating = total/count;
		}
		else{
			rating = 0.0;
		}
		return rating;
		//return -1;
	}
	
	@Override
	public String getSnippetProfile() {
		String shortDesc = "";
		if(this.description.length() > 100)
			shortDesc = this.description.substring(0, 100) + "(more...)";
		else
			shortDesc = this.description;
		
		return this.name + " (" + this.priceRange.name() + ")\n" 
				+ shortDesc + "\n"
				+ "Categories: " + this.getCategoriesString() + "\n"
				+ "Rating: " + this.rating + " (" + this.reviews.size() + ")";
	}
	
	@Override
	public String getFullProfile() {
		return this.name + " (" + this.priceRange.name() + ")\n"
				+ this.description + "\n"
				+ "Categories: " + this.getCategoriesString() + "\n"
				+ "Rating: " + this.rating + " (" + this.reviews.size() + ")\n"
				+ "Address: " + this.address.toString() + "\n"
				+ "Phone: " + this.phone + "\n"
				+ "Hours: " + this.getHoursString();
	}
	
	public String toString(){
		return getFullProfile();
	}
	
	public String getHoursString(){
		String getStr = "\nSun: " + this.getOpenHour(hours[0]);
		getStr += "\nMon: " + this.getOpenHour(hours[1]);
		getStr += "\nTue: " + this.getOpenHour(hours[2]);
		getStr += "\nWed: " + this.getOpenHour(hours[3]);
		getStr += "\nThu: " + this.getOpenHour(hours[4]);
		getStr += "\nFri: " + this.getOpenHour(hours[5]);
		getStr += "\nSat: " + this.getOpenHour(hours[6]);
		
		return getStr;
	}
	
	public String getOpenHour(TimeInterval hour){
		if(hour.startTime == null || hour.endTime == null)
			return "close";
		else
			return timeFormat.format(hour.startTime) + "-" + timeFormat.format(hour.endTime);
	}

	//Additional methods to fully implement BusinessInterface
	public void setID(int id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setDescription(String desc) {
		this.description = desc;
	}
	
	public int getID() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Address getAddress() {
		return address;
	}

	public String getPhone() {
		return phone;
	}

	public String getDescription() {
		return description;
	}
	//End of interface methods
	
	//Created additional methods
	
	public double getSimRow() {
		return simRow;
	}

	public void setSimRow(double simRow) {
		this.simRow = simRow;
	}
	//End of additional methods

	
	
	
}
