<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/kworks/mps/inc/taglib_common.jsp"%>

<form id="undrWtrTubchkRecdEditform" name="undrWtrTubWelRecdEditform" method="post">
	<div class="layerCont">
		<input type="hidden" class="permNtNo" value="${result.permNtNo}" />
		<input type="hidden" class="chkIdn" value="${result.chkIdn}" />
		<table id="etUV_bassInfoEdit" class="cmmTable v2 ma_b_30" summary="지하수관정 점검정비이력 정보 수정 관련 테이블" style="table-layout: fixed; word-wrap: break-word;">
			<caption>지하수관정 점검정비이력 정보 수정 테이블</caption>
			<colgroup>
				<col width="30%" />
				<col width="35%" />
				<col width="35%" />
			</colgroup>
			<tbody>
				<tr>
					<th scope="row">구분</th>
					<td colspan="2">
						<select name="chkCde" class="selectBox w_100">
							<c:forEach items="${chkCde}" var="obj" varStatus="status">
								<option value="${obj.codeId}" ${obj.codeId == result.chkCde ? 'selected' : ''}>${obj.codeNm}</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<th scope="row">날짜</th>
					<td colspan="2">
						<input type="text" name="chkYmd" value="${result.chkYmd}" class="inputText easyui-datebox" style="width:100px" maxlength="10" />
					</td>
				</tr>
				<tr>
					<th scope="row">소속/성명</th>
					<td colspan="2"><input type="text" name="chkUsr" value="${result.chkUsr}" class="inputText w100" maxlength="100" /></td>
				</tr>
				<tr>
					<th scope="row">적요</th>
					<td colspan="2"><input type="text" name="chkDes" value="${result.chkDes}" class="inputText w100" maxlength="100" /></td>
				</tr>
			</tbody>
		</table>
	</div>
	<p class="btnRight v2">
		<a href="#" class="a_save_modifyUgrwtrHist btnBlue">저장</a>
		<a href="#" class="a_delete_modifyUgrwtrHist btnBlue">삭제</a>
		<a href="#" class="a_close_modifyUgrwtrHist btnBlue">닫기</a>
	</p>
</form>
