<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Sign Up Page</title>
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

#signup-form {
	background-color: #ffffff;
	padding: 20px;
	border-radius: 8px;
	box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
	text-align: center;
}

h1 {
	color: #333333;
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

.password-toggle {
	cursor: pointer;
	color: #4caf50;
	text-decoration: underline;
	font-weight: bold;
	display: block;
	margin-top: 8px;
}

input[type="submit"] {
	background-color: #4caf50;
	color: #ffffff;
	cursor: pointer;
}

input[type="submit"]:hover {
	background-color: #45a049;
}
</style>
</head>
<body>
	<div id="signup-form">
		<h1>회원가입</h1>
		<form action="SignUpServlet" method="post">
			<label for="nickname">ID:</label> <input type="text" id="nickname"
				name="nickname" placeholder="게시판에서 활동하실 아이디를 입력하세요" required>

			<label for="password">Password:</label>
			<div style="position: relative;">
				<input type="password" id="password" name="password"
					placeholder="사용하실 패스워드를 입력하세요" required>
			</div>

			<label for="email">Email:</label> <input type="email" id="email"
				name="email" placeholder="이메일 주소를 입력하세요" required
				oninput="validateEmail(this.value)"> <span id="emailError"
				style="color: red;"></span>

			<script>
				function validateEmail(email) {
					const emailInput = document.getElementById('email');
					const emailError = document.getElementById('emailError');

					const regex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
					const isValid = regex.test(email);

					if (isValid) {
						emailInput.setCustomValidity('');
						emailError.textContent = '';
					} else {
						emailInput.setCustomValidity('올바른 이메일 주소를 입력하세요');
						emailError.textContent = '올바른 이메일 주소를 입력하세요';
					}
				}
			</script>
			<label for="phone">휴대전화 번호:</label> <input type="tel" id="phone"
				name="phone" placeholder="휴대폰 번호를 입력하세요" required
				oninput="validatePhoneNumber(this.value)"> <span
				id="phoneError" style="color: red;"></span>

			<script>
				function validatePhoneNumber(phone) {
					const phoneInput = document.getElementById('phone');
					const phoneError = document.getElementById('phoneError');

					const regex = /^\d{3}\s?\d{4}\s?\d{4}$/;
					const isValid = regex.test(phone.replace(/\s/g, ''));

					if (isValid) {
						phoneInput.setCustomValidity('');
						phoneError.textContent = '';
					} else {
						phoneInput.setCustomValidity('올바른 휴대전화 번호를 입력하세요');
						phoneError.textContent = '올바른 휴대전화 번호를 입력하세요 (예: 010 1234 5678)';
					}
				}
			</script>
			<input type="submit" value="Sign Up">
		</form>
	</div>
</body>
</html>
