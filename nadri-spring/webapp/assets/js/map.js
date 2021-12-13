// 마커를 생성하고 지도위에 표시하는 함수입니다
function addMarker(position) {

    // 마커를 생성합니다
    var marker = new kakao.maps.Marker({
        position : position
    });

    // 마커가 지도 위에 표시되도록 설정합니다
    marker.setMap(map);
    kakao.maps.event.addListener(marker, 'click', function(mouseEvent){
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
    var moveLatLon = new kakao.maps.LatLng(37.564214, 127.001699);

    // 지도 중심을 부드럽게 이동시킵니다
    // 만약 이동할 거리가 지도 화면보다 크면 부드러운 효과 없이 이동합니다
    map.panTo(moveLatLon);
}

// 지도에 표시된 마커 객체를 가지고 있을 배열입니다
var markers = [];
var mapContainer = document.getElementById('map'), // 지도를 표시할 div  
mapOption = {
    center : new kakao.maps.LatLng(37.564214, 127.001699), // 지도의 중심좌표
    level : 2
// 지도의 확대 레벨
};
// 마커 하나를 지도위에 표시합니다 

var map = new kakao.maps.Map(mapContainer, mapOption); // 지도를 생성합니다
var centerMarker = new kakao.maps.Marker({
    position : new kakao.maps.LatLng(37.564214, 127.001699)
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

            var resultDiv = document.getElementById('clickLatlng');
            resultDiv.innerHTML = message;
            // 인포윈도우에 클릭한 위치에 대한 법정동 상세 주소정보를 표시합니다
            infowindow.setContent(content);
            infowindow.open(map, marker);

        }
    });

});
