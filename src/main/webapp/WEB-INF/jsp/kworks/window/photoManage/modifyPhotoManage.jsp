<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 	 uri="http://java.sun.com/jsp/jstl/core" %>

<div class="layerCont">
	<form method="post" action="<c:url value='/photoManage/modify.do' />" enctype="multipart/form-data" >
		<table class="cmmTable v2" summary="사진정보 수정 테이블">
			<caption>사진정보 수정 테이블</caption>
			<colgroup>
				<col width="23%" />
				<col width="*" />
			</colgroup>
			<tbody id="tbl_phtot_manage_modify">
				<input type="hidden" id="txt_modify_photo_manage_image_no" name="imageNo" class="w100" value="" />
				<input type="hidden" id="txt_modify_photo_manage_ftr_idn" name="ftrIdn" class="w100" value="" />
				<input type="hidden" id="txt_modify_photo_manage_image_file_no" name="imageFileNo" class="w100" value="" />
				<input type="hidden" id="txt_modify_photo_manage_thumb_file_no" name="thumbFileNo" class="w100" value="" />
				<input type="hidden" id="txt_modify_photo_manage_outpt_file_no" name="outptFileNo" class="w100" value="" />
				<tr>
					<th scope="row">시설명</th>
					<td>
						<input id="txt_modify_photo_manage_ftr_cde_nam" class="w100" value="" disabled="disabled" />
						<input type="hidden" id="txt_modify_photo_manage_ftr_cde" name="ftrCde" class="w100" value="" />
					</td>
				</tr>	
				<tr>
					<th scope="row">제목</th>
					<td>
						<input type="text" id="txt_modify_photo_manage_image_sj" name="imageSj" class="w100" value="" />
					</td>
				</tr>
				<tr>
					<th scope="row">내용</th>
					<td>
						<textarea id="txt_modify_photo_manage_image_cn" name="imageCn" class="textArea w100 h_80"></textarea>
					</td>
				</tr>
				<tr>
					<th scope="row">비고</th>
					<td>
						<textarea id="txt_modify_photo_manage_rmkExp" name="rmkExp" class="textArea w100 h_80"></textarea>
					</td>
				</tr>
				<tr>
					<th>위치</th>
					<td>
					<input type="text" id="txt_modify_photo_manage_lc_x" class="w_115" name="lcX" value="" />&nbsp;<input type="text" id="txt_modify_photo_manage_lc_y" class="w_115" name="lcY" value="" />
					<a href="#" id="btn_modify_photo_manage_lcXY">
						<img src="<c:url value="/images/kworks/map/skin2/btn_modify2.png" />" alt="변경" />
					</a>
					</td>
				</tr>
				<tr>
					<th scope="row">파일</th>
					<td>
						<p class="ma_b_10">
							<input type="file" name="orignlFileNm" value="" />
						</p>
					</td>
				</tr>
			</tbody>
		</table>
		<div class="div_tool_photo_manage">
			<a class="a_save_modify_photo_manage btn_register" href="#" ></a>
			<a class="a_close_modify_photo_manage btn_close" href="#" ></a>
		</div>
	</form>
</div>
