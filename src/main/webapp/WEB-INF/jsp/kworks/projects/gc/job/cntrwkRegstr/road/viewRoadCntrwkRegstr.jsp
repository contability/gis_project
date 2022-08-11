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
						<td>${result.ftrIdn}</td>
						<th>e호조번호</th>
						<td>${result.cttNum}</td>
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
					<c:forEach var="roadFlawMendngDtls" items="${roadFlawMendngDtls}">
						<tr data-type="rdtFlawDt" data-ftr-idn="${roadFlawMendngDtls.ftrIdn}" data-sht-idn="${roadFlawMendngDtls.shtIdn}">
							<td>
								${roadFlawMendngDtls.flaYmd}
							</td>
							<td>
								${roadFlawMendngDtls.rprYmd}
							</td>
							<td>${roadFlawMendngDtls.rprDes}</td>
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
								${result.fnsYmd}
							</td>
						</tr>
						<tr>
							<th>준공검사일</th>
							<td>
								${result.fchYmd}
							</td>
							<th>실준공일</th>
							<td>
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
						<c:forEach var="roadCntrwkCtPymntDtls" items="${roadCntrwkCtPymntDtls}">
							<tr data-type="rdtCostDt" data-ftr-idn="${roadCntrwkCtPymntDtls.ftrIdn}" data-sht-idn="${roadCntrwkCtPymntDtls.shtIdn}">
								<td>
									<c:forEach items="${payCdeList}" var="payCdeList">
										<c:if test="${payCdeList.codeId eq roadCntrwkCtPymntDtls.payCde}">${payCdeList.codeNm}</c:if>
									</c:forEach>
								</td>
								<td>
									${roadCntrwkCtPymntDtls.payYmd}
								</td>
								<td>
									<fmt:formatNumber value="${roadCntrwkCtPymntDtls.payAmt}" pattern="#,###.##" />원
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
							${result.bidYmd}
						</td>
						<th>계약일자</th>
						<td>
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
			<div class="head_text">- 설계변경내역</div>
			<table class="table_text tableSelector">
				<tbody class="tbody_2">
					<tr>
						<th>변경일자</th>
						<th>증감금액</th>
						<th>증감관급금액</th>
						<th>변경공사총액</th>
					</tr>
					 <c:forEach var="roadDsgnChangeDtls" items="${roadDsgnChangeDtls}">
						<tr data-type="rdtChngDt" data-ftr-idn="${roadDsgnChangeDtls.ftrIdn}" data-sht-idn="${roadDsgnChangeDtls.shtIdn}">
							<td>
								${roadDsgnChangeDtls.chgYmd}
							</td>
							<td>
								<fmt:formatNumber value="${roadDsgnChangeDtls.incAmt}" pattern="#,###.##" />원
							</td>
							<td>
								<fmt:formatNumber value="${roadDsgnChangeDtls.igvAmt}" pattern="#,###.##" />원
							</td>
							<td>
								<fmt:formatNumber value="${roadDsgnChangeDtls.chgAmt}" pattern="#,###.##" />원
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
					<c:forEach var="roadSubcntrDtls" items="${roadSubcntrDtls}">
						<tr data-type="rdtSubcDt" data-ftr-idn="${roadSubcntrDtls.ftrIdn}" data-sht-idn="${roadSubcntrDtls.shtIdn}">
							<td>${roadSubcntrDtls.subNam}</td>
							<td>${roadSubcntrDtls.psbNam}</td>
							<td>${roadSubcntrDtls.subAdr}</td>
							<td>${roadSubcntrDtls.subTel}</td>
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
			<button class="btn_modify_roadCntrwkRegstrDetail button_flat_normal btn_blue">편집</button>
		</div>
	</div>
</div>