<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 			uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" 			uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="form"		uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" 		uri="http://java.sun.com/jsp/jstl/fmt" %>

<script type="text/javascript" src="<c:url value='/js/kworks/manage/edit.js' />"></script>

<div id ="div_edit_manage_list" class="panel cont">
	<h1>편집여부 관리</h1>
	<hr class="hr_title"/>
	
	<form:form commandName="dataCtgrySearchDTO" method="get" >
		<div class="searchBox">
			<span class="sLabel">
  				데이터 카테고리
 			<form:select path="dataCtgry" class="w_180">
 				<form:option value="all">:::전체:::</form:option>
 				<c:forEach var="ctgryList" items="${dataCtgrylist}">
 					<form:option value="${ctgryList.dataCtgryId}">${ctgryList.dataCtgryNm}</form:option>
 				</c:forEach>
 			</form:select>
  			</span>
  			
			<span class="sLabel pa_l_10">검색어</span>
			<form:select path="searchCondition" class="w_110" >
				<form:option value="1">아이디</form:option>
				<form:option value="2">별칭</form:option>
				<form:option value="3">편집여부</form:option>
			</form:select>
			<form:input name="searchKeyword" path="searchKeyword" class="w_240" value="" />
			<a href="#" class="a_search">검색</a>
			
		</div>
		
		<div class="t_a_l f_s_15 c_666 bold">총계 : ${paginationInfo.totalRecordCount}</div>
	
		<table class="tbl_user_list" >
			<colgroup>
				<col width="35%">
				<col width="35%">
				<col width="30%">
				
			</colgroup>
			<thead>
				<tr>
					<th>데이터 아이디</th>
					<th>데이터 별칭</th>
					<th>편집 여부 </th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="row" items="${rows}" varStatus="status">
					<tr data-user-id="${row.dataId}" style="cursor:pointer">
						<td>${row.dataId}</td>
						<td>${row.dataAlias}</td>
						<td>${row.editAt}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		
		<div id="paginate" align="center">
			<ui:pagination paginationInfo = "${paginationInfo}" type="image" jsFunction="editObj.search" />
			<form:hidden path="pageIndex"  />
		</div>			
	</form:form>
	
</div>

<div id="div_edit_modify" class="div_modal" title="KWS_DATA 편집 여부 수정">
	<form method="post" >
    	<fieldset>
    		<div>
    			<span class="title">
    				<label for="span_edit_modify_dataId">데이터 아이디</label>
    			</span>
    			<span class="content">
    				<span class="content dataId"></span>
    			</span>
    		</div>
    		
    		<div>
    			<span class="title">
    				<label for="span_edit_modify_dataAlias">데이터 별칭</label>
    			</span>
    			<span class="content">
    				<span class="content dataAlias"></span>
    			</span>
    		</div>
    		
    		<div>
    			<span class="title">
    				<label for="span_edit_modify_editAt">편집 여부</label>
    			</span>
    			<span class="content">
    				<select name="editAt" id="txt_edit_modify_editAt">
    					<option value="Y">Y</option>
    					<option value="N">N</option>
    				</select>
    			</span>
    		</div>
    	</fieldset>
	</form>
</div>

