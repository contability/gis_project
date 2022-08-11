<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 			uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" 			uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="fmt" 		uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form"		uri="http://www.springframework.org/tags/form" %>

<div id="listLandUseInfoRegstr" class="window_container">
	<div class="window_article">
		<table class="table_text">
			<tbody>
				<tr>
					<th>공사명</th>
					<td colspan="3">
						<input type="text" class="easyui-textbox" name="cntNam" path="cntNam" style="width:100%"/>
					</td>
				</tr>
				<tr>
					<th>사용정보번호</th>
					<td colspan="3">
						<input type="text" class="easyui-numberbox" name="luiIdn" path="luiIdn" style="width:100%"/>
					</td>			
				</tr>
				<tr>
					<th>소유자명</th>
					<td colspan="3">
						<input type="text" class="easyui-textbox" name="ownNam" path="ownNam" style="width:100%"/>
					</td>
				</tr>
				<tr>
					<th>읍면동</th>
					<td>
						<select name="selDong" id="selDong" class="sel_dong easyui-combobox" style="width:50%">
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
						<input type="text" class="txt_mnnm easyui-numberbox" name="txtMnnm" path="txtMnnm"/>
					</td>
					<th>부번</th>
					<td>
						<input type="text" class="txt_slno easyui-numberbox" name="txtSlno" path="txtSlno"/>
					</td>											
				</tr>
			</tbody>
		</table>
	</div>
	<div class="window_footer">
		<div class="button_flat">
			<button class="a_reset_landUseInfoRegstr button_flat_normal btn_blue">초기화</button>
			<button class="a_search_landUseInfoRegstr button_flat_normal btn_blue">검색</button>
		</div>
	</div>
</div>


