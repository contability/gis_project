<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 		     uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui"			 uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="fmt"			 uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form"		 uri="http://www.springframework.org/tags/form" %>

<form id="addChangeDetection" name="addRoadDsgnChangeDtls" method="post" enctype="multipart/form-data">
	<div class="window_container">
		<div class="window_article">
			<table class="table_text">
				<tbody>
					<tr>
						<th>제목</th>
						<td colspan="3">
							<input type="text" name="chngeDetctSj" class="input_text_w w_300" maxlength="99" style="font-family:맑은고딕;" />
						</td>
					</tr>
					<tr>
						<th>내용</th>
						<td colspan="3">
							<textarea name="chngeDetctSy" rows="7" class="w_300" style="resize: none; font-family:맑은고딕; overflow-y:hidden;"></textarea>
						</td>
					</tr>
				</tbody>
			</table>
		</div>
		<div class="window_footer">
			<div class="button_flat">
				<button class="btn_save_addChangeDetection button_flat_normal btn_blue">저장</button>
				<button class="btn_close_addChangeDetection button_flat_normal btn_blue">닫기</button>
			</div>
		</div>
	</div>
</form>
