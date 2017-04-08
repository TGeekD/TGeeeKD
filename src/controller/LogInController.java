package controller;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import model.User;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import application.mainApp;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import model.LogInModel;

public class LogInController implements Initializable{

	LogInModel loginModel = new LogInModel();
	@FXML JFXTextField userNameField;
	@FXML JFXPasswordField userPasswordField;
	@FXML Button adminBtn;
	@FXML Button whBtn;
	@FXML Button btnLogin;
	@FXML public Label loginlabel;


	public User user;

	mainApp main = new mainApp ();


	public void showAlert(String info)
	{
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setHeaderText(info);
		alert.show();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources){

	}

	public void Login (ActionEvent event)
	{
		try{
			user = new User();
			user.setUsername(userNameField.getText());
			user.setPassword(userPasswordField.getText());

		if(loginModel.isLogin(user.getUsername(), user.getPassword()))
		{
			Stage stage = (Stage) btnLogin.getScene().getWindow();
		    stage.close();
			main.initHomeLayout(user.getUsername());

		}else{
			showAlert("Username and password is incorrect!");
		}
		}catch(SQLException e){
			showAlert("Unable to reach Database.");
			e.printStackTrace();
		}

	}
	
	public void whLogin (ActionEvent event)
	{
		try{
			user = new User();
			user.setUsername(userNameField.getText());
			user.setPassword(userPasswordField.getText());

		if(loginModel.isWhLogin(user.getUsername(), user.getPassword()))
		{
			Stage stage = (Stage) btnLogin.getScene().getWindow();
		    stage.close();
			main.initWHLayout();

		}else{
			showAlert("Username and password is incorrect!");
		}
		}catch(SQLException e){
			showAlert("User or password is Incorrect.");
			e.printStackTrace();
		}

	}
// OPTIONAL

	public void showLogin(){
		Stage stage = (Stage) adminBtn.getScene().getWindow();
	    stage.close();
	    main.initRootLayout(true);
	}

	public void showLoginWH(){
		Stage stage = (Stage) whBtn.getScene().getWindow();
	    stage.close();
	    main.initRootLayout(false);
	}

	public void showUDevelopment(){
		showAlert("Warehouse Module under development.");
	}






}
