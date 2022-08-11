<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 		     uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui"			 uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="fmt"			 uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form"		 uri="http://www.springframework.org/tags/form" %>

<form id="fom_image_view" name="imageViewform" method="get" enctype="multipart/form-data">
	<div id="div_image_view" class="window_container">
		<div class="window_article">
			<table class="table_text">
				<tbody>
					<tr>
						<th>제목</th>
						<td>
							${result.imageSj}
						</td>
					</tr>
					<tr class="h_65">
						<th>설명</th>
						<td>
							<div class="h_54">
								${result.imageCn}
							</div>
						</td>
					</tr>
					<tr>
						<th>사진위치</th>
						<td>
							<input type="text" name="lcX" class="input_text_w w_148"  value="X:${result.lcX}" readonly />
							<input type="text" name="lcY" class="input_text_w w_148"  value="Y:${result.lcY}" readonly />
						</td>
					</tr>
					<tr class="h_320">
						<th>사진파일</th>
						<td>
							<img src="<c:url value='/cmmn/image/${result.imageNo}/thumbnail.do"' />" width="300" height="320" />
						</td>
					</tr>
				</tbody>
			</table>
		</div>
		<div class="window_footer">
			<div class="button_flat">
			<button class="btn_down_image_view button_flat_width_custom w_80 btn_blue">사진 내려받기</button>
			</div>
		</div>
	</div>
</form>