<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 			uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" 			uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="fmt" 		uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form"		uri="http://www.springframework.org/tags/form" %>

<form id="fom_riverSidePoint_view" name="riverSidePointViewform" method="get" enctype="multipart/form-data">
	<input type="hidden" id="ftrCde" value="${result.ftrCde}" />
	<input type="hidden" id="staIdn" value="${result.staIdn}" />
	<input type="hidden" id="imageNo" value="${result.riverSidePointImage.imageNo}" />
	<div id="div_riverSidePoint_view" class="window_container">
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
							<div>
								${result.staNam}
							</div>
						</td>
					</tr>
					<tr>
						<th>설명</th>
						<td>
							<div>
								${result.imaExp}
							</div>
						</td>
					</tr>
					<tr class="h_320">
						<th>사진파일</th>
						<td>
							<c:if test="${result.riverSidePointImage ne null}">
								<img src="<c:url value='/cmmn/image/${result.riverSidePointImage.imageNo}/thumbnail.do"' />" width="300" height="320" alt="사진"/>
							</c:if>
						</td>
					</tr>
				</tbody>
			</table>
		</div>
		<div class="window_footer">
			<div class="button_flat">
				<button class="btn_down_riverSidePointView button_flat_width_custom w_80 btn_blue">사진 내려받기</button>
				<button class="btn_modify_riverSidePointView button_flat_width_custom w_80 btn_blue">편집</button>
			</div>
		</div>
	</div>
</form>
