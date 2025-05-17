<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Project Management</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Bootstrap Icons -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.0/font/bootstrap-icons.css">
    <style>
        .project-form-container {
            max-width: 800px;
            margin: 30px auto;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 0 20px rgba(0,0,0,0.1);
            background-color: white;
        }
        body {
            background-color: #f8f9fa;
            padding-top: 20px;
        }
        .form-label {
            font-weight: 500;
        }
        .status-badge {
            font-size: 0.9rem;
            padding: 5px 10px;
            border-radius: 20px;
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
    <div class="project-form-container">
        <div class="d-flex justify-content-between align-items-center mb-4">
            <h2>
                <c:choose>
                    <c:when test="${not empty project.project_id}">
                        <i class="bi bi-pencil-square"></i> Edit Project
                    </c:when>
                    <c:otherwise>
                        <i class="bi bi-plus-circle"></i> Create New Project
                    </c:otherwise>
                </c:choose>
            </h2>
            <c:if test="${not empty project.project_id}">
                <span class="status-badge bg-${project.status == 'active' ? 'success' : 'secondary'}">
                    ${project.status}
                </span>
            </c:if>
        </div>
        
        <form action="../ProjectServlet" method="post" class="needs-validation" novalidate>
            <input type="hidden" name="action" value="${not empty project.project_id ? 'update' : 'create'}">
            <input type="hidden" name="project_id" value="${project.project_id}">
            
            <div class="mb-4">
                <label for="name" class="form-label">Project Name *</label>
                <input type="text" class="form-control" id="name" name="name" 
                       value="${project.name}" required maxlength="150">
                <div class="invalid-feedback">
                    Please provide a project name.
                </div>
            </div>
            
            <div class="mb-4">
                <label for="description" class="form-label">Description</label>
                <textarea class="form-control" id="description" name="description" 
                          rows="5">${project.description}</textarea>
            </div>
            
            <div class="row mb-4">
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
            
            <c:if test="${not empty project.project_id}">
                <div class="mb-4">
                    <label class="form-label">Status</label>
                    <div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" name="status" id="status-active" 
                                   value="active" ${project.status == 'active' ? 'checked' : ''}>
                            <label class="form-check-label" for="status-active">Active</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" name="status" id="status-completed" 
                                   value="completed" ${project.status == 'completed' ? 'checked' : ''}>
                            <label class="form-check-label" for="status-completed">Completed</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" name="status" id="status-archived" 
                                   value="archived" ${project.status == 'archived' ? 'checked' : ''}>
                            <label class="form-check-label" for="status-archived">Archived</label>
                        </div>
                    </div>
                </div>
            </c:if>
            
            <div class="d-flex justify-content-between">
                <div>
                    <c:if test="${not empty project.project_id}">
                        <button type="button" class="btn btn-outline-danger me-2" data-bs-toggle="modal" 
                                data-bs-target="#deleteModal">
                            <i class="bi bi-trash"></i> Delete
                        </button>
                    </c:if>
                </div>
                <div>
                    <a href="../ProjectServlet?action=list" class="btn btn-secondary me-2">
                        <i class="bi bi-x-circle"></i> Cancel
                    </a>
                    <button type="submit" class="btn btn-primary">
                        <c:choose>
                            <c:when test="${not empty project.project_id}">
                                <i class="bi bi-check-circle"></i> Update Project
                            </c:when>
                            <c:otherwise>
                                <i class="bi bi-plus-circle"></i> Create Project
                            </c:otherwise>
                        </c:choose>
                    </button>
                </div>
            </div>
        </form>
    </div>
</div>

<!-- Delete Confirmation Modal -->
<c:if test="${not empty project.project_id}">
<div class="modal fade" id="deleteModal" tabindex="-1" aria-labelledby="deleteModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header bg-danger text-white">
                <h5 class="modal-title" id="deleteModalLabel">Confirm Deletion</h5>
                <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                Are you sure you want to delete this project? This action cannot be undone.
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                <a href="ProjectServlet?action=delete&project_id=${project.project_id}" class="btn btn-danger">
                    <i class="bi bi-trash"></i> Delete
                </a>
            </div>
        </div>
    </div>
</div>
</c:if>

<!-- Bootstrap JS Bundle with Popper -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<script>
// Form validation
(function() {
    'use strict';
    const forms = document.querySelectorAll('.needs-validation');
    
    Array.prototype.slice.call(forms)
        .forEach(function(form) {
            form.addEventListener('submit', function(event) {
                if (!form.checkValidity()) {
                    event.preventDefault();
                    event.stopPropagation();
                }
                
                // Date validation
                const startDate = document.getElementById('start_date').value;
                const endDate = document.getElementById('end_date').value;
                
                if (startDate && endDate && endDate < startDate) {
                    alert('End date cannot be before start date');
                    event.preventDefault();
                }
                
                form.classList.add('was-validated');
            }, false);
        });
})();
</script>
</body>
</html>