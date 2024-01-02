<%@ page language="java" contentType="text/html; charset=UTF-8"%>
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
    <title>게시글 보기</title>
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
            padding: 20px;
            text-align: center;
        }

        #header a {
            color: white;
            text-decoration: none;
        }

        #post-container {
            background-color: white;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            margin-top: 20px;
            max-width: 800px;
            width: 100%;
        }

        .post, .comment {
            background-color: #f9f9f9;
            padding: 20px;
            border-radius: 8px;
            margin-bottom: 20px;
        }

        .comment {
            margin-top: 10px;
        }

        #comments-container {
            margin-top: 20px;
        }

        #comment-form, #back-link {
            margin-top: 20px;
            text-align: center;
        }

        textarea {
            width: 100%;
            padding: 10px;
            margin-top: 10px;
            box-sizing: border-box;
            border-radius: 8px;
            resize: vertical;
        }

        button {
            padding: 10px 20px;
            margin-top: 10px;
            border-radius: 8px;
            text-decoration: none;
            background-color: #4CAF50;
            color: white;
            cursor: pointer;
        }

        .button {
            background-color: #4caf50;
            color: white;
            padding: 10px 20px;
            text-align: center;
            text-decoration: none;
            display: inline-block;
            font-size: 16px;
            margin: 10px;
            cursor: pointer;
            border-radius: 8px;
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

    <div id="post-container">
        <h2>게시글</h2>
        <c:if test="${not empty post}">
            <div class="post">
                <h3>${post.title}</h3>
                <p>작성자: ${post.id}</p>
                <p>작성 일시: ${post.wrTime}</p>
                <p>수정 일시: ${post.editTime}</p>
                <p>${post.content}</p>
                <p>파일 이름 : ${post.file}</p>
                <c:if test="${not empty post.file}">
                    <c:forEach var="image" items="${post.file}">
                        <img class="uploaded-image" src="${request.getContextPath()}/uploaded/${image}" alt="Uploaded Image">
                    </c:forEach>
                </c:if>

                <c:if test="${not empty post.file}">
                    <c:forEach var="image" items="${post.file}">
                        <p>파일 다운로드: <a href="${request.getContextPath()}/downloadfile?fileName=${image}">${image}</a></p>
                    </c:forEach>
                </c:if>

                <c:if test="${isAdmin}">
                    <form action="${pageContext.request.contextPath}/DeletePostServlet" method="post">
                        <input type="hidden" name="listNum" value="${post.listNum}">
                        <button type="submit">게시글 삭제</button>
                    </form>
                </c:if>
            </div>

            <c:if test="${not empty comments}">
                <div id="comments-container">
                    <h3>댓글</h3>
                    <c:forEach var="comment" items="${comments}">
                        <div class="comment">
                            <p>댓글번호: ${comment.id}</p>
                            <p>작성자: ${comment.username}</p>
                            <p>댓글 내용: ${comment.commentContent}</p>
                            <p>작성 일시: ${comment.commentTime}</p>

                            <c:if test="${isAdmin}">
                                <form action="${pageContext.request.contextPath}/DeleteCommentServlet" method="post">
                                    <input type="hidden" name="commentId" value="${comment.id}">
                                    <input type="hidden" name="listNum" value="${post.listNum}">
                                    <button type="submit">댓글 삭제</button>
                                </form>
                            </c:if>
                        </div>
                    </c:forEach>
                </div>
            </c:if>

            <c:if test="${isLoggedIn}">
                <div id="comment-form">
                   <form action="${pageContext.request.contextPath}/ViewPostServlet" method="post" accept-charset="UTF-8">
                        <input type="hidden" name="postId" value="${post.listNum}">
                        <textarea name="commentContent" rows="4" cols="50" required></textarea>
                        <button type="submit">댓글 작성</button>
                    </form>
                </div>
            </c:if>

            <div id="back-link">
                <button class="button" onclick="goBack()">이전 페이지로 돌아가기</button>
            </div>
        </c:if>
    </div>
    <script>
        function goBack() {
            window.history.back();
        }
    </script>
</body>
</html>
