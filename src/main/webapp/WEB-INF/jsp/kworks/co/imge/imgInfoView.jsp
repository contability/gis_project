<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/kworks/mps/inc/taglib_common.jsp"%>

<script type="text/javascript" src="<c:url value='/js/kworks/jquery.bxslider/jquery.bxslider.js' />"></script>
<link type="text/css" rel="stylesheet" href="<c:url value='/js/kworks/jquery.bxslider/jquery.bxslider.css' />" />
<script>
	$(function(){
		$('.picSlider ul').bxSlider({
			slideWidth:'300'
		});
	});
</script>
<div class="map_layerWrap w_300 m_auto">
	<div class="picSlider">
		<ul>
			<center>
			<li><img src="<c:url value='/co/imge/thumbnail.do?fileName=${fileNm}&nocache=${nocache}' />" alt="${info.ttlDes}" /></li>
			</center>
		</ul>
	</div>
	
	<table summary="사진정보 관련 테이블" class="cmmTable v2 ma_b_30">
		<caption>사진정보 테이블</caption>
		<colgroup>
			<col width="25%" />
			<col width="" />
		</colgroup>
		<tbody>
			<tr>
				<th scope="row">등록일자</th>
				<td>${info.lodYmd}</td>
			</tr>
			<tr>
				<th scope="row">분류</th>
				<td>${info.dspCde}</td>
			</tr>
			<tr>
				<th scope="row">제목</th>
				<td>${info.ttlDes}</td>
			</tr>
			<tr>
				<th scope="row">설명</th>
				<td>${info.imgExp}</td>
			</tr>
		</tbody>
	</table>
	
	<p class="btnRight">
		<a href="#" onclick="kwImgSlide.down('${imgFile}');";><img src="<c:url value='/images/kworks/map/skin2/btn_down.gif' />" alt="내려받기" /></a>
		<c:if test="${imgEdit eq 'Y' }" >
		<a href="#" onclick="kwImgSlide.edit('${ftrIdn}','${ftrCde}','${dspCde}',${shtIdn});"><img src="<c:url value='/images/kworks/map/skin2/btn_edit2.png' />" alt="편집" /></a>
		</c:if>
		<a href="#" onclick="kwImgSlide.imgInfoClose();"><img src="<c:url value='/images/kworks/map/skin2/btn_close.gif' />" alt="닫기" /></a>
	</p>
	
</div>

