<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 			uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"			uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="ui" 			uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="form"		uri="http://www.springframework.org/tags/form" %>

<script type="text/javascript" src="<c:url value='/webjars/ckeditor/4.5.8/full/ckeditor.js' />"></script>
<script type="text/javascript" src="<c:url value='/webjars/ckeditor/4.5.8/full/adapters/jquery.js' />"></script>
<script type="text/javascript" src="<c:url value='/js/kworks/cmmn/ckeditorconfig.js' />"></script>
<script type="text/javascript" src="<c:url value='/js/kworks/manage/bcrnMap.js' />"></script>

<div id="div_bcrnMap_list" class="panel cont">

	<h1>배경지도 관리</h1>
	<hr class="hr_title" />
	
	<div class="t_a_l f_s_15 c_666 bold">총계 : ${fn:length(rows)}</div>
	
	<form:form commandName="searchDTO" method="get" >
		<table class="tbl_bcrnMap_list" >
			<colgroup>
				<col width="10%">
				<col width="25%">
				<col width="25%">
				<col width="10%">
				<col width="*">
				<col width="10%">
			</colgroup>
			<thead>
				<tr>
					<th>번호</th>
					<th>배경지도명</th>
					<th>배경지도설명</th>
					<th>확장자</th>
					<th>요청포멧</th>
					<th>초기사용여부</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="row" items="${rows}" varStatus="status">
					<tr data-map-no="${row.mapNo}" style="cursor:pointer">
						<td>${status.count}</td>
						<td>${row.title}</td>
						<td>${row.description}</td>
						<td>${row.extension}</td>
						<td>${row.requestFormat}</td>
						<td>${row.baseYn}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</form:form>
</div>

<div id="div_bcrnMap_modify" class="div_modal" title="배경지도 편집">
	<form method="post">
		<fieldset>
			<div>
				<span class="title">
	   				<label for="txt_bcrnMap_modify_title">배경지도명</label>
	   			</span>
	   			<span class="content mapTitle"></span>
   			</div>
			<div>
				<span class="title">
	   				<label for="txt_bcrnMap_modify_description">배경지도설명</label>
	   			</span>
	   			<span class="content">
	   				<input type="text" name="description" id="txt_bcrnMap_modify_description" value="" maxlength="50"/>
	   			</span>
   			</div>
			<div>
				<span class="title">
	   				<label for="txt_bcrnMap_modify_requestFormat">요청 포멧</label>
	   			</span>
	   			<span class="content">
	   				<input type="text" name="requestFormat" id="txt_bcrnMap_modify_requestFormat" value="" maxlength="500"/>
	   			</span>
   			</div>
   			<div>
				<span class="title">
	   				<label for="txt_bcrnMap_modify_baseYn">초기사용여부</label>
	   			</span>
	   			<span class="content">
	   				<select id="select_bcrnMap_modify_baseYn" name="baseYn">
	   					<option value="Y">Y</option>
	   					<option value="N">N</option>
	   				</select>
	   			</span>
   			</div>
		</fieldset>
	</form>
</div>