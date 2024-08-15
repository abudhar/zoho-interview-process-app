<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Success Page</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/styles.css">
    <style>
        .graffiti {
            text-align: center;
            margin-top: 50px;
        }
        .message {
            font-size: 1.2rem;
            color: #555;
        }
        .decor {
            position: absolute;
            top: 10%;
            left: 10%;
            width: 100%;
            height: 100%;
            pointer-events: none;
        }
        .spray1, .spray2 {
            position: absolute;
            border-radius: 50%;
            background: rgba(0, 0, 0, 0.1);
        }
        .spray1 {
            width: 100px;
            height: 100px;
            top: 20%;
            left: 20%;
        }
        .spray2 {
            width: 150px;
            height: 150px;
            top: 60%;
            right: 20%;
        }
    </style>
</head>
<body>
    <div class="container d-flex flex-column align-items-center justify-content-center vh-100">
        <div class="graffiti">
            <h1 class="display-4 text-success">🎉 Congratulations, ${sessionScope.candidate.name}! 🎉</h1>
            <p class="message mt-4">You have successfully completed the task!</p>
        </div>
        <div class="decor">
            <div class="spray1"></div>
            <div class="spray2"></div>
        </div>
        <a href="${pageContext.request.contextPath}/register" class="btn btn-primary mt-5">Go to Home Page</a>
    </div>
    <!-- jQuery and Bootstrap JS -->
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/interview.js"></script>
</body>
</html>
