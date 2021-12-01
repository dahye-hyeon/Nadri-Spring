<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<link rel="stylesheet" href="../../assets/css/header.css">
<%@ page import="com.nadri.vo.*"%>

<%
UsersVo vo = (UsersVo) session.getAttribute("info");
System.out.println("check: " + vo);
%>
<header style="position: static;">
	<div class="container clear">
		<div>
			<a href="#"><h1 class="logo">
					NADRI<span>국내여행 나드리 갈래?</span>
				</h1></a>
		</div>
		<nav>
			<ul>
				<c:set var="auth" value="<%=vo%>" />
				<c:choose>
					<c:when test="${auth == null }">
						<li><a href="/Nadri-frontEnd/main?a=login">로그인</a></li>
						<li><a href="/user/joinForm">회원가입</a></li>
					</c:when>
					<c:otherwise>
						<li><a href="/Nadri-frontEnd/main?a=logout">로그아웃</a></li>
					</c:otherwise>
				</c:choose>
			</ul>
		</nav>
	</div>
</header>