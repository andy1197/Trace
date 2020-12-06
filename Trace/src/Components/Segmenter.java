package Components;

import java.util.ArrayList;

import javafx.scene.control.ChoiceBox;

public class Segmenter {

	private ArrayList<Customer> backup;
	
	public Segmenter(ArrayList<Customer> arr) {
		
	}
	
	public ArrayList<Customer> makeBackupTable(ArrayList<Customer> arr) {
		backup = new ArrayList<Customer>();
		for (Customer c : arr) {
			backup.add(c);
		}
		return backup;
	}
	
	public ChoiceBox<String> getLoyaltyChoiceBox() {
        ChoiceBox<String> choiceBox = new ChoiceBox<>();
        choiceBox.getItems().addAll("All", "Member", "Bronze Member", "Silver Member", "Golden Member");
        choiceBox.setValue("All");
        return choiceBox;
	}
	
	public ArrayList<Customer> byAreaCode(int n, ArrayList<Customer> arr){
		ArrayList<Customer> custs  = arr;
		ArrayList<Customer> selected = new ArrayList<Customer>();
		
		for(Customer c : custs) {
			if(Integer.parseInt(c.getPhoneNum().substring(0,3)) == n)
			{
				selected.add(c);
			}
		}
		return selected;
		
	}
	public ArrayList<Customer> updateList(int min, int max, String loyalty, int areaCode, ArrayList<Customer> arr) {
		ArrayList<Customer> newList = new ArrayList<Customer>();
		for (Customer c : arr) {
			if (c.getAge() <= max && c.getAge() >= min) {
				if (loyalty == "All" || c.getLoyalty().compareTo(loyalty) == 0) {
					if (areaCode == -1 || Integer.parseInt(c.getPhoneNum().substring(0,3)) == areaCode) {
						newList.add(c);
					}
				}
			}
		}
		return newList;
	}
}
