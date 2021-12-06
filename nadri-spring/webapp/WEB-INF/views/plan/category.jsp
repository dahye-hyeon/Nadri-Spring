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
<link rel="shortcut icon" type="image/x-icon" href="favicon.png">
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
		<div class="bg"></div>
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
							<li data-tab="tab-1" class="on" onclick="filterCity(1)"><a
								href="#">수도권</a></li>
							<li data-tab="tab-1" onclick="filterCity(2)"><a href="#">강원도</a></li>
							<li data-tab="tab-1"><a href="#">충청도</a></li>
							<li data-tab="tab-1"><a href="#">전라도</a></li>
							<li data-tab="tab-1"><a href="#">경상도</a></li>
							<li data-tab="tab-1"><a href="#">제주도</a></li>
							<li class="searchIcon" data-tab="tab-1"><a href="#main"><img
									src="../../assets/images/search-icon.png" alt="검색 아이콘"
									style="width: 20px; height: 20px;"></a></li>
						</ul>
					</div>
			</article>
			<!-- //leftNav -->

			<article class="container">
				<div class="cards clear">
					<ul></ul>
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
		function filterCity(cityId) {

			var params = {
				regionId : cityId
			}
  
			$.ajax({
						url : "city",
						type : "post",
						data : params,
						datatype : "json",
						success : function(data) {
							var jsonData = JSON.parse(data);
							var jsonText = ""
							$(".container > .cards > ul").empty();
							$.each(jsonData,function(index, item) {
								var text = "<div class='popUp " + item.cityId +"'> "
											+ "<figure> <img src='" + item.cityImageURL
											+ "' alt='" + item.cityName + "'>"
											+ "</figure>"
											+ "<div class='popupDesc'>"
											+ "<a class='exit' href='javascript:;'></a>"
											+ "<h4 class='popupTitle'>"+ item.cityName + "</h4>"
											+ "<b>"+ item.cityName+ "</b>"
											+ "<p>"+ item.cityContent+ "</p>"
											+ "<a href='#'>"
											+ "<form action='schedule' method='post'>"
											+ "<input type='hidden' name='latitude' value=" + item.cityLatitude + ">"
											+ "<input type='hidden' name='longitude' value=" + item.cityLongitude + ">"
											+ "<input type='hidden' name='cityId' value=" + item.cityId + ">"
											+ "<input id='input' type='submit' value='일정 만들기'>"
											+ "</form></a>"
											+ "</div>"
											+ "<a class='exit' href='javascript:;'></a>"
											+ "</div>"
											+ "<li class='" + item.cityId + "'><a href='javascript:;'>"
											+ "<ul>"
											+ "<li class='contImg'><img src='" + item.cityImageURL + "' alt='" + item.cityName + "'></li>"
											+ "<li class='desc'"+ ">"
											+ "<h3>"+ item.cityName+ "</h3>"
											+ "<p>"+ item.cityEngName+ "</p>"
											+ "</li></ul></a></li>";
							  
							jsonText += text;
							});
							$("#category .cards > ul").prepend(jsonText);
						},
						error : function(XHR, status, error) {
							console.error(status + " : " + error);
						}
					});
		}

		$(document).ready(function() {

			var search = $("#category .leftNav ul li:last-of-type a");
			var currentPosition = parseInt($(".leftNav").css("top"));
			filterCity(1);
			$(window).scroll(function() {
				var position = $(window).scrollTop();

				if (position > 300) {
					$(".leftNav").stop().animate({
						position : "fixed",
						top : currentPosition + position - 300 + "px"
					}, 'fast', 'swing');
					$("header").addClass("on");
				} else if (position <= 300) {
					$(".leftNav").css({
						position : "absolute",
						top : currentPosition
					})
					$("header").removeClass("on");
				}
			})

			$("#category .leftNav ul li").on('click', function() {
				$("#category .leftNav ul li").removeClass("on");
				$(this).addClass("on");
			})

			var attr = "#category .cards > ul > li"
			var $card = $(attr)

			$("#category .cards > ul").on("click", "li", function() {
				console.log("클릭 완료");
				var $thisClass = $(this).attr('class');
				var $thisPop = "." + $thisClass;
				console.log("result: ", $thisPop);
				$(".bg").addClass("on");
				$($thisPop).addClass("on");
				$('body').on("click", ".bg", function() {
					$(".bg").removeClass("on");
					$($thisPop).removeClass("on");
				})
				$('body').on("click", ".exit", function() {
					$(".bg").removeClass("on");
					$($thisPop).removeClass("on");
				})
			})

		});
	</script>
</body>
</html>