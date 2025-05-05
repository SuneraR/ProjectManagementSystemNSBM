package util;

import java.sql.DriverManager;

import com.sun.jdi.connect.spi.Connection;

public class DBconnection {
	
	 public static Connection getConnection() {
	        try {
	            Class.forName("com.mysql.cj.jdbc.Driver");
	            return (Connection) DriverManager.getConnection(
	                "jdbc:mysql://localhost:3306/projectmanager", "root", "Sunera6717$"
	            );
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return null;
	    }

}
