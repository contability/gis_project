<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 		     uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui"			 uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="fmt"			 uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form"		 uri="http://www.springframework.org/tags/form" %>

<form id="swgeCntrwkRegstrPhotoAddFrom" name="swgeCntrwkRegstrPhotoAddFrom" method="post" enctype="multipart/form-data">
	<div id="swgeCntrwkRegstrPhotoAdd" class="window_container">
		<div class="window_article">
			<table class="table_text">
				<tbody>
					<tr>
						<th>제목</th>
						<td>
							<input type="text" name="imageSj" class="input_text" >
						</td>
					</tr>
					<tr>
						<th>설명</th>
						<td>
							<div class="h_54">
								<textarea name="imageCn" rows="4" cols="47"></textarea>
							</div>
						</td>
					</tr>
					<tr>
						<th>사진위치</th>
						<td>
							<input type="text" name="lcX" class="input_texts w_110" >
							<input type="text" name="lcY" class="input_texts w_110" >
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
				<button id="a_save_swgeCntrwkRegstrPhotoAdd" class="button_flat_normal btn_blue">저장</button>
			</div>
		</div>
	</div>
</form>