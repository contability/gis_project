<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 			uri="http://java.sun.com/jsp/jstl/core" %>

<div class="div_window_measure">
	<ul>
		<li>
			<a href="#" class="a_measure_area" >
				<img class="tool_toggle_radio img_ov_off" src="<c:url value='/images/kworks/main/window/measure/area_off.svg' />" alt="면적측정" title="면적측정" />
			</a>
		</li>
		<li>
			<a href="#" class="a_measure_distance" >
				<img class="tool_toggle_radio img_ov_off" src="<c:url value='/images/kworks/main/window/measure/distance_off.svg' />" alt="거리측정" title="거리측정" />
			</a>
		</li>
		<li>
			<a href="#" class="a_measure_radius" >
				<img class="tool_toggle_radio img_ov_off" src="<c:url value='/images/kworks/main/window/measure/radius_off.svg' />" alt="반경측정" title="반경측정" />
			</a>
		</li>
		<li>
			<a href="#" class="a_measure_coordinate" >
				<img class="tool_toggle_radio img_ov_off" src="<c:url value='/images/kworks/main/window/measure/position_off.svg' />" alt="좌표측정" title="좌표측정" />
			</a>
		</li>
		<li>
			<a href="#" class="a_measure_clear" >
				<img src="<c:url value='/images/kworks/main/window/measure/del_off.svg' />" alt="모두지우기" title="모두지우기" />
			</a>
		</li>
	</ul>
</div>