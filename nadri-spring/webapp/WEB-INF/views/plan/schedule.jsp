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
<title>????????? - ????????????</title>
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
					<a class="addPlan" href="javascript:;">?????? ??????</a>
				</div>
				<div id="selectArea">
                    <h3>????????????</h3>
                    <ul class="select tabnav">
                        <li onclick="selectHotelFrame()" class="item"><a href="#">??????</a></li>
                        <li onclick="selectPlaceAndRestFrame()" class="item"><a href="#">???????????????</a></li>
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
                    <input class="search" type="text" placeholder="???????????? ???????????????."><i class="fas fa-search" onclick=""></i>
                    <h3>????????????</h3>
                    <ul class="select tabnav">
                        <li onclick="showHotel()"><a href="#">??????</a></li>
                        <li onclick="showPlace()"><a href="#">??????</a></li>
                        <li onclick="showRestaurant()"><a href="#">??????</a></li>
                        <li class="indicator"></li>
                    </ul>
                    	<div id="showList" ></div>
                </div>
            </article>
		</section>
	</div>
	
	<script type="text/javascript"
		src="//dapi.kakao.com/v2/maps/sdk.js?appkey=09f2b2fa3047367e09a83ad2e1752c03&libraries=services"></script>
	<script>
	var data = [];
	var markers = [];
	// ????????? ???????????? ???????????? ???????????? ???????????????
	function addMarker(position) {
		var data = {};
		// ????????? ???????????????
		var marker = new kakao.maps.Marker({
			position : position
		});

		// ????????? ?????? ?????? ??????????????? ???????????????
		marker.setMap(map);
		kakao.maps.event.addListener(marker, 'click', function(mouseEvent) {
			marker.setMap(null);
		});
		// ????????? ????????? ????????? ???????????????
		markers.push(marker);
		return marker;
	}

	// ????????? ????????? ???????????? ????????? ??????????????? ???????????? ???????????????
	function setMarkers(map) {
		for (var i = 0; i < markers.length; i++) {
			markers[i].setMap(map);
		}
	}

	// "?????? ?????????" ????????? ???????????? ???????????? ????????? ????????? ????????? ????????? ???????????? ???????????????
	function showMarkers() {
		setMarkers(map)
	}

	// "?????? ?????????" ????????? ???????????? ???????????? ????????? ????????? ????????? ???????????? ???????????? ???????????????
	function hideMarkers() {
		setMarkers(null);
	}

	function panTo(centerLat, centerLng, name) {
		var shortName = name.substring(0,2);
		// ????????? ?????? ?????? ????????? ??????????????? 
		var moveLatLon = new kakao.maps.LatLng(centerLat, centerLng);
		var content = '<div class="wrap">' + 
        '        <div class="circle">' + shortName +'</div></div>';
		customOverlay = new kakao.maps.CustomOverlay({
		    position: moveLatLon,
		    content: content   
		});
		// ?????? ????????? ???????????? ??????????????????
		// ?????? ????????? ????????? ?????? ???????????? ?????? ???????????? ?????? ?????? ???????????????
		customOverlay.setMap(map);
		map.panTo(moveLatLon);
		

	}
	
	function closeOverlay(){
		customOverlay.setMap(null);
	}

	
	var centerLat = ${startLatitude}
	var centerLng = ${startLongitude}
	var cityId = ${cityId}

	var mapContainer = document.getElementById('map'), // ????????? ????????? div  
	mapOption = {
		center : new kakao.maps.LatLng(centerLat, centerLng), // ????????? ????????????
		level : 8
	// ????????? ?????? ??????
	};
	// ?????? ????????? ???????????? ??????????????? 

	var map = new kakao.maps.Map(mapContainer, mapOption); // ????????? ???????????????
	var centerMarker = new kakao.maps.Marker({
		position : new kakao.maps.LatLng(centerLat, centerLng)
	});
	centerMarker.setMap(map);
	markers.push(centerMarker);
	var customOverlay;
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
			+ 	"<button onclick='deleteHotelList()' href='javascript:;' class='btnrm'>??????????????????</button>"
			+	"<small>????????? ????????? ?????? ????????? ?????? ???????????? ???????????????.<br>????????? ?????? ?????? ??????????????? ???????????????.</small>"			
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
		var text = "<strong>0 <span>(??? 00?????? 0???)</span></strong>"
			+ 	"<button onclick='deletePlaceAndRestList()' href='javascript:;' class='btnrm'>??????????????????</button>"
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
		+	"<span>??????</span>"
		+	"<input type='number' name='minute' id='minute' value='0' min='0' max='59'>"
		+	" <span>???</span>"
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
	    		+   "<small>????????? ???????????? ????????? ???????????????.</small>"
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
		        closeText: '??????',
		        prevText: '?????????',
		        nextText: '?????????',
		        currentText: '??????',
		        monthNames: ['1???','2???','3???','4???','5???','6???',
		        '7???','8???','9???','10???','11???','12???'],
		        monthNamesShort: ['1???','2???','3???','4???','5???','6???',
		        '7???','8???','9???','10???','11???','12???'],
		        dayNames: ['???','???','???','???','???','???','???'],
		        dayNamesShort: ['???','???','???','???','???','???','???'],
		        dayNamesMin: ['???','???','???','???','???','???','???'],
		        weekHeader: 'Wk',
		        dateFormat: 'yy.mm.dd',
		        firstDay: 0,
		        isRTL: false,
		        showMonthAfterYear: false,
		        yearSuffix: '',
		        showOn: 'both',
		        buttonText: "??????",
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
		    
		    var $leftTabnav = $("#leftBox #selectArea .tabnav li");
		    var $rightTabnav = $("#rightBox #selectArea .tabnav li")
		    var $leftIndicator = $("#leftBox #selectArea .indicator");
		    var $rightIndicator = $("#rightBox #selectArea .indicator");
		    var ani = "500, 'swing'";
		    
		    $($leftTabnav).first().on('click', function() {
		    	console.log("click");
		        $($leftIndicator).stop().animate({
		            left:0
		        },ani)
		    })
		    $($leftTabnav).eq(1).on('click', function() {
		    	console.log("click");
		        $($leftIndicator).stop().animate({
		            left : "150px"
		        },ani)
		    })
		    $($rightTabnav).first().on('click', function() {
		    	console.log("click");
		        $($rightIndicator).stop().animate({
		            left:0
		        },ani)
		    })
		    $($rightTabnav).eq(1).on('click', function() {
		    	console.log("click");
		        $($rightIndicator).stop().animate({
		            left:100
		        },ani)
		    })
		    $($rightTabnav).eq(2).on('click', function() {
		    	console.log("click");
		        $($rightIndicator).stop().animate({
		            left:200
		        },ani)
		    })
	});//jquery 
	
	function clickedDayButton(id){
		$("button.btnDay").css("backgroundColor","#ddd");
	    $("#"+id).children("button.btnDay").css("backgroundColor", "#2e3c7e");
	    
	    currentDayId = "#"+id;
	}
	
	function initShowHotel(){
		deleteHotelList()
	}
	
	//????????? ??????-?????? ??????
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