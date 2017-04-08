package model;

import java.sql.Connection;
//import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
//import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import com.sun.javafx.collections.SetAdapterChange;

import controller.AddWarehouseController;
import controller.LogInController;

//import javax.swing.JOptionPane;


public class AddWarehouseModel
{
	Connection connection;
	public LogInController loginController;
	public LogInModel loginModel;


	public void orderSubmit(String orderid, Integer quantity, String warehouse) throws SQLException{
		AddWarehouseController adwhController = new AddWarehouseController();
		loginModel = new LogInModel();
		connection = loginModel.connection();
		LocalTimeDate date = new LocalTimeDate();
		PreparedStatement preparedStatement = null;
		String query = "INSERT INTO cargo (orderID, quantity, warehousePlace, dateCreated, stamp) VALUES (?,?,?,?,?)";
		preparedStatement = connection.prepareStatement(query);
		preparedStatement.setString(1, orderid);
		preparedStatement.setInt(2, quantity);
		preparedStatement.setString(3, warehouse);
			reduceCapacity(quantity, warehouse, connection);
		preparedStatement.setString(4, date.getDate());
		preparedStatement.setString(5, "uploaded by Admin."); //SAMPLE JUST FOR ADMINISTRATOR

		preparedStatement.executeUpdate();
		System.out.println("Succesfully added Order.");
		JOptionPane.showMessageDialog(null,
			    "Successfully added order to Database.");
	}

	public void reduceCapacity(Integer size, String warehouse, Connection connection) throws SQLException{


			Integer capacity, newCapacity;
			PreparedStatement ps = null;
			ResultSet rs = null;
			String query = "SELECT remCapacity FROM warehouse WHERE warehousePlace=?";
			ps = connection.prepareStatement(query);
			ps.setString(1, warehouse);

			rs = ps.executeQuery();
			rs.next();
			capacity = rs.getInt("remCapacity");
			newCapacity = capacity - size;
			System.out.println("Capacity is " + newCapacity);

			// Apply newCapacity to database
			String query2 = "UPDATE warehouse SET remCapacity = ? WHERE warehousePlace = ?";

			ps = connection.prepareStatement(query2);
			ps.setInt(1, newCapacity); // remCapacity
			ps.setString(2, warehouse); // warehousePlace
			ps.executeUpdate();
			System.out.println("Successful Capacity Reduce.");
	}

	public void isSubmit(String wareId, String wPlace, String superName, String contactNo, Integer capacity) throws ClassNotFoundException
	{
		try{
			LocalTimeDate time = new LocalTimeDate();
			loginController = new LogInController();
			loginModel = new LogInModel();
			connection=loginModel.connection();
			PreparedStatement preparedStatement= null;
			String query;

			query = "INSERT INTO warehouse (warehouseID, warehousePlace, capacity, remCapacity, svrName, contactNo, stamp) VALUES(?,?,?,?,?,?,?)";
			preparedStatement = connection.prepareStatement(query);

			preparedStatement.setString(1,wareId);
			preparedStatement.setString(2,wPlace);
			preparedStatement.setInt(3, capacity);
			preparedStatement.setInt(4, capacity);
			preparedStatement.setString(5,superName);
			preparedStatement.setString(6,contactNo);
			preparedStatement.setString(7, time.getTime());

			preparedStatement.executeUpdate();
			System.out.println("Successful Data Entry.");

			loginController.showAlert("Warehouse ID: " + wareId + " from location " + wPlace + " has been successfully added to Database.");
		}catch (SQLException e){
			e.printStackTrace();
			loginController.showAlert("Failed to insert to Warehouse Database.");
		}
	}

	public void shiftCargo(String orderid, String warehouse, Integer quantity){
		loginModel = new LogInModel();
		connection = loginModel.connection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		String query = "UPDATE ";
		//ps = connection.preparedStatement(query);

	}
}
