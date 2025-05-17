package model;

import java.time.LocalDate;

public class Project {
    private int projectId;
    private String name;
    private String description;
    private int managerId;  // Now required since manager is always the creator
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean isCompleted;

    // Constructors
    public Project() {
    }

    public Project(int projectId, String name, String description, int managerId, 
                  LocalDate startDate, LocalDate endDate) {
        this.projectId = projectId;
        this.name = name;
        this.description = description;
        this.managerId = managerId;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    // Getters and Setters
    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getManagerId() {
        return managerId;
    }

    public void setManagerId(int managerId) {
        this.managerId = managerId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
    public boolean getIsCompleted() {
    	return isCompleted;
    }
    public void setIsCompleted(boolean isCompleted) {
    	this.isCompleted=isCompleted;
    }

    // Utility methods
    public boolean isActive() {
        LocalDate today = LocalDate.now();
        return (startDate != null && !today.isBefore(startDate)) && 
               (endDate == null || !today.isAfter(endDate));
    }

    
    @Override
    public String toString() {
        return "Project{" +
                "projectId=" + projectId +
                ", name='" + name + '\'' +
                ", managerId=" + managerId +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }


}