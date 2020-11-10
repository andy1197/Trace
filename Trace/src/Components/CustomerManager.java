package Components;
import java.util.ArrayList;

/*
 * Manages list of Customers
 */
public class CustomerManager {
	
	private ArrayList<Customer> customers; // list of cutomers
	
	/*
	 * Customer Manager constructor
	 */
	public CustomerManager() 
	{
		customers = new ArrayList<Customer>();
	}
	
	/*
	 * Adds given Customer to list
	 */
	public void addCustomer(Customer p)
	{
		this.customers.add(p);
	}
	
	/*
	 * Deletes given Customer from list
	 */
	public void deleteCustomer(Customer p)
	{
		this.customers.remove(p);
	}
	
	/*
	 * Returns number of customers in list
	 */
	public int getSize()
	{
		return this.customers.size();
	}
	
	/*
	 * Returns Customer located at given index
	 */
	public Customer getCustomer(int i)
	{
		return this.customers.get(i);
	}
	
	/*
	 * Returns list of Customers
	 */
	public ArrayList<Customer> getCustomerList()
	{
		return customers;
	}
	
	/*
	 * Sets a Customer to given index in list
	 */
	public void setCustomer(int i, Customer c)
	{
		customers.set(i, c);
	}

	
	/*
	 * sort by ___
	 */
}
