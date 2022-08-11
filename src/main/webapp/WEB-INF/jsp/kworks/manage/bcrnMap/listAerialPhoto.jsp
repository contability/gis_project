<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 			uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"			uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="ui" 			uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="form"		uri="http://www.springframework.org/tags/form" %>

<script type="text/javascript" src="<c:url value='/webjars/ckeditor/4.5.8/full/ckeditor.js' />"></script>
<script type="text/javascript" src="<c:url value='/webjars/ckeditor/4.5.8/full/adapters/jquery.js' />"></script>
<script type="text/javascript" src="<c:url value='/js/kworks/cmmn/ckeditorconfig.js' />"></script>
<script type="text/javascript" src="<c:url value='/js/kworks/manage/aerialPhoto.js' />"></script>

<div id="div_aerialPhoto_list" class="panel cont">

	<h1>항공사진 관리</h1>
	<hr class="hr_title" />
	
	<div class="t_a_l f_s_15 c_666 bold">총계 : ${fn:length(rows)}</div>
	
	<form:form commandName="searchDTO" method="get" >
		<table class="tbl_aerialPhoto_list" >
			<colgroup>
				<col width="10%">
				<col width="25%">
				<col width="25%">
				<col width="10%">
				<col width="*">
				<col width="10%">
				<col width="10%">
				<col width="10%">
			</colgroup>
			<thead>
				<tr>
					<th>번호</th>
					<th>항공사진명</th>
					<th>항공사진설명</th>
					<th>확장자</th>
					<th>요청포멧</th>
					<th>초기사용여부</th>
					<th>배경지도<br>사용여부</th>
					<th>레이어<br>사용여부</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="row" items="${rows}" varStatus="status">
					<tr data-map-no="${row.mapNo}">
						<td>${status.count}</td>
						<td>${row.title}</td>
						<td>${row.description}</td>
						<td>${row.extension}</td>
						<td>${row.requestFormat}</td>
						<td>${row.baseYn}</td>
						<td>${row.baseMapYn}</td>
						<td>${row.layerYn}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</form:form>
</div>

<div id="div_aerialPhoto_modify" class="div_modal" title="항공사진  정보 관리">
	<form method="post">
		<fieldset>
			<div>
				<span class="title">
	   				<label for="txt_aerialPhoto_modify_title">항공사진명</label>
	   			</span>
	   			<span class="content_half">
	   				<input type="text" name="title" id="txt_aerialPhoto_modify_mapTitle" value="" maxlength="50"/>
	   			</span>
				<span class="title">
	   				<label for="txt_aerialPhoto_modify_description">항공사진 설명</label>
	   			</span>
	   			<span class="content_half">
	   				<input type="text" name="description" id="txt_aerialPhoto_modify_description" value="" maxlength="50"/>
	   			</span>
   			</div>
			<div>
				<span class="title">
	   				<label for="txt_aerialPhoto_modify_requestFormat">요청 포멧 </label>
	   			</span>
	   			<span class="content_490">
	   				<input type="text" name="requestFormat" id="txt_aerialPhoto_modify_requestFormat" value="" maxlength="500"/>
	   			</span>
   			</div>
   			<div>
				<span class="title">
	   				<label for="txt_aerialPhoto_modify_photographyYear">촬영년도</label>
	   			</span>
	   			<span class="content_half">
	   				<input type="text" name="photographyYear" id="txt_aerialPhoto_modify_photographyYear" value="" maxlength="500"/>
	   			</span>
				<span class="title">
	   				<label for="txt_aerialPhoto_modify_photographyInstitution">촬영기관</label>
	   			</span>
	   			<span class="content_half">
	   				<input type="text" name="photographyInstitution" id="txt_aerialPhoto_modify_photographyInstitution" value="" maxlength="500"/>
	   			</span>
   			</div>
			<div>
				<span class="title">
	   				<label for="txt_aerialPhoto_modify_resolution">해상도</label>
	   			</span>
	   			<span class="content_half">
	   				<input type="text" name="resolution" id="txt_aerialPhoto_modify_resolution" value="" maxlength="500"/>
	   			</span>
				<span class="title">
	   				<label for="txt_aerialPhoto_modify_baseYn">초기사용여부</label>
	   			</span>
	   			<span class="content_half">
	   				<select id="select_aerialPhoto_modify_baseYn" name="baseYn">
	   					<option value="Y">Y</option>
	   					<option value="N">N</option>
	   				</select>
	   			</span>
   			</div>
   			<div>
				<span class="title">
	   				<label for="txt_aerialPhoto_modify_baseMapYn">배경지도<br>사용여부</label>
	   			</span>
	   			<span class="content_half">
	   				<select id="select_aerialPhoto_modify_baseMapYn" name="baseMapYn">
	   					<option value="Y">Y</option>
	   					<option value="N">N</option>
	   				</select>
	   			</span>
				<span class="title">
	   				<label for="txt_aerialPhoto_modify_layerYn">레이어<br>사용여부</label>
	   			</span>
	   			<span class="content_half">
	   				<select id="select_aerialPhoto_modify_layerYn" name="layerYn">
	   					<option value="Y">Y</option>
	   					<option value="N">N</option>
	   				</select>
	   			</span>
   			</div>
		</fieldset>
	</form>
</div>