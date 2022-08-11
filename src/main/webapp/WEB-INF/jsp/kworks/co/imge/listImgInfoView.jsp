<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/kworks/mps/inc/taglib_common.jsp"%>

<script type="text/javascript" src="<c:url value='/js/kworks/jquery.bxslider/jquery.bxslider.js' />"></script>
<link type="text/css" rel="stylesheet" href="<c:url value='/js/kworks/jquery.bxslider/jquery.bxslider.css' />" />
<style>
 .bx-wrapper .bx-pager a.active table{
    display: in-line;
  }
  
  .bx-wrapper .bx-pager a table{
    display: none;
  }
</style>
<input id="listImgInfoFtrIdn" type="hidden" value="" />
<input id="listImgInfoFtrCde" type="hidden" value="" />
<input id="listImgInfoShtIdn" type="hidden" value="" />
<input id="listImgInfoDspCde" type="hidden" value="" />
<input id="listImgInfoImgFile" type="hidden" value="" />

<div class="map_layerWrap w_300 m_auto">
	<div class="picSlider">
		<center>
		<ul>
			<c:forEach items="${info }" var="img" varStatus="cnt">
			<li><img src="<c:url value='/co/imge/thumbnail.do?fileName=${img.tflNam}&nocache=${nocache}${cnt.index}' />" alt="${img.ttlDes}" /></li>
			</c:forEach>
		</ul>
		</center>
	</div>
	<%-- <p class="map_imgInfo_pic">
		<img src="<c:url value='/co/imge/thumbnail.do?fileName=${fileNm}&nocache=${nocache}' />" alt="${info.ttlDes}" />
	</p> --%>
	
	<c:forEach items="${info}" var="att" varStatus="attCnt">
		<table summary="사진정보 관련 테이블" class="cmmTable v2 ma_b_30 picSliderTbl display_n" id="picSliderTbl${attCnt.index}" ftrCde="${att.ftrCde}" ftrIdn="${att.ftrIdn}" shtIdn="${att.shtIdn}" dspCde="${att.dspCde}" iflNam="${att.iflNam}">
			<caption>사진정보 테이블</caption>
			<colgroup>
				<col width="25%" />
				<col width="" />
			</colgroup>
			
			<tbody>
				<tr>
					<th scope="row">등록일자</th>
					<td>${att.lodYmd}</td>
				</tr>
				<tr>
					<th scope="row">분류</th>
					<td>${att.dspCde}</td>
				</tr>
				<tr>
					<th scope="row">제목</th>
					<td>${att.ttlDes}</td>
				</tr>
				<tr>
					<th scope="row">설명</th>
					<td>${att.imgExp}</td>
				</tr>
			</tbody>
		</table>
	</c:forEach>	
	</div>
	<p class="btnRight">
		<a href="#" onclick="fn_listImgInfoImgDown();"><img src="<c:url value='/images/kworks/map/skin2/btn_down.gif' />" alt="내려받기" /></a>
		<c:if test="${imgEdit eq 'Y' }" >
		<a href="#" onclick="fn_listImgInfoImgEdit();"><img src="<c:url value='/images/kworks/map/skin2/btn_edit2.png' />" alt="편집" /></a>
		</c:if>
		<a href="#" onclick="kwImgSlide.imgInfoClose();"><img src="<c:url value='/images/kworks/map/skin2/btn_close.gif' />" alt="닫기" /></a>
	</p>
	
</div>
<script>
	
	$(function(){
		$('.picSlider ul').bxSlider({
			slideWidth:'300'
			,

			onSlideAfter : function(node, oldIndex, newIndex){
				$("#listImgInfoFtrIdn").val($("#picSliderTbl"+newIndex).attr("ftrIdn"));
				$("#listImgInfoFtrCde").val($("#picSliderTbl"+newIndex).attr("ftrCde"));
				$("#listImgInfoShtIdn").val($("#picSliderTbl"+newIndex).attr("shtIdn"));
				$("#listImgInfoDspCde").val($("#picSliderTbl"+newIndex).attr("dspCde"));
				$("#listImgInfoImgFile").val($("#picSliderTbl"+newIndex).attr("iflNam"));
				
				$(".picSliderTbl").hide();
				$("#picSliderTbl"+newIndex).show();
			}
		});
		
		$("#picSliderTbl0").show();
	});
	
	function fn_listImgInfoImgDown(){
		var listImgInfoImgFile = $("#listImgInfoImgFile").val();
		kwImgSlide.down(listImgInfoImgFile);
	}
	
	function fn_listImgInfoImgEdit(){
		var listImgInfoFtrIdn = $("#listImgInfoFtrIdn").val();
		var listImgInfoFtrCde = $("#listImgInfoFtrCde").val();
		var listImgInfoShtIdn = $("#listImgInfoShtIdn").val();
		var listImgInfoDspCde = $("#listImgInfoDspCde").val();
		
		kwImgSlide.edit(listImgInfoFtrIdn,listImgInfoFtrCde,listImgInfoDspCde,listImgInfoShtIdn);
	}
</script>
