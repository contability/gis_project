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
		var FULL_PATH  = '${pageContext.request.scheme}' + '://' + '${pageContext.request.serverName}' + ':' + '${pageContext.request.serverPort}' + CONTEXT_PATH;
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
	<script src="<c:url value='/lib/jquery/easyui/jquery.easyui.min.js' />" type="text/javascript"></script>
	
	<!-- gis -->
	<script src="<c:url value='/webjars/proj4js/2.2.1/proj4.js' />" type="text/javascript"></script>
	<script src="<c:url value='/webjars/openlayers/3.13.0/ol-debug.js' />" type="text/javascript"></script>
	<script src="<c:url value='/lib/kworks/kworks-debug.js' />" type="text/javascript"></script>
		
	<!-- kworks -->
	<script src="<c:url value='/js/kworks/cmmn/utils.js' />" type="text/javascript"></script>
	<script src="<c:url value='/js/kworks/server/${serverType}.js' />" type="text/javascript"></script>
	
	<script src="<c:url value='/js/${prjCode}/props/tms.js' />" type="text/javascript"></script>
	<script src="<c:url value='/js/kworks/gis/backgroundMap.js' />" type="text/javascript"></script>
	
	<script src="<c:url value='/js/kworks/gis/sld.js' />" type="text/javascript"></script>
	<script src="<c:url value='/js/kworks/gis/legend.js' />" type="text/javascript"></script>
	
	<script src="<c:url value='/js/kworks/rest/coordinate.js' />" type="text/javascript"></script>
	<script src="<c:url value='/js/kworks/rest/themamap.js' />" type="text/javascript"></script>
	
	<script src="<c:url value='/js/kworks/window/window.js' />" type="text/javascript"></script>
	
	<script src="<c:url value='/js/kworks/popup/output/highRankMap.js' />" type="text/javascript"></script>
	<script src="<c:url value='/js/kworks/popup/output/highRankConfirm.js' />" type="text/javascript"></script>
	<script src="<c:url value='/js/kworks/window/drawTool.js' />" type="text/javascript"></script>
	
	<title>출력</title>
</head>
<body>
	<input type="hidden" id="export_no" value="<c:out value='${exportNo}' />" />
	<input type="hidden" id="hid_user_name" value="<c:out value='${userName}' />" />
	<input type="hidden" id="hid_dept_name" value="<c:out value='${deptName}' />" />
	
	<input type="hidden" id="hid_export_cntm_id" value="<c:out value='${exportCntmId}' />" />
	
	<input type="hidden" id="hid_layout_id" value="<c:out value='${layoutId}' />" />
	<input type="hidden" id="hid_center_x" value="<c:out value='${centerX}' />" />
	<input type="hidden" id="hid_center_y" value="<c:out value='${centerY}' />" />
	<input type="hidden" id="hid_sc" value="<c:out value='${sc}' />" />
	<input type="hidden" id="hid_view_id" value="<c:out value='${viewId}' />" />
	<input type="hidden" id="hid_tms_type" value="<c:out value='${tmsType}' />" />
	<input type="hidden" id="hid_sld" value="<c:out value='${sld}' />" />
	<input type="hidden" id="hid_base_maps" value="<c:out value='${baseMaps}' />" />
	<input type="hidden" id="hid_user_graphics" value="<c:out value='${userGraphics}' />" />
	<!-- Lks : 항공영상 원점 정보 -->
	<input type="hidden" id="hid_origin_x" value="<c:out value='${originX}' />" />
	<input type="hidden" id="hid_origin_y" value="<c:out value='${originY}' />" />

	
	<div class="div_loading">
		<img src="<c:url value='/images/kworks/common/output-loading.gif' />" alt="로딩중" title="로딩중" />
	</div>
	
	<div id="container">
		<div id="div_menubar">
			<span class="span_print">
				<label for="sel_print">프린터</label>
				<select id="sel_print"></select>
			</span>
			
			<span class="span_paper">
				<label for="sel_paper">용지크기</label>
				<select id="sel_paper"></select>
			</span>
			
			<span class="span_output">
				<a href="#" id="a_exeute_output"><img src="<c:url value='/images/kworks/popup/btn_print.png' />" alt="인쇄" /></a>
			</span>
			
			<c:if test="${ progrsSttus eq 'CONSENT_WAITING' }">
				<span class="span_execute">
					<a href="#" id="a_export_confirm" ><img src="<c:url value='/images/kworks/popup/btn_confirm.png' />" alt="승인" title="내보내기 승인" /></a>
					<a href="#" id="a_open_return" ><img src="<c:url value='/images/kworks/popup/btn_return.png' />" alt="반려" title="내보내기 반려" /></a>
				</span>
			</c:if>
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
		<div id="div_scale">
			<span class="span_scale_text">
				축척 1 : <c:out value='${sc}' />
			</span>
		</div>
		<div id="div_legend">
			<ul></ul>
		</div>
		<div id="div_index"></div>
	</div>
	<div id="div_export_return" title="반려">
   		<div class="div_export_return_prvonsh">
   			<span class="title">
    			<label for="txa_export_detail_return_prvonsh">반려 사유</label>
    		</span>
   			<span class="content">
    			<textarea name="returnPrvonsh" id="txa_export_detail_return_prvonsh" rows="10" ></textarea>
			</span>
		</div>
		<div class="div_button_container">
			<button id="a_export_return" type="button" class="ui-button ui-corner-all ui-widget">등록</button>
			<button id="a_export_return_close" type="button" class="ui-button ui-corner-all ui-widget">닫기</button>
		</div>
	</div>
	
	<c:if test="${prjCode eq 'ss' and envType eq 'oper' and userName ne '관리자'}">
		<script type="text/javascript" src="<c:url value='/lib/fsw/fsw.js' />"></script>
	</c:if>
</body>
</html>