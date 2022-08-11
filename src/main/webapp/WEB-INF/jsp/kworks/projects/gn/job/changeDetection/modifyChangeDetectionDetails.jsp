<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 		     uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui"			 uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="fmt"			 uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form"		 uri="http://www.springframework.org/tags/form" %>

<form id="modifyChangeDetectionDetails" name="modifyChangeDetectionDetails" method="post" enctype="multipart/form-data">
	<div class="window_container">
		<div class="window_article">
			<table class="table_text">
				<tbody>
					<tr>
						<th>제목</th>
						<td>
							<input type="text" name="chngeDetctSj" class="input_text_w w_300" style="font-family:맑은고딕;" value="${data.chngeDetctSj}" />
						</td>
					</tr>
					<tr>
						<th>개요</th>
						<td>
							<textarea name="chngeDetctSy" rows="7" class="w_300" style="resize: none; font-family:맑은고딕;">${data.chngeDetctSy}</textarea>
						</td>
					</tr>
				</tbody>
			</table>
		</div>
		<div class="window_footer">
			<div class="button_flat">
				<button type="button" class="btn_save_modifyChangeDetectionDetails button_flat_normal btn_blue" data-chnge-detct-no="${data.chngeDetctNo}">수정</button>
				<button type="button" class="btn_close_modifyChangeDetectionDetails button_flat_normal btn_blue" data-chnge-detct-no="${data.chngeDetctNo}">닫기</button>
			</div>
		</div>
	</div>
</form>