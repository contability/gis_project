<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 			uri="http://java.sun.com/jsp/jstl/core" %>

<script type="text/javascript" src="<c:url value='/js/kworks/manage/restore.js' />"></script>

<div id="div_restore_data" class="panel cont">

	<h1>연계 데이터 복원</h1>
	<hr class="hr_title" />
	
	<!-- 데이터 목록 -->
	<div class="w_425 pa_l_15 pa_r_15 f_l">
		<h2>연계 데이터</h2>
		<hr class="hr_subTitle" />
			<div style="height:421px; overflow-y: scroll;">
				<table class="tbl_cntc_data_list" >
					<colgroup>
						<col width="35%">
						<col width="65%">
					</colgroup>
					<thead>
						<tr>
							<th>데이터명</th>
							<th>데이터 수</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="row" items="${rows}">
							<tr data-id="${row.dataId}" style="cursor:pointer">
								<td>${row.dataAlias}</td>
								<td>${row.dataCo}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
	</div>
	
	<!-- 도메인 코드 관리 -->
	<div class="w_425 pa_l_15 pa_r_15 f_l">
		<h2>백업 데이터 목록</h2>
		<hr class="hr_subTitle" />
		
		<div style="height:421px; overflow-y: scroll;">
			<table class="tbl_backup_data_list" >
				<colgroup>
					<col width="25%">
					<col width="50%">
					<col width="25%">
				</colgroup>
				<thead>
					<tr>
						<th>백업아이디</th>
						<th>데이터 수</th>
						<th>복원</th>
					</tr>
				</thead>
				<tbody>
				</tbody>
			</table>
		</div>
	</div>
</div>