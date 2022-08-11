$(function() {
	
	// 메인 이미지 슬라이더
	$(".kworksMainImg").bxSlider({
		auto: true,
		pause : 10000,
		mode: 'horizontal',
		useCSS: false,
	    slideWidth: 1200,
		speed: 3000,
		autoControls: false,
		controls: false,
		pager:false,
		easing:'easeOutCirc'
	});
	
	if($("#portal_sys_slider li").length < 6) {
		$("#portal_sys_slider li").css("margin-left", "30px");
	}
	
});