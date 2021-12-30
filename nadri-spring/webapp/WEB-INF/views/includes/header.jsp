<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/assets/css/header.css">
<%@ page import="com.nadri.vo.*"%>
<%@ page import="com.nadri.controller.*"%>

<header>
	<div class="container clear">
		<div>
			<a href="/"><h1 class="logo">
					NADRI<span>우리나라로 나드리 갈래?</span>
				</h1></a>
		</div>
		<nav>
			<ul>
				<c:set var="auth" value="${usersVo}" />
				<c:choose>
					<c:when test="${auth == null }">
						<li><a
							href="${pageContext.request.contextPath}/user/loginForm">로그인</a></li>
						<li><a
							href="${pageContext.request.contextPath}/user/joinForm">회원가입</a></li>
					</c:when>
					<c:otherwise>
						<li><a href="${pageContext.request.contextPath}/user/myPage">내
								정보</a></li>
						<li><a href="${pageContext.request.contextPath}/user/logout">로그아웃</a></li>
					</c:otherwise>
				</c:choose>
			</ul>
		</nav>
	</div>
</header>