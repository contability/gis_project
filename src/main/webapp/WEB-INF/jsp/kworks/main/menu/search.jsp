<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div id="div_menu_panel_search_menu" class="mapTab clearfix"></div>

<!-- 지번 검색 -->
<div id="div_menu_panel_search_lot_number" class="tab_content" >
	<div class="div_search_condition">
		<div>
			<span class="span_title">읍면동</span>
			<span class="span_content"><select class="sel_dong"></select></span>	
		</div>
		<div>
			<span class="span_title"><label for="div_menu_panel_search_lot_number_mntn">산</label></span>
			<span class="span_content"><input type="checkbox" class="chk_mntn" id="div_menu_panel_search_lot_number_mntn" /></span>
		</div>
		<div>
			<span class="span_title">본번</span>
			<span class="span_content"><input type="text" class="txt_mnnm" /></span>
		</div>
		<div>
			<span class="span_title">부번</span>
			<span class="span_content"><input type="text" class="txt_slno" /></span>
		</div>
		<div class="div_tool">
			<a href="#" class="a_search">검색</a>
		</div>
	</div>
	<div class="div_search_content">
		<div class="total_count">총  <font class="font_total_count"></font>건을 검색하였습니다.</div>
		<ul class="ul_search_content">
		</ul>
	</div>
	<div class="div_search_pagination">
	</div>
</div>

<!-- 도로명주소 검색 -->
<div id="div_menu_panel_search_road_name_address" class="tab_content" >
	<div class="div_search_condition">
		<div>
			<span class="span_title">도로명</span>
			<span class="span_content"><select class="sel_road_name"></select></span>	
		</div>
		<div>
			<span class="span_title">본번</span>
			<span class="span_content"><input type="text" class="txt_mnnm" /></span>
		</div>
		<div>
			<span class="span_title">부번</span>
			<span class="span_content"><input type="text" class="txt_slno" /></span>
		</div>
		<div class="div_tool">
			<a href="#" class="a_search">검색</a>
		</div>
	</div>
	<div class="div_search_content">
		<div class="total_count">총  <font class="font_total_count"></font>건을 검색하였습니다.</div>
		<ul class="ul_search_content">
		</ul>
	</div>
	<div class="div_search_pagination">
	</div>
</div>

<!-- 건물명 검색 -->
<div id="div_menu_panel_search_building" class="tab_content" >
	<div class="div_search_condition">
		<div>
			<span class="span_title">건물명</span>
			<span class="span_content"><input type="text" class="txt_buld_nm" /></span>	
		</div>
		<div class="div_tool">
			<a href="#" class="a_search">검색</a>
		</div>
	</div>
	<div class="div_search_content">
		<div class="total_count">총  <font class="font_total_count"></font>건을 검색하였습니다.</div>
		<ul class="ul_search_content">
		</ul>
	</div>
	<div class="div_search_pagination">
	</div>
</div>

<!-- 도로명 검색 -->
<div id="div_menu_panel_search_road_name" class="tab_content" >
	<div class="div_search_condition">
		<div>
			<span class="span_title">도로명</span>
			<span class="span_content"><input type="text" class="txt_road_name" /></span>	
		</div>
		<div class="div_tool">
			<a href="#" class="a_search">검색</a>
		</div>
	</div>
	<div class="div_search_content">
		<div class="total_count">총  <font class="font_total_count"></font>건을 검색하였습니다.</div>
		<ul class="ul_search_content">
		</ul>
	</div>
	<div class="div_search_pagination">
	</div>
</div>

<!-- 통합 검색 -->
<div id="div_menu_panel_search_unity" class="tab_content" >
	<div class="div_search_condition">
		<div>
			<span class="span_title">검색어</span>
			<span class="span_content"><input type="text" class="txt_search" /></span>	
		</div>
		<div class="div_tool">
			<a href="#" class="a_search">검색</a>
		</div>
	</div>
	<div class="div_search_content div_search_content_none">
		<div class="total_count">검색 결과가 없습니다.</div>
	</div>
	<div class="div_search_content div_search_content_lot_number">
		<div class="total_count">[<font class="font_total_count">지번</font>] 검색 결과</div>
		<ul class="ul_search_content">
		</ul>
		<div class="div_tool">
			<a href="#" class="a_more"><img src="<c:url value='/images/kworks/map/common/btn_else.png' />" alt="더보기" title="더보기" /></a>
		</div>
	</div>
	<div class="div_search_content div_search_content_road_address">
		<div class="total_count">[<font class="font_total_count">도로명 주소</font>] 검색 결과</div>
		<ul class="ul_search_content">
		</ul>
		<div class="div_tool">
			<a href="#" class="a_more"><img src="<c:url value='/images/kworks/map/common/btn_else.png' />" alt="더보기" title="더보기" /></a>
		</div>
	</div>
	<div class="div_search_content div_search_content_building">
		<div class="total_count">[<font class="font_total_count">건물명</font>] 검색 결과</div>
		<ul class="ul_search_content">
		</ul>
		<div class="div_tool">
			<a href="#" class="a_more"><img src="<c:url value='/images/kworks/map/common/btn_else.png' />" alt="더보기" title="더보기" /></a>
		</div>
	</div>
	<div class="div_search_content div_search_content_road_name">
		<div class="total_count">[<font class="font_total_count">도로명</font>] 검색 결과</div>
		<ul class="ul_search_content">
		</ul>
		<div class="div_tool">
			<a href="#" class="a_more"><img src="<c:url value='/images/kworks/map/common/btn_else.png' />" alt="더보기" title="더보기" /></a>
		</div>
	</div>
</div>

<!-- 과천시 도로구간 검색 -->
<div id="div_menu_panel_search_citypln_road" class="tab_content" >
	<div class="div_search_condition">
		<div>
			<span class="span_title" style="width : 60px;">도로구간</span>
			<span class="span_content"><input type="text" class="txt_citypln_road_nm" /></span>	
		</div>
		<div class="div_tool">
			<a href="#" class="a_search">검색</a>
		</div>
	</div>
	<div class="div_search_content">
		<div class="total_count">총  <font class="font_total_count"></font>건을 검색하였습니다.</div>
		<ul class="ul_search_content">
		</ul>
	</div>
	<div class="div_search_pagination">
	</div>
</div>

<!-- 동해시 도시계획도로 검색 -->
<div id="div_menu_panel_search_dh_plan_road" class="tab_content" >
	<div class="div_search_condition">
		<div>
			<span class="span_title">국도명</span>
			<span class="span_content">
				<input type="text" class="txt_road_name" />
			</span>	
		</div>
		<div>
			<span class="span_title">구분</span>
			<span class="span_content">
				<select class="sel_plan_road_grade">
					<option value="0">전체</option>
					<option value="RGD001">광로</option>
					<option value="RGD002">대로</option>
					<option value="RGD003">중로</option>
					<option value="RGD004">소로</option>
				</select>
			</span>	
		</div>
		<div>
			<span class="span_title">분류</span>
			<span class="span_content">
				<select class="sel_plan_road_type">
					<option value="0">전체</option>
					<option value="1">1</option>
					<option value="2">2</option>
					<option value="3">3</option>
				</select>
			</span>	
		</div>
		<div>
			<span class="span_title">노선</span>
			<span class="span_content">
				<input type="text" class="txt_plan_road_num" />
			</span>
		</div>
		<div class="div_tool">
			<a href="#" class="a_search">검색</a>
		</div>
	</div>
</div>

<!-- 강릉시 도시계획도로 검색 -->
<div id="div_menu_panel_search_plan_road" class="tab_content" >
	<div class="div_search_condition">
		<div>
			<span class="span_title">구역명</span>
			<span class="span_content">
				<select class="sel_plan_road_sect">
					<option value="강릉">강릉</option>
					<option value="주문진">주문진</option>
					<option value="옥계">옥계</option>
				</select>
			</span>	
		</div>
		<div>
			<span class="span_title">등급</span>
			<span class="span_content">
				<select class="sel_plan_road_grade">
					<option value="0">전체</option>
					<option value="UPR001">광로</option>
					<option value="UPR002">대로</option>
					<option value="UPR003">중로</option>
					<option value="UPR004">소로</option>
				</select>
			</span>	
		</div>
		<div>
			<span class="span_title">류</span>
			<span class="span_content">
				<select class="sel_plan_road_type">
					<option value="0">전체</option>
					<option value="1">1</option>
					<option value="2">2</option>
					<option value="3">3</option>
				</select>
			</span>	
		</div>
		<div>
			<span class="span_title">번호</span>
			<span class="span_content"><input type="text" class="txt_plan_road_num" /></span>
		</div>
		<div class="div_tool">
			<a href="#" class="a_search">검색</a>
		</div>
	</div>
	<div class="div_search_content">
		<div class="total_count">총  <font class="font_total_count"></font>건을 검색하였습니다.</div>
		<ul class="ul_search_content_plan_road">
		</ul>
	</div>
	<div class="div_search_pagination">
	</div>
	<div class="div_footer">
		<div class="button_flat_l">
			<button id="btn_quick" class="button_flat_small btn_blue">조회</button>
		</div>
	</div>
</div>