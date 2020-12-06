package application;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
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
import javafx.scene.text.Text;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.awt.List;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.ArrayList;

import Components.*;

public class Main extends Application {
	
	TableView<Customer> customerTable; // table of Customers
	ArrayList<Customer> backupList; // backup table for when we update customerTable
	TableView<Product> productTable; // TableView form of a customer's product history
	ArrayList<Product> productHistory; // holds a single customer's product history
	static CustomerManager manager; // instance of CustomerManager class and handles any customer interaction

	BorderPane root; // main GUI window

	public enum ErrorMessage{NO_SELECTION, EMPTY_TABLE}
	
	// main 
	public static void main(String[] args) {
		manager = new CustomerManager();
		launch(args);
	}
	
	@Override
	/*
	 * main window
	 */
	public void start(Stage primaryStage) {
		try {
			primaryStage.setTitle("Trace");
			HBox menu = new HBox();

			// menu buttons
			Button create = new Button("Create");
			Button edit = new Button("Edit");
			Button delete = new Button("Delete");
			Button segment = new Button("Segment");
			Button insights = new Button("Insights");
			Button traceCus = new Button("Trace");

			// Handle when "create" button pressed
			create.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					showCreateCustomerScreen();
				}
			});

			// Handle when "segment" button pressed
			segment.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					showSegmentCustomerScreen();
				}
			});

			// Handle when "insights" button pressed
			insights.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					showInsightsScreen();
				}
			});

			// Handle when "delete" button pressed"
			delete.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					deleteCustomer();
				}
			});

			// Handle when "trace" button pressed
			traceCus.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					showTraceScreen();

				}
			});

			// Handle when "edit" button pressed
			edit.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					showEditScreen();
				}
			});
			
			// adds and formats menu buttons
			menu.getChildren().addAll(create, edit, delete, segment, traceCus, insights);
			menu.setSpacing(20.0);
			menu.setStyle("-fx-padding: 5 10 8 10;");

			// create and set border pane components
			root = new BorderPane();
			root.setPadding(new Insets(20, 20, 20, 20));
			root.setTop(menu);
			menu.setAlignment(Pos.CENTER);
			root.setCenter(setCustomerTable());

			// set up scene
			Scene scene = new Scene(root, 800, 800);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	void showErrorPopup(ErrorMessage err) {
		Stage errorScreen = new Stage();
		VBox box = new VBox();
		box.setPadding(new Insets(10));
		box.setAlignment(Pos.CENTER);

		Label errorLabel = new Label();
		
		// create and format the error label and close button
		if (err.equals(ErrorMessage.EMPTY_TABLE)) {
			errorLabel.setText("Your table is empty! Add some customers and try again.");
		}
		else if (err.equals(ErrorMessage.NO_SELECTION)) {
			errorLabel.setText("You have not selected a customer. Select and try again!");
		}

		errorLabel.setTextFill(Color.INDIANRED);
		Button closeButton = new Button("Ok");
		
		// add label and button to the screen
		box.getChildren().addAll(errorLabel, closeButton);
		box.setSpacing(15);
		
		// initialize the scene
		Scene scene = new Scene(box, 400, 100);
		errorScreen.setScene(scene);
		errorScreen.show();
		
		// Handle when "close" button is pressed
		closeButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				errorScreen.close();
			}
		});
	}

	/*
	 * Load customers from manager to table
	 */
	public ObservableList<Customer> loadCustomers() {
		ObservableList<Customer> obsCustomers = FXCollections.observableArrayList();
		for (Customer c : manager.getCustomerList()) {
			obsCustomers.add(c);
		}
		return obsCustomers;
	}

	/*
	 * Load products from product history array to table
	 */
	public ObservableList<Product> loadProductHistory() {
		ObservableList<Product> obsProducts = FXCollections.observableArrayList();
		for (Product p : productHistory) {
			obsProducts.add(p);
		}
		return obsProducts;
	}

	/*
	 * Show insights screen
	 */
	public void showInsightsScreen() {
		if (manager.getSize() == 0) {
			showErrorPopup(ErrorMessage.EMPTY_TABLE);
			return;
		}
		Stage insightsScreen = new Stage();
		VBox box = new VBox();
		box.setPadding(new Insets(10));
		box.setAlignment(Pos.CENTER);

		InsightsGenerator insights = new InsightsGenerator(manager.getCustomerList());
		Label titleLabel = new Label("Insights");
		titleLabel.setTextFill(Color.INDIANRED);
		
		DecimalFormat df = new DecimalFormat("#.##");
		
		Label popProduct = new Label("Most popular product: " + insights.popularProduct().getName());
		Label averageAge = new Label("Average Age: " + df.format(insights.averageAge()));
		Label highestSpender = new Label("Highest Spender: " + insights.highestSpender().getName());
		Label averageSpending = new Label("Avearge Money Spent: " + df.format(insights.averageSpending()));

		box.getChildren().addAll(titleLabel, popProduct, averageAge, highestSpender, averageSpending);

		Scene scene = new Scene(box, 300, 200);
		insightsScreen.setScene(scene);
		insightsScreen.show();

	}

	/*
	 * shows the customer segmentation screen
	 */
	public void showSegmentCustomerScreen() {
		if (manager.getSize() == 0) {
			showErrorPopup(ErrorMessage.EMPTY_TABLE);
			return;
		}
		Stage segmentCustomerScreen = new Stage();
		VBox box = new VBox();
		box.setPadding(new Insets(10));
		box.setAlignment(Pos.CENTER);

		Segmenter s = new Segmenter(manager.getCustomerList()); // create a backup of the customer list
		backupList = s.makeBackupTable(manager.getCustomerList());

		Label titleLabel = new Label("Segment Customers");
		Button reset = new Button("Reset all");
		Button segmentButton = new Button("Segment!");

		// segment by age
		TextField minAge = new TextField();
		minAge.setPromptText("Min Age");
		TextField maxAge = new TextField();
		maxAge.setPromptText("Max Age");

		// segment by loyalty
		ChoiceBox<String> choiceBox = s.getLoyaltyChoiceBox();

		// segment by area code
		TextField areaCodeField = new TextField();
		areaCodeField.setPromptText("Area Code (3 Digits)");

		reset.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				manager.setCustomerList(backupList);
				root.setCenter(setCustomerTable());
				segmentCustomerScreen.close();
			}
		});

		segmentButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				
				// assign int values
				int minAgeInt = 0;
				if (minAge.getText() != null && !minAge.getText().isEmpty()) {
					minAgeInt = Integer.parseInt(minAge.getText());
				}
				int maxAgeInt;
				if (maxAge.getText() != null && !maxAge.getText().isEmpty()) {
					maxAgeInt = Integer.parseInt(maxAge.getText());
				} else {
					maxAgeInt = Integer.MAX_VALUE;
				}
				
				String requestedLoyalty = choiceBox.getValue(); // assign loyalty values
				
				// assign area code values
				int areaCode = -1;
				if (areaCodeField.getText() != null && !areaCodeField.getText().isEmpty()
						&& areaCodeField.getText().length() == 3) {
					areaCode = Integer.parseInt(areaCodeField.getText());
				}
				
				// delete everything from the table that doesn't fulfill the requirements
				ArrayList<Customer> newList = s.updateList(minAgeInt, maxAgeInt, requestedLoyalty, areaCode, manager.getCustomerList());
				manager.setCustomerList(newList);
				root.setCenter(setCustomerTable());
			}
		});

		box.getChildren().addAll(titleLabel, choiceBox, minAge, maxAge, areaCodeField, segmentButton, reset);

		Scene scene = new Scene(box, 300, 400);
		segmentCustomerScreen.setScene(scene);
		segmentCustomerScreen.show();
		
		segmentCustomerScreen.setOnCloseRequest(event -> {
			manager.setCustomerList(backupList);
			root.setCenter(setCustomerTable());
		});
	}

	/*
	 * Shows Create Customer pop-up window
	 */
	public void showCreateCustomerScreen() {
		
		final Label error = new Label(); // error label

		// set up stage
		Stage customerCreateScreen = new Stage();
		VBox box = new VBox();
		box.getChildren().add(error);
		box.setPadding(new Insets(10));
		box.setAlignment(Pos.CENTER);
		Label label = new Label("Create a new customer");

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
		Button cancel = new Button("Close");

		// when "create" clicked, validate text fields
		done.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				error.setText(null); // clear error label
				
				// check to make sure there is valid input in each text field
				if (manager.validateTextFields(textName, textAge, textEmail, textPhoneNum, textStreetAddr, textCity, textState, textZipcode)) {

					// save text
					Address customerAddr = new Address(textStreetAddr.getText(), textCity.getText(),
							textState.getText(), textZipcode.getText());
					manager.addCustomer(new Customer(textName.getText(), Integer.parseInt(textAge.getText()),
							textEmail.getText(), textPhoneNum.getText(), customerAddr));

					// redisplay table
					root.setCenter(setCustomerTable());

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

		// close button
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
		box.getChildren().addAll(label, textName, textAge, textEmail, textPhoneNum, textStreetAddr, textCity, textState, textZipcode, done, cancel);
		Scene scene = new Scene(box, 300, 400);
		customerCreateScreen.setScene(scene);
		customerCreateScreen.show();
	}

	/*
	 * Shows Trace Customer pop-up window
	 */
	public void showTraceScreen() {
		// set up stage
		Stage traceScreen = new Stage();
		VBox box = new VBox();
		box.setPadding(new Insets(10));
		box.setAlignment(Pos.CENTER);
		Label label = new Label("Trace Customer");

		// allow selection if there are customers
		Customer c = null;
		if (manager.getSize() != 0) {
			TableViewSelectionModel<Customer> selectionModel = customerTable.getSelectionModel();
			ObservableList<Customer> selectedItems = selectionModel.getSelectedItems();
			selectionModel.setSelectionMode(SelectionMode.SINGLE);

			if (!selectedItems.isEmpty()) {
				c = selectedItems.get(0);
				PurchaseHistory h = c.getPurchaseHistory();
				productHistory = h.getHistory();
			}
			else {
				showErrorPopup(ErrorMessage.NO_SELECTION);
				return;
			}
		}

		else {
			showErrorPopup(ErrorMessage.NO_SELECTION);
			return;
		}

		String s = c.getDemographic();
		Label label2 = new Label("Demographic: " + s);
		label2.setTextFill(Color.INDIANRED);

		String loyalty = c.getLoyalty();
		Label label3 = new Label("Customer Loyalty: " + loyalty);
		if (loyalty.equals("Golden Member")) {
			label3.setTextFill(Color.GOLD);
		} 
		else if (loyalty.equals("Silver Member")) {
			label3.setTextFill(Color.SILVER);
		} 
		else if (loyalty.equals("Bronze Member")) {
			label3.setTextFill(Color.BROWN);
		}

		box.getChildren().add(label);
		box.getChildren().add(label2);
		box.getChildren().add(label3);
		box.getChildren().add(setProductHistoryTable());

		Scene scene = new Scene(box, 300, 400);
		traceScreen.setScene(scene);
		traceScreen.show();

	}

	/*
	 * Deletes selected customer
	 */
	public void deleteCustomer() {

		// allow selection if there are customers
		if (manager.getSize() != 0) {
			TableViewSelectionModel<Customer> selectionModel = customerTable.getSelectionModel();
			ObservableList<Customer> selectedItems = selectionModel.getSelectedItems();
			selectionModel.setSelectionMode(SelectionMode.SINGLE);
			if (!selectedItems.isEmpty()) {
				manager.deleteCustomer(selectedItems.get(0)); // delete selected customer
			}
			else {
				showErrorPopup(ErrorMessage.NO_SELECTION);
			}
			root.setCenter(setCustomerTable()); // refresh customer table

		} else {
			showErrorPopup(ErrorMessage.EMPTY_TABLE);
		}

	}

	/*
	 * Shows Edit Customer pop-up window
	 */
	public void showEditScreen() {
		if (manager.getSize() != 0) {
			// user selects one customer to edit
			TableViewSelectionModel<Customer> selectionModel = customerTable.getSelectionModel();
			ObservableList<Customer> selectedItems = selectionModel.getSelectedItems();
			ObservableList<Integer> selectedIndices = selectionModel.getSelectedIndices();
			selectionModel.setSelectionMode(SelectionMode.SINGLE);
			
			Customer selectedCust;
			int selectedIndex;
			Address customerAddr;
			if (!selectedItems.isEmpty()) {
				selectedCust = selectedItems.get(0);
				selectedIndex = selectedIndices.get(0);
				customerAddr = selectedCust.getAddrObject();
			}
			else {
				showErrorPopup(ErrorMessage.NO_SELECTION);
				return;
			}
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
					error.setText(null); // clear label

					// change data if new text is entered
					if (manager.validateSingleTextField(textName)) {
						selectedCust.setName(textName.getText());
					}
					if (manager.validateSingleTextField(textAge)) {
						selectedCust.setAge(Integer.parseInt(textAge.getText()));
					}
					if (manager.validateSingleTextField(textEmail)) {
						selectedCust.setEmail(textEmail.getText());
					}
					if (manager.validateSingleTextField(textStreetAddr)) {
						customerAddr.setStreetAddr(textStreetAddr.getText());
					}
					if (manager.validateSingleTextField(textCity)) {
						customerAddr.setCity(textCity.getText());
					}
					if (manager.validateSingleTextField(textState)) {
						customerAddr.setState(textState.getText());
					}
					if (manager.validateSingleTextField(textZipcode)) {
						customerAddr.setZipcode(textZipcode.getText());
					}
					if (manager.validateSingleTextField(textPhoneNum)) {
						selectedCust.setPhoneNum(textPhoneNum.getText());
					}

					selectedCust.setAddrObject(customerAddr);
					manager.setCustomer(selectedIndex, selectedCust);

					// redisplay table
					root.setCenter(setCustomerTable());

					// clear all text
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
			box.getChildren().addAll(label, textName, textAge, textEmail, textPhoneNum, textStreetAddr, textCity, textState, textZipcode, done, cancel);
			Scene scene = new Scene(box, 300, 400);
			editScreen.setScene(scene);
			editScreen.show();

			// refresh table
			root.setCenter(setCustomerTable());

		} else {
			showErrorPopup(ErrorMessage.NO_SELECTION);
		}

	}

	/*
	 * Set table of product history
	 */
	public TableView<Product> setProductHistoryTable() {
		// allow selection if there is are customers in the list
		if (manager.getSize() != 0) {
			TableViewSelectionModel<Customer> selectionModel = customerTable.getSelectionModel();
			ObservableList<Customer> selectedItems = selectionModel.getSelectedItems();
			selectionModel.setSelectionMode(SelectionMode.MULTIPLE);

		}

		// create table columns
		TableColumn<Product, String> nameColumn = new TableColumn<>("Name");
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		TableColumn<Product, Integer> priceColumn = new TableColumn<>("Price");
		priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

		// create table and set table elements
		productTable = new TableView<>();
		productTable.setItems(loadProductHistory());
		productTable.getColumns().addAll(nameColumn, priceColumn);
		productTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

		return productTable;
	}

	/*
	 * Set table of customers
	 */
	public TableView<Customer> setCustomerTable() {
		// allow selection if there is are customers in the list
		if (manager.getSize() != 0) {
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

		updateDatabase(); // updates the file database
		return customerTable;
	}

	/*
	 * Updates text file database
	 */
	public static void updateDatabase() {
		try {
			ArrayList<String> lines = new ArrayList<>();
			ArrayList<Customer> cList = manager.getCustomerList();

			// loop through customer list to add data
			for (Customer c : cList) {
				lines.add("Name: " + c.getName());
				lines.add("Age: " + Integer.toString(c.getAge()));
				lines.add("Email: " + c.getEmail());
				lines.add("Phone: " + c.getPhoneNum());
				lines.add("Address: " + c.getAddr());
				lines.add("Demographic: " + c.getDemographic());
				lines.add("Loyalty: " + c.getLoyalty());
				lines.add("");
			}

			// write data to file
			Path file = Paths.get("customerDatabase.txt");
			Files.write(file, lines, StandardCharsets.UTF_8);
		} catch (IOException e) {
			System.out.println("An error occured when updating the database");
			e.printStackTrace();
		}

	}
}
