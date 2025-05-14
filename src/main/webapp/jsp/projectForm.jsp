<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Project Management</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container mt-4">
        <h2 class="mb-4">
            <c:choose>
                <c:when test="${not empty project.project_id}">Edit Project</c:when>
                <c:otherwise>Create New Project</c:otherwise>
            </c:choose>
        </h2>
        
        <form action="../ProjectServlet" method="post">
            <input type="hidden" name="action" value="${not empty project.project_id ? 'update' : 'create'}">
            <input type="hidden" name="project_id" value="${project.project_id}">
            
            <div class="mb-3">
                <label for="name" class="form-label">Project Name</label>
                <input type="text" class="form-control" id="name" name="name" 
                       value="${project.name}" required maxlength="150">
            </div>
            
            <div class="mb-3">
                <label for="description" class="form-label">Description</label>
                <textarea class="form-control" id="description" name="description" 
                          rows="4">${project.description}</textarea>
            </div>
            
            <div class="row mb-3">
                <div class="col-md-6">
                    <label for="start_date" class="form-label">Start Date</label>
                    <input type="date" class="form-control" id="start_date" name="start_date" 
                           value="${project.start_date}">
                </div>
                <div class="col-md-6">
                    <label for="end_date" class="form-label">End Date</label>
                    <input type="date" class="form-control" id="end_date" name="end_date" 
                           value="${project.end_date}">
                </div>
            </div>
            
            <div class="d-grid gap-2 d-md-flex justify-content-md-end">
                <button type="submit" class="btn btn-primary me-md-2">
                    <c:choose>
                        <c:when test="${not empty project.project_id}">Update Project</c:when>
                        <c:otherwise>Create Project</c:otherwise>
                    </c:choose>
                </button>
                <a href="ProjectServlet?action=list" class="btn btn-secondary">Cancel</a>
            </div>
        </form>
    </div>
    
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        // Date validation
        document.querySelector('form').addEventListener('submit', function(e) {
            const startDate = document.getElementById('start_date').value;
            const endDate = document.getElementById('end_date').value;
            
            if (startDate && endDate && endDate < startDate) {
                alert('End date cannot be before start date');
                e.preventDefault();
            }
        });
    </script>
</body>
</html>