<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 		     uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui"			 uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="fmt"			 uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form"		 uri="http://www.springframework.org/tags/form" %>

<div id="swgeCntrwkRegstrPhotoView" class="window_container">
	<div class="window_article">
		<table class="table_text">
			<tbody>
				<tr>
					<th>제목</th>
					<td>
						<input type="text" name="imageSj" class="input_text_w w_300" value="${result.imageSj}" readonly />
					</td>
				</tr>
				<tr>
					<th>설명</th>
					<td>
						<div class="h_54">
							<textarea name="imageCn" rows="4" cols="47" readonly >${result.imageCn}</textarea>
						</div>
					</td>
				</tr>
				<tr>
					<th>사진위치</th>
					<td>
						<input type="text" name="lcX" class="input_text_w w_148"  value="${result.lcX}" readonly />
						<input type="text" name="lcY" class="input_text_w w_148"  value="${result.lcY}" readonly />
					</td>
				</tr>
				<tr>
					<th>사진파일</th>
					<td>
						<img src="<c:url value='/cmmn/image/${result.imageNo}/thumbnail.do"' />" width="300" height="320" />
					</td>
				</tr>
			</tbody>
		</table>
	</div>	
</div>
