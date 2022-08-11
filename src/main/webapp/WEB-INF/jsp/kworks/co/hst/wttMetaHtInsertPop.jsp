<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/kworks/mps/inc/taglib_common.jsp"%>
<script>
$(document).ready(function(){
	//date format
	$("#wttMetaHtHstForm .easyui-datebox").datebox({
		formatter:myformatter,
		parser:myparser
	});
	
	$("#wttMetaHtHstForm .easyui-datebox").datebox('setValue','today');
});

</script>

<form id="wttMetaHtHstForm" name="wttMetaHtHstForm" method="post">
	<table class="cmmTable v2 ma_b_10" summary="">
		<caption></caption>
		<colgroup>
			<col width="40%">
			<col width="*">
		</colgroup>
		<tbody>
			<tr class="display_n">
				<th scope="row">지형지물부호</th>
				<td><input class="w_200" id="wttMetaHtHstFormFtrCde" name="wttMetaHtHstFormFtrCde" value="<c:out value='${vo.ftrCde }'/>" disabled="disabled" /></td>
			</tr>
			<tr>
				<th scope="row">지형지물부호</th>
				<td><input class="w_200" id="wttMetaHtHstFormFtrCdeNm" name="wttMetaHtHstFormFtrCdeNm" value="<c:out value='${vo.ftrCdeNm }'/>" disabled="disabled" /></td>
			</tr>
			<tr>
				<th scope="row">관리번호</th>
				<td><input class="w_200" id="wttMetaHtHstFormFtrIdn" name="wttMetaHtHstFormFtrIdn" value="<c:out value='${vo.ftrIdn }'/>" disabled="disabled" /></td>
			</tr>
			<tr>
				<th scope="row">교체구분</th>
				<td>
					<select id="wttMetaHtHstFormGcwCde" name="wttMetaHtHstFormGcwCde" class="w_200">
						<c:forEach items="${gcwCdeList}" var="gcwList">
							<option value="${gcwList.codeValue}">${gcwList.codeNm}</option>
						</c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<th scope="row">교체사유</th>
				<td>
					<select id="wttMetaHtHstFormCrsCde" name="wttMetaHtHstFormCrsCde" class="w_200">
						<c:forEach items="${crsCdeList}" var="crsList">
							<option value="${crsList.codeValue}">${crsList.codeNm}</option>
						</c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<th scope="row">교체일자</th>
				<td><input class="inputText easyui-datebox w_200" type="text" id="wttMetaHtHstFormChgYmd" name="wttMetaHtHstFormChgYmd" value="" /></td>
			</tr>
			<tr>
				<th scope="row">철거계량기 기물번호</th>
				<td><input class="w_200" id="wttMetaHtHstFormOmeNum" name="wttMetaHtHstFormOmeNum" value="" /></td>
			</tr>
			<tr>
				<th scope="row">철거계량기 구경</th>
				<td><input class="w_200" type="number" id="wttMetaHtHstFormOmeDip" name="wttMetaHtHstFormOmeDip" value="" /></td>
			</tr>
			<tr>
				<th scope="row">철거계량기 형식</th>
				<td>
					<select id="wttMetaHtHstFormOmeMof" name="wttMetaHtHstFormOmeMof" class="w_200">
						<c:forEach items="${mofCdeList}" var="mofList">
							<option value="${mofList.codeValue}">${mofList.codeNm}</option>
						</c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<th scope="row">철거계량기 지침수</th>
				<td><input class="w_200" type="number" id="wttMetaHtHstFormOmeCnt" name="wttMetaHtHstFormOmeCnt" value="" /></td>
			</tr>
			<tr>
				<th scope="row">철거계량기 제작회사</th>
				<td><input class="w_200" id="wttMetaHtHstFormOmeNam" name="wttMetaHtHstFormOmeNam" value="" /></td>
			</tr>
			<tr>
				<th scope="row">교체자명</th>
				<td><input class="w_200" id="wttMetaHtHstFormChgNam" name="wttMetaHtHstFormChgNam" value="" /></td>
			</tr>
		</tbody>
	</table>
</form>
<p class="btnRight v2">
	<a href="#" class="btn_save2" onclick="changeHistoryMng.insert();"></a>
	<a href="#" class="btn_close" onclick="changeHistoryMng.insertClose();"></a>
</p>