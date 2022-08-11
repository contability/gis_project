<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 	 uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" 	 uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<script type="text/javascript" src="<c:url value='/webjars/ckeditor/4.5.8/full/ckeditor.js' />"></script>
<script type="text/javascript" src="<c:url value='/webjars/ckeditor/4.5.8/full/adapters/jquery.js' />"></script>
<script type="text/javascript" src="<c:url value='/js/kworks/cmmn/ckeditorconfig.js' />"></script>
<script type="text/javascript" src="<c:url value='/js/kworks/self/qestn.js' />"></script>

<div id="div_qestn_list" class="panel cont">

	<h1>묻고답하기-질문게시판</h1>
	<hr class="hr_title" />
	
	<form:form commandName="searchDTO" name="frm_qestn_list" method="get" >
		<table class="tbl_qestn_list" >
			<colgroup>
				<col width="10%">
				<col width="*">
				<col width="15%">
				<col width="15%">
			</colgroup>
			<thead>
				<tr>
					<th>번호</th>
					<th>제목</th>
					<th>진행상태</th>
					<th>작성일</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="row" items="${rows}">
					<tr data-qestn-no="${row.qestnNo}" style="cursor:pointer">
						<td>${row.qestnNo}</td>
						<td>${row.qestnSj}</td>
						<td>
							<c:forEach var="sttus" items="${progrsSttus}">
								<c:if test="${row.progrsSttus eq sttus}">
									${sttus.value}
								</c:if>
							</c:forEach>
						</td>
						<td>${row.frstRgsde}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		
		<div class="ma_t_10">
			<div id="paginate" align="center">
				<ui:pagination paginationInfo = "${paginationInfo}" type="image" jsFunction="qestnObj.search" />
				<form:hidden path="pageIndex"  />
			</div>			
		</div>
	</form:form>
	
	<div class="div_btn">
		<a class="a_new_qestn" href="#"><img src="<c:url value='/images/kworks/system/btn_regist.png' />" /></a>
	</div>

</div>

<!-- 조회 -->
<div id="div_qestn_select" class="div_modal" title="질문 조회">
   	<div>
   		<div>
   			<span class="title">
   				제목
   			</span>
   			<span class="content qestnSj"></span>
   		</div>
   		<div>
   			<span class="title">
    			내용
    		</span>
   			<div class="content qestnCn"></div>
		</div>
   		<div class="div_file_list">
   			<span class="title">
      			첨부파일
			</span>
   			<div class="content">
   				<ul class="fileList"></ul>
   			</div>
   		</div>
   		<div class="div_answer">
   			<span class="title">
    			답변
    		</span>
   			<div class="content answerCn"></div>
		</div>
	</div>
</div>

<!-- 질문 등록 -->
<div id="div_qestn_add" class="div_modal" title="질문 등록">
	<form method="post" action="<c:url value='/self/qna/add.do' />" enctype="multipart/form-data" >
    	<fieldset>
    		<div>
    			<span class="title">
    				<label for="txt_qestn_add_sj">제목</label>
	    		</span>
    			<span class="content">
      				<input type="text" name="qestnSj" id="txt_qestn_add_sj" value="" >
      			</span>
    		</div>
    		<div>
    			<span class="title">
	    			<label for="txa_qestn_add_cn">내용</label>
	    		</span>
    			<span class="content">
	    			<textarea name="qestnCn" id="txa_qestn_add_cn" rows="17" ></textarea>
				</span>
			</div>
    		<div>
    			<span class="title">
	      			<label for="file_qestn_add_file">첨부파일</label>
	    		</span>
	    		<div class="content">
	    			<span class="span_file">
	   					<input type="file" name="qestnFile" value="" />
	   				</span>
	    		</div>
    		</div>
    	</fieldset>
	</form>
</div>

<!-- 질문 수정 -->
<div id="div_qestn_modify" class="div_modal" title="질문 수정">
	<form method="post" enctype="multipart/form-data" >
    	<fieldset>
    		<div>
    			<span class="title">
    				<label for="txt_qestn_modify_sj">제목</label>
    			</span>
    			<span class="content">
      				<input type="text" name="qestnSj" id="txt_qestn_modify_sj" value="" />
      			</span>
    		</div>
    		<div>
    			<span class="title">
	    			<label for="txa_qestn_modify_cn">내용</label>
	    		</span>
    			<span class="content">
	    			<textarea name="qestnCn" id="txa_qestn_modify_cn" rows="15" ></textarea>
				</span>
			</div>
    		<div>
    			<span class="title">
	      			<label for="file_qestn_modify_file">첨부파일</label>
				</span>
    			<div class="content">
    				<ul class="fileList">
    					<li>
    						<input type="hidden" name="fileNo" value="" />
    						<input type="hidden" name="deleteFileNo" value="" />
    						<span class="orignlFileNm"></span>
    						<a href="#" class="a_remove_file" >
    							<img src="<c:url value='/images/kworks/map/skin2/btn_remove.png' />" alt="삭제" title="삭제" />
    						</a>
    					</li>
    				</ul>
    				<span class="span_file">
    					<input type="file" name="qestnFile" value="" />
    				</span>
					<span class="span_warn">
						※ 신규파일 등록 시 이전 파일은 삭제됩니다.
					</span>
    			</div>
    		</div>
    	</fieldset>
	</form>
</div>
