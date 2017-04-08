package controller;
import java.io.IOException;
import java.sql.*;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import javax.swing.JOptionPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import model.AddWarehouseModel;
import model.LogInModel;
import application.mainApp;

public class AddWarehouseController
{
	Connection connection;

	public mainApp main;
	public AddWarehouseController whController;

    @FXML JFXTextField wareId;
	@FXML JFXTextField place;
	@FXML JFXTextField superName;
    @FXML JFXTextField contactNo;
    @FXML JFXTextField cap;
    @FXML JFXButton logoutbtn;
    static Label welcomeUserField;
    @FXML ImageView header;


    @FXML JFXTextField orderidField;
    @FXML JFXTextField quantityField;
    @FXML ListView<String> warehouselist = new ListView<String>();
    @FXML JFXButton btnOrder;

    @FXML ComboBox<String> orderIDBox = new ComboBox<String>();
    @FXML ComboBox<String> shiftToBox = new ComboBox<String>();
    @FXML JFXTextField whPlaceSC;
    @FXML JFXTextField quantitySC;
    @FXML JFXTextField capacitySC;
    @FXML JFXTextField remCapacitySC;
    @FXML JFXButton fetch1btn;
    @FXML JFXButton fetch2btn;

	AddWarehouseModel addWareModel = new AddWarehouseModel();
	LogInModel loginModel;

	//table start
	

	// table end
	public void shiftTo(ActionEvent e) throws SQLException, ClassNotFoundException{

		String orderid, shiftto, warehouse;
		Integer quantity, capacity, remCapacity;

		Class.forName("org.sqlite.JDBC");
        Connection connection = DriverManager.getConnection("jdbc:sqlite:Cargoo.sqlite");

		orderid = orderIDBox.getSelectionModel().getSelectedItem();
		shiftto = shiftToBox.getSelectionModel().getSelectedItem();
		warehouse = whPlaceSC.getText();
		quantity = Integer.parseInt(quantitySC.getText());
		capacity = Integer.parseInt(capacitySC.getText());
		remCapacity = Integer.parseInt(remCapacitySC.getText());

		PreparedStatement ps = null;
		String query = "UPDATE cargo SET warehousePlace = ? WHERE orderid = ?";

		ps = connection.prepareStatement(query);
		ps.setString(1,shiftto);
		ps.setString(2, orderid);
		ps.executeUpdate();
		ps.close();

		// Fetch remaining capacity for old warehouse THEN add reduced capacity

		String query2 = "SELECT remCapacity FROM warehouse WHERE warehousePlace=?";
		ResultSet rs = null;
		ps = connection.prepareStatement(query2);
		ps.setString(1, warehouse);
		rs = ps.executeQuery();
		rs.next();
			Integer current = rs.getInt("remCapacity");
			Integer newCapacity = current + quantity;

		String query3 = "UPDATE warehouse SET remCapacity=? WHERE warehousePlace=?";
		ps = connection.prepareStatement(query3);
		ps.setInt(1, newCapacity);
		ps.setString(2, warehouse);
		ps.executeUpdate();
		System.out.println("Old Warehouse: " + warehouse + " New Capacity: " + newCapacity);

		ps.close();


		//Fetch remaining capacity on new shifted warehouse then subtract capacity

		String query4 = "SELECT remCapacity FROM warehouse WHERE warehousePlace=?";
		ps = connection.prepareStatement(query4);
		ps.setString(1, shiftto);
		rs = ps.executeQuery();
		rs.next();
			Integer currentS = rs.getInt("remCapacity");
			Integer newCapacityS = currentS - quantity;

		String query5 = "UPDATE warehouse SET remCapacity=? WHERE warehousePlace = ?";
		ps = connection.prepareStatement(query5);
		ps.setInt(1, newCapacityS);
		ps.setString(2, shiftto);
		ps.executeUpdate();
		System.out.println("New Warehouse: " + shiftto + " .New Capacity: " + newCapacity);

		JOptionPane.showMessageDialog(null, "Successfully Shifted Database. OrderID: " + orderid + ". New Warehouse: " + shiftto);
		ps.close();
		connection.close();
	}

	public void disablefetchbtns(){
		fetch1btn.setDisable(true);
		fetch2btn.setDisable(true);
	}

	public void updateFields() throws SQLException{
		disablefetchbtns();
		loginModel = new LogInModel();
		connection = loginModel.connection();
		ResultSet rs = null;
		PreparedStatement ps = null;
		String query="SELECT orderID FROM cargo";

		ps = connection.prepareStatement(query);
		rs = ps.executeQuery();

		ObservableList<String> orderidlist = FXCollections.observableArrayList();
		while(rs.next()){
			String current = rs.getString("orderID");
			orderidlist.add(current);
			System.out.println("Fetching OrderID: " + current);
		}
		orderIDBox.setPromptText("Select OrderID");
		orderIDBox.setItems(orderidlist);

		String query2 = "SELECT warehousePlace FROM warehouse";

		ps=connection.prepareStatement(query2);
		rs = ps.executeQuery();

		ObservableList<String> warehouselist = FXCollections.observableArrayList();
		while(rs.next()){
			String current2 = rs.getString("warehousePlace");
			warehouselist.add(current2);
			System.out.println("Warehouse Place: " + current2);
		}
		shiftToBox.setPromptText("Select Warehouse");
		shiftToBox.setItems(warehouselist);

			fetch1btn.setDisable(false);
			fetch2btn.setDisable(false);
		ps.close();
		connection.close();
	}

	public void fetchValueOrder(ActionEvent e) throws SQLException{

		String orderid = orderIDBox.getSelectionModel().getSelectedItem();
		System.out.println("Order Id from fetchValue order is: " + orderid);

		LogInModel loginModel = new LogInModel();
		Connection connection = loginModel.connection();

		PreparedStatement ps= null;
		ResultSet rs = null;
		String query = "SELECT warehousePlace,quantity FROM cargo WHERE orderID = ?";

		ps=connection.prepareStatement(query);
		ps.setString(1, orderid);
		rs = ps.executeQuery();
		rs.next();
			String warehouse = rs.getString("warehousePlace");
			Integer quantity = rs.getInt("quantity");

		whPlaceSC.setText(warehouse);
		quantitySC.setText(quantity.toString());
		ps.close();


	}

	public void fetchWarehouse(ActionEvent e) throws SQLException{
		String warehouse = shiftToBox.getSelectionModel().getSelectedItem();
		System.out.println("Warehouse Place from fetchValue order is: " + warehouse);

		LogInModel loginModel = new LogInModel();
		Connection connection = loginModel.connection();

		PreparedStatement ps= null;
		ResultSet rs = null;
		String query = "SELECT capacity,remCapacity FROM warehouse WHERE warehousePlace = ?";

		ps=connection.prepareStatement(query);
		ps.setString(1, warehouse);
		rs = ps.executeQuery();
		rs.next();
			Integer capacity = rs.getInt("capacity");
			Integer remCapacity = rs.getInt("remCapacity");

		capacitySC.setText(capacity.toString());
		remCapacitySC.setText(remCapacity.toString());
		ps.close();
	}

	public AddWarehouseController(){

	}

	public void showAlert(String info)
	{
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setHeaderText(info);
		alert.show();
	}

	public void Submit(ActionEvent e) throws ClassNotFoundException{

		try{
			String id = wareId.getText();
			String loc = place.getText();
			String svName = superName.getText();
			String no = contactNo.getText();


			if(id.equals("")){
				showAlert("Warehouse ID must not be left blank.");
			} if(loc.equals("")){
				showAlert("Place field must not be left blank.");
			} if(svName.equals("")){
				showAlert("Supervisor Name must not be left blank.");
			} if(no.equals("")){
				showAlert("Contact Number must not be left blank.");
			} else {
				Integer capacity = Integer.parseInt(cap.getText());
				addWareModel.isSubmit(id, loc, svName, no, capacity);
				cfWarehouse();
			}
		} catch(NumberFormatException exc) {
			exc.printStackTrace();
			System.out.println("Number Exception on Field.");
			showAlert("Warehouse Capacity must not ne left blank.");
		}


	}

	public void dispatch(){
		Stage stage = (Stage) logoutbtn.getScene().getWindow();
	    stage.close();
	    main = new mainApp();
	    main.initWelcomeLayout();
	    showAlert("Admin has been Logged Out.");

	}

	public void welcomeUser(String username){
			System.out.println("@WelcomeUser");
			welcomeUserField.setText("Welcome Mr. " + username);
		}

	public void awesome(){
		showAlert("NaViCargo developed by TechGeek'D Team. \n \nKent Joshua Sadullo\nMartin Lorenze Reyes\nJohn Carlo Gonzales\nAgrielio De Lazo.");
		welcomeUserField.setText("FUCK YES");;
	}

	public void cfWarehouse(){
		wareId.setText("");
		place.setText("");
		superName.setText("");
		contactNo.setText("");
		cap.setText("");
	}

	public void submitOrder(ActionEvent e) throws IOException, SQLException{
		String orderid;
		Integer quantity;
		String warehouse;
		orderid = orderidField.getText();
		quantity = Integer.parseInt(quantityField.getText());
		warehouse = getList();
		addWareModel.orderSubmit(orderid, quantity, warehouse);
	}


	public String getList() throws SQLException{

		loginModel = new LogInModel();
		connection = loginModel.connection();

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String query = "SELECT warehousePlace FROM warehouse";
		preparedStatement = connection.prepareStatement(query);

		resultSet = preparedStatement.executeQuery();
		ObservableList<String> whPlaces = FXCollections.observableArrayList();
		while(resultSet.next()){
			String current = resultSet.getString("warehousePlace");
			whPlaces.add(current);
			System.out.println(current);
		}
		warehouselist.setItems(whPlaces);
		String warehouse;
		warehouselist.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		warehouse = warehouselist.getSelectionModel().getSelectedItem();
		preparedStatement.close();
		return warehouse;
	}


}
