package dao;

import java.sql.*;
import java.util.*;
import model.Comment;
import util.DBconnection;

public class CommentDAO {
    
    public void insertComment(Comment comment) throws SQLException {
        String sql = "INSERT INTO comments (task_id, user_id, comment) VALUES (?, ?, ?)";
        try (Connection conn = DBconnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setInt(1, comment.getTaskId());
            stmt.setInt(2, comment.getUserId());
            stmt.setString(3, comment.getComment());
            stmt.executeUpdate();
            
            // Retrieve auto-generated comment_id if needed
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    comment.setCommentId(generatedKeys.getInt(1));
                }
            }
        }
    }

    public List<Comment> getCommentsByTaskId(int taskId) throws SQLException {
        List<Comment> comments = new ArrayList<>();
        String sql = "SELECT * FROM comments WHERE task_id = ? ORDER BY timestamp DESC";
        
        try (Connection conn = DBconnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, taskId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Comment comment = new Comment();
                comment.setCommentId(rs.getInt("comment_id"));
                comment.setTaskId(rs.getInt("task_id"));
                comment.setUserId(rs.getInt("user_id"));
                comment.setComment(rs.getString("comment"));
                comment.setTimestamp(rs.getTimestamp("timestamp"));
                
                comments.add(comment);
            }
        }
        return comments;
    }
    
    // Optional: Get comments by user ID
    public List<Comment> getCommentsByUserId(int userId) throws SQLException {
        List<Comment> comments = new ArrayList<>();
        String sql = "SELECT * FROM comments WHERE user_id = ? ORDER BY timestamp DESC";
        
        try (Connection conn = DBconnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Comment comment = new Comment();
                comment.setCommentId(rs.getInt("comment_id"));
                comment.setTaskId(rs.getInt("task_id"));
                comment.setUserId(rs.getInt("user_id"));
                comment.setComment(rs.getString("comment"));
                comment.setTimestamp(rs.getTimestamp("timestamp"));
                
                comments.add(comment);
            }
        }
        return comments;
    }
    
    // Optional: Delete a comment
    public boolean deleteComment(int commentId) throws SQLException {
        String sql = "DELETE FROM comments WHERE comment_id = ?";
        
        try (Connection conn = DBconnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, commentId);
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        }
    }
}