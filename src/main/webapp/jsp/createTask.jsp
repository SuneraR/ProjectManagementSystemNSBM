<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Create Task</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Bootstrap Icons -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.0/font/bootstrap-icons.css">
    <style>
        .form-container {
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
    <div class="form-container">
        <h2 class="mb-4">Create Task for Project: ${project.name}</h2>
        
        <c:if test="${not empty errorMessage}">
            <div class="alert alert-danger" role="alert">
                ${errorMessage}
            </div>
        </c:if>

        <form action="CreateTaskServlet" method="post">
            <input type="hidden" name="projectId" value="${project.projectId}" />

            <div class="mb-3">
                <label for="title" class="form-label">Title</label>
                <input type="text" class="form-control" id="title" name="title" required>
            </div>

            <div class="mb-3">
                <label for="description" class="form-label">Description</label>
                <textarea class="form-control" id="description" name="description" rows="4"></textarea>
            </div>

            <div class="mb-3">
                <label for="assigned_to" class="form-label">Assign to</label>
                <c:choose>
                    <c:when test="${not empty users}">
                        <select class="form-select" id="assigned_to" name="assigned_to" required>
                            <c:forEach var="user" items="${users}">
                                <option value="${user.id}">${user.username} (${user.email})</option>
                            </c:forEach>
                        </select>
                    </c:when>
                    <c:otherwise>
                        <div class="alert alert-warning">No users available to assign this task.</div>
                    </c:otherwise>
                </c:choose>
            </div>

            <div class="mb-4">
                <label for="deadline" class="form-label">Deadline</label>
                <input type="date" class="form-control" id="deadline" name="deadline">
            </div>

            <div class="d-flex justify-content-between">
                <button type="submit" class="btn btn-primary">
                    <i class="bi bi-plus-circle"></i> Create Task
                </button>
                <a href="ViewProject?projectId=${project.projectId}" class="btn btn-outline-secondary">
                    <i class="bi bi-arrow-left"></i> Back to Project
                </a>
            </div>
        </form>
    </div>
</div>

<!-- Bootstrap JS Bundle with Popper -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>