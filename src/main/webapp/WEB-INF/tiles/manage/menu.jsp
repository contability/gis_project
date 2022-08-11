<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/kworks/mps/inc/taglib_common.jsp"%>
<c:set var="prjCode"><spring:message code='Globals.Prj' /></c:set>
<c:set var="isContactRestore"><spring:message code='Contact.Restore.UseAt' text='false'/></c:set>
<c:set var="isEditHistory"><spring:message code='Extensions.EditHistory.UseAt' text='false'/></c:set>
<c:set var="isLandCenter"><spring:message code='Contact.LandCenter.UseAt' text='false'/></c:set>
<c:set var="isAerialPhoto"><spring:message code='Extensions.AerialPhoto.UseAt' text='false'/></c:set>
<c:set var="isOcpatRegstr"><spring:message code='Contact.OcpatRegstr.UseAt' text='false' /></c:set><!-- 점용대장 -->
<c:set var="isPolicyRegstr"><spring:message code='Contact.PolicyRegstr.UseAt' text='false' /></c:set><!-- 정책지도 -->
<c:set var="isMobileExport"><spring:message code='Extensions.MobileExport.UseAt' text='false' /></c:set><!-- 재난현장 모바일 -->

	
<script type="text/javascript" src="<c:url value='/js/kworks/manage/leftMenu.js' />"></script>
<div class="nav f_l bd_l_d6 bd_r_d6 bd_b_d6">
	<input type="hidden" id="menu_request_uri" value='${requestScope["javax.servlet.forward.request_uri"]}' />
	<div class="pa_b_10"><img src="<c:url value='/images/kworks/system/left_image.png' />" /></div>
	<ul id="ul_menu_list">
		<li class="pa_b_2"><a href="<c:url value='/manage/user/list.do'/>"><span class="leftMenu">사용자 관리</span></a></li>
		<li class="pa_b_2"><a href="<c:url value='/manage/dept/list.do'/>"><span class="leftMenu">부서 관리</span></a></li>
		<li class="pa_b_2"><a href="<c:url value='/manage/authorGroup/list.do'/>"><span class="leftMenu">권한그룹 관리</span></a></li>
		<li class="pa_b_2"><a href="<c:url value='/manage/userAuthorHist/list.do'/>"><span class="leftMenu">권한부여 이력관리</span></a></li>
		
		<c:if test="${isAerialPhoto eq false}" >
			<li class="pa_b_2"><a href="<c:url value='/manage/bcrnMap/list.do'/>"><span class="leftMenu">배경지도 관리</span></a></li>
		</c:if>
		
		<c:if test="${isAerialPhoto eq true}" >
			<li class="pa_b_2"><a href="<c:url value='/manage/bcrnMap/listAerialPhoto.do'/>"><span class="leftMenu">항공사진 관리</span></a></li>
		</c:if>
		
		<li class="pa_b_2"><a href="<c:url value='/manage/bassThemamap/list.do'/>"><span class="leftMenu">주제도 관리</span></a></li>
		<li class="pa_b_2"><a href="<c:url value='/manage/notice/list.do'/>"><span class="leftMenu">공지사항 관리</span></a></li>
		<li class="pa_b_2"><a href="<c:url value='/manage/qna/list.do'/>"><span class="leftMenu">Q&amp;A 관리</span></a></li>
		<li class="pa_b_2"><a href="<c:url value='/manage/recsroom/list.do'/>"><span class="leftMenu">자료실 관리</span></a></li>
		<li class="pa_b_2"><a href="<c:url value='/manage/domn/list.do'/>"><span class="leftMenu">도메인 관리</span></a></li>
		<li class="pa_b_2"><a href="<c:url value='/manage/edit/list.do'/>"><span class="leftMenu">편집여부 관리</span></a></li>
		<li class="pa_b_2"><a href="<c:url value='/manage/export/list.do?progrsSttus=CONSENT_WAITING'/>"><span class="leftMenu">내보내기 관리</span></a></li>
		<li class="pa_b_2"><a href="<c:url value='/manage/exportHist/list.do'/>"><span class="leftMenu">내보내기 이력관리</span></a></li>
		<li class="pa_b_2"><a href="<c:url value='/manage/conectStats/unitySys/list.do'/>"><span class="leftMenu">통합시스템 접속통계</span></a></li>
		<li class="pa_b_2"><a href="<c:url value='/manage/conectStats/indvdlzSys/listWeek.do'/>"><span class="leftMenu">개별시스템 접속통계</span></a></li>
		<c:if test="${isLandCenter eq true}" >
			<li class="pa_b_2"><a href="<c:url value='/manage/landCenter/edit/list.do'/>"><span class="leftMenu">토지공사대장 편집이력</span></a></li>
			<li class="pa_b_2"><a href="<c:url value='/manage/landCenter/down/list.do'/>"><span class="leftMenu">토지사용증명서 이력관리</span></a></li>
		</c:if>
		<li class="pa_b_2"><a href="<c:url value='/manage/opertLog/conectLog.do'/>"><span class="leftMenu">작업 로그</span></a></li>
		<li class="pa_b_2"><a href="<c:url value='/manage/log/errorLog.do'/>"><span class="leftMenu">에러 로그</span></a></li>
		<c:if test="${isEditHistory eq true}" >
			<li class="pa_b_2"><a href="<c:url value='/manage/editHist/list.do'/>"><span class="leftMenu">편집이력 조회</span></a></li>
		</c:if>
		<c:if test="${isOcpatRegstr eq true}" >
			<li class="pa_b_2"><a href="<c:url value='/manage/ocpatRegstr/edit/list.do'/>"><span class="leftMenu">점용대장 편집이력</span></a></li>
			<li class="pa_b_2"><a href="<c:url value='/manage/ocpatRegstr/download/list.do'/>"><span class="leftMenu">점용대장 자료 조회이력</span></a></li>
		</c:if>
		<c:if test="${isPolicyRegstr eq true}" >
			<li class="pa_b_2"><a href="<c:url value='/policy/edit/list.do'/>"><span class="leftMenu">정책 편집</span></a></li>
			<li class="pa_b_2"><a href="<c:url value='/policy/editRefrHi/list.do'/>"><span class="leftMenu">부속자료 이력</span></a></li>
			<li class="pa_b_2"><a href="<c:url value='/policy/download/list.do'/>"><span class="leftMenu">부속자료 다운로드</span></a></li>
			<li class="pa_b_2"><a href="<c:url value='/policy/repo/list.do'/>"><span class="leftMenu">보고서 편집</span></a></li>
		</c:if>
		<c:if test="${isContactRestore eq true}" >
			<li class="pa_b_2"><a href="<c:url value='/manage/restore/list.do'/>"><span class="leftMenu">연계 데이터 복원</span></a></li>
		</c:if>
		
		<!-- 재난현장 모바일 -->		
		<c:if test="${isMobileExport eq true}" >
			<li class="pa_b_2"><a href="<c:url value='/manage/mobileExport/history/list.do'/>"><span class="leftMenu">재난현장 모바일</span></a></li>
		</c:if>
		
		<c:if test="${prjCode eq 'gn' }">
			<li class="pa_b_2"><a href="<c:url value='/manage/userAcntMngRegstr/list.do'/>"><span class="leftMenu">사용자계정 관리대장</span></a></li>
			<li class="pa_b_2"><a href="<c:url value='/manage/spatialInfoData/list.do'/>"><span class="leftMenu">공간정보 자료제공 대장</span></a></li>
			<li class="pa_b_2"><a href="<c:url value='/manage/krasDataPvsnRegstr/list.do'/>"><span class="leftMenu">KRAS 전산자료 제공대장</span></a></li>
			<li class="pa_b_2"><a href="<c:url value='/manage/spatialInfoRegstr/list.do'/>"><span class="leftMenu">공간정보관리대장</span></a></li>
		</c:if>
	</ul>
</div>