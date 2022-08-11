<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<div class="window_container">
	<div class="window_article">
		<table class="table_text">
			<tbody>
				<tr>
					<th>관리번호</th>
					<td colspan="5" id="ftrIdn"></td>
				</tr>
			</tbody>
		</table>
		<div class="tabs-1">
			<ul class="tab-list-1">
				<li class="active"><a class="tab-control" href="#list_tab1">일반사항</a></li>
				<li><a class="tab-control" href="#list_tab2">변경이력</a></li>
				<li><a class="tab-control" href="#list_tab3">부속자료</a></li>
			</ul>
		</div>
		<div class="tab-panel-1 active" id="list_tab1">
			<table class="table_text">
				<tbody>
					<tr>
						<th>허가번호</th>
						<td colspan="2" id="pmtNum"></td>
						<th>허가일</th>
						<td colspan="2" id="pmtYmd"></td>
					</tr>
					<tr>
						<th>점용자 성명</th>
						<td colspan="2" id="prsNam"></td>
						<th>주민등록번호</th>
						<td colspan="2" id="regNum"></td>
					</tr>
					<tr>
						<th>점용자 주소</th>
						<td colspan="5" id="prsAdr"></td>
					</tr>
					<tr>
						<th>점용 위치</th>
						<td colspan="5" id="jygLoc"></td>
					</tr>
					<tr>
						<th>점용 목적</th>
						<td colspan="5" id="jygPur"></td>
					</tr>
					<tr>
						<th>점용 면적</th>
						<td colspan="2" id="jygAra"></td>
						<th>점용 기간</th>
						<td colspan="2">
						<span class="space" id = "jygUlm"></span>:
						<span class="space" id = "jysYmd"></span>~<span class="space" id="jyeYmd"></span>
						</td>
					</tr>
					<tr>
						<th>구조</th>
						<td colspan="5" id="jygCmt"></td>
					</tr>
					<tr>
						<th>준공일자</th>
						<td colspan="2" id="endYmd"></td>
						<th>연락처</th>
						<td colspan="2" id="jygTel"></td>
					</tr>
					<tr>
						<th>대장유형</th>
						<td colspan="2" id="oldCde"></td>
						<th style="width:99%;">최초대장관리번호
						</th>
						<td colspan="2" id="oldIdn"></td>
					</tr>
					<tr>
						<th>도로종류</th>
						<td id="rodTyp"></td>
						<th>노선명</th>
						<td id="rotNam"></td>
						<th>읍면동</th>
						<td id="umdNam"></td>
					</tr>
					<tr>
						<th>지번주소</th>
						<td colspan="5"></td>
						<span class="space" id = "selDong"></span>
						<span class="space" id = "isMount"></span>
						<span class="space" id = "mainNum"></span>-
						<span class="space" id = "subNum"></span>
					</tr>
					<tr>
						<th>비고</th>
						<td colspan="5" id = "rmkExp"></td>
					</tr>
				</tbody>
			</table>
		</div>
		<div class="tab-panel-1" id="list_tab2">
			<table id="ocpatTable_Change" class="table_text tableSelector">
				<tbody class="tbody_2">
					<tr>
						<th style="text-align: center;">번호</th>
						<th colspan="2" style="text-align: center;">변동 사항</th>
						<th colspan="3" style="text-align: center;">변동 허가번호</th>
					</tr>
				</tbody>
			</table>
			<div class="window_footer">
				<div class="button_flat">
					<button class="btn_add_ocpatChange button_flat_normal btn_blue">등록</button>
				</div>
			</div>
		</div>
		<div class="tab-panel-1" id="list_tab3">
			 <table id="ocpatTable_file" class="table_text">
				<colgroup>
			 		<col width="92px">
			 		<col width="120px">
			 		<col width="">
			 		<col width="">
			 	</colgroup>
				<tbody class="tbody_2">
					<tr>
						<th>부속자료 번호</th>
						<th>부속자료 종류</th>
						<th colspan="5">내용</th>
						<th>등록일자</th>
					</tr>
				</tbody>
			</table> 
			<div class="window_footer">
				<div class="button_flat_l" style="padding-left:5px;">
					<a class="a_preview_view_file btn_preview" href="#" ></a>
					<a class="a_down_view_file btn_down" href="#" ></a>
				</div>
				<div class="button_flat">
					<a class="a_add_list_file btn_register" href="#" ></a>
					<a class="a_modify_view_file btn_edit" href="#" ></a>
					<a class="a_remove_view_file btn_del" href="#" ></a>
				</div>
			</div>
		</div>
	</div>
	<div class="window_footer">
		<div class="button_flat">
			<button class="btn_modify_ocpatPrint button_flat_normal btn_blue">대장출력</button>
			<button class="btn_update_ocpatRegstr button_flat_normal btn_blue">편집</button>
			<button class="btn_remove_RdtOcpatDtRegstr button_flat_normal btn_blue">삭제</button>
			<button class="btn_close_ocpatRegstrAdd button_flat_normal btn_blue">닫기</button>
		</div>
	</div>
</div>
