<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 			uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" 			uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="fmt" 		uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form"		uri="http://www.springframework.org/tags/form" %>

<div id="EnviroRegister" class="window_container">
	<div class="window_article">
	<input type="hidden" id="pnu" value="${result.pnu}" />
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
				<li class="active"><a class="tab-control" href="#list_tab1">현지조사표</a></li>
				<li><a class="tab-control" href="#list_tab2">제거작업일지</a></li>
				<li><a class="tab-control" href="#list_tab3">사진관리</a></li>
			</ul>
		</div>
		<div class="tab-panel-1 active" id="list_tab1">
			<table class="table_text">
				<tbody>
					<tr>
						<th>조사대상</th>
						<td colspan="3" id="expType">
						${result.expType}
						</td>
						<th>조사자</th>
						<td colspan="1" id="invNam">
						${result.invNam}
						</td>
					</tr>
					<tr>
						<th>조사일</th>
						<td colspan="1" id="invDate">
						${result.invDate}
						</td>
						<th>조사장소</th>
						<td colspan="3" id="address">
						${result.address}
						</td>
					</tr>
					<tr>
						<th>분포지 유형</th>
						<td colspan="2" id="disType">
						${result.disType}
						</td>
						<th>분포 면적</th>
						<td colspan="2" id="disArea">
						${result.disArea}
						</td>
					</tr>
					<tr>
						<th>분포 밀도</th>
						<td colspan="1" id="disDens">
						${result.disDens}
						</td>
						<th>평균 키</th>
						<td colspan="1" id="expSize">
						${result.expSize}
						</td>
						<th>생육단계</th>
						<td colspan="1" id="expStep">
						${result.expStep}
						</td>
					</tr>
					<tr>
						<th>특이사항</th>
						<td colspan="5" id="invNote">
						${result.invNote}
						</td>
					</tr>
				</tbody>
			</table>
		</div>
		
		<div class="tab-panel-1" id="list_tab2">
			<table id="tableHistory" class="rowSelected">
				<tbody class="reantList">
				</tbody>
			</table>
		</div>
		
		<div class="tab-panel-1" id="list_tab3">
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
