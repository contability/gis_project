<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 			uri="http://java.sun.com/jsp/jstl/core" %>

<div class="div_menu_panel_layer_tabs">
    <div title="분류별설정">
    	<div class="div_category_tab">
	    	<div class="div_tool clearfix">
		    	<span class="f_l">
		    		<a href="#" class="a_add_layer" ><img src="<c:url value='/images/kworks/map/skin2/select022.png' />" alt="레이어 추가" title="레이어 추가" /></a>
		    	</span>
	    		<span class="f_r">
					<a href="#" class="a_expand_all"><img src="<c:url value='/images/kworks/map/skin2/select018.png' />" title="레이어 전체 확장" alt="레이어 전체 확장" /></a>
					<a href="#" class="a_collapse_all"><img src="<c:url value='/images/kworks/map/skin2/select019.png' />" title="레이어 전체 축소" alt="레이어 전체 축소" /></a>
					<a href="#" class="a_check_all"><img src="<c:url value='/images/kworks/map/skin2/select020.png' />" title="레이어 전체 선택" alt="레이어 전체 선택" /></a>
					<a href="#" class="a_uncheck_all"><img src="<c:url value='/images/kworks/map/skin2/select021.png' />" title="레이어 전체 해제" alt="레이어 전체 해제" /></a>
				</span>
			</div>
			<div class="div_tree">
	    		<ul class="ul_layer_tree2"></ul>
        		<ul class="ul_base_map_tree2"></ul>
        	</div>
    	</div>
    </div>
    <div title="개별설정">
    	<div class="div_individualization_tab">
		    <div class="div_tool clearfix">
		    	<span class="f_l">
		    		<a href="#" class="a_add_layer" ><img src="<c:url value='/images/kworks/map/skin2/select022.png' />" alt="레이어 추가" title="레이어 추가" /></a>
		    	</span>
		    	<span class="f_r">
		    		<a href="#" class="a_order_set"><img src="<c:url value='/images/kworks/map/skin2/btn_layer_adjust.png' />" title="레이어 순서 조정" alt="레이어 순서 조정" /></a>
		    		<a href="#" class="a_expand_all"><img src="<c:url value='/images/kworks/map/skin2/select018.png' />" title="레이어 전체 확장" alt="레이어 전체 확장" /></a>
					<a href="#" class="a_collapse_all"><img src="<c:url value='/images/kworks/map/skin2/select019.png' />" title="레이어 전체 축소" alt="레이어 전체 축소" /></a>
					<a href="#" class="a_check_all"><img src="<c:url value='/images/kworks/map/skin2/select020.png' />" title="레이어 전체 선택" alt="레이어 전체 선택" /></a>
					<a href="#" class="a_uncheck_all"><img src="<c:url value='/images/kworks/map/skin2/select021.png' />" title="레이어 전체 해제" alt="레이어 전체 해제" /></a>
			    </span>
			</div>
			<div class="div_tree">
				<ul class="ul_layer_tree"></ul>
	        	<ul class="ul_base_map_tree"></ul>
			</div>
    	</div>
    </div>
    <div title="사용자">
    	<div class="div_user_tab">
    		<div class="div_tool clearfix">
				<span class="f_r">
					<a href="#" class="a_expand_all"><img src="<c:url value='/images/kworks/map/skin2/select018.png' />" title="레이어 전체 확장" alt="레이어 전체 확장" /></a>
					<a href="#" class="a_collapse_all"><img src="<c:url value='/images/kworks/map/skin2/select019.png' />" title="레이어 전체 축소" alt="레이어 전체 축소" /></a>
					<a href="#" class="a_check_all"><img src="<c:url value='/images/kworks/map/skin2/select020.png' />" title="레이어 전체 선택" alt="레이어 전체 선택" /></a>
					<a href="#" class="a_uncheck_all"><img src="<c:url value='/images/kworks/map/skin2/select021.png' />" title="레이어 전체 해제" alt="레이어 전체 해제" /></a>
				</span>
			</div>
	   		<ul class="ul_layer_tree"></ul>
    	</div>
    </div>
</div>