<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Edit Task</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .edit-task-container {
            max-width: 600px;
            margin: 30px auto;
            padding: 30px;
            border-radius: 8px;
            box-shadow: 0 0 15px rgba(0,0,0,0.1);
            background-color: white;
        }
        body {
            background-color: #f8f9fa;
            padding-top: 20px;
        }
        .form-label {
            font-weight: 500;
        }
    </style>
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <div class="container-fluid">
         <a class="navbar-brand" href="#"> 
        <img alt="Logo" src="${pageContext.request.contextPath}/images/logo.png" class="img-fluid" style="width: 80px; height: auto;" />
        </a>
        <div class="collapse navbar-collapse">
            <ul class="navbar-nav ms-auto">
                <li class="nav-item">
                    <a class="nav-link" href="projectForm.jsp">Create Project</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="../ProjectServlet">All Projects</a>
                </li>
            </ul>
        </div>
    </div>
</nav>
<div class="container">
    <div class="edit-task-container">
        <h2 class="mb-4">Edit Task</h2>
        
        <form method="post" action="EditTaskServlet">
            <input type="hidden" name="taskId" value="${task.taskId}">
            
            <div class="mb-3">
                <label for="title" class="form-label">Title</label>
                <input type="text" class="form-control" id="title" name="title" 
                       value="${task.title}" required>
            </div>
            
            <div class="mb-3">
                <label for="description" class="form-label">Description</label>
                <textarea class="form-control" id="description" name="description" 
                          rows="4">${task.description}</textarea>
            </div>
            
            <div class="mb-3">
                <label for="status" class="form-label">Status</label>
                <select class="form-select" id="status" name="status">
                    <option value="pending" ${task.status == 'pending' ? 'selected' : ''}>Pending</option>
                    <option value="in progress" ${task.status == 'in progress' ? 'selected' : ''}>In Progress</option>
                    <option value="completed" ${task.status == 'completed' ? 'selected' : ''}>Completed</option>
                </select>
            </div>
            
            <div class="mb-4">
                <label for="deadline" class="form-label">Deadline</label>
                <input type="date" class="form-control" id="deadline" 
                       name="deadline" value="${task.deadline}">
            </div>
            
            <div class="d-flex justify-content-between">
                <button type="submit" class="btn btn-primary">
                    <i class="bi bi-check-circle"></i> Update Task
                </button>
                <a href="javascript:history.back()" class="btn btn-outline-secondary">
                    <i class="bi bi-arrow-left"></i> Cancel
                </a>
            </div>
        </form>
    </div>
</div>

<!-- Bootstrap Icons -->
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.0/font/bootstrap-icons.css">
<!-- Bootstrap JS Bundle with Popper -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>