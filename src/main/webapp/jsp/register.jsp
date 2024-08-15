<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login Page</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/style.css">
</head>
<body>
    <div class="container">
        <div class="row justify-content-center">
            <div class="col-md-4">
                <h2 class="text-center mt-5">View Interview Result</h2>
                <div class="card mt-4">
                    <div class="card-body">
	                    <c:if test="${error != null}">
		                    <div class="alert alert-danger" role="alert">${error}</div>
	                    </c:if>
                        <!-- Form -->
                        <form action="${pageContext.request.contextPath}/view-Result" method="POST">
                            <!-- Username -->
                            <div class="mb-3">
                                <label for="username" class="form-label">Email ID</label>
                                <input type="text" class="form-control" id="email" name="email" required>
                            </div>

                            <!-- Password -->
                            <div class="mb-3">
                                <label for="dob" class="form-label">DOB</label>
                                <input type="date" class="date form-control" id="dob" name="dob" required>
                            </div>

                            <!-- Submit Button -->
                            <div class="d-grid">
                                <button type="submit" class="btn btn-primary">View Result</button>
                            </div>
                        </form>
                    </div>
                </div>
                <p class="text-center mt-3">
                    Didn't Attended the Interview yet? <a href="/login">New Interview</a>
                </p>
            </div>
        </div>
    </div>
	<div id="overlay"><div class="cv-spinner"><span class="spinner"></span></div></div>
    <!-- Bootstrap JS and dependencies -->
  	<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
   	<script src="${pageContext.request.contextPath}/static/js/interview.js"></script>
</body>
</html>

