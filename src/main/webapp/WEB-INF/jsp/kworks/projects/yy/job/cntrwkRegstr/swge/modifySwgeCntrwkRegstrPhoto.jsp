<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 		     uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui"			 uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="fmt"			 uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form"		 uri="http://www.springframework.org/tags/form" %>

<form id="swgeCntrwkRegstrPhotoModifyFrom" name="swgeCntrwkRegstrPhotoModifyFrom" method="post" enctype="multipart/form-data">
	<div id="swgeCntrwkRegstrPhotoModify" class="window_container">
		<div class="window_article">
			<table class="table_text">
				<tbody>
					<tr>
						<th>제목</th>
						<td>
							<input type="text" name="imageSj" class="input_text_w w_300" value="${result.imageSj}" />
						</td>
					</tr>
					<tr>
						<th>설명</th>
						<td>
							<div class="h_54">
								<textarea name="imageCn" rows="4" cols="47" >${result.imageCn}</textarea>
							</div>
						</td>
					</tr>
					<tr>
						<th>사진위치</th>
						<td>
							<input type="text" name="lcX" class="input_texts w_110"  value="${result.lcX}" >
							<input type="text" name="lcY" class="input_texts w_110"  value="${result.lcY}" >
							<button id="getLcXY" class="button_flat_small btn_grey">변경</button>
						</td>
					</tr>
					<tr>
						<th>사진파일</th>
						<td>
							<input id="swgeCntrwkPhoto" name="swgeCntrwkPhoto" type="file">
						</td>
					</tr>
				</tbody>
			</table>
		</div>
		<div class="window_footer">
			<div class="button_flat">
				<button id="a_save_swgeCntrwkRegstrPhotoModify" class="button_flat_normal btn_blue">저장</button>
			</div>
		</div>
	</div>
</form>