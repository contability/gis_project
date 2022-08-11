<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 			uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" 			uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="fmt"			uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form"		uri="http://www.springframework.org/tags/form" %>

<style>
#userAcntMngRegstr_modify .table_text tbody tr td input{
	width: 100%;
}

</style>

<script type="text/javascript" src="<c:url value='/js/gn/manage/userAcntMngRegstr.js' />"></script>

<div id="userAcntMngRegstr_list" class="panel cont">

	<h1>사용자 권한부여 이력관리</h1>
	<hr class="hr_title" />
	
	<form:form commandName="searchDTO" id="frm_user_author_hist_list" name="frm_user_author_hist_list" method="get" >
		<div class="searchBox">
			<span class="sLabel">검색어</span>
			<form:select path="searchCondition" class="w_80" >
				<form:option value="1">성명</form:option>
				<form:option value="2">처리자</form:option>
			</form:select>
			<form:input name="searchKeyword" path="searchKeyword" class="w_190" value="" />
			&nbsp;
			&nbsp;
			<span class="sLabel">권한부여기간</span>
			<input name="searchStartDt" class="datepicker w_80" value="${searchDTO.searchStartDt}" />
			<span>~</span>
			<input name="searchEndDt" class="datepicker w_80" value="${searchDTO.searchEndDt}" />
			<span class="date1month"><img src="<c:url value='/images/kworks/map/skin2/btn_1month_off.png' />" alt="1개월" /></span>
			<span class="date3month"><img src="<c:url value='/images/kworks/map/skin2/btn_3month_off.png' />" alt="3개월" /></span>
			<span class="date6month"><img src="<c:url value='/images/kworks/map/skin2/btn_6month_off.png' />" alt="6개월" /></span>
			
			<a href="#" class="a_search">검색</a>
		</div>
		
		<div class="t_a_l f_s_15 c_666 bold">총계 : ${paginationInfo.totalRecordCount}</div>
	
		<table class="tbl_user_author_hist_list" >
			<colgroup>
				<col width="10%">
				<col width="14%">
				<col width="14%">
				<col width="*">
				<col width="14%">
				<col width="18%">
				<col width="14%">
			</colgroup>
			<thead>
				<tr>
					<th>번호</th>
					<th>소속</th>
					<th>성명</th>
					<th>접근권한</th>
					<th>처리내용</th>
					<th>처리일자</th>
					<th>처리자</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="row" items="${rows}" varStatus="status">
					<tr sn = ${row.sn }>
						<td>
							${paginationInfo.firstRecordIndex + status.index + 1}
						</td>
						<td>${row.deptNm}</td>
						<td>${row.userNm}</td>
						<td>${row.authorGroupNm}</td>
						<td>${row.prcrCn}</td>
						<td><fmt:formatDate pattern="yyyy-MM-dd hh:mm:ss" value="${row.gradAlwncDt}" /></td>
						<td>${row.gradUserNm}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		
		<div id="paginate" align="center">
			<ui:pagination paginationInfo = "${paginationInfo}" type="image" jsFunction="userAcntMngRegstrObj.search" />
			<form:hidden path="pageIndex"  />
		</div>			
	</form:form>
	
	<div class="div_btn">
		<a class="a_excelDownload_user_author_hist" href="#"><img src="<c:url value='/images/kworks/system/download.jpg' />" /></a>
	</div>
</div>

<div id="userAcntMngRegstr_modify" class="div_modal" title="사용자 권한부여 이력관리 수정">
		<form method="post">
			<input type="hidden" name="sn">
				<table class="table_text">
					<colgroup>
						<col width="10%">
						<col width="40%">
						<col width="10%">
						<col width="40%">
					</colgroup>
					<tbody>
						<tr>
							<th>소속</th>
							<td id="txt_userAcntMngRegstr_modify_deptNm"></td>
							<th>성명</th>
							<td id="txt_userAcntMngRegstr_modify_userNm"></td>
						</tr>
						<tr>
							<th><label for="txt_userAcntMngRegstr_modify_gradAlwncDt">처리일자</label></th>
							<td id="txt_userAcntMngRegstr_modify_gradAlwncDt"></td>
							<th><label for="txt_userAcntMngRegstr_modify_gradUserNm">처리자</label></th>
							<td id="txt_userAcntMngRegstr_modify_gradUserNm"></td>
						</tr>
						<tr>
							<th><label for="txt_userAcntMngRegstr_modify_authorGroupNm">접근권한</label></th>
							<td><input type="text" name="authorGroupNm" id="txt_userAcntMngRegstr_modify_authorGroupNm"></td>
							<th><label for="txt_userAcntMngRegstr_modify_prcrCn">처리내용</label></th>
							<td><input type="text" name="prcrCn" id="txt_userAcntMngRegstr_modify_prcrCn"></td>
						</tr>
						<tr>
							<th><label for="txt_userAcntMngRegstr_modify_jobNm">업무명</label></th>
							<td colspan="3"><input type="text" name="jobNm" id="txt_userAcntMngRegstr_modify_jobNm"></td>
						</tr>
					</tbody>
				</table>
		</form>
	</div>
