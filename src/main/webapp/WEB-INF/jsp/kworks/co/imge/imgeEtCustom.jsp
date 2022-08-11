<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/kworks/mps/inc/taglib_common.jsp"%>
<script type="text/javascript">

$(function() {
	$("#frm_cstm_imge_et").ajaxForm({
		success : function(res) {
			if(kwImgSlide.ajaxFormCallback) kwImgSlide.ajaxFormCallback(res);
		}
	});
	
	$("input[name=file]").on("change",function(){
		preImgView(this);
	});
});


//이미지 미리보기 :: ie9 이하에서는 동작 안함
function preImgView(input) {
	if (input.files && input.files[0]) {
		var reader = new FileReader();
		reader.onload = function (e) { $('#imgInfoImgView').attr('src', e.target.result); }                 
		reader.readAsDataURL(input.files[0]);
	}
}

</script>
<div class="layerCont">
	<form id="frm_cstm_imge_et" method="post" enctype="multipart/form-data" >
		<input type="hidden" name="shtIdn" value="" />
		<table class="cmmTable v2" summary="사진정보 등록 수정 테이블">
			<caption>사진정보 등록 수정 테이블</caption>
			<colgroup>
				<col width="23%" />
				<col width="*" />
			</colgroup>
			<tbody>
				<tr>
					<th scope="row">구분</th>
					<td>
						<select id="dspCde" name="dspCde">
							<c:forEach items="${dspCdeList}" var="dsp">
								<c:if test="${dsp.dspCde ne 'DSP000'}">
								<option value="${dsp.dspCde}">${dsp.dspCdeNm}</option>
								</c:if>
							</c:forEach>
						</select>
					</td>
				</tr>	
				<tr>
					<th scope="row">시설명</th>
					<td>
						<input type="hidden" name="ftrCde" value="IM999" />
						<input type="hidden" name="ftrIdn" value="" />
						<c:out value='사용자등록이미지' />
					</td>
				</tr>	
				<tr>
					<th scope="row">등록자</th>
					<td>
						<input type="hidden" name="usrDes" value="<c:out value='${userId}' />" />
						<input type="hidden" name="usrNam" value="<c:out value='${userNm}' />" />
						<c:out value='${userNm}' />
					</td>
				</tr>
				<tr>
					<th scope="row">등록일자</th>
					<td>
						<input type="hidden" name="lodYmd" value="<c:out value='${today}' />" />
						<c:out value='${today}' />
					</td>
				</tr>
				<tr>
					<th scope="row">제목</th>
					<td><input type="text" name="ttlDes" class="w100" value="" /></td>
				</tr>
				<tr>
					<th scope="row">설명</th>
					<td><textarea name="imgExp" class="textArea w100 h_70"></textarea></td>
				</tr>
				<tr>
					<th>사진위치</th>
					<td>
						<input name="nggX" class="w_120" type="text" value="" />&nbsp;<input name="nggY" class="w_120" type="text" value="" />
						<a href="#" onclick="imgeEt.getNggXY('custom');">
							<img src="<c:url value='/images/kworks/map/skin2/btn_modify2.png' />" alt="변경" />
						</a>
					</td>
				</tr>
				<tr>
					<th scope="row">사진파일</th>
					<td>
						<p class="">
							<input type="file" name="file" id="file" value="" />
						</p>
						<p class="ma_t_5"><img id="imgInfoImgView" class="bd_gray4" src="<c:url value='/images/kworks/map/blank.gif' />" style="width:160px;height:100px;" /></p>
					</td>
				</tr>
			</tbody>
		</table>
	
		<p class="btnRight v2">
			<a href="#" class="btn_save2 mode_regist" onclick="kwImgSlide.customInsert();"></a>
			<a href="#" class="btn_close mode_regist mode_view mode_edit" onclick="kwImgSlide.insertClose();"></a>
		</p>
	</form>
</div>