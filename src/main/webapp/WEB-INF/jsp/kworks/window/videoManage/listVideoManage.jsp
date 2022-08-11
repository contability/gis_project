<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 			uri="http://java.sun.com/jsp/jstl/core" %>

<div id="div_window_video_manage" class="layerCont">
	<input type="hidden" id="hid_list_video_manage_up_no" value="" />
	<input type="hidden" id="hid_list_video_manage_down_no" value="" />
	<input type="hidden" id="hid_list_video_manage_ftr_idn" value="" />
	<table class="cmmTable v2 ma_b_30" summary="영상관리 정보 관련 테이블">
		<caption>영상관리 정보 테이블</caption>
		<colgroup>
			<col width="22%" />
			<col width="28%" />
			<col width="25%" />
			<col width="25%" />
		</colgroup>
		<tbody>
			<tr>
				<th scope="row">노선명</th>
				<td colspan="3">
					<span id="spn_list_video_manage_rot_nam"></span>
				</td>
			</tr>
			<tr>
				<th>노선번호</th>
				<td>
					<span id="spn_list_video_manage_rot_idn"></span>
				</td>
				<th>구간</th>
				<td>
					<span id="spn_list_video_manage_sec_idn"></span>
				</td>
			</tr>
			<tr>
				<th scope="row">상행 영상</th>
				<td colspan="3">
					<span id="spn_list_video_manage_up_file"></span>
					<div class="picSlider">
						<ul>
							<center>
								<li class="li_video">
									<video id="video_up_view" width="270" height="210" autoplay="autoplay" controls="controls">
										<source type="video/webm" />
									</video>
								</li>
							</center>
						</ul>
					</div>
				</td>
			</tr>
			<tr>
				<th scope="row">하행 영상</th>
				<td colspan="3">
					<span id="spn_list_video_manage_down_file"></span>
					<div class="picSlider">
						<ul>
							<center>
								<li class="li_video">
									<video id="video_down_view" width="270" height="210" autoplay="autoplay" controls="controls">
										<source type="video/webm" />
									</video>
								</li>
							</center>
						</ul>
					</div>
				</td>
			</tr>
		</tbody>
	</table>
	<div class="div_tool_video_manage">
		<!-- 내려받기, 편집, 닫기 버튼 -->
		<a class="a_add_list_video btn_register" href="#" ></a>
		<a class="a_close_list_video btn_close" href="#" ></a>
	</div>
</div>
