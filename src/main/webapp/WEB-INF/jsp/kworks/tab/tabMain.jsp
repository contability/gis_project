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
		<c:set var="isTopographicMap"><spring:message code='Extensions.TopographicMap.UseAt' text='false' /></c:set> <!-- ???????????? ?????? -->
		<c:set var="isCadastralMap"><spring:message code='Extensions.CadastralMap.UseAt' text='false' /></c:set> <!-- ???????????? & ???????????? ?????? -->
		<c:set var="isLgstrStats"><spring:message code='Extensions.LandRgisterStatistics.UseAt' text='false' /></c:set> <!-- ???????????? ?????? -->
		<c:set var="isCityPlanRoad"><spring:message code='Extensions.CityPlanRoad.UseAt' text='false' /></c:set> <!-- ????????? ?????????????????? ?????? -->
		<c:set var="isLgstrBiz"><spring:message code='Extensions.LgstrBiz.UseAt' text='false'/></c:set> <!-- ????????? ???????????? ?????? -->
		<!-- favicon -->
		<link rel="shortcut icon" href="<c:url value='/favicon.ico'/>" />
		<link rel="icon" type="image/png" sizes="32x32" href="<c:url value='/images/kworks/favicon-32x32.png'/>">
		<link rel="icon" type="image/png" sizes="16x16" href="<c:url value='/images/kworks/favicon-16x16.png'/>">
		
		<!-- easyui -->
		<link rel="stylesheet" type="text/css" href="<c:url value='/lib/jquery/easyui/themes/icon.css' />" />
		<link rel="stylesheet" type="text/css" href="<c:url value='/lib/jquery/easyui/themes/bootstrap/easyui.css' />" />
		
		<!-- ?????? -->
		<link rel="stylesheet" href="<c:url value='/lib/venobox/venobox.css' />" type="text/css">	
		
		<!-- colorpicker -->
		<link rel="stylesheet" type="text/css" href="<c:url value='/lib/colorpicker/css/layout.css' />" />
		<link rel="stylesheet" type="text/css" href="<c:url value='/lib/colorpicker/css/colorpicker.css' />" />
		
		<!-- ol3 -->
		<link rel="stylesheet" href="<c:url value='/webjars/openlayers/3.13.0/ol.css' />" type="text/css">
		
		<!-- kworks -->
		<link rel="stylesheet" href="<c:url value='/lib/kworks/css/kworks.css' />" type="text/css">
		<link type="text/css" rel="stylesheet" href="<c:url value='/css/kworks/tab/tabLayout_map.css' />" />
		
		<!-- window -->
		<link type="text/css" rel="stylesheet" href="<c:url value='/css/kworks/window/window.css' />" />
		
		<!-- import javascript -->
		<script type="text/javascript">
			// ???????????? ??????
			var PROJECT_CODE = "<spring:message code='Globals.Prj' />";
			var ENV_TYPE = "<spring:message code='Globals.EnvType' />";
			// ???????????? ??????
			var CONTEXT_PATH = "${pageContext.request.contextPath}";
			var FULL_PATH  = '${pageContext.request.scheme}' + '://' + '${pageContext.request.serverName}' + ':' + '${pageContext.request.serverPort}' + CONTEXT_PATH;
			var COM = {
				CITY_NAME : '<spring:message code='Com.CityName' />',
				DEFAULT_CASE : '<spring:message code='Com.DefaultCase' />'
			};
						// Lks : ??????/?????? ??????
			var UPWID_EDIT = {
				UseAt : '<spring:message code='Extensions.EditUpwid.UseAt' />',
				Layers : '<spring:message code='Extensions.EditUpwid.Layers' />'
			};
			
			// ??????
			var MAP = {
				// ?????? ?????? ??????
				SERVER_TYPE : "<spring:message code='Map.ServerType' />",
				// ?????????
				PREFIX : "<spring:message code='Map.Prefix' />",
				// ?????? ?????????
				PROJECTION : "<spring:message code='Map.Projection' />",
				// ???????????? ?????? ???
				GEOMETRY_ATTR_NAME : "<spring:message code='Map.GeometryName' />".toLowerCase(),
				// TMS ??????
				TMS_URL : "<spring:message code='Map.Url.Tms' />",
				// ?????? ?????? ??????
				SYMBOL_URL : "<spring:message code='Map.Url.Symbol' />",
				// ?????? ?????? ??????
				INIT_EXTENT : "<spring:message code='Map.InitExtent' />"
			};
			
			// ????????? ?????? 
			var INDEX_MAP = {
				// ?????????
				LAYER : "<spring:message code='Map.Index.Layer' />",
				// ?????????
				MAX_RESOLUTION : <spring:message code='Map.Index.MaxResolution' />,
				// ?????? X
				CENTER_X : <spring:message code='Map.Index.Center.X' />,
				// ?????? Y
				CENTER_Y : <spring:message code='Map.Index.Center.Y' />
			};
			
			// ????????? ?????????
			var SYS_ID = <c:out value='${sys.sysId}' />;
			
			// ????????? ?????????
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
			
			// ???????????? ??????		
			var IS_TOPOGRAPHICMAP = "<spring:message code='Extensions.TopographicMap.UseAt' text='false'/>";
			var TOPOGRAPHIC_ICON_URL = "<spring:message code='Map.Url.DEM' text='/'/>";
			
			// ??????????????????
			var IS_CITY_PLAN_ROAD = "<spring:message code='Extensions.CityPlanRoad.UseAt' text='false'/>";
			var CITY_PLAN_ROAD_SPATIAL = "<spring:message code='Extension.CityPlanRoad.Spatial' text=''/>";
			var CITY_PLAN_ROAD_SPATIAL_FIELD = "<spring:message code='Extension.CityPlanRoad.Spatial.Field' text=''/>";
			
			// ???????????? ??????		
			var IS_CADASTRALMAP = "<spring:message code='Extensions.CadastralMap.UseAt' text='false'/>";
			var CADASTRALMAP_SCALE = "<spring:message code='Extensions.CadastralMap.Scale' text='5000'/>";
			var CADASTRALMAP_OPACITY = "<spring:message code='Extensions.CadastralMap.Opacity' text='0'/>";
			var CADASTRALMAP_DATAID = "<spring:message code='Extensions.CadastralMap.DataId' text=''/>";
			
			// ???????????? ??????
			var IS_LANDREGISTERSTATISTICS = "<spring:message code='Extensions.LandRgisterStatistics.UseAt' text='false'/>";
			
			// ???????????? ??????
			var IS_LGSTRBIZ = "<spring:message code='Extensions.LgstrBiz.UseAt' text='false'/>";
		</script>
		<!-- jquery & jeasy-ui -->
		<script src="<c:url value='/webjars/jquery/2.1.4/jquery.min.js' />" type="text/javascript"></script>
		<script src="<c:url value='/lib/jquery/cookie/jquery.cookie.js' />" type="text/javascript"></script>
		<script src="<c:url value='/lib/jquery/form/jquery.form.min.js' />" type="text/javascript"></script>
		<script src="<c:url value='/lib/jquery/easyui/jquery.easyui.min.js' />" type="text/javascript"></script>
		
		<!-- ?????? -->
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
		<c:if test="${envType eq 'oper' and userName ne '?????????' }">
			<!-- daum api-> kakao daum api -->
			<script src="http://dapi.kakao.com/v2/maps/sdk.js?appkey=<spring:message code='Contact.Daum.Key' />" type="text/javascript"></script>
		</c:if>
		
		<!-- ?????? ??????????????????  ?????? -->
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
		<script src="<c:url value='/js/kworks/main/userEnvironment.js' />" type="text/javascript"></script>
		<script src="<c:url value='/js/kworks/tab/tabMap.js' />" type="text/javascript"></script>
		<script src="<c:url value='/js/kworks/main/multi.js' />" type="text/javascript"></script>
		<script src="<c:url value='/js/kworks/main/pnu.js' />" type="text/javascript"></script>
		<script src="<c:url value='/js/kworks/main/result.js' />" type="text/javascript"></script>
		<script src="<c:url value='/js/kworks/main/crossSectionalResult.js' />" type="text/javascript"></script>
		
		<script src="<c:url value='/js/kworks/window/window.js' />" type="text/javascript"></script>
		<script src="<c:url value='/js/kworks/tab/tab.js' />" type="text/javascript"></script>
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
			
		<!-- ???????????? -->
		<script src="<c:url value='/js/kworks/window/result/additionChoice.js' />" type="text/javascript"></script>
		<script src="<c:url value='/js/kworks/window/result/conditionSearch.js' />" type="text/javascript"></script>
		<script src="<c:url value='/js/kworks/window/result/itemSetup.js' />" type="text/javascript"></script>
		<script src="<c:url value='/js/kworks/window/result/relationSearch.js' />" type="text/javascript"></script>
		
		<script src="<c:url value='/js/kworks/tab/tabMain.js' />" type="text/javascript"></script>
	 	<script src="<c:url value='/js/kworks/main/menu/job.js' />" type="text/javascript"></script>
		<script src="<c:url value='/js/kworks/main/menu/search.js' />" type="text/javascript"></script>
		<script src="<c:url value='/js/kworks/main/menu/backgroundMap.js' />" type="text/javascript"></script>
		<script src="<c:url value='/js/kworks/main/menu/themamap.js' />" type="text/javascript"></script>
		<script src="<c:url value='/js/kworks/main/menu/layer.js' />" type="text/javascript"></script>
		<script src="<c:url value='/js/kworks/main/menu/file.js' />" type="text/javascript"></script>
		<script src="<c:url value='/js/kworks/main/menu/output.js' />" type="text/javascript"></script>
		<script src="<c:url value='/js/kworks/main/menu/bookMark.js' />" type="text/javascript"></script>
		
		<script src="<c:url value='/js/${prjCode}/job/job.js' />" type="text/javascript"></script>
		<script src="<c:url value='/js/${prjCode}/main/custom.js' />" type="text/javascript"></script>
		<!-- ?????? ??????????????????  ??? -->
		
		<!-- ???????????? ?????? -->
		<c:if test="${isTopographicMap eq 'true'}">
			<script src="<c:url value='/js/kworks/gis/topographicObj.js' />" type="text/javascript"></script>
			<script src="<c:url value='/js/kworks/gis/topographicSectionView.js' />" type="text/javascript"></script>
			<script src="<c:url value='/js/kworks/main/menu/topographic.js' />" type="text/javascript"></script>
			<script src="<c:url value='/js/kworks/main/topographicSectionResult.js' />" type="text/javascript"></script>
			<script src="<c:url value='/js/kworks/window/analysis/topographicSectionSelect.js' />" type="text/javascript"></script>
		</c:if>
		
		<c:if test="${ prjCode eq 'dh' }">
		<!-- ????????? -->
			<!-- 306vr -->
			<script src="<c:url value='/js/${prjCode}/job/panoramaVR/panoramaVR.js' />" type="text/javascript"></script>
			<!-- ???????????? -->
			<script src="<c:url value='/js/${prjCode}/job/cmmnProp/cmmnProp.js' />" type="text/javascript"></script>
		</c:if>
		
		<c:if test="${prjCode eq 'gn' }">
			<!-- ???????????? -->
			<script type="text/javascript" src="<c:url value='/js/${prjCode }/job/cmmnProp/cmmnProp.js'/>"></script>
			<script type="text/javascript" src="<c:url value='/js/${prjCode }/job/cmmnProp/loanDrowTool.js'/>"></script>
			<script type="text/javascript" src="<c:url value='/js/${prjCode }/job/cmmnProp/oousDrowTool.js'/>"></script>
			<script type="text/javascript" src="<c:url value='/js/${prjCode }/job/cmmnProp/highlight.js'/>"></script>
			<script type="text/javascript" src="<c:url value='/js/${prjCode }/job/cmmnProp/oousHighlight.js'/>"></script>
		</c:if>
		
		<!-- Css  ?????? -->
		
		<link type="text/css" rel="stylesheet" href="<c:url value='/css/kworks/common/reset.css' />" />
		<link type="text/css" rel="stylesheet" href="<c:url value='/css/kworks/common/common.css' />" />
		<link type="text/css" rel="stylesheet" href="<c:url value='/css/kworks/tab/tabMain.css' />" />
		<link type="text/css" rel="stylesheet" href="<c:url value='/css/kworks/common/button.css' />" />
		
		<!-- Css  ??? -->
		
		<title>${cityNm} ????????? ???????????????????????? ?????????</title>
	</head>
	<body>
		<input id="hid_user_name" type="hidden" value="<c:out value='${userName}' />" />
		<input id="hid_dept_name" type="hidden" value="<c:out value='${deptName}' />" />
		<div id="wrap">
			<div id="header">
				<!-- ?????? -->
				<div class="logo ma_t_10 ma_l_20">
					<a href="<c:url value='/' />" ><img alt="${cityNm}" title="${cityNm}" src="<c:url value='/images/${prjCode}/common/logo-min.png' />" /></a>
				</div>
				<!-- ?????? -->
				
				<!-- ????????? -->
				<div class="systemTitle ma_t_9 ma_l_20">
					<c:if test="${sys.sysTy eq 'SYSTEM'}">
						<img alt="<c:out value='${sys.sysAlias}' />" title="<c:out value='${sys.sysAlias}' />" src="<c:url value='/images/${prjCode}/main/sys/sys_${sys.sysNm}.png' />" />
					</c:if>
					<c:if test="${sys.sysTy eq 'USER'}">
						<img class="img_user_system" alt="<c:out value='${sys.sysAlias}' />" title="<c:out value='${sys.sysAlias}' />" src="<c:url value='/images/${prjCode}/main/sys/sys.png' />" />
						<span class="span_user_system"><c:out value='${sys.sysAlias}' /></span>
					</c:if>
				</div>
				<!-- ?????????-->
				
			<c:if test="${ prjCode eq 'dh'}">
				<!-- ????????? ?????? ???????????? -->
				<div class="gnb div_warnings color_orange">
						<span class="ma_r_10">???</span>???????????? ?????? ???????????? ??????
				</div>
			</c:if>
				
				<!-- ????????? / ???????????? -->
				<div class="gnb div_logout clearfix">
					<div class="color_3 ma_t_4 f_s_14">
						<span>
							<strong><c:out value='${userName}' /></strong> 
							??? ???????????????.<c:out value='${resultHadm}' />
						</span>
					</div>
					<div class="gnb_div ma_t_3">
						<div class="gnb_img">
							<a href="<c:url value='/j_spring_security_logout' />" class="btn_logout" title="????????????" ></a>
						</div>
						<div class="gnb_img">
							<a id="a_open_settings" href="#">
								<img src="<c:url value='/images/kworks/map/svg/topTools/ic_set.png' />" alt="????????????" title="????????????" />
							</a>
						</div>
					</div>
				</div>
				<!-- ????????? / ???????????? -->
			
			</div><!-- header-->
			
			<!-- container -->
			<div id="container">
				<!-- ?????? ?????? ??? -->
				<div id="ul_map_toolbar_vertical">
				</div>
				<!-- ?????? ?????? ??? -->
				
				<!-- ????????? ?????? ??? -->
				<div id="div_multi_map" class="po_a bg_white" style="top:5px;right:50px;z-index:2000;display:none;">
					<div class="k_pop_box bd_sky w_106">
						<div class="pa_b_10 pa_t_10 bd_b_gray1">
							<a class="a_multi_map pa_l_20" href="#" data-mode="0">
								<img src="<c:url value='/images/kworks/map/skin2/square01_off.png'  />" alt="1???" title="1???" />
							</a>
						</div>
						<div class="pa_t_10 pa_b_10 bd_b_gray1">
							<a class="a_multi_map pa_l_20 pa_r_20" href="#" data-mode="1">
								<img src="<c:url value='/images/kworks/map/skin2/square02_off.png'/>" alt="2???(??????)" title="2???(??????)" />
							</a>
							<a href="#" class="a_multi_map" data-mode="2">
								<img src="<c:url value='/images/kworks/map/skin2/square03_off.png'  />" alt="2???(??????)" title="2???(??????)" />
							</a>
						</div>
						<div class="pa_t_10 pa_b_10 bd_b_gray1">
							<a class="a_multi_map pa_l_20 pa_r_20" href="#" data-mode="3">
								<img src="<c:url value='/images/kworks/map/skin2/square04_off.png'  />" alt="3???(???)" title="3???(???)" />
							</a>
							<a href="#" class="a_multi_map" data-mode="4">
								<img src="<c:url value='/images/kworks/map/skin2/square05_off.png'  />" alt="3???(???)" title="3???(???)" />
							</a>
						</div>
						<div class="pa_t_10 pa_b_10 bd_b_gray1">
							<a class="a_multi_map pa_l_20 pa_r_20" href="#" data-mode="5">
								<img src="<c:url value='/images/kworks/map/skin2/square06_off.png'  />" alt="3???(???)" title="3???(???)" />
							</a>
							<a href="#" class="a_multi_map" data-mode="6">
								<img src="<c:url value='/images/kworks/map/skin2/square07_off.png'  />" alt="3???(???)" title="3???(???)" />
							</a>
						</div>
						<div class="pa_t_10 pa_b_10">
							<a class="a_multi_map pa_l_20 pa_r_20" href="#" data-mode="7">
								<img src="<c:url value='/images/kworks/map/skin2/square08_off.png'  />" alt="4???" title="4???" />
							</a>
						</div>
					</div>
				</div>
				
				<!-- ????????????-->
				<div id="div_menu" class="aside">
					<div class="nav">
						<ul class="nav_lst">
							<li>
								<a id="a_menu_job" data-menu-id="job" href="#">
									<img src="<c:url value='/images/kworks/map/svg/left/nov01_off.svg' />" alt="????????????" title="????????????" />
								</a>
							</li>
							<li>
								<a id="a_menu_search" data-menu-id="search" href="#">
									<img src="<c:url value='/images/kworks/map/svg/left/nov05_off.svg' />" alt="??????" title="??????" />
								</a>
							</li>
							<li>
								<a id="a_menu_background_map" data-menu-id="backgroundMap" href="#">
									<img src="<c:url value='/images/kworks/map/svg/left/nov03_off.svg' />" alt="????????????" title="????????????" />
								</a>
							</li>
							<li>
								<a id="a_menu_themamap" data-menu-id="themamap" href="#">
									<img src="<c:url value='/images/kworks/map/svg/left/nov04_off.svg' />" alt="?????????" title="?????????" />
								</a>
							</li>
							<li>
								<a id="a_menu_layer" data-menu-id="layer" href="#">
									<img src="<c:url value='/images/kworks/map/svg/left/nov06_off.svg' />" alt="?????????" title="?????????" /> 
								</a>
							</li>
							<li>
								<a id="a_menu_file" data-menu-id="file" href="#">
									<img src="<c:url value='/images/kworks/map/svg/left/nov08_off.svg' />" alt="??????" title="??????" />
								</a>
							</li>
							<li>
								<a id="a_menu_output" data-menu-id="output" href="#">
									<img src="<c:url value='/images/kworks/map/svg/left/nov02_off.svg' />" alt="??????" title="??????" />
								</a>
							</li>
							<li>
								<a id="a_menu_bookmark" data-menu-id="bookMark" href="#">
									<img src="<c:url value='/images/kworks/map/svg/left/nov07_off.svg' />" alt="?????????" title="?????????" />
								</a>
							</li>
						</ul>
					</div>
				</div><!-- ????????????-->
				<!-- ?????? ?????? ?????? ?????? -->
				<div id="div_menu_panel">
				</div><!-- ?????? ?????? ?????? ??? -->
				
				<div id="map_container" class="div_map_container_tabs">
				<!-- <div id="map_container" class="div_map_container_tabs easyui-tabs" style="width:100%;height:100%;"> -->
					<div title="????????????">
						<!-- <div class="heder_snb h_50 ma_t_5 bg_sky"> -->
						<div class="heder_snb h_50 bg_sky">
							<div id="div_quick_search">
								<span class="div_quick_search_type">
									<span>
										<select class="sel_search_type">
											<option value="legalDong">?????????</option>
											<option value="roadName">?????????</option>
										</select>
									</span>
								</span>
								
								<!-- ?????? ?????? ?????? start -->
								<span id="div_quick_search_legal_dong" class="div_container_top_search snb_text color_white kw_toptoolbar_div_left_search kw_toptoolbar_div_sub">
									<span><select class="sel_dong"></select></span>	
									<span>
										<label for="div_quick_search_legal_dong_mntn">???</label>
										<input type="checkbox" class="chk_mntn" id="div_quick_search_legal_dong_mntn" />
									</span>
									<span>
										?????? <input type="text" class="txt_mnnm" />
									</span>
									-
									<span>
										<input type="text" class="txt_slno" />
									</span>
									<span>
										<a href="#" class="a_search">??????</a>
									</span>
								</span>
								<!-- ?????? ?????? ?????? end -->
								
								<!-- ?????? ????????? ?????? start -->
								<span id="div_quick_search_road_name">
									<span>
										<select class="sel_road_name"></select>
									</span>
									<span>
										<input type="text" class="txt_mnnm" />-
										<input type="text" class="txt_slno" />
									</span>
									<span>
										<a href="#" class="a_search easyui-linkbutton">??????</a>
									</span>
								</span>
								<!-- ?????? ????????? ?????? end -->
							</div>
							<!-- div_quick_search end -->
							<!-- ?????? ??? -->
							<ul id="ul_map_toolbar_on_off" class="toolbar ma_t_11 kw_toptoolbar_div_right">
								<li id="li_map_tool_line_on_off" class="li_line"><img src="<c:url value='/images/kworks/map/skin2/ic_set_line.png' />" /></li>
								<li id="li_map_tool_on" class=" display_n"><a class="kw_toptoolbar_a" id="a_map_tool_on" href="#"><img class="img_ov_off tootip" src="<c:url value='/images/kworks/map/svg/topTools/down_off.svg' />" alt="???????????????" title="???????????????" /></a></li>
								<li id="li_map_tool_off"><a class="kw_toptoolbar_a" id="a_map_tool_off" href="#"><img class="img_ov_off tootip" src="<c:url value='/images/kworks/map/svg/topTools/up_off.svg' />" alt="???????????????" title="???????????????" /></a></li>
								<%-- <li id="li_map_tool_on"><a class="kw_toptoolbar_a" id="a_map_tool_on" href="#"><img class="img_ov_off tootip" src="<c:url value='/images/kworks/map/svg/topTools/down_off.svg' />" alt="???????????????" title="???????????????" /></a></li>
								<li id="li_map_tool_off" class="display_n"><a class="kw_toptoolbar_a" id="a_map_tool_off" href="#"><img class="img_ov_off tootip" src="<c:url value='/images/kworks/map/svg/topTools/up_off.svg' />" alt="???????????????" title="???????????????" /></a></li> --%>
							</ul>
							<ul id="ul_map_toolbar" class="toolbar ma_t_11 kw_toptoolbar_div_right">
							</ul>
						</div>
						
						
						<div id="div_map_layer_tool_base" class="div_map_layer_tool">
							<span class="span_title">????????????</span>
							<select id="sel_map_layer_background_layer_base" class="sel_map_layer_background_layer">
							</select>
							<a href="#" id="a_land_register_base" class="a_land_register" data-index="0" >
								<img src="<c:url value='/images/kworks/map/svg/topTools/land_register_off.png' />" alt="??????" title="??????" />
							</a>
						</div>
						<div id="div_map" style="width:100%;height:100%;" ></div>
						<!-- <div id="div_map" style="width:99%;height:89.5%;top:9.8%;position:absolute;" ></div> -->
						<!-- <div id="div_map" style="width:100%;height:91.1%;top:9%;position:absolute;" > -->
						<!-- <div id="div_map" style="width:100%;height:100%;position:absolute;" >
							<div id="ul_map_toolbar_vertical">
							</div>
						</div> -->
						
						<div id="div_coordinate_container">
							<a id="a_kworks_coordinate"></a>
						</div> 
									<!-- ???????????? Comment -->
						<div id="div_description"></div>
						
						<!-- map_control -->
						<div class="map_control ma_t_10 ma_r_10" style="right:40px">
							<div class="pa_l_31 w_32 h_31">
								<a id="a_map_control_zoomin" href="#"><img src="<c:url value='/images/kworks/map/common/scale_zin.png' />" alt="??????" title="??????" /></a>
							</div>
							<div class="con_box h_31">
								<a id="a_map_control_prev" href="#"><img src="<c:url value='/images/kworks/map/common/scale_pre.png' />" alt="????????????" title="????????????" /></a>
								<a id="a_map_control_home" href="#"><img src="<c:url value='/images/kworks/map/common/scale_home.png' />" alt="??????" title="??????" /></a>
								<a id="a_map_control_next" href="#"><img src="<c:url value='/images/kworks/map/common/scale_next.png' />" alt="????????????" title="????????????" /></a>
							</div>
							<div class="pa_l_31 w_32 h_31">
								<a id="a_map_control_zoomout" href="#"><img src="<c:url value='/images/kworks/map/common/scale_zout.png' />" alt="??????" title="??????" /></a>
							</div>
							<div id="div_quick_menu">
								<a id="a_quick_menu_open" href="#" class="display_n" ><img src="<c:url value='/images/kworks/map/tool/quick_down.png' />" alt="Quick Open" title="Quick Open" /></a>
								<a id="a_quick_menu_close" href="#"><img src="<c:url value='/images/kworks/map/tool/quick_up.png' />" alt="Quick Close" title="Quick Close" /></a>
							</div>
							<div id="div_quick_menu_container">
							</div>
						</div>
						
						<!-- ???????????? ???????????? -->
 						<div class="moveAerialMap_control ma_t_10 ma_r_10">
							<div class="pa_l_31 w_32 h_31">
								<a id="a_moveAerialMap_control_up" href="#"><img src="<c:url value='/images/kworks/map/common/scale_up.png' />" alt="???" title="???" /></a>
							</div>
							<div class="con_box h_31">
								<a id="a_moveAerialMap_control_left" href="#"><img src="<c:url value='/images/kworks/map/common/scale_pre.png' />" alt="???" title="???" /></a>
								<a id="a_moveAerialMap_control_original" href="#"><img src="<c:url value='/images/kworks/map/common/scale_original.png' />" alt="????????????" title="????????????" /></a>
								<a id="a_moveAerialMap_control_right" href="#"><img src="<c:url value='/images/kworks/map/common/scale_next.png' />" alt="???" title="???" /></a>
							</div>
							<div class="pa_l_31 w_32 h_31">
								<a id="a_moveAerialMap_control_down" href="#"><img src="<c:url value='/images/kworks/map/common/scale_down.png' />" alt="???" title="???" /></a>
							</div>
							<div id="div_moveAerialMap_menu">
								<a id="a_moveAerialMap_control_mouse" href="#"><img class="tool_toggle_radio img_ov_off tootip" src="<c:url value='/images/kworks/map/common/scale_mouse_off.png' />" alt="???????????????" title="???????????????" /></a>
							</div>
						</div>
						
						<!-- zoom map -->
						<div id="div_zoom">
							<div class="zoom_relative">
			        			<div id="zoom_map"></div>
				        		<div class="zoom_img_container">
				        			<img class="zoom_img" src="<c:url value='/images/kworks/map/zoom/zoom_2x_300.png' />" usemap="#map_zoom_300" alt="?????????" />
				        			<map id="map_zoom_300" name="map_zoom_300">
				        				<area class="area_zoom" data-zoom="2" shape="poly" alt="2???" title="2???" coords="8,116,27,121,39,89,50,72,59,62,44,49,25,76,15,97" />
				        				<area class="area_zoom" data-zoom="4" shape="poly" alt="4???" title="4???" coords="8,118,28,122,26,150,27,171,6,172" />
				        				<area class="area_size" data-next-size="400" shape="poly" alt="??????" title="??????" coords="18,215,40,205,56,232,74,248,62,264,45,248,24,223" />
				        			</map>
				        			<map id="map_zoom_400" name="map_zoom_400">
				        				<area class="area_zoom" data-zoom="2" shape="poly" alt="2???" title="2???" coords="18,136,37,145,52,109,68,88,82,75,68,60,50,77,30,107" />
				        				<area class="area_zoom" data-zoom="4" shape="poly" alt="4???" title="4???" coords="18,136,38,145,31,176,29,206,6,207,7,182,11,155" />
				        				<area class="area_size" data-next-size="500" shape="poly" alt="??????" title="??????" coords="28,302,53,288,70,312,85,328,93,334,73,353,55,336,36,315" />
				        			</map>
				        			<map id="map_zoom_500" name="map_zoom_500">
				        				<area class="area_zoom" data-zoom="2" shape="poly" alt="2???" title="2???" coords="34,137,52,149,76,111,102,84,118,72,106,55,81,75,54,105" />
				        				<area class="area_zoom" data-zoom="4" shape="poly" alt="4???" title="4???" coords="34,139,53,149,39,179,31,210,29,224,9,219,13,193,25,160" />
				        				<area class="area_size" data-next-size="300" shape="poly" alt="??????" title="??????" coords="33,372,58,358,75,382,96,407,76,428,54,405,37,382" />
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
				        				<area class="area_next" shape="poly" alt="?????????" title="?????????" coords="98,265,131,273,172,273,200,264,209,283,171,294,143,294,112,288,90,282" />
				        				<area class="area_size" data-next-size="400" shape="poly" alt="??????" title="??????" coords="16,216,41,204,57,226,71,240,81,249,62,270,39,251,25,233" />
				        			</map>
				        			<map id="map_xray_400" name="map_xray_400">
				        				<area class="area_next" shape="poly" alt="?????????" title="?????????" coords="142,366,175,372,210,375,254,367,263,387,237,394,177,392,149,389,138,386" />
				        				<area class="area_size" data-next-size="500" shape="poly" alt="??????" title="??????" coords="27,300,52,287,73,314,89,330,96,335,73,354,55,337,37,316" />
				        			</map>
				        			<map id="map_xray_500" name="map_xray_500">
				        				<area class="area_next" shape="poly" alt="?????????" title="?????????" coords="180,464,219,472,266,475,303,470,323,465,329,482,293,491,256,493,213,490,175,481" />
				        				<area class="area_size" data-next-size="300" shape="poly" alt="??????" title="??????" coords="31,369,59,355,77,384,91,402,99,407,75,428,57,409,38,384" />
				        			</map>
				        		</div>
			        		</div>
						</div>
						
						<!-- index map -->
						<div id="div_index_container" class="display_n bd_gray2">
							<div style="position:relative;width:100%;height:100%;">
								<div id="index_map"></div>
								<div class="div_index_mode">
									<a id="a_index_mode" href="#" data-mode="MAX" ><img class="w_32 h_32" src="<c:url value='/images/kworks/map/common/index_extents_on.png' />" alt="????????????????????????" title="????????????????????????" /></a>
								</div>
								<div class="div_index_close">
									<a id="a_index_close" href="#"><img src="<c:url value='/images/kworks/main/map/index/index_close_right_off.png' />" alt="??????????????????" title="??????????????????" /></a>
								</div>
							</div>
						</div>
						<div id="div_index_open">
							<a id="a_index_open" href="#"><img src="<c:url value='/images/kworks/main/map/index/index_open_right_off.png' />" alt="???????????? ??????" title="???????????? ??????" /></a>
						</div>
						<!-- //index map -->
						
						<!-- road view  -->
						<div id="roadview_container" class="bd_gray2"></div>
						<div id="roadview_mode">
							<a id="a_roadview_mode" href="#">
								<img src="<c:url value='/images/kworks/main/map/index/index_open_right_off.png' />"  alt="?????? ??????/??????" title="?????? ??????/??????" />
							</a>
						</div>
						
						<!-- ?????? ??? -->
						<div id="div_result" class="easyui-window" data-options="title:'?????? ?????? ?????? ??????',height:400,collapsible:false,minimizable:false,draggable:false,resizeable:false,inline:true,closed:true,onClose:function(){resultObj.clear();}">
							<div class="easyui-layout div_result_layout" data-options="fit:true" style="width:100%;height:310px;">
								<div data-options="region:'west',title:'?????? ?????? ??????'" style="width:300px;">
									<table class="grid_result_layers"></table>
								</div>
								<div class="div_result_center" data-options="region:'center'">
									<div class="div_result_menu" style="display:block;height:60px;">
										<div class="f_l">
											<a href="#" class="a_setting_item" >
												<img class="img_ov_off" src="<c:url value='/images/kworks/map/common/btn_slide_set_off.png' />" alt="????????????" />
											</a> 
											<a href="#" class="a_addition_choice" >
												<img class="img_ov_off" src="<c:url value='/images/kworks/map/common/btn_slide_select_off.png' />" alt="????????????" />
											</a>
											<a href="#" class="a_remove_rows">
												<img class="img_ov_off" src="<c:url value='/images/kworks/map/common/btn_slide_delete_off.png' />" alt="????????????" />
											</a>
											<a href="#" class="a_search_condition">
												<img class="img_ov_off" src="<c:url value='/images/kworks/map/common/btn_slide_search3_off.png' />" alt="????????????" />
											</a>
											<a href="#" class="a_search_relation">
												<img class="img_ov_off" src="<c:url value='/images/kworks/map/common/btn_slide_search2_off.png' />" alt="????????????" />
											</a>
										</div>
										<div class="f_r">
											<a href="#" class="a_move_location">
												<img class="img_ov_off" src="<c:url value='/images/kworks/map/common/btn_slide_position_off.png' />" alt="????????????" />
											</a>
											<a href="#" class="a_corporation_location">
												<img class="img_ov_off" src="<c:url value='/images/kworks/map/common/btn_slide_construction_position_off.png' />" alt="????????????" />
											</a>
											<a href="#" class="a_search_register">
			 									<img class="img_ov_off" src="<c:url value='/images/kworks/map/common/btn_slide_list_off.png' />" alt="????????????" />
											</a>
											<a href="#" class="a_batchPrint_ctrlpnt" style="display:none;">
			 									<img class="img_ov_off" src="<c:url value='/images/kworks/map/common/btn_slide_print_off.png' />" alt="???????????? ????????????" />
											</a>
											<a href="#" class="a_export_excel">
												<img class="img_ov_off" src="<c:url value='/images/kworks/map/common/btn_slide_send1_off.png' />" alt="??????????????????" />
											</a>
											<a href="#" class="a_export_shape">
												<img class="img_ov_off" src="<c:url value='/images/kworks/map/common/btn_slide_send2_off.png' />" alt="??????????????????" />
											</a>
											<a href="#" class="a_export_ldConsPs" style="display:none;">
												<img class="img_ov_off" src="<c:url value='/images/kworks/map/common/btn_slide_send2_off.png' />" alt="????????????????????????" />
											</a>
											<a href="#" class="a_export_fix">
												<img class="img_ov_off" src="<c:url value='/images/kworks/map/common/btn_slide_map_off.png' />" alt="????????????" />
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
					</div>
									
				</div><!-- Tab???-->
				
			</div>
		</div>
	</body>
</html>