$(".xj-swipe-item").tap(function(event,elem){
	var id = $(elem).attr("data-id");
	var index = $(elem).parent().find(".xj-swipe-item").index(elem);
	s.skip(index);//使边界对齐
	setContentHeight();
	showCat(id)
});

function showCat(id){
	$(".xj-swipe-item.active").removeClass("active");
	$("[data-id=" + id + "]").addClass("active");
	$(".nav-content:visible").hide();
	$("#"+id).show();
}

function setContentHeight(){
	var height = $(window).height() - $(".empty-header").height() - $(".empty-nav").height() - parseInt($(".content").css("margin-top"));
	$(".nav-content").css("height", function(index,value){
		value = parseInt(value);
		if(value<height){
			return height;
		}
	})
}
setContentHeight()