<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Create Task</title>
</head>
<body>
    <h2>Create Task for Project: ${project.name}</h2>
    <c:if test="${not empty errorMessage}">
        <p style="color: red; font-weight: bold;">${errorMessage}</p>
    </c:if>

    <form action="CreateTaskServlet" method="post">
        <input type="hidden" name="projectId" value="${project.projectId}" />

        <label for="title">Title:</label><br>
        <input type="text" name="title" required /><br><br>

        <label for="description">Description:</label><br>
        <textarea name="description" rows="4" cols="50"></textarea><br><br>

        <label for="assigned_to">Assign to:</label><br>
           <c:choose>
    <c:when test="${not empty users}">
        <select name="assigned_to" required>
            <c:forEach var="user" items="${users}">
							<option value="${user.id}">${user.username}
								(${user.email})</option>
						</c:forEach>
        </select>
    </c:when>
    <c:otherwise>
        <p>No users available to assign this task.</p>
    </c:otherwise>
</c:choose>

        </select><br><br>

        <label for="deadline">Deadline:</label><br>
        <input type="date" name="deadline" /><br><br>

        <input type="submit" value="Create Task" />
    </form>

    <a href="ViewProject?projectId=${project.projectId}">Back to Project</a>
</body>
</html>
