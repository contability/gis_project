<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 			uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form"		uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" 		uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" 		uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" 			uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="validator" 	uri="http://www.springmodules.org/tags/commons-validator" %>

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
	<c:set var="serverType"><spring:message code='Map.ServerType' /></c:set>
	<c:set var="isStatsMap"><spring:message code='Extensions.StatsMap.UseAt' /></c:set>
	<c:set var="isUgrwtr"><spring:message code='Extensions.ugrwtr.UseAt' /></c:set>
	<c:set var="isCntrwkRegstr"><spring:message code='Extensions.cntrwkRegstr.UseAt' /></c:set>
	<c:set var="isCtrlpnt"><spring:message code='Extensions.ctrlpnt.UseAt' /></c:set>
	<c:set var="isLossSttemnt"><spring:message code='Extensions.lossSttemnt.UseAt' /></c:set>
	<c:set var="isCivilAppeal"><spring:message code='Extensions.civilAppeal.UseAt' /></c:set>
	<c:set var="isMesrSgnal"><spring:message code='Extensions.mesrSgnal.UseAt' /></c:set>
	<c:set var="isRiverSidePoint"><spring:message code='Extensions.riverSidePoint.UseAt' /></c:set>
	<c:set var="isAerialPhoto"><spring:message code='Extensions.AerialPhoto.UseAt' /></c:set>
	<c:set var="isSharingMap"><spring:message code='Extensions.SharingMap.UseAt' /></c:set>
	<c:set var="isQuickSearchDetail"><spring:message code='Extensions.QuickSearchDetail.UseAt' /></c:set>
	<c:set var="isTopographicMap"><spring:message code='Extensions.TopographicMap.UseAt' text='false' /></c:set> <!-- 지형지도 활용 -->
	<c:set var="isCadastralMap"><spring:message code='Extensions.CadastralMap.UseAt' text='false' /></c:set> <!-- 지적원도 & 드론영상 활용 -->
	<c:set var="isLgstrStats"><spring:message code='Extensions.LandRgisterStatistics.UseAt' text='false' /></c:set> <!-- 지적통계 활용 -->
	<c:set var="isCityPlanRoad"><spring:message code='Extensions.CityPlanRoad.UseAt' text='false' /></c:set> <!-- 동해시 도시계획도로 활용 -->
	<c:set var="isLgstrBiz"><spring:message code='Extensions.LgstrBiz.UseAt' text='false'/></c:set> <!-- 순창군 지적업무 활용 -->
	
	<!-- favicon -->
	<link rel="shortcut icon" href="<c:url value='/favicon.ico'/>" />
	<link rel="icon" type="image/png" sizes="32x32" href="<c:url value='/images/kworks/favicon-32x32.png'/>">
	<link rel="icon" type="image/png" sizes="16x16" href="<c:url value='/images/kworks/favicon-16x16.png'/>">
	
	<!-- import css -->
	<!-- kworks common -->
	<link type="text/css" rel="stylesheet" href="<c:url value='/css/kworks/common/reset.css' />" />
	<link type="text/css" rel="stylesheet" href="<c:url value='/css/kworks/common/common.css' />" />
	<link type="text/css" rel="stylesheet" href="<c:url value='/css/kworks/common/layout_map.css' />" />
	<link type="text/css" rel="stylesheet" href="<c:url value='/css/kworks/common/button.css' />" />
	
	<link type="text/css" rel="stylesheet" href="<c:url value='/css/kworks/cmmn/common.css' />" />
	<link type="text/css" rel="stylesheet" href="<c:url value='/css/kworks/main/main.css' />" />	
	<link type="text/css" rel="stylesheet" href="<c:url value='/css/kworks/map/stateMap/stateMap.css' />" />

	<c:if test="${ prjCode eq 'gn' or prjCode eq 'gds' }">
		<link type="text/css" rel="stylesheet" href="<c:url value='/css/gn/common/cityPark_stats.css' />" />
		<link type="text/css" rel="stylesheet" href="<c:url value='/css/kworks/main/roadVideo.css' />" />	
		<link type="text/css" rel="stylesheet" href="<c:url value='/css/gn/common/prtsArea.css'/>" />
	</c:if>
	
	<c:if test="${ prjCode eq 'gs'}">
		<link type="text/css" rel="stylesheet" href="<c:url value='/css/gs/common/explantprint.css' />" />
		<link type="text/css" rel="stylesheet" href="<c:url value='/css/gs/common/printcontainer.css' />" />
		<link type="text/css" rel="stylesheet" href="<c:url value='/css/gs/common/reantprint.css' />" />
	</c:if>
	
	<c:if test="${prjCode eq 'sunchang' }">
		<link type="text/css" rel="stylesheet" href='<c:url value="/css/sunchang/iprvar/iprvar.css"/>'>
	</c:if>
	
	<!-- easyui -->
	<link rel="stylesheet" type="text/css" href="<c:url value='/lib/jquery/easyui/themes/icon.css' />" />
	<link rel="stylesheet" type="text/css" href="<c:url value='/lib/jquery/easyui/themes/bootstrap/easyui.css' />" />
	
	<!-- 사진 -->
	<link rel="stylesheet" href="<c:url value='/lib/venobox/venobox.css' />" type="text/css">
	
	<!-- colorpicker -->
	<link rel="stylesheet" type="text/css" href="<c:url value='/lib/colorpicker/css/layout.css' />" />
	<link rel="stylesheet" type="text/css" href="<c:url value='/lib/colorpicker/css/colorpicker.css' />" />
	
	<!-- ol3 -->
	<link rel="stylesheet" href="<c:url value='/webjars/openlayers/3.13.0/ol.css' />" type="text/css">
	
	<!-- kworks -->
	<link rel="stylesheet" href="<c:url value='/lib/kworks/css/kworks.css' />" type="text/css">
	
	<!-- window -->
	<link type="text/css" rel="stylesheet" href="<c:url value='/css/kworks/window/window.css' />" />	
	
	<c:if test="${ prjCode eq 'gds' }">
	<!-- 시연 드론  -->
		<link rel="stylesheet" href="<c:url value='/css/gds/dron/dron.css' />" type="text/css">
	</c:if>
	
	<!-- import javascript -->
	<script type="text/javascript">
		// 프로젝트 코드
		var PROJECT_CODE = "<spring:message code='Globals.Prj' />";
		var ENV_TYPE = "<spring:message code='Globals.EnvType' />";
		// 컨텍스트 패스
		var CONTEXT_PATH = "${pageContext.request.contextPath}";
		var FULL_PATH  = '${pageContext.request.scheme}' + '://' + '${pageContext.request.serverName}' + ':' + '${pageContext.request.serverPort}' + CONTEXT_PATH;
		var COM = {
			CITY_NAME : '<spring:message code='Com.CityName' />',
			DEFAULT_CASE : '<spring:message code='Com.DefaultCase' />'
		};

		// Lks : 상월/하월 편집
		var UPWID_EDIT = {
			UseAt : '<spring:message code='Extensions.EditUpwid.UseAt' />',
			Layers : '<spring:message code='Extensions.EditUpwid.Layers' />'
		};
		
		// 지도
		var MAP = {
			// 지도 서버 타입
			SERVER_TYPE : "<spring:message code='Map.ServerType' />",
			// 선행자
			PREFIX : "<spring:message code='Map.Prefix' />",
			// 지도 좌표계
			PROJECTION : "<spring:message code='Map.Projection' />",
			// 공간정보 속성 명
			GEOMETRY_ATTR_NAME : "<spring:message code='Map.GeometryName' />".toLowerCase(),
			// TMS 경로
			TMS_URL : "<spring:message code='Map.Url.Tms' />",
			// 지도 심볼 경로
			SYMBOL_URL : "<spring:message code='Map.Url.Symbol' />",
			// 초기 지도 영역
			INIT_EXTENT : "<spring:message code='Map.InitExtent' />"
		};
		
		// 인덱스 지도 
		var INDEX_MAP = {
			// 레이어
			LAYER : "<spring:message code='Map.Index.Layer' />",
			// 해상도
			MAX_RESOLUTION : <spring:message code='Map.Index.MaxResolution' />,
			// 중심 X
			CENTER_X : <spring:message code='Map.Index.Center.X' />,
			// 중심 Y
			CENTER_Y : <spring:message code='Map.Index.Center.Y' />
		};
		
		// 시스템 아이디
		var SYS_ID = <c:out value='${sys.sysId}' />;
		
		// 주제도 아이디
		var THEMAMAP_ID = <c:out value='${sys.themamapId}' />;

		
		var CONTACT = {
			KRAS_IS_WINDOWPOPUP : "<spring:message code='Contact.Kras.isWindowPopup' />",
			EAIS_USE_AT : "<spring:message code='Contact.Eais.UseAt' />",
			SEAOLL_USE_AT : "<spring:message code='Contact.Seaoll.UseAt' />"
		};
		
		var EXTENSIONS = {
			AERIAL_PHOTO : <spring:message code='Extensions.AerialPhoto.UseAt' />,
			QUICK_SEARCH_DETAIL : <spring:message code='Extensions.QuickSearchDetail.UseAt' />
		};
		
		// 지형지도 활용		
		var IS_TOPOGRAPHICMAP = "<spring:message code='Extensions.TopographicMap.UseAt' text='false'/>";
		var TOPOGRAPHIC_ICON_URL = "<spring:message code='Map.Url.DEM' text='/'/>";
		
		// 도시계획도로
		var IS_CITY_PLAN_ROAD = "<spring:message code='Extensions.CityPlanRoad.UseAt' text='false'/>";
		var CITY_PLAN_ROAD_SPATIAL = "<spring:message code='Extension.CityPlanRoad.Spatial' text=''/>";
		var CITY_PLAN_ROAD_SPATIAL_FIELD = "<spring:message code='Extension.CityPlanRoad.Spatial.Field' text=''/>";
		
		// 지적원도 활용		
		var IS_CADASTRALMAP = "<spring:message code='Extensions.CadastralMap.UseAt' text='false'/>";
		var CADASTRALMAP_SCALE = "<spring:message code='Extensions.CadastralMap.Scale' text='5000'/>";
		var CADASTRALMAP_OPACITY = "<spring:message code='Extensions.CadastralMap.Opacity' text='0'/>";
		var CADASTRALMAP_DATAID = "<spring:message code='Extensions.CadastralMap.DataId' text=''/>";
		
		// 지적통계 활용
		var IS_LANDREGISTERSTATISTICS = "<spring:message code='Extensions.LandRgisterStatistics.UseAt' text='false'/>";
		
		// 지적업무 활용
		var IS_LGSTRBIZ = "<spring:message code='Extensions.LgstrBiz.UseAt' text='false'/>";
	</script>
	
	<!-- jquery & jeasy-ui -->
	<script src="<c:url value='/webjars/jquery/2.1.4/jquery.min.js' />" type="text/javascript"></script>
	<script src="<c:url value='/lib/jquery/cookie/jquery.cookie.js' />" type="text/javascript"></script>
	<script src="<c:url value='/lib/jquery/form/jquery.form.min.js' />" type="text/javascript"></script>
	<script src="<c:url value='/lib/jquery/easyui/jquery.easyui.min.js' />" type="text/javascript"></script>
	
	<!-- 사진 -->
	<script src="<c:url value='/lib/venobox/venobox.min.js' />" type="text/javascript"></script>
	
	<!-- kworks common -->
	<script src="<c:url value='/js/kworks/cmmn/utils.js' />" type="text/javascript"></script>
	
	<!-- colorpicker -->
	<script src="<c:url value='/lib/colorpicker/js/colorpicker.js' />" type="text/javascript"></script>
	
	<!-- kworks image -->
	<script src="<c:url value='/js/kworks/cmmn/image.js' />" type="text/javascript"></script>
	
	<!-- svg & canvas -->
	<script src="<c:url value='/webjars/d3js/3.5.12/d3.min.js' />" type="text/javascript"></script>
	<script src="<c:url value='/webjars/canvg/1.3.0/dist/canvg.min.js' />" type="text/javascript"></script>
	
	<!-- bxslider -->
	<link type="text/css" rel="stylesheet" href="<c:url value='/lib/jquery/bxslider/jquery.bxslider.css' />" />
	<script type="text/javascript" src="<c:url value='/lib/jquery/bxslider/jquery.bxslider.js' />"></script>
	
	<!-- gis -->
	<script src="<c:url value='/webjars/proj4js/2.2.1/proj4.js' />" type="text/javascript"></script>
	<script src="<c:url value='/webjars/jsts/0.14.0/javascript.util.js' />" type="text/javascript"></script>
	<script src="<c:url value='/webjars/jsts/0.14.0/jsts.js' />" type="text/javascript"></script>
	<%-- <script src="<c:url value='/webjars/jsts/1.2.1/jsts.min.js' />" type="text/javascript"></script> --%>
	
	<script src="<c:url value='/webjars/openlayers/3.13.0/ol-debug.js' />" type="text/javascript"></script>
	<script src="<c:url value='/lib/kworks/kworks-debug.js' />" type="text/javascript"></script>
	
	<c:if test="${envType eq 'dev'}">
		<!-- daum api -->
		<script src="http://dapi.kakao.com/v2/maps/sdk.js?appkey=<spring:message code='Contact.Daum.Key' />" type="text/javascript"></script>
	</c:if>
	<c:if test="${envType eq 'oper' and userName ne '관리자' }">
		<!-- daum api-> kakao daum api -->
		<script src="http://dapi.kakao.com/v2/maps/sdk.js?appkey=<spring:message code='Contact.Daum.Key' />" type="text/javascript"></script>
	</c:if>
	
	<!-- kworks -->
	<script src="<c:url value='/js/${prjCode}/props/common.js' />" type="text/javascript"></script>	
	<script src="<c:url value='/js/${prjCode}/props/tms.js' />" type="text/javascript"></script>
	<script src="<c:url value='/js/kworks/server/${serverType}.js' />" type="text/javascript"></script>
	
	<script src="<c:url value='/js/kworks/gis/backgroundMap.js' />" type="text/javascript"></script>
	<script src="<c:url value='/js/kworks/gis/highlight.js' />" type="text/javascript"></script>
	<script src="<c:url value='/js/kworks/gis/hghnkOutput.js' />" type="text/javascript"></script>
	<script src="<c:url value='/js/kworks/gis/legend.js' />" type="text/javascript"></script>
	<script src="<c:url value='/js/kworks/gis/sld.js' />" type="text/javascript"></script>
	<script src="<c:url value='/js/kworks/gis/select.js' />" type="text/javascript"></script>
	<script src="<c:url value='/js/kworks/gis/crossSectionalView.js' />" type="text/javascript"></script>
	
	<script src="<c:url value='/js/kworks/rest/coordinate.js' />" type="text/javascript"></script>
	<script src="<c:url value='/js/kworks/rest/baseMapAuthor.js' />" type="text/javascript"></script>
	<script src="<c:url value='/js/kworks/rest/data.js' />" type="text/javascript"></script>
	<script src="<c:url value='/js/kworks/rest/userAuthor.js' />" type="text/javascript"></script>
	<script src="<c:url value='/js/kworks/rest/dataFtrCde.js' />" type="text/javascript"></script>
	<script src="<c:url value='/js/kworks/rest/domnCode.js' />" type="text/javascript"></script>
	<script src="<c:url value='/js/kworks/rest/deptSub.js' />" type="text/javascript"></script>
	<script src="<c:url value='/js/kworks/rest/dong.js' />" type="text/javascript"></script>
	<script src="<c:url value='/js/kworks/rest/hDong.js' />" type="text/javascript"></script>
	<script src="<c:url value='/js/kworks/rest/index1000.js' />" type="text/javascript"></script>
	<script src="<c:url value='/js/kworks/rest/dongLi.js' />" type="text/javascript"></script>
	<script src="<c:url value='/js/kworks/rest/li.js' />" type="text/javascript"></script>
	<script src="<c:url value='/js/kworks/rest/ftrIdn.js' />" type="text/javascript"></script>
	<script src="<c:url value='/js/kworks/rest/layer.js' />" type="text/javascript"></script>
	<script src="<c:url value='/js/kworks/rest/road.js' />" type="text/javascript"></script>
	<script src="<c:url value='/js/kworks/rest/themamap.js' />" type="text/javascript"></script>

	<script src="<c:url value='/js/kworks/main/main.js' />" type="text/javascript"></script>
	<script src="<c:url value='/js/kworks/main/userEnvironment.js' />" type="text/javascript"></script>
	<script src="<c:url value='/js/kworks/main/map.js' />" type="text/javascript"></script>
	<script src="<c:url value='/js/kworks/main/multi.js' />" type="text/javascript"></script>
	<script src="<c:url value='/js/kworks/main/pnu.js' />" type="text/javascript"></script>
	<script src="<c:url value='/js/kworks/main/result.js' />" type="text/javascript"></script>
	<script src="<c:url value='/js/kworks/main/crossSectionalResult.js' />" type="text/javascript"></script>
	
	<script src="<c:url value='/js/kworks/window/window.js' />" type="text/javascript"></script>
	<script src="<c:url value='/js/kworks/window/attr.js' />" type="text/javascript"></script>
	<script src="<c:url value='/js/kworks/window/bookMark.js' />" type="text/javascript"></script>
	<script src="<c:url value='/js/kworks/window/coordinate.js' />" type="text/javascript"></script>
	<script src="<c:url value='/js/kworks/window/domainCl.js' />" type="text/javascript"></script>
	<script src="<c:url value='/js/kworks/window/drawTool.js' />" type="text/javascript"></script>
	<script src="<c:url value='/js/kworks/window/edit.js' />" type="text/javascript"></script>
	<script src="<c:url value='/js/kworks/window/duplicationConfirm.js' />" type="text/javascript"></script>
	<script src="<c:url value='/js/kworks/window/directionConversion.js' />" type="text/javascript"></script>
	<script src="<c:url value='/js/kworks/window/export.js' />" type="text/javascript"></script>
	<script src="<c:url value='/js/kworks/window/measure.js' />" type="text/javascript"></script>
	<script src="<c:url value='/js/${prjCode}/window/kras.js' />"></script>
	<script src="<c:url value='/js/kworks/window/layer.js' />" type="text/javascript"></script>
	<script src="<c:url value='/js/kworks/window/layerSetting.js' />" type="text/javascript"></script>
	<script src="<c:url value='/js/kworks/window/symbolSetting.js' />" type="text/javascript"></script>
	<script src="<c:url value='/js/kworks/window/lineSetting.js' />" type="text/javascript"></script>
	<script src="<c:url value='/js/kworks/window/polygonSetting.js' />" type="text/javascript"></script>
	<script src="<c:url value='/js/kworks/window/labelSetting.js' />" type="text/javascript"></script>
	<script src="<c:url value='/js/kworks/window/register.js' />" type="text/javascript"></script>
	<script src="<c:url value='/js/kworks/window/scale.js' />" type="text/javascript"></script>
	<script src="<c:url value='/js/kworks/window/settings.js' />" type="text/javascript"></script>
	<script src="<c:url value='/js/kworks/window/spatialInfo.js' />" type="text/javascript"></script>
	<script src="<c:url value='/js/kworks/window/spatialSearch.js' />" type="text/javascript"></script>
	<script src="<c:url value='/js/kworks/window/spatialSelect.js' />" type="text/javascript"></script>
	<script src="<c:url value='/js/kworks/window/themamap.js' />" type="text/javascript"></script>
	<script src="<c:url value='/js/kworks/window/writeText.js' />" type="text/javascript"></script>
	<script src="<c:url value='/js/kworks/window/seaoll.js' />" type="text/javascript"></script>
	<script src="<c:url value='/js/kworks/window/unity/unityConditionSearch.js' />" type="text/javascript"></script>
	<script src="<c:url value='/js/kworks/window/analysis/crossSectionalSelect.js' />" type="text/javascript"></script>
	<script src="<c:url value='/js/kworks/window/analysis/waterControlValueSelect.js' />" type="text/javascript"></script>
	<script src="<c:url value='/js/kworks/window/analysis/waterControlValueResult.js' />" type="text/javascript"></script>
	
	<script src="<c:url value='/js/kworks/main/menu/job.js' />" type="text/javascript"></script>
	<script src="<c:url value='/js/kworks/main/menu/search.js' />" type="text/javascript"></script>
	<script src="<c:url value='/js/kworks/main/menu/backgroundMap.js' />" type="text/javascript"></script>
	<script src="<c:url value='/js/kworks/main/menu/themamap.js' />" type="text/javascript"></script>
	<script src="<c:url value='/js/kworks/main/menu/layer.js' />" type="text/javascript"></script>
	<script src="<c:url value='/js/kworks/main/menu/file.js' />" type="text/javascript"></script>
	<script src="<c:url value='/js/kworks/main/menu/output.js' />" type="text/javascript"></script>
	<script src="<c:url value='/js/kworks/main/menu/bookMark.js' />" type="text/javascript"></script>
		
	<!-- 검색결과 -->
	<script src="<c:url value='/js/kworks/window/result/additionChoice.js' />" type="text/javascript"></script>
	<script src="<c:url value='/js/kworks/window/result/conditionSearch.js' />" type="text/javascript"></script>
	<script src="<c:url value='/js/kworks/window/result/itemSetup.js' />" type="text/javascript"></script>
	<script src="<c:url value='/js/kworks/window/result/relationSearch.js' />" type="text/javascript"></script>
	
	<!-- 도로 현황 -->
	<script src="<c:url value='/js/kworks/window/road/name.js' />" type="text/javascript"></script>
	<script src="<c:url value='/js/kworks/window/road/route.js' />" type="text/javascript"></script>
	<script src="<c:url value='/js/kworks/window/road/searchFacilities.js' />" type="text/javascript"></script>
	<script src="<c:url value='/js/kworks/window/road/roadNameEdit.js' />" type="text/javascript"></script>
	
	<!-- 기준점 -->
	<script src="<c:url value='/js/kworks/window/ctrlPnt/controlPoint.js' />" type="text/javascript"></script>

	<!-- 관리이력 -->
	<script src="<c:url value='/js/kworks/window/manageHist.js' />" type="text/javascript"></script>
	
	<!-- 사진관리 -->
	<script src="<c:url value='/js/kworks/window/photoManage.js' />" type="text/javascript"></script>
	
	<!-- 영상관리 -->
	<script src="<c:url value='/js/kworks/window/videoManage.js' />" type="text/javascript"></script>
	
	<!-- 구간도면관리 -->
	<script src="<c:url value='/js/kworks/window/sectionPlan.js' />" type="text/javascript"></script>
	
	<!-- 단위도면관리 -->
	<script src="<c:url value='/js/kworks/window/localPlan.js' />" type="text/javascript"></script>
	
	<!-- 계약대장 -->
	<script src="<c:url value='/js/kworks/window/cntrct/cntrctRegstr.js' />" type="text/javascript"></script>
	
	<c:if test="${isStatsMap eq true}" >
		<script src="<c:url value='/js/kworks/main/menu/statsMap.js' />" type="text/javascript"></script>
	</c:if>
	
	<c:if test="${isAerialPhoto eq true}" >
		<!-- 역사지도 -->
		<script src="<c:url value='/js/kworks/main/menu/historyMap.js' />" type="text/javascript"></script>
	
		<!-- 변화탐지 -->
		<script src="<c:url value='/js/kworks/main/menu/changeDetection.js' />" type="text/javascript"></script>
		
		<!-- 영상자료 -->
		<script src="<c:url value='/js/kworks/main/menu/videoData.js' />" type="text/javascript"></script>
		
	</c:if>
	
	<c:if test="${isSharingMap eq true}" >
		<!-- 공유지도 -->
		<script src="<c:url value='/js/kworks/main/menu/sharingMap.js' />" type="text/javascript"></script>
	</c:if>
	
	<c:if test="${isUgrwtr eq true}" >
	<!-- 지하수관정 -->
		<script src="<c:url value='/js/${prjCode}/job/ugrwtr/ugrwtr.js' />" type="text/javascript"></script>
	</c:if>
	
	<c:if test="${isRiverSidePoint eq true}">
	<!-- 하천측점 -->
		<script src="<c:url value='/js/${prjCode}/job/riverSidePoint/riverSidePoint.js' />" type="text/javascript"></script>
	</c:if>
	
	<c:if test="${isCntrwkRegstr eq true}" >
	<!-- 공사대장 -->
	
		<!-- 도로 공사대장 -->
		<script src="<c:url value='/js/${prjCode}/job/cntrwkRegstr/road/roadCntrwkRegstr.js' />" type="text/javascript"></script>
		<script src="<c:url value='/js/${prjCode}/job/cntrwkRegstr/road/roadFlawMendngDtls.js' />" type="text/javascript"></script>
		<script src="<c:url value='/js/${prjCode}/job/cntrwkRegstr/road/roadCntrwkCtPymntDtls.js' />" type="text/javascript"></script>
		<script src="<c:url value='/js/${prjCode}/job/cntrwkRegstr/road/roadDsgnChangeDtls.js' />" type="text/javascript"></script>
		<script src="<c:url value='/js/${prjCode}/job/cntrwkRegstr/road/roadSubcntrDtls.js' />" type="text/javascript"></script>
		
		<!-- 상수 공사대장 -->
		<script src="<c:url value='/js/${prjCode}/job/cntrwkRegstr/wrpp/wrppCntrwkRegstr.js' />" type="text/javascript"></script>
		<script src="<c:url value='/js/${prjCode}/job/cntrwkRegstr/wrpp/wrppFlawMendngDtls.js' />" type="text/javascript"></script>
		<script src="<c:url value='/js/${prjCode}/job/cntrwkRegstr/wrpp/wrppCntrwkCtPymntDtls.js' />" type="text/javascript"></script>
		<script src="<c:url value='/js/${prjCode}/job/cntrwkRegstr/wrpp/wrppDsgnChangeDtls.js' />" type="text/javascript"></script>
		<script src="<c:url value='/js/${prjCode}/job/cntrwkRegstr/wrpp/wrppSubcntrDtls.js' />" type="text/javascript"></script>
		
		<!-- 급수 공사대장 -->
		<script src="<c:url value='/js/${prjCode}/job/cntrwkRegstr/wsp/wspCntrwkRegstr.js' />" type="text/javascript"></script>
		
		<!-- 하수 공사대장 -->
		<script src="<c:url value='/js/${prjCode}/job/cntrwkRegstr/swge/swgeCntrwkRegstr.js' />" type="text/javascript"></script>
		<script src="<c:url value='/js/${prjCode}/job/cntrwkRegstr/swge/swgeFlawMendngDtls.js' />" type="text/javascript"></script>
		<script src="<c:url value='/js/${prjCode}/job/cntrwkRegstr/swge/swgeCntrwkCtPymntDtls.js' />" type="text/javascript"></script>
		<script src="<c:url value='/js/${prjCode}/job/cntrwkRegstr/swge/swgeDsgnChangeDtls.js' />" type="text/javascript"></script>
		<script src="<c:url value='/js/${prjCode}/job/cntrwkRegstr/swge/swgeSubcntrDtls.js' />" type="text/javascript"></script>
		
	</c:if>
	
	<!-- 기준점 -->
	<c:if test="${isCtrlpnt eq true}" >
		<c:if test="${ prjCode ne 'yy' }">
			<script src="<c:url value='/js/${prjCode}/job/ctrlpnt/ctrlpnt.js' />" type="text/javascript"></script>
		</c:if>
		<c:if test="${ prjCode eq 'yy' }">
			<script src="<c:url value='/js/${prjCode}/job/ctrlpnt/surveyBasePoint.js' />" type="text/javascript"></script>
		</c:if>
	</c:if>
	
	<c:if test="${isMesrSgnal eq true}" >
	<!-- 측량표지현황 -->
		<script src="<c:url value='/js/${prjCode}/job/mesrSgnal/mesrSgnalSttusExaminHist.js' />" type="text/javascript"></script>
	</c:if>
		
	<c:if test="${isLossSttemnt eq true}" >
	<!-- 망실신고 -->
		<script src="<c:url value='/js/${prjCode}/job/lossSttemnt/lossSttemnt.js' />" type="text/javascript"></script>
	</c:if>
	
	<c:if test="${isCivilAppeal eq true}" >
	<!-- 민원 -->
		<!-- 도로시스템 // 도로민원 -->
		<script src="<c:url value='/js/${prjCode}/job/civilAppeal/road/roadCivilAppeal.js' />" type="text/javascript"></script>
		<!-- 상수시스템 // 상수민원 -->
		<script src="<c:url value='/js/${prjCode}/job/civilAppeal/wrpp/wrppCivilAppeal.js' />" type="text/javascript"></script>
		<!-- 하수시스템 // 하수민원 / 배수설비인허가 -->
		<script src="<c:url value='/js/${prjCode}/job/civilAppeal/swge/swgeCivilAppeal.js' />" type="text/javascript"></script>
		<script src="<c:url value='/js/${prjCode}/job/civilAppeal/swge/drngEqpCnfmPrmisn.js' />" type="text/javascript"></script>
	</c:if>
	
	<!-- 지자체별 업무 -->
	<script src="<c:url value='/js/${prjCode}/job/job.js' />" type="text/javascript"></script>
	<script src="<c:url value='/js/${prjCode}/main/custom.js' />" type="text/javascript"></script>
	
	<c:if test="${ prjCode eq 'gds' }">
	<!-- 시연 -->
		<!-- 드론 -->
		<script src="<c:url value='/js/${prjCode}/dron/dron.js' />" type="text/javascript"></script>
		
		<!-- 도시공원 -->
		<script src="<c:url value='/js/${prjCode}/job/edit.js' />" type="text/javascript"></script>
		<script src="<c:url value='/js/${prjCode}/job/cityPark/cityPark.js' />" type="text/javascript"></script>
		<script src="<c:url value='/js/${prjCode}/job/cityPark/civilAppeal.js' />" type="text/javascript"></script>
		<script src="<c:url value='/js/${prjCode}/job/cityPark/changeDetails.js' />" type="text/javascript"></script>
		<script src="<c:url value='/js/${prjCode}/job/cityPark/cityParkStatistics.js' />" type="text/javascript"></script>
		
		<!-- 생태자연도 -->
		<script src="<c:url value='/js/${prjCode}/job/ecologyNatureMap/ecologyNatureMap.js' />" type="text/javascript"></script>
		
		<!-- 토지중심 -->
		<script src="<c:url value='/js/${prjCode}/job/landCenter/landCenterCntrwkRegstr.js' />" type="text/javascript"></script>
		<script src="<c:url value='/js/${prjCode}/job/landCenter/landUseInfoRegstr.js' />" type="text/javascript"></script>
	</c:if>
	
	<c:if test="${ prjCode eq 'gn' }">
	<!--  강릉시 -->
		<!-- 도시공원 -->
		<script src="<c:url value='/js/${prjCode}/job/edit.js' />" type="text/javascript"></script>
		<script src="<c:url value='/js/${prjCode}/job/cityPark/cityPark.js' />" type="text/javascript"></script>
		<script src="<c:url value='/js/${prjCode}/job/cityPark/civilAppeal.js' />" type="text/javascript"></script>
		<script src="<c:url value='/js/${prjCode}/job/cityPark/changeDetails.js' />" type="text/javascript"></script>
		<script src="<c:url value='/js/${prjCode}/job/cityPark/cityParkStatistics.js' />" type="text/javascript"></script>
		
		<!-- 생태자연도 -->
		<script src="<c:url value='/js/${prjCode}/job/ecologyNatureMap/ecologyNatureMap.js' />" type="text/javascript"></script>
		
		<!-- 토지중심 -->
		<script src="<c:url value='/js/${prjCode}/job/landCenter/landCenterCntrwkRegstr.js' />" type="text/javascript"></script>
		<script src="<c:url value='/js/${prjCode}/job/landCenter/landUseInfoRegstr.js' />" type="text/javascript"></script>
		
		<!-- 도로대장 -->
		<script src="<c:url value='/js/${prjCode}/job/roadRegstr/roadRegstr.js' />" type="text/javascript"></script>
		<script src="<c:url value='/js/kworks/rest/roadReg.js' />" type="text/javascript"></script>
		<script src="<c:url value='/js/kworks/main/roadRegResult.js' />" type="text/javascript"></script>
		<script src="<c:url value='/js/kworks/main/roadVideoResult.js' />" type="text/javascript"></script>
		<script src="<c:url value='/js/kworks/main/roadVideoView.js' />" type="text/javascript"></script>
		<script src="<c:url value='/js/kworks/window/roadRegister.js' />" type="text/javascript"></script>
		
		<!--  침출수 -->
		<script src="<c:url value='/js/${prjCode}/job/cntrwkRegstr/lwl/lwlCntrwkRegstr.js' />" type="text/javascript"></script>
		
		<!-- 도로표지판관리 -->
		<script src="<c:url value='/js/${prjCode}/job/roadSgnlbrd/roadSgnlbrdManage.js' />" type="text/javascript"></script>
		
		<!-- 관광안내표지판관리 -->
		<script src="<c:url value='/js/${prjCode }/job/tursmGuidanceSgnlbrd/tursmGuidanceSgnlbrdManage.js' />" type="text/javascript"></script>
		
		<!-- 점용허가관리 -->
		<script src="<c:url value='/js/kworks/rest/ocpatReg.js' />" type="text/javascript"></script>
		<script src="<c:url value='/js/${prjCode}/job/ocpat/ocpatRegstr.js' />" type="text/javascript"></script>
		<script src="<c:url value='/js/kworks/main/ocpatRegResult.js' />" type="text/javascript"></script>
	 	<script src="<c:url value='/js/kworks/window/ocpatRegister.js' />" type="text/javascript"></script>
	 	<!-- 점용허가관리 부속자료/변경이력  -->
	 	<script src="<c:url value='/js/kworks/window/ocpatHist.js' />" type="text/javascript"></script>
	 	
	 	<!-- 상수실시간측량지점(상수관로심도) 조서조회 -->
		<script src="<c:url value='/js/${prjCode}/job/wrppAcmsrSpot/wrppAcmsrSpotManage.js' />" type="text/javascript"></script>

		<!-- 도시계획도로  -->		
		<script src="<c:url value='/js/kworks/window/cityPlanRoad/cityPlanRoad.js' />" type="text/javascript"></script>
		
		<!-- 보호구역  -->
		<script type="text/javascript" src="<c:url value='/js/${prjCode }/job/prtsArea/prtsArea.js'/>"></script>
	</c:if>
	
	<c:if test="${ prjCode eq 'dh' }">
	<!-- 동해시 -->
		<!-- 해안침식 -->
		<script src="<c:url value='/js/${prjCode}/job/beachErosion/beachErosion.js' />" type="text/javascript"></script>
		
		<!-- 도시계획도로  -->		
		<script src="<c:url value='/js/${prjCode}/job/cityPlanRoad/dhPlanRoad.js' />" type="text/javascript"></script>
		<script src="<c:url value='/js/${prjCode}/job/cityPlanRoad/dhPlanRoadResult.js' />" type="text/javascript"></script>	
		
		<!-- 306vr -->
		<script src="<c:url value='/js/${prjCode}/job/panoramaVR/panoramaVR.js' />" type="text/javascript"></script>
	</c:if>
	
	<c:if test="${ prjCode eq 'yy' }">
	<!-- 양양군 -->
		<!-- 생태자연도 -->
		<script src="<c:url value='/js/${prjCode}/job/ecologyNatureMap/ecologyNatureMap.js' />" type="text/javascript"></script>
		<!-- 정책지도 -->
		<script src="<c:url value='/js/${prjCode}/job/policy/policyRegstr.js' />" type="text/javascript"></script> <!-- 대장검색 -->
		<script src="<c:url value='/js/${prjCode}/job/policy/editPolicy.js' />" type="text/javascript"></script> <!-- 위치편집 -->
		<script src="<c:url value='/js/${prjCode}/job/policy/editPolicyRegister.js' />" type="text/javascript"></script> <!-- 대장등록 -->
		<script src="<c:url value='/js/${prjCode}/job/policy/policyRegResult.js' />" type="text/javascript"></script> <!-- 검색목록현황 -->
		<script src="<c:url value='/js/${prjCode}/job/policy/policyRegister.js' />" type="text/javascript"></script> <!-- 대장조회 -->
		<script src="<c:url value='/js/${prjCode}/job/policy/policyDeptSearch.js' />" type="text/javascript"></script> <!-- 정책지도 실과소 -->
		 <!--ck에디터 관련-->
		<script src="<c:url value='/webjars/ckeditor/4.5.8/full/ckeditor.js' />" type="text/javascript"></script>
		<script src="<c:url value='/webjars/ckeditor/4.5.8/full/adapters/jquery.js'/>" type="text/javascript"></script>
		<script src="<c:url value='/js/${prjCode}/job/policy/ckeditorconfig.js' />" type="text/javascript"></script>
	</c:if>
	
	<c:if test="${ prjCode eq 'yg'}">
	<!-- 양구군 -->
		<!-- 생태자연도 -->
		<script src="<c:url value='/js/${prjCode}/job/ecologyNatureMap/ecologyNatureMap.js' />" type="text/javascript"></script>
		<!-- 토지중심 -->
		<script src="<c:url value='/js/${prjCode}/job/landCenter/landCenterCntrwkRegstr.js' />" type="text/javascript"></script>
		<script src="<c:url value='/js/${prjCode}/job/landCenter/landUseInfoRegstr.js' />" type="text/javascript"></script>
	</c:if>
		
		<!-- 고성군 -->
	<c:if test="${ prjCode eq 'gs' }">
		<!-- 환경공간업무 -->
		<script src="<c:url value='/js/${prjCode}/job/enviro/explant.js' />" type="text/javascript"></script>
		<script src="<c:url value='/js/${prjCode}/job/enviro/nelEcSearch.js' />" type="text/javascript"></script>
		<script src="<c:url value='/js/${prjCode}/job/enviro/reant.js' />" type="text/javascript"></script>
		<script src="<c:url value='/js/${prjCode}/job/enviro/explantedit.js' />" type="text/javascript"></script>
		<script src="<c:url value='/js/${prjCode}/job/enviro/reantedit.js' />" type="text/javascript"></script>
		<script src="<c:url value='/js/${prjCode}/job/enviro/explantproperty.js' />" type="text/javascript"></script>
		<script src="<c:url value='/js/${prjCode}/job/enviro/reantproperty.js' />" type="text/javascript"></script>
	</c:if>
	
	<!-- 지형지도 활용 -->
	<c:if test="${isTopographicMap eq 'true'}">
		<script src="<c:url value='/js/kworks/gis/topographicObj.js' />" type="text/javascript"></script>
		<script src="<c:url value='/js/kworks/gis/topographicSectionView.js' />" type="text/javascript"></script>
		<script src="<c:url value='/js/kworks/main/menu/topographic.js' />" type="text/javascript"></script>
		<script src="<c:url value='/js/kworks/main/topographicSectionResult.js' />" type="text/javascript"></script>
		<script src="<c:url value='/js/kworks/window/analysis/topographicSectionSelect.js' />" type="text/javascript"></script>
	</c:if>

	<!-- 지적원도/드론영상 -->
	<c:if test="${isCadastralMap eq 'true'}">
		<script src="<c:url value='/js/kworks/gis/cadastralObj.js' />" type="text/javascript"></script>
		<script src="<c:url value='/js/kworks/main/menu/cadastralMap.js' />" type="text/javascript"></script>
	</c:if>
	
	<!-- 지적통계 -->
	<c:if test="${isLgstrStats eq 'true' }">
		<script src="<c:url value='/js/kworks/main/menu/lgstrStats.js'/>" type="text/javascript"></script>
	</c:if>
	
	<!-- 지적업무 -->
	<c:if test="${isLgstrBiz eq 'true' }">
		<script src="<c:url value='/js/kworks/main/menu/lgstrBiz.js'/>" type="text/javascript"></script>
	</c:if>
		
	<title>${cityNm} 웹표준 공간정보통합관리 서비스</title>
</head>
<body>
	<input id="hid_user_name" type="hidden" value="<c:out value='${userName}' />" />
	<input id="hid_dept_name" type="hidden" value="<c:out value='${deptName}' />" />
	<div id="div_loading">
		<img src="<c:url value='/images/kworks/common/loading.svg' />" alt="로딩중" title="로딩중" />
	</div>

	<div id="wrap">
	
		<!-- 상단 영역 시작 -->
		<div id="header">
			<!-- 로고 -->
			<div class="logo ma_t_10 ma_l_20">
				<a href="<c:url value='/' />" ><img alt="${cityNm}" title="${cityNm}" src="<c:url value='/images/${prjCode}/common/logo-min.png' />" /></a>
			</div>
			
			<!-- 시스템 -->
			<div class="systemTitle ma_t_9 ma_l_20">
				<c:if test="${sys.sysTy eq 'SYSTEM'}">
					<img alt="<c:out value='${sys.sysAlias}' />" title="<c:out value='${sys.sysAlias}' />" src="<c:url value='/images/${prjCode}/main/sys/sys_${sys.sysNm}.png' />" />
				</c:if>
				<c:if test="${sys.sysTy eq 'USER'}">
					<img class="img_user_system" alt="<c:out value='${sys.sysAlias}' />" title="<c:out value='${sys.sysAlias}' />" src="<c:url value='/images/${prjCode}/main/sys/sys.png' />" />
					<span class="span_user_system"><c:out value='${sys.sysAlias}' /></span>
				</c:if>
			</div>
			
			<c:if test="${ prjCode eq 'dh'}">
				<!-- 동해시 상단 경고문구 -->
				<div class="gnb div_warnings color_orange">
						<span class="ma_r_10">※</span>공간정보 도면 외부유출 금지
				</div>
			</c:if>
			
			<!-- 사용자 / 로그아웃 -->
			<div class="gnb div_logout clearfix">
				<div class="color_3 ma_t_4 f_s_14"><span><strong><c:out value='${userName}' /></strong> 님 환영합니다.<c:out value='${resultHadm}' /></span></div>
				<div class="gnb_div ma_t_3">
					<div class="gnb_img">
						<a href="<c:url value='/j_spring_security_logout' />" class="btn_logout" title="로그아웃" ></a>
					</div>
					<div class="gnb_img">
						<a id="a_open_settings" href="#">
							<img src="<c:url value='/images/kworks/map/svg/topTools/ic_set.png' />" alt="화면설정" title="화면설정" />
						</a>
					</div>
				</div>
			</div>
			
			<!-- 빠른 검색 & 지도 기능 -->
			<div class="heder_snb h_50 ma_t_10 bg_sky" style="overflow:hidden;">
				<!-- 빠른 검색 -->
				<div id="div_quick_search">
					<!-- 법정동 or 도로명 주소 검색 -->
					<span class="div_quick_search_type">
						<span>
							<select class="sel_search_type">
								<option value="legalDong">법정동</option>
								<option value="roadName">도로명</option>
							</select>
						</span>
					</span>
					<!-- 상단 지번 검색 start -->
					<span id="div_quick_search_legal_dong" class="div_container_top_search snb_text color_white kw_toptoolbar_div_left_search kw_toptoolbar_div_sub">
						<span><select class="sel_dong"></select></span>	
						<span>
							<label for="div_quick_search_legal_dong_mntn">산</label>
							<input type="checkbox" class="chk_mntn" id="div_quick_search_legal_dong_mntn" />
						</span>
						<span>
							지번 <input type="text" class="txt_mnnm" />
						</span>
						-
						<span>
							<input type="text" class="txt_slno" />
						</span>
						<span>
							<a href="#" class="a_search">검색</a>
						</span>
					</span>
					<!-- 상단 지번 검색 end -->
					<!-- 상단 지번 확장 검색(읍면동 / 리) start -->
					<span id="div_quick_search_legal_dong_li" class="div_container_top_search snb_text color_white kw_toptoolbar_div_left_search kw_toptoolbar_div_sub">
						<span><select class="sel_dong"></select></span>	
						<span><select class="sel_li"></select></span>	
						<span>
							<label for="div_quick_search_legal_dong_mntn">산</label>
							<input type="checkbox" class="chk_mntn" id="div_quick_search_legal_dong_mntn_li" />
						</span>
						<span>
							지번 <input type="text" class="txt_mnnm" />
						</span>
						-
						<span>
							<input type="text" class="txt_slno" />
						</span>
						<span>
							<a href="#" class="a_search">검색</a>
						</span>
					</span>
					<!-- 상단 지번 확장 검색 end -->
					<!-- 상단 도로명 검색 start -->
					<span id="div_quick_search_road_name">
						<span>
							<select class="sel_road_name"></select>
						</span>
						<span>
							<input type="text" class="txt_mnnm" />-
							<input type="text" class="txt_slno" />
						</span>
						<span>
							<a href="#" class="a_search easyui-linkbutton">검색</a>
						</span>
					</span>
					<!-- 상단 도로명 검색 end -->
				</div>
				
				<!-- 지도 툴 -->
				<ul id="ul_map_toolbar_on_off" class="toolbar ma_t_11 kw_toptoolbar_div_right">
					<li id="li_map_tool_line_on_off" class="li_line"><img src="<c:url value='/images/kworks/map/skin2/ic_set_line.png' />" /></li>
					<li id="li_map_tool_on" class="display_n"><a class="kw_toptoolbar_a" id="a_map_tool_on" href="#"><img class="img_ov_off tootip" src="<c:url value='/images/kworks/map/svg/topTools/down_off.svg' />" alt="지도툴열기" title="지도툴열기" /></a></li>
					<li id="li_map_tool_off"><a class="kw_toptoolbar_a" id="a_map_tool_off" href="#"><img class="img_ov_off tootip" src="<c:url value='/images/kworks/map/svg/topTools/up_off.svg' />" alt="지도툴닫기" title="지도툴닫기" /></a></li>
					<%-- <li id="li_map_tool_on"><a class="kw_toptoolbar_a" id="a_map_tool_on" href="#"><img class="img_ov_off tootip" src="<c:url value='/images/kworks/map/svg/topTools/down_off.svg' />" alt="지도툴열기" title="지도툴열기" /></a></li>
					<li id="li_map_tool_off" class="display_n"><a class="kw_toptoolbar_a" id="a_map_tool_off" href="#"><img class="img_ov_off tootip" src="<c:url value='/images/kworks/map/svg/topTools/up_off.svg' />" alt="지도툴닫기" title="지도툴닫기" /></a></li> --%>
				</ul>
				<ul id="ul_map_toolbar" class="toolbar ma_t_11 kw_toptoolbar_div_right">
				</ul>
			</div>
			
		</div>
		<!-- 상단 영역 끝 -->
		
		<!-- 메인 영역 시작 -->
		<div id="container">
		
			<!-- 지도 세로 툴 -->
			<div id="ul_map_toolbar_vertical">
			</div>
		
			<!-- 다분면 선택 창 -->
			<div id="div_multi_map" class="po_a bg_white" style="top:5px;right:50px;z-index:2000;display:none;">
				<div class="k_pop_box bd_sky w_106">
					<div class="pa_b_10 pa_t_10 bd_b_gray1">
						<a class="a_multi_map pa_l_20" href="#" data-mode="0">
							<img src="<c:url value='/images/kworks/map/skin2/square01_off.png'  />" alt="1면" title="1면" />
						</a>
					</div>
					<div class="pa_t_10 pa_b_10 bd_b_gray1">
						<a class="a_multi_map pa_l_20 pa_r_20" href="#" data-mode="1">
							<img src="<c:url value='/images/kworks/map/skin2/square02_off.png'/>" alt="2면(좌우)" title="2면(좌우)" />
						</a>
						<a href="#" class="a_multi_map" data-mode="2">
							<img src="<c:url value='/images/kworks/map/skin2/square03_off.png'  />" alt="2면(상하)" title="2면(상하)" />
						</a>
					</div>
					<div class="pa_t_10 pa_b_10 bd_b_gray1">
						<a class="a_multi_map pa_l_20 pa_r_20" href="#" data-mode="3">
							<img src="<c:url value='/images/kworks/map/skin2/square04_off.png'  />" alt="3면(좌)" title="3면(좌)" />
						</a>
						<a href="#" class="a_multi_map" data-mode="4">
							<img src="<c:url value='/images/kworks/map/skin2/square05_off.png'  />" alt="3면(우)" title="3면(우)" />
						</a>
					</div>
					<div class="pa_t_10 pa_b_10 bd_b_gray1">
						<a class="a_multi_map pa_l_20 pa_r_20" href="#" data-mode="5">
							<img src="<c:url value='/images/kworks/map/skin2/square06_off.png'  />" alt="3면(상)" title="3면(상)" />
						</a>
						<a href="#" class="a_multi_map" data-mode="6">
							<img src="<c:url value='/images/kworks/map/skin2/square07_off.png'  />" alt="3면(하)" title="3면(하)" />
						</a>
					</div>
					<div class="pa_t_10 pa_b_10">
						<a class="a_multi_map pa_l_20 pa_r_20" href="#" data-mode="7">
							<img src="<c:url value='/images/kworks/map/skin2/square08_off.png'  />" alt="4면" title="4면" />
						</a>
					</div>
				</div>
			</div>
		
			<!-- 왼쪽 메뉴 시작 -->
			<div id="div_menu" class="aside">
				<div class="nav">
					<ul class="nav_lst">
						<c:if test="${sys.sysNm ne 'GIS'}">
							<li>
								<a id="a_menu_job" data-menu-id="job" href="#">
									<img src="<c:url value='/images/kworks/map/svg/left/nov01_off.svg' />" alt="업무관리" title="업무관리" />
								</a>
							</li>
						</c:if>
						<li>
							<a id="a_menu_search" data-menu-id="search" href="#">
								<img src="<c:url value='/images/kworks/map/svg/left/nov05_off.svg' />" alt="검색" title="검색" />
							</a>
						</li>
						<li>
							<a id="a_menu_background_map" data-menu-id="backgroundMap" href="#">
								<img src="<c:url value='/images/kworks/map/svg/left/nov03_off.svg' />" alt="배경지도" title="배경지도" />
							</a>
						</li>
						<!-- 지형지도 활용 -->						
						<c:if test="${isTopographicMap eq true}" >
							<li>
								<a id="a_menu_topographic_map" data-menu-id="topographicMap" href="#">
									<img src="<c:url value='/images/kworks/map/svg/left/nov14_off.svg' />" alt="지형지도" title="지형지도" />
								</a>
							</li>
						</c:if>
						<!-- 지적원도 활용 -->						
						<c:if test="${isCadastralMap eq true}" >
							<c:if test="${prjCode eq 'sc' || prjCode eq 'sunchang'}">
								<li>
									<a id="a_menu_cadastral_map" data-menu-id="cadastralMap" href="#">
										<img src="<c:url value='/images/kworks/map/svg/left/nov16_off.svg' />" alt="드론영상" title="드론영상" />
									</a>
								</li>
							</c:if>
							<c:if test="${prjCode ne 'sc' && prjCode ne 'sunchang'}">
								<li>
									<a id="a_menu_cadastral_map" data-menu-id="cadastralMap" href="#">
										<img src="<c:url value='/images/kworks/map/svg/left/nov15_off.svg' />" alt="지적원도" title="지적원도" />
									</a>
								</li>
							</c:if>
						</c:if>
						<!-- 지적업무 활용 -->
						<c:if test="${isLgstrBiz eq true }">
							<li>
								<a id="a_menu_lgstrBiz" data-menu-id="lgstrBiz" href="#">
									<img alt="변환지적" title="변환지적" src="<c:url value='/images/kworks/map/svg/left/nov19_off.svg'/>">
									<%-- <img alt="지적업무" title="지적업무" src="<c:url value='/images/kworks/map/svg/left/nov18_off.svg'/>"> --%>
								</a>
							</li>
						</c:if>
						<li>
							<a id="a_menu_themamap" data-menu-id="themamap" href="#">
								<img src="<c:url value='/images/kworks/map/svg/left/nov04_off.svg' />" alt="주제도" title="주제도" />
							</a>
						</li>
						<li>
							<a id="a_menu_layer" data-menu-id="layer" href="#">
								<img src="<c:url value='/images/kworks/map/svg/left/nov06_off.svg' />" alt="레이어" title="레이어" /> 
							</a>
						</li>
						<c:if test="${isStatsMap eq true}" >
							<li>
								<a id="a_menu_state_map" data-menu-id="statsMap" href="#">
									<img src="<c:url value='/images/kworks/map/svg/left/nov09_off.png' />" alt="통계지도" title="통계지도" />
								</a>
							</li>
						</c:if>
						<li>
							<a id="a_menu_file" data-menu-id="file" href="#">
								<img src="<c:url value='/images/kworks/map/svg/left/nov08_off.svg' />" alt="파일" title="파일" />
							</a>
						</li>
						<c:if test="${isLgstrStats eq true}">
							<li>
								<a id="a_menu_lgstrStats" data-menu-id="lgstrStats" href="#">
									<img src="<c:url value='/images/kworks/map/svg/left/nov17_off.svg'/>" alt="지적통계" title="지적통계"/>
								</a>
							</li>
						</c:if>
						<li>
							<a id="a_menu_output" data-menu-id="output" href="#">
								<img src="<c:url value='/images/kworks/map/svg/left/nov02_off.svg' />" alt="출력" title="출력" />
							</a>
						</li>
						<li>
							<a id="a_menu_bookmark" data-menu-id="bookMark" href="#">
								<img src="<c:url value='/images/kworks/map/svg/left/nov07_off.svg' />" alt="북마크" title="북마크" />
							</a>
						</li>
						<c:if test="${sys.sysId eq '1'}">
							<c:if test="${isAerialPhoto eq true}" >
								<li>
									<a id="a_menu_change_detection" data-menu-id="historyMap" href="#">
										<img src="<c:url value='/images/kworks/map/svg/left/nov10_off.svg' />" alt="역사지도" title="역사지도" />
									</a>
								</li>
								<li>
									<a id="a_menu_change_detection" data-menu-id="changeDetection" href="#">
										<img src="<c:url value='/images/kworks/map/svg/left/nov11_off.svg' />" alt="변화탐지" title="변화탐지" />
									</a>
								</li>
								<li>
									<a id="a_menu_change_detection" data-menu-id="videoData" href="#">
										<img src="<c:url value='/images/kworks/map/svg/left/nov12_off.svg' />" alt="영상자료" title="영상자료" />
									</a>
								</li>
							</c:if>
						</c:if>
						<c:if test="${isSharingMap eq true}" >
						<!--  공유지도 -->
							<li>
								<a id="a_menu_sharing_map" data-menu-id="sharingMap" href="#">
									<img src="<c:url value='/images/kworks/map/svg/left/nov13_off.png' />" alt="공유지도" title="공유지도" />
								</a>
							</li>
						</c:if>
						
					</ul>
				</div>
			</div>
			<!-- 왼쪽 메뉴 끝 -->
			
			<!-- 왼쪽 메뉴 패널 시작 -->
			<div id="div_menu_panel">
			</div>
			<!-- 왼쪽 메뉴 패널 끝 -->
			
			<!-- 지도 시작 -->
			<div id="map_container">
				<div id="map" class="po_a">
					<div style="position:relative;width:100%;height:100%;overflow-x:hidden;">
						<div id="div_map_layer_tool_base" class="div_map_layer_tool">
							<span class="span_title">배경지도</span>
							<select id="sel_map_layer_background_layer_base" class="sel_map_layer_background_layer">
							</select>
							<a href="#" id="a_land_register_base" class="a_land_register" data-index="0" >
								<img src="<c:url value='/images/kworks/map/svg/topTools/land_register_off.png' />" alt="지번" title="지번" />
							</a>
						</div>
						<div id="div_map" style="width:100%;height:100%;" ></div>
						
						<!-- coordinate -->
						<div id="div_coordinate_container">
							<a id="a_kworks_coordinate"></a>
						</div> 

						<!-- 배경지도 Comment -->
						<div id="div_description"></div>
												
						<!-- map_control -->
						<div class="map_control ma_t_10 ma_r_10">
							<div class="pa_l_31 w_32 h_31">
								<a id="a_map_control_zoomin" href="#"><img src="<c:url value='/images/kworks/map/common/scale_zin.png' />" alt="확대" title="확대" /></a>
							</div>
							<div class="con_box h_31">
								<a id="a_map_control_prev" href="#"><img src="<c:url value='/images/kworks/map/common/scale_pre.png' />" alt="이전영역" title="이전영역" /></a>
								<a id="a_map_control_home" href="#"><img src="<c:url value='/images/kworks/map/common/scale_home.png' />" alt="전체" title="전체" /></a>
								<a id="a_map_control_next" href="#"><img src="<c:url value='/images/kworks/map/common/scale_next.png' />" alt="다음영역" title="다음영역" /></a>
							</div>
							<div class="pa_l_31 w_32 h_31">
								<a id="a_map_control_zoomout" href="#"><img src="<c:url value='/images/kworks/map/common/scale_zout.png' />" alt="축소" title="축소" /></a>
							</div>
							
							<!--  동해시  Quick버튼 안보이기 요구사항 -->
							<c:if test="${ prjCode ne 'dh' }">
								<div id="div_quick_menu">
									<a id="a_quick_menu_open" href="#" class="display_n" ><img src="<c:url value='/images/kworks/map/tool/quick_down.png' />" alt="Quick Open" title="Quick Open" /></a>
									<a id="a_quick_menu_close" href="#"><img src="<c:url value='/images/kworks/map/tool/quick_up.png' />" alt="Quick Close" title="Quick Close" /></a>
								</div>
								<div id="div_quick_menu_container">
								</div>
							</c:if>
						</div>
						<!-- //map_control -->
						
						<!-- 항공사진 위치조절 -->
						<div class="moveAerialMap_control ma_t_10 ma_r_10">
							<div class="pa_l_31 w_32 h_31">
								<a id="a_moveAerialMap_control_up" href="#"><img src="<c:url value='/images/kworks/map/common/scale_up.png' />" alt="상" title="상" /></a>
							</div>
							<div class="con_box h_31">
								<a id="a_moveAerialMap_control_left" href="#"><img src="<c:url value='/images/kworks/map/common/scale_pre.png' />" alt="좌" title="좌" /></a>
								<a id="a_moveAerialMap_control_original" href="#"><img src="<c:url value='/images/kworks/map/common/scale_original.png' />" alt="초기위치" title="초기위치" /></a>
								<a id="a_moveAerialMap_control_right" href="#"><img src="<c:url value='/images/kworks/map/common/scale_next.png' />" alt="우" title="우" /></a>
							</div>
							<div class="pa_l_31 w_32 h_31">
								<a id="a_moveAerialMap_control_down" href="#"><img src="<c:url value='/images/kworks/map/common/scale_down.png' />" alt="하" title="하" /></a>
							</div>
							<div id="div_moveAerialMap_menu">
								<a id="a_moveAerialMap_control_mouse" href="#"><img class="tool_toggle_radio img_ov_off tootip" src="<c:url value='/images/kworks/map/common/scale_mouse_off.png' />" alt="마우스이동" title="마우스이동" /></a>
							</div>
						</div>
						<!-- //moveAerialMap_control -->
						
					</div>
				</div>
				
				<!-- zoom map -->
				<div id="div_zoom">
					<div class="zoom_relative">
	        			<div id="zoom_map"></div>
		        		<div class="zoom_img_container">
		        			<img class="zoom_img" src="<c:url value='/images/kworks/map/zoom/zoom_2x_300.png' />" usemap="#map_zoom_300" alt="돋보기" />
		        			<map id="map_zoom_300" name="map_zoom_300">
		        				<area class="area_zoom" data-zoom="2" shape="poly" alt="2배" title="2배" coords="8,116,27,121,39,89,50,72,59,62,44,49,25,76,15,97" />
		        				<area class="area_zoom" data-zoom="4" shape="poly" alt="4배" title="4배" coords="8,118,28,122,26,150,27,171,6,172" />
		        				<area class="area_size" data-next-size="400" shape="poly" alt="크기" title="크기" coords="18,215,40,205,56,232,74,248,62,264,45,248,24,223" />
		        			</map>
		        			<map id="map_zoom_400" name="map_zoom_400">
		        				<area class="area_zoom" data-zoom="2" shape="poly" alt="2배" title="2배" coords="18,136,37,145,52,109,68,88,82,75,68,60,50,77,30,107" />
		        				<area class="area_zoom" data-zoom="4" shape="poly" alt="4배" title="4배" coords="18,136,38,145,31,176,29,206,6,207,7,182,11,155" />
		        				<area class="area_size" data-next-size="500" shape="poly" alt="크기" title="크기" coords="28,302,53,288,70,312,85,328,93,334,73,353,55,336,36,315" />
		        			</map>
		        			<map id="map_zoom_500" name="map_zoom_500">
		        				<area class="area_zoom" data-zoom="2" shape="poly" alt="2배" title="2배" coords="34,137,52,149,76,111,102,84,118,72,106,55,81,75,54,105" />
		        				<area class="area_zoom" data-zoom="4" shape="poly" alt="4배" title="4배" coords="34,139,53,149,39,179,31,210,29,224,9,219,13,193,25,160" />
		        				<area class="area_size" data-next-size="300" shape="poly" alt="크기" title="크기" coords="33,372,58,358,75,382,96,407,76,428,54,405,37,382" />
		        			</map>
		        		</div>
	        		</div>
				</div>
				
				<!-- x-ray map -->
				<div id="div_xray">
					<div class="xray_relative">
	        			<div id="xray_map"></div>
		        		<div class="xray_img_container">
		        			<img class="xray_img" src="<c:url value='/images/kworks/map/xray/x_ray_300_1.png' />" usemap="#map_xray_300" alt="X-Ray" />
		        			<map id="map_xray_300" name="map_xray_300">
		        				<area class="area_next" shape="poly" alt="시설물" title="시설물" coords="98,265,131,273,172,273,200,264,209,283,171,294,143,294,112,288,90,282" />
		        				<area class="area_size" data-next-size="400" shape="poly" alt="크기" title="크기" coords="16,216,41,204,57,226,71,240,81,249,62,270,39,251,25,233" />
		        			</map>
		        			<map id="map_xray_400" name="map_xray_400">
		        				<area class="area_next" shape="poly" alt="시설물" title="시설물" coords="142,366,175,372,210,375,254,367,263,387,237,394,177,392,149,389,138,386" />
		        				<area class="area_size" data-next-size="500" shape="poly" alt="크기" title="크기" coords="27,300,52,287,73,314,89,330,96,335,73,354,55,337,37,316" />
		        			</map>
		        			<map id="map_xray_500" name="map_xray_500">
		        				<area class="area_next" shape="poly" alt="시설물" title="시설물" coords="180,464,219,472,266,475,303,470,323,465,329,482,293,491,256,493,213,490,175,481" />
		        				<area class="area_size" data-next-size="300" shape="poly" alt="크기" title="크기" coords="31,369,59,355,77,384,91,402,99,407,75,428,57,409,38,384" />
		        			</map>
		        		</div>
	        		</div>
				</div>
				
				<!-- index map -->
				<div id="div_index_container" class="display_n bd_gray2">
					<div style="position:relative;width:100%;height:100%;">
						<div id="index_map"></div>
						<div class="div_index_mode">
							<a id="a_index_mode" href="#" data-mode="MAX" ><img class="w_32 h_32" src="<c:url value='/images/kworks/map/common/index_extents_on.png' />" alt="인덱스맵모드변경" title="인덱스맵모드변경" /></a>
						</div>
						<div class="div_index_close">
							<a id="a_index_close" href="#"><img src="<c:url value='/images/kworks/main/map/index/index_close_right_off.png' />" alt="인덱스맵닫기" title="인덱스맵닫기" /></a>
						</div>
					</div>
				</div>
				<div id="div_index_open">
					<a id="a_index_open" href="#"><img src="<c:url value='/images/kworks/main/map/index/index_open_right_off.png' />" alt="인덱스맵 열기" title="인덱스맵 열기" /></a>
				</div>
				<!-- //index map -->
				
				<!-- road view  -->
				<div id="roadview_container" class="bd_gray2"></div>
				<div id="roadview_mode">
					<a id="a_roadview_mode" href="#">
						<img src="<c:url value='/images/kworks/main/map/index/index_open_right_off.png' />"  alt="지도 열기/닫기" title="지도 열기/닫기" />
					</a>
				</div>
			
				<!-- 결과 창 -->
				<div id="div_result" class="easyui-window" data-options="title:'검색 결과 목록 현황',height:400,collapsible:false,minimizable:false,draggable:false,resizeable:false,inline:true,closed:true,onClose:function(){resultObj.clear();}">
					<div class="easyui-layout div_result_layout" data-options="fit:true" style="width:100%;height:310px;">
						<div data-options="region:'west',title:'검색 결과 목록'" style="width:300px;">
							<table class="grid_result_layers"></table>
						</div>
						<div class="div_result_center" data-options="region:'center'">
							<div class="div_result_menu" style="display:block;height:60px;">
								<div class="f_l">
									<a href="#" class="a_setting_item" >
										<img class="img_ov_off" src="<c:url value='/images/kworks/map/common/btn_slide_set_off.png' />" alt="항목설정" />
									</a> 
									<a href="#" class="a_addition_choice" >
										<img class="img_ov_off" src="<c:url value='/images/kworks/map/common/btn_slide_select_off.png' />" alt="추가선택" />
									</a>
									<a href="#" class="a_remove_rows">
										<img class="img_ov_off" src="<c:url value='/images/kworks/map/common/btn_slide_delete_off.png' />" alt="선택제거" />
									</a>
									<a href="#" class="a_search_condition">
										<img class="img_ov_off" src="<c:url value='/images/kworks/map/common/btn_slide_search3_off.png' />" alt="조건검색" />
									</a>
									<a href="#" class="a_search_relation">
										<img class="img_ov_off" src="<c:url value='/images/kworks/map/common/btn_slide_search2_off.png' />" alt="연관검색" />
									</a>
								</div>
								<div class="f_r">
									<a href="#" class="a_move_location">
										<img class="img_ov_off" src="<c:url value='/images/kworks/map/common/btn_slide_position_off.png' />" alt="목록위치" />
									</a>
									<a href="#" class="a_corporation_location">
										<img class="img_ov_off" src="<c:url value='/images/kworks/map/common/btn_slide_construction_position_off.png' />" alt="공사위치" />
									</a>
									<a href="#" class="a_search_register">
 										<img class="img_ov_off" src="<c:url value='/images/kworks/map/common/btn_slide_list_off.png' />" alt="대장조회" />
									</a>
									<a href="#" class="a_batchPrint_ctrlpnt" style="display:none;">
 										<img class="img_ov_off" src="<c:url value='/images/kworks/map/common/btn_slide_print_off.png' />" alt="점의조서 일괄출력" />
									</a>
									<a href="#" class="a_export_excel">
										<img class="img_ov_off" src="<c:url value='/images/kworks/map/common/btn_slide_send1_off.png' />" alt="속성내보내기" />
									</a>
									<a href="#" class="a_export_shape">
										<img class="img_ov_off" src="<c:url value='/images/kworks/map/common/btn_slide_send2_off.png' />" alt="공간내보내기" />
									</a>
									<a href="#" class="a_export_ldConsPs" style="display:none;">
										<img class="img_ov_off" src="<c:url value='/images/kworks/map/common/btn_slide_send2_off.png' />" alt="공사위치내보내기" />
									</a>
									<a href="#" class="a_export_fix">
										<img class="img_ov_off" src="<c:url value='/images/kworks/map/common/btn_slide_map_off.png' />" alt="지도고정" />
									</a>
								</div>
							</div>
							<div class="div_result_detail">
								<table class="grid_result_detail"></table>
								<div class="grid_result_pagination"></div>
							</div>
						</div>
					</div>
				</div>

				<c:if test="${ prjCode eq 'gn' }">
					<!-- 도로대장 검색 결과 창 -->
					<div id="div_road_reg_result" class="easyui-window" data-options="title:'검색 결과 목록 현황',height:400,collapsible:false,minimizable:false,draggable:false,resizeable:false,inline:true,closed:true,onClose:function(){roadRegResultObj.clear();}">
						<div class="easyui-layout div_result_layout" data-options="fit:true" style="width:100%;height:310px;">
							<div data-options="region:'west',title:'검색 결과 목록'" style="width:300px;">
								<table class="grid_result_layers"></table>
							</div>
							<div class="div_result_center" data-options="region:'center'">
								<div class="div_result_menu" style="display:block;height:60px;">
									<div class="f_l">
										<a href="#" class="a_setting_item" >
											<img class="img_ov_off" src="<c:url value='/images/kworks/map/common/btn_slide_set_off.png' />" alt="항목설정" />
										</a> 
									</div>
									<div class="f_r">
										<a href="#" class="a_move_location">
											<img class="img_ov_off" src="<c:url value='/images/kworks/map/common/btn_slide_position_off.png' />" alt="목록위치" />
										</a>
										<a href="#" class="a_search_register">
	 										<img class="img_ov_off" src="<c:url value='/images/kworks/map/common/btn_slide_list_off.png' />" alt="대장조회" />
										</a>
										<a href="#" class="a_export_fix">
											<img class="img_ov_off" src="<c:url value='/images/kworks/map/common/btn_slide_map_off.png' />" alt="지도고정" />
										</a>
									</div>
								</div>
								<div class="div_result_detail">
									<table class="grid_result_detail"></table>
									<div class="grid_result_pagination"></div>
								</div>
							</div>
						</div>
					</div>
	
					<!-- 도로대장 영상조회 결과 창 -->
					<div id="div_road_video_result" class="easyui-window" data-options="title:'영상 목록 현황',height:300,collapsible:false,minimizable:false,draggable:false,resizeable:false,inline:true,closed:true,onClose:function(){roadVideoResultObj.clear();}">
						<div class="easyui-layout div_result_layout" data-options="fit:true" style="width:100%;height:310px;">
							<div class="road_video" data-options="region:'west',title:'영상'" style="width:400px;">
								<video id="road_video_view" width="360" height="200" autoplay="autoplay">
									<source type="video/mp4" />
								</video>
							</div>
							<div class="div_result_center" data-options="region:'center'">
								<div class="div_result_menu" style="display:block;height:60px;">
									<div class="f_l">
										<a href="#" class="a_setting_item" >
											<img class="img_ov_off" src="<c:url value='/images/kworks/map/common/btn_slide_set_off.png' />" alt="항목설정" />
										</a> 
									</div>
									<div class="f_r">
										<a href="#" class="a_export_video">
											<img class="img_ov_off" src="<c:url value='/images/kworks/map/common/btn_slide_send3_off.png' />" alt="영상내보내기" />
										</a>
									</div>
								</div>
								<div class="div_result_detail">
									<table class="grid_result_detail"></table>
									<div class="grid_result_pagination"></div>
								</div>
							</div>
						</div>
					</div>
				</c:if>
								
				<!-- 횡단면도 -->
				<div id="div_cross_sectional_result" class="easyui-window" data-options="title:'횡단면도 분석 결과',height:380,maximizable:false,minimizable:false,closed:true,onClose:function(){ crossSectionalResultObj.clear(); }">
					<div class="div_layout easyui-layout" data-options="fit:true" style="width:100%;height:500px;overflow:hidden;">
						<div class="div_attr" data-options="region:'west',title:'시설물명',collapsible:false" style="width:300px;">
							<table class="grid_attribute"></table>
						</div>
						<div data-options="region:'center'" style="padding:10px;">
							<div class="boardTit" style="display:block;height:60px;">
								<div class="f_r">
									<a class="a_export_image" href="#" ><img class="pa_r_3 bd_r_c2" src="<c:url value='/images/kworks/map/common/btn_slide_send2_off.png' />" alt="횡단면도이미지저장" /></a>
									<a class="a_export_excel" href="#" ><img src="<c:url value='/images/kworks/map/common/btn_slide_send1_off.png' />" alt="엑셀저장" /></a>
								</div>
							</div>
							<!-- div 와 svg 사이에 공백 두지 말것 -->
							<div class="div_cross" style="display:block;width:100%;height:250px;overflow:hidden;"><svg id="svg_cross"></svg></div>
						</div>
					</div>
				</div>
				
				<!-- 변화 탐지  -->
				<div id="div_change_detection_result" class="easyui-window" data-options="title:'변화 탐지 검색 결과',height:400,collapsible:false,minimizable:false,draggable:false,resizeable:false,inline:true,closed:true,onClose:function(){ changeDetectionObj.clear(); }">
					<div class="div_change_detection_result_layout easyui-layout" data-options="fit:true" style="width:100%;height:600px;overflow:hidden;">
						<div class="div_change_detection_details" data-options="region:'west',collapsible:false" style="width:300px;">
							<input id="chngeDetctNo" type="hidden" value="" />
							<div class="easyui-panel" title="변화탐지 제목" style="height:100px;">
								<p class="changeDetectionDetailsSj" style="font-family:맑은고딕;"></p>
							</div>
							<div class="easyui-panel" title="변화탐지 개요" style="height:229px;">
								<p class="changeDetectionDetailsSy" style="font-family:맑은고딕;"></p>
							</div>
							<div class="div_change_detection_details_tool" style="padding:5px 5px 5px 77px; position:absolute; bottom:0; width:100%;">
								<a href="#" class="a_print_change_detection_details easyui-linkbutton" style="width:70px; border:1px solid #D8D8D8;">출력</a>
								<a href="#" class="a_modify_change_detection_details easyui-linkbutton" style="width:70px; border:1px solid #D8D8D8;">수정</a>
								<a href="#" class="a_remove_change_detection_details easyui-linkbutton" style="width:70px; border:1px solid #D8D8D8;">삭제</a>
							</div>
						</div>
						<div class="div_change_detection_area" data-options="region:'center',title:'변화 탐지 지역',collapsible:false" style="padding:10px;">
							<table class="grid_change_detection_area"></table>
							<div class="div_change_detection_area_tool" style="padding:5px 0 0 0; text-align:right;">
								<a href="#" class="a_detail_change_detection_area easyui-linkbutton" data-options="iconCls:'icon-search'" style="width:20%; border:1px solid #D8D8D8;">상세조회</a>
								<a href="#" class="a_add_change_detection_area easyui-linkbutton" data-options="iconCls:'icon-add'" style="width:20%; border:1px solid #D8D8D8;">지역추가</a>
							</div>
						</div>
					</div>
				</div>
				
				<c:if test="${ prjCode eq 'gn' }">
					<!-- 점용허가대장 검색 결과 창 -->
					<div id="div_ocpat_reg_result" class="easyui-window" data-options="title:'검색 결과 목록 현황',height:400,collapsible:false,minimizable:false,draggable:false,resizeable:false,inline:true,closed:true,onClose:function(){ocpatRegResultObj.clear();}">
						<div class="easyui-layout div_result_layout" data-options="fit:true" style="width:100%;height:310px;">
							<div data-options="region:'west',title:'검색 결과 목록'" style="width:300px;">
								<table class="grid_result_layers"></table>
							</div>
							<div class="div_result_center" data-options="region:'center'">
								<div class="div_result_menu" style="display:block;height:60px;">
									<div class="f_l">
										<a href="#" class="a_setting_item" >
											<img class="img_ov_off" src="<c:url value='/images/kworks/map/common/btn_slide_set_off.png' />" alt="항목설정" />
										</a> 
									</div>
									<div class="f_r">
										<a href="#" class="a_move_location">
											<img class="img_ov_off" src="<c:url value='/images/kworks/map/common/btn_slide_position_off.png' />" alt="목록위치" />
										</a>
										<a href="#" class="a_search_register">
	 										<img class="img_ov_off" src="<c:url value='/images/kworks/map/common/btn_slide_list_off.png' />" alt="대장조회" />
										</a>
										<a href="#" class="a_batchPrint_ocpat">
	 										 <img class="img_ov_off" src="<c:url value='/images/kworks/map/common/btn_ocpat_print_off.png' />" alt="대장 출력" /> 
										</a>
										<a href="#" class="a_export_fix">
											<img class="img_ov_off" src="<c:url value='/images/kworks/map/common/btn_slide_map_off.png' />" alt="지도고정" />
										</a>
									</div>
								</div>
								<div class="div_result_detail">
									<table class="grid_result_detail"></table>
									<div class="grid_result_pagination"></div>
								</div>
							</div>
						</div>
					</div>
				</c:if>
				
				<!-- 지형종단면도 -->
				<c:if test="${isTopographicMap eq 'true'}">
					<div id="div_topographic_section_result" class="easyui-window" data-options="title:'지형단면도 분석 결과',height:380,maximizable:false,minimizable:false,closed:true,onClose:function(){ topographicSectionResultObj.clear(); }">
						<div class="div_layout easyui-layout" data-options="fit:true" style="width:100%;height:500px;overflow:hidden;">
							<div data-options="region:'center'" style="padding:10px;">
								<div class="boardTit" style="display:block;height:60px;">
									<div class="f_r">
										<a class="a_export_image" href="#" ><img class="pa_r_3 bd_r_c2" src="<c:url value='/images/kworks/map/common/btn_slide_send2_off.png' />" alt="종단면도이미지저장" /></a>
										<a class="a_export_excel" href="#" ><img src="<c:url value='/images/kworks/map/common/btn_slide_send1_off.png' />" alt="엑셀저장" /></a>
									</div>
								</div>
								<!-- div 와 svg 사이에 공백 두지 말것 -->
								<div class="div_profile" style="display:block;width:100%;height:250px;overflow:hidden;"><svg id="svg_profile"></svg></div>
							</div>
						</div>
					</div>
				</c:if>				
				
				<div id="div_policy_reg_result" class="easyui-window" data-options="title:'검색 결과 목록 현황',height:400,collapsible:false,minimizable:false,draggable:false,resizeable:false,inline:true,closed:true,onClose:function(){policyRegResultObj.clear();}">
					<div class="easyui-layout div_result_layout" data-options="fit:true" style="width:100%;height:310px;">
						<div data-options="region:'west',title:'검색 결과 목록'" style="width:300px;">
							<table class="grid_result_layers"></table>
						</div>
						<div class="div_result_center" data-options="region:'center'">
							<div class="div_result_menu" style="display:block;height:60px;">
								<div class="f_l">
									<a href="#" class="a_setting_item" >
										<img class="img_ov_off" src="<c:url value='/images/kworks/map/common/btn_slide_set_off.png' />" alt="항목설정" />
									</a> 
								</div>
								<div class="f_r">
									<a href="#" class="a_move_location">
										<img class="img_ov_off" src="<c:url value='/images/kworks/map/common/btn_slide_position_off.png' />" alt="목록위치" />
									</a>
									<a href="#" class="a_search_register">
 										<img class="img_ov_off" src="<c:url value='/images/kworks/map/common/btn_slide_list_off.png' />" alt="대장조회" />
									</a>
									<a href="#" class="a_export_fix">
										<img class="img_ov_off" src="<c:url value='/images/kworks/map/common/btn_slide_map_off.png' />" alt="지도고정" />
									</a>
								</div>
							</div>
							<div class="div_result_detail">
								<table class="grid_result_detail"></table>
								<div class="grid_result_pagination"></div>
							</div>
						</div>
					</div>
				</div>
				
				<c:if test="${ prjCode eq 'dh' and isCityPlanRoad eq true}">
					<!-- 동해시 도시계획도로 검색 결과 창 -->
					<div id="div_ropl_reg_result" class="easyui-window" data-options="title:'검색 결과 목록 현황',height:400,collapsible:false,minimizable:false,draggable:false,resizeable:false,inline:true,closed:true,onClose:function(){dhRoplResultObj.clear();}">
						<div class="easyui-layout div_result_layout" data-options="fit:true" style="width:100%;height:310px;">
							<div data-options="region:'west',title:'검색 결과 목록'" style="width:300px;">
								<table class="grid_result_layers"></table>
							</div>
							<div class="div_result_center" data-options="region:'center'">
								<div class="div_result_menu" style="display:block;height:60px;">
									<div class="f_l">
										<a href="#" class="a_setting_item" >
											<img class="img_ov_off" src="<c:url value='/images/kworks/map/common/btn_slide_set_off.png' />" alt="항목설정" />
										</a> 
									</div>
									<div class="f_r">
										<a href="#" class="a_move_location">
											<img class="img_ov_off" src="<c:url value='/images/kworks/map/common/btn_slide_position_off.png' />" alt="목록위치" />
										</a>
										<a href="#" class="a_search_register">
	 										<img class="img_ov_off" src="<c:url value='/images/kworks/map/common/btn_slide_list_off.png' />" alt="대장조회" />
										</a>
										<a href="#" class="a_print_ropl">
	 										 <img class="img_ov_off" src="<c:url value='/images/kworks/map/common/btn_ropl_print_off.png' />" alt="대장출력" /> 
										</a>
										<a href="#" class="a_export_excel">
											<img class="img_ov_off" src="<c:url value='/images/kworks/map/common/btn_slide_send1_off.png' />" alt="엑셀내보내기" />
										</a>
										<a href="#" class="a_export_fix">
											<img class="img_ov_off" src="<c:url value='/images/kworks/map/common/btn_slide_map_off.png' />" alt="지도고정" />
										</a>
									</div>
								</div>
								<div class="div_result_detail">
									<table class="grid_result_detail"></table>
									<div class="grid_result_pagination"></div>
								</div>
							</div>
						</div>
					</div>
				</c:if>
			</div>
			<!-- 지도 끝 -->
			
		</div>
		<!-- 메인 영역 끝 -->
		
	</div>
	
	<!-- result excel download -->
 	<form id="frmExcelFile" action="<c:url value='/excel/exportFromJson.do' />" method="POST">
 		<input type="hidden" name="data" value="" />
 	</form>
 	
 	<!-- result excel download -->
 	<form id="frmExcelFileFromData" action="<c:url value='/excel/exportFromData.do' />" method="POST">
 		<input type="hidden" name="data" value="" />
 	</form>
	
	<!-- download base64 image -->
    <form id="frmDownloadBase64Image" action="<c:url value='/download/base64Image.do' />" method="POST" >
       <input type="hidden" name="data" value="" />
    </form>
    
    <c:if test="${ prjCode eq 'gn' or prjCode eq 'yy' or prjCode eq 'yg'}">
    	<form id="frmEcologyNatureMapExcel" action="<c:url value='/ecologyNatureMap/excel.do' />" method="POST">
	 		<input type="hidden" name="dataId" value="" />
	 		<input type="hidden" name="pnu" value="" />
	 		<input type="hidden" name="data" value="" />
	 	</form>
	 	
		<!-- download road video -->
	    <form id="frmRoadVideoDownload" method="GET" >
	       <input type="hidden" name="data" value="" />
	    </form>
	 	
	</c:if>
    
    <c:if test="${prjCode eq 'ss' and envType eq 'oper' and userName ne '관리자'}">
		<script type="text/javascript" src="<c:url value='/lib/fsw/fsw.js' />"></script>
	</c:if>
    
</body>
