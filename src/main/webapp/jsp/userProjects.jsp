<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Users projects</title>
</head>
<body>
<c:forEach var="project" items="${projects}">
    <div>
        <h3>${project.name}</h3>
        <p>${project.description}</p>
        <a href="ViewProject?projectId=${project.projectId}">View Details</a>
    </div>
</c:forEach>

</body>
</html>