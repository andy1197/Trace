package Components;

/*
 * Address of a Customer
 */
public class Address {

	String streetAddr;
	String city;
	String state;
	String zipcode;

	/*
	 * Address constructor
	 */
	public Address(String streetAddr, String city, String state, String zipcode) {
		this.streetAddr = streetAddr;
		this.city = city;
		this.state = state;
		this.zipcode = zipcode;

	}

	// setter method for a fresh address
	public void setAddress(String str, String c, String s, String zip) {
		setStreetAddr(str);
		setCity(c);
		setState(s);
		setZipcode(zip);
	}

	// setter method for the street address
	public void setStreetAddr(String s) {
		this.streetAddr = s;
	}

	// setter method for the city
	public void setCity(String s) {
		this.city = s;
	}

	// setter method for the state
	public void setState(String s) {
		this.state = s;
	}

	// setter method for the zip code
	public void setZipcode(String s) {
		this.zipcode = s;
	}

	// returns the combined address
	public String toString() {
		return streetAddr + "\n" + city + ", " + state + " " + zipcode;
	}

	// getter method for the street address
	public String getStreetAddr() {
		return streetAddr;
	}

	// getter method for the city
	public String getCity() {
		return city;
	}

	// getter method for the state
	public String getState() {
		return state;
	}

	// getter method for the zipcode
	public String getZipcode() {
		return zipcode;
	}
}
