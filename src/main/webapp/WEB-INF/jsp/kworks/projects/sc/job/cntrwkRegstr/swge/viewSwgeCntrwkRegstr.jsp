<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 		     uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui"			 uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="fmt"			 uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form"		 uri="http://www.springframework.org/tags/form" %>

<div class="window_container">
	<div class="window_article">
		<input type="hidden" id="ftrIdn" value="${result.ftrIdn}" />
		<table class="table_text">
			<tbody>
					<tr>
						<th>공사번호</th>
						<td colspan="2">${result.ftrIdn}</td>
					</tr>
			</tbody>
		</table>
		<div class="tabs-1">
			<ul class="tab-list-1">
				<li class="active"><a class="tab-control" href="#list_tab1">일반사항</a></li>
				<li><a class="tab-control" href="#list_tab2">공사관련</a></li>
				<li><a class="tab-control" href="#list_tab3">계약사항</a></li>
				<li><a class="tab-control" href="#list_tab4">도급자정보</a></li>
				<li><a class="tab-control" href="#list_tab5">공사사진</a></li>
			</ul>
		</div>
		<div class="tab-panel-1 active" id="list_tab1">
			<table class="table_text">			
				<tbody>
					<tr>
						<th>공사명</th>
						<td colspan="5">
							${result.cntNam}
						</td>
					</tr>
					<tr>
						<th>공사구분</th>
						<td colspan="2">
							<c:forEach items="${wrkCdeList}" var="wrkCdeList">
								<c:if test="${wrkCdeList.codeId eq result.wrkCde}">${wrkCdeList.codeNm}</c:if>
							</c:forEach>
						</td>
						<th>설계자</th>
						<td colspan="2">
							${result.dsnNam}
						</td>											
					</tr>
					<tr>
						<th>공사위치</th>
						<td colspan="5">
							${result.cntLoc}
						</td>
					</tr>
					<tr>
						<th>설계총액</th>
						<td colspan="2">
							<fmt:formatNumber value="${result.dsnAmt}" pattern="#,###.##" />원
						</td>
						<th>관급금액</th>
						<td colspan="2">
							<fmt:formatNumber value="${result.dgcAmt}" pattern="#,###.##" />원
						</td>
					</tr>
					<tr>
						<th>순공사비</th>
						<td colspan="2">
							<fmt:formatNumber value="${result.dpcAmt}" pattern="#,###.##" />원
						</td>
						<th>기타잡비</th>
						<td colspan="2">
							<fmt:formatNumber value="${result.detAmt}" pattern="#,###.##" />원
						</td>
					</tr>
					<tr>
						<th>공사개요</th>
						<td colspan="5">
							${result.cntDes}
						</td>
					</tr>
					<tr>
						<th>국비</th>
						<td>
							<fmt:formatNumber value="${result.natAmt}" pattern="#,###.##" />원
						</td>
						<th>도비</th>
						<td>
							<fmt:formatNumber value="${result.couAmt}" pattern="#,###.##" />원
						</td>
						<th>시군비</th>
						<td>
							<fmt:formatNumber value="${result.citAmt}" pattern="#,###.##" />원
						</td>
					</tr>
					<tr >
						<th>기채</th>
						<td colspan="2">
							<fmt:formatNumber value="${result.bndAmt}" pattern="#,###.##" />원
						</td>
						<th>잉여금</th>
						<td colspan="2">
							<fmt:formatNumber value="${result.cssAmt}" pattern="#,###.##" />원
						</td>
					</tr>
					<tr>
						<th>관</th>
						<td colspan="2">
							${result.kwnExp}
						</td>
						<th>항</th>
						<td colspan="2">
							${result.hngExp}
						</td>
					</tr>
					<tr>
						<th>세</th>
						<td colspan="2">
							${result.shnExp}
						</td>
						<th>목</th>
						<td colspan="2">
							${result.mokExp}
						</td>
					</tr>
				</tbody>
			</table>
			<div class="head_text">- 하자보수내용</div>
			<table class="table_text tableSelector">
				<tbody class="tbody_2">
					<tr>
						<th>하자발생일</th>
						<th>하자보수일</th>
						<th>하자보수내용</th>
					</tr>
					<c:forEach var="swgeFlawMendngDtls" items="${swgeFlawMendngDtls}">
						<tr data-type="swtFlawDt" data-ftr-idn="${swgeFlawMendngDtls.ftrIdn}" data-sht-idn="${swgeFlawMendngDtls.shtIdn}">
							<td>
								<%-- <fmt:parseDate value="${swgeFlawMendngDtls.flaYmd}" var="swgeFlawMendngDtls_flaYmd" pattern="yyyyMMdd" />
								<fmt:formatDate value="${swgeFlawMendngDtls_flaYmd}" pattern="yyyy-MM-dd" /> --%>
								${swgeFlawMendngDtls.flaYmd}
								
							</td>
							<td>
								<%-- <fmt:parseDate value="${swgeFlawMendngDtls.rprYmd}" var="swgeFlawMendngDtls_rprYmd" pattern="yyyyMMdd" />
								<fmt:formatDate value="${swgeFlawMendngDtls_rprYmd}" pattern="yyyy-MM-dd" /> --%>
								${swgeFlawMendngDtls.rprYmd}
							</td>
							<td>${swgeFlawMendngDtls.rprDes}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<div class="tab-panel-1" id="list_tab2">
				<table class="table_text">
					<tbody>
						<tr>
							<th>감독자</th>
							<td>
								${result.svsNam}
							</td>
							<th>착공일자</th>
							<td>
								<%-- <fmt:parseDate value="${result.begYmd}" var="begYmd" pattern="yyyyMMdd" />
								<fmt:formatDate value="${begYmd}" pattern="yyyy-MM-dd" /> --%>
								${result.begYmd}
							</td>
						</tr>
						<tr>
							<th>준공검사자</th>
							<td>
								${result.fchNam}
							</td>
							<th>준공예정일</th>
							<td>
								<%-- <fmt:parseDate value="${result.fnsYmd}" var="fnsYmd" pattern="yyyyMMdd" />
								<fmt:formatDate value="${fnsYmd}" pattern="yyyy-MM-dd" /> --%>
								${result.fnsYmd}
							</td>
						</tr>
						<tr>
							<th>준공검사일</th>
							<td>
								<%-- <fmt:parseDate value="${result.fchYmd}" var="fchYmd" pattern="yyyyMMdd" />
								<fmt:formatDate value="${fchYmd}" pattern="yyyy-MM-dd" /> --%>
								${result.fchYmd}
							</td>
							<th>실준공일</th>
							<td>
								<%-- <fmt:parseDate value="${result.rfnYmd}" var="rfnYmd" pattern="yyyyMMdd" />
								<fmt:formatDate value="${rfnYmd}" pattern="yyyy-MM-dd" /> --%>
								${result.rfnYmd}
							</td>
						</tr>
						<tr>
							<th>관급물량</th>
							<td colspan="3">
								${result.gvrDes}
							</td>											
						</tr>
					</tbody>
				</table>
				<div class="head_text">- 공사비 지급 내역</div>
				<table class="table_text tableSelector">
					<tbody class="tbody_2">
						<tr>
							<th>지급구분</th>
							<th>지급일자</th>
							<th>지급금액</th>
						</tr>
						<c:forEach var="swgeCntrwkCtPymntDtls" items="${swgeCntrwkCtPymntDtls}">
							<tr data-type="swtCostDt" data-ftr-idn="${swgeCntrwkCtPymntDtls.ftrIdn}" data-sht-idn="${swgeCntrwkCtPymntDtls.shtIdn}">
								<td>
									<c:forEach items="${payCdeList}" var="payCdeList">
										<c:if test="${payCdeList.codeId eq swgeCntrwkCtPymntDtls.payCde}">${payCdeList.codeNm}</c:if>
									</c:forEach>
								</td>
								<td>
									<%-- <fmt:parseDate value="${swgeCntrwkCtPymntDtls.payYmd}" var="swgeCntrwkCtPymntDtls_payYmd" pattern="yyyyMMdd" />
									<fmt:formatDate value="${swgeCntrwkCtPymntDtls_payYmd}" pattern="yyyy-MM-dd" /> --%>
									${swgeCntrwkCtPymntDtls.payYmd}
								</td>
								<td>
									<fmt:formatNumber value="${swgeCntrwkCtPymntDtls.payAmt}" pattern="#,###.##" />원
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
			<div class="tab-panel-1" id="list_tab3">
			<table class="table_text">
				<tbody>
					<tr>
						<th>입찰일자</th>
						<td>
							<%-- <fmt:parseDate value="${result.bidYmd}" var="bidYmd" pattern="yyyyMMdd" />
							<fmt:formatDate value="${bidYmd}" pattern="yyyy-MM-dd" /> --%>
							${result.bidYmd}
						</td>
						<th>계약일자</th>
						<td>
							<%-- <fmt:parseDate value="${result.cttYmd}" var="cttYmd" pattern="yyyyMMdd" />
							<fmt:formatDate value="${cttYmd}" pattern="yyyy-MM-dd" /> --%>
							${result.cttYmd}
						</td>
					</tr>
					<tr>
						<th>계약방법</th>
						<td>
							<c:forEach items="${cttCdeList}" var="cttCdeList">
								<c:if test="${cttCdeList.codeId eq result.cttCde}">${cttCdeList.codeNm}</c:if>
							</c:forEach>
						</td>
						<th>예정금액</th>
						<td>
							<fmt:formatNumber value="${result.estAmt}" pattern="#,###.##" />원
						</td>
					</tr>
					<tr>
						<th>계약총액</th>
						<td>
							<fmt:formatNumber value="${result.tctAmt}" pattern="#,###.##" />원
						</td>
						<th>순공사비</th>
						<td>
							<fmt:formatNumber value="${result.cpcAmt}" pattern="#,###.##" />원
						</td>
					</tr>
					<tr>
						<th>관급금액</th>
						<td>
							<fmt:formatNumber value="${result.cgvAmt}" pattern="#,###.##" />원
						</td>
						<th>기타잡비</th>
						<td>
							<fmt:formatNumber value="${result.cetAmt}" pattern="#,###.##" />원
						</td>
					</tr>					
				</tbody>
			</table>
			<div class="head_text">- 설계 변경 내역</div>
			<table class="table_text tableSelector">
				<tbody class="tbody_2">
					<tr>
						<th>변경일자</th>
						<th>증감금액</th>
						<th>증감관급금액</th>
						<th>변경공사총액</th>
					</tr>
					<c:forEach var="swgeDsgnChangeDtls" items="${swgeDsgnChangeDtls}">
						<tr data-type="swtChngDt" data-ftr-idn="${swgeDsgnChangeDtls.ftrIdn}" data-sht-idn="${swgeDsgnChangeDtls.shtIdn}">
							<td>
								<%-- <fmt:parseDate value="${swgeDsgnChangeDtls.chgYmd}" var="swgeDsgnChangeDtls_chgYmd" pattern="yyyyMMdd" />
								<fmt:formatDate value="${swgeDsgnChangeDtls_chgYmd}" pattern="yyyy-MM-dd" /> --%>
								${swgeDsgnChangeDtls.chgYmd}
							</td>
							<td>
								<fmt:formatNumber value="${swgeDsgnChangeDtls.incAmt}" pattern="#,###.##" />원
							</td>
							<td>
								<fmt:formatNumber value="${swgeDsgnChangeDtls.igvAmt}" pattern="#,###.##" />원
							</td>
							<td>
								<fmt:formatNumber value="${swgeDsgnChangeDtls.chgAmt}" pattern="#,###.##" />원
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<div class="tab-panel-1" id="list_tab1">
			<table class="table_text">
				<tbody>
					<tr>
						<th>상호</th>
						<td>
							${result.gcnNam}
						</td>
						<th>대표자</th>
						<td>
							${result.pocNam}
						</td>
					</tr>
					<tr>
						<th>주소</th>
						<td colspan="5">
							${result.gcnAdr}
						</td>
					</tr>
					<tr>
						<th>전화번호</th>
						<td colspan="5">
							${result.gcnTel}
						</td>
					</tr>
				</tbody>
			</table>
			<div class="head_text">- 하도급 내역</div>
			<table class="table_text tableSelector">
				<tbody class="tbody_2">
					<tr>
						<th>상호명</th>
						<th>대표자</th>
						<th>주소</th>
						<th>전화번호</th>
					</tr>
					<c:forEach var="swgeSubcntrDtls" items="${swgeSubcntrDtls}">
						<tr data-type="swtSubcDt" data-ftr-idn="${swgeSubcntrDtls.ftrIdn}" data-sht-idn="${swgeSubcntrDtls.shtIdn}">
							<td>${swgeSubcntrDtls.subNam}</td>
							<td>${swgeSubcntrDtls.psbNam}</td>
							<td>${swgeSubcntrDtls.subAdr}</td>
							<td>${swgeSubcntrDtls.subTel}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<div class="tab-panel-1" id="list_tab5">
			<table class="table_text tableSelector">
				<tbody class="tbody_2">
					<tr>
						<th>사진</th>
						<th>제목</th>
						<th>설명</th>
						<th>등록일자</th>
					</tr>
					<c:forEach var="resultPhoto" items="${resultPhoto}">
						<tr class="h_80" data-type="roadCntrwkImage" data-ftr-cde="${resultPhoto.ftrCde}" data-ftr-idn="${resultPhoto.ftrIdn}" data-image-no="${resultPhoto.imageNo}">
							<td>
								<img src="<c:url value='/cmmn/image/${resultPhoto.imageNo}/thumbnail.do"' />" width="120" height="70" />
							</td>
							<td>${resultPhoto.imageSj}</td>
							<td>${resultPhoto.imageCn}</td>
							<td>${resultPhoto.frstRgsde}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
	<div class="window_footer">
		<div class="button_flat">
			<button class="btn_modify_swgeCntrwkRegstrDetail button_flat_normal btn_blue">편집</button>
		</div>
	</div>
</div>