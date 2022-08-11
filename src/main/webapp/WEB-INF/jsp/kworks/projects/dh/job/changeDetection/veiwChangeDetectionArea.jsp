<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 		     uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui"			 uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="fmt"			 uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form"		 uri="http://www.springframework.org/tags/form" %>

<div class="window_container">
	<div class="window_article">
		<table class="table_text">
			<tbody>
				<tr>
					<th>위치</th>
					<td class="chngeAreaLc"></td>
				</tr>
				<tr>
					<th>설명</th>
					<td class="chngeAreaDc"></td>
				</tr>
				<tr style="height:54px;">
					<th>특이사항</th>
					<td class="chngeAreaRm"></td>
				</tr>
			</tbody>
		</table>
	</div>
	<div class="window_footer">
		<div class="button_flat">
			<button class="btn_modify_addChangeDetection button_flat_normal btn_blue" data-chnge-area-no="${data.chngeAreaNo}" data-chnge-detct-no="${data.chngeDetctNo}">편집</button>
			<button class="btn_remove_addChangeDetection button_flat_normal btn_blue" data-chnge-area-no="${data.chngeAreaNo}" data-chnge-detct-no="${data.chngeDetctNo}">삭제</button>
			<button class="btn_close_addChangeDetection button_flat_normal btn_blue" data-chnge-area-no="${data.chngeAreaNo}" data-chnge-detct-no="${data.chngeDetctNo}">닫기</button>
		</div>
	</div>
</div>
