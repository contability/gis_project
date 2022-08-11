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
					<td colspan="5" id="ftrIdn">
						${result.ftrIdn}
					</td>
				</tr>
			</tbody>
		</table>
		<div class="tabs-1">
			<ul class="tab-list-1">
				<li class="active"><a class="tab-control" href="#list_tab1">일반사항</a></li>
				<li><a class="tab-control" href="#list_tab2">부속자료</a></li>
			</ul>
		</div>
		<div class="tab-panel-1 active" id="list_tab1" style="height:540px">
			<table class="table_text">
				<tbody>
					<tr>
						<th>정책명</th>
						<td colspan="5" id="plcyTit">
						${result.plcyTit}
						</td>
					</tr>
					<tr>
						<th>대표지번</th>
						<td colspan="5">
						<span class="space" id = "selDong">${result.selDong}</span>
						<span class="space" id = "isMount">${result.isMount}</span>
						<span class="space" id = "mainNum">${result.mainNum}</span>-
						<span class="space" id = "subNum">${result.subNum}</span>
						</td>
					</tr>
					<tr>
						<th>실과소</th>
						<td colspan="1" id="plcyDep">
						${result.plcyDep}
						</td>
						<th>담당부서</th>
						<td colspan="1" id="deptSbNm">
						${result.deptSbNm}
						</td>
						<th>담당자</th>
						<td colspan="1" id="plcyMng">
						${result.plcyMng}
						</td>
					</tr>
					<tr>
						<th>계약일</th>
						<td id="ctrctDt">
						${result.ctrctDt}
						</td>
						<th>착공일</th>
						<td id="cttBeg">
						${result.cttBeg}
						</td>
						<th>준공일</th>
						<td id="cttFrn">
							${result.cttFrn}
						</td>
					</tr>
					<tr>
						<th>도급자 상호</th>
						<td colspan="2" id="cttNam">
						${result.cttNam}
						</td>
						<th>공사금액</th>
						<td colspan="2" id="ctrctamt">
							${result.ctrctamt}
						</td>
					</tr>
					<tr>
						<th>비고</th>
						<td colspan="5" id="rmkExp">
						${result.rmkExp}
						</td>
					</tr>
					<tr>
					<th>보고서</th>
					<td colspan="5" id="repoDoc" style="height:339px">
						${rows.repoDoc}
					</td>
				</tr>
				</tbody>
			</table>
		</div>
		<div class="tab-panel-1" id="list_tab2" style="height:540px">
				<table id="tableImage" class="rowSelected">
					<tbody class="tbodyImage">
					</tbody>
				</table>
			<div class="window_footer">
				<div class="button_flat">
					<button class="btn_add_KwsImagePolicyRegstr button_flat_normal btn_blue">등록</button>
				</div>
			</div>
		</div>
	</div>
	<div class="window_footer">
		<div class="button_flat">
			<button class="btn_pnt_PlcyStatAsRegstr button_flat_normal btn_blue">대장출력</button>
			<button class="btn_modify_PlcyStatAsRegstr button_flat_normal btn_blue">편집</button>
			<button class="btn_remove_PlcyStatAsRegstr button_flat_normal btn_blue">삭제</button>
			<button class="btn_close_PlcyStatAsRegstr button_flat_normal btn_blue">닫기</button>
		</div>
	</div>
</div>
