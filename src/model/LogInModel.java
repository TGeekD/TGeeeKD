package model;
import java.sql.*;

import controller.SqliteConnection;
//import javafx.fxml.Initializable;

public class LogInModel{

	Connection connection;

	public LogInModel()
    {
        connection = SqliteConnection.Connector();
        if(connection == null) {
        	System.out.print("Connection not successful");
        	System.exit(1);
        }
    }

	public Connection connection(){
		return connection;
	}


	public boolean isDbConnected()
    {
        try{
           return !connection.isClosed();
        }catch(SQLException e)
        {
            e.printStackTrace();
            return false;
        }

    }

	public boolean isLogin(String user, String pass) throws SQLException{

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String query = "select username,password from adminLogin where username = ? and password = ?";

		try{
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1,user);
			preparedStatement.setString(2,pass);

			resultSet = preparedStatement.executeQuery();

			if(resultSet.next())
			{
				return true;
			}else{
				return false;
			}
		}catch (Exception e){
			return false;
		}finally {
			preparedStatement.close();
			resultSet.close();
		}

	}


	public String fetchName(String username) throws SQLException{

		String name = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String query = "SELECT fullname FROM adminLogin where username= ? ";
		try{
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, username);
			resultSet = preparedStatement.executeQuery();
			name = resultSet.getString("fullname");

		} catch (SQLException e){
			e.printStackTrace();
			System.out.println("Failed to Fetch Name.");
		} finally {
			preparedStatement.close();
			resultSet.close();
		}
			return name;
	}
	
	public boolean isWhLogin(String user, String pass) throws SQLException{

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String query = "select username,password from warehouseLogin where username = ? and password = ?";

		try{
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1,user);
			preparedStatement.setString(2,pass);

			resultSet = preparedStatement.executeQuery();

			if(resultSet.next())
			{
				return true;
			}else{
				return false;
			}
		}catch (Exception e){
			return false;
		}finally {
			preparedStatement.close();
			resultSet.close();
		}

	}
}

	