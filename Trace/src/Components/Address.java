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
	public Address(String streetAddr, String city, String state, String zipcode)
	{
		this.streetAddr = streetAddr;
		this.city = city;
		this.state = state;
		this.zipcode = zipcode;
		
	}
	
	/*
	 * Set full address 
	 */
	public void setAddress(String str, String c, String s, String zip)
	{
		setStreetAddr(str);
		setCity(c);
		setState(s);
		setZipcode(zip);
	}
	
	/*
	 * Set street address 
	 */
	public void setStreetAddr(String s)
	{
		this.streetAddr = s;
	}
	
	/*
	 * Set city
	 */
	public void setCity(String s)
	{
		this.city = s;
	}
	
	/*
	 * Set state
	 */
	public void setState(String s)
	{
		this.state = s;
	}
	
	/*
	 * Set zipcode
	 */
	public void setZipcode(String s )
	{
		this.zipcode = s;
	}
	
	/*
	 * Address in String form
	 */
	public String toString()
	{
		return streetAddr + "\n" + city + ", " + state + " " + zipcode;
	}
	
	public String getStreetAddr()
	{
		return streetAddr;
	}
	
	public String getCity()
	{
		return city;
	}
	
	public String getState()
	{
		return state;
	}
	
	public String getZipcode()
	{
		return zipcode;
	}
}
