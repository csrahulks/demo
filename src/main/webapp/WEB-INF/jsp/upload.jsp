<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Upload Form</title>
</head>
<body>
    <h2>Upload File Form</h2>
    <form action="http://localhost:8080/validate-and-sign" method="post" enctype="multipart/form-data">
        <label for="name">Name:</label><br>
        <input type="text" id="name" name="name" required><br><br>

        <label for="token">Token:</label><br>
        <input type="text" id="token" name="token" required><br><br>

        <label for="pdfFile">Choose a file to upload:</label><br>
        <input type="file" id="pdfFile" name="pdfFile" required><br><br>
        <input type="submit" value="Upload">
    </form>
</body>
</html>
