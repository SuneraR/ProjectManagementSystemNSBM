<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Project Details</title>
    <style>
        .container {
            width: 80%;
            margin: auto;
        }

        h2, h3 {
            text-align: center;
        }

        table {
            border-collapse: collapse;
            width: 100%;
            margin-top: 20px;
        }

        th, td {
            border: 1px solid #ddd;
            padding: 10px;
        }

        th {
            background-color: #f4f4f4;
        }

        .btn {
            padding: 6px 10px;
            background-color: #007bff;
            color: white;
            border-radius: 3px;
            text-decoration: none;
        }

        .btn:hover {
            background-color: #0056b3;
        }

        .back-link {
            margin-top: 20px;
            display: inline-block;
        }
    </style>
</head>
<body>

<div class="container">
    <h2>Project Details</h2>

    <table>
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

    <h3>Tasks</h3>

    <c:if test="${not empty tasks}">
        <table>
            <thead>
            <tr>
                <th>Title</th>
                <th>Description</th>
                <th>Status</th>
                <th>Deadline</th>
                <th>Assigned To</th>
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
                    
       <td>             	<c:choose>
    <c:when test="${currentUser.id == task.assignedTo}">
      <form action="SaveTaskServlet" method="post">
    <input type="hidden" name="taskId" value="${task.taskId}" />

    <select name="status">
        <option value="pending" ${task.status == 'pending' ? 'selected' : ''}>Pending</option>
        <option value="in_progress" ${task.status == 'in_progress' ? 'selected' : ''}>In Progress</option>
        <option value="completed" ${task.status == 'completed' ? 'selected' : ''}>Completed</option>
    </select>

    <button type="submit">Update Status</button>
</form>

    </c:when>
    <c:otherwise>
        ${task.status}
    </c:otherwise>
</c:choose>
</td>
                    <td>${task.deadline}</td>
                    <td>${task.assignedToName}</td> <!-- assume you populate this field in the servlet -->
                     <c:if test="${currentUser.id == project.managerId}">
                    <td>
                            <a class="btn" href="EditTaskServlet?taskId=${task.taskId}">Edit</a>

                            <a class="btn" href="DeleteTaskServlet?taskId=${task.taskId}" onclick="return confirm('Delete this task?');">Delete</a>
                    </td>
                        </c:if>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </c:if>

    <c:if test="${empty tasks}">
        <p>No tasks found for this project.</p>
    </c:if>

    <c:if test="${currentUser.id == project.managerId}">
        <a class="btn" href="CreateTaskServlet?projectId=${project.projectId}">Add Task</a>
    </c:if>

    <br>
    <a class="back-link" href="ProjectListServlet">← Back to Project List</a>
</div>

</body>
</html>
