public class Address {
/** Name: Pongpeera Sukasem
 * StudentID: 5988040
 * Section: 1 (Group 1)
 */
	String street;		// e.g., 999 Phuttamonthon 4 Road 
	String district;	// e.g., Salaya
	String province;	// e.g., Nakhon Pathom
	String postalCode;		// e.g., 73170
	double latitude = 0.0;	// e.g., 100.324611
	double longitude = 0.0;	// e.g., 13.794760
	
	public Address(String fullAddress){
		// YOUR CODE GOES HERE
		fullAddress = fullAddress.replace("[", "");
		fullAddress = fullAddress.replace("]", "");
		String dummy[] = fullAddress.split(",");
		if(dummy.length == 6){
			this.street = dummy[0];
			this.district = dummy[1];
			this.province = dummy[2];
			this.postalCode = dummy[3];
			this.latitude = Double.parseDouble(dummy[4]);
			this.longitude = Double.parseDouble(dummy[5]);
		}
		else if(dummy.length < 6 && dummy.length > 0){
			this.street = fullAddress;
			this.district = ""; //*Empty string != null*
			this.province = "";
			this.postalCode = "";
			this.latitude = 0.0;
			this.longitude = 0.0;
		}
	}
	
	public Address(String street, String district, String province, String postalCode, double lat, double lon){
		// YOUR CODE GOES HERE
		this.street = street;
		this.district = district;
		this.province = province;
		this.postalCode = postalCode;
		this.latitude = lat;
		this.longitude = lon;
	}
	
	public String toString(){
		String str = street;
		if(!district.isEmpty())
			str += ", " + district;
		if(!province.isEmpty())
			str += ", " + province;
		if(!postalCode.isEmpty())
			str += ", " + postalCode;
		if(latitude != 0.0 & longitude != 0.0)
			str += "(" + latitude +"," + longitude + ")";
		return str;
	}
}
