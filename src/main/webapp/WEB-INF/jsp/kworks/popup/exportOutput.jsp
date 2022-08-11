<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 			uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" 		uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" 		uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="ko">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	
	<meta http-equiv="X-UA-Compatible" content="IE=11" />
	<meta http-equiv="X-UA-Compatible" content="IE=10" />
	<meta http-equiv="X-UA-Compatible" content="IE=9" />
	
	<!-- project -->
	<c:set var="prjCode"><spring:message code='Globals.Prj' /></c:set>
	<c:set var="envType"><spring:message code='Globals.EnvType' /></c:set>
	<c:set var="serverType"><spring:message code='Map.ServerType' /></c:set>
	
	<!-- easyui -->
	<link rel="stylesheet" type="text/css" href="<c:url value='/lib/jquery/easyui/themes/icon.css' />" />
	<link rel="stylesheet" type="text/css" href="<c:url value='/lib/jquery/easyui/themes/bootstrap/easyui.css' />" />
	
	<!-- kworks -->
	<link type="text/css" rel="stylesheet" href="<c:url value='/css/kworks/common/reset.css' />" />
	<link type="text/css" rel="stylesheet" href="<c:url value='/css/kworks/cmmn/common.css' />" />
	<link type="text/css" rel="stylesheet" href="<c:url value='/css/kworks/main/main.css' />" />	
	<link href="<c:url value='/css/kworks/cmmn/common.css' />" type="text/css" rel="stylesheet" />
	<link href="<c:url value='/css/kworks/popup/output/common.css' />" type="text/css" rel="stylesheet" />
	<link href="<c:url value='/css/kworks/popup/output/layout_${layoutId}.css' />" type="text/css" rel="stylesheet" />
	
	
	<!-- import javascript -->
	<script type="text/javascript">
		// 컨텍스트 패스
		var CONTEXT_PATH = "${pageContext.request.contextPath}";
		var COM = {
			CITY_NAME : '<spring:message code='Com.CityName' />',
			DEFAULT_CASE : '<spring:message code='Com.DefaultCase' />'
		};
		
		var MAP = {
			// 지도 서버 타입
			SERVER_TYPE : "<spring:message code='Map.ServerType' />",
			// 지도 좌표계
			PROJECTION : "<spring:message code='Map.Projection' />",
			// TMS 경로
			TMS_URL : "<spring:message code='Map.Url.Tms' />",
			// 지도 심볼 경로
			SYMBOL_URL : "<spring:message code='Map.Url.Symbol' />"
		};
	</script>

	<script src="<c:url value='/js/${prjCode}/props/common.js' />" type="text/javascript"></script>	
	
	<!-- jquery -->
	<script src="<c:url value='/webjars/jquery/2.1.4/jquery.min.js' />" type="text/javascript"></script>
	<script src="<c:url value='/lib/jquery/form/jquery.form.min.js' />" type="text/javascript"></script>
	<script src="<c:url value='/lib/jquery/easyui/jquery.easyui.min.js' />" type="text/javascript"></script>
	
	<!-- gis -->
	<script src="<c:url value='/webjars/proj4js/2.2.1/proj4.js' />" type="text/javascript"></script>
	<script src="<c:url value='/webjars/openlayers/3.13.0/ol-debug.js' />" type="text/javascript"></script>
	<script src="<c:url value='/lib/kworks/kworks-debug.js' />" type="text/javascript"></script>
		
	<!-- kworks -->
	<script src="<c:url value='/js/kworks/cmmn/utils.js' />" type="text/javascript"></script>
	<script src="<c:url value='/js/kworks/main/map.js' />" type="text/javascript"></script>
	<script src="<c:url value='/js/kworks/server/${serverType}.js' />" type="text/javascript"></script>
	
	<script src="<c:url value='/js/${prjCode}/props/tms.js' />" type="text/javascript"></script>
	<script src="<c:url value='/js/kworks/gis/backgroundMap.js' />" type="text/javascript"></script>
	
	<script src="<c:url value='/js/kworks/gis/sld.js' />" type="text/javascript"></script>
	<script src="<c:url value='/js/kworks/gis/legend.js' />" type="text/javascript"></script>
	<script src="<c:url value='/js/kworks/gis/hghnkOutput.js' />" type="text/javascript"></script>
	
	<script src="<c:url value='/js/kworks/rest/coordinate.js' />" type="text/javascript"></script>
	<script src="<c:url value='/js/kworks/rest/themamap.js' />" type="text/javascript"></script>

	<script src="<c:url value='/js/kworks/window/window.js' />" type="text/javascript"></script>
	<script src="<c:url value='/js/kworks/window/export.js' />" type="text/javascript"></script>
	
	<script src="<c:url value='/js/kworks/popup/output/highRankMap.js' />" type="text/javascript"></script>
	<script src="<c:url value='/js/kworks/popup/output/exportOutput.js' />" type="text/javascript"></script>
	<script src="<c:url value='/js/kworks/window/drawTool.js' />" type="text/javascript"></script>

	<style type="text/css">
		/* #div_menubar #a_scale img {
			vertical-align:middle;
		} */
	
		#div_info table tr {
			height: 65px;
		}
	</style>

<title>출력</title>
</head>
<body>
	<input type="hidden" id="hid_layout_id" value="<c:out value='${layoutId}' />" />

	<!-- tmp -->	
	<!-- <input type="hidden" id="hid_srs" value="" /> -->
	
	<input type="hidden" id="hid_user_name" value="<c:out value='${userName}' />" />
	<input type="hidden" id="hid_dept_name" value="<c:out value='${deptName}' />" />
	
	<div id="div_loading">
		<img src="<c:url value='/images/kworks/common/loading.svg' />" alt="로딩중" title="로딩중" />
	</div>
	<div id="container">
		<div id="div_menubar">
			<span class="span_area_text" style="vertical-align:inherit;">
				<input type="radio" name="exportType" value="area" checked> 영역
				<input id="txt_extent" type="hidden" value="">
			</span>
			<span class="span_scale_text" style="vertical-align:inherit;">
				<input type="radio" name="exportType" value="scale">
				축척 1 : 
				<input id="txt_scale" type="text" value=""/>
				<a id="a_scale"><img src="<c:url value='/images/kworks/map/common/btn_set.png' />" alt="설정" /></a>
			</span>
			<span class="span_execute">
				<a href="#" id="a_export_request_export" ><img src="<c:url value='/images/kworks/map/skin2/request_export.png' />" alt="내보내기 요청" title="내보내기 요청" /></a>
			</span>
		</div>
		<div id="div_map">
			<div id="div_compass"><img src="<c:url value='/images/kworks/map/print/compass.png' />" alt="나침반" /></div>
		</div>
		<div id="div_info">
			<table>
				<colgroup>
					<col width="100px" />
					<col width="100px" />
				</colgroup>
				<thead>
					<tr>
						<th colspan="2">공간정보 도면출력</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td>부 서 명</td>
						<td><c:out value='${deptName}' /></td>
					</tr>
					<tr>
						<td>작 성 자</td>
						<td><c:out value='${userName}' /></td>
					</tr>
					<tr>
						<td>작 성 일</td>
						<td><fmt:formatDate value="${writeDate}" pattern="yyyy-MM-dd" /></td>
					</tr>
				</tbody>
			</table>
		</div>
		<%-- <div id="div_type">
			<div class="span_scale_text">
				<input type="radio" name="exportType" value="scale" checked>
				축척 1 : 
				<input id="txt_scale" type="text" value="" />
				<a id="a_scale"><img src="<c:url value='/images/kworks/map/common/btn_set.png' />" alt="설정" /></a>
			</div>
			<div>
				<input type="radio" name="exportType" value="area"> 영역
				<input id="txt_extent" type="hidden" value="">
			</div>
		</div> --%>
		<div id="div_legend">
			<ul></ul>
		</div>
		<div id="div_index"></div>
	</div>
	
	<c:if test="${prjCode eq 'ss' and envType eq 'oper' and userName ne '관리자'}">
		<script type="text/javascript" src="<c:url value='/lib/fsw/fsw.js' />"></script>
	</c:if>
</body>
</html>