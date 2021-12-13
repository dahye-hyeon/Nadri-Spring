<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.util.*" %>
<%@ page import="java.text.*" %>
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
<%
	Calendar cal = Calendar.getInstance();

	Date time = new Date();
	SimpleDateFormat format1 = new SimpleDateFormat("yyyy.MM.dd");
	String today = format1.format(time);
	cal.setTime(time);
	cal.add(Calendar.DATE, +2);
	String defaultDate = format1.format(cal.getTime());

%>
<title>나드리 - 계획하기</title>
</head>
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
				<div id="schedule">
					<a href="#">일정 생성</a>
				</div>
			</article>

			<article class="rightBox"></article>
		</section>
		
	</div>
	
	<script type="text/javascript"
		src="//dapi.kakao.com/v2/maps/sdk.js?appkey=7f4cf056636b11d74c3ba9e1dd9980ee&libraries=services"></script>
	<script>
	var data = [];
	var markers = [];
	// 마커를 생성하고 지도위에 표시하는 함수입니다
	function addMarker(position) {
		var data = {};
		// 마커를 생성합니다
		var marker = new kakao.maps.Marker({
			position : position
		});

		// 마커가 지도 위에 표시되도록 설정합니다
		marker.setMap(map);
		kakao.maps.event.addListener(marker, 'click', function(mouseEvent) {
			marker.setMap(null);
		});
		// 생성된 마커를 배열에 추가합니다
		markers.push(marker);
		return marker;
	}

	// 배열에 추가된 마커들을 지도에 표시하거나 삭제하는 함수입니다
	function setMarkers(map) {
		for (var i = 0; i < markers.length; i++) {
			markers[i].setMap(map);
		}
	}

	// "마커 보이기" 버튼을 클릭하면 호출되어 배열에 추가된 마커를 지도에 표시하는 함수입니다
	function showMarkers() {
		setMarkers(map)
	}

	// "마커 감추기" 버튼을 클릭하면 호출되어 배열에 추가된 마커를 지도에서 삭제하는 함수입니다
	function hideMarkers() {
		setMarkers(null);
	}

	function searchDetailAddrFromCoords(coords, callback) {
		// 좌표로 법정동 상세 주소 정보를 요청합니다
		geocoder.coord2Address(coords.getLng(), coords.getLat(), callback);
	}

	function panTo() {
		// 이동할 위도 경도 위치를 생성합니다 
		var moveLatLon = new kakao.maps.LatLng(centerLat, centerLng);

		// 지도 중심을 부드럽게 이동시킵니다
		// 만약 이동할 거리가 지도 화면보다 크면 부드러운 효과 없이 이동합니다
		map.panTo(moveLatLon);
	}
	
	function getDistanceFromLatLonInKm(lat1,lng1,lat2,lng2) { 
		function deg2rad(deg) { return deg * (Math.PI/180) } 
		var R = 6371; // Radius of the earth in km 
		var dLat = deg2rad(lat2-lat1); // deg2rad below 
		var dLon = deg2rad(lng2-lng1); 
		var a = Math.sin(dLat/2) * Math.sin(dLat/2) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.sin(dLon/2) * Math.sin(dLon/2);
		var c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a)); 
		var d = R * c; // Distance in km 
		
		console.log("두 지점간의 거리는" + d.toFixed(3)*1000 + "m 입니다.");
		
		return d.toFixed(3)*1000; 
	}
	
	var centerLat = ${latitude}
	var centerLng = ${longitude}
	var cityId = ${cityId}

	var mapContainer = document.getElementById('map'), // 지도를 표시할 div  
	mapOption = {
		center : new kakao.maps.LatLng(centerLat, centerLng), // 지도의 중심좌표
		level : 8
	// 지도의 확대 레벨
	};
	// 마커 하나를 지도위에 표시합니다 

	var map = new kakao.maps.Map(mapContainer, mapOption); // 지도를 생성합니다
	var centerMarker = new kakao.maps.Marker({
		position : new kakao.maps.LatLng(centerLat, centerLng)
	});
	centerMarker.setMap(map);
	markers.push(centerMarker);

	var geocoder = new kakao.maps.services.Geocoder();
	infowindow = new kakao.maps.InfoWindow({
		zindex : 1
	});

	// 지도를 클릭했을때 클릭한 위치에 마커를 추가하도록 지도에 클릭이벤트를 등록합니다
	kakao.maps.event.addListener(map, 'click', function(mouseEvent) {
		var latlng = mouseEvent.latLng;
		searchDetailAddrFromCoords(latlng, function(result, status) {
			if (status === kakao.maps.services.Status.OK) {
				var detailAddr = !!result[0].road_address ? '<div>도로명주소 : '
						+ result[0].road_address.address_name + '</div>'
						: '';
				detailAddr += '<div>지번 주소 : '
						+ result[0].address.address_name + '</div>';

				var content = '<div class="bAddr">'
						+ '<span class="title">법정동 주소정보</span>'
						+ detailAddr + '</div>';
				// 클릭한 위치에 마커를 표시합니다 
				marker = addMarker(latlng);

				var message = '클릭한 위치의 위도는 ' + latlng.getLat() + ' 이고, ';
				message += '경도는 ' + latlng.getLng() + ' 입니다';
				dist = getDistanceFromLatLonInKm(centerLat, centerLng, latlng.getLat(), latlng.getLng());
				var resultDiv = document.getElementById('clickLatlng');
				resultDiv.innerHTML = message;
				// 인포윈도우에 클릭한 위치에 대한 법정동 상세 주소정보를 표시합니다
				infowindow.setContent(content);
				infowindow.open(map, marker);

			}
		});

	});
	
	$(function(){
		$("header").addClass("on");
		//[37.56667, 126.978393]
		$("#schedule").on("click", function(){
			/* var params = [
			    { id: 0, latitude: 37.545886456428626, longitude: 126.97350197587478 },
			    { id: 15123, latitude: 37.533926629930185, longitude: 126.9727305765772 },
			    { id: 21211, latitude: 37.52752413409103, longitude: 127.04059837787925 },
			    { id: 11111, latitude: 37.53692761066133, longitude: 127.0002309124208 },
			    { id: -1, latitude: 37.49610392685795, longitude: 127.04741038767537},
			  ];
			 */
			 
			//0: 출발점
			//-1: 도착점
			var params = {
					 '0': {
						 latitude: 37.545886456428626, 
						 longitude: 126.97350197587478 
					 },
					 '15123': {
						 latitude: 37.533926629930185, 
						 longitude: 126.9727305765772 
					 },
					 '21211': {
						 latitude: 37.52752413409103, 
						 longitude: 127.04059837787925 
					 },
					 '11111': {
						 latitude: 37.53692761066133, 
						 longitude: 127.0002309124208 
					 },
					 '-1': {
						 latitude: 37.49610392685795, 
						 longitude: 127.04741038767537 
					 }
			 }
			$.ajax({
				url : "schedule",
				type : "post",
				data : {
				       data: JSON.stringify(params) //params --> string
			     },
				datatype : "text",
				success : function(data) {
					if(data == "success"){
						location.href = "modify"
					}
				},
				error : function(XHR, status, error) {
					console.error(status + " : " + error);
				}
			});
		});
	});
	</script>
</body>
</html>