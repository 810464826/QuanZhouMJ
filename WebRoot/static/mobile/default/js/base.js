var width = $(window).width();
var height = $(window).height();
document.documentElement.style.fontSize = 100*width/480 + "px";

$(".header .backBtn").click(function(){
	window.history.go(-1);
})

