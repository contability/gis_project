<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/kworks/mps/inc/taglib_common.jsp"%>
<script type="text/javascript">
$(function() {
	$("#frm_imge_et").ajaxForm({
		success : function(res) {
			if(imgeEt.ajaxFormCallback) imgeEt.ajaxFormCallback(res);
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
	<form id="frm_imge_et" method="post" enctype="multipart/form-data" >
		<input type="hidden" name="shtIdn" value="<c:out value='${data.shtIdn}' />" />
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
						<input type="hidden" name="dspCde" value="<c:out value='${data.dspCde}' />" />
						<c:if test="${dspCdeList.size() > 0}">
							<c:out value='${dspCdeList.get(0).getCodeNm()}' />
						</c:if>
					</td>
				</tr>	
				<tr>
					<th scope="row">시설명</th>
					<td>
						<input type="hidden" name="ftrCde" value="<c:out value='${data.ftrCde}' />" />
						<input type="hidden" name="ftrIdn" value="<c:out value='${data.ftrIdn}' />" />
						<c:if test="${ftrCdeList.size() > 0}">
							<c:out value='${ftrCdeList.get(0).getCodeNm()}' />
						</c:if>
					</td>
				</tr>	
				<tr>
					<th scope="row">등록자</th>
					<td>
						<input type="hidden" name="usrDes" value="<c:out value='${data.usrDes}' />" />
						<input type="hidden" name="usrNam" value="<c:out value='${data.usrNam}' />" />
						<c:out value='${data.usrNam}' />
					</td>
				</tr>
				<tr>
					<th scope="row">등록일자</th>
					<td>
						<input type="hidden" name="lodYmd" value="<c:out value='${data.lodYmd}' />" />
						<c:out value='${data.lodYmd}' />
					</td>
				</tr>
				<tr>
					<th scope="row">제목</th>
					<td><input type="text" name="ttlDes" class="w100" value="<c:out value='${data.ttlDes}' />" /></td>
				</tr>
				<tr>
					<th scope="row">설명</th>
					<td><textarea name="imgExp" class="textArea w100 h_80"><c:out value='${data.imgExp}' /></textarea></td>
				</tr>
				<tr>
					<th>사진위치</th>
					<td><input name="nggX" class="w_120" type="text" value="<c:out value='${data.nggX}' />" />&nbsp;<input name="nggY" class="w_120" type="text" value="<c:out value='${data.nggY}' />" />
					<a href="#" id="imgNggXY" onclick="imgeEt.getNggXY();">
						<img src="<c:url value='/images/kworks/map/skin2/btn_modify2.png' />" alt="변경" />
					</a>
					</td>
				</tr>
				<tr>
					<th scope="row">사진파일</th>
					<td>
						<p class="ma_b_10">
							<input type="file" name="file" id="file" value="" />
						</p>
						<c:if test="${data.tphDes ne null and data.tflNam ne null}">
							<p class="ma_t_10"><img id="imgInfoImgView" class="bd_gray4" src="<c:url value='/co/imge/thumbnail.do?fileName=${data.tflNam}&nocache=${nocache}' />" style="width:160px;height:100px;" /></p>
						</c:if>
					</td>
				</tr>
			</tbody>
		</table>
	
		<p class="btnRight v2">
			<a href="#" class="btn_save2 mode_regist" onclick="imgeEt.insert();" style="display:none;"></a>
			<a href="#" class="btn_change mode_edit" onclick="imgeEt.update();" style="display:none;"></a>
			<a href="#" class="btn_del mode_edit" onclick="imgeEt.remove();" style="display:none;"></a>
			<a href="#" class="btn_close mode_regist mode_view mode_edit" onclick="imgeEt.close();" style="display:none;"></a>
		</p>
	</form>
</div>