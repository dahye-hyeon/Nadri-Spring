$(function() {
    console.log('test');
    // leftbox

    // region area
    var $regionList = $("#regionArea .tabnav.region li");
    var regionCont = $("#regionArea .tabnav.region li a").attr("href");
    // var test = $("#regionAreaTab01").siblings();
    // console.log(regionCont);
    $(regionCont).siblings().hide();
    $($regionList).first().addClass("on");
    $("h2").on('click', function() {
        $("#regionArea").stop().slideDown();
    });
    $($regionList).last().on('click', function() {
        // console.log("click");
        $("#regionArea").stop().slideUp();
    });
    $($regionList).not(":last-of-type").on('click', function(e) {
        e.preventDefault();
        $($regionList).removeClass("on");
        $(this).addClass("on");
        $(".region.tabcontent > div").hide();
        var regionContId = $(this).children().attr("href");
        $(regionContId).show();
    })

    // select area
    // tabmenu
    $("#selectArea .tabnav.select li").on('click', function() {
        
    })
})