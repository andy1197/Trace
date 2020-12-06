package Components;

import java.util.ArrayList;

/*
 * Generates insights
 */
public class InsightsGenerator {

	ArrayList<Customer> arr;
	int num; // number of unique products
	
	public InsightsGenerator(ArrayList<Customer> arr) {
		this.arr = arr;
		PurchaseHistory h = arr.get(0).getPurchaseHistory();
		num = h.numOfProducts();
	}
	
	public Product popularProduct() {
		int[] count = new int[num]; 
		for (Customer c : arr) {
			PurchaseHistory h = c.getPurchaseHistory();
			ArrayList<Product> history = h.getHistory();
			int[] thisCount = h.getProductCount();
			for (int i = 0; i < thisCount.length; i++) {
				count[i] = count[i] + thisCount[i];
				System.out.println(count[i]);
			}
		}
		int max = 0;
		int maxIndex = 0;
		for (int i = 0; i < count.length; i++) {
			if (count[i] > max) {
				max = count[i];
				maxIndex = i;
			}
		}
		PurchaseHistory h = arr.get(0).getPurchaseHistory();
		System.out.println(count[maxIndex]);
		return h.getProduct(maxIndex);

	}
	
	public double averageAge() {
		double count = 0;
		int sum = 0;
		for (Customer c : arr) {
			sum += c.getAge();
			count++;
		}
		return sum / count; 
	}
	
	public Customer highestSpender() {
		int max = 0;
		Customer maxSpender = null;
		for (Customer c : arr) {
			PurchaseHistory h = c.getPurchaseHistory();
			ArrayList<Product> history = h.getHistory();
			int spendings = 0;
			for (Product p : history) {
				spendings += p.getPrice();
			}
			if (spendings > max) {
				max = spendings;
				maxSpender = c;
			}
		}
		return maxSpender;
	}
	
}
