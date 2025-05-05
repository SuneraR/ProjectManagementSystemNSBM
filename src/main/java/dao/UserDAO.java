package dao;

import java.sql.*;

import util.DBconnection;

public class UserDAO {
	Connection conn = (Connection) DBconnection.getConnection();

    public boolean validateUser(String username, String password) throws SQLException {
        boolean status = false;
        String query = "SELECT * FROM users WHERE username = ? AND password = ?";

        try (PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();

            status = rs.next();
        }
        return status;
    }
}