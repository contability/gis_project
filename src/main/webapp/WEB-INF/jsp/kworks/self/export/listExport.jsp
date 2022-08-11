<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 	 uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" 	 uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" 		uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" 		uri="http://www.springframework.org/tags"%>

<c:set var="prjCode"><spring:message code='Globals.Prj' /></c:set>

<script src="<c:url value='/js/${prjCode}/props/common.js' />" type="text/javascript"></script>	
<script src="<c:url value='/js/kworks/gis/hghnkOutput.js' />" type="text/javascript"></script>
<script type="text/javascript" src="<c:url value='/js/kworks/self/export.js' />"></script>

<div id="div_export_list" class="panel cont">

	<h1>내보내기 관리</h1>
	<!-- 동해시 요구사항  -->
	<c:if test="${ prjCode eq 'dh' }">
		<div class="t_a_l pa_t_5 pa_l_10 f_s_11 c_777 bold">"배포요청 후 10분 후에 자동승인 처리됩니다."</div>
	</c:if>
	<hr class="hr_title" />
	
	<!-- 고급 출력 설치 -->
	<div class="div_output_guide">
		<img src="<c:url value='/images/kworks/map/print/guide_print.png' />" alt="고급출력 서비스를 원활히 이용하기 위해 필요한 'PrintService' 프로그램이 설치되어있지 않거나, 최신 버전이 아닙니다. 설치를 완료하신 후 '고급출력 서비스 이용' 버튼을 클릭하여 기능을 활성화 해 주세요." />
		<a href="#" class="a_connect_output"><img src="<c:url value='/images/kworks/map/print/btn_hghnk_print.png' />" alt="고급출력 서비스 이용" /></a>
	</div>
	
	<!-- 내보내기 목록 -->
	<form:form commandName="exportSearchDTO" name="frm_export_list" class="display_n" method="get" >
		<table class="tbl_export_list" >
			<colgroup>
				<col width="10%">
				<col width="*">
				<col width="15%">
				<col width="15%">
				<col width="15%">
				<col width="10%">
			</colgroup>
			<thead>
				<tr>
					<th>번호</th>
					<th>내보내기 명</th>
					<th>타입</th>
					<th>요청 일시</th>
					<th>진행 상태</th>
					<th>삭제 예정 일시</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="row" items="${rows}">
					<tr data-export-no="${row.exportNo}" data-export-ty="${row.exportTy}" style="cursor:pointer">
						<td>${row.exportNo}</td>
						<td>${row.exportNm}</td>
						<td>
							<c:forEach var="export" items="${exportTy}">
								<c:if test="${row.exportTy eq export}">
									${export.value}
								</c:if>
							</c:forEach>
						</td>
						<td><fmt:formatDate value="${row.requstDt}" pattern="yyyy-MM-dd" /></td>
						<td>
							<c:forEach var="sttus" items="${progrsSttus}">
								<c:if test="${row.progrsSttus eq sttus}">
									${sttus.value}
								</c:if>
							</c:forEach>
						</td>
						<td><fmt:formatDate value="${row.deletePrearngeDt}" pattern="yyyy-MM-dd" /></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		
		<div class="ma_t_10">
			<div id="paginate" align="center">
				<ui:pagination paginationInfo = "${paginationInfo}" type="image" jsFunction="exportObj.search" />
				<form:hidden path="pageIndex"  />
			</div>			
		</div>
	</form:form>
</div>

<!-- 내보내기 상세정보 -->
<div id="div_export_detail" class="div_modal" title="내보내기 상세정보">
	<div>
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
	</div>
</div>

<!-- 내보내기 반려 사유 -->
<div id="div_export_return" class="div_modal" title="반려 정보">
	<div>
		<div>
			<span class="title">반려 사유</span>
			<div class="content returnPrvonsh">
			</div>
		</div>
	</div>
</div>