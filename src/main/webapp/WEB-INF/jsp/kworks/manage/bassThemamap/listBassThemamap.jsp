<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 			uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"			uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form"		uri="http://www.springframework.org/tags/form" %>

<script type="text/javascript" src="<c:url value='/js/kworks/manage/bassThemamap.js' />"></script>

<div id="div_bass_themamap_list" class="panel cont">

	<h1>주제도 관리</h1>
	<hr class="hr_title" />
	
	<div class="t_a_l f_s_15 c_666 bold">총계 : ${fn:length(rows)}</div>
	
	<form:form commandName="searchDTO" method="get" >
		<table class="tbl_bass_themamap_list" >
			<colgroup>
				<col width="5%">
				<col width="20%">
				<col width="25%">
				<col width="*">
			</colgroup>
			<thead>
				<tr>
					<th>번호</th>
					<th>시스템명</th>
					<th>기본 주제도</th>
					<th>주제도 설명</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="row" items="${rows}" varStatus="status">
					<c:forEach var="themamap" items="${themamapList}">
						<c:if test="${row.themamapId eq themamap.themamapId}">
							<tr data-sys-id="${row.sysId}" style="cursor:pointer">
								<td>${status.count}</td>
								<td>${row.sysAlias}</td>
								<td>${themamap.themamapNm}</td>
								<td>${themamap.themamapDc}</td>
							</tr>
						</c:if>
					</c:forEach>
				</c:forEach>
			</tbody>
		</table>
	</form:form>
</div>

<div id="div_bass_themamap_modify" class="div_modal" title="기본 주제도 설정">
	<form method="post">
    	<fieldset>
    		<div>
    			<span class="title">
    				시스템 명
    			</span>
    			<span class="content themamapId">
      			</span>
    		</div>
    		<div>
    			<span class="title">
	    			<label for="sel_bass_themamap_modify_themamap">기본 주제도</label>
	    		</span>
    			<span class="content">
   					<select id="sel_bass_themamap_modify_themamap" name="themamapId">
   						<c:forEach var="themamap" items="${themamapList}">
   							<option value="${themamap.themamapId}">${themamap.themamapNm}</option>
   						</c:forEach>
   					</select>
				</span>
			</div>
    	</fieldset>
	</form>
</div>