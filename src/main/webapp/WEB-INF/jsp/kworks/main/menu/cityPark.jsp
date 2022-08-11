<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 			uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" 		uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" 		uri="http://java.sun.com/jsp/jstl/fmt" %>

<!-- project -->
<c:set var="envType"><spring:message code='Globals.EnvType' /></c:set>
<div class="div_menu_panel_city_park_tabs">
	<div title="<span class='span_tab_title'>도시<br>공원</span>" style="padding:10px;">
    	<div class="div_city_park">
		    <div class="div_search_condition">
				<div>
					<span class="span_title">분류</span>
					<span class="span_content"><select class="sel_cl"></select></span>	
				</div>
				<div>
					<span class="span_title">법정동</span>
					<span class="span_content"><select class="sel_dong"></select></span>
				</div>
				<div>
					<span class="span_title">명칭</span>
					<span class="span_content"><input type="text" class="txt_name" /></span>
				</div>
				<div>
					<span class="span_title">위치</span>
					<span class="span_content"><input type="text" class="txt_location" /></span>
				</div>
				<div>
					<span class="span_title">영역 내 검색</span>
					<span class="span_content"><input type="text" class="switch_within_zone" /></span>
				</div>
				<div class="div_tool">
					<a href="#" class="a_search">검색</a>
				</div>
			</div>
			<div class="div_search_content div_search_content_none">
				<div class="total_count">검색 결과가 없습니다.</div>
			</div>
			<div class="div_search_content div_search_content_result">
				<div class="total_count">총  <font class="font_total_count">10</font>건을 검색하였습니다.</div>
				<ul class="ul_search_content">
				</ul>
			</div>
			<div class="div_search_pagination">
			</div>
    	</div>
    </div>
    <!-- 2차 개발 시 진행 
    <div title="<span class='span_tab_title'>변천<br>내역</span>" style="padding:10px;">
    	<div class="div_change_details">
		    <div class="div_search_condition">
				<div>
					<span class="span_title">분류</span>
					<span class="span_content"><select class="sel_cl"></select></span>	
				</div>
				<div>
					<span class="span_title">법정동</span>
					<span class="span_content"><select class="sel_dong"></select></span>
				</div>
				<div>
					<span class="span_title">명칭</span>
					<span class="span_content"><input type="text" class="txt_name" /></span>
				</div>
				<div>
					<span class="span_title">위치</span>
					<span class="span_content"><input type="text" class="txt_location" /></span>
				</div>
				<div>
					<span class="span_title">기간</span>
					<span class="span_content">
						<input type="text" class="date_start" />
						<input type="text" class="date_end" />
					</span>
				</div>
				<div>
					<span class="span_title">폐지 포함 여부</span>
					<span class="span_content"><input type="text" class="switch_abolition_yn" /></span>
				</div>
				<div class="div_tool">
					<a href="#" class="a_search">검색</a>
				</div>
			</div>
		</div>
    </div>
    <div title="<span class='span_tab_title'>민원<br>관리</span>" style="padding:10px;">
    	<div class="div_civil_appeal">
    		<div class="div_search_condition">
				<div>
					<span class="span_title">분류</span>
					<span class="span_content"><select class="sel_cl"></select></span>	
				</div>
				<div>
					<span class="span_title">공원명</span>
					<span class="span_content"><select class="sel_city_park"></select></span>
				</div>
				<div>
					<span class="span_title">처리상태</span>
					<span class="span_content"><select class="sel_process_status"></select></span>
				</div>
				<div>
					<span class="span_title">접수일자</span>
					<span class="span_content">
						<input type="text" class="date_start" />
						<input type="text" class="date_end" />
					</span>
				</div>
				<div class="div_tool">
					<a href="#" class="a_registration">등록</a>
					<a href="#" class="a_search">검색</a>
				</div>
			</div>
    	</div>
    </div>
    -->
    
     <div title="<span class='span_tab_title'>시설<br>관리</span>" id="div_facility_manage" style="padding:10px;">
    	<div class="div_facility_manage" id="div_menu_panel_job">
    		<ul class="ul_menu_job_menu_list"></ul>
    	</div>
    </div>
    
    <c:if test="${envType eq 'dev'}">
	    <div title="통계" style="padding:10px;">
	    	<div class="div_statistics" id="div_city_park_menu">
	    		<ul class="ul_city_park_menu_job_menu_list">
	    			<li class="li_menu_group"><h2>도시공원 통계현황</h2>
	    				<ul>
			    			<li class="li_menu">
				    			<img src="<c:url value='/images/kworks/map/skin2/left_3dep_bl.png'/>" alt="DASH" />
				    			<a href="#" class="a_statistics_bjd" >법정구역별 도시공원 현황</a>
			    			</li>
			    			<li class="li_menu">
				    			<img src="<c:url value='/images/kworks/map/skin2/left_3dep_bl.png'/>" alt="DASH" />
				    			<a href="#" class="a_statistics_facilities" >공원별 시설물 현황</a>
			    			</li>
	    				</ul>
		    		</li>	
	    			<li class="li_menu_group"><h2>공원종류별 시설물 통계현황</h2>
	    				<ul>
			    			<li class="li_menu">
			    				<img src="<c:url value='/images/kworks/map/skin2/left_3dep_bl.png'/>" alt="DASH" />
			    				<a href="#" class="a_statistics_aestheticPlaza" >미관광장</a>
			    			</li>
			    			<li class="li_menu">
			    				<img src="<c:url value='/images/kworks/map/skin2/left_3dep_bl.png'/>" alt="DASH" />
			    				<a href="#" class="a_statistics_smallPark" >소공원</a>
			    			</li>
			    			<li class="li_menu">
			    				<img src="<c:url value='/images/kworks/map/skin2/left_3dep_bl.png'/>" alt="DASH" />
			    				<a href="#" class="a_statistics_childrensPark" >어린이공원</a>
			    			</li>
	    				</ul>
		    		</li>	
	    		</ul>
	    	</div>
	    </div>
    </c:if>
    
	<c:if test="${envType eq 'oper'}">
	    <div title="통계" style="padding:10px;">
	    	<div class="div_statistics" id="div_city_park_menu">
	    		<ul class="ul_city_park_menu_job_menu_list">
	    			<li class="li_menu_group"><h2>도시공원 통계현황</h2>
	    				<ul>
			    			<li class="li_menu">
				    			<img src="<c:url value='/images/kworks/map/skin2/left_3dep_bl.png'/>" alt="DASH" />
				    			<a href="#" class="a_statistics_bjd" >법정구역별 도시공원 현황</a>
			    			</li>
			    			<li class="li_menu">
				    			<img src="<c:url value='/images/kworks/map/skin2/left_3dep_bl.png'/>" alt="DASH" />
				    			<a href="#" class="a_statistics_facilities" >공원별 시설물 현황</a>
			    			</li>
	    				</ul>
		    		</li>	
	    			<li class="li_menu_group"><h2>공원종류별 시설물 통계현황</h2>
	    				<ul>
			    			<li class="li_menu">
			    				<img src="<c:url value='/images/kworks/map/skin2/left_3dep_bl.png'/>" alt="DASH" />
			    				<a href="#" class="a_statistics_aestheticPlaza" >미관광장</a>
			    			</li>
			    			<li class="li_menu">
			    				<img src="<c:url value='/images/kworks/map/skin2/left_3dep_bl.png'/>" alt="DASH" />
			    				<a href="#" class="a_statistics_smallPark" >소공원</a>
			    			</li>
			    			<li class="li_menu">
			    				<img src="<c:url value='/images/kworks/map/skin2/left_3dep_bl.png'/>" alt="DASH" />
			    				<a href="#" class="a_statistics_childrensPark" >어린이공원</a>
			    			</li>
	    				</ul>
		    		</li>	
	    		</ul>
	    	</div>
	    </div>
    </c:if>
</div>