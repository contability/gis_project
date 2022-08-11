<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 			uri="http://java.sun.com/jsp/jstl/core" %>

<div class="div_window_coordinate">
	<table>
		<tr>
			<th>X</th>
			<td><input class="tm_x" type="text" /></td>
			<th>Y</th>
			<td><input class="tm_y" type="text" /></td>
			<td class="td_move">
				<a href='#' class="a_move_tm"><img src="<c:url value='/images/kworks/map/common/btn_point.png'/>" alt="이동"/></a>
			</td>
		</tr>
		<tr>
			<th>경도</th>
			<td><input class="lonlat_lon" type="text" /></td>
			<th>위도</th>
			<td><input class="lonlat_lat" type="text" /></td>		
			<td class="td_move">
				<a href='#' class="a_move_lonlat"><img src="<c:url value='/images/kworks/map/common/btn_point.png'/>" alt="이동" /></a>
			</td>
		</tr>
		<tr>
			<th>경도</th>
			<td>			
				<input class="dms_lon_do" type="text" /> 도<br /><br />
				<input class="dms_lon_min" type="text" /> 분<br /><br />
				<input class="dms_lon_secnd" type="text" /> 초<br /><br />
			</td>
			<th>위도</th>
			<td>
				<input class="dms_lat_do" type="text" /> 도<br /><br />
				<input class="dms_lat_min" type="text" /> 분<br /><br />
				<input class="dms_lat_secnd" type="text" /> 초<br /><br />
			</td>
			<td class="td_move">
				<a href='#' class="a_move_dms"><img src="<c:url value='/images/kworks/map/common/btn_point.png'/>" alt="이동" /></a>
			</td>
		</tr>
	</table>
</div>