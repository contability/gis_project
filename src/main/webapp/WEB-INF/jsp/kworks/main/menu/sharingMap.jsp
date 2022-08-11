<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 			uri="http://java.sun.com/jsp/jstl/core" %>

<div class="div_tool">
	<a class="a_add_sharing_map" href="#"><img src="<c:url value='/images/kworks/map/common/bookmark_plus.png' />" alt="위치 추가" /></a>
	<a class="a_add_kml_file" href="#"><img src="<c:url value='/images/kworks/map/common/kml_plus.png' />" alt="KML 파일추가" /></a>
	<div class="div_import_file" style="display: none;">
			<input id="file_import_file" name="file_import_file" type="file" />
	</div>
	<br>
	<div>
		<input class="switch_always_show">
		<span style="font-size:13px; margin-left: 10px;">공유 위치 항시 표시</span> 
	</div>
</div>
<div class="div_sharing_map"></div>