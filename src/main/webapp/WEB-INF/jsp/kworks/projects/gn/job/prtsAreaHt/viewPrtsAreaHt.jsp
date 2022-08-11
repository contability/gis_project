<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 		     uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="window_container">
	<div class="window_article">
		<table class="table_text">
			<tbody>
				<tr>
					<th>관리번호</th>
					<td class="select_prtsAreaHt_impIdn"></td>
				</tr>
			</tbody>
		</table>
		<div class="div_window_prtsAreaHt_result_tabs">
			<div title="개선사업이력">
				<div class="div_prtsAreaHt" data-options="fit:true" style="width:100%; heigth:244px;">
					<table class="table_text">
						<tbody>
							<tr>
								<th>보호구역<br>지형지물부호</th>
								<td class="select_prtsAreaHt_ftfCde">
								</td>
								<th>보호구역<br>관리번호</th>
								<td class="select_prtsAreaHt_ftfIdn"></td>
							</tr>
							<tr>
								<th>사업명</th>
								<td class="select_prtsAreaHt_prjNam"></td>
								<th>계약일</th>
								<td class="select_prtsAreaHt_cttYmd"></td>
							</tr>
							<tr>
								<th>착수일</th>
								<td class="select_prtsAreaHt_begYmd"></td>
								<th>준공일</th>
								<td class="select_prtsAreaHt_rfnYmd"></td>
							</tr>
							<tr>
								<th>공사개요</th>
								<td class="select_prtsAreaHt_cntDes" colspan="3"></td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
			<div title="부속자료">
				<div class="div_kwsAtch" data-options="fit:true" style="width:100%; heigth:244px;">
					<form id="chkedForm">
						<!-- <input type="hidden" name="dataId" class="atch_prtsAreaHt_dataId"> -->
						<table class="table_text">
							<colgroup>
								<col width="10%">
								<col width="10%">
								<col width="20%">
								<col width="*">
								<%-- <col width="10%"> --%>
							</colgroup>
							<tbody>
								<tr>
									<th><input type="checkbox" id="chkAll"></th>
									<th>번호</th>
									<th>제목</th>
									<th>내용</th>
									<!-- <th>비고</th> -->
								</tr>
							</tbody>
						</table>
					</form>
				</div>
			</div>
		</div>
		<div class="window_footer">
				<div class="button_flat">
					<button class="btn_modifyPrtsAreaHt button_flat_normal btn_blue">편집</button>
					<button class="btn_removePrtsAreaHt button_flat_normal btn_blue">삭제</button>
					
					<button class="btn_downloadKwsAtch button_flat_normal btn_blue">내려받기</button>
					<button class="btn_addKwsAtch button_flat_normal btn_blue">등록</button>
					<button class="btn_removeKwsAtch button_flat_normal btn_blue">삭제</button>
					
					<button class="btn_closePrtsAreaHt button_flat_normal btn_blue">닫기</button>
				</div>
			</div>
	</div>
</div>