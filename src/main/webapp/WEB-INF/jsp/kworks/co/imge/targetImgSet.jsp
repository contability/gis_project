<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/kworks/mps/inc/taglib_common.jsp"%>

<div class="map_layerWrap">
	<p class="blt_tit">사진정보 구분</p>
	<div class="map_imgInfo_cate">
		<ul>
			<c:set var="chkTrue" value="0" />
			<c:forEach items="${dspCdeList}" var="dsp" varStatus="st">
				<c:set var="chkTrue" value="0" />
				<c:forEach items="${chkList}" var="chkLi" varStatus="chkLiSt">
					<c:if test="${dsp.dspCde eq chkLi}">
						<c:set var="chkTrue" value="1" />
					</c:if>
				</c:forEach>
				
				<c:if test="${chkTrue eq '0' }">
				<li><input type="checkbox" value="${dsp.dspCde}"> ${dsp.dspCdeNm}</li>
				</c:if>
				
				<c:if test="${chkTrue eq '1' }">
				<li><input type="checkbox" value="${dsp.dspCde}" checked="checked"> ${dsp.dspCdeNm}</li>
				</c:if>
				
			</c:forEach>
		</ul>
	</div>
	<p class="btnRight">
		<a href="#" onclick="kwImgSlide.targetImgSetClose();"><img src="<c:url value='/images/kworks/map/skin2/btn_close.gif' />" alt="닫기" /></a>
	</p>
</div>