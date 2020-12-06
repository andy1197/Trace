package Components;
import java.util.ArrayList;

import javafx.scene.control.TextField;

/*
 * Manages list of Customers
 */
public class CustomerManager {
	
	private ArrayList<Customer> customers; // list of customers
	
	// returns true if the fields are valid, false otherwise
	public boolean validateTextFields(TextField name, TextField age, TextField email, TextField phone, TextField street, TextField city, TextField state, TextField zip) {
		return name.getText() != null && age.getText() != null && email.getText() != null 
				&& phone.getText() != null && street.getText() != null
				&& !name.getText().isEmpty() && !age.getText().isEmpty()
				&& !email.getText().isEmpty() && !phone.getText().isEmpty()
				&& !street.getText().isEmpty() && city.getText() != null && state != null
				&& zip != null && !city.getText().isEmpty() && !state.getText().isEmpty()
				&& !zip.getText().isEmpty();
	}
	
	public boolean validateSingleTextField(TextField text) {
		return text.getText() != null && !text.getText().isEmpty();
	}
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
	 * Sets the customer list
	 */
	public void setCustomerList(ArrayList<Customer> arr)
	{
		customers = arr;
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
