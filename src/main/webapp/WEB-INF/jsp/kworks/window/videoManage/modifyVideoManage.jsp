<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 			uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" 			uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="fmt" 		uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form"		uri="http://www.springframework.org/tags/form" %>

<form id="videoManageUpModify" name="videoManageUpModify" method="post" enctype="multipart/form-data" >
	<input type="hidden" name="upNo" id="upNo" value="${result.upNo}">
	<input type="hidden" name="downNo" id="downNo" value="${result.downNo}">
	<input type="hidden" name="ftrIdn" id="ftrIdn" value="${result.ftrIdn}">
	<div id="div_window_section_plan" class="layerCont">
		<table class="cmmTable v2 ma_b_30" summary="영상관리  수정 테이블">
			<caption>영상관리 수정 테이블</caption>
			<colgroup>
				<col width="22%" />
				<col width="28%" />
				<col width="25%" />
				<col width="25%" />
			</colgroup>
			<tbody>
				<tr>
					<th scope="row">노선명</th>
					<td colspan="3">
						<input type="text" id="rotNam" name="rotNam" class="w100" value="${result.rotNam}" readonly="readonly" />
					</td>
				</tr>
				<tr>
					<th>노선번호</th>
					<td>
						<input type="text" id="rotIdn" name="rotIdn" class="w100" value="${result.rotIdn}" readonly="readonly" />
					</td>
					<th>구간</th>
					<td>
						<input type="text" id="secIdn" name="secIdn" class="w100" value="${result.secIdn}" readonly="readonly" />
					</td>
				</tr>
				<tr>
					<th scope="row">상행영상 파일</th>
					<td colspan="3">
						<p class="ma_b_10">
							<input type="file" name="videoManageUpFile" id="videoManageUpFile" value="" accept="video/mp4">
						</p>
					</td>
				</tr>
				<tr>
					<th scope="row">하행영상 파일</th>
					<td colspan="3">
						<p class="ma_b_10">
							<input type="file" name="videoManageDownFile" id="videoManageDownFile" value="" accept="video/mp4">
						</p>
					</td>
				</tr>
			</tbody>
		</table>
		<div class="div_tool_section_plan">
			<a class="a_save_modify_video_manage btn_register" href="#" ></a>
			<a class="a_close_modify_video_manage btn_close" href="#" ></a>
		</div>
	</form>
</div>