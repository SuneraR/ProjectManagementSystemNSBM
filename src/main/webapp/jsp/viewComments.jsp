<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Comments</title>
    <!-- Bootstrap 5 CDN -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .chat-box {
            display: flex;
            flex-direction: column-reverse;
            gap: 10px;
            max-height: 400px;
            overflow-y: auto;
            scroll-behavior: smooth;
        }

        .chat-message {
            max-width: 70%;
            padding: 10px 15px;
            border-radius: 15px;
        }

        .left {
            align-self: flex-start;
            background-color: #f1f1f1;
        }

        .right {
            align-self: flex-end;
            background-color: #d1ecf1;
        }

        .modal-content textarea {
            resize: vertical;
        }
    </style>
</head>
<body class="bg-light">
<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <div class="container-fluid">
         <a class="navbar-brand" href="#"> 
        <img alt="Logo" src="${pageContext.request.contextPath}/images/logo.png" class="img-fluid" style="width: 80px; height: auto;" />
        </a>
        <div class="collapse navbar-collapse">
            <ul class="navbar-nav ms-auto">
                <li class="nav-item">
                    <a class="nav-link" href="jsp/projectForm.jsp">Create Project</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="ProjectServlet">All Projects</a>
                </li>
            </ul>
        </div>
    </div>
</nav>
<div class="container my-5">
    <div class="card shadow">
        <div class="card-header bg-primary text-white">
            <h4 class="mb-0">Task Comments</h4>
        </div>
        <div class="card-body">
            <div class="chat-box mb-4">
                <c:choose>
                    <c:when test="${hasComments}">
                        <c:forEach var="comment" items="${comments}">
                            <div class="chat-message ${comment.userId == sessionScope.user.id ? 'right' : 'left'}">
                                <div class="text-muted small mb-1">
                                    <strong>User #${comment.userId}</strong> - ${comment.timestamp}
                                </div>
                                <div>${comment.comment}</div>
                            </div>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <p class="text-center text-muted">No comments yet. Be the first to comment!</p>
                    </c:otherwise>
                </c:choose>
            </div>
            <div class="text-center">
                <button id="addCommentBtn" class="btn btn-success">Add Comment</button>
            </div>
        </div>
    </div>
</div>

<!-- Modal -->
<div class="modal" tabindex="-1" id="commentModal">
    <div class="modal-dialog">
        <div class="modal-content">
            <form id="commentForm" action="AddCommentServlet" method="post">
                <div class="modal-header">
                    <h5 class="modal-title">Add New Comment</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <input type="hidden" name="taskId" value="${param.taskId}">
                    <input type="hidden" name="userId" value="${sessionScope.user.id}">
                    <div class="mb-3">
                        <label for="comment" class="form-label">Comment</label>
                        <textarea id="comment" name="comment" rows="4" class="form-control" required placeholder="Enter your comment..."></textarea>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="submit" class="btn btn-primary">Submit</button>
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                </div>
            </form>
        </div>
    </div>
</div>

<!-- Bootstrap JS Bundle (with Popper) -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

<script>
    const addCommentBtn = document.getElementById("addCommentBtn");
    const commentModal = new bootstrap.Modal(document.getElementById("commentModal"));

    addCommentBtn.addEventListener("click", () => {
        commentModal.show();
    });

    // Scroll to bottom on load
    window.onload = function () {
        const chatBox = document.querySelector(".chat-box");
        chatBox.scrollTop = chatBox.scrollHeight;
    };
</script>

</body>
</html>
