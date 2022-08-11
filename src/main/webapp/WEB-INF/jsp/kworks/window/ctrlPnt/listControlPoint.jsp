<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/kworks/mps/inc/taglib_common.jsp"%>

<div class="layerCont over_yh">
	<div class="tableTop">
		<label class="va_t display_in pa_t_5" for="txt_listControlPoint_Search">점의번호 </label>
		<input class="txt_listControlPoint_serIdn inputText w_200" name="serIdn"  />
		<a name="btnSch" class="btn_ri btn_search4" href="#" />
	</div>
	<div class="h_430 over_ys">
		<table class="tbl_listControlPoint cmmTable v2 ma_b_10">
			<thead>
				<tr>
					<th>&nbsp;점의번호</th>
					<th>&nbsp;급수&nbsp;</th>
					<th>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;소재지</th>
					<th>&nbsp;계획기관</th>
					<th>&nbsp;&nbsp;&nbsp;시행기관</th>
					<th>&nbsp;매설형태</th>
					<th>&nbsp;&nbsp;관측일</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${result}" var="list">
					<tr data-ftr-idn="${list.ftrIdn}" data-objectid="${list.objectid}">
						<td class="serIdn"><center>${list.serIdn}</center></td>
						<td class="grad"><center>${list.pcbCdeNm}</center></td>
						<td class="hjdCde"><center>${list.hjdCdeNm}</center></td>
						<td class="planCde"><center>${list.planCde}</center></td>
						<td class="wrkOrg"><center>${list.wrkOrg}</center></td>
						<td class="pcbDes"><center>${list.pcbDes}</center></td>
						<td class="srvYmd"><center>${list.srvYmdDate}</center></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</div>
<p class="btnCenter">
	<a href="#" id="btn_listControlPoint_add"><img src="<c:url value='/images/kworks/map/skin2/btn_listPlus.png' />" alt="목록추가"></a>
</p>