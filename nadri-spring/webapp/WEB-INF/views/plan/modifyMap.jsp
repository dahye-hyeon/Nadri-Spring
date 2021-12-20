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
<link rel="shortcut icon" type="image/x-icon" href="${pageContext.request.contextPath}/favicon.png">
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/jquery-ui.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/all.min.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/reset.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/fonts.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/header.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/plan.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/caleander.css">
<link href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="https://code.jquery.com/jquery-1.12.4.min.js" ></script>
<script type="text/javascript" src="https://code.jquery.com/ui/1.12.1/jquery-ui.js" ></script>
<script src="${pageContext.request.contextPath}/assets/js/jquery-3.6.0.min.js"></script>
<script src="${pageContext.request.contextPath}/assets/js/jquery-ui.min.js"></script>
<script src="${pageContext.request.contextPath}/assets/js/calender.js"></script>
  <style>
.label * {display: inline-block;vertical-align: top;}
.label .left {background: url("https://t1.daumcdn.net/localimg/localimages/07/2011/map/storeview/tip_l.png") no-repeat;display: inline-block;height: 24px;overflow: hidden;vertical-align: top;width: 7px;}
.label .center {background: url(https://t1.daumcdn.net/localimg/localimages/07/2011/map/storeview/tip_bg.png) repeat-x;display: inline-block;height: 24px;font-size: 12px;line-height: 24px;}
.label .right {background: url("https://t1.daumcdn.net/localimg/localimages/07/2011/map/storeview/tip_r.png") -1px 0  no-repeat;display: inline-block;height: 24px;overflow: hidden;width: 6px;}
</style>
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
						placeholder="${sDate}"> <span>-</span> <input
						id="edate" type="text" name="date" placeholder="${eDate}">
				</div>
				<div id="selectArea">
                    <h3>여행일정</h3>
                    <div id="complete">
                    	<a class="addPlan" href="#">일정 완료</a>
                    </div>
                    <div class="selectTab path"></div>
                  </div>
			</article>
		</section>
	</div>
	
	<script type="text/javascript"
		src="//dapi.kakao.com/v2/maps/sdk.js?appkey=7f4cf056636b11d74c3ba9e1dd9980ee&libraries=services"></script>
	<script>
	var data = [];
	var sdate = new Date("${sDate}");
	var edate = new Date("${eDate}");
	var centerLat = ${centerLat}
	var centerLng = ${centerLng}
	var hotelInfo = ${hotelInfo};
	var placeAndRestInfo = ${placeAndRestInfo};
	var startInfo = ${startInfo};
	var path = ${path};
	var linePath = [];
	var savedOverlayList = [];
	var polyline;
	var diffDays = getDiff();

	
	function searchDetailAddrFromCoords(coords, callback) {
		// 좌표로 법정동 상세 주소 정보를 요청합니다
		geocoder.coord2Address(coords.getLng(), coords.getLat(), callback);
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
	
	for(var i in path){
		showOverlay(path[i]);
	}
	
	showPath(linePath);
	
	function initPath(){
		/* var text="";
		for(var i in path){
			
			text += "<li class='selectedCard card' value='" + path[i] + "'>"
				+ 	"<figure>"
				+	" <img src=" + mapInfo[path[i]]['url'] + " alt='" +  mapInfo[path[i]]['name'] + "'>"
				+	"</figure>"
				+	"<b>" + mapInfo[path[i]]['name'] +"</b>"
				+	"</li>"
		}
		 
			
		$(".path").append(text); */
	}
	
	function showPath(line){
			polyline = new kakao.maps.Polyline({
		    path: line, // 선을 구성하는 좌표배열 입니다
		    strokeWeight: 5, // 선의 두께 입니다
		    strokeColor: '#800000', // 선의 색깔입니다
		    strokeOpacity: 1, // 선의 불투명도 입니다 1에서 0 사이의 값이며 0에 가까울수록 투명합니다
		    strokeStyle: 'solid' // 선의 스타일입니다
		});
		
		// 지도에 선을 표시합니다 
		polyline.setMap(map);
	}
	
	function showOverlay(i){

		/* 	var localLat = mapInfo[i]['latitude'];
			var localLng = mapInfo[i]['longitude'];
			var position =  new kakao.maps.LatLng(localLat, localLng); 
			linePath.push(new kakao.maps.LatLng(localLat, localLng));
			var content = '<div class ="label"><span class="left"></span><span class="center">' + mapInfo[i]['name'] +'</span><span class="right"></span></div>';
			var customOverlay = new kakao.maps.CustomOverlay({
			    position: position,
			    content: content   
			});
			savedOverlayList.push(customOverlay);
			customOverlay.setMap(map);
	}
	
	function closeOverlay(){
		for(const overlay of savedOverlayList){
			overlay.setMap(null);
		} */
	}
	
	$(function(){
		$("header").addClass("on");
		initPath();	
		
		$.each(path, function(key, item){
			console.log(key, item)
		})
		
		showPlanFrame()
		showList();
		$(".sortable").sortable();
	    $(".sortable").disableSelection();
	    $(".sortable").sortable({
	    	  stop: function(event, ui) {
	    		 linePath = []
	  	    	polyline.setMap(null);
	  	    	closeOverlay();
	  	    	$(".sortable").children().each(function(){ 
	  	    		var value = $(this).attr("value");
	  	    		showOverlay(value)
	  	    	});
	  	    	showPath(linePath);
	    	  }
	    });
	});
	
	function getDiff(){
	
		var msDiff = edate.getTime()-sdate.getTime();
		var diffDay = Math.floor(msDiff/(1000*60*60*24));
		return diffDay
	}
	
	function showPlanFrame(){
		  for(var i=1; i<=diffDays; i++){	
		        var id = 'dayID' + i;
		        var prevDay = sdate.getDate();
		        var prevMonth = sdate.getMonth()+1;
		        var nextDay = sdate.setDate(prevDay+1);
		        var nextMonth = sdate.getMonth()+1;
		    	text = "<div id=" + id + " class='dayArea'>"
		    		+	"<button href='javascript:;' class='btnDay' onclick='clickedDayButton(\"" + id + "\")'>DAY" + i + " <span>"+prevMonth+"."+prevDay+"-"+nextMonth+"."+sdate.getDate()+"</span></button>"
		    		+   "<div class='sortable'></div>" 
		    		+   "</div>"	
		    	$(".path").append(text);
		   }
	}

	
	function showList(){
		
	}
	</script>
</body>
</html>