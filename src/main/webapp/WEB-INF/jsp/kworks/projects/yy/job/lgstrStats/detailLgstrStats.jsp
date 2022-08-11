<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<div id="div_lgstrStats_select" class="div_modal" title="지적통계 조회">
	<fieldset>
		<div>
			<span class="title">제목</span> <span class="content lgstrStatsSj">${lgstrStatsSj}</span>
		</div>
		<div>
			<span class="title">내용</span>
			<div class="content lgstrStatsCn">${lgstrStatsCn}</div>
		</div>
		<div class="div_file_list">
			<span class="title"> 첨부파일 </span>
			<div class="content">
				<ul class="fileList"></ul>
			</div>
		</div>
		<div class="div_button">
			<a id="btn_modify_lgstrStats" class="btn_edit" href="#"></a> 
			<a id="btn_delete_lgstrStats" class="btn_del" href="#"></a> 
			<a id="btn_close_lgstrStats" class="btn_close" href="#"></a>
		</div>
	</fieldset>
</div>