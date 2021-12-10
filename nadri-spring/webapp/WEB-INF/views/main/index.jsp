<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>나드리 - 국내여행</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/reset.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/header.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/main.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/fonts.css">
    <link rel="shortcut icon" type="image/x-icon" href="favicon.png">
    <style>
    	header {position: static !important}
    </style>
</head>
<body>
    <div id="wrap">
       <c:import url="/WEB-INF/views/includes/header.jsp"></c:import>
<!-- //header -->
        <div id="main">
                <div class="leftBox">
                     <figure>
                        <video src="${pageContext.request.contextPath}/assets/images/main.mp4" autoplay="true" loop="true" muted="true"></video>
                    </figure>
                </div>
                <div class="rightBox">
                    <b>국내여행 플래너 사이트</b>
                    <h2 class="bLogo"><a href="javascript:;">NADRI</a></h2>
                    <div>
                        <a href="/plan/category" class="btnStart">시작하기</a>
                    </div>                   
                </div>
            </div>

        <footer>
            <address>kosta a조</address>
        </footer>
    </div>
</body>
</html>