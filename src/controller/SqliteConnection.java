package controller;
import java.sql.*;

public class SqliteConnection {
    public static Connection Connector(){
        try
        {
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection("jdbc:sqlite:Cargoo.sqlite");
            return conn;
        }catch(Exception e)
        {
        	System.out.print(e);
            return null;
        }
    }
}