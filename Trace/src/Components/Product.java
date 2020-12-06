package Components;

/*
 * Class for the products
 */
public class Product {

	private String name;
	private int price;

	// Constructor
	public Product(String name, int price) {
		this.name = name;
		this.price = price;
	}

	// getter method for the price
	public int getPrice() {
		return price;
	}

	// getter method for the name
	public String getName() {
		return name;
	}

}
