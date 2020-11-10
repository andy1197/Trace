package Components;

/*
 * Customer 
 */
public class Customer {
	
	private String name;
	private int age;
	private String email;
	private String phoneNum;
	private String addr;
	//private Address addr;
	
	/*
	 * Customer constructor
	 */
	public Customer(String name, int age, String email, String phoneNum, String addr)
	{
		this.name = name;
		this.age = age;
		this.email = email;
		this.phoneNum = phoneNum;
		this.addr = addr;
	}
	
	/*
	 * Sets name to given String
	 */
	public void setName(String n)
	{
		this.name = n;
	}
	
	/*
	 * Sets age to given integer
	 */
	public void setAge(int n)
	{
		this.age = n;
	}
	
	/*
	 * Set email to given String
	 */
	public void setEmail(String e)
	{
		this.email = e;
	}
	
	/*
	 * Sets phone number to given String
	 */
	public void setPhoneNum(String e)
	{
		this.phoneNum = e;
	}
	
	/*
	 * Sets address to given String
	 */
	public void setAddr(String e)
	{
		this.addr = e;
	}
	
	/*
	public void setAddr(Address e)
	{
		this.addr = e;
	}
	*/
	
	/*
	 * Returns name
	 */
	public String getName()
	{
		return this.name;
	}
	
	/*
	 * Returns email
	 */
	public String getEmail()
	{
		return this.email;
	}
	
	/*
	 * returns phone number
	 */
	public String getPhoneNum()
	{
		return this.phoneNum;
	}
	
	/*
	public Address getAddress()
	{
		return this.addr;
	}
	*/
	
	/*
	 * Returns address
	 */
	public String getAddr()
	{
		return addr;
	}
	
	/*
	 * Returns address as a string
	 */
	public String getAddressString()
	{
		return this.addr.toString();
	}
	
	/*
	 * Returns age
	 */
	public int getAge()
	{
		return this.age;
	}
}
