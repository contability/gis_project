<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 			uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" 			uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="fmt" 		uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form"		uri="http://www.springframework.org/tags/form" %>

<form:form commandName="bmlWellPs">
	<div class="search ma_t_20">
		<span class="display_in w_80 ma_l_20">관리번호</span>
		<form:input class="inputText w_160" name="mgNo" path="mgNo" />
		<span class="display_in w_100 ma_l_20">구분</span>
		<form:select class="selectBox w_160" name="rgtMbdGbn" path="rgtMbdGbn">
			<form:option value="" label="전체"/>
			<form:options items="${listRgtMbdGbn}" itemValue="codeId" itemLabel="codeNm" />
		</form:select>
	</div>
	<div class="search ma_t_5">
		<span class="display_in w_80 ma_l_20">시설구분</span>
		<form:select class="selectBox w_160" name="permNtFormGbn" path="permNtFormGbn">
			<form:option value="" label="전체"/>
			<form:options items="${listPermNtFormGbn}" itemValue="codeId" itemLabel="codeNm" />
		</form:select>
		<span class="display_in w_100 ma_l_20">세부용도</span>
		<form:select class="selectBox w_160" name="uwaterDtlSrv" path="uwaterDtlSrv">
			<form:option value="" label="전체"/>
			<form:options items="${listUwaterDtlSrv}" itemValue="codeId" itemLabel="codeNm" />
		</form:select>
	</div>
	<div class="search ma_t_5">
		<span class="display_in w_80 ma_l_20">상호(성명)</span>
		<form:input class="inputText w_160" name="rgtMbdNm" path="rgtMbdNm" />
		<span class="display_in w_100 ma_l_20">사업자번호(법인)</span>
		<form:input class="inputText w_160" type="text" name="rgtMbdRegNo" path="rgtMbdRegNo" />
	</div>
	<div class="search ma_t_5">
		<span class="display_in w_80 ma_l_20">법정동</span>
		<form:select class="selectBox w_160" name="dvopLocRegnCode" path="dvopLocRegnCode">
			<form:option value="" label="전체"/>
		</form:select>
		<span class="display_in w_100 ma_l_20">번지</span>
		<form:input class="inputText w_75" name="dvopLocBunji" path="dvopLocBunji" /> -
		<form:input class="inputText w_70" name="dvopLocHo" path="dvopLocHo" />
	</div>
	<div class="btnRight">
		<a class="btn_search4" href="#" alt="검색"></a>
		<a class="btn_close btn_ri" href="#" ></a>
	</div>
</form:form>
