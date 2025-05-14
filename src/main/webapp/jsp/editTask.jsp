<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<form method="post" action="EditTaskServlet">
    <input type="hidden" name="taskId" value="${task.taskId}">
    
    <label>Title:</label>
    <input type="text" name="title" value="${task.title}" required><br>

    <label>Description:</label>
    <textarea name="description">${task.description}</textarea><br>

    <label>Status:</label>
    <select name="status">
        <option value="pending" ${task.status == 'pending' ? 'selected' : ''}>Pending</option>
        <option value="in progress" ${task.status == 'in progress' ? 'selected' : ''}>In Progress</option>
        <option value="completed" ${task.status == 'completed' ? 'selected' : ''}>Completed</option>
    </select><br>

    <label>Deadline:</label>
    <input type="date" name="deadline" value="${task.deadline}"><br>

    <button type="submit">Update Task</button>
</form>

</body>
</html>