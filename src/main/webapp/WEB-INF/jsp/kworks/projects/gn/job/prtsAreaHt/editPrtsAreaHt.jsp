<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 		     uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<div class="window_container">
	<div class="window_article">
		<div class="div_window_prtsAreaHt_modify_tabs">
			<div title="개선사업이력">
				<div class="div_prtsAreaHt" data-options="fit:true" style="width:100%; heigth:244px;">
					<!-- <input type="hidden" name="ftrCde" class="modify_prtsAreaHt_ftrCde">
					<input type="hidden" name="ftfCde" class="modify_prtsAreaHt_ftfCde">
					<input type="hidden" name="ftfIdn" class="modify_prtsAreaHt_ftfIdn"> -->
					<table class="table_text">
						<tbody>
							<!-- <tr>
								<th>보호구역<br>지형지물부호</th>
								<td class="modify_prtsAreaHt_ftfCde">
									<select class="sel_prtsAreaHt_ftfCde"></select>
								</td>
								<th>보호구역<br>관리번호</th>
								<td>
									<input type="text" class="modify_prtsAreaHt_ftfIdn">
								</td>
							</tr> -->
							<tr>
								<th>사업명</th>
								<td>
									<input type="text" class="modify_prtsAreaHt_prjNam">
								</td>
								<th>계약일</th>
								<td>
									<input type="text" class="modify_prtsAreaHt_cttYmd">
								</td>
							</tr>
							<tr>
								<th>착수일</th>
								<td>
									<input type="text" class="modify_prtsAreaHt_begYmd">
								</td>
								<th>준공일</th>
								<td>
									<input type="text" class="modify_prtsAreaHt_rfnYmd">
								</td>
							</tr>
							<tr>
								<th>공사개요</th>
								<td colspan="3">
									<input type="text" class="modify_prtsAreaHt_cntDes">
								</td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
			<!-- <div title="부속자료">
				<div class="div_kwsAtch" data-options="fit:true" style="width:100%; heigth:244px;">
				<form id="form_prtsAreaHt_atch">
					<table class="table_text">
						<tbody>
							<tr>
								<th>제목</th>
								<td>
									<input type="text" class="modify_prtsAreaHt_atchSj">
								</td>
							</tr>
							<tr>
								<th>내용</th>
								<td>
									<input type="text" class="modify_prtsAreaHt_atchCn">
								</td>
							</tr>
							<tr>
								<th>파일</th>
								<td>
									<input type="file" class="modify_prtsAreaHt_file">
								</td>
							</tr>
						</tbody>
					</table>
				</form>
				</div>
			</div> -->
		</div>
		<div class="window_footer">
				<div class="button_flat">
					<button class="btn_savePrtsAreaHt button_flat_normal btn_blue">저장</button>
					<button class="btn_closePrtsAreaHt button_flat_normal btn_blue">닫기</button>
				</div>
			</div>
	</div>
</div>