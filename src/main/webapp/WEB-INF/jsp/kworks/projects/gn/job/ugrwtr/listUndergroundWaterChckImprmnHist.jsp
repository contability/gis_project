<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/kworks/mps/inc/taglib_common.jsp"%>

<div id="etUL_main" class="layerCont">
	<input type="hidden" id="permNtNo" name="permNtNo" value="${permNtNo}" />
	<table class="cmmTable ma_t_20" summary="지하수관정 점검정비이력 정보 테이블">
		<caption>지하수관정 점검정비이력 정보 테이블</caption>
		<colgroup>
			<col width="15%">
			<col width="15%">
			<col width="20%">
			<col width="*%">
		</colgroup>
		<thead>
			<tr>
				<th scope="col">구분</th>
				<th scope="col">날짜</th>
				<th scope="col">소속성명</th>
				<th scope="col">적요</th>
			</tr>
		</thead>
	</table>
	<div class="scrollBox h_210">
		<table id="undrWtrTubChkRecdData" class="cmmTable rowLink" summary="지하수관정 점검정비이력 테이블 관련 구분,날짜,소속성명,적요 제공">
			<caption>하천측정 점검정비이력 테이블</caption>
			<colgroup>
				<col width="15%">
				<col width="15%">
				<col width="20%">
				<col width="*%">
			</colgroup>
			<tbody>
				<c:forEach items="${result}" var="result" varStatus="status">
					<tr chkIdn="${result.chkIdn}">
						<td>
							<c:forEach items="${chkCde}" var="chkCde">
								<c:if test="${chkCde.codeId eq result.chkCde}">${chkCde.codeNm}</c:if>
							</c:forEach>
						</td>
						<td>${result.chkYmd}</td>
						<td>${result.chkUsr}</td>
						<td>${result.chkDes}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</div>
<p class="btnRight">
	<a href="#" class="a_add_listUgrwtrChckHist btnBlue">등록</a>
	<a href="#" class="a_close_listUgrwtrChckHist btnBlue">닫기</a>
</p>
