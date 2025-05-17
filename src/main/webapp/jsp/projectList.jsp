<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>My Projects</title>
    <!-- Bootstrap 5 CDN -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />
</head>
<body class="bg-light">
<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <div class="container-fluid">
        <a class="navbar-brand" href="#"> <img alt="Logo" src="${pageContext.request.contextPath}/images/logo.png" class="img-fluid" style="width: 80px; height: auto;" /></a>
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

<div class="container py-5">
    <h1 class="mb-5 text-center text-primary">My Projects</h1>

    <!-- Projects where user has tasks -->
    <div class="mb-5">
        <h2 class="mb-4 text-secondary">Projects with Assigned Tasks</h2>
        <c:choose>
            <c:when test="${not empty tasksProjects}">
                <div class="row">
                    <c:forEach var="project" items="${tasksProjects}">
                        <div class="col-md-6 mb-4">
                            <div class="card shadow-sm h-100">
                                <div class="card-body">
                                     <h5 class="card-title">
                                    ${project.name}
                                       <span class="badge bg-${project.isCompleted ? 'success' : 'warning'} float-end">
        										${project.isCompleted ? 'Completed' : 'In Progress'}
   									 </span>
                                    </h5>
                                    <p class="card-text">${project.description}</p>
                                    <a href="ViewProject?projectId=${project.projectId}" class="btn btn-primary">View Project</a>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </c:when>
            <c:otherwise>
                <div class="alert alert-warning">You have no tasks assigned in any project.</div>
            </c:otherwise>
        </c:choose>
    </div>

    <!-- Projects managed by user -->
    <div>
        <h2 class="mb-4 text-secondary">Projects You Manage</h2>
        <c:choose>
            <c:when test="${not empty manageProjects}">
                <div class="row">
                    <c:forEach var="project" items="${manageProjects}">
                        <div class="col-md-6 mb-4">
                            <div class="card shadow-sm h-100">
                                <div class="card-body">
                                    <h5 class="card-title">
                                    ${project.name}
                                       <span class="badge bg-${project.isCompleted ? 'success' : 'warning'} float-end">
        										${project.isCompleted ? 'Completed' : 'In Progress'}
   									 </span>
                                    </h5>
                                    <p class="card-text">${project.description}</p>
                                    <a href="ViewProject?projectId=${project.projectId}" class="btn btn-success">Manage Project</a>
                                    <a href="DeleteProjectServlet?projectId=${project.projectId}" onclick="return confirm('Delete this project?');">Delete project</a>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </c:when>
            <c:otherwise>
                <div class="alert alert-warning">You are not managing any projects.</div>
            </c:otherwise>
        </c:choose>
    </div>
</div>

<!-- Bootstrap JS Bundle (Optional for interactivity) -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
