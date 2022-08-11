<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 			uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" 			uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="fmt" 		uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form"		uri="http://www.springframework.org/tags/form" %>

<div class="layerCont">
	<input type="hidden" id="ftrIdn" name="ftrIdn" value="${result.ftrIdn}" />
	<input type="hidden" id="ftrCde" name="ftrCde" value="${result.ftrCde}" />
	<input type="hidden" id="ftfIdn" name="ftfIdn" value="${result.ftfIdn}" />
	<input type="hidden" id="ftfCde" name="ftfCde" value="${result.ftfCde}" />
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
					${result.rptYmy}
				</td>
				<th scope="row">조사년월일</th>
				<td>
					${result.svrYmd}
				</td>
			</tr>
			<tr>
				<th scope="row">기준점표석상태</th>
				<td>
					<c:forEach items="${cpsCde}" var="cpsCde">
						<c:if test="${cpsCde.codeId eq result.cpsCde}">${cpsCde.codeNm}</c:if>
					</c:forEach>
				</td>
				<th scope="row">조사작성자소속</th>
				<td>
					${result.svgNam}
				</td>
			</tr>
			<tr>
				<th scope="row">상부표석상태</th>
				<td>
					<c:forEach items="${spsCde}" var="spsCde">
						<c:if test="${spsCde.codeId eq result.spsCde}">${spsCde.codeNm}</c:if>
					</c:forEach>
				</td>
				<th scope="row">조사작성자직급</th>
				<td>
					${result.svpPst}
				</td>
			</tr>
			<tr>
				<th scope="row">하부표석상태</th>
				<td>
					<c:forEach items="${sbsCde}" var="sbsCde">
						<c:if test="${sbsCde.codeId eq result.sbsCde}">${sbsCde.codeNm}</c:if>
					</c:forEach>
				</td>
				<th scope="row">조사작성자성명</th>
				<td>
					${result.svpNam}
				</td>
			</tr>
			<tr>
				<th scope="row">조사자판단표석상태</th>
				<td colspan="3">
					<c:forEach items="${dcsCde}" var="dcsCde">
						<c:if test="${dcsCde.codeId eq result.dcsCde}">${dcsCde.codeNm}</c:if>
					</c:forEach>
				</td>
			</tr>
			<tr>
				<th scope="row">토지소유자성명</th>
				<td colspan="3">
					${result.lndNam}
				</td>
			</tr>
			<tr>
				<th scope="row">토지소유자지번주소</th>
				<td colspan="3">
					${result.lndPad}
				</td>
			</tr>
			<tr>
				<th scope="row">토지소유자도로명주소</th>
				<td colspan="3">
					${result.lndRad}
				</td>
			</tr>
			<tr>
				<th scope="row">비고</th>
				<td colspan="3">
					${result.rmkDes}
				</td>
			</tr>
		</tbody>
	</table>
	<table class="cmmTable v2 ma_b_30" summary="사진 테이블">
		<caption>사진 테이블</caption>
		<colgroup>
			<col width="20%" />
			<col width="25%" />
			<col width="20%" />
			<col width="25%" />
		</colgroup>
		<tbody>
			<tr>
				<th class="center" scope="row" colspan="2">근경사진</th>
				<th class="center" scope="row" colspan="2">원경사진</th>
			</tr>
			<tr>
				<td colspan="2" class="h_200">
					<c:if test="${result.mesrSgnalSttusExaminHistCloseRangeView ne null}">
						<img src="<c:url value='/cmmn/image/${result.mesrSgnalSttusExaminHistCloseRangeView.imageNo}/thumbnail.do'/>" alt="근경 사진" />
					</c:if>
				</td>
				<td colspan="2" class="h_200">
					<c:if test="${result.mesrSgnalSttusExaminHistDistantView ne null}">
						<img src="<c:url value='/cmmn/image/${result.mesrSgnalSttusExaminHistDistantView.imageNo}/thumbnail.do'/>" alt="원경 사진" />
					</c:if>
				</td>
			</tr>
		</tbody>
	</table>
</div>
<p class="btnRight v2">
	<a id="a_print_viewMesrSgnalSttusExaminHist" href="#" class="btnBlue">출력</a>
	<a id="a_modify_viewMesrSgnalSttusExaminHist" href="#" class="btnBlue">수정</a>
</p>
