<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Login</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Bootstrap Icons -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.0/font/bootstrap-icons.css">
    <style>
        .login-container {
            max-width: 400px;
            margin: 100px auto;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 0 20px rgba(0,0,0,0.1);
            background-color: white;
        }
        body {
            background-color: #f8f9fa;
        }
        .form-control:focus {
            border-color: #86b7fe;
            box-shadow: 0 0 0 0.25rem rgba(13, 110, 253, 0.25);
        }
    </style>
</head>
<body>
<div class="container">
    <div class="login-container">
        <h2 class="text-center mb-4">Login</h2>
        
        <form action="../LoginServlet" method="post">
            <div class="mb-3">
                <label for="username" class="form-label visually-hidden">Username</label>
                <div class="input-group">
                    <span class="input-group-text"><i class="bi bi-person-fill"></i></span>
                    <input type="text" class="form-control" id="username" name="username" 
                           placeholder="Username" required>
                </div>
            </div>
            
            <div class="mb-4">
                <label for="password" class="form-label visually-hidden">Password</label>
                <div class="input-group">
                    <span class="input-group-text"><i class="bi bi-lock-fill"></i></span>
                    <input type="password" class="form-control" id="password" name="password" 
                           placeholder="Password" required>
                </div>
            </div>
            
            <c:if test="${not empty param.errorMessage}">
                <div class="alert alert-danger mb-3" role="alert">
                    
                </div>
            </c:if>
            
            <div class="d-grid mb-3">
                <button type="submit" class="btn btn-primary btn-lg">
                    <i class="bi bi-box-arrow-in-right"></i> Login
                </button>
            </div>
            
            <div class="text-center">
                <a href="signup.jsp" class="text-decoration-none">
                    <i class="bi bi-person-plus"></i> Create an account
                </a>
            </div>
        </form>
    </div>
</div>

<!-- Bootstrap JS Bundle with Popper -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>