<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 			uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" 			uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="fmt" 		uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form"		uri="http://www.springframework.org/tags/form" %>

<div id="viewAerialPhotoMetadataInfo" class="layerCont">
	<table id="etUV_main" class="cmmTable v2 ma_b_30" summary="항공사진 메타데이터 정보 테이블" style="table-layout: fixed; word-wrap: break-word;">
		<caption>항공사진 메타데이터 정보 테이블</caption>
		<colgroup>
			<col width="28%" />
			<col width="*" />
		</colgroup>
		<tbody>
			<tr>
				<th class="h_22" scope="row">항공사진명</th>
				<td>${result.title}</td>
			</tr>	
			<tr>
				<th class="h_22" scope="row">항공사진설명</th>
				<td>${result.description}</td>
			</tr>	
			<tr>
				<th class="h_22" scope="row">요청포멧</th>
				<td>${result.requestFormat}</td>
			</tr>	
			<tr>
				<th class="h_22" scope="row">촬영년도</th>
				<td>${result.photographyYear}</td>
			</tr>	
			<tr>
				<th class="h_22" scope="row">촬영기관</th>
				<td>${result.photographyInstitution}</td>
			</tr>
			<tr>
				<th class="h_22" scope="row">해상도</th>
				<td>${result.resolution}</td>
			</tr>	
		</tbody>
	</table>
</div>
<p class="btnRight v2">
	<a id="a_close_viewAerialPhoto" href="#" class="btn_close"></a>
</p>
