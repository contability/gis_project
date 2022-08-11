<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<div id="div_iprvar_select" class="layerCont">
	<input type="hidden" id="iprvarNo" value="${result.iprvarNo }">
	<table id="etUV_main" class="cmmTable v2 ma_b_30" summary="정비보류지역 관리조서 테이블" style="table-layout: fixed; word-wrap: break-word;">
		<caption>정비보류지역 관리조서 테이블</caption>
		<colgroup>
			<col width="22%">
			<col width="28%">
			<col width="20%">
			<col width="30%">
		</colgroup>
		<tbody>
			<tr>
				<th scope="row">읍면동</th>
				<td id="detail_emd"></td>
				<th scope="row">리</th>
				<td id="detail_li"></td>
			</tr>
			<tr>
				<th scope="row">지번</th>
				<td id="detail_jibun"></td>
				<th scope="row">지목</th>
				<td id="detail_lndcgr"></td>
			</tr>
			<tr>
				<th scope="row">면적</th>
				<td id="detail_area"></td>
				<th scope="row">축척</th>
				<td id="detail_scale"></td>
			</tr>
			<tr>
				<th scope="row">정정항목</th>
				<td id="detail_iem"></td>
				<th scope="row">도호</th>
				<td id="detail_doho"></td>
			</tr>
			<tr>
				<th scope="row">정비보류사유</th>
				<td colspan="3" id="detail_why"></td>
			</tr>
			<tr>
				<th scope="row">비고</th>
				<td colspan="3" id="detail_rmkExp"></td>
			</tr>
		</tbody>
	</table>
	
	<!-- <div class="panel-title">정비보류지역 관리조서 부속 자료</div> -->
	<div>
		<table class="cmmTable v2 ma_b_30" summary="정비보류지역 관리조서 부속 자료 테이블" id="iprvarFileTable">
			<caption>정비보류지역 관리조서 부속 자료 테이블</caption>
			<thead>
				<tr>
					<th class="center" colspan="2">정비보류지역 관리조서 부속자료</th>
				</tr>
				<tr>
					<th class="center one"><input type="checkbox" id="allChk"></th>
					<th class="center two">제목</th>
				</tr>
			</thead>
			<tbody id="fileTBody">
			</tbody>
		</table>
		<div class="window_footer">
			<div class="button_flat">
				<a id="a_down_iprvar_file" href="#" class="btnBlue">내려받기</a>
				<a id="a_add_iprvar_file" href="#" class="btnBlue">등록</a>
				<a id="a_remove_iprvar_file" href="#" class="btnBlue">삭제</a>
			</div>
		</div>
	</div>
</div>
<p class="btnRight v2">
	<button id="a_remove_iprvar" class="button_flat_normal btn_blue">삭제</button>
	<button id="a_update_iprvar" class="button_flat_normal btn_blue">편집</button>
	<button id="a_close_iprvar" class="button_flat_normal btn_blue">닫기</button>
</p>
