<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Comments</title>
    <style>
        body {
            font-family: Arial, sans-serif;
        }

        .chat-container {
            max-width: 700px;
            margin: auto;
            padding: 20px;
            border: 1px solid #ccc;
            background: #f9f9f9;
        }

        .chat-box {
    display: flex;
    flex-direction: column-reverse;
    gap: 10px;
    max-height: 400px;
    overflow-y: auto;
    padding-bottom: 10px;
    scroll-behavior: smooth;
}

        .chat-message {
            max-width: 60%;
            padding: 10px;
            border-radius: 10px;
            position: relative;
        }

        .left {
            align-self: flex-start;
            background-color: #e2e2e2;
        }

        .right {
            align-self: flex-end;
            background-color: #d1ecf1;
        }

        .chat-meta {
            font-size: 12px;
            color: #555;
            margin-bottom: 4px;
        }

        /* Modal styles */
        .modal {
            display: none;
            position: fixed;
            z-index: 1;
            left: 0;
            top: 0;
            width: 100%;
            height: 100%;
            overflow: auto;
            background-color: rgba(0,0,0,0.4);
        }

        .modal-content {
            background-color: #fefefe;
            margin: 10% auto;
            padding: 20px;
            border: 1px solid #888;
            width: 50%;
            max-width: 500px;
        }

        .close {
            color: #aaa;
            float: right;
            font-size: 28px;
            font-weight: bold;
            cursor: pointer;
        }

        .close:hover {
            color: black;
        }

        .form-group {
            margin-bottom: 15px;
        }

        .form-group textarea {
            width: 100%;
            padding: 8px;
            box-sizing: border-box;
        }

        .btn {
            background-color: #4CAF50;
            color: white;
            padding: 10px 15px;
            border: none;
            cursor: pointer;
        }

        .btn:hover {
            background-color: #45a049;
        }

        .bottom-bar {
            text-align: center;
            margin-top: 20px;
        }
    </style>
</head>
<body>
<div class="chat-container">
    <h2>Task Comments</h2>

    <div class="chat-box">
        <c:choose>
            <c:when test="${hasComments}">
                <c:forEach var="comment" items="${comments}">
                    <div class="chat-message ${comment.userId == sessionScope.user.id ? 'right' : 'left'}">
                        <div class="chat-meta">
                            <b>User #${comment.userId}</b> - ${comment.timestamp}
                        </div>
                        <div class="chat-text">${comment.comment}</div>
                    </div>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <p>No comments yet. Be the first to comment!</p>
            </c:otherwise>
        </c:choose>
    </div>

    <div class="bottom-bar">
        <button id="addCommentBtn" class="btn">Add Comment</button>
    </div>
</div>

<!-- Modal -->
<div id="commentModal" class="modal">
    <div class="modal-content">
        <span class="close">&times;</span>
        <h3>Add New Comment</h3>
        <form id="commentForm" action="AddCommentServlet" method="post">
            <input type="hidden" name="taskId" value="${param.taskId}">
            <input type="hidden" name="userId" value="${sessionScope.user.id}">
            <div class="form-group">
                <textarea id="comment" name="comment" rows="4" required placeholder="Enter your comment..."></textarea>
            </div>
            <button type="submit" class="btn">Submit</button>
        </form>
    </div>
</div>

<script>
    var modal = document.getElementById("commentModal");
    var btn = document.getElementById("addCommentBtn");
    var span = document.getElementsByClassName("close")[0];

    btn.onclick = function () {
        modal.style.display = "block";
    }

    span.onclick = function () {
        modal.style.display = "none";
    }

    window.onclick = function (event) {
        if (event.target == modal) {
            modal.style.display = "none";
        }
    }
 // Scroll chat to bottom on load
    window.onload = function() {
        var chatBox = document.querySelector(".chat-box");
        chatBox.scrollTop = chatBox.scrollHeight;
    }

</script>
</body>
</html>
