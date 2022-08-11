<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 			uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" 			uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="form"		uri="http://www.springframework.org/tags/form" %>

<script type="text/javascript" src="<c:url value='/webjars/ckeditor/4.5.8/full/ckeditor.js' />"></script>
<script type="text/javascript" src="<c:url value='/webjars/ckeditor/4.5.8/full/adapters/jquery.js' />"></script>
<script type="text/javascript" src="<c:url value='/js/kworks/cmmn/ckeditorconfig.js' />"></script>
<script type="text/javascript" src="<c:url value='/js/kworks/manage/spatialInfoData.js' />"></script>
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

<div id="div_spatialInfoData_list" class="panel cont">

	<h1>공간정보 자료제공 대장</h1>
	<hr class="hr_title">
	
	<form:form commandName="searchDTO" method="get">
	<div class="searchBox">
		<form:select path="searchCondition" class="w_120">
			<form:option value="1">수령인 소속(기관)</form:option>
			<form:option value="2">수령인 생년월일</form:option>
			<form:option value="3">수령인 성명</form:option>
			<form:option value="4">접수일자</form:option>
			<form:option value="5">제공일자</form:option>
		</form:select>
		<form:input path="searchKeyword" name="searchKeyword" class="w_240"/>
		<span id="dateSearch">
			<input type="text" id="startDate" name="startDate" value="${startDate}" placeholder="YYYY-MM-DD"/>
			~
			<input type="text" id="endDate" name="endDate" value="${endDate }" placeholder="YYYY-MM-DD"/>
		</span>
		<a href="#" class="a_search">검색</a>
	</div>
	
	<div class="t_a_l f_s_15 c_666 bold">총계 : ${paginationInfo.totalRecordCount}</div>
	
		<table class="tbl_spatialInfoData_list">
			<colgroup>
				<col width="16%">
				<col width="*">
				<col width="16%">
				<col width="16%">
				<col width="16%">
				<col width="16%">
			</colgroup>
			<thead>
				<tr>
					<th>관리번호</th>
					<th>접수일자</th>
					<th>제공일자</th>
					<th>소속(기관)</th>
					<th>생년월일</th>
					<th>성명</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="row" items="${rows }" varStatus="status">
					<tr data-no="${row.dtaNo }" style="cursor: pointer">
						<td>${row.manageNo }</td>
						<td>${row.rptDt }</td>
						<td>${row.provdDt }</td>
						<td>${row.recptrPsitn}</td>
						<td>${row.recptrBrthdy }</td>
						<td>${row.recptrNm }</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		
		<div id="paginate" align="center">
			<ui:pagination paginationInfo="${paginationInfo }" type="image" jsFunction="spatialInfoDataObj.search"/>
			<form:hidden path="pageIndex"/> 
		</div>
	</form:form>
	
	<div class="div_btn">
		<a class="a_clipreport_export" href="#"><img alt="내려받기" src="<c:url value='/images/kworks/map/skin2/btn_down.gif'/>"/></a>
		<a class="a_new_spatialInfoData" href="#"><img src="<c:url value='/images/kworks/system/btn_regist.png'/>"/></a>
	</div>
	
	<div id="div_spatialInfoData_add" class="div_modal" title="공간정보 자료제공 대장 등록">
		<form action="<c:url value='/manage/spatialInfoData/add.do'/>" method="post">
			<fieldset>
				<div>
					<span class="title">
						<label for="txt_spatialInfoData_add_rptDt">접수일자</label>
					</span>
					<span class="content">
						<input type="text" name="rptDt" id="txt_spatialInfoData_add_rptDt" placeholder="YYYY-MM-DD">
					</span>
				</div>
				<div>
					<span class="title">
						<label for="txt_spatialInfoData_add_provdDt">제공일자</label>
					</span>
					<span class="content">
						<input type="text" name="provdDt" id="txt_spatialInfoData_add_provdDt" placeholder="YYYY-MM-DD">
					</span>
				</div>
				<div>
					<span class="title">
						<label for="txt_spatialInfoData_add_infoGrad">정보등급</label>
					</span>
					<span class="content">
						<input type="text" name="infoGrad" id="txt_spatialInfoData_add_infoGrad">
					</span>
				</div>
				<div>
					<span class="title">
						<label>공간정보 자료제공 내역</label>
					</span>
					<span class="contentList">
						<div>
							<span>
								<label for="txt_spatialInfoData_add_dtlsDtaKnd">자료종류</label>
							</span>
						 	<span style="padding-left:62px;">
								<input type="text" name="dtlsDtaKnd" id="txt_spatialInfoData_add_dtlsDtaKnd" style="width:356px;">
							</span>
						</div>
						<div>
							<span>
								<label for="txt_spatialInfoData_add_dtlsinfoSize">매수크기(MB)</label>
							</span>
							<span style="padding-left:25px;">
								<input type="text" name="dtlsInfoSize" id="txt_spatialInfoData_add_dtlsinfoSize" style="width:356px;">
							</span>
						</div>
						<div>
							<span>
								<label for="txt_spatialInfoData_add_dtlsUsePurps">사용목적</label>
							</span>
							<span style="padding-left:62px;">
								<input type="text" name="dtlsUsePurps" id="txt_spatialInfoData_add_dtlsUsePurps" style="width:356px;">
							</span>
						</div>
						<div>
							<span>
								<label for="txt_spatialInfoData_add_dtlsProvdType">제공형태</label>
							</span>
							<span style="padding-left:62px;">
								<input type="text" name="dtlsProvdType" id="txt_spatialInfoData_add_dtlsProvdType" style="width:356px;">
							</span>
						</div>
						<div>
							<span>
								<label for="txt_spatialInfoData_add_dtlsFee">수수료</label>
							</span>
							<span style="padding-left:78px;">
								<input type="text" name="dtlsFee" id="txt_spatialInfoData_add_dtlsFee" style="width:356px;">
							</span>
						</div>
					</span>
				</div>
				<div>
					<span class="title">
						<label>수령인</label>
					</span>
					<span class="contentList">
						<div>
							<span>
								<label for="txt_spatialInfoData_add_recptrPsitn">소속(기관)</label>
							</span>
							<span style="padding-left:47px;">
								<input type="text" name="recptrPsitn" id="txt_spatialInfoData_add_recptrPsitn" style="width:356px;">
							</span>
						</div>
						<div>
							<span style="display:inline-block; text-align: left;">
								<label for="txt_spatialInfoData_add_recptrBrthdy">생년월일<br>(법인등록번호)</label>
							</span>
							<span style="padding-left:17px;">
								<input type="text" name="recptrBrthdy" id="txt_spatialInfoData_add_recptrBrthdy" style="width:356px;" placeholder="YYYY-MM-DD">
							</span>
						</div>
						<div>
							<span>
								<label for="txt_spatialInfoData_add_recptrNm">성명</label>
							</span>
							<span style="padding-left:96px;">
								<input type="text" name="recptrNm" id="txt_spatialInfoData_add_recptrNm" style="width:356px;">
							</span>
						</div>
						<div>
							<span>
								<label for="txt_spatialInfoData_add_recptMeth">수령방법</label>
							</span>
							<span style="padding-left:62px;">
								<input type="text" name="recptMeth" id="txt_spatialInfoData_add_recptMeth" style="width:356px;">
							</span>
						</div>
					</span>
					
				</div>
				<div>
					<span class="title">
						<label for="txt_spatialInfoData_add_ipttInfo">
							출력자(복제자)<br>
							소속/성명
						</label>
					</span>
					<span class="content">
						<input type="text" name="ipttInfo" id="txt_spatialInfoData_add_ipttInfo">
					</span>
				</div>
			</fieldset>
		</form>
	</div>
	
	<div id="div_spatialInfoData_modify" class="div_modal" title="공간정보 자료제공 대장 수정">
		<form method="post">
			<fieldset>
				<input type="hidden" name="dtaNo">
				<div>
					<span class="title">
						<label for="txt_spatialInfoData_add_manageNo">관리번호</label>
					</span>
					<span class="content">
						<span id="txt_spatialInfoData_modify_manageNo"></span>
					</span>
				</div>
				<div>
					<span class="title">
						<label for="txt_spatialInfoData_modify_rptDt">접수일자</label>
					</span>
					<span class="content">
						<input type="text" name="rptDt" id="txt_spatialInfoData_modify_rptDt" placeholder="YYYY-MM-DD">
					</span>
				</div>
				<div>
					<span class="title">
						<label for="txt_spatialInfoData_modify_provdDt">제공일자</label>
					</span>
					<span class="content">
						<input type="text" name="provdDt" id="txt_spatialInfoData_modify_provdDt" placeholder="YYYY-MM-DD">
					</span>
				</div>
				<div>
					<span class="title">
						<label for="txt_spatialInfoData_modify_infoGrad">정보등급</label>
					</span>
					<span class="content">
						<input type="text" name="infoGrad" id="txt_spatialInfoData_modify_infoGrad">
					</span>
				</div>
				<div>
					<span class="title">
						<label>공간정보 자료제공 내역</label>
					</span>
					<span class="contentList">
						<div>
							<span>
								<label for="txt_spatialInfoData_modify_dtlsDtaKnd">자료종류</label>
							</span>
						 	<span style="padding-left:62px;">
								<input type="text" name="dtlsDtaKnd" id="txt_spatialInfoData_modify_dtlsDtaKnd" style="width:356px;">
							</span>
						</div>
						<div>
							<span>
								<label for="txt_spatialInfoData_modify_dtlsinfoSize">매수크기(MB)</label>
							</span>
							<span style="padding-left:25px;">
								<input type="text" name="dtlsInfoSize" id="txt_spatialInfoData_modify_dtlsinfoSize" style="width:356px;">
							</span>
						</div>
						<div>
							<span>
								<label for="txt_spatialInfoData_modify_dtlsUsePurps">사용목적</label>
							</span>
							<span style="padding-left:62px;">
								<input type="text" name="dtlsUsePurps" id="txt_spatialInfoData_modify_dtlsUsePurps" style="width:356px;">
							</span>
						</div>
						<div>
							<span>
								<label for="txt_spatialInfoData_modify_dtlsProvdType">제공형태</label>
							</span>
							<span style="padding-left:62px;">
								<input type="text" name="dtlsProvdType" id="txt_spatialInfoData_modify_dtlsProvdType" style="width:356px;">
							</span>
						</div>
						<div>
							<span>
								<label for="txt_spatialInfoData_modify_dtlsFee">수수료</label>
							</span>
							<span style="padding-left:78px;">
								<input type="text" name="dtlsFee" id="txt_spatialInfoData_modify_dtlsFee" style="width:356px;">
							</span>
						</div>
					</span>
				</div>
				<div>
					<span class="title">
						<label>수령인</label>
					</span>
					<span class="contentList">
						<div>
							<span>
								<label for="txt_spatialInfoData_modify_recptrPsitn">소속(기관)</label>
							</span>
							<span style="padding-left:47px;">
								<input type="text" name="recptrPsitn" id="txt_spatialInfoData_modify_recptrPsitn" style="width:356px;">
							</span>
						</div>
						<div>
							<span style="display:inline-block; text-align: left;">
								<label for="txt_spatialInfoData_modify_recptrBrthdy">생년월일<br>(법인등록번호)</label>
							</span>
							<span style="padding-left:17px;">
								<input type="text" name="recptrBrthdy" id="txt_spatialInfoData_modify_recptrBrthdy" style="width:356px;" placeholder="YYYY-MM-DD">
							</span>
						</div>
						<div>
							<span>
								<label for="txt_spatialInfoData_modify_recptrNm">성명</label>
							</span>
							<span style="padding-left:96px;">
								<input type="text" name="recptrNm" id="txt_spatialInfoData_modify_recptrNm" style="width:356px;">
							</span>
						</div>
						<div>
							<span>
								<label for="txt_spatialInfoData_modify_recptMeth">수령방법</label>
							</span>
							<span style="padding-left:62px;">
								<input type="text" name="recptMeth" id="txt_spatialInfoData_modify_recptMeth" style="width:356px;">
							</span>
						</div>
					</span>
					
				</div>
				<div>
					<span class="title">
						<label for="txt_spatialInfoData_modify_ipttInfo">
							출력자(복제자)<br>
							소속/성명
						</label>
					</span>
					<span class="content">
						<input type="text" name="ipttInfo" id="txt_spatialInfoData_modify_ipttInfo">
					</span>
				</div>
			</fieldset>
		</form>
	</div>
	
</div>