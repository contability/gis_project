<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 		     uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui"			 uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="fmt"			 uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form"		 uri="http://www.springframework.org/tags/form" %>

<form id="mofidyPhotoForm" name="modifyPhotoForm" method="post" enctype="multipart/form-data">
	<div id="modifyPhoto" class="window_container">
		<div class="window_article">
			<input type="hidden" name="imageNo" value="${image.imageNo}" />
			<input type="hidden" name="ftrCde" value="${image.ftrCde}" />
			<input type="hidden" name="ftrIdn" value="${image.ftrIdn}" />
			<input type="hidden" name="imageSj" value="${image.imageSj}" />
			<input type="hidden" name="imageTy" value="${image.imageTy}" />
			<input type="hidden" name="fileNo" value="${image.imageFileNo}">
			<input type="hidden" name="thumbNo" value="${image.thumbFileNo}">
			
			<table id="tablePhoto" class="table_text">
				<tbody>
					<tr>
						<th>관리번호</th>
						<td>${image.ftrIdn}</td>
					</tr>
					<tr>
						<th>제목</th>
						<td id="imageSj">${image.imageSj}</td>
					</tr>
					<tr>
						<th>사진</th>
						<td>
							<div class="picSlider">
								<ul>
									<center>
										<li class="li_image">
											<a class="venobox" href="#" target="_blank">
												<img id="img_view_photo_manage_thumbnail" src="" />
											</a>
										</li>
									</center>
								</ul>
							</div>			
						</td>
					</tr>
					<tr>
						<th scope="row">교체할 사진</th>
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
			<div class="button_flat_l pa_l_10 pa_t_3">
				<a class="a_down_view_photo btn_down" href="#" ></a>
			</div>		
			<div class="button_flat">
				<button id="btn_update" class="button_flat_normal btn_blue">저장</button>
				<button id="btn_delete" class="button_flat_normal btn_blue">삭제</button>
				<button id="btn_close" class="button_flat_normal btn_black">닫기</button>
			</div>
		</div>
	</div>
</form>