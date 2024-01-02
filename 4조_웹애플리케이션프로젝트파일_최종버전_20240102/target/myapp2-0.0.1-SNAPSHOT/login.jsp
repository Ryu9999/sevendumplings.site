<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Login Page</title>
<style>
body {
	font-family: Arial, sans-serif;
	background-color: #f4f4f4;
	margin: 0;
	padding: 0;
	display: flex;
	justify-content: center;
	align-items: center;
	height: 100vh;
}

#login-form {
	background-color: #ffffff;
	padding: 20px;
	border-radius: 8px;
	box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
	text-align: center;
}

h1 {
	color: #333333;
}

h5 {
	color: #666666;
	margin-bottom: 20px;
}

label {
	display: block;
	margin-bottom: 8px;
	color: #333333;
}

input {
	width: calc(100% - 16px);
	padding: 8px;
	margin-bottom: 16px;
	box-sizing: border-box;
	border: 1px solid #cccccc;
	border-radius: 4px;
	outline: none;
}

input[type="submit"] {
	background-color: #4caf50;
	color: #ffffff;
	cursor: pointer;
}

input[type="submit"]:hover {
	background-color: #45a049;
}

#signup-link a {
	color: #4caf50;
	text-decoration: none;
	font-weight: bold;
}

#signup-button {
	background-color: transparent;
	border: none;
	color: #4caf50;
	cursor: pointer;
	text-decoration: underline;
	font-weight: bold;
}
</style>
</head>
<body>
	<div id="login-form">
		<h1>이보게</h1>
		<h3>보안정보 공유 게시판</h3>
		<h5>이처럼 많은 보안정보 게시판에서, 보안정보를 공유하세요!</h5>
		<form action="LoginServlet" method="post">
			<label for="username">로그인</label> <input type="text" id="username"
				name="username" placeholder="Enter your username" required>
			<input type="password" id="password" name="password"
				placeholder="Enter your password" required> <input
				type="submit" value="Login">
		</form>
		<div id="signup-link">
			계정을 만들어 댓글과 게시글 작성으로 지식을 쌓아보세요 <a href="signup.jsp">회원가입</a>
		</div>
		<button id="signup-button"
			onclick="window.location.href='mainboard.jsp'">회원가입하지 않고 게시판
			이용하기</button>
	</div>
</body>

</html>

