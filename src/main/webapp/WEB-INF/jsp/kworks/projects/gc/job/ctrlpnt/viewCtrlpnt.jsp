<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 			uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" 			uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="fmt" 		uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form"		uri="http://www.springframework.org/tags/form" %>

<div id="addCtrlpnt" class="layerCont">
	<input type="hidden" id="ftrCde" value="${result.ftrCde}" />
	<input type="hidden" id="ftrIdn" value="${result.ftrIdn}" />
	<input type="hidden" id="bseNam" value="${result.bseNam}" />
	<table id="etUV_main" class="cmmTable v2 ma_b_30" summary="기준점 정보 관련 테이블" style="table-layout: fixed; word-wrap: break-word;">
		<caption>기준점 정보 테이블</caption>
		<colgroup>
			<col width="22%" />
			<col width="28%" />
			<col width="20%" />
			<col width="30%" />
		</colgroup>
		<tbody>
			<tr>
				<th scope="row">사업명</th>
				<td colspan="3">${result.prjNam}</td>
			</tr>
			<tr>
				<th scope="row">고시일자</th>
				<td>
					<%-- <fmt:parseDate value="${result.decYmd}" var="decYmd" pattern="yyyyMMdd" />
					<fmt:formatDate value="${decYmd}" pattern="yyyy-MM-dd" /> --%>
					${result.decYmd}
				</td>
				<th scope="row">고시번호</th>
				<td>${result.decNum}</td>
			</tr>
			<tr>
				<th scope="row">점의종류</th>
				<td>
					<c:forEach items="${cpkCde}" var="cpkCde">
						<c:if test="${cpkCde.codeId eq result.cpkCde}">${cpkCde.codeNm}</c:if>
					</c:forEach>
				</td>
				<th scope="row">점번호</th>
				<td>${result.bseNam}</td>
			</tr>
			<tr>
				<th scope="row">급수</th>	
				<td>
					<c:forEach items="${grdCde}" var="grdCde">
						<c:if test="${grdCde.codeId eq result.grdCde}">${grdCde.codeNm}</c:if>
					</c:forEach>
				</td>	
				<th scope="row">시행자</th>	
				<td>${result.wrkOrg}</td>
			</tr>
			<tr>
				<th scope="row">1/50,000도엽명</th>	
				<td>${result.s50Nam}</td>		
				<th scope="row">상태</th>	
				<td>
					<c:forEach items="${cpsCde}" var="cpsCde">
						<c:if test="${cpsCde.codeId eq result.cpsCde}">${cpsCde.codeNm}</c:if>
					</c:forEach>
				</td>	
			</tr>
			<tr>
				<th scope="row">소재지</th>	
				<td colspan="3">
					<c:forEach items="${bjdCde}" var="bjdCde">
						<c:if test="${bjdCde.codeId eq result.bjdCde}">${bjdCde.codeNm}</c:if>
					</c:forEach> 
				${result.setLoc}</td>
			</tr>	
			<tr>
				<th scope="row">매설일자</th>	
				<td>
					<%-- <fmt:parseDate value="${result.setYmd}" var="setYmd" pattern="yyyyMMdd" />
					<fmt:formatDate value="${setYmd}" pattern="yyyy-MM-dd" /> --%>
					${result.setYmd}
				</td>		
				<th scope="row">관측일자</th>	
				<td>
					<%-- <fmt:parseDate value="${result.obsYmd}" var="obsYmd" pattern="yyyyMMdd" />
					<fmt:formatDate value="${obsYmd}" pattern="yyyy-MM-dd" /> --%>
					${result.obsYmd}
				</td>
			</tr>	
			<tr>
				<th scope="row">매설자</th>	
				<td>${result.setNam}</td>		
				<th scope="row">관측자</th>	
				<td>${result.obsNam}</td>
			</tr>	
			<tr>
				<th scope="row">매설형태</th>	
				<td>${result.marSit}</td>		
				<th scope="row">관측장비</th>	
				<td>${result.obfNam}</td>
			</tr>	
			<tr>
				<th scope="row">공공기준점</th>	
				<td>신성과</td>		
				<th scope="row">공공수준점</th>	
				<td>
					<c:forEach items="${lskCde}" var="lskCde">
						<c:if test="${lskCde.codeId eq result.lskCde}">${lskCde.codeNm}</c:if>
					</c:forEach>
				</td>
			</tr>	
			<tr>
				<th scope="row">경로</th>	
				<td colspan="3">${result.setAdd}</td>	
			</tr>
			<tr>
				<th scope="row">비고</th>
				<td colspan="3">${result.refDes}</td>
			</tr>	
		</tbody>
	</table>
	<table class="cmmTable v2 ma_b_30" summary="성과 정보 관련 테이블">
		<caption>성과 정보 테이블</caption>
		<colgroup>
			<col width="*" />
			<col width="20%" />
			<col width="25%" />
			<col width="20%" />
			<col width="25%" />
		</colgroup>
		<tbody>
			<tr>
				<th scope="row" rowspan="5" class="center">성과</th>
				<th scope="row">좌표원점</th>
				<td colspan="3">${result.pntNam}</td>
			</tr>
			<tr>
				<th scope="row">경도(E)</th>
				<td>${result.ngwY}</td>
				<th scope="row">위도(N)</th>
				<td>${result.ngwX}</td>
			</tr>
			<tr>
				<th scope="row" colspan="4" class="center">GRS-80 타원체</th>
			</tr>
			<tr>
				<th scope="row">N(X)</th>	
				<td class="nggX">${result.nggY}</td>	
				<th scope="row">E(Y)</th>	
				<td class="nggY">${result.nggX}</td>
			</tr>
			<tr>
				<th scope="row">타원체고</th>	
				<td>${result.esdHgt}</td>		
				<th scope="row">정표고</th>	
				<td>${result.bseHgt}</td>
			</tr>
			<tr>
				<th scope="row" rowspan="4" class="center">사진</th>
				<th class="center" scope="row" colspan="2">약도</th>
				<th class="center" scope="row" colspan="2">시통도</th>
			</tr>
			<tr>
				<td colspan="2" class="h_200">
					<c:if test="${result.ctrlPntOutlineMap ne null}">
						<img src="<c:url value='/cmmn/image/${result.ctrlPntOutlineMap.imageNo}/thumbnail.do'/>" alt="약도 사진" />
					</c:if>
				</td>
				<td colspan="2" class="h_200">
					<c:if test="${result.ctrlPntVisibilityMap ne null}">
						<img src="<c:url value='/cmmn/image/${result.ctrlPntVisibilityMap.imageNo}/thumbnail.do'/>" alt="시통도 사진" />
					</c:if>
				</td>
			</tr>
			<tr>
				<th class="center" scope="row" colspan="2">근경</th>
				<th class="center" scope="row" colspan="2">원경</th>
			</tr>
			<tr>
				<td colspan="2" class="h_200">
					<c:if test="${result.ctrlPntCloseRangeView ne null}">
						<img src="<c:url value='/cmmn/image/${result.ctrlPntCloseRangeView.imageNo}/thumbnail.do'/>" alt="근경 사진" />
					</c:if>
				</td>
				<td colspan="2" class="h_200">
					<c:if test="${result.ctrlPntDistantView ne null}">
						<img src="<c:url value='/cmmn/image/${result.ctrlPntDistantView.imageNo}/thumbnail.do'/>" alt="원경 사진" />
					</c:if>
				</td>
			</tr>
		</tbody>
	</table>
</div>
<p class="btnRight v2">
	<a id="a_modify_ctrlpntAdd" href="#" class="btnBlue">편집</a>
	<a id="a_print_ctrlpntAdd" href="#" class="btnBlue">출력</a>
	<a id="a_lossSttemnt_ctrlpntAdd" href="#" class="btnBlue">망실신고</a>
	<c:if test="${isMesrSgnal eq true}" >
		<a id="a_mesrSgnalSttusExaminHist_ctrlpntAdd" href="#" class="btnBlue">측량표지현황조사이력</a>
	</c:if>
</p>

