<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>My Projects</title>
    <style>
        .project-list {
            margin-bottom: 40px;
        }
        .project {
            padding: 15px;
            border: 1px solid #ddd;
            margin-bottom: 10px;
        }
        h2 {
            color: #333;
        }
    </style>
</head>
<body>

    <h1>My Projects</h1>

    <!-- Projects where user has tasks -->
    <div class="project-list">
        <h2>Projects with Assigned Tasks</h2>
        <c:choose>
            <c:when test="${not empty tasksProjects}">
                <c:forEach var="project" items="${tasksProjects}">
                    <div class="project">
                        <h3>${project.name}</h3>
                        <p>${project.description}</p>
                        <a href="ViewProject?projectId=${project.projectId}">View Project</a>
                    </div>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <p>You have no tasks assigned in any project.</p>
            </c:otherwise>
        </c:choose>
    </div>

    <!-- Projects managed by user -->
    <div class="project-list">
        <h2>Projects You Manage</h2>
        <c:choose>
            <c:when test="${not empty manageProjects}">
                <c:forEach var="project" items="${manageProjects}">
                    <div class="project">
                        <h3>${project.name}</h3>
                        <p>${project.description}</p>
                        <a href="ViewProject?projectId=${project.projectId}">Manage Project</a>
                    </div>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <p>You are not managing any projects.</p>
            </c:otherwise>
        </c:choose>
    </div>

</body>
</html>
