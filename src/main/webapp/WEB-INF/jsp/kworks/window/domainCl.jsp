<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 			uri="http://java.sun.com/jsp/jstl/core" %>

<div class="div_window_domain_cl">
	<div class="div_west" data-options="region:'west',collapsible:false" title="분류 목록">
		<ul class="ul_all_cls">
		</ul>
	</div>
    <div class="div_center" data-options="region:'center'" title="대상 분류">
		<ul class="ul_select_cls">
		</ul>
    </div>
    <div class="div_south" data-options="region:'south',border:false">
       	<a class="a_submit btn_popSelect" href="#" ></a>
       	<a class="a_close btn_popCancel" href="#" ></a>
    </div>
</div>