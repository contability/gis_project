<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 	 uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" 	 uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" 		uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" 		uri="http://www.springframework.org/tags"%>

<c:set var="prjCode"><spring:message code='Globals.Prj' /></c:set>

<script src="<c:url value='/js/${prjCode}/props/common.js' />" type="text/javascript"></script>	
<script src="<c:url value='/js/kworks/gis/hghnkOutput.js' />" type="text/javascript"></script>
<script type="text/javascript" src="<c:url value='/js/kworks/manage/export.js' />"></script>

<div id="div_export_list" class="panel cont">

	<h1>내보내기 관리</h1>
	<hr class="hr_title" />
	
	<!-- 고급 출력 설치 -->
	<div class="div_output_guide">
		<img src="<c:url value='/images/kworks/map/print/guide_print.png' />" alt="고급출력 서비스를 원활히 이용하기 위해 필요한 'PrintService' 프로그램이 설치되어있지 않거나, 최신 버전이 아닙니다. 설치를 완료하신 후 '고급출력 서비스 이용' 버튼을 클릭하여 기능을 활성화 해 주세요." />
		<a href="#" class="a_connect_output"><img src="<c:url value='/images/kworks/map/print/btn_hghnk_print.png' />" alt="고급출력 서비스 이용" /></a>
	</div>
	
	<!-- 내보내기 목록 -->
	<form:form commandName="exportSearchDTO" name="frm_export_list" class="display_n" method="get" >
		<div class="searchBox">
			<span class="sLabel">진행상태</span>
			<form:select path="progrsSttus" class="w_150" >
				<form:option value="">전체</form:option>
				<form:options items="${progrsSttusList}" itemLabel="value" />
			</form:select>
			<a href="#" class="a_search">검색</a>
		</div>
		<div class="t_a_l f_s_15 c_666 bold">총계 : ${paginationInfo.totalRecordCount}</div>
		<table class="tbl_export_list" >
			<colgroup>
				<col width="5%">
				<col width="*">
				<col width="10%">
				<col width="10%">
				<col width="15%">
				<col width="15%">
			</colgroup>
			<thead>
				<tr>
					<th>번호</th>
					<th>내보내기 명</th>
					<th>타입</th>
					<th>요청자</th>
					<th>요청 일시</th>
					<th>진행 상태</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="row" items="${rows}" varStatus="status">
					<tr data-export-no="${row.exportNo}" data-export-ty="${row.exportTy}" style="cursor:pointer">
						<td>
							${paginationInfo.firstRecordIndex + status.index + 1}
						</td>
						<td>${row.exportNm}</td>
						<td>
							<c:forEach var="export" items="${exportTy}">
								<c:if test="${row.exportTy eq export}">
									${export.value}
								</c:if>
							</c:forEach>
						</td>
						<td>${row.kwsUser.userNm}</td>
						<td><fmt:formatDate value="${row.requstDt}" pattern="yyyy-MM-dd" /></td>
						<td>
							<c:forEach var="sttus" items="${progrsSttus}">
								<c:if test="${row.progrsSttus eq sttus}">
									${sttus.value}
								</c:if>
							</c:forEach>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		
		<div id="paginate" align="center">
			<ui:pagination paginationInfo = "${paginationInfo}" type="image" jsFunction="exportObj.search" />
			<form:hidden path="pageIndex"  />
		</div>			
	</form:form>
</div>

<div id="div_export_detail" class="div_modal" title="내보내기 상세정보">
	<form method="post">
    	<fieldset>
    		<div>
    			<span class="title">요청자</span>
    			<span class="content rqesterId"></span>
    		</div>
    		<div>
    			<span class="title">요청 일시</span>
    			<span class="content requstDt"></span>
    		</div>
    		<div>
    			<span class="title">내보내기 명</span>
    			<span class="content exportNm"></span>
    		</div>
    		<div>
    			<span class="title">데이터 타입</span>
    			<span class="content exportTy"></span>
    		</div>
    		<div class="div_export_detail_exportCntmId">
    			<span class="title">데이터 좌표계</span>
    			<span class="content exportCntmId"></span>
    		</div>
    		<div>
    			<span class="title">데이터</span>
				<div class="content">
					<ul class="fileList">
					</ul>
				</div>
    		</div>
    	</fieldset>
	</form>
</div>

<div id="div_export_return" class="div_modal" title="반려">
	<form method="post">
    	<fieldset>
    		<div>
    			<span class="title">
	    			<label for="txa_export_detail_return_prvonsh">반려 사유</label>
	    		</span>
    			<span class="content">
	    			<textarea name="returnPrvonsh" id="txa_export_detail_return_prvonsh" rows="15" ></textarea>
				</span>
			</div>
    	</fieldset>
	</form>
</div>
