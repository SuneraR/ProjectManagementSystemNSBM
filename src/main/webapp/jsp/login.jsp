<%@ page language="java" %>
<html>
<head><title>Login</title></head>
<body>
    <form action="../LoginServlet" method="post">
        <input type="text" name="username" placeholder="Username" required /><br/>
        <input type="password" name="password" placeholder="Password" required /><br/>
        <input type="submit" value="Login" />
        <p style="color:red;">
            <%= request.getParameter("errorMessage") != null ? "Invalid credentials" : "" %>
        </p>
    </form>
    <a href="jsp/signup.jsp">Signup</a>
</body>
</html>