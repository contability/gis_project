<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 		     uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui"			 uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="fmt"			 uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form"		 uri="http://www.springframework.org/tags/form" %>

<form id="modifyWrppCntrwkRegstrform" name="modifyWrppCntrwkRegstrform" method="post" enctype="multipart/form-data">
	<div id="wrppCntrwkRegstrModify" class="window_container">
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
					<li class="active"><a class="tab-control tab_wrppCntrwkRegstr_modify_1" href="#list_tab1">일반사항</a></li>
					<li><a class="tab-control tab_wrppCntrwkRegstr_modify_2" href="#list_tab2">공사관련</a></li>
					<li><a class="tab-control tab_wrppCntrwkRegstr_modify_3" href="#list_tab3">계약사항</a></li>
					<li><a class="tab-control tab_wrppCntrwkRegstr_modify_4" href="#list_tab4">도급자정보</a></li>
					<li><a class="tab-control tab_wrppCntrwkRegstr_modify_5" href="#list_tab5">공사사진</a></li>
				</ul>
			</div>
			<div class="tab-panel-1 active" id="list_tab1">
				<table class="table_text">			
					<tbody>
						<tr>
							<th>공사명</th>
							<td colspan="5">
								<input type="text" name="cntNam" class="input_text" value="${result.cntNam}" />
							</td>
						</tr>
						<tr>
							<th>공사구분</th>
							<td colspan="2">
								<select name="wrkCde" class="input_text">
									<c:forEach items="${wrkCdeList}" var="wrkCdeList" varStatus="status">
										<option value="${wrkCdeList.codeId}" ${wrkCdeList.codeId==result.wrkCde ? 'selected' : ''}>${wrkCdeList.codeNm}</option>
									</c:forEach>
								</select>
							</td>
							<th>설계자</th>
							<td colspan="2">
								<input type="text" name="dsnNam" class="input_text" value="${result.dsnNam}" />
							</td>											
						</tr>
						<tr>
							<th>공사위치</th>
							<td colspan="5">
								<input type="text" name="cntLoc" class="input_text" value="${result.cntLoc}" />
							</td>
						</tr>
						<tr>
							<th>설계총액</th>
							<td colspan="2">
								<input type="text" name="dsnAmt" class="input_text numOnly" value="${result.dsnAmt}" />
							</td>
							<th>관급금액</th>
							<td colspan="2">
								<input type="text" name="dgcAmt" class="input_text numOnly" value="${result.dgcAmt}" />
							</td>
						</tr>
						<tr>
							<th>순공사비</th>
							<td colspan="2">
								<input type="text" name="dpcAmt" class="input_text numOnly" value="${result.dpcAmt}" />
							</td>
							<th>기타잡비</th>
							<td colspan="2">
								<input type="text" name="detAmt" class="input_text numOnly" value="${result.detAmt}" />
							</td>
						</tr>
						<tr>
							<th>공사개요</th>
							<td colspan="5">
								<input type="text" name="cntDes" class="input_text" value="${result.cntDes}" />
							</td>
						</tr>
						<tr>
							<th>국비</th>
							<td>
								<input type="text" name="natAmt" class="input_text numOnly" value="${result.natAmt}" />
							</td>
							<th>도비</th>
							<td>
								<input type="text" name="couAmt" class="input_text numOnly" value="${result.couAmt}" />
							</td>
							<th>시군비</th>
							<td>
								<input type="text" name="citAmt" class="input_text numOnly" value="${result.citAmt}" />
							</td>
						</tr>
						<tr >
							<th>기채</th>
							<td colspan="2">
								<input type="text" name="bndAmt" class="input_text numOnly" value="${result.bndAmt}" />
							</td>
							<th>잉여금</th>
							<td colspan="2">
								<input type="text" name="cssAmt" class="input_text numOnly" value="${result.cssAmt}" />
							</td>
						</tr>
						<tr>
							<th>관</th>
							<td colspan="2">
								<input type="text" name="kwnExp" class="input_text numOnly" value="${result.kwnExp}" />
							</td>
							<th>항</th>
							<td colspan="2">
								<input type="text" name="hngExp" class="input_text numOnly" value="${result.hngExp}" />
							</td>
						</tr>
						<tr>
							<th>세</th>
							<td colspan="2">
								<input type="text" name="shnExp" class="input_text numOnly" value="${result.shnExp}" />
							</td>
							<th>목</th>
							<td colspan="2">
								<input type="text" name="mokExp" class="input_text numOnly" value="${result.mokExp}" />
							</td>
						</tr>
					</tbody>
				</table>
				<div class="head_text">- 하자 보수 내역</div>
				<table class="table_text tableSelector">
					<tbody class="tbody_2">
						<tr>
							<th>하자발생일</th>
							<th>하자보수일</th>
							<th>하자보수내용</th>
						</tr>
						<c:forEach var="wrppFlawMendngDtls" items="${wrppFlawMendngDtls}">
							<tr data-type="wttFlawDt" data-ftr-idn="${wrppFlawMendngDtls.ftrIdn}" data-sht-idn="${wrppFlawMendngDtls.shtIdn}">
								<td>
									<%-- <fmt:parseDate value="${wrppFlawMendngDtls.flaYmd}" var="wrppFlawMendngDtls_flaYmd" pattern="yyyyMMdd" />
									<fmt:formatDate value="${wrppFlawMendngDtls_flaYmd}" pattern="yyyy-MM-dd" /> --%>
									${wrppFlawMendngDtls.flaYmd}
								</td>
								<td>
									<%-- <fmt:parseDate value="${wrppFlawMendngDtls.rprYmd}" var="wrppFlawMendngDtls_rprYmd" pattern="yyyyMMdd" />
									<fmt:formatDate value="${wrppFlawMendngDtls_rprYmd}" pattern="yyyy-MM-dd" /> --%>
									${wrppFlawMendngDtls.rprYmd}
								</td>
								<td>${wrppFlawMendngDtls.rprDes}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				<div class="button_flat_mar">
					<button class="btn_save_wrppFlawMendngDtlsAdd button_flat_normal btn_blue">내역등록</button>
				</div>
			</div>
			<div class="tab-panel-1" id="list_tab2">
					<table class="table_text">
						<tbody>
							<tr>
								<th>감독자</th>
								<td>
									<input type="text" name="svsNam" class="input_text" value="${result.svsNam}" />
								</td>
								<th>착공일자</th>
								<td>
									<input type="text" name="begYmd" class="input_text easyui-datebox" value="${result.begYmd}" />
								</td>
							</tr>
							<tr>
								<th>준공검사자</th>
								<td>
									<input type="text" name="fchNam" class="input_text" value="${result.fchNam}" />
								</td>
								<th>준공예정일</th>
								<td>
									<input type="text" name="fnsYmd" class="input_text easyui-datebox" value="${result.fnsYmd}" />
								</td>
							</tr>
							<tr>
								<th>준공검사일</th>
								<td>
									<input type="text" name="fchYmd" class="input_text easyui-datebox" value="${result.fchYmd}" />
								</td>
								<th>실준공일</th>
								<td>
									<input type="text" name="rfnYmd" class="input_text easyui-datebox" value="${result.rfnYmd}" />
								</td>
							</tr>
							<tr>
								<th>관급물량</th>
								<td colspan="3">
									<input type="text" name="gvrDes" class="input_text" value="${result.gvrDes}" />
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
							<c:forEach var="wrppCntrwkCtPymntDtls" items="${wrppCntrwkCtPymntDtls}">
								<tr data-type="wttCostDt" data-ftr-idn="${wrppCntrwkCtPymntDtls.ftrIdn}" data-sht-idn="${wrppCntrwkCtPymntDtls.shtIdn}">
									<td>
										<c:forEach items="${payCdeList}" var="payCdeList">
											<c:if test="${payCdeList.codeId eq wrppCntrwkCtPymntDtls.payCde}">${payCdeList.codeNm}</c:if>
										</c:forEach>
									</td>
									<td>
										<%-- <fmt:parseDate value="${wrppCntrwkCtPymntDtls.payYmd}" var="wrppCntrwkCtPymntDtls_payYmd" pattern="yyyyMMdd" />
										<fmt:formatDate value="${wrppCntrwkCtPymntDtls_payYmd}" pattern="yyyy-MM-dd" /> --%>
										${wrppCntrwkCtPymntDtls.payYmd}
									</td>
									<td>
										<fmt:formatNumber value="${wrppCntrwkCtPymntDtls.payAmt}" pattern="#,###.##" />원
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
					<div class="button_flat_mar">
						<button class="btn_save_wrppCntrwkCtPymntDtlsAdd button_flat_normal btn_blue">내역등록</button>
					</div>
				</div>
				<div class="tab-panel-1" id="list_tab3">
				<table class="table_text">
					<tbody>
						<tr>
							<th>입찰일자</th>
							<td>
								<input type="text" name="bidYmd" class="input_text easyui-datebox" value="${result.bidYmd}" />
							</td>
							<th>계약일자</th>
							<td>
								<input type="text" name="cttYmd" class="input_text easyui-datebox" value="${result.cttYmd}" />
							</td>
						</tr>
						<tr>
							<th>계약방법</th>
							<td>
								<select name="cttCde" class="input_text">
									<c:forEach items="${cttCdeList}" var="cttCdeList" varStatus="status">
										<option value="${cttCdeList.codeId}" ${cttCdeList.codeId==result.cttCde ? 'selected' : ''}>${cttCdeList.codeNm}</option>
									</c:forEach>
								</select>
							</td>
							<th>예정금액</th>
							<td>
								<input type="text" name="estAmt" class="input_text numOnly" value="${result.estAmt}" />
							</td>
						</tr>
						<tr>
							<th>계약총액</th>
							<td>
								<input type="text" name="tctAmt" class="input_text numOnly" value="${result.tctAmt}" />
							</td>
							<th>순공사비</th>
							<td>
								<input type="text" name="cpcAmt" class="input_text numOnly" value="${result.cpcAmt}" />
							</td>
						</tr>
						<tr>
							<th>관급금액</th>
							<td>
								<input type="text" name="cgvAmt" class="input_text numOnly" value="${result.cgvAmt}" />
							</td>
							<th>기타잡비</th>
							<td>
								<input type="text" name="cetAmt" class="input_text numOnly" value="${result.cetAmt}" />
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
						<c:forEach var="wrppDsgnChangeDtls" items="${wrppDsgnChangeDtls}">
							<tr data-type="wttChngDt" data-ftr-idn="${wrppDsgnChangeDtls.ftrIdn}" data-sht-idn="${wrppDsgnChangeDtls.shtIdn}">
								<td>
									<%-- <fmt:parseDate value="${wrppDsgnChangeDtls.chgYmd}" var="wrppDsgnChangeDtls_chgYmd" pattern="yyyyMMdd" />
									<fmt:formatDate value="${wrppDsgnChangeDtls_chgYmd}" pattern="yyyy-MM-dd" /> --%>
									${wrppDsgnChangeDtls.chgYmd}
								</td>
								<td>
									<fmt:formatNumber value="${wrppDsgnChangeDtls.incAmt}" pattern="#,###.##" />원
								</td>
								<td>
									<fmt:formatNumber value="${wrppDsgnChangeDtls.igvAmt}" pattern="#,###.##" />원
								</td>
								<td>
									<fmt:formatNumber value="${wrppDsgnChangeDtls.chgAmt}" pattern="#,###.##" />원
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				<div class="button_flat_mar">
					<button class="btn_save_wrppDsgnChangeDtlsAdd button_flat_normal btn_blue">내역등록</button>
				</div>
			</div>
			<div class="tab-panel-1" id="list_tab1">
				<table class="table_text">
					<tbody>
						<tr>
							<th>상호</th>
							<td>
								<input type="text" name="gcnNam" class="input_text" value="${result.gcnNam}" />
							</td>
							<th>대표자</th>
							<td>
								<input type="text" name="pocNam" class="input_text" value="${result.pocNam}" />
							</td>
						</tr>
						<tr>
							<th>주소</th>
							<td colspan="5">
								<input type="text" name="gcnAdr" class="input_text" value="${result.gcnAdr}" />
							</td>
						</tr>
						<tr>
							<th>전화번호</th>
							<td colspan="5">
								<input type="text" name="gcnTel" class="input_text" value="${result.gcnTel}" />
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
						<c:forEach var="wrppSubcntrDtls" items="${wrppSubcntrDtls}">
							<tr data-type="wttSubcDt" data-ftr-idn="${wrppSubcntrDtls.ftrIdn}" data-sht-idn="${wrppSubcntrDtls.shtIdn}">
								<td>${wrppSubcntrDtls.subNam}</td>
								<td>${wrppSubcntrDtls.psbNam}</td>
								<td>${wrppSubcntrDtls.subAdr}</td>
								<td>${wrppSubcntrDtls.subTel}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				<div class="button_flat_mar">
					<button class="btn_save_wrppSubcntrDtlsAdd button_flat_normal btn_blue">내역등록</button>
				</div>
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
							<tr class="h_80" data-type="wrppCntrwkImage" data-ftr-cde="${resultPhoto.ftrCde}" data-ftr-idn="${resultPhoto.ftrIdn}" data-image-no="${resultPhoto.imageNo}">
								<td>
									<img src="<c:url value='/cmmn/image/${resultPhoto.imageNo}/thumbnail.do' />" width="120" height="70" />
								</td>
								<td>${resultPhoto.imageSj}</td>
								<td>${resultPhoto.imageCn}</td>
								<td>${resultPhoto.frstRgsde}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				<div class="button_flat_mar">
					<button class="btn_photo_wrppCntrwkRegstrAdd button_flat_small btn_grey">내역등록</button>
				</div>
			</div>
		</div>
		<div class="window_footer">
			<div class="button_flat">
				<button class="btn_save_wrppCntrwkRegstrModify button_flat_normal btn_blue">저장</button>
			</div>
		</div>
	</div>
</form>