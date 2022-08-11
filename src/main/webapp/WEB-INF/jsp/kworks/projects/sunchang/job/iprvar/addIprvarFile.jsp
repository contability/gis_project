<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<div id="div_iprvar_file_insert" class="layerCont">
	<form enctype="multipart/form-data">
		<input type="hidden" name="iprvarNo" value="${iprvarNo }">
		<table id="etUV_main" class="cmmTable v2 ma_b_30" summary="정비보류지역 관리조서 부속자료 등록 테이블" style="table-layout: fixed; word-wrap: break-word;">
			<caption>정비보류지역 관리조서 부속자료 등록 테이블</caption>
			<colgroup>
				<col width="22%">
				<col width="*">
			</colgroup>
			<tbody>
				<tr>
					<th scope="row">제목</th>
					<td><input type="text" name="iprvarFileSj"></td>
				</tr>
				<tr>
					<th scope="row">파일</th>
					<td>
						<p class="ma_b_10">
							<input type="file" name="fileNm" accept="image/*|application/pdf">
						</p>
					</td>
				</tr>
			</tbody>
		</table>
		<div class="div_tool_photo_manage">
			<a id="a_add_iprvar_file" href="#" class="btn_register"></a>
			<a id="a_close_iprvar_file" href="#" class="btn_close"></a>
		</div>
	</form>
</div>