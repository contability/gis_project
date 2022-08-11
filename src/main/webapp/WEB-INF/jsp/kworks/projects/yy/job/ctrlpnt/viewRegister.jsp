<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<form id="viewControlPointform" name="viewControlPointform" method="post" enctype="multipart/form-data">
	<div id="viewControlPoint" class="window_container">
		<div class="window_article">
			<input type="hidden" name="ftrCde" value="${dataCde}" />
			<table class="table_text">
				<tbody>
					<tr>
						<th>기준점 종류</th>
						<td colspan="2">${row.ftrCde}</td>
						<th>관리번호</th>
						<td colspan="2" id="ftrIdn">${row.ftrIdn}</td>
					</tr>
				</tbody>
			</table>
			<div class="tabs-1">
				<ul class="tab-list-1">
					<li class="active"><a class="tab-control" href="#list_tab1">일반사항</a></li>
					<li><a class="tab-control" href="#list_tab2">시준점</a></li>
					<li><a class="tab-control" href="#list_tab3">사진</a></li>
				</ul>
			</div>
			<div id="list_tab1" class="tab-panel-1 active" style="height:310px">
				<table id="controlpoint_register" class="table_text">
					<tbody>
						<tr>
							<th>기준점명</th>
							<td colspan="2">${row.bseNam}</td>
							<th>원점</th>
							<td colspan="2">${row.pntNam}</td>
						</tr>
						<tr>
							<th rowspan="2">지역좌표계</th>
							<th>종선좌표(X)</th>
							<td colspan="4">${row.nbgX}</td>
						</tr>
						<tr>
							<th>횡선좌표(Y)</th>
							<td colspan="4">${row.nbgY}</td>
						</tr>
						<tr>
							<th rowspan="2">세계측지계</th>
							<th>종선좌표(X)</th>
							<td colspan="4">${row.nggX}</td>
						</tr>
						<tr>
							<th>횡선좌표(Y)</th>
							<td colspan="4">${row.nggY}</td>
						</tr>
						<tr>
							<th>위도(B)</th>
							<td colspan="2">${row.ngwB}</td>
							<th>경도(L)</th>
							<td colspan="2">${row.ngwL}</td>
						</tr>
						<tr>
							<th>표고</th>
							<td colspan="2">${row.bseHgt}</td>
							<th>자오선수차</th>
							<td colspan="2">${row.merCon}</td>
						</tr>
						<tr>
							<th>토지소재지</th>
							<td colspan="5">${row.setLoc}</td>
						</tr>
						<tr>
							<th>설치일자</th>
							<td>${row.setYmd}</td>
							<th>설치구분</th>
							<td>${row.setCde}</td>
							<th>표지재질</th>
							<td>${row.setMet}</td>
						</tr>
						<tr>
							<th>비고</th>
							<td colspan="5">${row.rmkDes}</td>
						</tr>
					</tbody>
				</table>
			</div>
			<div id="list_tab2" class="tab-panel-1"  style="height:310px">
				<table id="tablePassPoint" class="rowSelected">
					<tbody class="tbodyPassPoint">
					</tbody>
				</table>
				<div class="window_footer">
					<div class="button_flat">
						<button id="btn_pass_add" class="button_flat_normal btn_blue">등록</button>
					</div>
				</div>
			</div>
			<div id="list_tab3" class="tab-panel-1"  style="height:310px">
				<table id="tableImage" class="rowSelected">
					<tbody class="tbodyImage">
					</tbody>
				</table>
				<div class="window_footer">
					<div class="button_flat">
						<button id="btn_image_add" class="button_flat_normal btn_blue">등록</button>
					</div>
				</div>
			</div>
		</div>
		<div class="window_footer">
			<div class="button_flat">
				<button id="btn_print" class="button_flat_normal btn_blue">대장출력</button>
				<button id="btn_edit" class="button_flat_normal btn_blue">편집</button>
				<button id="btn_delete" class="button_flat_normal btn_blue">삭제</button>
				<button id="btn_close" class="button_flat_normal btn_black">닫기</button>
			</div>
		</div>
	</div>
</form>