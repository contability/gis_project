<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 	 uri="http://java.sun.com/jsp/jstl/core" %>

<div class="layerCont">
	<form method="post" action="<c:url value='/ocpat/insertOcpatFile.do' />" enctype="multipart/form-data" >
		<input type="hidden" id="txt_add_file_manage_ftr_idn" name="ftrIdn" class="w100" value="" />
		<table class="cmmTable v2" summary="파일정보 등록 테이블">
			<caption>사진정보 등록 테이블</caption>
			<colgroup>
				<col width="23%" />
				<col width="*" />
			</colgroup>
			<tbody id="tbl_file_manage_add">
				<tr>
					<th scope="row">대장명</th>
					<td>
						<input id="txt_add_file_manage_ftr_cde_nam" class="w100" value="" disabled="disabled" />
						<input type="hidden" id="txt_add_file_manage_ftr_cde" name="ftrCde" class="w100" value="" />
					</td>
				</tr>	
				<tr>
					<th scope="row">부속자료</th>
					<td>
						<select name="fileSj" class="w100 easyui-combobox">
								<option value="OCP001">점용 허가증</option>
								<option value="OCP002">점용허가 신청서</option>
								<option value="OCP003">점용허가 변경신청서</option>
								<option value="OCP004">준공확인 신청서</option>
								<option value="OCP005">취소신청서</option>
								<option value="OCP006">취하서</option>
								<option value="OCP011">위치도</option>
								<option value="OCP012">구적도</option>
								<option value="OCP013">현황실측도</option>
								<option value="OCP014">현황실측도-구적도</option>
								<option value="OCP015">현장사진</option>
								<option value="OCP016">준공사진</option>
								<option value="OCP017">설계개요</option>
								<option value="OCP018">공사계획평면도</option>
								<option value="OCP019">공사복구계획평면도</option>
								<option value="OCP020">시설배치계획평면도</option>
								<option value="OCP021">배치도</option>
								<option value="OCP022">건물배치도</option>
								<option value="OCP023">지적현황측량성과도</option>
								<option value="OCP024">공사계획평면도-구적도</option>
								<option value="OCP025">공사완료평면도</option>
								<option value="OCP026">시설배치 및 복구계획평면도</option>
								<option value="OCP027">시설배치 및 공사계획평면도</option>
								<option value="OCP028">원상복구계획도</option>
								<option value="OCP029">토지이용계획도</option>
								<option value="OCP030">현황실측도-구적도-공사계획평면도</option>
								<option value="OCP031">1층평면도</option>
								<option value="OCP032">단면상세도</option>
								<option value="OCP033">면적산출도</option>
								<option value="OCP034">시설물도안</option>
								<option value="OCP035">참고용도면</option>
								<option value="OCP036">첨부자료</option>
								
						</select>
					</td>
				</tr>
				<tr>
					<th scope="row">내용</th>
					<td>
						<textarea id="txt_add_file_manage_image_cn" name="fileCn" class="textArea w100 h_80"></textarea>
					</td>
				</tr><%-- 
				<tr>
					<th>위치</th>
					<td>
					<input type="text" id="txt_add_photo_manage_lc_x" class="w_115" name="lcX" value="" />&nbsp;<input type="text" id="txt_add_photo_manage_lc_y" class="w_115" name="lcY" value="" />
					<a href="#" id="btn_add_photo_manage_lcXY">
						<img src="<c:url value="/images/kworks/map/skin2/btn_modify2.png" />" alt="변경" />
					</a>
					</td>
				</tr> --%>
				<tr>
					<th scope="row">파일</th>
					<td>
						<p class="ma_b_10">
							<input type="file" name="orignlFileNm" value="" accept="image/*|application/pdf"/>
						</p>
					</td>
				</tr>
			</tbody>
		</table>
		<div class="div_tool_photo_manage">
			<a class="a_save_add_file_manage btn_register" href="#" ></a>
			<a class="a_close_add_file_manage btn_close" href="#" ></a>
		</div>
	</form>
</div>
