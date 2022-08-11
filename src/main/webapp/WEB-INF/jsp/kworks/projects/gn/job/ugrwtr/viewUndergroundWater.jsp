<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 			uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" 			uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="fmt" 		uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form"		uri="http://www.springframework.org/tags/form" %>

<style type="text/css">
	.border1 { 
		border : 1px solid #c6c6c6; 
	}
	ul.titleBox1 { 
		height : 18px; 
		background-color : #f2f2f2; 
		font-weight : bold; 
		padding-left : 10px; 
		line-height : 15pt; 
		color : #052fc8; 
	}
</style>

<div class="layerCont">
	<input type="hidden" class="ftrCde" value="${result.ftrCde}" />
	<input type="hidden" class="ftrIdn" value="${result.ftrIdn}" />
	<input type="hidden" class="objectid" value="${result.objectid}" />
	<input type="hidden" class="permNtNo" value="${result.permNtNo}" />
	
	<div class="border1 ma_b_15">
		<ul class="titleBox1 cursor_point UWtogClick" togId="UWtog1">
			<li style="float:left; width:570px;">기본 정보</li>
			<li id="UWtog1" style="float:left; width:17px; height:17px;" class="accordion-collapse acco"></li>
		</ul>
		<table id="etUV_bassInfo" perm_No="${result.permNtNo}" class="cmmTable v2 thNorma UWtog UWtog1" summary="지하수관정 기본정보 관련 테이블" style="table-layout: fixed; word-wrap: break-word; ">
			<caption>지하수관정 기본정보 테이블</caption>
			<colgroup>
				<col width="20%" />
				<col width="30%" />
				<col width="20%" />
				<col width="30%" />
			</colgroup>
			<tbody>
				<tr>
					<th scope="row">관리번호</th>
					<td colspan="3">${result.mgNo}</td>
				</tr>
				<tr>
					<th scope="row">사용구분</th>
					<td>
						<c:forEach items="${kwsDisCde}" var="kwsDisCde">
							<c:if test="${kwsDisCde.codeId eq result.disCde}">${kwsDisCde.codeNm}</c:if>
						</c:forEach>
					</td>
					<th scope="row">시설구분</th>
					<td>
						<c:forEach items="${kwsPermNtFo}" var="kwsPermNtFo">
							<c:if test="${kwsPermNtFo.codeId eq result.permNtFo}">${kwsPermNtFo.codeNm}</c:if>
						</c:forEach>
					</td>
				</tr>
				<tr>
					<th scope="row">허가신고번호</th>
					<td>${result.permNtNo}</td>
					<th scope="row">허가신고일</th>
					<td>${result.permNtYm}</td>
				</tr>
			</tbody>
		</table>
	</div>
	<div class="border1 ma_b_15">
		<ul class="titleBox1 cursor_point UWtogClick" togId="UWtog2">
			<li style="float:left; width:570px;">이용자 정보</li>
			<li id="UWtog2" style="float:left; width:17px; height:17px;" class="accordion-expand acco"></li>
		</ul>
		<table id="etUV_userInfo" ftrCde="${result.permNtNo}" class="cmmTable v2 thNorma UWtog UWtog2" summary="지하수관정 이용자 정보 관련 테이블" style="display:none; table-layout: fixed; word-wrap: break-word;">
			<caption>지하수관정 이용자 정보 테이블</caption>
			<colgroup>
				<col width="20%" />
				<col width="30%" />
				<col width="20%" />
				<col width="30%" />
			</colgroup>
			<tbody>
				<tr>
					<th scope="row">이용자구분</th>
					<td>
						<c:forEach items="${kwsRgtMbdGb}" var="kwsRgtMbdGb">
							<c:if test="${kwsRgtMbdGb.codeId eq result.rgtMbdGb}">${kwsRgtMbdGb.codeNm}</c:if>
						</c:forEach>
					</td>
					<th scope="row">상호(성명)</th>
					<td>${result.rgtMbdNm}</td>
				</tr>
				<tr>
					<th scope="row">대표자</th>
					<td>${result.userCeo}</td>
					<th scope="row">생년월일/<br>사업자번호</th>
					<td>${result.rgtMbdRe}</td>
				</tr>
				<tr>
					<th scope="row">주소</th>
					<td colspan="3">${result.rdnWhlAd}</td>
				</tr>
			</tbody>
		</table>
	</div>
	<div class="border1 ma_b_15">
		<ul class="titleBox1 cursor_point UWtogClick" togId="UWtog3">
			<li style="float:left; width:570px;">개발위치 정보</li>
			<li id="UWtog3" style="float:left; width:17px; height:17px;" class="accordion-expand acco"></li>
		</ul>
		<table id="etUV_devSiteInfo" ftrCde="${result.permNtNo}" class="cmmTable v2 thNorma UWtog UWtog3" summary="지하수관정 개발위치 정보 관련 테이블" style="display:none; table-layout: fixed; word-wrap: break-word;">
			<caption>지하수관정 개발위치 정보 테이블</caption>
			<colgroup>
				<col width="20%" />
				<col width="30%" />
				<col width="20%" />
				<col width="30%" />
			</colgroup>
			<tbody>
				<tr>
					<th scope="row">주소</th>
					<td colspan="3">${result.dvopLocR}</td>
				</tr>
				<tr>
					<th scope="row">경도</th>
					<td>${result.litdDg}° ${result.litdMint}' ${result.litdSc}"</td>
					<th scope="row">위도</th>
					<td>${result.lttdDg}° ${result.lttdMint}' ${result.lttdSc}"</td>
				</tr>
			</tbody>
		</table>
	</div>
	<div class="border1 ma_b_15">
		<ul class="titleBox1 cursor_point UWtogClick" togId="UWtog4">
			<li style="float:left; width:570px;">용도 정보</li>
			<li id="UWtog4" style="float:left; width:17px; height:17px;" class="accordion-expand acco"></li>
		</ul>
		<table id="etUV_useInfo" ftrCde="${result.permNtNo}" class="cmmTable v2 thNorma UWtog UWtog4" summary="지하수관정 용도 정보 관련 테이블" style="display:none; table-layout: fixed; word-wrap: break-word;">
			<caption>지하수관정 용도 정보 테이블</caption>
			<colgroup>
				<col width="20%" />
				<col width="30%" />
				<col width="20%" />
				<col width="30%" />
			</colgroup>
			<tbody>
				<tr>
					<th scope="row">용도구분</th>
					<td>${result.uwaterSrv}</td>
					<th scope="row">용도명칭</th>
					<td>${result.uwaterDtl}</td>
				</tr>
				<tr>
					<th scope="row">허가유효 시작일</th>
					<td>${result.permEfSt}</td>
					<th scope="row">허가유효 종료일</th>
					<td>${result.permEfEn}</td>
				</tr>
				<tr>
					<th scope="row">음용여부</th>
					<td colspan="3">${result.uwaterPot}</td>
				</tr>
			</tbody>
		</table>
	</div>
	<div class="border1 ma_b_15">
		<ul class="titleBox1 cursor_point UWtogClick" togId="UWtog5">
			<li style="float:left; width:570px;">준공 정보</li>
			<li id="UWtog5" style="float:left; width:17px; height:17px;" class="accordion-expand acco"></li>
		</ul>
		<table id="etUV_comInfo" ftrCde="${result.permNtNo}" class="cmmTable v2 thNorma UWtog UWtog5" summary="지하수관정 준공 정보 관련 테이블" style="display:none; table-layout: fixed; word-wrap: break-word;">
			<caption>지하수관정 준공 정보 테이블</caption>
			<colgroup>
				<col width="20%" />
				<col width="30%" />
				<col width="20%" />
				<col width="30%" />
			</colgroup>
			<tbody>
				<tr>
					<th scope="row">준공일</th>
					<td>${result.jgongYmd}</td>
					<th scope="row">몽리면적</th>
					<td>${result.havQua}</td>
				</tr>
				<tr>
					<th scope="row">굴착깊이</th>
					<td>${result.digDph}</td>
					<th scope="row">굴착지름</th>
					<td>${result.digDiam}</td>
				</tr>
				<tr>
					<th scope="row">취수계획량</th>
					<td>${result.frwPlnQu}</td>
					<th scope="row">소요수량</th>
					<td>${result.ndQt}</td>
				</tr>
				<tr>
					<th scope="row">동력장치마력(hp)</th>
					<td>${result.dynEqnHr}</td>
					<th scope="row">토출관직경</th>
					<td>${result.pipeDiam}</td>
				</tr>
				<tr>
					<th scope="row">설치심도</th>
					<td>${result.esbDph}</td>
					<th scope="row">양수능력(m<sup>3</sup>/일)</th>
					<td>${result.rwtCp}</td>
				</tr>
				<tr>
					<th scope="row">구조물상태</th>
					<td colspan="3">${result.wellSts}</td>
				</tr>
			</tbody>
		</table>
	</div>
	<div class="border1 ma_b_15">
		<ul class="titleBox1 cursor_point UWtogClick" togId="UWtog6">
			<li style="float:left; width:570px;">폐공 정보</li>
			<li id="UWtog6" style="float:left; width:17px; height:17px;" class="accordion-expand acco"></li>
		</ul>
		<table id="etUV_adWeInfo" ftrCde="${result.permNtNo}" class="cmmTable v2 thNorma UWtog UWtog6" summary="지하수관정 폐공 정보 관련 테이블" style="display:none; table-layout: fixed; word-wrap: break-word;">
			<caption>지하수관정 폐공 정보 테이블</caption>
			<colgroup>
				<col width="20%" />
				<col width="30%" />
				<col width="20%" />
				<col width="30%" />
			</colgroup>
			<tbody>
				<tr>
					<th scope="row">폐공사유</th>
					<td colspan="3">${result.disTxt}</td>
				</tr>
				<tr>
					<th scope="row">폐공후상태</th>
					<td colspan="3">${result.disSts}</td>
				</tr>
			</tbody>
		</table>
	</div>
	<div class="border1 ma_b_15">
		<ul class="titleBox1 cursor_point UWtogClick" togId="UWtog7">
			<li style="float:left; width:570px;">사진 정보</li>
			<li id="UWtog7" style="float:left; width:17px; height:17px;" class="accordion-expand acco"></li>
		</ul>
		<table id="etUV_imageWeInfo" class="cmmTable v2 thNorma UWtog UWtog7" summary="지하수관정 사진 정보 관련 테이블" style="display:none; table-layout: fixed; word-wrap: break-word;">
			<caption>지하수관정 사진 정보 테이블</caption>
			<colgroup>
				<col width="25%" />
				<col width="25%" />
				<col width="25%" />
				<col width="25%" />
			</colgroup>
			<tbody>
				<tr>
					<th colspan="2" scope="row">원경</th>
					<th colspan="2" scope="row">근경</th>
				</tr>
				<tr>
					<td colspan="2" class="h_200">
						<c:if test="${result.bmlDistantView ne null}">
							<img class="bmlWellImage" data-image-no="${result.bmlDistantView.imageNo}" src="<c:url value='/cmmn/image/${result.bmlDistantView.imageNo}/thumbnail.do'/>" alt="원경 사진" />
						</c:if>
					</td>
					<td  colspan="2" class="h_200">
						<c:if test="${result.bmlCloseRangeView ne null}">
							<img class="bmlWellImage" data-image-no="${result.bmlCloseRangeView.imageNo}" src="<c:url value='/cmmn/image/${result.bmlCloseRangeView.imageNo}/thumbnail.do'/>" alt="근경 사진" />
						</c:if>
					</td>
				</tr>
				<tr>
					<th colspan="2" scope="row">GPS관측도</th>
					<th colspan="2" scope="row">폐공</th>
				</tr>
				<tr>
					<td colspan="2" class="h_200">
						<c:if test="${result.bmlGpsObservationMap ne null}">
							<img class="bmlWellImage" data-image-no="${result.bmlGpsObservationMap.imageNo}" src="<c:url value='/cmmn/image/${result.bmlGpsObservationMap.imageNo}/thumbnail.do'/>" alt="GPS 관측도 사진" />
						</c:if>					
					</td>
					<td  colspan="2" class="h_200">
						<c:if test="${result.bmlAbandonedWell ne null}">
							<img class="bmlWellImage" data-image-no="${result.bmlAbandonedWell.imageNo}" src="<c:url value='/cmmn/image/${result.bmlAbandonedWell.imageNo}/thumbnail.do'/>" alt="폐공 사진" />
						</c:if>					
					</td>
				</tr>
				<tr>
					<th colspan="2" scope="row">기타</th>
				</tr>
				<tr>
					<td colspan="2" class="h_200">
						<c:if test="${result.bmlTempView ne null}">
							<img class="bmlWellImage" data-image-no="${result.bmlTempView.imageNo}" src="<c:url value='/cmmn/image/${result.bmlTempView.imageNo}/thumbnail.do'/>" alt="기타 사진" />
						</c:if>					
					</td>
				</tr>
			</tbody>
		</table>
	</div>
	<p class="btnRight v2">
		<a href="#" class="a_open_chckImprmnHist_viewUgrwtr btnBlue">점검정비이력</a>
		<a href="#" class="a_outpt_viewUgrwtr btnBlue">출력</a>
		<a href="#" class="a_modify_viewUgrwtr btnBlue">편집</a>
		<a href="#" class="a_close_viewUgrwtr btnBlue">닫기</a>
	</p>
</div>
