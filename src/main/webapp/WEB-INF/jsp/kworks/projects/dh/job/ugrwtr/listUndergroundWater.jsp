<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 			uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" 			uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="fmt" 		uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form"		uri="http://www.springframework.org/tags/form" %>

<form:form commandName="bmlWellPs">
	<div class="search ma_t_5">
		<span class="display_in w_80 ma_l_20">사용구분</span>
		<form:select class="selectBox w_160" name="disCde" path="disCde">
			<form:option value="" label="전체"/>
			<form:options items="${disCde}" itemValue="codeId" itemLabel="codeNm" />
		</form:select>
		<span class="display_in w_80 ma_l_20">법정동</span>
		<form:select class="selectBox w_160" name="dvopLocC" path="dvopLocN">
			<form:option value="" label="전체"/>
			<form:options items="${dvopLocN}" itemValue="codeId" itemLabel="codeNm" />
		</form:select>
	</div>
	<div class="search ma_t_5">
		<span class="display_in w_80 ma_l_20">용도구분</span>
		<form:select class="selectBox w_160" name="uwaterSrv" path="uwaterSrv">
			<form:option value="" label="전체"/>
			<form:options items="${uwaterSrv}" itemValue="codeId" itemLabel="codeNm" />
		</form:select>
		<span class="display_in w_80 ma_l_20">허가신고번호</span>
		<form:input class="inputText w_160" name="permNtNo" path="permNtNo" />
	</div>
	<div class="search ma_t_5">
		<span class="display_in w_80 ma_l_20">성명(상호)</span>
			<form:input class="inputText w_160" name="rgtMbdNm" path="rgtMbdNm" />
	</div>
	<div class="btnRight">
		<a class="btn_init4" href="#" alt="초기화"></a>
		<a class="btn_search4" href="#" alt="검색"></a>
		<a class="btn_close btn_ri" href="#" ></a>
	</div>
</form:form>
