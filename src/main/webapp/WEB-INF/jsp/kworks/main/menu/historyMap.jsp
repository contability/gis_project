<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 			uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" 		uri="http://www.springframework.org/tags"%>

<div class="div_base_map_select_tool" style="padding:10px;" >
	<select class="sel_start">
	</select>
	~
	<select class="sel_end">
	</select>
	<a class="a_select_base_map">선택</a>
</div>
<div class="div_base_map">

</div>
<div class="div_base_map_tool" style="text-align:center;padding:10px;" >
	<div>
		<input class="switch_play_type" />
		<input class="number_play_inteval" /> 초
	</div>
	<div style="margin-top:5px;">
		<a class="a_prev_play a_player" data-diarection="prev" >
			<img src="<c:url value='/images/kworks/main/menu/historyMap/prev_off.svg' />" alt="역방향" />
		</a>
		<a class="a_next_play a_player" data-diarection="next">
			<img src="<c:url value='/images/kworks/main/menu/historyMap/next_off.svg' />" alt="정방향" />
		</a>
		<a class="a_play_start a_player">
			<img src="<c:url value='/images/kworks/main/menu/historyMap/start_off.svg' />" alt="시작" />
		</a>
		<a class="a_play_pause a_player">
			<img src="<c:url value='/images/kworks/main/menu/historyMap/pause_off.svg' />" alt="일시정지" />
		</a>
		<a class="a_play_stop a_player">
			<img src="<c:url value='/images/kworks/main/menu/historyMap/stop_off.svg' />" alt="종료" />
		</a>
	</div>
</div>
