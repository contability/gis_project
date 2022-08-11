<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<c:set var="prjCode">
	<spring:message code="Globals.Prj"/>
</c:set>

<link type="text/css" rel="stylesheet" href="<c:url value='/css/kworks/window/pledge.css'/>"/>

<div class="div_window_pledge">
	<p id="pledge_title">
		보 안 서 약 서
	</p>
	<p>
		<span>본인은 2021년 <input type="number" class="pledge_date" min="1" max="12">월 <input type="number" class="pledge_date" min="1" max="31">일부로 </span>
		<span>국가용 보안시스템</span>
		<span class="off_green"><input type="checkbox" class="chkContents">(강릉시 공간정보통합관리시스템 포함)</span>
		<span>과 관련한 업무를 수행함에 있어 다음 사항을 준수할 것을 엄숙히 서약합니다.</span>
	</p>
	<p>
		<span>1. 본인은 강릉시 국가용 보안시스템(강릉시 공간정보통합관리시스템 포함)과 관련된 소관 업무가 </span>
		<span class="off_green"><input type="checkbox" class="chkContents">국가기밀 사항임을 인정하고 제반 보안관계규정 및 지침을 성실히 준수</span>
		<span>한다.</span>
	</p>
	<p>
		<span>2. 나는 이 </span>
		<span class="off_green"><input type="checkbox" class="chkContents">기밀을 누설함이 이적행위</span>
		<span>가 됨을 명심하고 재직 중은 물론 퇴직 후에도 알게 된 모든</span>
		<span class="off_green"><input type="checkbox" class="chkContents">기밀사항을 일절 타인에게 누설하지 아니한다.</span>
	</p>
	<p>
		<span>3. 나는 개인정보보호법을 비롯한 </span>
		<span class="off_green"><input type="checkbox" class="chkContents">개인정보보호에 관한 제반 법령의 내용을 준수</span>
		<span>한다.</span>
	</p>
	<p>
		<span>4. 나는 </span>
		<span class="off_green"><input type="checkbox" class="chkContents">기밀을 누설한 때에는 </span>
		<span>아래의 관계법규에 따라 </span>
		<span class="off_green"><input type="checkbox" class="chkContents">엄중한 처벌을 받을 것</span>
		<span>을 서약한다.</span>
	</p>
	<p>
		<dl>
			<dd>가. 국가보안법 제4조제1항 제2호 및 제5호(국가기밀 누설 등)</dd>
			<dd>나. 형법 제99조(일반이적) 및 제127조(공무상 비밀의 누설)</dd>
			<dd>다. 전자정부법 제35조(금지행위) 및 제76조(벌칙)</dd>
		</dl>
	</p>
	<p class="p_cnt">
		<span>2021년 <input type="number" class="pledge_date" min="1" max="12">월 <input type="number" class="pledge_date" min="1" max="12">일</span>
	</p>
	
	<div>
		<table id="pledge_tbl">
			<colgroup>
				<col width="10%">
				<col width="10%">
				<col width="13%">
				<col width="10%">
				<col width="*">
				<col width="10%">
				<col width="15%">
			</colgroup>
			<tr>
				<th rowspan="2">서약자</th>
				<th rowspan="2">소속</th>
				<td rowspan="2">
					<span id="pledge_dept"></span>
				</td>
				<th>직급</th>
				<td>
					<input type="text" class="pledge_txt" name="pledge_clf">
				</td>
				<th rowspan="2">성명</th>
				<td rowspan="2">
					<span id="pledge_nm"></span>
				</td>
			</tr>
			<tr>
				<th>직위</th>
				<td><input type="text" class="pledge_txt" name="pledge_ofp"></td>
			</tr>
		</table>
	</div>
</div>