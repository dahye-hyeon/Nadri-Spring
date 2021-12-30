<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.util.*"%>
<%@ page import="java.text.*"%>
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
<script type="text/javascript" src="https://code.jquery.com/jquery-1.12.4.min.js"></script>
<script type="text/javascript" src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script src="${pageContext.request.contextPath}/assets/js/jquery-3.6.0.min.js"></script>
<script src="${pageContext.request.contextPath}/assets/js/jquery-ui.min.js"></script>
<script src="${pageContext.request.contextPath}/assets/js/calender.js"></script>

<title>나드리 - 계획하기</title>
</head>

<body>
	<div id="wrap">
		<div id="mapArea">
			<div id="map" style="width: 100%; height: 100vh;"></div>
			<div id="clickLatlng"></div>
		</div>

		<c:import url="/WEB-INF/views/includes/header.jsp"></c:import>
		<!-- //header -->

		<section id="fl">
			<article class="leftBox">
				<h2>${name}</h2>
				<small>${nameEng}</small>
				<div class="calendar">
					<input id="sdate" type="text" name="date" placeholder="${sDate}">
					<span>-</span> <input id="edate" type="text" name="date"
						placeholder="${eDate}">
				</div>
				<div id="selectArea">
					<h3>여행일정</h3>
					<div id="complete">
						<a class="addPlan" href="javascript:;">수정 완료</a>
					</div>
					<div id="modifiedScroll" class="selectTab path"></div>
				</div>
			</article>
		</section>
	</div>

	<script type="text/javascript"
		src="//dapi.kakao.com/v2/maps/sdk.js?appkey=09f2b2fa3047367e09a83ad2e1752c03&libraries=services"></script>
	<script>
	var data = [];
	var sdate = new Date("${sDate}");
	var edate = new Date("${eDate}");
	var centerLat = ${centerLat}
	var centerLng = ${centerLng}
	var info = ${info};
	var linePathDict = {};
	var savedOverlayDict= {};
	var diffDays = getDiff();
	var colorSet = ['#0000FF', '#8B4513', '#000000','#FFA500','#9ACD32','#87CEFA']
	var colorId = 0;
	var dayColorDict = {}

	var mapContainer = document.getElementById('map'), // 지도를 표시할 div  
	mapOption = {
		center : new kakao.maps.LatLng(centerLat, centerLng), // 지도의 중심좌표
		level : 6
	// 지도의 확대 레벨
	};
	// 마커 하나를 지도위에 표시합니다 

	var map = new kakao.maps.Map(mapContainer, mapOption); // 지도를 생성합니다
	// 지도에 표시할 선을 생성합니다


	function showPath(linePathDict){
		$.each(linePathDict, function(key, value){
			value.setMap(map);
		});
	}
	
	function showOverlay(data, overlayList, linePath){
		var localLat = data.latitude
		var localLng = data.longitude
		var position =  new kakao.maps.LatLng(localLat, localLng); 
		linePath.push(new kakao.maps.LatLng(localLat, localLng));
		var content = '<div class="wrap">' + 
        '<div class="circle">' + (data.name).substring(0,2) +'</div></div>'
        
		var customOverlay = new kakao.maps.CustomOverlay({
		    position: position,
		    content: content   
		});
        overlayList.push(customOverlay)
		customOverlay.setMap(map);
	}
	
	 $(function(){
		$("header").addClass("on");
		showPlanFrame()
		showList();
		showPath(linePathDict);
		$(".sortable").sortable();
	    $(".sortable").disableSelection();
	    $(".sortable").sortable({
	    	  connectWith: ".sortable",
	    	  stop: function(event, ui) {
	    			
	    			var id = "#" + $(this).parent().attr("id");
	    			linePathDict[id].setMap(null);
	    			
	    			var linePath = []
		  	    	$(id).children(".sortable").children().each(function(){
		  	    		var value = $(this).attr("value");
		  	    		var localLat 
		  	    		var localLng 
		  	    		if(value == "start"){
		  	    			localLat = info[id][0].latitude
			  	    		localLng = info[id][0].longitude
		  	    		} else {
		  	    			localLat = info[id][value].latitude
			  	    		localLng = info[id][value].longitude
		  	    		}
		  	    		
		  	    		linePath.push(new kakao.maps.LatLng(localLat, localLng))
		  	    	});
	    			
	    			var	polyline = new kakao.maps.Polyline({
	    			    path: linePath, // 선을 구성하는 좌표배열 입니다
	    			    strokeWeight: 5, // 선의 두께 입니다
	    			    strokeColor: dayColorDict[id], // 선의 색깔입니다
	    			    strokeOpacity: 1, // 선의 불투명도 입니다 1에서 0 사이의 값이며 0에 가까울수록 투명합니다
	    			    strokeStyle: 'solid' // 선의 스타일입니다
	    			});
	    			colorId += 1;
	    			linePathDict[id] = polyline;
	    			polyline.setMap(map);
	    	  }
	    }); 
	    
	    $("#complete").on("click", function(){
	    	var params = {}
	    	$(".selectedCard").each(function(){
	    		var id = "#" + $(this).parents(".dayArea").attr("id");
	    		params[id] = []
  	    	});
	    	
	    	$(".selectedCard").each(function(){
	    		var id = "#" + $(this).parents(".dayArea").attr("id");
	    		var idx = $(this).attr("value"); 
	    		if(idx == 'start'){
	    			idx = 0;
	    		} 
	    		params[id].push(info[id][idx]);
	    		
  	    	});
	    	
	    	console.log(params)
			$.ajax({
				url : "updatePlan",
				type : "post",
				datatype : "text",
				data : {
					data : JSON.stringify(params)
				},
				success : function(data) {
					location.href = "myPage"
				},
				error : function(XHR, status, error) {
					console.error(status + " : " + error);
				}
			});
		});
	}); 
	
	function getDiff(){
	
		var msDiff = edate.getTime()-sdate.getTime();
		var diffDay = Math.floor(msDiff/(1000*60*60*24));
		return diffDay
	}
	
	function showPlanFrame(){
		  for(var i=1; i<=diffDays+1; i++){	
		        var id;
		        if (i == diffDays+1){
		        	id = "last";
		        } else {
		        	id = 'dayID' + i;
		        }
		        	
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
		var startCheck = 0;
		$.each(info, function(day, item){
			var linePath = []
			var overlayList = []
			$.each(item, function(index, data){
				var id;
				if (index == 0){
					id = "start"
				} else {
					id = "" + index
				}
				 var text ="<div class='selectedCard card' value='" + id + "'>"
					+	"<figure>"
					+	"<img src=" + data.url + " alt= " + data.name + ">"
					+	"</figure>"
					+	"<b>" + data.name + "</b>"
					+	"<a href='javascript:;' class='del'><i class='fas fa-times'></i></a>"
					+	"</div>" 
					
				$(day).children(".sortable").append(text);
				showOverlay(data, overlayList, linePath);
			});
			var	polyline = new kakao.maps.Polyline({
			    path: linePath, // 선을 구성하는 좌표배열 입니다
			    strokeWeight: 5, // 선의 두께 입니다
			    strokeColor: colorSet[colorId%colorSet.length], // 선의 색깔입니다
			    strokeOpacity: 1, // 선의 불투명도 입니다 1에서 0 사이의 값이며 0에 가까울수록 투명합니다
			    strokeStyle: 'solid' // 선의 스타일입니다
			});
			
			linePathDict[day] = polyline;
			dayColorDict[day] = colorSet[colorId%colorSet.length];
			savedOverlayDict[day] = overlayList;
			colorId += 1;			
		})
	}
	
	function clickedDayButton(id){
		$("button.btnDay").css("backgroundColor","#ddd");
	    $("#"+id).children("button.btnDay").css("backgroundColor", "#2e3c7e");
	    $.each(linePathDict, function(key, value){
	    	value.setMap(null);
	    })
	     $.each(savedOverlayDict, function(key, value){
	    	$.each(value, function(k, v){
	    		v.setMap(null);
	    	})
	    })
	    
	    $.each(savedOverlayDict["#"+id], function(key, value){
	    	value.setMap(map); 
	    })
	    linePathDict["#"+id].setMap(map);
	}
	</script>
</body>
</html>