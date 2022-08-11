<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 	 uri="http://java.sun.com/jsp/jstl/core" %>

<!-- import javascript -->
<div class="map_layerWrap w_300 m_auto" style="overflow:hiddn;">
	<input type="hidden" id="hid_view_sharing_map_ftr_idn" value="">
	<input type="hidden" id="hid_view_sharing_map_ftr_cde" value="">
	<div class="picSlider">
		<ul>
			<center>
				<li class="li_image">
					<a class="venobox" href="#" target="_blank">
						<img id="img_view_photo_manage_thumbnail" src="" />
					</a>
				</li>
				<li class="li_video">
					<video id="video_view" width="270" height="210" autoplay="autoplay" controls="controls">
						<source type="video/webm" />
					</video>
				</li>
			</center>
		</ul>
	</div>
	<table class="cmmTable v2" summary="공유지도 테이블">
		<caption>공유지도 테이블</caption>
		<colgroup>
			<col width="25%" />
			<col width="*" />
		</colgroup>
		<tbody id="tbl_sharing_map_view">
			<tr>
				<th scope="row">등록일자</th>
				<td>
					<span id="spn_view_sharing_map_frst_rgsde"></span>
				</td>
			</tr>
			<tr>
				<th scope="row">제목</th>
				<td>
					<span id="spn_view_sharing_map_poi_sj"></span>
				</td>
			</tr>
			<tr>
				<th scope="row">내용</th>
				<td>
					<div id="spn_view_sharing_map_poi_cn" style="white-space:pre-line;"></div>
				</td>
			</tr>
		</tbody>
	</table>
	<div style="display:inline-block; height:38px;">
		<div class="div_tool_photo_manage">
			<a class="a_down_view_photo btn_down" href="#" ></a>
			<a class="a_modify_view_sharing_map btn_edit" href="#" ></a>
			<a class="a_remove_view_sharing_map btn_del" href="#" ></a>
			<a class="a_close_view_sharing_map btn_close" href="#" ></a>
		</div>
	</div>
</div>