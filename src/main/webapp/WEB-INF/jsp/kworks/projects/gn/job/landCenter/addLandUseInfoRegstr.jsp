<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 			uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" 			uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="fmt" 		uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form"		uri="http://www.springframework.org/tags/form" %>

<form id="addLandUseInfoRegstrAddform" name="addLandUseInfoRegstrAddform" method="post" enctype="multipart/form-data">
	<input type="hidden" name="pnu" id="landUseInfoRegstrAddformPnu">
	<div id="addLandUseInfoRegstr" class="window_container">
		<div class="window_article">
			<table class="table_text">
				<tbody>
					<tr>
						<th>공사명</th>
						<td>
							 <select id="cntNam" name="cntIdn" class="easyui-combobox" style="width: 100%;">
								<c:forEach items="${cntNam}" var="cntNamObj" varStatus="status">
									<option value="${cntNamObj.cntIdn}">${cntNamObj.cntNam}</option>
								</c:forEach>
							</select>						
						</td>
					</tr>
				</tbody>
			</table>	
			<div class="tabs-1">
				<ul class="tab-list-1">
					<li class="active"><a class="tab-control" href="#list_tab1">일반사항</a></li>
					<li><a class="tab-control" href="#list_tab2">증명서</a></li>
				</ul>
			</div>
			<div class="tab-panel-1 active" id="list_tab1" style="height:141px">
				<table class="table_text">
					<tbody>
						<tr>
							<th>읍면동</th>
							<td>
								<select class="sel_dong easyui-combobox" style="width:50%">
									<c:forEach items="${selDong}" var="selDongObj" varStatus="status">
										<option value="${selDongObj.codeId}">${selDongObj.codeNm}</option>
									</c:forEach>
								</select>
							</td>
							<th>산</th>
							<td>
								<input type="checkbox" class="chk_mntn" name="chkMntn" id="div_quick_search_legal_dong_mntn" />
							</td>
						</tr>
						<tr>
							<th>본번</th>
							<td>
								<input type="text" class="txt_mnnm easyui-numberbox" name="txtMnnm" value=""/>
							</td>
							<th>부번</th>
							<td>
								<input type="text" class="txt_slno easyui-numberbox" name="txtSlno" value=""/>
							</td>											
						</tr>
						<tr>
							<th>소유자</th>
							<td>
								<input type="text" class="easyui-textbox" name="ownNam" value=""/>
							</td>
							<th>생년월일</th>
							<td>
								<input type="text" class="easyui-datebox" name="ownYmd" value=""/>
							</td>											
						</tr>
						<tr>
							<th>전체면적</th>
							<td>
								<input type="text" class="easyui-numberbox" name="totArea" value=""/>
							</td>
							<th>편입면적</th>
							<td>
								<input type="text" class="easyui-numberbox" name="incArea" value=""/>
							</td>											
						</tr>
					</tbody>
				</table>
			</div>
			<div class="tab-panel-1" id="list_tab2" style="height:141px;">
				<table class="table_text tableSelector">
					<tbody>
						<tr>
							<th>사용승낙서</th>
							<td>
								<input type="file" name="agrFile" id="agrFile" value="" accept="application/pdf">
							</td>
						</tr>
						<tr>
							<th>인감증명서</th>
							<td>
								<input type="file" name="regFile" id="regFile" value="" accept="application/pdf">
							</td>
						</tr>
						<tr>
							<th>용지도</th>
							<td>
								<input type="file" name="sitFile" id="sitFile" value="" accept="application/pdf">
							</td>
						</tr>
						<tr>
							<th>기타</th>
							<td>
								<input type="file" name="etcFile" id="etcFile" value="" accept="application/pdf">
							</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
		<div class="window_footer">
			<div class="button_flat">
				<button class="btn_save_addLandUseInfoRegstr button_flat_normal btn_blue">저장</button>
				<button class="btn_cancel_addLandUseInfoRegstr button_flat_normal btn_blue">취소</button>
			</div>
		</div>
	</div>
</form>


