<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.nadri.vo.*"%>
<%@ page import="com.nadri.controller.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>나드리 - 마이페이지</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/reset.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/header.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/fonts.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/myPage.css">
	<link rel="shortcut icon" type="image/x-icon" href="${pageContext.request.contextPath}/favicon.png">
<script src="${pageContext.request.contextPath}/assets/js/jquery-3.6.0.min.js"></script>
<script src="${pageContext.request.contextPath}/assets/js/jquery-ui.min.js"></script> 
 </head>
<body>
    <div class="wrapper">
        <div class="container">
            <div class="top-background-div"></div>
            <div class="top-container">
                <div class="profilePhotoContainer">
                    <div class="profilePhoto-text" id="profilePhote"></div>
                </div>

                <div class="text">${vo.usersName}</div>
                <form class="btn-normal" onclick="">프로필 수정</form>
            </div>
            <div>
                <div class="row">
                    <div class="index-section">
                        <div class="index-circle" onclick="">
                            <h5 style="font-family: 'Montserrat' !important">
                                <b>나의 일정</b>
                            </h5>
                            <div>
                                <h2 style="line-height: 1; font-weight: 700" id="myPlan"></h2>
                            </div>
                        </div>
                        <div class="index-circle" onclick="">
                            <h5 style="font-family: 'Montserrat' !important">
                                <b>나의 리뷰</b>
                            </h5>
                            <div>
                                <h2 style="line-height: 1; font-weight: 700" id="myReview">0</h2>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!--나의 일정-->
    <div>
        <div class="content">
            <h3 style="text-align: left;">나의 일정</h3>
            <div class="planContent-1">
                <div class="cardContent">
                    <img style="float: left;" src="images/mountain.jpg">
                    <div class="content-1">
                        <div class="travel-title">JEJU</div>
                        <div class="uk-text-meta">대한민국 제주도</div>
                        <div class="uk-text-meta" style="font-size: 12px; margin-top: 8px;">사용자 계정</div>
                    </div>
                    <div class="content-2">
                        <div class="second-content-1">
                            <div class="text-content-1">
                                <div class="small-title">여행이름</div>
                                <div class="small-text">혼자 가는 제주도</div>
                            </div>
                            <div class="text-content-2">
                                <div class="small-title">최종수정</div>
                                <div class="small-text">2021.12.10</div>
                            </div>
                        </div>
                        <div class="second-content-2">
                            <div class="text-content-1">
                                <div class="small-title">여행일자</div>
                                <div class="small-text">2021.12.12-2021.12.20</div>
                            </div>
                            <div class="text-content-2">
                                <div class="small-title">선택장소</div>
                                <div class="small-text">12</div>
                            </div>
                        </div>
                    </div>
                    <div class="botton-content">
                        <button>일정수정</button>
                        <button>일정표</button>
                        <button>일정공유</button>
                        <button>삭제</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>