<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Sign Up</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .error { color: red; }
        .signup-container {
            max-width: 500px;
            margin: 50px auto;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 0 20px rgba(0, 0, 0, 0.1);
        }
        .form-label {
            font-weight: 500;
        }
    </style>
</head>
<body class="bg-light">
    <div class="container">
        <div class="signup-container bg-white">
            <h2 class="text-center mb-4">Sign Up</h2>
            <form action="/ProjectManagementSystem/SignupServlet" method="post">
                <div class="mb-3">
                    <label for="username" class="form-label">Username:</label>
                    <input type="text" class="form-control" id="username" name="username" required>
                </div>
                <div class="mb-3">
                    <label for="email" class="form-label">Email:</label>
                    <input type="email" class="form-control" id="email" name="email" required>
                </div>
                <div class="mb-3">
                    <label for="password" class="form-label">Password:</label>
                    <input type="password" class="form-control" id="password" name="password" required>
                </div>
                <div class="mb-3">
                    <label for="confirmPassword" class="form-label">Confirm Password:</label>
                    <input type="password" class="form-control" id="confirmPassword" name="confirmPassword" required>
                </div>
                <div class="d-grid mb-3">
                    <button type="submit" class="btn btn-primary">Sign Up</button>
                </div>
                <p class="error text-center">${error}</p>
            </form>
            <p class="text-center mt-3">Already have an account? <a href="login.jsp" class="text-decoration-none">Login</a></p>
        </div>
    </div>

    <!-- Bootstrap JS Bundle with Popper -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>