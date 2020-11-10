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
	 * Changes address 
	 */
	public void changeAddress(String streetAddr, String city, String state, String zipcode)
	{
		this.streetAddr = streetAddr;
		this.city = city;
		this.state = state;
		this.zipcode = zipcode;
	}
	
	/*
	 * Address in String form
	 */
	public String toString()
	{
		return streetAddr + "\n" + city + ", " + state + " " + zipcode;
	}
}
