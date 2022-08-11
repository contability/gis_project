<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 	 uri="http://java.sun.com/jsp/jstl/core" %>

<div class="layerCont">
	<form method="post" action="<c:url value='/rest/sharingMap/add.do' />" enctype="multipart/form-data" >
		<input type="hidden" id="hid_add_sharing_map_ftr_cde" name="ftrCde" class="w100" value="" />
		<table class="cmmTable v2" summary="공유지도 등록 테이블">
			<caption>공유지도 등록 테이블</caption>
			<colgroup>
				<col width="23%" />
				<col width="*" />
			</colgroup>
			<tbody id="tbl_sharing_map_add">
				<tr>
					<th scope="row">제목</th>
					<td>
						<input type="text" id="txt_add_sharing_map_poi_sj" name="poiSj" class="w100" value="" />
					</td>
				</tr>
				<tr>
					<th scope="row">내용</th>
					<td>
						<textarea id="txt_add_sharing_map_poi_cn" name="poiCn" class="textArea w100 h_80"></textarea>
					</td>
				</tr>
				<tr>
					<th>위치</th>
					<td>
					<input type="text" id="txt_add_sharing_map_lc_x" class="w_115" name="lcX" value="" />&nbsp;<input type="text" id="txt_add_sharing_map_lc_y" class="w_115" name="lcY" value="" />
					<a href="#" id="btn_add_sharing_map_lcXY">
						<img src="<c:url value="/images/kworks/map/skin2/btn_modify2.png" />" alt="변경" />
					</a>
					</td>
				</tr>
				<tr>
					<th scope="row">파일</th>
					<td>
						<p class="ma_b_10">
							<input type="file" name="orignlFileNm" value="" accept="video/mp4|image/*" />
						</p>
					</td>
				</tr>
			</tbody>
		</table>
		<div class="div_tool_photo_manage">
			<a class="a_save_add_sharing_map btn_register" href="#" ></a>
			<a class="a_close_add_sharing_map btn_close" href="#" ></a>
		</div>
	</form>
</div>
