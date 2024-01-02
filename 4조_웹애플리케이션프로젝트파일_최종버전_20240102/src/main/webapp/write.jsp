<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ page import="java.io.File"%>
<%@ page import="java.util.Enumeration"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Write Page</title>
<style>
body {
	padding: 20px;
	font-family: Arial, sans-serif;
}

.container {
	max-width: 600px;
	margin: 0 auto;
}

h2 {
	margin-top: 40px;
	margin-bottom: 20px;
	text-align: center;
}

form {
	margin-top: 20px;
}

.form-group {
	margin-bottom: 20px;
}

label {
	font-weight: bold;
	display: block;
	margin-bottom: 5px;
}

input, textarea {
	width: 100%;
	padding: 8px;
	box-sizing: border-box;
}

button {
	background-color: #45a049;
	color: #fff;
	padding: 10px 20px;
	border: none;
	cursor: pointer;
}
</style>
</head>
<body>
	<div class="container">
		<h2>게시판 글 작성</h2>
		<form action="WriteServlet" method="post"
			enctype="multipart/form-data">
			<div class="form-group">
				<label for="title">제목:</label> <input type="text" name="title"
					id="title" required>
			</div>
			<div class="form-group">
				<label for="content">내용:</label>
				<textarea name="content" id="content" rows="5" required></textarea>
			</div>
			<div class="form-group">
				<label for="file">파일 업로드:</label> <input type="file" name="file"
					id="file" accept=".jpeg, .png, .bmp, .gif">
			</div>
			<button type="submit">글작성</button>
		</form>
	</div>
</body>
</html>
