$(function() {
	
	// 메인 이미지 슬라이더
	$("#portal_main_image_slider").bxSlider({
		auto: true,
		pause : 10000,
		mode: 'horizontal',
		useCSS: false,
	    slideWidth: 300,
	    minSlides: 4,
	    maxSlides: 4,
	    moveSlides : 3,
		speed: 3000,
		autoControls: false,
		controls: false,
		pager:false,
		easing:'easeOutCirc'
	});
	
	// 메인 이미지 슬라이더 가운데 정렬
	$("#portal_sys_slider").css({
		'text-align' : 'center',
		'display' : 'inline-block'
	});
	
	$(".bx-wrapper").css("max-width","1175px");
	
});