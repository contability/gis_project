<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<style>
	#div_iprvar_update .inputTxt{
		width: 161px;
	}
	
	#div_iprvar_update .txtAr{
		width: 484px;
		height: 82px;
	}
	
</style>

<div id="div_iprvar_update" class="layerCont">
	<form method="post">
		<input type="hidden" id="ud_iprvarNo" name="iprvarNo">
		<table id="etUV_main" class="cmmTable v2 ma_b_30"
			summary="정비보류지역 관리조서 테이블"
			style="table-layout: fixed; word-wrap: break-word;">
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
					<td><select class="sel_dong"></select></td>
					<th scope="row">리</th>
					<td><select class="sel_li"></select></td>
				</tr>
				<tr>
					<th scope="row">본번</th>
					<td><input type="text" class="txt_mnnm"></td>
					<th scope="row">부번</th>
					<td><input type="text" class="txt_slno"></td>
				</tr>
				<tr>
					<th scope="row">산</th>
					<td>
						<input type="checkbox" id="chk_mntn">
						<input type="hidden" name="pnu">
					</td>
					<th scope="row">지목</th>
					<td><select class="sel_lndcgr" name="iprvarLndcgr"></select></td>
				</tr>
				<tr>
					<th scope="row">면적</th>
					<td><input type="text" name="iprvarAr" id="iprvarAr" class="inputTxt"></td>
					<th scope="row">축척</th>
					<td><input type="text" name="iprvarSc" id="iprvarSc" class="inputTxt"></td>
				</tr>
				<tr>
					<th scope="row">정정항목</th>
					<td><input type="text" name="updtIem" id="updtIem" class="inputTxt"></td>
					<th scope="row">도호</th>
					<td><input type="text" name="iprvarDoho" id="iprvarDoho" class="inputTxt"></td>
				</tr>
				<tr>
					<th scope="row">정비보류사유</th>
					<td colspan="3"><input type="text" name="iprvarWhy" id="iprvarWhy" class="inputLongTxt"></td>
				</tr>
				<tr>
					<th scope="row">비고</th>
					<!-- <td colspan="3"><input type="text" name="rmkExp" class="inputTxt"></td> -->
					<td><textarea rows="3" cols="3" name="rmkExp" id="rmkExp" class="txtAr"></textarea></td>
				</tr>
			</tbody>
		</table>
	</form>
</div>
<p class="btnRight v2">
	<button id="a_update_iprvar" class="button_flat_normal btn_blue">저장</button> 
	<button id="a_close_iprvar" class="button_flat_normal btn_blue">닫기</button>
</p>
