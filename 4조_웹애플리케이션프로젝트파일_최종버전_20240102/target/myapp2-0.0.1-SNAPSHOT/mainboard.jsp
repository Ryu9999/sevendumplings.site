<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.util.List"%>
<%@ page import="com.myapp2.BoardDAO"%>
<%@ page import="com.myapp2.Post"%>

<%
String username = (String) session.getAttribute("username");
boolean isLoggedIn = (username != null);
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>게시판</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 0;
            display: flex;
            flex-direction: column;
            align-items: center;
        }

        #header {
            background-color: #333;
            color: white;
            padding: 10px;
            text-align: center;
        }

        #board-links {
            display: flex;
            justify-content: space-around;
            margin-top: 10px;
        }

        .button {
            background-color: #4caf50;
            color: white;
            padding: 10px;
            text-align: center;
            text-decoration: none;
            display: inline-block;
            font-size: 16px;
            margin: 4px 2px;
            cursor: pointer;
            border-radius: 4px;
        }

        #board-container {
            background-color: white;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            margin-top: 20px;
            max-width: 600px;
            width: 100%;
        }

        #board-content {
            margin-top: 20px;
            max-width: 600px;
            width: 100%;
            text-align: center;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 10px;
        }

        th, td {
            border: 1px solid #ddd;
            padding: 12px;
            text-align: left;
        }

        th {
            background-color: #f2f2f2;
        }

        tbody tr:nth-child(even) {
            background-color: #f9f9f9;
        }

        #no-posts {
            text-align: center;
        }

        #search-bar {
            display: flex;
            align-items: center;
            margin-bottom: 20px;
        }

        #search-bar input {
            flex: 1;
            padding: 10px;
            box-sizing: border-box;
            border: 1px solid #cccccc;
            border-radius: 4px;
            outline: none;
            margin-right: 10px;
        }

        #search-bar .button {
            margin-right: 10px;
        }

        #write-form {
            text-align: center;
        }

        #write-form input {
            background-color: #4caf50;
            color: white;
            padding: 10px;
            text-align: center;
            text-decoration: none;
            display: inline-block;
            font-size: 16px;
            margin: 4px 2px;
            cursor: pointer;
            border-radius: 4px;
        }
    </style>
</head>
<body>

<div id="header">
    <h2>보안 정보 공유 게시판, 이보게</h2>
    <h3>이처럼 많은 보안 정보 게시판, 이보게입니다.</h3>
    <c:choose>
        <c:when test="${not empty username}">
            <!-- 사용자가 로그인한 경우 -->
            <p>${username}님, 환영합니다!</p>
            <form action="${pageContext.request.contextPath}/LogoutServlet" method="post">
                <input type="submit" value="로그아웃" class="button";">
            </form>
        </c:when>
        <c:otherwise>
            <!-- 사용자가 로그인하지 않은 경우 -->
            <a href="${pageContext.request.contextPath}/login.jsp" class="button">로그인</a>
            <p>로그인 하지 않았다면, 로그인하여 게시판과 댓글 작성 기능을 사용해 보세요!</p>
        </c:otherwise>
    </c:choose>
</div>

<div id="board-container">
    <div id="board-links">
        <a href="<%=request.getContextPath()%>/board?type=vulnerability" class="button">게시판 조회하기</a>
        <c:choose>
            <c:when test="${not empty username}">
                <form action="<%=request.getContextPath()%>/write.jsp" method="post" id="write-form">
                    <input type="submit" value="게시글 작성" class="button">
                </form>
            </c:when>
            <c:otherwise></c:otherwise>
        </c:choose>
    </div>

    <div id="board-content">
        <h2>게시글 목록</h2>
        <table>
            <colgroup>
                <col style="width: 15%;">
                <col style="width: 40%;">
                <col style="width: 20%;">
                <col style="width: 25%;">
            </colgroup>
            <thead>
                <tr>
                    <th>순번</th>
                    <th>제목</th>
                    <th>작성자</th>
                    <th>등록일</th>
                </tr>
            </thead>
            <tbody>
                <c:if test="${not empty posts}">
                    <c:forEach var="post" items="${posts}">
                        <tr>
                            <td>${post.listNum}</td>
								<td>
								    <a href="<%=request.getContextPath()%>/ViewPostServlet?listNum=${post.listNum}">${post.title}</a>
								</td>
                            <td>${post.id}</td>
                            <td>${post.wrTime}</td>
                        </tr>
                    </c:forEach>
                </c:if>
            </tbody>
        </table>
    </div>

    <c:if test="${empty posts}">
        <div id="no-posts">
            <p>게시글이 없습니다.</p>
        </div>
    </c:if>
</div>
</body>
</html>
