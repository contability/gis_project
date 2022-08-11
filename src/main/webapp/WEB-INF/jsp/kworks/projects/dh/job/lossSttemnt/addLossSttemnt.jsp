<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 			uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" 			uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="fmt" 		uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form"		uri="http://www.springframework.org/tags/form" %>

<form id="bisELAddform" name="bisELAddform" method="post" enctype="multipart/form-data" >
	<div class="layerCont">
		<table class="cmmTable v2" summary="기준점망실신고 세부내용 관련 테이블">
			<caption>기준점망실신고 등록 테이블</caption>
			<colgroup>
				<col width="20%" />
				<col width="*" />
			</colgroup>
			<tbody>
				<tr>
					<th scope="row">신고제목</th>
					<td><input type="text" name="lptDes" class="inputText w100" maxlength="112" /></td>
				</tr>
				<tr>
					<th scope="row">신고내용</th>
					<td><textarea name="lpcExp" class="textArea w100 h_50"></textarea></td>
				</tr>
				<tr>
					<th scope="row">비고(불가사유)</th>
					<td><textarea name="lpaExp" class="textArea w100 h_50"></textarea></td>
				</tr>
			</tbody>
		</table>
	</div>
</form>

<p class="btnRight">
	<a id="a_save_lossSttemntAdd" href="#" class="btnBlue">등록</a>
</p>
