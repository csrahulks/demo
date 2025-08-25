<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Upload Documents</title>
 
 
</head>
<body>
	<h1>Upload Your Documents</h1>
 
 
 
	<form action="http://localhost:8080/validate-pan-esign" method="post"
		enctype="multipart/form-data">
		<label for="name">Name:</label><br> <input type="text" id="name"
			name="name" required><br> <br> <label for="token">Token:</label><br>
		<input type="text" id="token" name="token"><br> <br>
		<label for="pdfFile">Choose a file to upload:</label><br> <input
			type="file" id="pdfFile1" name="pdfFile" required />
		<button type="button" onclick="clearFile('pdfFile1')">Clear</button>
		<br> <br> <input type="file" id="pdfFile2" name="pdfFile" />
		<button type="button" onclick="clearFile('pdfFile2')">Clear</button>
		<br> <br> <input type="file" id="pdfFile3" name="pdfFile" />
		<button type="button" onclick="clearFile('pdfFile3')">Clear</button>
		<br> <br> <input type="file" id="pdfFile4" name="pdfFile" />
		<button type="button" onclick="clearFile('pdfFile4')">Clear</button>
		<br> <br> <input type="file" id="pdfFile5" name="pdfFile" />
		<button type="button" onclick="clearFile('pdfFile5')">Clear</button>
 
		<br> <br> <small>You can upload up to 5 files (PDF
			only).</small><br> <br> <input type="submit" value="Upload">
	</form>
 
 
	<div>
		<p th:if="${success}" style="color: green;">${success}</p>
		<p th:if="${error}" style="color: red;">${error}</p>
	</div>
 
	<script>
		function clearFile(fileId) {
			document.getElementById(fileId).value = ''; // Clears the file input field
		}
	</script>
</body>
</html>