<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/kworks/mps/inc/taglib_common.jsp"%>
<input id="hstDetailFtrCde" type="hidden" value ="${result.ftrCde}" />
<input id="hstDetailFtrIdn" type="hidden" value ="${result.ftrIdn}" />
<input id="hstDetailShtIdn" type="hidden" value ="${result.shtIdn}" />
<table class="cmmTable v2 ma_b_10" summary="">
	<caption></caption>
	<colgroup>
		<col width="30%">
		<col width="*">
	</colgroup>
	<tbody>
		<tr>
			<th scope="row">지형지물부호</th>
			<td>${result.ftrCdeNm}</td>
		</tr>
		<tr>
			<th scope="row">관리번호</th>
			<td>${result.ftrIdn}</td>
		</tr>
		<tr>
			<th scope="row">이력 관리번호</th>
			<td>${result.shtIdn}</td>
		</tr>
		<tr>
			<th scope="row">관리이력구분</th>
			<td>${result.mnhNm}</td>
		</tr>
		<tr>
			<th scope="row">보수사유</th>
			<td>${result.sbjCde}</td>
		</tr>
		<tr>
			<th scope="row">보수내용</th>
			<td>${result.repDes}</td>
		</tr>
		<tr>
			<th scope="row">보수시작일</th>
			<td>${result.sreYmd}</td>
		</tr>
		<tr>
			<th scope="row">보수종료일</th>
			<td>${result.ereYmd}</td>
		</tr>
		<tr>
			<th scope="row">공사관리번호</th>
			<td>${result.cntIdn}</td>
		</tr>
	</tbody>
</table>

<p class="btnRight">
	<a href="#" onclick="fn_historyEdit();"><img src="<c:url value='/images/kworks/map/skin2/btn_edit2.png' />" alt="편집"></a>
	<a href="#" onclick="historyMng.viewClose();"><img src="<c:url value='/images/kworks/map/skin2/btn_close.gif' />" alt="닫기"></a>
</p>
<script>
	function fn_historyEdit(){
		var param = {};
		param.ftrCde = $("#hstDetailFtrCde").val();
		param.ftrIdn = $("#hstDetailFtrIdn").val();
		param.shtIdn = $("#hstDetailShtIdn").val();
		historyMng.viewCloseAndEditView(param);
	}
</script>