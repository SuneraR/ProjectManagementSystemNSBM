package model;

import java.time.LocalDate;
import java.util.Date;

public class Task {
    private int taskId;
    private int projectId;
    private int assignedTo; // Can be 0/null if unassigned
    private String title;
    private String description;
    private String status; // "pending", "in_progress", "completed"
    private LocalDate deadline;
    private String projectName; 
    private String assignedToName;// Optional: used for display on dashboard

    // Constructors
    public Task() {}

    public Task(int taskId, int projectId, int assignedTo, String title, String description, String status, LocalDate deadline) {
        this.taskId = taskId;
        this.projectId = projectId;
        this.assignedTo = assignedTo;
        this.title = title;
        this.description = description;
        this.status = status;
        this.deadline = deadline;
    }

    // Getters and Setters
    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public int getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(int assignedTo) {
        this.assignedTo = assignedTo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate localDate) {
       this.deadline = localDate;
    }
 
   

    public String getAssignedToName() {
        return assignedToName;
    }

    public void setAssignedToName(String assignedToName) {
        this.assignedToName = assignedToName;
    }


    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }




}
