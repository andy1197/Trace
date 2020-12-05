package application;
	
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import Components.*;

public class Main extends Application {
	TableView<Customer> customerTable; // table of Customers
	static CustomerManager manager;	// holds Customers
	BorderPane root; // main GUI window
	
	
	@Override
	/*
	 * main window
	 */
	public void start(Stage primaryStage) {
		try {
			// window title
			primaryStage.setTitle("Trace");
			
			// menu
			HBox menu = new HBox();
			
			// menu buttons
			Button create = new Button("Create Customer");
			Button edit = new Button("Edit Customer");
			Button delete = new Button("Delete Customer");
			//Button seg = new Button("Delete Customer");
			Button traceCus = new Button("Trace Customer");
			
			// 
			// button actions
			//
			create.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					// TODO Auto-generated method stub
					showCreateCustomerScreen();
					
				}
				
			});
			
			delete.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					// TODO Auto-generated method stub
					deleteCustomer();

				}

			});
			

			edit.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					// TODO Auto-generated method stub
					showEditScreen();

				}

			});
			
			// add buttons to window
			menu.getChildren().addAll(create, edit, delete, traceCus);

			
			// create and set border pane components
			root = new BorderPane();
			root.setPadding(new Insets(20, 20, 20, 20));
			root.setTop(menu);
			menu.setAlignment(Pos.CENTER);
			root.setCenter(setTable());
			
			// set up scene
			Scene scene = new Scene(root,550,500);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
			
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * Load customers from manager to table
	 */
	public ObservableList<Customer> loadCustomers(){
		ObservableList<Customer> obsCustomers = FXCollections.observableArrayList();
		for(Customer c : manager.getCustomerList())
		{
			obsCustomers.add(c);
		}
		return obsCustomers;
	}
	
	/*
	 * Shows Create Customer pop-up window
	 */
	public void showCreateCustomerScreen() {
		// error message
		final Label error = new Label();
		
		// set up stage
        Stage customerCreateScreen = new Stage();
        VBox box = new VBox();
        box.getChildren().add(error);
        box.setPadding(new Insets(10));
        box.setAlignment(Pos.CENTER);
        Label label = new Label("Create Customer");
        
        // set up text fields for user input
        TextField textName = new TextField();
        textName.setPromptText("Name");
        TextField textAge = new TextField();
        textAge.setPromptText("Age");
        TextField textEmail = new TextField();
        textEmail.setPromptText("Email");
        TextField textPhoneNum = new TextField();
        textPhoneNum.setPromptText("Phone Number");
        TextField textStreetAddr = new TextField();
        textStreetAddr.setPromptText("Street Address");
        TextField textCity = new TextField();
        textCity.setPromptText("City");
        TextField textState = new TextField();
        textState.setPromptText("State");
        TextField textZipcode = new TextField();
        textZipcode.setPromptText("Zipcode");
        
        // window buttons
        Button done = new Button("Done");
        Button cancel = new Button("Cancel");
        
        
        //
        // button actions
        //
        done.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				error.setText(null); // clear label
				
				// check to make sure there is valid input in each text field
				if(textName.getText() != null && textAge.getText() != null && 
						textEmail.getText() != null && textPhoneNum.getText() != null 
						&& textStreetAddr.getText() != null && !textName.getText().isEmpty() && !textAge.getText().isEmpty() && 
						!textEmail.getText().isEmpty() && !textPhoneNum.getText().isEmpty() 
						&& !textStreetAddr.getText().isEmpty() && textCity.getText() != null && textState != null && textZipcode != null 
						&& !textCity.getText().isEmpty() && !textState.getText().isEmpty() && !textZipcode.getText().isEmpty()) {
					
					// save text
					Address customerAddr = new Address(textStreetAddr.getText(), textCity.getText(), textState.getText(), textZipcode.getText());
					manager.addCustomer(new Customer(textName.getText(), Integer.parseInt(textAge.getText()),textEmail.getText(), textPhoneNum.getText(), customerAddr));
				
					// redisplay table
					root.setCenter(setTable());
					
					// clear text
					textName.clear();
        	        textAge.clear();
        	        textEmail.clear();
        	        textPhoneNum.clear();
        	        textStreetAddr.clear();
        	        textCity.clear();
        	        textState.clear();
        	        textZipcode.clear();
				}
				// there is missing/invalid info
				else {
					error.setText("Some text fields are empty/invalid");
					error.setTextFill(Color.INDIANRED);
				}
			}
			
		});
        
        cancel.setOnAction(new EventHandler<ActionEvent>() {

        	@Override
        	    public void handle(ActionEvent e) {
        	        textName.clear();
        	        textAge.clear();
        	        textEmail.clear();
        	        textPhoneNum.clear();
        	        textStreetAddr.clear();
        	        textCity.clear();
        	        textState.clear();
        	        textZipcode.clear();
        	        customerCreateScreen.close();
        	    }
        	});
		
        // combine components and create scene
        box.getChildren().add(label);
        box.getChildren().add(textName);
        box.getChildren().add(textAge);
        box.getChildren().add(textEmail);
        box.getChildren().add(textPhoneNum);
        box.getChildren().add(textStreetAddr);
        box.getChildren().add(textCity);
        box.getChildren().add(textState);
        box.getChildren().add(textZipcode);
        box.getChildren().add(done);
        box.getChildren().add(cancel);
        Scene scene = new Scene(box, 300, 400);
        customerCreateScreen.setScene(scene);
        customerCreateScreen.show();
    }
	
	/*
	 * Deletes selected customer
	 */
	public void deleteCustomer() {
		
		// allow selection if there are customers
		if(manager.getSize() != 0)
		{
			TableViewSelectionModel<Customer> selectionModel = customerTable.getSelectionModel();
			ObservableList<Customer> selectedItems = selectionModel.getSelectedItems();
			selectionModel.setSelectionMode(SelectionMode.SINGLE);
			manager.deleteCustomer(selectedItems.get(0)); // delete selected customer
			root.setCenter(setTable());	// refresh customer table

		}
		else {
			System.out.println("No customers selected");
		}
		
	}
	
	/*
	 * Shows Edit Customer pop-up window
	 */
	public void showEditScreen() {
		if(manager.getSize() != 0)
		{
			// user selects one customer to edit
			TableViewSelectionModel<Customer> selectionModel = customerTable.getSelectionModel();
			ObservableList<Customer> selectedItems = selectionModel.getSelectedItems();
			ObservableList<Integer> selectedIndices = selectionModel.getSelectedIndices();
			selectionModel.setSelectionMode(SelectionMode.SINGLE);
			Customer selectedCust = selectedItems.get(0);
			int selectedIndex = selectedIndices.get(0);
			Address customerAddr = selectedCust.getAddrObject();
			
			
			// set up window
			final Label error = new Label();
	        Stage editScreen = new Stage();
	        VBox box = new VBox();
	        box.getChildren().add(error);
	        box.setPadding(new Insets(10));
	        box.setAlignment(Pos.CENTER);
	 
	        Label label = new Label("Edit Customer");
	        
	        // set text fields to selected customer's current attributes
	        TextField textName = new TextField();
	        textName.setPromptText(selectedCust.getName());
	        TextField textAge = new TextField();
	        textAge.setPromptText(Integer.toString(selectedCust.getAge()));
	        TextField textEmail = new TextField();
	        textEmail.setPromptText(selectedCust.getEmail());
	        TextField textPhoneNum = new TextField();
	        textPhoneNum.setPromptText(selectedCust.getPhoneNum());
	        TextField textStreetAddr = new TextField();
	        textStreetAddr.setPromptText(customerAddr.getStreetAddr());
	        TextField textCity = new TextField();
	        textCity.setPromptText(customerAddr.getCity());
	        TextField textState = new TextField();
	        textState.setPromptText(customerAddr.getState());
	        TextField textZipcode = new TextField();
	        textZipcode.setPromptText(customerAddr.getZipcode());
	        
	        // buttons
	        Button done = new Button("Done");
	        Button cancel = new Button("Cancel");
	
	        //
	        // button actions
	        //
	        done.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					// TODO Auto-generated method stub
					error.setText(null); // clear label
					
					// change data if new text is entered
					if(textName.getText() != null && !textName.getText().isEmpty()) {
						selectedCust.setName(textName.getText());
					}
					if(textAge.getText() != null && !textAge.getText().isEmpty()) {
						selectedCust.setAge(Integer.parseInt(textAge.getText()));
					}
						
					if(textEmail.getText() != null && !textEmail.getText().isEmpty()) {
						selectedCust.setEmail(textEmail.getText());
					}
					if(textStreetAddr.getText() != null && !textStreetAddr.getText().isEmpty()) {
						customerAddr.setStreetAddr(textStreetAddr.getText());
					}
					
					if(textCity.getText() != null && !textCity.getText().isEmpty()) {
						customerAddr.setCity(textCity.getText());
					}
					if(textState.getText() != null && !textState.getText().isEmpty()) {
						customerAddr.setState(textState.getText());
					}
					if(textZipcode.getText() != null && !textZipcode.getText().isEmpty()) {
						customerAddr.setZipcode(textZipcode.getText());
					}
					if(textPhoneNum.getText() != null && !textPhoneNum.getText().isEmpty()) {
						selectedCust.setPhoneNum(textPhoneNum.getText());
					}
					
					selectedCust.setAddrObject(customerAddr);
					manager.setCustomer(selectedIndex, selectedCust);
						
				
						// redisplay table
						root.setCenter(setTable());
						
						// clear text
						textName.clear();
	        	        textAge.clear();
	        	        textEmail.clear();
	        	        textPhoneNum.clear();
	        	        textStreetAddr.clear();
	        	        textCity.clear();
	        	        textState.clear();
	        	        textZipcode.clear();
				}

				
			});
	        
	        // close window
	        cancel.setOnAction(new EventHandler<ActionEvent>() {

	        	@Override
	        	    public void handle(ActionEvent e) {
	        	        textName.clear();
	        	        textAge.clear();
	        	        textEmail.clear();
	        	        textPhoneNum.clear();
	        	        textStreetAddr.clear();
	        	        textCity.clear();
	        	        textState.clear();
	        	        textZipcode.clear();
	        	        editScreen.close();
	        	    }
	        	});
			
	        // combine components and set scene
	        box.getChildren().add(label);
	        box.getChildren().add(textName);
	        box.getChildren().add(textAge);
	        box.getChildren().add(textEmail);
	        box.getChildren().add(textPhoneNum);
	        box.getChildren().add(textStreetAddr);
	        box.getChildren().add(textCity);
	        box.getChildren().add(textState);
	        box.getChildren().add(textZipcode);
	        box.getChildren().add(done);
	        box.getChildren().add(cancel);
	        Scene scene = new Scene(box, 300, 400);
	        editScreen.setScene(scene);
	        editScreen.show();
			
			// refresh table
			root.setCenter(setTable());

		}
		else {
			System.out.println("No customers selected");
		}
		
	}
	
	/*
	 * Set table of customers
	 */
	public TableView<Customer> setTable() {
		// allow selection if there is are customers in the list
		if(manager.getSize() != 0)
		{
			TableViewSelectionModel<Customer> selectionModel = customerTable.getSelectionModel();
			ObservableList<Customer> selectedItems = selectionModel.getSelectedItems();
			selectionModel.setSelectionMode(SelectionMode.MULTIPLE);

		}
		
		// create table columns
		TableColumn<Customer, String> nameColumn = new TableColumn<>("Name");
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		TableColumn<Customer, Integer> ageColumn = new TableColumn<>("Age");
		ageColumn.setCellValueFactory(new PropertyValueFactory<>("age"));
		TableColumn<Customer, String> emailColumn = new TableColumn<>("Email");
		emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
		TableColumn<Customer, String> phoneNumColumn = new TableColumn<>("Phone Number");
		phoneNumColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNum"));
		TableColumn<Customer, String> addressColumn = new TableColumn<>("Address");
		addressColumn.setCellValueFactory(new PropertyValueFactory<>("addr"));
		
		// create table and set table elements
		customerTable = new TableView<>();
		customerTable.setItems(loadCustomers());
		customerTable.getColumns().addAll(nameColumn, ageColumn, emailColumn, phoneNumColumn, addressColumn);
		customerTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		
		return customerTable;
	}
	
	/*
	 * main method
	 */
	public static void main(String[] args) {
		manager = new CustomerManager();
		launch(args);
	}
}
