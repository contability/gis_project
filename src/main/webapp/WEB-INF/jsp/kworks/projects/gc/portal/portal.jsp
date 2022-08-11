<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 			uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form"		uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="ui" 			uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" 		uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" 		uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" 			uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="validator" 	uri="http://www.springmodules.org/tags/commons-validator" %>
<%@ taglib prefix="tiles" 		uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="fmt" 		uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html lang="ko">

<head>
	<meta charset="utf-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=11" />
	<meta http-equiv="X-UA-Compatible" content="IE=10" />
	<meta http-equiv="X-UA-Compatible" content="IE=9" />
	<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=yes">
	
	<!-- project -->
	<c:set var="prjCode"><spring:message code='Globals.Prj' /></c:set>
	<c:set var="envType"><spring:message code='Globals.EnvType' /></c:set>
	<c:set var="cityNm"><spring:message code='Com.City' /></c:set>
	<c:set var="isMySystem"><spring:message code='Extensions.MySystem.UseAt' /></c:set>
	
	<!-- favicon -->
	<link rel="shortcut icon" href="<c:url value='/favicon.ico'/>" />
	<link rel="icon" type="image/png" sizes="32x32" href="<c:url value='/images/kworks/favicon-32x32.png'/>">
	<link rel="icon" type="image/png" sizes="16x16" href="<c:url value='/images/kworks/favicon-16x16.png'/>">
	
	<!-- import css -->
	<!-- kworks common -->
	<link type="text/css" rel="stylesheet" href="<c:url value='/css/kworks/common/reset.css' />" />
	<link type="text/css" rel="stylesheet" href="<c:url value='/css/kworks/common/common.css' />" />
	<link type="text/css" rel="stylesheet" href="<c:url value='/css/${prjCode}/common/layout_main.css' />" />
	<link type="text/css" rel="stylesheet" href="<c:url value='/css/kworks/common/button.css' />" />
	<link type="text/css" rel="stylesheet" href="<c:url value='/css/kworks/manage/manage.css' />" />
	<link type="text/css" rel="stylesheet" href="<c:url value='/webjars/jquery-ui/1.12.0/jquery-ui.min.css' />" />
	
	<!-- bxslider -->
	<link type="text/css" rel="stylesheet" href="<c:url value='/lib/jquery/bxslider/jquery.bxslider.css' />" />
	
	<!-- import javascript -->
	<script type="text/javascript">
		var CONTEXT_PATH = "${pageContext.request.contextPath}";
		var PRJ_CODE = "${prjCode}";
	</script>
	
	<!-- import message -->
	<script type="text/javascript">
		var EXTENSIONS = {
				IS_SURVEY : <spring:message code='Extensions.Survey.UseAt' text='false'/>
		};
	</script>

	<!-- jquery -->
	<script type="text/javascript" src="<c:url value='/webjars/jquery/2.1.4/jquery.min.js' />"></script>
	<script type="text/javascript" src="<c:url value='/webjars/jquery-ui/1.12.0/jquery-ui.min.js' />"></script>
	
	<!-- bxslider -->
	<script src="<c:url value='/lib/jquery/bxslider/jquery.bxslider.min.js' />"></script>
	
	<!-- kworks -->
	<script src="<c:url value='/js/kworks/portal/portal.js' />"></script>
	<script type="text/javascript" src="<c:url value='/js/${prjCode}/props/portal.js' />"></script>	

	<script type="text/javascript" src="<c:url value='/js/kworks/cmmn/utils.js' />"></script>
	<script type="text/javascript" src="<c:url value='/js/kworks/bbs/notice.js' />"></script>
	<script type="text/javascript" src="<c:url value='/js/kworks/bbs/recsroom.js' />"></script>
	
	<!-- 접속통계 -->
	<script type="text/javascript" src="<c:url value='/js/kworks/main/connectYear.js' />"></script>
	<link type="text/css" rel="stylesheet" href="<c:url value='/css/kworks/main/connectYear.css' />" />
	
	<title>${cityNm} 웹표준 공간정보통합관리 체계</title>
</head>
<body>
	<div id="wrap">
	
		<!-- 상단 영역 -->
		<div id="header">
			<div class="w_1200 h_60 m_auto">
				<div class="logo ma_t_9 ma_l_10">
					<a href="<c:url value='/' />" ><img alt="<spring:message code='Com.City' /> 웹표준 공간정보통합관리 체계" src="<c:url value='/images/${prjCode}/common/logo.png' />" /></a>
				</div>
				<div class="gnb ma_t_18 ma_r_10">
					<div class="gnb_text color_3 ma_t_6 f_s_14"><span><strong><c:out value='${userName}' /></strong> 님 환영합니다.</span></div>
					<div class="gnb_line color_3 ma_t_4 ma_l_6 ma_r_6 f_s_18"><span><strong>|</strong></span></div>
					<div class="gnb_text color_3"><a href="<c:url value='/j_spring_security_logout' />"><img src="<c:url value='/images/kworks/main/btnLogout.png' />" /></a></div>
				</div>
				<div class="gnb ma_t_20 ma_r_10  f_s_14 connectBtn" id="connectBtn">
					<div class="gnb_text color_3 ma_t_3 ma_r_5"><span>접속통계 : </span></div>
					<div class="gnb_text color_3 ma_r_4"><span><img src="<c:url value='/images/kworks/main/connectDay.png' />"></span></div>
					<div class="gnb_text color_3 ma_t_3 ma_r_6"><span><c:out value='${result.t_23}'/> </span></div>
						<c:forEach items="${monthResult}" var="list">
							<c:set var="allCount" value="${list.count}" />
						</c:forEach>
					<div class="gnb_text color_3 ma_r_3"><span><img src="<c:url value='/images/kworks/main/connectMonth.png' />"></span></div>
					<div class="gnb_text color_3 ma_t_3 ma_r_5"><span><fmt:formatNumber value="${allCount}" pattern="#,###"/></span></div>
					<div class="gnb_line color_3 ma_t_2 ma_l_6 f_s_18"><span><strong>|</strong></span></div>
				</div>
			</div>
		</div>
		
		<!-- 메인 영역 시작 -->
		<div id="container">
		
			<!-- 메인 이미지 영역 -->
			<div class="slider bd_t_blue4 bd_b_gray3">
				<div class="slideMap m_auto">
					<ul class="kworksMainImg">
						<li>
							<img src="<c:url value='/images/${prjCode}/portal/main_1.png' />" alt="과천 이미지 1" />
						</li>
						<li>
							<img src="<c:url value='/images/${prjCode}/portal/main_2.png' />" alt="과천 이미지 2" />
						</li>
						<li>
							<img src="<c:url value='/images/${prjCode}/portal/main_3.png' />" alt="과천 이미지 3" />
						</li>
					</ul>
				</div>
			</div>
			
			<!-- 시스템 목록 -->
			<div class="w100 bg_f2">
				<c:if test="${isMySystem eq true}" >
					<div class="survice1 m_auto">
						<div class="surviTit pa_l_110 pa_r_10 pa_t_20"><img src="<c:url value='/images/kworks/main/center_tit.png' />" /></div>
						<div class="myMenu">
							<select id="mySysSelect" class="ma_t_25">
								<option value="" >==선택하세요==</option>
								<c:forEach items="${selfSysemList}" var="selfSysem">
									<option value="<c:out value='${selfSysem.sysId}' />" ><c:out value='${selfSysem.sysAlias}' /></option>
								</c:forEach>
							</select>
						</div>
						<div class="surviBtn ma_t_24 ma_l_5"><a href="#" class="btn_go"></a></div>
					</div>
				</c:if>
				<div class="survice2 t_a_c">
					<c:if test="${fn:length(sysAuthorList) > 5}">
						<div id="bx-prev" class="f_l ma_t_80 centerNum ma_r_20"><a href="#"><img src="<c:url value='/images/kworks/main/center_pre.png' />" /></a></div>
					</c:if>
					<div class="f_l w_1000">
					
						<ul id="portal_sys_slider">
							<c:forEach items="${sysList}" var="sys">
								<c:forEach items="${sysAuthorList}" var="sysAuthor">
									<c:if test="${sysAuthor.sysId eq sys.sysId}">
										<li>
											<c:if test="${sysAuthor.authorAt eq 'Y'}">
												<a href="<c:url value='/main.do?sysId=${sys.sysId}' />">
													<img class="mainSys cursor_point" src="<c:url value='/images/${prjCode}/portal/sys_${sys.sysNm}.png' />" />
												</a>
											</c:if>
											<c:if test="${sysAuthor.authorAt ne 'Y'}">
												<a href="#" class="a_sys_no_author">
													<img class="mainSys cursor_point" src="<c:url value='/images/${prjCode}/portal/sys_${sys.sysNm}.png' />" />
												</a>
											</c:if>
										</li>
									</c:if>
								</c:forEach>
							</c:forEach>
						</ul>
					</div>
					<c:if test="${fn:length(sysAuthorList) > 5}">
						<div id="bx-next" class="f_l ma_t_80 centerNum ma_l_20"><a href="#"><img src="<c:url value='/images/kworks/main/center_next.png' />" /></a></div>
					</c:if>
				</div>
			</div>
			
			<!-- 공지사항 -->
			<div class="w100 bg_blue2 h_200">
				<div class="mainBbsArea w_1200 t_a_c m_auto">
					<div class="mainNoticeArea pa_t_30 h_165 w_350 pa_l_65">
						<div class="ma_b_30">
							<span class="line bd_b_white w_350"><img class="noticeTit pa_b_8" src="<c:url value='/images/kworks/main/noticeTit.png' />" /> 
								<a class="noticeMore pa_t_5" href="<c:url value='/bbs/notice/list.do' />"><img src="<c:url value='/images/kworks/main/btnMore.png' />" /></a>
							</span>
						</div>
						<table class="noticeBox w100">
							<colgroup>
								<col width="*" />
								<col width="30%" />
							</colgroup>
							<c:forEach items="${noticeList}" var="notice">
								<tr>
									<td class="t_a_l pa_l_10">
										<a href="#" data-notice-no="${notice.noticeNo}" class="a_notice color_white f_s_12">
											<c:choose>
												<c:when test="${fn:length(notice.noticeSj) > 24}">
													<c:out value="${fn:substring(notice.noticeSj,0,17)}"/>....
												</c:when>
												<c:otherwise>
													<c:out value="${notice.noticeSj}"/>
												</c:otherwise> 
											</c:choose>
										</a>
									</td>
									<td class="t_a_r color_white f_s_12"><fmt:formatDate value="${notice.frstRgsde}" /></td>
								</tr>
							</c:forEach>
						</table>
					</div>
					<!-- 자료실 -->
					<div class="mainRecsroomArea pa_t_30 h_165 w_350 pa_l_65">
						<div class="ma_b_30">
							<span class="line bd_b_white w_350"><img class="recsroomTit pa_b_8" src="<c:url value='/images/kworks/main/recsroomTit.png' />" /> 
								<a class="recsroomMore pa_t_5" href="<c:url value='/bbs/recsroom/list.do' />"><img src="<c:url value='/images/kworks/main/btnMore.png' />" /></a>
							</span>
						</div>
						<table class="recsroomBox w100">
							<colgroup>
								<col width="*" />
								<col width="30%" />
							</colgroup>
							<c:forEach items="${recsroomList}" var="recsroom">
								<tr>
									<td class="t_a_l pa_l_10">
										<a href="#" data-recsroom-no="${recsroom.recsroomNo}" class="a_recsroom color_white f_s_12">
										<c:choose>
											<c:when test="${fn:length(recsroom.recsroomSj) > 24}">
												<c:out value="${fn:substring(recsroom.recsroomSj,0,17)}"/>....
											</c:when>
											<c:otherwise>
												<c:out value="${recsroom.recsroomSj}"/>
											</c:otherwise> 
										</c:choose>
										</a>
									</td>
									<td class="t_a_r color_white f_s_12"><fmt:formatDate value="${recsroom.frstRgsde}" /></td>
								</tr>
							</c:forEach>
						</table>
					</div>
					<div class="system pa_t_26 pa_l_65">
						<a href="<c:url value='/self/export/list.do' />"><img src="<c:url value='/images/kworks/main/system_01.png' />" /></a>
					</div>
					<c:if test="${userGrad eq 'ROLE_MNGR'}">
						<div class="system pa_t_26 pa_l_65">
							<a href="<c:url value='/manage/user/list.do' />"><img src="<c:url value='/images/kworks/main/system_02.png' />" /></a>
						</div>
					</c:if>
					<c:if test="${userGrad eq 'ROLE_DEPT_MNGR'}">
						<div class="system pa_t_26 pa_l_65">
							<a href="<c:url value='/deptManage/export/list.do' />"><img src="<c:url value='/images/kworks/main/system_06.png' />" /></a>
						</div>
					</c:if>
				</div>
			</div>
			
			<!-- 하단 영역 -->
			<div class="w100 h_60 pa_t_10">
				<img src="<c:url value='/images/${prjCode}/portal/footer.png' />" />
			</div>
		</div>
		<!-- 메인 영역 끝 -->
	</div>
	
	<!-- 공지사항 보기 -->
	<div id="div_notice_select" class="div_modal" title="공지사항 조회">
		<div>
			<div>
				<span class="title">제목</span>
				<span class="content noticeSj"></span>
			</div>
		   	<div>
		   		<span class="title">내용</span>
				<div class="content noticeCn"></div>
			</div>
		   	<div class="div_file_list">
		   		<span class="title">
		     		첨부파일
				</span>
		   		<div class="content">
		   			<ul class="fileList"></ul>
		   		</div>
		   	</div>
		</div>
	</div>
	
	<!-- 자료 보기 -->
	<div id="div_recsroom_select" class="div_modal" title="자료실 조회">
		<div>
	   		<div>
	   			<span class="title">제목</span>
	   			<span class="content recsroomSj"></span>
	   		</div>
	   		<div>
	   			<span class="title">내용</span>
	   			<div class="content recsroomCn"></div>
			</div>
	   		<div class="div_file_list">
		   		<span class="title">
		     		첨부파일
				</span>
		   		<div class="content">
		   			<ul class="fileList"></ul>
		   		</div>
		   	</div>
	    </div>
	</div>
	
	<div id="div_connect_select" class="div_modal" title="년도별 접속통계 조회">
		<div>
			<c:forEach items="${lastYearResult}" var="list">
					<c:set var="lastYearDay" value="${list.day}" />
					<c:set var="lastYearCount" value="${list.totalCount}" />			
	   		</c:forEach>
	   		<c:forEach items="${thisYearResult}" var="list">
					<c:set var="thisYearDay" value="${list.day}" />
					<c:set var="thisYearCount" value="${list.totalCount}" />
	   		</c:forEach>
	   		<div style="font-size:13px;">
	   			<span class="title">동일기간 전년누계</span>
	   			<span class="connect connectSj"><fmt:formatNumber value="${lastYearCount }" pattern="#,###,###"/>회</span>
	   		</div>
	   		<div style="font-size:13px;">
	   			<span class="title">금년누계</span>
	   			<span class="connect connectCn"><fmt:formatNumber value="${thisYearCount }" pattern="#,###,###"/>회</span>
			</div>
			<c:set var="connectGapCount" value="${thisYearCount - lastYearCount }" />
			<br/>
			<c:if test="${thisYearCount + 0 > lastYearCount + 0 }">
				<span class="connCount">전년도 대비 </span>
				<span class="connCount connRed"><fmt:formatNumber value="${connectGapCount }" pattern="#,###,###"/>회  증가</span>
				<span class="connCount">하였습니다.</span>
			</c:if>
			<c:if test="${thisYearCount + 0 == lastYearCount + 0 }">
				<span class="connCount">전년도와 </span><span class="connCount connGreen"> 동일</span><span class="connCount">합니다.</span>
			</c:if>
			<c:if test="${thisYearCount + 0 < lastYearCount + 0 }">
				<span class="connCount">전년도 대비 </span><span class="connCount connBlue"><fmt:formatNumber value="${connectGapCount * -1 }" pattern="#,###,###"/>회  감소</span><span class="connCount">하였습니다.</span>
			</c:if>
	    </div>
	</div>
	
	<c:if test="${prjCode eq 'ss' and envType eq 'oper' and userName ne '관리자'}">
		<script type="text/javascript" src="<c:url value='/lib/fsw/fsw.js' />"></script>
	</c:if>
	
</body>

