<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/kworks/mps/inc/taglib_common.jsp"%>
<script>
$(document).ready(function(){
	//date format
	$("#historyForm .easyui-datebox").datebox({
		formatter:myformatter,
		parser:myparser
	});
	
	$("#historyForm .easyui-datebox").datebox('setValue','today');
});

</script>

<form id="historyForm" name="historyForm" method="post">
	<table class="cmmTable v2 ma_b_10" summary="">
		<caption></caption>
		<colgroup>
			<col width="30%">
			<col width="*">
		</colgroup>
		<tbody>
			<tr>
				<th scope="row">지형지물부호</th>
				<td><input class="w_200" id="historyFormFtrCde" name="historyFormFtrCde" value="<c:out value='${vo.ftrCdeNm}' />" disabled="disabled" ftrCdeNm = "${vo.ftrCde}" /></td>
			</tr>
			<tr>
				<th scope="row">관리번호</th>
				<td><input class="w_200" id="historyFormFtrIdn" name="historyFormFtrIdn" value="<c:out value='${vo.ftrIdn}'/>" disabled="disabled" /></td>
			</tr>
			<tr>
				<th scope="row">관리이력구분</th>
				<td>
					<select id="historyFormMnhCde" class="w_200">
						<c:forEach items="${mnhCdeList}" var="mnhList">
							<option value="${mnhList.codeValue}">${mnhList.codeNm}</option>
						</c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<th scope="row">보수사유</th>
				<td><input class="w_200" id="historyFormSbjCde" name="historyFormSbjCde" value="" /></td>
			</tr>
			<tr>
				<th scope="row">보수내용</th>
				<td><textarea id="historyFormRepDes" name="historyFormRepDes" class="w_200 h_50"></textarea></td>
			</tr>
			<tr>
				<th scope="row">보수시작일</th>
				<td><input type="text" id="historyFormSreYmd" class="inputText easyui-datebox w_200" name="historyFormSreYmd" /></td>
			</tr>
			<tr>
				<th scope="row">보수종료일</th>
				<td><input type="text" id="historyFormEreYmd" class="inputText easyui-datebox w_200"  name="historyFormEreYmd" /></td>
			</tr>
			<tr>
				<th scope="row">공사관리번호</th>
				<td>
					 <input class="w_200" id="historyFormCntIdn" name="historyFormCntIdn" value="" /> 
					 <a href="#" class="ma_l_5 va_t" onclick="bisDocuObj.consMaListOpenRtnCode('historyFormCntIdn');"><img src="<c:url value='/images/kworks/map/skin2/btn_change.gif'/>" alt="변경" title="변경"  /></a>
				</td>
			</tr>
		</tbody>
	</table>
</form>
<p class="btnRight v2">
	<a href="#" class="btn_save2" onclick="historyMng.insert();"></a>
	<!-- <a href="#" class="btn_del" onclick="historyMng.remove();"></a> -->
	<a href="#" class="btn_close" onclick="historyMng.insertClose();"></a>
</p>