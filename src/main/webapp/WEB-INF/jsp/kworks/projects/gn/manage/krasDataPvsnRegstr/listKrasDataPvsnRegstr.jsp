<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/kworks/mps/inc/taglib_common.jsp"%>

<script type="text/javascript" src="<c:url value='/webjars/ckeditor/4.5.8/full/ckeditor.js' />"></script>
<script type="text/javascript" src="<c:url value='/webjars/ckeditor/4.5.8/full/adapters/jquery.js' />"></script>
<script type="text/javascript" src="<c:url value='/js/kworks/cmmn/ckeditorconfig.js' />"></script>
<script type="text/javascript" src="<c:url value='/js/gn/manage/krasDataPvsnRegstr.js' />"></script>
<script type="text/javascript">
	var CONTEXT_PATH = "${pageContext.request.contextPath}";
</script>

<style>
	.contentList{
		display:inline-block;
	}
	
	.contentList div{
		padding-bottom:20px;
	}
	
	.title{
		font-weight:bold;
	}
	
	#div_spatialInfoData_add{
		overflow:hidden;
	}
	
	#div_spatialInfoData_modify{
		overflow:hidden;
	}
	
	#div_spatialInfoData_add form fieldset > div .content{
		padding-left: 44px;
	}
	
	#div_spatialInfoData_modify form fieldset > div .content{
		padding-left: 44px;
	}
	
	input::placeholer{
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

<div id="div_krasDataPvsnRegstr_list" class="panel cont">

	<h1>부동산종합공부 전산자료 제공대장</h1>
	<hr class="hr_title">
	
	<form:form commandName="searchDTO" method="get">
	<div class="searchBox">
		<form:select path="searchCondition" class="w_120">
			<form:option value="1">접수일자</form:option>
			<form:option value="2">제공일자</form:option>
			<form:option value="3">요청기관</form:option>
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
	
		<table class="tbl_krasDataPvsnRegstr_list">
			<colgroup>
				<col width="16%">
				<col width="16%">
				<col width="16%">
				<col width="*">
				<col width="16%">
			</colgroup>
			<thead>
				<tr>
					<th>관리번호</th>
					<th>접수일자</th>
					<th>제공일자</th>
					<th>요청기관명</th>
					<th>요청담당자</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="row" items="${rows }" varStatus="status">
					<tr data-no="${row.dtaNo }" style="cursor: pointer">
						<td>${row.manageNo }</td>
						<td>${row.rptDt }</td>
						<td>${row.provdDt }</td>
						<td>${row.dmndInstNm}</td>
						<td>${row.dmndPicNm }</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		
		<div id="paginate" align="center">
			<ui:pagination paginationInfo="${paginationInfo }" type="image" jsFunction="krasDataPvsnRegstrObj.search"/>
			<form:hidden path="pageIndex"/> 
		</div>
	</form:form>
	
	<div class="div_btn">
		<a class="a_clipreport_export" href="#"><img alt="내려받기" src="<c:url value='/images/kworks/map/skin2/btn_down.gif'/>"/></a>
		<a class="a_new_krasDataPvsnRegstr" href="#"><img src="<c:url value='/images/kworks/system/btn_regist.png'/>"/></a>
	</div>
	
	<div id="div_krasDataPvsnRegstr_add" class="div_modal" title="부동산종합공부 전산자료 제공대장 등록">
		<form action="<c:url value='/manage/krasDataPvsnRegstr/add.do'/>" method="post">
			<fieldset>
				<div>
					<span class="title">
						<label for="txt_krasDataPvsnRegstr_add_rptDt">접수일자</label>
					</span>
					<span class="content">
						<input type="text" name="rptDt" id="txt_krasDataPvsnRegstr_add_rptDt" placeholder="YYYY-MM-DD" class="addDialog">
					</span>
				</div>
				<div>
					<span class="title">
						<label for="txt_krasDataPvsnRegstr_add_dmndInstNm">요청기관명</label>
					</span>
					<span class="content">
						<input type="text" name="dmndInstNm" id="txt_krasDataPvsnRegstr_add_dmndInstNm" class="addDialog">
					</span>
				</div>
				<div>
					<span class="title">
						<label for="txt_krasDataPvsnRegstr_add_dmndOfldcNo">요청문서번호</label>
					</span>
					<span class="content">
						<input type="text" name="dmndOfldcNo" id="txt_krasDataPvsnRegstr_add_dmndOfldcNo" class="addDialog">
					</span>
				</div>
				<div>
					<span class="title">
						<label for="txt_krasDataPvsnRegstr_add_dmndBasisResn">요청 근거 및 사유</label>
					</span>
					<span class="content">
						<input type="text" name="dmndBasisResn" id="txt_krasDataPvsnRegstr_add_dmndBasisResn" class="addDialog">
					</span>
				</div>
				<div>
					<span class="title">
						<label for="txt_krasDataPvsnRegstr_add_dmndCn">요청내용</label>
					</span>
					<span class="content">
						<input type="text" name="dmndCn" id="txt_krasDataPvsnRegstr_add_dmndCn" class="addDialog">
					</span>
				</div>
				<div>
					<span class="title">
						<label for="txt_krasDataPvsnRegstr_add_dmndPicNm">요청담당자</label>
					</span>
					<span class="content">
						<input type="text" name="dmndPicNm" id="txt_krasDataPvsnRegstr_add_dmndPicNm" class="addDialog">
					</span>
				</div>
				<div>
					<span class="title">
						<label for="txt_krasDataPvsnRegstr_add_dmndPicTelnm">요청담당자 전화전호</label>
					</span>
					<span class="content">
						<input type="text" name="dmndPicTelnm" id="txt_krasDataPvsnRegstr_add_dmndPicTelnm"class="addDialog">
					</span>
				</div>
				<div>
					<span class="title">
						<label for="txt_krasDataPvsnRegstr_add_provdDt">제공일자</label>
					</span>
					<span class="content">
						<input type="text" name="provdDt" id="txt_krasDataPvsnRegstr_add_provdDt" placeholder="YYYY-MM-DD" class="addDialog">
					</span>
				</div>
				<div>
					<span class="title">
						<label for="txt_krasDataPvsnRegstr_add_pvsnOfldcNo">제공문서번호</label>
					</span>
					<span class="content">
						<input type="text" name="pvsnOfldcNo" id="txt_krasDataPvsnRegstr_add_pvsnOfldcNo" class="addDialog">
					</span>
				</div>
				<div>
					<span class="title">
						<label for="txt_krasDataPvsnRegstr_add_pvsnCn">제공내용</label>
					</span>
					<span class="content">
						<input type="text" name="pvsnCn" id="txt_krasDataPvsnRegstr_add_pvsnCn" class="addDialog">
					</span>
				</div>
				<div>
					<span class="title">
						<label for="txt_krasDataPvsnRegstr_add_pvsnMth">제공방법</label>
					</span>
					<span class="content">
						<input type="text" name="pvsnMth" id="txt_krasDataPvsnRegstr_add_pvsnMth" class="addDialog">
					</span>
				</div>
				<div>
					<span class="title">
						<label for="txt_krasDataPvsnRegstr_add_pvsnPicNm">제공담당자</label>
					</span>
					<span class="content">
						<input type="text" name="pvsnPicNm" id="txt_krasDataPvsnRegstr_add_pvsnPicNm" class="addDialog">
					</span>
				</div>
				<div>
					<span class="title">
						<label for="txt_krasDataPvsnRegstr_add_pvsnRspnberNm">제공책임자</label>
					</span>
					<span class="content">
						<input type="text" name="pvsnRspnberNm" id="txt_krasDataPvsnRegstr_add_pvsnRspnberNm" class="addDialog">
					</span>
				</div>
			</fieldset>
		</form>
	</div>
	
	<div id="div_krasDataPvsnRegstr_modify" class="div_modal" title="공간정보 자료제공 대장 수정">
		<form method="post">
			<fieldset>
				<input type="hidden" name="dtaNo">
				<div>
					<span class="title">
						<label for="txt_krasDataPvsnRegstr_modify_manageNo">관리번호</label>
					</span>
					<span class="content">
						<span id="txt_krasDataPvsnRegstr_modify_manageNo" class="modifyDialog"></span>
					</span>
				</div>
				<div>
					<span class="title">
						<label for="txt_krasDataPvsnRegstr_modify_rptDt">접수일자</label>
					</span>
					<span class="content">
						<input type="text" name="rptDt" id="txt_krasDataPvsnRegstr_modify_rptDt" placeholder="YYYY-MM-DD" class="modifyDialog">
					</span>
				</div>
				<div>
					<span class="title">
						<label for="txt_krasDataPvsnRegstr_modify_dmndInstNm">요청기관명</label>
					</span>
					<span class="content">
						<input type="text" name="dmndInstNm" id="txt_krasDataPvsnRegstr_modify_dmndInstNm" class="modifyDialog">
					</span>
				</div>
				<div>
					<span class="title">
						<label for="txt_krasDataPvsnRegstr_modify_dmndOfldcNo">요청문서번호</label>
					</span>
					<span class="content">
						<input type="text" name="dmndOfldcNo" id="txt_krasDataPvsnRegstr_modify_dmndOfldcNo" class="modifyDialog">
					</span>
				</div>
				<div>
					<span class="title">
						<label for="txt_krasDataPvsnRegstr_modify_dmndBasisResn">요청 근거 및 사유</label>
					</span>
					<span class="content">
						<input type="text" name="dmndBasisResn" id="txt_krasDataPvsnRegstr_modify_dmndBasisResn" class="modifyDialog">
					</span>
				</div>
				<div>
					<span class="title">
						<label for="txt_krasDataPvsnRegstr_modify_dmndCn">요청내용</label>
					</span>
					<span class="content">
						<input type="text" name="dmndCn" id="txt_krasDataPvsnRegstr_modify_dmndCn" class="modifyDialog">
					</span>
				</div>
				<div>
					<span class="title">
						<label for="txt_krasDataPvsnRegstr_modify_dmndPicNm">요청담당자</label>
					</span>
					<span class="content">
						<input type="text" name="dmndPicNm" id="txt_krasDataPvsnRegstr_modify_dmndPicNm" class="modifyDialog">
					</span>
				</div>
				<div>
					<span class="title">
						<label for="txt_krasDataPvsnRegstr_modify_dmndPicTelnm">요청담당자 전화전호</label>
					</span>
					<span class="content">
						<input type="text" name="dmndPicTelnm" id="txt_krasDataPvsnRegstr_modify_dmndPicTelnm"class="modifyDialog">
					</span>
				</div>
				<div>
					<span class="title">
						<label for="txt_krasDataPvsnRegstr_modify_provdDt">제공일자</label>
					</span>
					<span class="content">
						<input type="text" name="provdDt" id="txt_krasDataPvsnRegstr_modify_provdDt" placeholder="YYYY-MM-DD" class="modifyDialog">
					</span>
				</div>
				<div>
					<span class="title">
						<label for="txt_krasDataPvsnRegstr_modify_pvsnOfldcNo">제공문서번호</label>
					</span>
					<span class="content">
						<input type="text" name="pvsnOfldcNo" id="txt_krasDataPvsnRegstr_modify_pvsnOfldcNo" class="modifyDialog">
					</span>
				</div>
				<div>
					<span class="title">
						<label for="txt_krasDataPvsnRegstr_modify_pvsnCn">제공내용</label>
					</span>
					<span class="content">
						<input type="text" name="pvsnCn" id="txt_krasDataPvsnRegstr_modify_pvsnCn" class="modifyDialog">
					</span>
				</div>
				<div>
					<span class="title">
						<label for="txt_krasDataPvsnRegstr_modify_pvsnMth">제공방법</label>
					</span>
					<span class="content">
						<input type="text" name="pvsnMth" id="txt_krasDataPvsnRegstr_modify_pvsnMth" class="modifyDialog">
					</span>
				</div>
				<div>
					<span class="title">
						<label for="txt_krasDataPvsnRegstr_modify_pvsnPicNm">제공담당자</label>
					</span>
					<span class="content">
						<input type="text" name="pvsnPicNm" id="txt_krasDataPvsnRegstr_modify_pvsnPicNm" class="modifyDialog">
					</span>
				</div>
				<div>
					<span class="title">
						<label for="txt_krasDataPvsnRegstr_modify_pvsnRspnberNm">제공책임자</label>
					</span>
					<span class="content">
						<input type="text" name="pvsnRspnberNm" id="txt_krasDataPvsnRegstr_modify_pvsnRspnberNm" class="modifyDialog">
					</span>
				</div>
			</fieldset>
		</form>
	</div>
	
</div>