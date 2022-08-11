<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 			uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" 			uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="form"		uri="http://www.springframework.org/tags/form" %>

<script type="text/javascript" src="<c:url value='/js/kworks/bbs/notice.js' />"></script>

<div id="div_notice_list" class="panel cont">

	<h1>공지사항 조회</h1>
	<hr class="hr_title" />
	
	<form:form commandName="searchDTO" method="get" >
		<table class="tbl_notice_list" >
			<colgroup>
				<col width="10%">
				<col width="*">
				<col width="15%">
				<col width="15%">
			</colgroup>
			<thead>
				<tr>
					<th>번호</th>
					<th>제목</th>
					<th>작성일</th>
					<th>조회수</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="row" items="${rows}">
					<tr data-notice-no="${row.noticeNo}" style="cursor:pointer">
						<td>${row.noticeNo}</td>
						<td>${row.noticeSj}</td>
						<td>${row.frstRgsde}</td>
						<td class="rdCnt">${row.rdCnt}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		
		<div id="paginate" align="center">
			<ui:pagination paginationInfo = "${paginationInfo}" type="image" jsFunction="noticeObj.search" />
			<form:hidden path="pageIndex"  />
		</div>			
	</form:form>

</div>

<div id="div_notice_select" class="div_modal" title="공지사항 조회">
	<div>
		<div>
			<span class="title">제목</span>
			<span class="content noticeSj"></span>
		</div>
	   	<div>
	   		<span class="title">내용</span>
			<div class="content noticeCn"></div>
		</div>
	   	<div class="div_file_list">
	   		<span class="title">
	     		첨부파일
			</span>
	   		<div class="content">
	   			<ul class="fileList"></ul>
	   		</div>
	   	</div>
	</div>
</div>
