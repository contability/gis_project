<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 		     uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="window_container">
	<div class="window_article">
		<form>
			<table class="table_text">
				<tbody>
					<tr>
						<th>제목</th>
						<td><input type="text" name='atchSj' class="registert_atchSj">
						</td>
					</tr>
					<tr>
						<th>내용</th>
						<td><input type="text" name ='atchCn' class="register_atchCn">
						</td>
					</tr>
					<!-- <tr>
						<th>비고</th>
						<td><input type="text" name ='rmkExp' class="register_atchRmkExp">
						</td>
					</tr> -->
					<tr>
						<th>파일</th>
						<td><input type="file" name='fileNm' class="register_file">
						</td>
					</tr>
				</tbody>
			</table>
		</form>
	</div>
	<div class="window_footer">
		<div class="button_flat">
			<button class="btn_savePrtsAreaHt_atch button_flat_normal btn_blue">저장</button>
			<button class="btn_closePrtsAreaHt_atch button_flat_normal btn_blue">닫기</button>
		</div>
	</div>
</div>
