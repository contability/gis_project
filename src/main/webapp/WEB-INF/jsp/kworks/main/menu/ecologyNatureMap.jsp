<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 			uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" 		uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" 		uri="http://www.springframework.org/tags"%>

<c:set var="prjCode"><spring:message code='Globals.Prj' /></c:set>

<div class="div_menu_panel_ecology_nature_map_tabs">
	<div title="조회" style="padding:10px;">
		<div class="div_ecology_nature_map">
			<div class="div_search_condition">
				<div>
					<span class="span_title">동/리</span>
					<span class="span_content"><select class="sel_dong"></select></span>
				</div>
				<div>
					<span class="span_title">산 여부</span>
					<span class="span_content"><input type="text" class="switch_mntn" /></span>
				</div>
				<div>
					<span class="span_title">본번</span>
					<span class="span_content">
						<input type="text" class="txt_mnnm" />
					</span>
				</div>
				<div>
					<span class="span_title">부번</span>
					<span class="span_content">
						<input type="text" class="txt_slno" />
					</span>
				</div>
				<div class="div_tool">
					<a href="#" class="a_search_map">화면에서 검색</a>
					<a href="#" class="a_search">검색</a>
				</div>
			</div>
			<div class="div_search_content div_search_content_none">
				<div class="total_count">검색 결과가 없습니다.</div>
			</div>
			<div class="div_search_content div_search_content_result" style="display:block;" >
				<div class="ul_search_title">조회 결과</div>
				<ul class="ul_search_content">
				</ul>
			</div>
			<c:if test="${ prjCode eq 'gn' }">
				<div class="div_result_tools">
					<a href="#" class="a_kras">토지건물</a>
					<a href="#" class="a_pdf">PDF</a>
					<a href="#" class="a_excel">엑셀</a>
				</div>
			</c:if>
			<c:if test="${ prjCode eq 'yg' }">
				<div class="div_result_tools">
					<a href="#" class="a_kras">토지건물</a>
					<a href="#" class="a_excel">엑셀</a>
				</div>
			</c:if>
		</div>
	</div>
	<c:if test="${ prjCode eq 'gn' }">
		<div title="통계" style="padding:10px;">
			<div class="div_statistics">
	    		<ul>
	    			<li><a href="#" class="a_statistics_grade" >- 생태자연도 등급별 현황</a></li>
	    			<li><a href="#" class="a_statistics_administzone" >- 행정구역별 생태특성정보 현황</a></li>
	    			<li><a href="#" class="a_statistics_owner" >- 소유자별 생태특성정보 현황</a></li>
	    		</ul>
	    	</div>
		</div>
	</c:if>
	<c:if test="${ prjCode eq 'yg' }">
		<div title="통계" style="padding:10px;">
			<div class="div_ecology_statistics_map">
				<div class="ul_owner_title">소유자별 면적 현황</div>
				<div class="ecology_contents">
					<span class="ecology_area_00" style="color:#0385b5">■</span>
					<span>국유지</span>
					<br />
					<span class="ecology_area_01" style="color:#ffff00">■</span>
					<span>도유지</span>
					<br />
					<span class="ecology_area_02" style="color:#fdbcbc">■</span>
					<span>군유지</span>
					<br />
					<span class="ecology_area_03" style="color:#c0c0c0">■</span>
					<span>사유지</span>
				</div>
				<div class="div_content_container panel" style="padding-top:3px;">
					<table id="tbl_ecology_stats_list"></table>
				</div>
				<div class="ecology_total">
					<table>
						<tr>
							<th class="statsOwn" style="width:76px;">전체면적</th>
							<th class="area" style="width:79px;">
								<span id="spn_ecology_area"></span>
							</th>
							<th class="ratio" style="width:70px;">
								<span id="spn_ecology_ratio"></span>
							</th>
						</tr>
					</table>
				</div>
			</div>
		</div>
	</c:if>
</div>