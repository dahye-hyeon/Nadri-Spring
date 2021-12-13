<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.util.*" %>
<%@ page import="java.text.*" %>s
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="shortcut icon" type="image/x-icon" href="favicon.png">
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/jquery-ui.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/all.min.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/reset.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/fonts.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/header.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/plan.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/caleander.css">
<script src="${pageContext.request.contextPath}/assets/js/jquery-3.6.0.min.js"></script>
<script src="${pageContext.request.contextPath}/assets/js/jquery-ui.min.js"></script>
<script src="${pageContext.request.contextPath}/assets/js/calender.js"></script>

<title>나드리 - 계획하기</title>
</head>
<%
	Calendar cal = Calendar.getInstance();

	Date time = new Date();
	SimpleDateFormat format1 = new SimpleDateFormat("yyyy.MM.dd");
	String today = format1.format(time);
	cal.setTime(time);
	cal.add(Calendar.DATE, +2);
	String defaultDate = format1.format(cal.getTime());

%>
<body>

	<div id="wrap">
		<div id="mapArea">
			<div id="map"
				style="width: 100%; height: 100vh;"></div>
			<div id="clickLatlng"></div>
		</div>

		 <c:import url="/WEB-INF/views/includes/header.jsp"></c:import>
		<!-- //header -->

		<section id="fl">
			<article class="leftBox">
				<h2>${cityName}</h2>
				<small>${cityEngName}</small>
				<div class="calendar">
					<input id="sdate" type="text" name="date"
						placeholder=<%=today%>> <span>-</span> <input
						id="edate" type="text" name="date" placeholder=<%=defaultDate%>>
				</div>
	
			</article>

			<article class="rightBox"></article>
		</section>
		
	</div>
	
	<script type="text/javascript"
		src="//dapi.kakao.com/v2/maps/sdk.js?appkey=7f4cf056636b11d74c3ba9e1dd9980ee&libraries=services"></script>
	<script>
	var data = [];

	function searchDetailAddrFromCoords(coords, callback) {
		// 좌표로 법정동 상세 주소 정보를 요청합니다
		geocoder.coord2Address(coords.getLng(), coords.getLat(), callback);
	}

	var centerLat = ${latitude};
	var centerLng = ${longitude};
	var mapInfo = ${mapInfo};
	console.log(${path});

	var linePath = [];
	
	for(var key in mapInfo){
		linePath.push(new kakao.maps.LatLng(mapInfo[key]['latitude'], mapInfo[key]['longitude']));
	}  
	
	var mapContainer = document.getElementById('map'), // 지도를 표시할 div  
	mapOption = {
		center : new kakao.maps.LatLng(centerLat, centerLng), // 지도의 중심좌표
		level : 6
	// 지도의 확대 레벨
	};
	// 마커 하나를 지도위에 표시합니다 

	var map = new kakao.maps.Map(mapContainer, mapOption); // 지도를 생성합니다
	// 지도에 표시할 선을 생성합니다
	var polyline = new kakao.maps.Polyline({
	    path: linePath, // 선을 구성하는 좌표배열 입니다
	    strokeWeight: 5, // 선의 두께 입니다
	    strokeColor: '#800000', // 선의 색깔입니다
	    strokeOpacity: 1, // 선의 불투명도 입니다 1에서 0 사이의 값이며 0에 가까울수록 투명합니다
	    strokeStyle: 'solid' // 선의 스타일입니다
	});

	// 지도에 선을 표시합니다 
	polyline.setMap(map);
	
	$(function(){
		$("header").addClass("on");
	});
	</script>
</body>
</html>