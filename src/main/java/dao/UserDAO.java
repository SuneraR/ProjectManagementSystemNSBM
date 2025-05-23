package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import model.User;
import util.DBconnection;

public class UserDAO {
    private Connection conn;

    public UserDAO() throws SQLException {
        this.conn = DBconnection.getConnection();
    }

    public boolean validateUser(String username, String passwordHash) throws SQLException {
        String query = "SELECT 1 FROM users WHERE name = ? AND password = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, username);
            ps.setString(2, passwordHash);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    public boolean addUser(User user) throws SQLException {
        String query = "INSERT INTO users (name, email, password, role) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPasswordHash());
            ps.setString(4, user.getRole().getValue());

            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                return false;
            }

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    user.setId(generatedKeys.getInt(1));
                }
            }
            return true;
        }
    }

    public boolean emailExists(String email) throws SQLException {
        String query = "SELECT 1 FROM users WHERE email = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    public void close() throws SQLException {
        if (conn != null && !conn.isClosed()) {
            conn.close();
        }
    }
    public User getUserByUsernameAndPassword(String username, String passwordHash) throws SQLException {
        String sql = "SELECT user_id, name, email, password, role FROM users WHERE name = ? AND password = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, passwordHash);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    // Assuming User constructor: User(id, name, email, passwordHash, role)
                    // Or that User has appropriate setters for all these fields.
                    return new User(
                        rs.getInt("user_id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("password"), // This is the password hash from DB
                        User.Role.fromString(rs.getString("role"))
                    );
                }
            }
        }
        return null; // login failed
    }
    
    public User getUserByUsername(String username) throws SQLException {
        String sql = "SELECT user_id, name, email, password, role FROM users WHERE name = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String passwordHash = rs.getString("password");
                    // Check if password hash exists
                    if (passwordHash == null || passwordHash.isEmpty()) {
                        // Return null if password hash is not found, as per requirement
                        return null; 
                    }
                    
                    return new User(
                        rs.getInt("user_id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        passwordHash,
                        User.Role.fromString(rs.getString("role"))
                    );
                }
            }
        }
        return null;
    }
    
 
        
        
        public List<User> getAllManagers() throws SQLException {
            String sql = "SELECT user_id, name, email, password, role FROM users WHERE role = 'manager'";
            List<User> managers = new ArrayList<>();
            
            try (PreparedStatement stmt = conn.prepareStatement(sql);
                 ResultSet rs = stmt.executeQuery()) {
                
                while (rs.next()) {
                    // Assuming User constructor: User(id, name, email, passwordHash, role)
                    // Or that User has appropriate setters for all these fields.
                    User manager = new User(
                        rs.getInt("user_id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("password"), // This is the password hash from DB
                        User.Role.fromString(rs.getString("role"))
                    );
                    managers.add(manager);
                }
            }
            return managers;
        
    }
        
        public List<User> getUsersByRole(String role) throws SQLException {
            List<User> users = new ArrayList<>();
            String sql = "SELECT user_id, name, email, password, role FROM users WHERE role = ?";

            try ( PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, role);
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        // Assuming User constructor: User(id, name, email, passwordHash, role)
                        // Or that User has appropriate setters for all these fields.
                        User user = new User(
                            rs.getInt("user_id"),
                            rs.getString("name"),
                            rs.getString("email"),
                            rs.getString("password"), // This is the password hash from DB
                            User.Role.fromString(rs.getString("role"))
                        );
                        users.add(user);
                    }
                }
            }
            return users;
        }

}