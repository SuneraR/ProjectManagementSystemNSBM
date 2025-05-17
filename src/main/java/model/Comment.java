package model;

import java.sql.Timestamp;

public class Comment {
    private int commentId;
    private int taskId;
    private int userId;
    private String comment;
    private Timestamp timestamp;

    // Default constructor
    public Comment() {
    }

    // Constructor with all fields except auto-incremented commentId
    public Comment(int taskId, int userId, String comment, Timestamp timestamp) {
        this.taskId = taskId;
        this.userId = userId;
        this.comment = comment;
        this.timestamp = timestamp;
    }

    // Constructor with all fields
    public Comment(int commentId, int taskId, int userId, String comment, Timestamp timestamp) {
        this.commentId = commentId;
        this.taskId = taskId;
        this.userId = userId;
        this.comment = comment;
        this.timestamp = timestamp;
    }

    // Getters and Setters
    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "commentId=" + commentId +
                ", taskId=" + taskId +
                ", userId=" + userId +
                ", comment='" + comment + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}