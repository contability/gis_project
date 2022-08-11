<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
    
<%-- <script type="text/javascript" src="<c:url value='/webjars/ckeditor/4.5.8/full/ckeditor.js' />"></script>
<script type="text/javascript" src="<c:url value='/webjars/ckeditor/4.5.8/full/adapters/jquery.js' />"></script>
<script type="text/javascript" src="<c:url value='/js/kworks/cmmn/ckeditorconfig.js' />"></script> --%>

   
<div id="div_lgstrStats_modify" class="div_modal" title="지적통계 수정">
	<form method="post" enctype="multipart/form-data" >
    	<fieldset>
    		<div>
    			<span class="title">
    				<label for="txt_lgstrStats_modify_sj">제목</label>
    			</span>
    			<span class="content">
      				<input type="text" name="lgstrStatsSj" id="txt_lgstrStats_modify_sj" value="" maxlength="300" />
      			</span>
    		</div>
    		<div>
    			<span class="title">
	    			<label for="txa_lgstrStats_modify_cn">내용</label>
	    		</span>
    			<span class="content">
	    			<textarea name="lgstrStatsCn" id="txa_lgstrStats_modify_cn" rows="10" ></textarea>
				</span>
			</div>
    		<div>
    			<span class="title">
	      			<label for="file_lgstrStats_modify_file">첨부파일</label>
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
    					<input type="file" name="lgstrStatsFile" value="" />
    				</span>
					<span class="span_warn">
						※ 신규파일 등록 시 이전 파일은 삭제됩니다.
					</span>
    			</div>
    			<div class="div_button">
    			<a class="btn_modify_lgstrStats btn_edit" href="#"></a>
    			<a class="btn_close_lgstrStats btn_close" href="#"></a>
    			</div>
    		</div>
    	</fieldset>
	</form>
</div>