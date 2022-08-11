<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 			uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" 			uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="form"		uri="http://www.springframework.org/tags/form" %>

<script type="text/javascript" src="<c:url value='/js/kworks/manage/domn.js' />"></script>
<script type="text/javascript" src="<c:url value='/js/kworks/manage/domnCode.js' />"></script>

<div class="panel cont">

	<h1>도메인 관리</h1>
	<hr class="hr_title" />
	
	<!-- 도메인 관리 -->
	<div id="div_domn_manage" class="w_425 pa_l_15 pa_r_15 f_l">
		<h2>도메인</h2>
		<hr class="hr_subTitle" />
		<form:form commandName="searchDTO" method="get">
			<div class="searchBox">
				<span class="sLabel">도메인명</span>
				<input id="searchKeyword_domn" name="searchKeyword" class="w_150" type="text" value="${searchDTO.searchKeyword}">
				<a id="search_domn" href="#" class="a_search">검색</a>
			</div>
			
			<div id="div_domn_list" style="height:421px; overflow-y: scroll;">
				<table>
					<colgroup>
						<col width="35%">
						<col width="65%">
					</colgroup>
					<thead>
						<tr>
							<th>도메인 ID</th>
							<th>도메인 명</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="row" items="${listDomn}">
							<tr data-domn-id="${row.domnId}" data-domn-nm="${row.domnNm}" style="cursor:pointer">
								<td field="domnId">${row.domnId}</td>
								<td field="domnNm">${row.domnNm}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
			
			<div class="f_r">
				<div class="div_btn f_l">
					<a class="a_new_domn" href="#"><img src="<c:url value='/images/kworks/system/btn_add.png' />" /></a>
				</div>
				<div class="div_btn f_l ">
					<a class="a_modify_domn display_n" href="#"><img src="<c:url value='/images/kworks/system/btn_edit.png' />" /></a>
				</div>
			</div>
		</form:form>
	</div>
	
	<!-- 도메인 코드 관리 -->
	<div id="div_domn_code_manage" class="w_425 pa_l_15 pa_r_15 f_l">
		<h2>도메인 코드</h2>
		<hr class="hr_subTitle" />
		
		<!-- 도메인 미선택 시 보여주는 문구 -->
		<div class="a_blank ma_t_300">
			선택된 도메인이 없습니다.
		</div>
		
		<form:form method="get" class="display_n">
			<div class="searchBox">
				<span class="sLabel">도메인 코드</span>
				<input id="searchKeyword_domn_code" name="searchKeyword" class="w_150" type="text">
				<a id="search_domn_code" href="#" class="a_search">검색</a>
			</div>
			
			<div id="div_domn_code_list" style="height:421px; overflow-y: scroll;">
				<table class="tbl_domn_list" >
					<colgroup>
						<col width="25%">
						<col width="50%">
						<col width="25%">
					</colgroup>
					<thead>
						<tr>
							<th>코드 ID</th>
							<th>코드 명</th>
							<th>사용여부</th>
						</tr>
					</thead>
					<tbody style="cursor:pointer">
					</tbody>
				</table>
			</div>
			
			<div class="f_r">
				<div class="div_btn f_l">
					<a class="a_new_domnCode" href="#"><img src="<c:url value='/images/kworks/system/btn_add.png' />" /></a>
				</div>
			</div>
		</form:form>
	</div>
</div>

<!-- 도메인 등록 팝업 -->
<div id="div_domn_add" class="div_modal" title="도메인 추가">
	<form:form method="post">
		<fieldset>
			<div>
				<span class="title">
	   				<label for="txt_domn_add_domn_cd">도메인 ID</label>
	    		</span>
	    		<span class="content">
	   				<input type="text" name="domnId" id="txt_domn_add_domn_id" value="" maxlength="20">
	   			</span>
			</div>
			
			<div>
				<span class="title">
	   				<label for="txt_domn_add_domn_nm">도메인 명</label>
	    		</span>
	    		<span class="content">
	   				<input type="text" name="domnNm" id="txt_domn_add_domn_nm" value="" maxlength="255">
	   			</span>
			</div>
		</fieldset>
	</form:form>
</div>

<!-- 도메인 편집 팝업 -->
<div id="div_domn_modify" class="div_modal" title="도메인 편집">
	<form:form method="post">
		<fieldset>
			<div>
				<span class="title">
	   				<label for="txt_domn_modify_domn_cd">도메인 ID</label>
	    		</span>
	    		<span class="content">
	    			<span></span>
	   				<input type="hidden" name="domnId" id="txt_domn_modify_domn_cd" value="" maxlength="20">
	   			</span>
			</div>
			
			<div>
				<span class="title">
	   				<label for="txt_domn_modify_domn_nm">도메인 명</label>
	    		</span>
	    		<span class="content">
	   				<input type="text" name="domnNm" id="txt_domn_modify_domn_nm" value="" maxlength="255">
	   			</span>
			</div>
		</fieldset>
	</form:form>
</div>

<!-- 도메인코드 등록 팝업 -->
<div id="div_domn_code_add" class="div_modal" title="도메인코드 추가">
	<form:form method="post">
		<fieldset>
			<div>
				<input type="hidden" name="domnId" id="txt_domn_code_add_domnId" value="" />
				<span class="title">
	   				<label for="txt_domn_code_add_code_id">코드 ID</label>
	    		</span>
	    		<span class="content">
	   				<input type="text" name="codeId" id="txt_domn_code_add_code_id" value="" maxlength="20">
	   			</span>
			</div>
			
			<div>
				<span class="title">
	   				<label for="txt_domn_code_add_code_nm">코드 명</label>
	    		</span>
	    		<span class="content">
	   				<input type="text" name="codeNm" id="txt_domn_code_add_code_nm" value="" maxlength="255">
	   			</span>
			</div>
			
			<div>
				<span class="title">
	   				<label for="txt_domn_code_add_use_at">사용여부</label>
	    		</span>
	    		<span class="content">
	   				<select id="txt_domn_code_add_use_at" name="useAt">
	   					<option value="Y">Y</option>
	   					<option value="N">N</option>
	   				</select>
	   			</span>
			</div>
		</fieldset>
	</form:form>
</div>

<!-- 도메인코드 편집 팝업 -->
<div id="div_domn_code_modify" class="div_modal" title="도메인코드 편집">
	<form:form method="post">
		<fieldset>
			<div>
				<input type="hidden" name="domnId" id="txt_domn_code_modify_domn_id" value="" />
				<span class="title">
	   				<label for="txt_domn_code_modify_code_id">코드 ID</label>
	    		</span>
	    		<span class="content">
	   				<input type="hidden" name="codeId" value="" maxlength="20">
	   				<span id="txt_domn_code_modify_code_id" ></span>
	   			</span>
			</div>
			
			<div>
				<span class="title">
	   				<label for="txt_domn_code_modify_code_nm">코드 명</label>
	    		</span>
	    		<span class="content">
	   				<input type="text" name="codeNm" id="txt_domn_code_modify_code_nm" value="" maxlength="255">
	   			</span>
			</div>
			
			<div>
				<span class="title">
	   				<label for="txt_domn_code_modify_use_at">사용여부</label>
	    		</span>
	    		<span class="content">
	   				<select id="txt_domn_code_modify_use_at" name="useAt">
	   					<option value="Y">Y</option>
	   					<option value="N">N</option>
	   				</select>
	   			</span>
			</div>
		</fieldset>
	</form:form>
</div>