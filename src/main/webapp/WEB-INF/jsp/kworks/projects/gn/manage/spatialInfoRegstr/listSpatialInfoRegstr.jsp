<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/kworks/mps/inc/taglib_common.jsp"%>

<script type="text/javascript" src="<c:url value='/webjars/ckeditor/4.5.8/full/ckeditor.js' />"></script>
<script type="text/javascript" src="<c:url value='/webjars/ckeditor/4.5.8/full/adapters/jquery.js' />"></script>
<script type="text/javascript" src="<c:url value='/js/kworks/cmmn/ckeditorconfig.js' />"></script>
<script type="text/javascript" src="<c:url value='/js/gn/manage/spatialInfoRegstr.js' />"></script>
<script type="text/javascript">
	var CONTEXT_PATH = "${pageContext.request.contextPath}";
</script>

<style>
	.title{
		font-weight:bold;
	}
	
	#div_spatialInfoRegstr_modify{
		overflow:hidden;
	}
		
	#div_spatialInfoRegstr_modify form fieldset > div .content{
		padding-left: 44px;
	}
	
	#div_spatialInfoRegstr_modify form table tbody tr td {
		border: 1px solid #d7d7d7; padding: 8px;
	}
	
	#div_spatialInfoRegstr_modify form table tbody tr td input {
		height: 30px; border: 1px solid #d7d7d7; padding: 0px 0px 0px 10px; color:#666;
	}
	
	input::placehoder{
		color: #DCDCDC;
	}
	
	#dateSearch{
		height: 29px;
		vertical-align: top;
	}
	
	#dateSearch input{
		width: 109px;
	}
</style>

<div id="div_spatialInfoRegstr_list" class="panel cont">

	<h1>공간정보관리대장</h1>
	<hr class="hr_title">
	
	<form:form commandName="searchDTO" method="get">
	<div class="searchBox">
		<!-- <span class="sLabel">수령인</span> -->
		<form:select path="searchCondition" class="w_120 searchCondition">
			<form:option value="1">사업명</form:option>
			<form:option value="2">제작연도</form:option>
			<form:option value="3">로딩일자</form:option>
		</form:select>
		<form:input path="searchKeyword" name="searchKeyword" class="w_240"/>
		<span id="dateSearch">
			<input type="text" id="searchStartDt" name="searchStartDt" value="${searchStartDt}" placeholder="YYYY-MM-DD"/>
			~
			<input type="text" id="searchEndDt" name="searchEndDt" value="${searchEndDt }" placeholder="YYYY-MM-DD"/>
		</span>
		<a href="#" class="a_search">검색</a>
	</div>
	
	<div class="t_a_l f_s_15 c_666 bold">총계 : ${paginationInfo.totalRecordCount}</div>
	
		<table class="tbl_spatialInfoData_list">
			<colgroup>
				<col width="16%">
				<col width="16%">
				<col width="*">
				<col width="16%">
			</colgroup>
			<thead>
				<tr>
					<th>관리번호</th>
					<th>제작연도</th>
					<th>사업명</th>
					<th>로딩일자</th>
			</thead>
			<tbody>
				<c:forEach var="row" items="${rows }" varStatus="status">
					<tr data-no="${row.regstrSn }" style="cursor: pointer">
						<%-- <td>
							${paginationInfo.firstRecordIndex + status.index +1 }
						</td> --%>
						<td>${row.manageNo }</td>
						<td>${row.grphinfoMnfctYear }</td>
						<td>${row.bsnsNm }</td>
						<td>${row.grphinfoUploadDe }</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		
		<div id="paginate" align="center">
			<ui:pagination paginationInfo="${paginationInfo }" type="image" jsFunction="spatialInfoRegstrObj.search"/>
			<form:hidden path="pageIndex"/> 
		</div>
	</form:form>
	
	<div id="div_spatialInfoRegstr_modify" class="div_modal" title="공간정보관리대장 수정">
		<form method="post">
			<input type="hidden" name="regstrSn">
			<div class="tabs-1" style="text-align:left;">
				<ul class="tab-list-1">
					<li class="active" data-no="1"><a class="tab-control" href="#list_tab1">공간정보관리대장</a></li>
					<li data-no="2"><a class="tab-control" href="#list_tab2">데이터로딩내역</a></li>
				</ul>
			</div>
			<div class="tab-panel-1 active" id="list_tab1">
				<table class="table_text">
					<tbody>
						<tr>
							<th><label for="txt_spatialInfoRegstr_modify_manageNo">관리번호</label></th>
							<td><input type="text" name="manageNo" id="txt_spatialInfoRegstr_modify_manageNo"></td>
							<th><label for="txt_spatialInfoRegstr_modify_grphinfoMnfctYear">제작연도</label></th>
							<td><input type="text" name="grphinfoMnfctYear" id="txt_spatialInfoRegstr_modify_grphinfoMnfctYear" placeholder="YYYY"></td>
						</tr>
						<tr>
							<th><label for="txt_spatialInfoRegstr_modify_bsnsNm">사업명</label></th>
							<td colspan="3"><input type="text" name="bsnsNm" id="txt_spatialInfoRegstr_modify_bsnsNm" style="width:615px"></td>
						</tr>
						<tr>
							<th><label for="txt_spatialInfoRegstr_modify_bsnsSumry">사업개요</label></th>
							<td colspan="3"><textarea name="bsnsSumry" id="txt_spatialInfoRegstr_modify_bsnsSumry" style="width:605px; height: 60px; border: 1px solid #d7d7d7; padding: 5px 0px 0px 10px; color:#666;"></textarea></td>
						</tr>
						<tr>
							<th><label for="txt_spatialInfoRegstr_modify_manageRspnber">관리책임자</label></th>
							<td><input type="text" name="manageRspnber" id="txt_spatialInfoRegstr_modify_manageRspnber"></td>
							<th><label for="txt_spatialInfoRegstr_modify_grphinfoUploadDe">로딩일자</label></th>
							<td><input type="text" name="grphinfoUploadDe" id="txt_spatialInfoRegstr_modify_grphinfoUploadDe" placeholder="YYYY-MM-DD"></td>
						</tr>
						<tr>
							<th><label for="txt_spatialInfoRegstr_modify_grphinfoNm">공간정보명</label></th>
							<td><input type="text" name="grphinfoNm" id="txt_spatialInfoRegstr_modify_grphinfoNm"></td>
							<th><label for="txt_spatialInfoRegstr_modify_servcEntrpsNm">개발용역업체</label></th>
							<td><input type="text" name="servcEntrpsNm" id="txt_spatialInfoRegstr_modify_servcEntrpsNm"></td>
						</tr>
					</tbody>
				</table>
			</div>
			<div class="tab-panel-1" id="list_tab2">
				<table class="tbl_spatialInfoData_list">
					<colgroup>
						<col width="16%">
						<col width="16%">
						<col width="16%">
						<col width="16%">
						<col width="16%">
						<col width="16%">
					</colgroup>
					<thead>
						<tr>
							<th>데이터명</th>
							<th>레이어명</th>
							<th>로딩전 개수</th>
							<th>로딩전 연장</th>
							<th>로딩후 개수</th>
							<th>로딩후 연장</th>
					</thead>
					<tbody class="atcFileViewArea">
					</tbody>
				</table>
			</div>
		</form>
	</div>
	
</div>