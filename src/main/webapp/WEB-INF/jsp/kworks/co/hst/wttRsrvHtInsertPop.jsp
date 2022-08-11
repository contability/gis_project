<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/kworks/mps/inc/taglib_common.jsp"%>
<script>
$(document).ready(function(){
	//date format
	$("#wttRsrvHtHstForm .easyui-datebox").datebox({
		formatter:myformatter,
		parser:myparser
	});
	
	$("#wttRsrvHtHstForm .easyui-datebox").datebox('setValue','today');
});

</script>

<form id="wttRsrvHtHstForm" name="wttRsrvHtHstForm" method="post">
	<table class="cmmTable v2 ma_b_10" summary="">
		<caption></caption>
		<colgroup>
			<col width="30%">
			<col width="*">
		</colgroup>
		<tbody>
			<tr class="display_n">
				<th scope="row">지형지물부호</th>
				<td><input class="w_200" id="wttRsrvHtHstFormFtrCde" name="wttRsrvHtHstFormFtrCde" value="<c:out value='${vo.ftrCde}'/>" disabled="disabled" /></td>
			</tr>
			<tr>
				<th scope="row">지형지물부호</th>
				<td><input class="w_200" value="<c:out value='${vo.ftrCdeNm}'/>" disabled="disabled" /></td>
			</tr>
			<tr>
				<th scope="row">관리번호</th>
				<td><input class="w_200" id="wttRsrvHtHstFormFtrIdn" name="wttRsrvHtHstFormFtrIdn" value="<c:out value='${vo.ftrIdn}'/>" disabled="disabled" /></td>
			</tr>
			<tr>
				<th scope="row">청소일자</th>
				<td><input class="inputText easyui-datebox w_200" type="text" id="wttRsrvHtHstFormClnYmd" name="wttRsrvHtHstFormClnYmd" value="" /></td>
			</tr>
			<tr>
				<th scope="row">청소업체명</th>
				<td><input class="w_200" id="wttRsrvHtHstFormClnNam" name="wttRsrvHtHstFormClnNam" value="" /></td>
			</tr>
			<tr>
				<th scope="row">청소내용</th>
				<td><textarea id="wttRsrvHtHstFormClnExp" name="wttRsrvHtHstFormClnExp" class="w_200 h_50"></textarea></td>
			</tr>
		</tbody>
	</table>
</form>
<p class="btnRight v2">
	<a href="#" class="btn_save2" onclick="cleanHistoryMng.insert();"></a>
	<a href="#" class="btn_close" onclick="cleanHistoryMng.insertClose();"></a>
</p>