<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 	 uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" 	 uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<script type="text/javascript" src="<c:url value='/webjars/ckeditor/4.5.8/full/ckeditor.js' />"></script>
<script type="text/javascript" src="<c:url value='/webjars/ckeditor/4.5.8/full/adapters/jquery.js' />"></script>
<script type="text/javascript" src="<c:url value='/js/kworks/cmmn/ckeditorconfig.js' />"></script>
<script type="text/javascript" src="<c:url value='/js/kworks/manage/answer.js' />"></script>

<div id="div_qestn_list" class="panel cont">

	<h1>묻고 답하기 관리</h1>
	<hr class="hr_title" />
	
	<div class="t_a_l f_s_15 c_666 bold">총계 : ${paginationInfo.totalRecordCount}</div>
	
	<form:form commandName="searchDTO" method="get" >
		<table id="tbl_answer_list" >
			<colgroup>
				<col width="10%">
				<col width="*">
				<col width="15%">
				<col width="15%">
				<col width="15%">
			</colgroup>
			<thead>
				<tr>
					<th>번호</th>
					<th>제목</th>
					<th>진행상태</th>
					<th>작성자</th>
					<th>작성일</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="row" items="${rows}" varStatus="status">
					<tr data-qestn-no="${row.qestnNo}" data-progrs-sttus="${row.progrsSttus}" style="cursor:pointer">
						<td>
							${paginationInfo.firstRecordIndex + status.index + 1}
						</td>
						<td>${row.qestnSj}</td>
						<td>
							<c:forEach var="sttus" items="${progrsSttus}">
								<c:if test="${row.progrsSttus eq sttus}">
									${sttus.value}
								</c:if>
							</c:forEach>
						</td>
						<td>${row.wrterId}</td>
						<td>${row.frstRgsde}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		
		<div id="paginate" align="center">
			<ui:pagination paginationInfo = "${paginationInfo}" type="image" jsFunction="answerObj.search" />
			<form:hidden path="pageIndex"  />
		</div>			
	</form:form>

</div>

<div id="div_answer_modify" class="div_modal" title="답변 등록">
	<form method="post" enctype="multipart/form-data" >
		<input type="hidden" name="qestnNo" value="" />
    	<fieldset>
    		<div>
    			<span class="title">
    				제목
    			</span>
    			<span class="content">
      				<span class="content qestnSj"></span>
      			</span>
    		</div>
    		<div>
    			<span class="title">
	    			내용
	    		</span>
	    			<div class="content qestnCn"></div>
			</div>
			<div>
    			<span class="title">
	    			작성자
	    		</span>
	    			<div class="content wrterId"></div>
			</div>
	    	<div class="div_file_list">
	   			<span class="title">
	      			첨부파일
				</span>
	   			<div class="content">
	   				<ul class="fileList"></ul>
	   			</div>
	   		</div>
    		<div>
    			<span class="title">
	    			<label for="txa_answer_modify_cn">답변</label>
	    		</span>
    			<span class="content">
	    			<textarea name="answerCn" id="txa_answer_modify_cn" rows="10" ></textarea>
				</span>
			</div>
    	</fieldset>
	</form>
</div>
