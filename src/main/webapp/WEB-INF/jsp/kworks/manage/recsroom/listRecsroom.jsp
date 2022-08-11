<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 	 uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" 	 uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<script type="text/javascript" src="<c:url value='/webjars/ckeditor/4.5.8/full/ckeditor.js' />"></script>
<script type="text/javascript" src="<c:url value='/webjars/ckeditor/4.5.8/full/adapters/jquery.js' />"></script>
<script type="text/javascript" src="<c:url value='/js/kworks/cmmn/ckeditorconfig.js' />"></script>
<script type="text/javascript" src="<c:url value='/js/kworks/manage/recsroom.js' />"></script>

<div id="div_recsroom_list" class="panel cont">

	<h1>자료실 관리</h1>
	<hr class="hr_title" />
	
	<div class="t_a_l f_s_15 c_666 bold">총계 : ${paginationInfo.totalRecordCount}</div>
	
	<form:form commandName="searchDTO" method="get" >
		<table class="tbl_recsroom_list" >
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
					<th>작성일</th>
					<th>조회수</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="row" items="${rows}" varStatus="status">
					<tr data-recsroom-no="${row.recsroomNo}" style="cursor:pointer">
						<td>
							${paginationInfo.firstRecordIndex + status.index + 1}
						</td>
						<td>${row.recsroomSj}</td>
						<td>${row.frstRgsde}</td>
						<td>${row.rdCnt}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		
		<div id="paginate" align="center">
			<ui:pagination paginationInfo = "${paginationInfo}" type="image" jsFunction="recsroomObj.search" />
			<form:hidden path="pageIndex"  />
		</div>			
	</form:form>
	
	<div class="div_btn">
		<a class="a_new_recsroom" href="#"><img src="<c:url value='/images/kworks/system/btn_regist.png' />" /></a>
	</div>

</div>

<div id="div_recsroom_add" class="div_modal" title="자료실 등록">
	<form method="post" action="<c:url value='/manage/recsroom/add.do' />" enctype="multipart/form-data" >
    	<fieldset>
    		<div>
    			<span class="title">
    				<label for="txt_recsroom_add_sj">제목</label>
	    		</span>
    			<span class="content">
      				<input type="text" name="recsroomSj" id="txt_recsroom_add_sj" value="" maxlength="300" >
      			</span>
    		</div>
    		<div>
    			<span class="title">
	    			<label for="txa_recsroom_add_cn">내용</label>
	    		</span>
    			<span class="content">
	    			<textarea name="recsroomCn" id="txa_recsroom_add_cn" rows="17" ></textarea>
				</span>
			</div>
    		<div>
    			<span class="title">
	      			<label for="file_recsroom_add_file">첨부파일</label>
	    		</span>
	    		<div class="content">
	    			<span class="span_file">
	   					<input type="file" name="recsroomFile" id="file_recsroom_add_file" value="" />
	   				</span>
	    		</div>
    		</div>
    	</fieldset>
	</form>
</div>

<div id="div_recsroom_modify" class="div_modal" title="자료실 수정">
	<form method="post" enctype="multipart/form-data" >
    	<fieldset>
    		<div>
    			<span class="title">
    				<label for="txt_recsroom_modify_sj">제목</label>
    			</span>
    			<span class="content">
      				<input type="text" name="recsroomSj" id="txt_recsroom_modify_sj" value="" maxlength="300" />
      			</span>
    		</div>
    		<div>
    			<span class="title">
	    			<label for="txa_recsroom_modify_cn">내용</label>
	    		</span>
    			<span class="content">
	    			<textarea name="recsroomCn" id="txa_recsroom_modify_cn" rows="15" ></textarea>
				</span>
			</div>
    		<div>
    			<span class="title">
	      			<label for="file_recsroom_modify_file">첨부파일</label>
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
    					<input type="file" name="recsroomFile" value="" />
    				</span>
					<span class="span_warn">
						※ 신규파일 등록 시 이전 파일은 삭제됩니다.
					</span>
    			</div>
    		</div>
    	</fieldset>
	</form>
</div>
