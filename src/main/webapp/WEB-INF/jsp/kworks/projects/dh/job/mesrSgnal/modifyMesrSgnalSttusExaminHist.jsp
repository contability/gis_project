<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 			uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" 			uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="fmt" 		uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form"		uri="http://www.springframework.org/tags/form" %>

<form id="modifyMesrSgnalSttusExaminHist" name="modifyMesrSgnalSttusExaminHist" method="post" enctype="multipart/form-data" >
	<input type="hidden" id="ftfIdn" name="ftfIdn" value="${result.ftfIdn}" />
	<input type="hidden" id="ftfCde" name="ftfCde" value="${result.ftfCde}" />
	<input type="hidden" id="ftrIdn" name="ftrIdn" value="${result.ftrIdn}" />
	<input type="hidden" id="ftrCde" name="ftrCde" value="${result.ftrCde}" />
	<div class="layerCont">
		<table class="cmmTable v2 ma_b_30" summary="기준점 정보 관련 테이블">
			<caption>기준점 정보 테이블</caption>
			<colgroup>
				<col width="22%" />
				<col width="28%" />
				<col width="20%" />
				<col width="30%" />
			</colgroup>
			<tbody>
				<tr>
					<th scope="row">기준점번호</th>
					<td>
						${result.ftfIdn}
					</td>
					<th scope="row">명칭 및 종류</th>
					<td>
						<c:forEach items="${ftfCde}" var="ftfCde">
							<c:if test="${ftfCde.codeId eq result.ftfCde}">${ftfCde.codeNm}</c:if>
						</c:forEach>
					</td>
				</tr>
				<tr>
					<th scope="row">보고대상년도</th>
					<td>
						<input type="text" name="rptYmy" class="inputText w100" value="${result.rptYmy}" />
					</td>
					<th scope="row">조사년월일</th>
					<td>
						<input type="text" name="svrYmd" class="inputText easyui-datebox" value="${result.svrYmd}" style="width:153px" maxlength="10" />
					</td>
				</tr>
				<tr>
					<th scope="row">기준점표석상태</th>
					<td>
						<select name="cpsCde" class="selectBox w100">
							<c:forEach items="${cpsCde}" var="cpsCde" varStatus="status">
								<option value="${cpsCde.codeId}" ${cpsCde.codeId==result.cpsCde ? 'selected' : ''}>${cpsCde.codeNm}</option>
							</c:forEach>
						</select>
					</td>
					<th scope="row">조사작성자소속</th>
					<td>
						<input type="text" name="svgNam" class="inputText w100" value="${result.svgNam}" />
					</td>
				</tr>
				<tr>
					<th scope="row">상부표석상태</th>
					<td>
						<select name="spsCde" class="selectBox w100">
							<c:forEach items="${spsCde}" var="spsCde" varStatus="status">
								<option value="${spsCde.codeId}" ${spsCde.codeId==result.spsCde ? 'selected' : ''}>${spsCde.codeNm}</option>
							</c:forEach>
						</select>
					</td>
					<th scope="row">조사작성자직급</th>
					<td>
						<input type="text" name="svpPst" class="inputText w100" value="${result.svpPst}" />
					</td>
				</tr>
				<tr>
					<th scope="row">하부표석상태</th>
					<td>
						<select name="sbsCde" class="selectBox w100">
							<c:forEach items="${sbsCde}" var="sbsCde" varStatus="status">
								<option value="${sbsCde.codeId}" ${sbsCde.codeId==result.sbsCde ? 'selected' : ''}>${sbsCde.codeNm}</option>
							</c:forEach>
						</select>
					</td>
					<th scope="row">조사작성자성명</th>
					<td>
						<input type="text" name="svpNam" class="inputText w100" value="${result.svpNam}" />
					</td>
				</tr>
				<tr>
					<th scope="row">조사자판단표석상태</th>
					<td colspan="3">
						<select name="dcsCde" class="selectBox w100">
							<c:forEach items="${dcsCde}" var="dcsCde" varStatus="status">
								<option value="${dcsCde.codeId}" ${dcsCde.codeId==result.dcsCde ? 'selected' : ''}>${dcsCde.codeNm}</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<th scope="row">토지소유자성명</th>
					<td colspan="3">
						<input type="text" name="lndNam" class="inputText w100" value="${result.lndNam}" />
					</td>
				</tr>
				<tr>
					<th scope="row">토지소유자지번주소</th>
					<td colspan="3">
						<input type="text" name="lndPad" class="inputText w100" value="${result.lndPad}" />
					</td>
				</tr>
				<tr>
					<th scope="row">토지소유자도로명주소</th>
					<td colspan="3">
						<input type="text" name="lndRad" class="inputText w100" value="${result.lndRad}" />
					</td>
				</tr>
				<tr>
					<th scope="row">비고</th>
					<td colspan="3">
						<textarea name="rmkDes" class="textArea w100 h_50">${result.rmkDes}</textarea>
					</td>
				</tr>
			</tbody>
		</table>
		<table class="cmmTable" summary="현장사진 테이블">
			<caption>현장사진 테이블</caption>
			<colgroup>
				<col width="25%" />
				<col width="*" />
			</colgroup>
			<thead>
				<tr>
					<th scope="col">현장사진</th>
					<th scope="col">파일명</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td>근경</td>
					<td class="left"><input type="file" name="closeRangeView" /></td>
				</tr>
				<tr>
					<td>원경</td>
					<td class="left"><input type="file" name="distantView" /></td>
				</tr>
			</tbody>
		</table>
	</div>
	<p class="btnRight v2">
		<a id="a_save_modifyMesrSgnalSttusExaminHist" href="#" class="btnBlue">저장</a>
	</p>
</form>
