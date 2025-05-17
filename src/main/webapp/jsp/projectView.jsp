<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Project Details</title>
    <!-- Bootstrap CSS CDN -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
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
<div class="container my-5">
    <h2 class="text-center mb-4">Project Details</h2>

    <table class="table table-bordered">
        <tr>
            <th>Name</th>
            <td>${project.name}</td>
        </tr>
        <tr>
            <th>Description</th>
            <td>${project.description}</td>
        </tr>
        <tr>
            <th>Start Date</th>
            <td>${project.startDate}</td>
        </tr>
        <tr>
            <th>End Date</th>
            <td>${project.endDate}</td>
        </tr>
    </table>

    <h3 class="text-center mt-5">Tasks</h3>

    <c:if test="${not empty tasks}">
        <div class="table-responsive">
            <table class="table table-striped table-bordered mt-3">
                <thead class="table-light">
                <tr>
                    <th>Title</th>
                    <th>Description</th>
                    <th>Status</th>
                    <th>Deadline</th>
                    <th>Assigned To</th>
                    <th>Comments</th>
                    <c:if test="${currentUser.id == project.managerId}">
                        <th>Actions</th>
                    </c:if>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="task" items="${tasks}">
                    <tr>
                        <td>${task.title}</td>
                        <td>${task.description}</td>
                        <td>
                            <c:choose>
                                <c:when test="${currentUser.id == task.assignedTo}">
                                    <form action="SaveTaskServlet" method="post" class="d-flex gap-2">
                                        <input type="hidden" name="taskId" value="${task.taskId}" />
                                        <select name="status" class="form-select form-select-sm w-auto">
                                            <option value="pending" ${task.status == 'pending' ? 'selected' : ''}>Pending</option>
                                            <option value="in_progress" ${task.status == 'in_progress' ? 'selected' : ''}>In Progress</option>
                                            <option value="completed" ${task.status == 'completed' ? 'selected' : ''}>Completed</option>
                                        </select>
                                        <button type="submit" class="btn btn-sm btn-primary">Update</button>
                                    </form>
                                </c:when>
                                <c:otherwise>
                                    ${task.status}
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td>${task.deadline}</td>
                        <td>${task.assignedToName}</td>
                        <td>
                            <a href="ViewCommentsServlet?taskId=${task.taskId}" class="btn btn-sm btn-info text-white">Comments</a>
                        </td>
                        <c:if test="${currentUser.id == project.managerId}">
                            <td>
                                <a class="btn btn-sm btn-warning" href="EditTaskServlet?taskId=${task.taskId}">Edit</a>
                                <a class="btn btn-sm btn-danger" href="DeleteTaskServlet?taskId=${task.taskId}&projectId=${project.projectId}" onclick="return confirm('Delete this task?');">Delete</a>
                            </td>
                        </c:if>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </c:if>

    <c:if test="${empty tasks}">
        <div class="alert alert-info mt-4">No tasks found for this project.</div>
    </c:if>

    <c:if test="${currentUser.id == project.managerId}">
        <a class="btn btn-success mt-3" href="CreateTaskServlet?projectId=${project.projectId}">Add Task</a>
    </c:if>

    <br><br>
    <a class="btn btn-secondary" href="ProjectServlet">← Back to Project List</a>
</div>

<!-- Bootstrap JS Bundle (optional if you use Bootstrap JS features) -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
