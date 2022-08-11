<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 			uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" 			uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="form"		uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>

<script type="text/javascript" src="<c:url value='/webjars/ckeditor/4.5.8/full/ckeditor.js' />"></script>
<script type="text/javascript" src="<c:url value='/webjars/ckeditor/4.5.8/full/adapters/jquery.js' />"></script>
<script type="text/javascript" src="<c:url value='/js/kworks/cmmn/ckeditorconfig.js' />"></script>
<script type="text/javascript" src="<c:url value='/js/kworks/manage/notice.js' />"></script>
<script type="text/javascript">
	var start = '';
</script>

<div id="div_notice_list" class="panel cont">
	<h1>공지사항 관리</h1>
	<hr class="hr_title" />
	
	<div class="t_a_l f_s_15 c_666 bold">총계 : ${paginationInfo.totalRecordCount}</div>
	
	<form:form commandName="searchDTO" method="get" >
		<table class="tbl_notice_list" >
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
					<th>팝업 사용</th>
					<th>작성일</th>
					<th>조회수</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="row" items="${rows}" varStatus="status">
					<tr data-notice-no="${row.noticeNo}" style="cursor:pointer">
						<td>
							${paginationInfo.firstRecordIndex + status.index + 1}
						</td>
						<td class="t_a_l">${row.noticeSj}</td>
						<td class='chk_popupAt'>
							<input type='hidden' class='popupStart' value='${row.popupStart }'>
							<input type='hidden' class='popupEnd' value='${row.popupEnd }'>
							<span></span>
						</td>
						<td>${row.frstRgsde}</td>
						<td>${row.rdCnt}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		
		<div id="paginate" align="center">
			<ui:pagination paginationInfo = "${paginationInfo}" type="image" jsFunction="noticeObj.search" />
			<form:hidden path="pageIndex"  />
		</div>			
	</form:form>
	
	<div class="div_btn">
		<a class="a_new_notice" href="#"><img src="<c:url value='/images/kworks/system/btn_regist.png' />" /></a>
	</div>

</div>

<div id="div_notice_add" class="div_modal" title="공지사항 등록">
	<form method="post" action="<c:url value='/manage/notice/add.do' />" enctype="multipart/form-data" >
    	<fieldset>
    		<div>
    			<span class="title">
    				<label for="txt_notice_add_sj">제목</label>
	    		</span>
    			<span class="content">
      				<input type="text" name="noticeSj" id="txt_notice_add_sj" value="" maxlength="300">
      				
      			</span>
    		</div>
    		<div>
    			<span class="title">
	    			<label for="txa_notice_add_cn">내용</label>
	    		</span>
    			<span class="content">
	    			<textarea name="noticeCn" id="txa_notice_add_cn" rows="17" ></textarea>
				</span>
			</div>
    		<div>
    			<span class="title">
	      			<label for="file_notice_add_file">첨부파일</label>
	    		</span>
	    		<div class="content">
	    			<span class="span_file">
	   					<input type="file" name="noticeFile" value="" />
	   				</span>
	    		</div>
    		</div>
    		<div>
    			<span class="title">
    				<label for="sel_notice_add_popup_at">팝업사용</label>
    			</span>
    			<span class="content">
    				<select id="sel_notice_add_popup_at">
    					<option value="N">N</option>
    					<option value="Y">Y</option>
    				</select>
	   				<div>
	    				<!-- <span>시작</span> -->
	    				<span>
	    					<input name="popupStart" type="date" class="popupDate" style="width: 220.5px;">
	    				</span>
	    				~
	    				<!-- <span>종료</span> -->
	    				<span>
	    					<input name="popupEnd" type="date" class="popupDate" style="width: 220.5px;">
	    				</span>
	    			</div>
    			</span>
    		</div>
    	</fieldset>
	</form>
</div>

<div id="div_notice_modify" class="div_modal" title="공지사항 수정">
	<form method="post" enctype="multipart/form-data" >
    	<fieldset>
    		<div>
    			<span class="title">
    				<label for="txt_notice_modify_sj">제목</label>
    			</span>
    			<span class="content">
      				<input type="text" name="noticeSj" id="txt_notice_modify_sj" value="" maxlength="300"/>
      			</span>
    		</div>
    		<div>
    			<span class="title">
	    			<label for="txa_notice_modify_cn">내용</label>
	    		</span>
    			<span class="content">
	    			<textarea name="noticeCn" id="txa_notice_modify_cn" rows="15" ></textarea>
				</span>
			</div>
    		<div>
    			<span class="title">
	      			<label for="file_notice_modify_file">첨부파일</label>
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
    					<input type="file" name="noticeFile" value="" />
    				</span>
					<span class="span_warn">
						※ 신규파일 등록 시 이전 파일은 삭제됩니다.
					</span>
    			</div>
    		</div>
    		<div>
    			<span class="title">
    				<label for="sel_notice_modify_popup_at">팝업사용</label>
    			</span>
    			<span class="content">
    				<select id="sel_notice_modify_popup_at">
    					<option value="N">N</option>
    					<option value="Y">Y</option>
    				</select>
	   				<div>
	    				<span>
	    					<input name="popupStart" class="popupDate" type="date" style="width: 220.5px;">
	    				</span>
	    				~
	    				<span>
	    					<input name="popupEnd" class="popupDate" type="date" style="width: 220.5px;">
	    				</span>
	    			</div>
    			</span>
    		</div>
    	</fieldset>
	</form>
</div>
