<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ page import="java.util.*" %>
<%@ page import="java.text.*" %>
<%@ page import="com.nadri.dao.*"%>
<%@ page import="com.nadri.vo.*"%>


<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="shortcut icon" type="image/x-icon" href="${pageContext.request.contextPath}/favicon.png">
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/jquery-ui.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/all.min.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/reset.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/fonts.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/header.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/plan.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/caleander.css">
<script src="${pageContext.request.contextPath}/assets/js/jquery-3.6.0.min.js"></script>
<script src="${pageContext.request.contextPath}/assets/js/jquery-ui.min.js"></script>
<%-- <script src="${pageContext.request.contextPath}/assets/js/calender.js"></script> --%>
<style>
.label * {display: inline-block;vertical-align: top;}
.label .left {background: url("https://t1.daumcdn.net/localimg/localimages/07/2011/map/storeview/tip_l.png") no-repeat;display: inline-block;height: 24px;overflow: hidden;vertical-align: top;width: 7px;}
.label .center {background: url(https://t1.daumcdn.net/localimg/localimages/07/2011/map/storeview/tip_bg.png) repeat-x;display: inline-block;height: 24px;font-size: 12px;line-height: 24px;}
.label .right {background: url("https://t1.daumcdn.net/localimg/localimages/07/2011/map/storeview/tip_r.png") -1px 0  no-repeat;display: inline-block;height: 24px;overflow: hidden;width: 6px;}
</style>
<%
	Calendar cal = Calendar.getInstance();

	Date time = new Date();
	SimpleDateFormat format1 = new SimpleDateFormat("yyyy.MM.dd");
	String today = format1.format(time);
	cal.setTime(time); 
	cal.add(Calendar.DATE, +2);
	String defaultDate = format1.format(cal.getTime());

	Date time2 = new Date();
	SimpleDateFormat format2 =  new SimpleDateFormat("MM.dd");
	String defaultDay1 = format2.format(time);
	
	cal.setTime(time2);
	cal.add(Calendar.DATE, +1);
	String defaultDay2 = format2.format(cal.getTime());
	
	cal.setTime(time2);
	cal.add(Calendar.DATE, +2);
	String defaultDay3 = format2.format(cal.getTime());
%>
<title>나드리 - 계획하기</title>
</head>
<body>

	<div id="wrap">
		<div id="mapArea">
				<div id="map"></div>
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
					<a class="addPlan" href="javascript:;">일정 생성</a>
				</div>
				<div id="selectArea">
                    <h3>선택목록</h3>
                    <ul class="select tabnav">
                        <li onclick="selectHotelFrame()" class="item"><a href="#">호텔</a></li>
                        <li onclick="selectPlaceAndRestFrame()" class="item"><a href="#">장소ㆍ음식</a></li>
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
                    <input class="search" type="text" placeholder="검색어를 입력하세요."><i class="fas fa-search" onclick=""></i>
                    <h3>추천목록</h3>
                    <ul class="select tabnav">
                        <li onclick="showHotel()"><a href="#">호텔</a></li>
                        <li onclick="showPlace()"><a href="#">장소</a></li>
                        <li onclick="showRestaurant()"><a href="#">음식</a></li>
                        <li class="indicator"></li>
                    </ul>
                    	<div id="showList" ></div>
                </div>
            </article>
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

	function panTo(centerLat, centerLng, name) {
		var shortName = name.substring(0,2);
		// 이동할 위도 경도 위치를 생성합니다 
		var moveLatLon = new kakao.maps.LatLng(centerLat, centerLng);
		var content = '<div class="wrap">' + 
        '        <div class="circle">' + shortName +'</div></div>';
		customOverlay = new kakao.maps.CustomOverlay({
		    position: moveLatLon,
		    content: content   
		});
		// 지도 중심을 부드럽게 이동시킵니다
		// 만약 이동할 거리가 지도 화면보다 크면 부드러운 효과 없이 이동합니다
		customOverlay.setMap(map);
		map.panTo(moveLatLon);
		

	}
	
	function closeOverlay(){
		customOverlay.setMap(null);
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
	
	var centerLat = ${startLatitude}
	var centerLng = ${startLongitude}
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
	var customOverlay;
	var geocoder = new kakao.maps.services.Geocoder();
	infowindow = new kakao.maps.InfoWindow({
		zindex : 1
	});

	var placeAndRestList = {}
	var hotelList = {}
	var startPoint = {}
	startPoint['latitude'] = centerLat
	startPoint['longitude'] = centerLng
	startPoint['name'] = "${startName}"
	startPoint['url'] = "${startURL}"
	startPoint['type'] = 'starting'
	startPoint['id'] = ${startId}

	var numOfHotel = 0;
	var placeAndRest = {}
	var placeAndRestHTML = ""
	var hotelHTML = ""
	var selectedHotelInfo = {} 
	var selectedHotelIdList = []
	var diffDays;
	var lastId;
	var startDate;
	var endDate;
	
	var sDate = "<%=today%>"
    var eDate = "<%=defaultDate%>"
    var currentDayId="";
	
	function addList(id, latitude, longtitude, url, name){
		data = {}
		closeOverlay();
		var itemId = "#" + id;
		
		data['latitude'] = latitude
		data['longitude'] = longtitude
		data['name'] = name
		data['url'] = url
		data['id'] = id
		
		//hotel
		if(id>0 && id<100001){
			selectHotel(url, name);
			selectHotelFrame();
		} else { //place and restaurant
			$(itemId).empty();
			placeAndRest[id] = id
			selectPlaceAndRest(url, name)
			selectPlaceAndRestFrame();
			placeAndRestList[id] = data
		}
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
								
								 var hotelName = item.hotelName.replace(/\s/g, '');
								
								 var text = "<div id="+ item.hotelId + ">" + "<div class='select tabcontent'>"
									+  "<div class='selectTab'>"
									+  "<div class='recommendCard card'>"
									+  "<figure>"
									+  "<img src="+ item.hotelImageURL+" alt="+ item.hotelName +">"
									+  "</figure>"
									+  "<b>" + item.hotelName + "</b>"
									+  "<a class='info' href='https://www.instagram.com/explore/tags/"+ hotelName +"'><i class='fas fa-info'></i></a>"
									+  "<a class='plus' onmouseout='closeOverlay();' onMouseOver='panTo("+ item.hotelLatitude +","+ item.hotelLongitude + ",\"" + item.hotelName +"\")' onclick='addList("+item.hotelId+"," + item.hotelLatitude + "," + item.hotelLongitude + ",\"" + item.hotelImageURL + "\",\"" + item.hotelName + "\")'" +" href='javascript:;'><i class='fas fa-plus' ></i></a></div>"
									+  "</div>"
									+  "</div></div>"
							  
								jsonText += text;
							});
							$("#showList").append(jsonText);
							
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
					if(item.placeId in placeAndRest){
						return true;
					}
					
					 var placeName = item.placeName.replace(/\s/g, '');

					 var text = "<div id="+ item.placeId + ">" +  " <div class='select tabcontent'>"
						+  "<div class='selectTab'>"
						+  "<div class='recommendCard card'>"
						+  "<figure>"
						+  "<img src="+ item.placeImageURL+" alt="+ item.placeName +">"
						+  "</figure>"
						+  "<b>" + item.placeName + "</b>"
						+  "<a class='info' href='https://www.instagram.com/explore/tags/"+ placeName +"'><i class='fas fa-info'></i></a>"
						+  "<div onmouseout='closeOverlay();' onMouseOver='panTo("+ item.placeLatitude +","+ item.placeLongitude + ",\"" + item.placeName +"\")'>"
						+  "<a class='plus' onclick='addList("+item.placeId+"," + item.placeLatitude + "," + item.placeLongitude+ ",\"" + item.placeImageURL + "\",\"" + item.placeName + "\")' href='javascript:;'><i class='fas fa-plus' ></i></a></div>"
						+  "</div>"
						+  "</div>"
						+  "</div></div>"
				  
					jsonText += text;
				});
				$("#showList").append(jsonText);
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
								if(item.restaurantId in placeAndRest){
									return true;
								}
								 var restaurantName = item.restaurantName.replace(/\s/g, '');

								 var text = "<div id="+ item.restaurantId + ">" + " <div class='select tabcontent'>"
									+  "<div id='recommendHotel' class='selectTab'>"
									+  "<div class='recommendCard card'>"
									+  "<figure>"
									+  "<img src="+ item.restaurantImageURL+" alt="+ item.restaurantName +">"
									+  "</figure>"
									+  "<b>" + item.restaurantName + "</b>"
									+  "<a class='info' href='https://www.instagram.com/explore/tags/"+ restaurantName +"'><i class='fas fa-info'></i></a>"
									+  "<div onmouseout='closeOverlay();' onMouseOver='panTo("+ item.restaurantLatitude +","+ item.restaurantLongitude +  ",\"" + item.restaurantName +"\")'>"
									+  "<a class='plus' onclick='addList("+item.restaurantId+"," + item.restaurantLatitude + "," + item.restaurantLongitude+ ",\"" + item.restaurantImageURL + "\",\"" + item.restaurantName + "\")' href='javascript:;''><i class='fas fa-plus' ></i></a></div>"
									+  "</div>"
									+  "</div>"
									+  "</div></div>"
							  
								jsonText += text;
							});
							$("#showList").append(jsonText);
							
						},
						error : function(XHR, status, error) {
							console.error(status + " : " + error);
						}
					});
	}
	
	
	function selectHotelFrame(){

		$("#selectTabHotel").empty();
		$("#selectTabPlace").empty(); 
		
		text1 = "<strong class='countHotel'>"+ numOfHotel +"</strong>"
			+ 	"<button onclick='deleteHotelList()' href='javascript:;' class='btnrm'>호텔전체삭제</button>"
			+	"<small>숙소는 일정의 시작 지점과 종료 지점으로 설정됩니다.<br>마지막 날은 시작 지점으로만 설정됩니다.</small>"			
			+	"<div id='scroll'><div id='selectedHotel'>"
			+	"</div></div>"	
		$("#selectTabHotel").append(text1);
		$("#selectedHotel").append(hotelHTML);
		
		for(let key in selectedHotelInfo){
			$(key).children(".showSeletedHotel").empty();
			$(key).children(".showSeletedHotel").append(selectedHotelInfo[key]);
		}
		
	}
	function selectPlaceAndRestFrame(){
		$("#selectTabHotel").empty();
		$("#selectTabPlace").empty();
		var text = "<strong>0 <span>(총 00시간 0분)</span></strong>"
			+ 	"<button onclick='deletePlaceAndRestList()' href='javascript:;' class='btnrm'>장소전체삭제</button>"
			+   "<div id='scroll'><div id='seletedPlaceAndRest'></div></div>"
		$("#selectTabPlace").append(text)
		$("#seletedPlaceAndRest").append(placeAndRestHTML);
	}
	
 	function selectHotel(url, name){
 		var id="";
 		if(currentDayId != ""){
			id = currentDayId
		} else if(selectedHotelIdList.length > 0){
			id = selectedHotelIdList.shift()
		}
 		
		var text ="<div class='hotelSelectCard card'>"
		+	"<figure>"
		+	"<img src=" + url + " alt= " + name + ">"
		+	"</figure>"
		+	"<b>" + name + "</b>"
		+	"<a href='javascript:;' class='del' onclick='delHotelCard(\"" + id + "\")'><i class='fas fa-times'></i></a>"
		+	"</div>"
		
		if(!(id in selectedHotelInfo) && id!=""){
			selectedHotelInfo[id] = text
			hotelList[id] = data
			numOfHotel += 1
		}
	}
 	
 	function delHotelCard(id){
 		delete selectedHotelInfo[id]
 		delete hotelList[id]
 		selectedHotelIdList.push(id);
 		numOfHotel -= 1;
 		selectHotelFrame()
 	}
	
	function selectPlaceAndRest(url, name){
		var text = "<div class='selectedCard card'>"
		+ 	"<figure>"
		+	" <img src=" + url + " alt=" + name + ">"
		+	"</figure>"
		+	"<b>" + name +"</b>"
		+	"<i class='fas fa-stopwatch'></i>"
		+	"<input type='number' name='hour' id='hour' value='2' min='0' max='23'>"
		+	"<span>시간</span>"
		+	"<input type='number' name='minute' id='minute' value='0' min='0' max='59'>"
		+	" <span>분</span>"
		+	"</div>"
		+	"<a href='javascript:;' class='del'><i class='fas fa-times'></i></a>"
		+	"</div></div>"

		placeAndRestHTML += text;

	}
	
	function deleteHotelList(){
		numOfHotel = 0;
		hotelList = {};
		hotelHTML = "";
		selectHotelFrame();
		selectedHotelInfo = {};
		selectedHotelIdList = [];
		$("#selectedHotel").empty();	
        diffDays = getDiff(eDate, sDate);
        var sdate = new Date(sDate);
        var edate = new Date(eDate);
    	for(var i=1; i<=diffDays; i++){	
	        var id = 'dayID' + i;
	        if(i == diffDays){
	        	lastId = id
	        } 

	        var prevDay = sdate.getDate();
	        var prevMonth = sdate.getMonth()+1;
	        var nextDay = sdate.setDate(prevDay+1);
	        var nextMonth = sdate.getMonth()+1;
	    	text = "<div id=" + id + " class='dayArea'>"
	    		+	"<button href='javascript:;' class='btnDay' onclick='clickedDayButton(\"" + id + "\")'>DAY" + i + " <span>"+prevMonth+"."+prevDay+"-"+nextMonth+"."+sdate.getDate()+"</span></button>"
	    		+   "<div class='showSeletedHotel'>" 
	    		+   "<small>날짜를 선택하고 호텔을 추가하세요.</small>"
	    		+	"<a class='plus' href='javascript:;'><i class='fas fa-plus'></i></a>"
	    		+   "</div>"
	    		+   "</div>"
	    	selectedHotelIdList.push("#"+id);
	    	hotelHTML += text
    	}
    	selectHotelFrame();
    	showHotel();
	}
	
	function deletePlaceAndRestList(){
		placeAndRest = {};
		placeAndRestHTML = "";
		closeOverlay();
		selectPlaceAndRestFrame();
		$("#seletedPlaceAndRest").empty();
		showPlace();
	}
	
	//jquery
	$(function(){
		$("header").addClass("on");
		showHotel();
		selectHotelFrame();
		$("#schedule").on("click", function(){
			var params = {}
			params['placeAndRest'] = placeAndRestList;
			params['hotel'] = hotelList;
			params['start'] = startPoint;
			params['date'] = {
					'sDate' : sDate,
					'eDate' : eDate,
					'lastId' : lastId
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
		
		initShowHotel();
		
		$.datepicker.regional['ko'] = {
		        closeText: '닫기',
		        prevText: '이전달',
		        nextText: '다음달',
		        currentText: '오늘',
		        monthNames: ['1월','2월','3월','4월','5월','6월',
		        '7월','8월','9월','10월','11월','12월'],
		        monthNamesShort: ['1월','2월','3월','4월','5월','6월',
		        '7월','8월','9월','10월','11월','12월'],
		        dayNames: ['일','월','화','수','목','금','토'],
		        dayNamesShort: ['일','월','화','수','목','금','토'],
		        dayNamesMin: ['일','월','화','수','목','금','토'],
		        weekHeader: 'Wk',
		        dateFormat: 'yy.mm.dd',
		        firstDay: 0,
		        isRTL: false,
		        showMonthAfterYear: false,
		        yearSuffix: '',
		        showOn: 'both',
		        buttonText: "달력",
		        changeMonth: false,
		        changeYear: false,
		        showButtonPanel: false,
		        yearRange: 'c-99:c+99',
		        showAnim: "fade"
		    };
	    $.datepicker.setDefaults($.datepicker.regional['ko']);

		    $('#sdate').datepicker();
		    $('#sdate').datepicker("option", "maxDate", $("#edate").val());
		    $('#sdate').datepicker("option", "onClose", function ( selectedDate ) {
		        $("#sdate").datepicker("option", "minDate", "today");
		        $("#edate").datepicker( "option", "minDate", selectedDate );
		        startDate = selectedDate;
		    });

	    $('#sdate').datepicker();
	    $('#sdate').datepicker("option", "maxDate", $("#edate").val());
	    $('#sdate').datepicker("option", "onClose", function ( selectedDate ) {
	        $("#sdate").datepicker("option", "minDate", "today");
	        $("#edate").datepicker( "option", "minDate", selectedDate );
	        sDate = selectedDate;
	        var date = $(this).datepicker('getDate')
        	month = date.getMonth() + 1;
        	day = date.getDate();
	    });

		    $('#edate').datepicker();
		    $('#edate').datepicker("option", "minDate", $("#sdate").val());
		    $('#edate').datepicker("option", "onClose", function ( selectedDate ) {
		        $("#sdate").datepicker( "option", "maxDate", selectedDate );
		        eDate = selectedDate;
		        deleteHotelList();
		    });
	});//jquery 
	
	function clickedDayButton(id){
		$("button.btnDay").css("backgroundColor","#ddd");
	    $("#"+id).children("button.btnDay").css("backgroundColor", "#2e3c7e");
	    
	    currentDayId = "#"+id;
	}
	
	function initShowHotel(){
		deleteHotelList()
	}
	
	//마지막 날짜-처음 날짜
	function getDiff(){
		var edate = new Date(eDate);
		var sdate = new Date(sDate);
		
		var msDiff = edate.getTime()-sdate.getTime();
		var diffDay = Math.floor(msDiff/(1000*60*60*24));
		return diffDay
	}
	
</script>
</body>
</html>