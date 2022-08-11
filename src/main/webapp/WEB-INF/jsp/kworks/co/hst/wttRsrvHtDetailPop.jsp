<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/kworks/mps/inc/taglib_common.jsp"%>
<input id="wttRsrvHtHstDetailFtrCde" type="hidden" value ="${result.ftrCde}" />
<input id="wttRsrvHtHstDetailFtrIdn" type="hidden" value ="${result.ftrIdn}" />
<input id="wttRsrvHtHstDetailShtIdn" type="hidden" value ="${result.shtIdn}" />
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
			<th scope="row">청소일자</th>
			<td>${result.clnYmd}</td>
		</tr>
		<tr>
			<th scope="row">청소업체명</th>
			<td>${result.clnNam}</td>
		</tr>
		<tr>
			<th scope="row">청소내용</th>
			<td>${result.clnExp}</td>
		</tr>
	</tbody>
</table>

<p class="btnRight">
	<a href="#" onclick="fn_wttRsrvHtHstEdit();"><img src="<c:url value='/images/kworks/map/skin2/btn_edit2.png' />" alt="편집"></a>
	<a href="#" onclick="cleanHistoryMng.viewClose();"><img src="<c:url value='/images/kworks/map/skin2/btn_close.gif' />" alt="닫기"></a>
</p>
<script>
	function fn_wttRsrvHtHstEdit(){
		var param = {};
		param.ftrCde = $("#wttRsrvHtHstDetailFtrCde").val();
		param.ftrIdn = $("#wttRsrvHtHstDetailFtrIdn").val();
		param.shtIdn = $("#wttRsrvHtHstDetailShtIdn").val();
		cleanHistoryMng.viewCloseAndEditView(param);
	}
</script>