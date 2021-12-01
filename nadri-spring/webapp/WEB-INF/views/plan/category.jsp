<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.nadri.dao.*"%>
<%@ page import="com.nadri.vo.*"%>
<%@ page import="java.util.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="shortcut icon" type="../../image/x-icon" href="favicon.png">
<link rel="stylesheet" href="../../assets/css/reset.css">
<link rel="stylesheet" href="../../assets/css/fonts.css">
<link rel="stylesheet" href="../../assets/css/header.css">
<link rel="stylesheet" href="../../assets/css/category.css">
<script src="../../assets/js/jquery-3.6.0.min.js"></script>
<script src="../../assets/js/jquery-ui.min.js"></script>
<title>나드리 - 모두보기</title>
</head>
<body>

	<div id="wrap">
		<c:import url="/WEB-INF/views/includes/header.jsp"></c:import>
		<section id="main">
			<div class="container">
				<h2>
					어디로 여행을 떠날까요<span>?</span><b>무엇이든 검색해보세요</b>
				</h2>
				<div id="searchBar" class="searchBar">
					<input type="text" name="search" id="search"
						placeholder="지역을 검색해보세요!">
				</div>
			</div>
		</section>
		<!-- //main -->
		
		<section id="category">
			<article class="container">
				<div class="leftNav">
					<div class="container">
						<ul class="tabs">
							<span class="pill"></span>
							<li data-tab="tab-1" class="on"><a
								href="/Nadri-frontEnd/plan?a=category&city=Metro">수도권</a></li>
							<li data-tab="tab-1"><a
								href="/Nadri-frontEnd/plan?a=category&city=Gang">강원도</a></li>
							<li data-tab="tab-1"><a
								href="/Nadri-frontEnd/plan?a=category&city=chung">충청도</a></li>
							<li data-tab="tab-1"><a
								href="/Nadri-frontEnd/plan?a=category&city=Jeon">전라도</a></li>
							<li data-tab="tab-1"><a
								href="/Nadri-frontEnd/plan?a=category&city=Gyeong">경상도</a></li>
							<li data-tab="tab-1"><a
								href="/Nadri-frontEnd/plan?a=category&city=Je">제주도</a></li>
							<li class="searchIcon" data-tab="tab-1"><a href="#main"><img
									src="../../assets/images/search-icon.png" alt="검색 아이콘"
									style="width: 20px; height: 20px;"></a></li>
						</ul>
					</div>
			</article>
			<!-- //leftNav -->

			<article class="container">
				<div class="cards clear">
					<ul>
					<c:forEach items="${categoryList}" var="categorylist">
						<li class="card"><a href="javascript:;">
								<ul>
									<li class="contImg"><img
										src="${categorylist.cityImageURL}" alt="${categorylist.cityName}"></li>
									<li class="desc">
										<h3>${categorylist.cityName}</h3>
										<p>${categorylist.cityName}</p>
									</li>
								</ul>
						</a></li>
					
					</c:forEach>
					</ul>
				</div>
			</article>

			<footer>
				<div class="container">
					<p>KOSTA 229기 JAVA/Spring 기반 디지털 컨버전스</p>
					<address>&copy; Team A, Copyright all rights.</address>
				</div>
			</footer>
	</div>
	</section>

	</div>

	<script>
        $(document).ready(function(){

            var search = $("#category .leftNav ul li:last-of-type a");
            var currentPosition = parseInt($(".leftNav").css("top"));

            $(window).scroll(function() {
                var position = $(window).scrollTop();

                if (position > 300) {
                    $(".leftNav").stop().animate({
                        position : "fixed",
                        top : currentPosition + position - 300 + "px"
                    },'fast', 'swing');
                    $("header").addClass("on");
                }else if (position <= 300) {
                    $(".leftNav").css({
                        position : "absolute",
                        top : currentPosition
                    })
                    console.log("test");
                    $("header").removeClass("on");
                }
            })
            
            $("#category .leftNav ul li").on('click', function() {
                $("#category .leftNav ul li").removeClass("on");
                $(this).addClass("on");
            })

    });


    </script>
</body>
</html>