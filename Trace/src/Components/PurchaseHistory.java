package Components;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/*
 * Class that organizes a customer's purchase history of products.
 * Takes a text file organized with lines of "product_name product_price"
 * and creates a purchase history for the customer
 */

public class PurchaseHistory {
	
	private String[] productList = {"Chicken Nuggets", "Backpack", "Potato", "Apple", "Orange", "Target Gift Card", 
			"Chips", "Pasta", "Rice", "Water", "Juice", "Soda", "Pasta Sauce", "Lettuce", "Tomato", "Ketchup", "Mustard",
			"Salt", "Pepper", "Cup", "Echo Frames", "Bananas", "Hair Brush", "Laptop", "Nintendo Switch", "Slippers", "Watch", 
			"Clock", "PS5", "Guacamole", "Kombucha", "Pizza", "Mashed Potatoes", "Dress Shoes", "Chicken", "Cheese", "Maple Syrup",
			"Vanilla Ice Cream", "Chocolate Ice Cream", "Beyond Burger Meat" };
	private int[] priceList = {10, 30, 5, 2, 2, 20, 5, 4, 30, 1, 4, 8, 12, 2, 3, 5, 5, 2, 2, 10, 100, 2, 5, 500, 299, 10, 500, 10, 400, 5, 2, 10, 2, 30, 10, 14, 10, 5, 5, 20};
	private int[] count = new int[productList.length];
	
	private ArrayList<Product> arr;
	
	public ArrayList<Product> getHistory() {
		return arr;
	}
	
	public int[] getProductCount() {
		return count;
	}
	
	public PurchaseHistory() { 
		Random gen = new Random();
		int productCount = gen.nextInt(30);
		arr = new ArrayList<>();
		for (int i = 0; i < productCount; i++) {
			arr.add(generateProduct());
		}
	}
	
	public String getDemographic() {
		int count = 0;
		for (Product p : arr) {
			count += p.getPrice();
		}
		if (count > 500) {
			return "Upper Class";
		}
		else if (count > 250) {
			return "Upper Middle Class";
		}
		else if (count > 150) {
			return "Middle Class";
		}
		return "Lower Middle Class";
	}
	
	public String getCustomerLoyalty() {
		int count = arr.size();
		if (count > 25) {
			return "Golden Member";
		}
		if (count > 15) {
			return "Silver Member";
		}
		if (count > 8) {
			return "Bronze Member";
		}
		return "Member";
	}
	
	public Product generateProduct() {
		Random gen = new Random();
		int rand = gen.nextInt(productList.length);
		
		String name = productList[rand];
		int price = priceList[rand];
		count[rand] = count[rand] + 1;
		
		return new Product(name, price);
	}
	
	public int numOfProducts() { return productList.length; }
	
	public Product getProduct(int i) {
		return new Product(productList[i], priceList[i]);
	}

	

}
