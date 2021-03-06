<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="shortcut icon" type="/image/x-icon" href="${pageContext.request.contextPath}/favicon.png">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/reset.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/header.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/login.css">
    <script src="${pageContext.request.contextPath}/assets/js/jquery-3.6.0.min.js"></script>
    <script src="${pageContext.request.contextPath}/assets/js/login.js"></script>
    <title>나드리 - 로그인</title>
</head>
<body>
    <div id="wrap">

        <div id="login">
            <div class="container">
                <h2 class="loginHead">
                    LOG IN
                </h2>
                <form action="${pageContext.request.contextPath}/user/login" method="post">
                    <label for="email">이메일</label>
                    <input type="text" name="usersEmail" id="email">
                    <label for="password">비밀번호</label>
                    <input type="password" name="usersPassword" id="password">
                    <a href="javascript:;" class="missing">비밀번호를 잊으셨나요?</a>
                    <button type="submit">로그인</button>
                </form>
                <a href="${pageContext.request.contextPath}/user/joinForm" class="join">회원가입하기</a>
                <h3>SNS 간편 로그인</h3>
                <div class="snsIcon">
                    <div id="naver"><a href="naverLogin" title="네이버"></a></div>
                    <div id="kakao"><a href="https://kauth.kakao.com/oauth/authorize?client_id=205f5731512f02607419fe81babedfc8&redirect_uri=https://www.nadri.shop:8088/user/loginKakao&response_type=code" title="카카오톡"></a></div>             
                </div>
            </div>
        </div>

    </div>
</body>
</html>
