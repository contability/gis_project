<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 			uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" 			uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="fmt" 		uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form"		uri="http://www.springframework.org/tags/form" %>

<div class="undergroundWaterDetail over_ys h_560">
	<table class="cmmTable ma_r_10"">
		<colgroup>
			<col width="20%" />
			<col width="16%" />
			<col width="24%" />
			<col width="16%" />
			<col width="24%" />
		</colgroup>
		<tbody>
			<!-- 기본 정보 -->
			<tr class="box">
				<th class="title" rowspan="2">기본정보</th>
				
				<th scope="row">관리번호</th>
				<td>${result.mgNo}</td>
				<th scope="row">시설구분</th>
				<td>
					<c:forEach items="${listPermNtFormGbn}" var="listCode" >
						<c:if test="${listCode.codeId eq  result.permNtFormGbn}">
							${listCode.codeNm}
						</c:if>
					</c:forEach>
				</td>
			</tr>
			<tr>
				<th scope="row">허가신고번호</th>
				<td>${result.permNtNo}</td>
				<th scope="row">구분</th>
				<td>
					<c:forEach items="${listRgtMbdGbn}" var="listCode" >
						<c:if test="${listCode.codeId eq  result.rgtMbdGbn}">
							${listCode.codeNm}
						</c:if>
					</c:forEach>
					<%-- <fmt:parseDate value="${result.permNtYmd}" var="permNtYmd" pattern="yyyyMMdd" /><fmt:formatDate value="${permNtYmd}" pattern="yyyy-MM-dd" /> --%>
				</td>
			</tr>
			
			<!-- 이용자 정보 -->
			<tr class="box">
				<th class="title" rowspan="3">이용자 정보</th>
				
				<th scope="row">이용자구분</th>
				<td>
					<c:forEach items="${listRgtMbdGbn}" var="listCode2" >
						<c:if test="${listCode2.codeId eq  result.rgtMbdGbn}">
							${listCode2.codeNm}
						</c:if>
					</c:forEach>
				</td>
				<th scope="row">상호(성명)</th>
				<td>${result.rgtMbdNm}</td>
			</tr>
			<tr>
				<th scope="row" colspan="2">사업자번호(법인) / 생년월일(일반)</th>
				<td colspan="2">${result.rgtMbdRegNo}</td>
			</tr>
			<tr>
				<th scope="row">주소</th>
				<td colspan="3">${result.rgtMbdAddr}</td>
			</tr>
			
			<!-- 개발위치 정보 -->
			<tr class="box">
				<th class="title" rowspan="4">개발위치 정보</th>
				
				<th scope="row">개발위치</th>
				<td colspan="3">${result.dvopLocAddr}</td>
			</tr>
			<tr>
				<th scope="row">경도(도)</th>
				<td>${result.litdDg}</td>
				<th scope="row">위도(도)</th>
				<td>${result.lttdDg}</td>
			</tr>
			<tr>
				<th scope="row">경도(분)</th>
				<td>${result.litdMint}</td>
				<th scope="row">위도(분)</th>
				<td>${result.lttdMint}</td>
			</tr>
			<tr>
				<th scope="row">경도(초)</th>
				<td>${result.litdSc}</td>
				<th scope="row">위도(초)</th>
				<td>${result.lttdSc}</td>
			</tr>
			
			<!-- 용도 정보 -->
			<tr class="box">
				<th class="title" rowspan="2">용도 정보</th>
				
				<th scope="row">용도</th>
				<td>${result.uwaterSrv}</td>
				<th scope="row">세부용도</th>
				<td>
					<c:forEach items="${listUwaterDtlSrv}" var="listCode2" >
						<c:if test="${listCode2.codeId eq  result.uwaterDtlSrv}">
							${listCode2.codeNm}
						</c:if>
					</c:forEach>
				</td>
			</tr>
			<tr>
				<th scope="row">음용여부</th>
				<td colspan="3">${result.uwaterPotaYn}</td>
			</tr>
			
			<!-- 준공 정보 -->
			<tr class="box">
				<th class="title" rowspan="4">준공 정보</th>
				
				<th scope="row">굴착깊이</th>
				<td>${result.digDph}</td>
				<th scope="row">굴착지름</th>
				<td>${result.digDbt}</td>
			</tr>
			<tr>
				<th scope="row">취수계획량</th>
				<td>${result.frwPlnQua}</td>
				<th scope="row">소요수량</th>
				<td>${result.ndQt}</td>
			</tr>
			<tr>
				<th scope="row">동력장치마력</th>
				<td>${result.dynEqnHrp}</td>
				<th scope="row">토출관직경</th>
				<td>${result.pipeDiam}</td>
			</tr>
			<tr>
				<th scope="row">설치심도</th>
				<td>${result.esbDph}</td>
				<th scope="row">양수능력</th>
				<td>${result.rwtCap}</td>
			</tr>
			
			<!-- 폐공 정보 -->
			<tr class="box">
				<th class="title" rowspan="3">폐공 정보</th>
				
				<th scope="row">폐공발생원인</th>
				<td>${result.lnhoRaiseCau}</td>
				<th scope="row">폐공발생일</th>
				<td colspan="3">
					<fmt:parseDate value="${result.lnhoRaiseYmd}" var="lnhoRaiseYmd" pattern="yyyyMMdd" /><fmt:formatDate value="${lnhoRaiseYmd}" pattern="yyyy-MM-dd" />
				</td>
			</tr>
			<tr>
				<th scope="row">폐공처리방법</th>
				<td>${result.lnhoDealMet}</td>
				<th scope="row">원상복구방법</th>
				<td>${result.ostrsMet}</td>
			</tr>
			<tr>
				<th scope="row">폐공처리자</th>
				<td>${result.lnhoDppNm}</td>
				<th scope="row">종료신고일</th>
				<td>
					<fmt:parseDate value="${result.dvusEndNtYmd}" var="dvusEndNtYmd" pattern="yyyyMMdd" /><fmt:formatDate value="${dvusEndNtYmd}" pattern="yyyy-MM-dd" />
				</td>
			</tr>
			
			<!-- 기타 정보 -->
			<tr class="box">
				<th class="title" rowspan="2">기타 정보</th>
				
				<th scope="row">민원취하</th>
				<td>
					<c:forEach items="${listPermYn}" var="listCode2" >
						<c:if test="${listCode2.codeId eq  result.permYn}">
							${listCode2.codeNm}
						</c:if>
					</c:forEach>
				</td>
				<th scope="row">허가취소</th>
				<td>
					<c:forEach items="${listPermCancelYn}" var="listCode2" >
						<c:if test="${listCode2.codeId eq  result.permCancelYn}">
							${listCode2.codeNm}
						</c:if>
					</c:forEach>
				</td>
			</tr>
			<tr>
				<th scope="row">비고</th>
				<td colspan="3">${result.rem}</td>
			</tr>
			
		</tbody>
	</table>
</div>