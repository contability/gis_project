<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 			uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" 			uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="fmt" 		uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form"		uri="http://www.springframework.org/tags/form" %>

<div id="ReantRegister" class="window_container">
	<div class="window_article">
	<input type="hidden" id="expIdn" value="${result.expIdn}" />
		<table class="table_text">
			<tbody>
				<tr>
					<th>관리번호</th>
					<td colspan="5" id="ftrIdn">
					 ${result.ftrIdn}
					</td>
				</tr>
			</tbody>
		</table>
		<div class="tabs-1">
			<ul class="tab-list-1">
				<li class="active"><a class="tab-control" href="#list_tab1">제거작업일지</a></li>
				<li><a class="tab-control" href="#list_tab2">사진관리</a></li>
			</ul>
		</div>
		<div class="tab-panel-1 active" id="list_tab1">
			<table class="table_text">
				<tbody>
					<tr>
						<th>주소</th>
						<td colspan="2" id="address">
						${result.address}
						</td>
						<th>작업인원</th>
						<td colspan="2" id="jobPers">
						${result.jobPers}
						</td>
					</tr>
					<tr>
						<th>작업일</th>
						<td colspan="2" id="jobDate">
						${result.jobDate}
						</td>
						<th>작업유형</th>
						<td colspan="2" id="jobType">
						${result.jobType}
						</td>
					</tr>
					<tr>
						<th>작업내용</th>
						<td colspan="5" id="jobNote">
						${result.jobNote}
						</td>
					</tr>
					<tr>
						<th>간성읍<br>작업면적</th>
						<td colspan="2" id="areaGs">
						${result.areaGs}
						</td>
						<th>거진읍<br>작업면적</th>
						<td colspan="2" id="areaGj">
						${result.areaGj}
						</td>
					</tr>
					<tr>
						<th>현내면<br>작업면적</th>
						<td colspan="1" id="areaHn">
						${result.areaHn}
						</td>
						<th>죽원면<br>작업면적</th>
						<td colspan="1" id="areaJw">
						 ${result.areaJw}
						</td>
						<th>토성면<br>작업면적</th>
						<td colspan="1" id="areaTs">
						${result.areaTs}
						</td>
					</tr>
					<tr>
						<th>특이사항</th>
						<td colspan="5" id="etcNote">
						${result.etcNote}
						</td>
					</tr>
				</tbody>
			</table>
		</div>
		<div class="tab-panel-1" id="list_tab2">
				<table id="tableHistory" class="rowSelected">
					<tbody class = "photoList">
					</tbody>
				</table>
			<div class="window_footer">
				<div class="button_flat">
					<button class="btn_add_photo button_flat_normal btn_blue">등록</button>
				</div>
			</div>
		</div>
	</div>
	<div class="window_footer">
		<div class="button_flat">
			<button class="btn_prt button_flat_normal btn_blue">출력</button>
			<button class="btn_edi button_flat_normal btn_blue">편집</button>
			<button class="btn_rem button_flat_normal btn_blue">삭제</button>
			<button class="btn_cls button_flat_normal btn_blue">닫기</button>
		</div>
	</div>
</div>
