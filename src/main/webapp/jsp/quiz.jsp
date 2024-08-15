<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Multiple Choice Question with Timer</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/style.css">
</head>
<body>
    <!-- Header -->
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
        <div class="container-fluid">
            <a class="navbar-brand" href="/">Interview App</a>
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav ms-auto">
                    <li class="nav-item">
                        <!-- Display username dynamically -->
                        <span class="navbar-text text-white">
                            Welcome, <strong>${sessionScope.candidate.name}</strong>
                        </span>
                    </li>
                </ul>
            </div>
        </div>
    </nav>

    <div class="container mt-5">
        <div class="row justify-content-center">
            <div class="col-md-6">
                <h3 class="text-center">Multiple Choice Question</h3>
                <div class="card mt-4">
                    <div class="card-body">
                        <!-- Timer -->
                        <h5 id="timer" class="text-center text-danger">Time Left: 30s</h5>

                        <h5 id="difficulty" class="text-right"> Difficulty -> ${question.difficulty} </h5>
                        <h5 id="section" class=" text-right"> Section -> ${question.section} </h5>
                        <h5 id="correctOption" class=" text-right"> Answer -> ${question.correctOption}</h5>
                        <!-- Question -->
                        <h5 id="question">${question.questionText}</h5>

                        <!-- Options -->
                        <form id="quizForm" action="${pageContext.request.contextPath}/quiz/next-quiz" method="POST">
                            <div class="form-check">
                                <input class="form-check-input" type="radio" name="answer" id="option1" value="A">
                                <label class="form-check-label" for="option1">${question.optionA}</label>
                            </div>
                            <div class="form-check">
                                <input class="form-check-input" type="radio" name="answer" id="option2" value="B">
                                <label class="form-check-label" for="option2">${question.optionB}</label>
                            </div>
                            <div class="form-check">
                                <input class="form-check-input" type="radio" name="answer" id="option3" value="C">
                                <label class="form-check-label" for="option3">${question.optionC}</label>
                            </div>
                            <div class="form-check">
                                <input class="form-check-input" type="radio" name="answer" id="option4" value="D">
                                <label class="form-check-label" for="option4">${question.optionD}</label>
                            </div>
							<input type="hidden" name="questionId" id="questionId" value="${question.questionId}">
							
                            <!-- Submit Button -->
                            <div class="d-grid mt-3">
                                <button type="submit" id="submitBtn" class="btn btn-primary" onclick="block()">Submit</button>
                            </div>
                        </form>

                        <!-- Result Message -->
                        <div id="resultMessage" class="mt-3"></div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- jQuery and Bootstrap JS -->
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
	<script src="${pageContext.request.contextPath}/static/js/interview.js"></script>
    <!-- jQuery Script for Timer and Form Handling -->
    <script>
        $(document).ready(function() {
            let timeLeft = 30;
            let timerInterval = setInterval(updateTimer, 1000);

            // Update the timer every second
            function updateTimer() {
                if (timeLeft <= 0) {
                    clearInterval(timerInterval);
                    checkAnswer(); // Auto submit when time is up
                } else {
                    $("#timer").text("Time Left: " + timeLeft + "s");
                    timeLeft--;
                }
            }

            // Submit button handler
           /*  $("#submitBtn").click(function() {
                clearInterval(timerInterval); // Stop the timer
                checkAnswer(); // Submit manually
            }); */

            // Check the selected answer
            function checkAnswer() {
                // Get selected answer
                var selectedAnswer = $("input[name='answer']:checked").val();


                // Check if an option was selected
                if (!selectedAnswer) {
                    $("#resultMessage").html('<div class="alert alert-warning" role="alert">Time is up! You didn\'t select an answer!</div>');
                    return;
                }
                
                

            }
        });
    </script>
    <div id="overlay"><div class="cv-spinner"><span class="spinner"></span></div></div>
</body>
</html>
