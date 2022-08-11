<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 		     uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui"			 uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="fmt"			 uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form"		 uri="http://www.springframework.org/tags/form" %>

<form id="roadCntrwkRegstrPhotoModifyFrom" name="roadCntrwkRegstrPhotoModifyFrom" method="post" enctype="multipart/form-data">
<input type="hidden" id="staIdn" value="${result.staIdn}" />
	<div id="roadCntrwkRegstrPhotoModify" class="window_container">
		<div class="window_article">
			<table class="table_text">
				<tbody>
					<tr>
						<th>하천명</th>
						<td>
							${result.rivNam}
						</td>
					</tr>
					<tr>
						<th>측점명</th>
						<td>
							${result.staNam}
						</td>
					</tr>
					<tr>
						<th>설명</th>
						<td>
							<input type="text" name="imaExp" class="input_text_w w_210" value="${result.imaExp}" />
						</td>
					</tr>
					<tr>
						<th>사진파일</th>
						<td>
							<input id="riverSidePointImageFile" name="riverSidePointImageFile" type="file">
						</td>
					</tr>
				</tbody>
			</table>
		</div>
		<div class="window_footer">
			<div class="button_flat">
				<button id="btn_save_riverSidePointModify" class="button_flat_normal btn_blue">저장</button>
			</div>
		</div>
	</div>
</form>