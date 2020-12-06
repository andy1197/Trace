package Components;

import java.util.ArrayList;

/*
 * Customer class 
 */
public class Customer {

	private String name;
	private int age;
	private String email;
	private String phoneNum;
	private String addr;
	private Address addrObj;
	private PurchaseHistory history;
	private String demographic;
	private String loyalty;

	/*
	 * Customer constructor
	 */
	public Customer(String name, int age, String email, String phoneNum, Address addr) {
		this.name = name;
		this.age = age;
		this.email = email;
		this.phoneNum = phoneNum;
		this.addrObj = addr;
		this.addr = addrObj.toString();
		history = new PurchaseHistory();
		demographic = history.getDemographic();
		loyalty = history.getCustomerLoyalty();
	}

	/*
	 * Sets name to given String
	 */
	public void setName(String n) {
		this.name = n;
	}

	/*
	 * Sets age to given integer
	 */
	public void setAge(int n) {
		this.age = n;
	}

	/*
	 * Set email to given String
	 */
	public void setEmail(String e) {
		this.email = e;
	}

	/*
	 * Sets phone number to given String
	 */
	public void setPhoneNum(String e) {
		this.phoneNum = e;
	}

	/*
	 * Sets address to given String
	 */
	public void setAddr(String e) {
		// this.addr = e;
	}

	public void setAddrObject(Address e) {
		this.addrObj = e;
		this.addr = this.addrObj.toString();
	}

	/*
	 * Returns name
	 */
	public String getName() {
		return this.name;
	}

	/*
	 * Returns email
	 */
	public String getEmail() {
		return this.email;
	}

	/*
	 * returns phone number
	 */
	public String getPhoneNum() {
		return this.phoneNum;
	}

	/*
	 * returns the customer's demographic
	 */
	public String getDemographic() {
		return demographic;
	}

	/*
	 * returns the customer's loyalty
	 */
	public String getLoyalty() {
		return loyalty;
	}

	/*
	 * Returns address
	 */
	public String getAddr() {
		return addr;
	}

	/*
	 * Returns address as a string
	 */
	public Address getAddrObject() {
		return addrObj;
	}

	/*
	 * Returns age
	 */
	public int getAge() {
		return this.age;
	}

	/*
	 * Returns purchase history
	 */
	public PurchaseHistory getPurchaseHistory() {
		return history;
	}
}
