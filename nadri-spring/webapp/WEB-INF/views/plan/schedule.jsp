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
				<div id="selectArea">
                    <h3>선택목록</h3>
                    <ul class="select tabnav">
                        <li onclick="selectHotel()"><a href="#">호텔</a></li>
                        <li onclick="selectPlaceAndRest()"><a href="#">장소ㆍ음식</a></li>
                        <li class="indicator"></li>
                    </ul>
                    <div class="select tabcontent">
                        <div id="selectTabHotel" class="selectTab">
                        </div>
                        <!-- //selectTab01 -->
                        <div id="selectTabPlace" class="selectTab">
                        </div>
                        <!-- //selectTab02 -->
                  </div>
                </div>
			</article>

			<article id="rightBox">
                <div id="selectArea">
                    <input class="search" type="text" placeholder="검색어를 입력하세요."><i class="fas fa-search"></i>
                    <h3>추천목록</h3>
                    <ul class="select tabnav">
                        <li onclick="showHotel()"><a href="#">호텔</a></li>
                        <li onclick="showPlace()"><a href="#">장소</a></li>
                        <li onclick="showRestaurant()"><a href="#">음식</a></li>
                        <li class="indicator"></li>
                    </ul>
                    	<div id="showList"></div>
                </div>
            </article>
		</section>
		
	</div>
	
	<script type="text/javascript"
		src="//dapi.kakao.com/v2/maps/sdk.js?appkey=7f4cf056636b11d74c3ba9e1dd9980ee&libraries=services"></script>
	<script>
	
	$(function() {
		$(".plus").on('click', function() {
			alert("clcik");
		})
	})

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

	function panTo(centerLat, centerLng) {
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
	
	var allList = {}
		data1 = {}
		data1['latitude'] = 37.541
		data1['longitude'] = 126.986
		allList[0] = data1

	function addList(id, latitude, longtitude){
		data = {}
		data['latitude'] = latitude
		data['longitude'] = longtitude
		if(id>0 && id<100001){
			id = -1
		}
		allList[id] = data
		
		console.log(allList);

	}
		
	function showHotel(){
		var params = {
				listId : 1,
				cityId : ${cityId}
			}
			$.ajax({
						url : "showList",
						type : "post",
						data : params,
						datatype : "text",
						success : function(data) {
							var jsonData = JSON.parse(data);
							var jsonText = "";
							$("#showList").empty();
							$.each(jsonData,function(index, item) {
								 var text = "<div class='select tabcontent'>"
									+  "<div class='selectTab'>"
									+  "<div class='recommendCard card'>"
									+  "<figure>"
									+  "<img src="+ item.hotelImageURL+" alt="+ item.hotelName +">"
									+  "</figure>"
									+  "<b>" + item.hotelName + "</b>"
									+  "<a href='javascript:;' class='info'><i class='fas fa-info'></i></a>"
									+  "<div onMouseOver='panTo("+ item.hotelLatitude +","+ item.hotelLongitude +")'><a class='plus' onclick='addList("+item.hotelId+"," + item.hotelLatitude + "," + item.hotelLongitude+")' href='javascript:;'><i class='fas fa-plus' ></i></a></div>"
									+  "</div>"
									+  "</div>"
									+  "</div>"
							  
								jsonText += text;
							});
							$("#showList").prepend(jsonText);
							
						},
						error : function(XHR, status, error) {
							console.error(status + " : " + error);
						}
					});
	}
	
	function showPlace(){
		var params = {
				listId : 2,
				cityId : ${cityId}
			}
		
		$.ajax({
			url : "showList",
			type : "post",
			data : params,
			datatype : "text",
			success : function(data) {
				var jsonData = JSON.parse(data);
				var jsonText = "";
				$("#showList").empty();
				$.each(jsonData,function(index, item) {
					 var text = "<div class='select tabcontent'>"
						+  "<div class='selectTab'>"
						+  "<div class='recommendCard card'>"
						+  "<figure>"
						+  "<img src="+ item.placeImageURL+" alt="+ item.placeName +">"
						+  "</figure>"
						+  "<b>" + item.placeName + "</b>"
						+  "<a href='avascript:;' class='info'><i class='fas fa-info'></i></a>"
						+  "<div onMouseOver='panTo("+ item.placeLatitude +","+ item.placeLongitude +")'><a class='plus' onclick='addList("+item.placeId+"," + item.placeLatitude + "," + item.placeLongitude+")' href='javascript:;'><i class='fas fa-plus' ></i></a></div>"
						+  "</div>"
						+  "</div>"
						+  "</div>"
				  
					jsonText += text;
				});
				$("#showList").prepend(jsonText);
			},
			error : function(XHR, status, error) {
				console.error(status + " : " + error);
			}
		});

	}
	
	function showRestaurant(){
		var params = {
				listId : 3,
				cityId : ${cityId}
			}
			$.ajax({
						url : "showList",
						type : "post",
						data : params,
						datatype : "text",
						success : function(data) {
							var jsonData = JSON.parse(data);
							var jsonText = ""
							$("#showList").empty();
							$.each(jsonData,function(index, item) {
								
								 var text = "<div class='select tabcontent'>"
									+  "<div id='recommendHotel' class='selectTab'>"
									+  "<div class='recommendCard card'>"
									+  "<figure>"
									+  "<img src="+ item.restaurantImageURL+" alt="+ item.restaurantName +">"
									+  "</figure>"
									+  "<b>" + item.restaurantName + "</b>"
									+  "<a href='avascript:;' class='info'><i class='fas fa-info'></i></a>"
									+  "<div onMouseOver='panTo("+ item.restaurantLatitude +","+ item.restaurantLongitude +")'><a class='plus' onclick='addList("+item.restaurantId+"," + item.restaurantLatitude + "," + item.restaurantLongitude+")' href='javascript:;''><i class='fas fa-plus' ></i></a></div>"
									+  "</div>"
									+  "</div>"
									+  "</div>"
							  
								jsonText += text;
							});
							$("#showList").prepend(jsonText);
							
						},
						error : function(XHR, status, error) {
							console.error(status + " : " + error);
						}
					});
	}
	
	function selectHotel(){
		var text = "<strong class='countHotel'>0</strong>"
		+ 	"<button href='javascript:;' class='btnrm'>호텔전체삭제</button>"
		+	"<small>숙소는 일정의 시작 지점과 종료 지점으로 설정됩니다.<br>마지막 날은 시작 지점으로만 설정됩니다.</small>"
		+ 	"<div class='dayArea'>"
		+	"<button href='javascript:;' class='btnDay'>DAY 1 <span>12.09 - 12.10</span></button>"
		+	"<div class='hotelSelectCard card'>"
		+	"<figure>"
		+	"<img src='${pageContext.request.contextPath}/assets/images/category-ex01.jpg' alt='힐튼 하와이안 비릴지 와이키키 어쩌고'>"
		+	"</figure>"
		+	"<b>힐튼 하와이안 빌리지 와이키키 비치 리조트</b>"
		+	"<a href='javascript:;' class='del'><i class='fas fa-times'></i></a>"
		+	"</div></div>"
		+	"<div class='dayArea'>"
		+	"<button href='javascript:;' class='btnDay'>DAY 2 <span>12.10 - 12.11</span></button>"
		+	"<small>날짜를 선택하고 호텔을 추가하세요.</small>"
		+	"<a class='plus' href='javascript:;''><i class='fas fa-plus'></i></a>"	
		+	"</div>"
		$("#selectTabHotel").empty();
		$("#selectTabPlace").empty();
		$("#selectTabHotel").prepend(text);
	}
	
	function selectPlaceAndRest(){
		var text = "<strong>0 <span>(총 00시간 0분)</span></strong>"
		+ 	"<button href='javascript:;' class='btnrm'>장소전체삭제</button>"
		+	"<div class='selectedCard card'>"
		+ 	"<figure>"
		+	" <img src='${pageContext.request.contextPath}/assets/images/category-ex01.jpg' alt='램파이어'>"
		+	"</figure>"
		+	"<b>럼파이어(RumFire)</b>"
		+	"<i class='fas fa-stopwatch'></i>"
		+	"<input type='number' name='hour' id='hour' value='2' min='0' max='23'>"
		+	"<span>시간</span>"
		+	"<input type='number' name='minute' id='minute' value='0' min='0' max='59'>"
		+	" <span>분</span>"
		+	"</div>"
		+	"<a href='javascript:;' class='del'><i class='fas fa-times'></i></a>"
		+	"</div>"
		$("#selectTabHotel").empty();
		$("#selectTabPlace").empty();
		$("#selectTabPlace").prepend(text);
	}
	
	$(function(){
		$("header").addClass("on");
		showHotel();
		selectHotel();
		$("#schedule").on("click", function(){
			$.ajax({
				url : "schedule",
				type : "post",
				data : {
				       data: JSON.stringify(allList) //params --> string
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