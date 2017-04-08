package application;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;

import controller.AddWarehouseController;
import controller.LogInController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
//import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.LogInModel;

public class mainApp extends Application {

	private Stage primaryStage;
	public LogInController loginController;

	@Override
	public void start(Stage primaryStage){

		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("Cargo Booking by TechGeekD");
		initWelcomeLayout();
		//initRootLayout();
		//initHomeLayout();
	}

	public void initRootLayout(Boolean isAdmin){  // Login
		try{

			FXMLLoader loader = new FXMLLoader();
			String fxmlDocPath = "src/LogIn.fxml";
			String fxmlDocPathWH = "src/LoginWH.fxml";

			if(isAdmin.equals(true)){
				FileInputStream fxmlStream = new FileInputStream(fxmlDocPath);
				AnchorPane root = (AnchorPane) loader.load(fxmlStream);
				Scene scene = new Scene(root);
				Stage stage = new Stage();
				stage.setTitle("NaviCargo Booking System");
				stage.centerOnScreen();
				stage.setScene(scene);
				stage.setResizable(false);
				stage.show();
			} else {
				FileInputStream fxmlStream = new FileInputStream(fxmlDocPathWH);
				AnchorPane root = (AnchorPane) loader.load(fxmlStream);
				Scene scene = new Scene(root);
				Stage stage = new Stage();
				stage.setTitle("NaviCargo Booking System");
				stage.centerOnScreen();
				stage.setScene(scene);
				stage.setResizable(false);
				stage.show();
			}


		} catch(IOException e){
			e.printStackTrace();
		}
	}

	public void initWelcomeLayout(){
		try{
			FXMLLoader loader = new FXMLLoader();
			String fxmlDocPath = "src/welcomee.fxml";
			FileInputStream fxmlStream = new FileInputStream(fxmlDocPath);

			AnchorPane welcomeroot = (AnchorPane) loader.load(fxmlStream);
			Scene scene = new Scene(welcomeroot);
			Stage stage = new Stage();
			stage.setTitle("NaviCargo Booking System");
			stage.setScene(scene);
			stage.setResizable(false);
			stage.show();

		} catch(IOException e){
			e.printStackTrace();
		}
	}

	public void initHomeLayout(String username) throws SQLException{
		try{

			FXMLLoader loader = new FXMLLoader();
			String fxmlDocPath = "src/home.fxml";
			FileInputStream fxmlStream = new FileInputStream(fxmlDocPath);

			AnchorPane homeroot = (AnchorPane) loader.load(fxmlStream);

			Scene scene = new Scene(homeroot);
			Stage stage = new Stage();
			stage.setTitle("NaviCargo Booking System");
			stage.setScene(scene);
			stage.setResizable(false);
			stage.show();

			LogInModel loginModel = new LogInModel();
			AddWarehouseController awc = new AddWarehouseController();
			awc.showAlert("Welcome Administrator, Sir " + loginModel.fetchName(username) + ".");

		} catch(IOException e){
			e.printStackTrace();
		}
	}

	public void initWHLayout(){
		try{
			FXMLLoader loader = new FXMLLoader();
			String fxmlDocPath = "src/whhome.fxml";
			FileInputStream fxmlStream = new FileInputStream(fxmlDocPath);

			AnchorPane homeroot = (AnchorPane) loader.load(fxmlStream);

			Scene scene = new Scene(homeroot);
			Stage stage = new Stage();
			stage.setTitle("NaviCargo Booking System");
			stage.centerOnScreen();
			stage.setScene(scene);
			stage.setResizable(false);
			stage.show();

		} catch(IOException e){
			e.printStackTrace();
		}
	}

	 public Stage getPrimaryStage() {
	        return primaryStage;
	    }

		public static void main(String[] args) {
			launch(args);
		}

}
