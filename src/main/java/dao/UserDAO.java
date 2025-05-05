package dao;

import java.sql.*;

import model.User;
import util.DBconnection;

public class UserDAO {
	private Connection conn;
    public UserDAO() throws SQLException {
        this.conn = DBconnection.getConnection();
    }


    public boolean validateUser(String username, String password) throws SQLException {
        boolean status = false;
        String query = "SELECT * FROM users WHERE name = ? AND password = ?";

        try (PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();

            status = rs.next();
        }
        return status;
    }
    
    
    public boolean addUser(User user) {
        String query = "INSERT INTO users (name, email, password, role) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = DBconnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            
            // The User class constructor already validates the role
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getRole().getValue());
            
          return ps.executeUpdate()>0;
            
         
        } catch (SQLException e) {
            if (e.getMessage().contains("Data truncated")) {
                System.err.println("Invalid role value: " + user.getRole());
            }
            e.printStackTrace();
        }
        return false;
    }
    
    // Method to check if email already exists
    public boolean emailExists(String email) {
        String query = "SELECT id FROM users WHERE email = ?";
        
        try (Connection conn = DBconnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            return rs.next();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


	
}