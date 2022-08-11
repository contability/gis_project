<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 			uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"			uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="ui" 			uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="form"		uri="http://www.springframework.org/tags/form" %>

<div id="div_topographic_meta" class="layerCont">
	<table id="etUV_main" class="cmmTable v2 ma_b_30" summary="지형지도 상세정보" style="table-layout: fixed; word-wrap: break-word;">
		<caption>지형지도 상세정보</caption>
		<colgroup>
			<col width="28%" />
			<col width="*" />
		</colgroup>
		<tbody>
			<tr>
				<th class="h_22" scope="row">지도명</th>
				<td id="title"></td>
			</tr>	
			<tr>
				<th class="h_22" scope="row">설명</th>
				<td id="description"></td>
			</tr>	
			<tr>
				<th class="h_22" scope="row">최소값</th>
				<td id="minValue"></td>
			</tr>
			<tr>
				<th class="h_22" scope="row">최대값</th>
				<td id="maxValue"></td>
			</tr>
			<tr>
				<th class="h_22" scope="row">제작년도</th>
				<td id="year"></td>
			</tr>	
			<tr>
				<th class="h_22" scope="row">제작기관</th>
				<td id="institution"></td>
			</tr>
			<tr>
				<th class="h_22" scope="row">투명도</th>
				<td id="opacity">
					<div class="div_slider"><input type="text" class="txt_opacity" readonly="readonly"/></div>				
				</td>
			</tr>
		</tbody>
	</table>
</div>
<p class="btnRight v2">
	<a id="a_close_topographic" href="#" class="btn_close"></a>
</p>
