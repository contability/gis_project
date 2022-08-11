<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 		     uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui"			 uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="fmt"			 uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form"		 uri="http://www.springframework.org/tags/form" %>

<form id="addPhotoForm" name="addPhotoForm" method="post" enctype="multipart/form-data" action="<c:url value='/ctrlpnt/insertPhoto.do' />">
	<div id="addPhoto" class="window_container">
		<div class="window_article">
			<input type="hidden" name="alias" value="${alias}" />
			<input type="hidden" name="imageSj" value="${alias}" />
			<input type="hidden" name="ftrCde" value="${ftrCde}" />
			<input type="hidden" name="ftrIdn" value="${ftrIdn}" />
			<input type="hidden" name="imageTy" value="${imageTy}" />
			<table id="tablePhoto" class="table_text">
				<tbody>
					<tr>
						<th>기준점 종류</th>
						<td>${alias}</td>
					</tr>
					<tr>
						<th>관리번호</th>
						<td>${ftrIdn}</td>
					</tr>
					<tr>
						<th>제목</th>
						<td>
							<input type="text" class="easyui-textbox" name="imageCn" style="width:99%"/>
						</td>
					</tr>
					<tr>
						<th scope="row">사진</th>
						<td>
							<p class="ma_b_10">
								<input type="file" name="orignlFileNm" value="" accept="image" />
							</p>
						</td>
					</tr>
				</tbody>
			</table>
		</div>
		<div class="window_footer">
			<div class="button_flat">
				<button id="btn_insert" class="button_flat_normal btn_blue">등록</button>
				<button id="btn_close" class="button_flat_normal btn_black">닫기</button>
			</div>
		</div>
	</div>
</form>