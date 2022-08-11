<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/kworks/mps/inc/taglib_common.jsp"%>
<input id="wttMetaHtDetailFtrCde" type="hidden" value ="${result.ftrCde}" />
<input id="wttMetaHtDetailFtrIdn" type="hidden" value ="${result.ftrIdn}" />
<input id="wttMetaHtDetailShtIdn" type="hidden" value ="${result.shtIdn}" />
<table class="cmmTable v2 ma_b_10" summary="">
	<caption></caption>
	<colgroup>
		<col width="40%">
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
			<th scope="row">교체일자</th>
			<td>${result.chgYmd}</td>
		</tr>
		<tr>
			<th scope="row">교체구분</th>
			<td>${result.gcwCdeNm}</td>
		</tr>
		<tr>
			<th scope="row">교체사유</th>
			<td>${result.crsCdeNm}</td>
		</tr>
		<tr>
			<th scope="row">철거계량기기물번호</th>
			<td>${result.omeNum}</td>
		</tr>
		<tr>
			<th scope="row">철거계량기구경</th>
			<td>${result.omeDip}</td>
		</tr>
		<tr>
			<th scope="row">철거계량기형식</th>
			<td>${result.omeMofNm}</td>
		</tr>
		<tr>
			<th scope="row">철거계량기지침수</th>
			<td>${result.omeCnt}</td>
		</tr>
		<tr>
			<th scope="row">철거계량기제작회사</th>
			<td>${result.omeNam}</td>
		</tr>
		<tr>
			<th scope="row">교체자명</th>
			<td>${result.chgNam}</td>
		</tr>
		
	</tbody>
</table>

<p class="btnRight">
	<a href="#" onclick="fn_historyEdit();"><img src="<c:url value='/images/kworks/map/skin2/btn_edit2.png' />" alt="편집"></a>
	<a href="#" onclick="changeHistoryMng.viewClose();"><img src="<c:url value='/images/kworks/map/skin2/btn_close.gif' />" alt="닫기"></a>
</p>
<script>
	function fn_historyEdit(){
		var param = {};
		param.ftrCde = $("#wttMetaHtDetailFtrCde").val();
		param.ftrIdn = $("#wttMetaHtDetailFtrIdn").val();
		param.shtIdn = $("#wttMetaHtDetailShtIdn").val();
		historyMng.viewCloseAndEditView(param);
	}
</script>