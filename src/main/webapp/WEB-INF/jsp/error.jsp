<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Error Page</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            padding: 40px;
            background-color: #f4f4f4;
            text-align: center;
        }
        .error-box {
            background: white;
            padding: 30px;
            border-radius: 8px;
            display: inline-block;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }
        .error-message {
            color: #e74c3c;
            font-size: 20px;
        }
    </style>
</head>
<body>
    <div class="error-box">
        <h1>Oops! Something went wrong</h1>
        <p class="error-message">
            <!-- Access model data directly using EL -->
            ${error}
        </p>
        <a href="http://localhost:8080/upload">← Go back to Upload Page</a>
    </div>
</body>
</html>
