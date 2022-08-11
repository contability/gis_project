<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
    
<%-- <script type="text/javascript" src="<c:url value='/webjars/ckeditor/4.5.8/full/ckeditor.js' />"></script>
<script type="text/javascript" src="<c:url value='/webjars/ckeditor/4.5.8/full/adapters/jquery.js' />"></script>
<script type="text/javascript" src="<c:url value='/js/kworks/cmmn/ckeditorconfig.js' />"></script> --%>
    
<div id="div_lgstrStats_add" class="div_modal" title="지적통계 등록" style="text-align: center;">
	<form id="addLgstrStatsForm" method="post" action="<c:url value='/lgstrStats/addLgstrStatsRes.do'/>" enctype="multipart/form-data" >
    	<fieldset>
    		<div>
    			<span class="title">
    				<label for="txt_lgstrStats_add_sj">제목</label>
	    		</span>
    			<span class="content">
      				<input type="text" name="lgstrStatsSj" id="txt_lgstrStats_add_sj" value="" maxlength="300" >
      			</span>
    		</div>
    		<div>
    			<span class="title">
	    			<label for="txa_lgstrStats_add_cn">내용</label>
	    		</span>
    			<span class="content">
	    			<textarea name="lgstrStatsCn" id="txa_lgstrStats_add_cn" rows="10" ></textarea>
				</span>
			</div>
    		<div>
    			<span class="title">
	      			<label for="file_lgstrStats_add_file">첨부파일</label>
	    		</span>
	    		<div class="content">
	    			<span class="span_file">
	   					<input type="file" name="lgstrStatsFile" id="file_lgstrStats_add_file" value="" />
	   				</span>
	    		</div>
    		</div>
    		<div class="div_button">
    			<a class="btn_save_addChangeDetection btn_register" href="#"></a>
    			<a class="btn_close_addChangeDetection btn_close" href="#"></a>
    		</div>
    	</fieldset>
	</form>
</div>