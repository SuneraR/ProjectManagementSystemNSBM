<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Sign Up</title>
    <style>
        .error { color: red; }
    </style>
</head>
<body>
    <h2>Sign Up Form</h2>
    <form action="/ProjectManagementSystem/SignupServlet" method="post">
        <div>
            <label>Username:</label>
            <input type="text" name="username" required>
        </div>
        <div>
            <label>Email:</label>
            <input type="email" name="email" required>
        </div>
        <div>
            <label>Password:</label>
            <input type="password" name="password" required>
        </div>
        <div>
            <label>Confirm Password:</label>
            <input type="password" name="confirmPassword" required>
        </div>
        <div>
            <input type="submit" value="Sign Up">
        </div>
    </form>
    <p class="error">${error}</p>
    <p>Already have an account? <a href="login.jsp">Login</a></p>
</body>
</html>