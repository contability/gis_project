<%@ page language="java" 		contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c"			uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" 			uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="form"		uri="http://www.springframework.org/tags/form" %>
<%-- <%@ include file="/WEB-INF/jsp/kworks/mps/inc/taglib_common.jsp"%> --%>

<div class="div_window_construction_road">
	<div class="sortBox">
		<table summary="검색조건 테이블">
			<caption>검색조건 테이블</caption>
			<colgroup>
				<col width="13%">
				<col width="">
				<col width="13%">
				<col width="">
			</colgroup>
			<tbody>
				<tr>
					<th scope="row">공사번호</th>
					<td colspan="3"><input type="text" id="ftrIdn" maxlength="6" value="" class="inputText w_177" /></td>
				</tr>
				<tr>
					<th scope="row">공사명</th>
					<td><input type="text" id="cntNam" maxlength="12" value="" class="inputText w_177" /></td>
					<th scope="row">공사구분</th>
					<td>
						<select id="cntCde" class="selectBox w_177">
							<option value="">전체</option>
							<c:forEach items="${cntCde}" var="obj" varStatus="status">
								<option value="${obj.codeId}">${obj.codeNm}</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<th scope="row">계약일</th>
					<td>
						<input type="text" id="cttYmd1" maxlength="12" value="" class="inputText easyui-datebox w_75" />
						<span class="space">~</span>
						<input type="text" id="cttYmd2" maxlength="12" value="" class="inputText easyui-datebox w_75" />
					</td>
					<th scope="row">착공일</th>
					<td>
						<input type="text" id="begYmd1" maxlength="12" value="" class="inputText easyui-datebox w_75" />
						<span class="space">~</span>
						<input type="text" id="begYmd2" maxlength="12" value="" class="inputText easyui-datebox w_75" />
					</td>
				</tr>
				<tr>
					<th scope="row">준공일</th>
					<td>
						<input type="text" id="rfnYmd1" maxlength="12" value="" class="inputText easyui-datebox w_75" />
						<span class="space">~</span>
						<input type="text" id="rfnYmd2" maxlength="12" value="" class="inputText easyui-datebox w_75" />
					</td>
					<th scope="row">계약금액</th>
					<td>
						<input type="text" id="tctAmt1" maxlength="12" value="" class="inputText w_75" />
						<span class="space">~</span>
						<input type="text" id="tctAmt2" maxlength="12" value="" class="inputText w_75" />
					</td>
				</tr>
			</tbody>
		</table>
		<p class="sortBtn">
			<a href="#" id="a_clear_road_Construction" class="btnBlue">초기화</a>
			<a href="#" id="a_search_road_Construction" class="btnBlue">검색</a>
		</p>
	</div>

	<table id="bisRdCMDataTitle" class="cmmTable ma_t_20" summary="도로공사대장 목록 테이블">
		<caption>도로공사대장 목록 테이블</caption>
		<colgroup>
			<col width="*">
			<col width="40%">
			<col width="20%">
		</colgroup>
		<thead>
			<tr>
				<th scope="col">
					<span>공사명</span>
				</th>
				<th scope="col">
					<span>공사위치</span>
				</th>
				<th scope="col">
					<span>공사구분</span>
				</th>
			</tr>
		</thead>
	</table>
	<div class="scrollBox h_210">
		<table id="bisRdCMData" class="cmmTable rowLink bd_n" summary="도로공사대장 목록 테이블">
			<caption>도로공사대장 목록 테이블</caption>
			<colgroup>
				<col width="*">
				<col width="36%">
				<col width="14%">
			</colgroup>
			<tbody class="tbl_road_list">
			
			</tbody>
		</table>
	</div>
</div>