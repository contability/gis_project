<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<form id="viewCityPlanRoadform" name="viewCityPlanRoadform" method="post" enctype="multipart/form-data">
	<div id="viewCityPlanRoad" class="window_container">
		<div class="window_article">
			<input type="hidden" name="editAt" value="${editAt}" />
			<table class="table_text">
				<tbody>
					<tr>
						<th>관리번호</th>
						<td colspan="5" id="uprIdn">${row.uprIdn}</td>
					</tr>
				</tbody>
			</table>
			<div class="tabs-1">
				<ul class="tab-list-1">
					<li class="active"><a class="tab-control" href="#list_tab1">일반사항</a></li>
					<li><a class="tab-control" href="#list_tab2">변천내역</a></li>
				</ul>
			</div>
			<div id="list_tab1" class="tab-panel-1 active" style="height:310px">
				<table id="cityPlanRoad_register" class="table_text">
					<tbody>
						<tr>
							<th>도시</th>
							<td colspan="2" id="uprBjd">${row.uprBjd}</td>
							<th>구역명</th>
							<td colspan="2" id="uprNam">${row.uprNam}</td>
						</tr>
						<tr>
							<th>등급</th>
							<td id="uprGrd">${row.uprGrd}</td>
							<th>류별</th>
							<td id="uprTyp">${row.uprTyp}</td>
							<th>번호</th>
							<td id="uprNum">${row.uprNum}</td>
						</tr>
						<tr>
							<th>폭원(m)</th>
							<td colspan="2" id="uprWid">${row.uprWid}</td>
							<th>연장(m)</th>
							<td colspan="2" id="uprLen">${row.uprLen}</td>
						</tr>
						<tr>
							<th>기점</th>
							<td colspan="2" id="uprStr">${row.uprStr}</td>
							<th>종점</th>
							<td colspan="2" id="uprEnd">${row.uprEnd}</td>
						</tr>
						<tr>
							<th>기능</th>
							<td colspan="2" id="uprFcn">${row.uprFcn}</td>
							<th>사용형태</th>
							<td colspan="2" id="uprUty">${row.uprUty}</td>
						</tr>
						<tr>
							<th>최초고시</th>
							<td colspan="2" id="strNtc">${row.strNtc}</td>
							<th>최초고시일</th>
							<td colspan="2" id="strYmd">${row.strYmd}</td>
						</tr>
						<tr>
							<th>인가고시</th>
							<td colspan="2" id="pmtNtc">${row.pmtNtc}</td>
							<th>인가고시일</th>
							<td colspan="2" id="pmtYmd">${row.pmtYmd}</td>
						</tr>
						<tr>
							<th>최종변경고시</th>
							<td colspan="2" id="lstNtc">${row.lstNtc}</td>
							<th>최종변경고시일</th>
							<td colspan="2" id="lstYmd">${row.lstYmd}</td>
						</tr>
						<tr>
							<th>폐지고시</th>
							<td colspan="2" id="disNtc">${row.disNtc}</td>
							<th>폐지고시일</th>
							<td colspan="2" id="disYmd">${row.disYmd}</td>
						</tr>
						<tr>
							<th>비고</th>
							<td colspan="5" id="rmkExp">${row.rmkExp}</td>
						</tr>
					</tbody>
				</table>
			</div>
			<div id="list_tab2" class="tab-panel-1"  style="height:310px">
				<table id="tableHistory" class="rowSelected">
					<tbody class="tbodyHistory">
					</tbody>
				</table>
				<div class="window_footer">
					<div class="button_flat">
						<c:if test="${editAt eq 'Y' or editAt eq 'y'}">
							<button id="btn_add" class="button_flat_normal btn_blue">등록</button>
						</c:if>
						<c:if test="${editAt eq 'N' or editAt eq 'n'}">
							<button id="btn_add" class="button_flat_normal btn_grey2" title="사용되지 않는 기능입니다.">등록</button>
						</c:if>
					</div>
				</div>
			</div>
		</div>
		<div class="window_footer">
			<div class="button_flat">
				<button id="btn_print" class="button_flat_normal btn_blue">대장출력</button>
				<c:if test="${editAt eq 'Y' or editAt eq 'y'}">
					<button id="btn_edit" class="button_flat_normal btn_blue">편집</button>
					<button id="btn_delete" class="button_flat_normal btn_blue">삭제</button>
				</c:if>
				<c:if test="${editAt eq 'N' or editAt eq 'n'}">
					<button id="btn_edit" class="button_flat_normal btn_grey2" title="사용되지 않는 기능입니다.">편집</button>
					<button id="btn_delete" class="button_flat_normal btn_grey2" title="사용되지 않는 기능입니다.">삭제</button>
				</c:if>
				<button id="btn_close" class="button_flat_normal btn_black">닫기</button>
			</div>
		</div>
	</div>
</form>