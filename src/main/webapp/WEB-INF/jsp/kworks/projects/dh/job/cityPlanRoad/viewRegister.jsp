<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<form id="viewCityPlanRoadform" name="viewCityPlanRoadform" method="post" enctype="multipart/form-data">
	<div id="viewCityPlanRoad" class="window_container">
		<div class="window_article">
			<input type="hidden" name="editAt" value="${editAt}" />
			<input type="hidden" name="uprStr" value="${row.uprStr}" />
			<input type="hidden" name="uprEnd" value="${row.uprEnd}" />
			<table class="table_text">
				<tbody>
					<tr>
						<th>관리번호</th>
						<td colspan="5" id="ftrIdn">${row.ftrIdn}</td>
					</tr>
				</tbody>
			</table>
			<div class="tabs-1">
				<ul class="tab-list-1">
					<li class="active"><a class="tab-control" href="#list_tab1">일반사항</a></li>
					<li><a class="tab-control" href="#list_tab2">공사개요</a></li>
					<li><a class="tab-control" href="#list_tab3">정비이력</a></li>
					<li><a class="tab-control" href="#list_tab4">사진자료</a></li>
				</ul>
			</div>
			<div id="list_tab1" class="tab-panel-1 active" style="height:481px">
				<table id="cityPlanRoad_register" class="table_text">
					<tbody>
						<tr>
							<th>국도명</th>
							<td colspan="2" id="uprNam">${row.uprNam}</td>
							<th>규모별 구분</th>
							<td colspan="2" id="uprGrd">${row.uprGrd}</td>
						</tr>
						<tr>
							<th>분류</th>
							<td colspan="2" id="uprTyp">${row.uprTyp}</td>
							<th>노선번호</th>
							<td colspan="2" id="uprNum">${row.uprNum}</td>
						</tr>
						<tr>
							<th>기능별 구분</th>
							<td colspan="2" id="uprFnc">${row.uprFnc}</td>
							<th>노선지정일</th>
							<td colspan="2" id="uprYmd">${row.uprYmd}</td>
						</tr>
						<tr>
							<th>기점 소재지</th>
							<td colspan="2" id="uprStr">${row.uprStr}</td>
							<th>종점 소재지</th>
							<td colspan="2" id="uprEnd">${row.uprEnd}</td>
						</tr>
						<tr>
							<th>총 연장 (m)</th>
							<td colspan="5" id="uprTot">${row.uprTot}</td>
						</tr>
						<tr>
							<th colspan="2">미개통도 연장 (m)</th>
							<td id="uprUnop">${row.uprUnop}</td>
							<th colspan="2">미개설도 연장 (m)</th>
							<td id="uprUnse">${row.uprUnse}</td>
						</tr>
						<tr>
							<th rowspan="3">포장도 (m)</th>
							<th>포장도 연장</th>
							<th>1차선</th>
							<td id="uprPa01">${row.uprPa01}</td>
							<th>2차선</th>
							<td id="uprPa02">${row.uprPa02}</td>
						</tr>
						<tr>
							<td rowspan="2" id="uprPatot">${row.uprPatot}</td>
							<th>4차선</th>
							<td id="uprPa04">${row.uprPa04}</td>
							<th>6차선</th>
							<td id="uprPa06">${row.uprPa06}</td>
						</tr>
						<tr>
							<th>8차선</th>
							<td id="uprPa08">${row.uprPa08}</td>
							<th>10차선 이상</th>
							<td id="uprPa10">${row.uprPa10}</td>
						</tr>
						<tr>
							<th rowspan="3">미포장도 (m)</th>
							<th>미포장도 연장</th>
							<th>1차선</th>
							<td id="uprUn01">${row.uprUn01}</td>
							<th>2차선</th>
							<td id="uprUn02">${row.uprUn02}</td>
						</tr>
						<tr>
							<td rowspan="2" id="uprUntot">${row.uprUntot}</td>
							<th>4차선</th>
							<td id="uprUn04">${row.uprUn04}</td>
							<th>6차선</th>
							<td id="uprUn06">${row.uprUn06}</td>
						</tr>
						<tr>
							<th>8차선</th>
							<td id="uprUn08">${row.uprUn08}</td>
							<th>10차선 이상</th>
							<td id="uprUn10">${row.uprUn10}</td>
						</tr>
						<tr>
							<th colspan="2">보도 설치일</th>
							<td id="uprWok">${row.uprWok}</td>
							<th colspan="2">보도 유효폭 (m)</th>
							<td id="uprWid">${row.uprWid}</td>
						</tr>
						<tr>
							<th colspan="2">보도 횡단경사 (%)</th>
							<td id="uprSle">${row.uprSle}</td>
							<th colspan="2">보도 연석높이 (m)</th>
							<td id="uprHgt">${row.uprHgt}</td>
						</tr>
						<tr>
							<th colspan="1">보도연장 (m)</th>
							<td id="uprWkt">${row.uprWkt}</td>
							<th colspan="1">보도좌측 (m)</th>
							<td id="uprWkl">${row.uprWkl}</td>
							<th colspan="1">보도우측 (m)</th>
							<td id="uprWkr">${row.uprWkr}</td>
						</tr>
						<tr>
							<th>비고</th>
							<td colspan="5" id="rmkExp">${row.remark}</td>
						</tr>
					</tbody>
				</table>
			</div>
			<div id="list_tab2" class="tab-panel-1"  style="height:481px">
				<table id="tableHistory" class="rowSelected">
					<tbody class="tbodyConstruction">
					</tbody>
				</table>
				<div class="window_footer">
					<div class="button_flat">
						<c:if test="${editAt eq 'Y' or editAt eq 'y'}">
							<button id="btn_cons_add" class="button_flat_normal btn_blue">등록</button>
						</c:if>
					</div>
				</div>
			</div>
			<div id="list_tab3" class="tab-panel-1"  style="height:481px">
				<table id="tableHistory" class="rowSelected">
					<tbody class="tbodyRepair">
					</tbody>
				</table>
				<div class="window_footer">
					<div class="button_flat">
						<c:if test="${editAt eq 'Y' or editAt eq 'y'}">
							<button id="btn_repa_add" class="button_flat_normal btn_blue">등록</button>
						</c:if>
					</div>
				</div>
			</div>
			<div id="list_tab4" class="tab-panel-1"  style="height:481px">
				<table id="tableHistory" class="rowSelected">
					<tbody class="tbodyPhoto">
					</tbody>
				</table>
				<div class="window_footer">
					<div class="button_flat">
						<c:if test="${editAt eq 'Y' or editAt eq 'y'}">
							<button id="btn_photo_add" class="button_flat_normal btn_blue">등록</button>
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
				</c:if>
				<button id="btn_close" class="button_flat_normal btn_black">닫기</button>
			</div>
		</div>
	</div>
</form>