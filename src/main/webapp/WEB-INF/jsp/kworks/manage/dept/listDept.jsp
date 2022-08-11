<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 			uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" 			uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="form"		uri="http://www.springframework.org/tags/form" %>

<script type="text/javascript" src="<c:url value='/js/kworks/manage/dept.js' />"></script>

<div id="div_dept_list" class="panel cont">

	<h1>부서관리</h1>
	<hr class="hr_title" />
	
	<div class="t_a_l f_s_15 c_666 bold">총계 : ${paginationInfo.totalRecordCount}</div>
	
	<form:form commandName="searchDTO" method="get" >
		<table class="tbl_dept_list">
			<colgroup>
				<col width="5%">
				<col width="40%">
				<col width="*">
			</colgroup>
			<thead>
				<tr>
					<th>번호</th>
					<th>부서 코드</th>
					<th>부서 명</th>
				</tr>
			</thead>
			
			<tbody>
				<c:forEach var="row" items="${rows}" varStatus="status">
				<tr data-dept-code="${row.deptCode}" style="cursor:pointer">
					<td>
						${paginationInfo.firstRecordIndex + status.index + 1}
					</td>
					<td>${row.deptCode}</td>
					<td>${row.deptNm}</td>
				</tr>
				</c:forEach>
			</tbody>
		</table>
		<div id="paginate" align="center">
			<ui:pagination paginationInfo = "${paginationInfo}" type="image" jsFunction="deptObj.search" />
			<form:hidden path="pageIndex"  />
		</div>			
	</form:form>
	
	<div class="div_btn">
		<a class="a_new_dept" href="#"><img src="<c:url value='/images/kworks/system/btn_add.png' />" /></a>
		<a class="a_excelDownload_dept" href="#"><img src="<c:url value='/images/kworks/system/download.jpg' />" /></a>
	</div>
</div>

<!-- 부서코드 등록 팝업 -->
<div id="div_dept_add" class="div_modal" title="부서 추가">
	<form>
		<fieldset>
			<div>
				<span class="title">
	   				<label for="txt_dept_add_dept_cd">부서 코드</label>
	    		</span>
	    		<span class="content">
	   				<input type="text" name="deptCode" id="txt_dept_add_dept_cd" value="" maxlength="20">
	   			</span>
			</div>
			
			<div>
				<span class="title">
	   				<label for="txt_dept_add_dept_nm">부서 명</label>
	    		</span>
	    		<span class="content">
	   				<input type="text" name="deptNm" id="txt_dept_add_dept_nm" value="" maxlength="255">
	   			</span>
			</div>
		</fieldset>
	</form>
	
</div>

<!-- 부서코드 편집 팝업 -->
<div id="div_dept_modify" class="div_modal" title="부서 수정">
	<form>
		<fieldset>
			<div class="">
				<span class="title">
	   				부서 코드
	    		</span>
	    		<span class="content deptCode">
	   			</span>
			</div>
			
			<div>
				<span class="title">
	   				<label for="txt_dept_modify_dept_nm">부서 명</label>
	    		</span>
	    		<span class="content">
	   				<input type="text" name="deptNm" id="txt_dept_modify_dept_nm"  maxlength="255" />
	   			</span>
			</div>
		</fieldset>
	</form>
</div>
	
